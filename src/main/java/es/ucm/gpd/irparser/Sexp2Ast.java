/*
 * Copyright (c) 2016. Grupo de Programación Declarativa - Universidad Complutense de Madrid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.ucm.gpd.irparser;

import es.ucm.gpd.irparser.ast.VariableDeclaration;
import es.ucm.gpd.irparser.ast.VerificationUnit;
import es.ucm.gpd.irparser.ast.assertion.*;
import es.ucm.gpd.irparser.ast.expr.*;
import es.ucm.gpd.irparser.ast.expr.case_.*;
import es.ucm.gpd.irparser.ast.expr.let.LetExpr;
import es.ucm.gpd.irparser.ast.expr.let.LetVarDecl;
import es.ucm.gpd.irparser.ast.expr.letfun.DFun;
import es.ucm.gpd.irparser.ast.expr.letfun.LetFunExpr;
import es.ucm.gpd.irparser.ast.metadata.FunctionMetadata;
import es.ucm.gpd.irparser.ast.metadata.MetadataType;
import es.ucm.gpd.irparser.ast.tld.FunctionDefinition;
import es.ucm.gpd.irparser.ast.tld.ToplevelDefinition;
import es.ucm.gpd.irparser.ast.type.*;
import es.ucm.sexp.SexpFileParser;
import es.ucm.sexp.SexpParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static es.ucm.sexp.SexpUtils.*;

/**
 * @author Santiago Saavedra
 */
public class Sexp2Ast {
    private static VerificationUnit parseVUHeader(SexpParser.Expr expr) {
        assert expr.isList();

        assert car(expr).isAtom();
        assert car(expr).getAtom().toString().equalsIgnoreCase
                ("verification-unit");
        List<String> usedPackages = new ArrayList<>();
        String documentation = "";
        List<String> verifyOnly = new ArrayList<>();
        List<String> assumeVerified = new ArrayList<>();

        String packageName = cadr(expr).getAtom().toString();
        expr = cddr(expr);

        while (expr != null) {
            if (carEqualsIgnoreCase(expr, ":uses")) {
                usedPackages = consToStringList(cadr(expr));
            } else if (carEqualsIgnoreCase(expr, ":documentation")) {
                documentation = cadr(expr).toString();
            } else if (carEqualsIgnoreCase(expr, ":verify-only")) {
                verifyOnly.addAll(consToStringList(cadr(expr)));
            } else if (carEqualsIgnoreCase(expr, ":assume-verified")) {
                assumeVerified.addAll(consToStringList(cadr(expr)));
            } else {
                System.err.println("Unhandled metadata " + car(expr));
            }
            expr = cddr(expr);

        }

        return new VerificationUnit(packageName, null,
                usedPackages, documentation, verifyOnly, assumeVerified);
    }

    private static boolean carEqualsIgnoreCase(SexpParser.Expr e, String s) {
        return e != null
                && e.isList()
                && car(e).toString().equalsIgnoreCase(s);
    }

    public static VerificationUnit parseVU(SexpFileParser parser) throws
            SexpParser.ParseException {
        VerificationUnit header = parseVUHeader(parser.parseExpr());

        List<ToplevelDefinition> topLevelForms = new ArrayList<>();

        while (parser.hasNext()) {
            SexpParser.Expr expr = parser.next();

            if (car(expr).toString().equalsIgnoreCase("define"))
                topLevelForms.add(parseDefine(expr));

            else if (car(expr).toString().equalsIgnoreCase("declare")) {
                // Ignore toplevel declarations
                System.err.println("Ignoring toplevel declaration " + expr);
            }
        }

        return new VerificationUnit(header.getPackageName(), topLevelForms,
                header.getUsedPackages(), header.getDocumentation(), header
                .getVerifyOnly(), header.getAssumeVerified());
    }

    private static ToplevelDefinition parseDefine(SexpParser.Expr expr) {
        assert car(expr).toString().equalsIgnoreCase("define");

        return parseFunctionDeclaration(cdr(expr));
    }

