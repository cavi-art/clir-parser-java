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

package es.ucm.gpd.irparser.ast.expr.case_;

import es.ucm.gpd.irparser.IRFileParser;
import es.ucm.gpd.irparser.ast.ASTUtils;
import es.ucm.gpd.irparser.ast.VerificationUnit;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.expr.let.LetExpr;
import es.ucm.gpd.irparser.ast.tld.FunctionDefinition;
import es.ucm.sexp.Atom;
import es.ucm.sexp.Cons;
import es.ucm.sexp.SexpParser;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Santiago Saavedra
 */
public class CaseExprCompoundExprTest {
    private static final String IN = "(verification-unit \"TEST\"\n" +
            ":sources (((:lang :clir) (:module :self)))\n" +
            ":uses (:ir)\n" +
            ":documentation \"This is a simple test\"\n" +
            ")\n\n" +
            "(define test ((v int)) ((r int))\n" +
            "  (declare (assertion \n" +
            "              (precd true) \n" +
            "              (postcd (@ = (@ - v 1) r))))\n" +
            "  (case v\n" +
            "    (default (let ((v1 int)) (@ - v 1) \n" +
            "               v1))))";

    private InputStream in;
    private IRFileParser parser;
    private CaseExpr expr;
    private FunctionDefinition definition;

    @org.junit.Before
    public void setUp() throws Exception {
        in = new ByteArrayInputStream(IN.getBytes());
        parser = new IRFileParser(in);
        List<VerificationUnit> list = parser.parseAllToplevel();
        definition = (FunctionDefinition) list.get(0).getToplevelForms().get(0);
        expr = (CaseExpr) definition.getExpr();
    }

    @Test
    public void getAlts() throws Exception {

        assertTrue(this.expr.getAlts().size() == 1);
        CaseAlt<Expression> alt = this.expr.getAlts().get(0);
        assertTrue(alt.getExpr() instanceof LetExpr);
        LetExpr expr = (LetExpr) alt.getExpr();

        assertTrue(expr.getLhs().getElements().size() == 1);
    }

    private Atom a(String s) {
        return new Atom(s);
    }

    private SexpParser.Expr consList(String... atoms) {
        SexpParser.Expr[] a = Arrays
                .stream(atoms)
                .map(this::a)
                .collect(Collectors.toList())
                .toArray(new SexpParser.Expr[atoms.length]);

        return consList(a);
    }

    private SexpParser.Expr consList(SexpParser.Expr... exprs) {
        return ASTUtils.consList(exprs);
    }

    private SexpParser.Expr consList(String atom, SexpParser.Expr... exprs) {
        return new Cons(a(atom), ASTUtils.consList(exprs));
    }

    @Test
    public void unparse() throws Exception {
        SexpParser.Expr expected = consList(
                a("define"),
                a("test"),
                consList(consList("v", "int")),
                consList(consList("r", "int")),
                consList("declare",
                        consList("assertion",
                                consList("postcd",
                                        consList("@", a("="),
                                                consList("@", "-", "v",
                                                        "1"),
                                                a("r"))),
                                consList("precd", "true"))),
                consList("case", a("v"),
                        consList("default",
                                consList("let",
                                        consList(consList("v1", "int")),
                                        consList("@", "-", "v", "1"),
                                        a("v1")
                                )
                        )
                )
        );

        assertEquals(expected, this.definition.unparse());
    }

}