    private static FunctionDefinition parseFunctionDeclaration(
            SexpParser.Expr cons
    ) {
        String functionName = car(cons).getAtom().toString();


        List<VariableDeclaration> formalParameters = parseVarDeclList(cadr
                (cons));
        List<VariableDeclaration> returnType = parseVarDeclList(cadr(cdr
                (cons)));

        Map<MetadataType, FunctionMetadata> metadata = Collections.EMPTY_MAP;

        // Test for optional metadata
        if (carEqualsIgnoreCase(cadr(cddr(cons)), "declare")) {
            metadata = FunctionMetadata.fromAList(cdr(cadr(cddr(cons))));
            cons = cdr(cons);
        }

        Expression expr = parseExpr(cadr(cddr(cons)));

        return new FunctionDefinition(functionName, formalParameters,
                returnType, metadata, expr);
    }

    private static Atom parseAtom(
            SexpParser.Expr expr
    ) {
        if (expr.isAtom()) {
            return new Var(expr.getAtom().toString(), null);
        }

        if (car(expr).toString().equalsIgnoreCase("the")) {
            return new Constant<>(parseType(cadr(expr)), cadr(cdr(expr)));
        }

        return null;
    }

    private static Expression parseExpr(SexpParser.Expr expr) {
        final AtomicExpression asAtomicExpr = parseAtomicExpr(expr);
        if (asAtomicExpr != null)
            return asAtomicExpr;

        // Else it is a complicated expression (letfun, let or case)
        if (car(expr).toString().equalsIgnoreCase("letfun")) {
            List<DFun> definitions = consToList(cadr(expr)).stream()
                    .map((e) -> {
                        FunctionDefinition d = parseFunctionDeclaration(e);
                        return new DFun(d.getFunctionName(), d
                                .getFunctionMetadata(), d
                                .getFormalParameters(), d.getReturnType(),
                                d.getExpr());
                    }).collect(Collectors.toList());

            Expression letExpr = parseExpr(cadr(cdr(expr)));
            return new LetFunExpr(definitions, letExpr);
        }

        if (car(expr).toString().equalsIgnoreCase("let")) {
            LetVarDecl lhs = new LetVarDecl(parseVarDeclList(cadr(expr)));
            AtomicExpression rhs = parseAtomicExpr(cadr(cdr(expr)));
            return new LetExpr(lhs, rhs, parseExpr(cadr(cddr(expr))));
        }

        if (car(expr).toString().equalsIgnoreCase("case")) {
            AtomicExpression discriminant = parseAtomicExpr(cadr(expr));
            List<CaseAlt<Expression>> alts = consToList(cddr(expr))
                    .stream()
                    .map(e -> new CaseAlt<Expression>(
                            parseCasePattern(car(e)), parseAtomicExpr(e)
                    ))
                    .collect(Collectors.toList());

            return new CaseExpr(discriminant, alts);
        }

        throw new IllegalArgumentException(String.format(
                "Expected <expr> but found %s", expr.toString()));
    }

    private static CasePattern parseCasePattern(SexpParser.Expr
                                                        expr) {
        if (carEqualsIgnoreCase(expr, "@@")) {
            String cname = cadr(expr).getAtom().toString();

            return new CasePatternConstructorApplication(cname,
                    parseVarDeclList(cddr(expr)));
        }

        if (carEqualsIgnoreCase(expr, "the")) {
            return new CaseConstant<>((Constant) parseAtom(expr));
        }

        if (carEqualsIgnoreCase(expr, "default")) {
            return new DefaultCase();
        }

        throw new IllegalArgumentException(String.format(
                "Expected <case-pattern> but found %s", expr.toString()));
    }

    private static AtomicExpression parseAtomicExpr(SexpParser.Expr expr) {
        final Atom asAtom = parseAtom(expr);
        if (asAtom != null)
            return asAtom;

        if (car(expr).toString().equalsIgnoreCase("tuple")) {
            final List<Atom> atoms = consToList
                    (cdr(expr))
                    .stream()
                    .map(Sexp2Ast::parseAtom)
                    .collect(Collectors.toList());

            return new Tuple(atoms);
        }

        if (car(expr).toString().equalsIgnoreCase("@")) {
            final String fname = cadr(expr).getAtom().toString();
            final List<Atom> args = consToList(cddr(expr)).stream()
                    .map(Sexp2Ast::parseAtom)
                    .collect(Collectors.toList());

            return new FunctionApplication(fname, args);
        }

        if (car(expr).toString().equalsIgnoreCase("@@")) {
            final String cname = cadr(expr).getAtom().toString();
            final List<Atom> args = consToList(cddr(expr)).stream()
                    .map(Sexp2Ast::parseAtom)
                    .collect(Collectors.toList());

            return new ConstructorApplication(cname, args);
        }

        return null;
    }

    private static List<VariableDeclaration> parseVarDeclList(
            SexpParser.Expr expr
    ) {
        return consToList(expr).stream()
                .map(Sexp2Ast::parseVarDecl)
                .collect(Collectors.toList());
    }

    private static VariableDeclaration parseVarDecl(SexpParser.Expr var) {
        return new VariableDeclaration(car(var).toString(), parseType
                (cadr(var)));
    }

    private static Type parseType(SexpParser.Expr e) {
        if (e.isAtom()) {
            if (e.toString().startsWith("'")) {
                // Quoted type: it is a type variable
                return new TypeVar(e.toString().substring(1));
            } else {
                return new SimpleType(e.toString());
            }
        }

        if (e.getList().car == null) {
            // Empty list: it is unit ()
            return new UnitType();
        }

        List<Type> typeArgs = consToList(cdr(e)).stream()
                .map(Sexp2Ast::parseType)
                .collect(Collectors.toList());
        return new CompoundType(car(e).getAtom().toString(), typeArgs);
    }


    public static AssertionExpr parseAssertion(SexpParser.Expr expr) {
        if (expr.isAtom()) {
            switch (expr.getAtom().toString().toLowerCase()) {
                case "true":
                    return new TrueAssertion();
                case "false":
                    return new FalseAssertion();
                default:
                    throw new RuntimeException("Cannot handle assertion " +
                            "atom: " + expr);
            }
        }
        switch (car(expr).getAtom().toString().toLowerCase()) {
            case "forall":
                return parseQuantified(Quantifier.Forall, cdr(expr));
            case "exists":
                return parseQuantified(Quantifier.Exists, cdr(expr));
            case "@":
                return parseApplication(cdr(expr));
            case "->":
                return parseBoolean(BooleanOperator.Implies,
                        cdr(expr));
            case "<->":
                return parseBoolean(BooleanOperator.DoubleImplies,
                        cdr(expr));
            case "and":
                return parseBoolean(BooleanOperator.And, cdr(expr));
            case "or":
                return parseBoolean(BooleanOperator.Or, cdr(expr));
            case "case":
                AtomicExpression discriminant = parseAtomicExpr(cadr(expr));
                List<CaseAlt<AssertionExpr>> alts = consToList(cddr(expr))
                        .stream()
                        .map(e -> new CaseAlt<>(
                                parseCasePattern(car(e)), parseAssertion(e)
                        ))
                        .collect(Collectors.toList());

                return new CaseAssertion(discriminant, alts);
            case "let":
                final LetVarDecl lhs = new LetVarDecl(
                        parseVarDeclList(cadr(expr)));
                final AtomicExpression rhs = parseAtomicExpr(cadr(cdr(expr)));

                return new LetAssertion(lhs, rhs, parseAssertion(cadr(cddr(expr))));
            default:
                throw new RuntimeException("Unknown predicate symbol: "
                        + car(expr));
        }
    }

    private static AssertionExpr parseQuantified(Quantifier q, SexpParser
            .Expr expr) {
        SexpParser.Expr varDeclList = car(expr);
        SexpParser.Expr mainExpr = cadr(expr);

        return new QuantifiedAssertion(q, parseVarDeclList(varDeclList),
                parseAssertion(mainExpr));
    }

    private static AssertionExpr parseApplication(SexpParser.Expr expr) {
        String predicateName = car(expr).getAtom().toString();
        SexpParser.Expr arguments = cdr(expr);

        List<AtomicExpression> args = consToList(arguments)
                .stream()
                .map(Sexp2Ast::parseAtomicExpr)
                .collect(Collectors.toList());

        return new PredicateApplication(predicateName, args);
    }

    private static AssertionExpr parseBoolean(BooleanOperator op,
                                              SexpParser.Expr expr) {

        List<AssertionExpr> expressionList = consToList(expr)
                .stream()
                .map(Sexp2Ast::parseAssertion)
                .collect(Collectors.toList());

        return new BooleanAssertion(op, expressionList);
    }


}
