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

package es.ucm.gpd.irparser.ast.assertion;

import es.ucm.gpd.irparser.ast.expr.AtomicExpression;
import es.ucm.gpd.irparser.ast.expr.let.LetExpr;
import es.ucm.gpd.irparser.ast.expr.let.LetVarDecl;
import es.ucm.sexp.SexpParser;

import static es.ucm.gpd.irparser.ast.ASTUtils.atom;
import static es.ucm.gpd.irparser.ast.ASTUtils.consList;

/**
 * This assertion introduces a new lexical variable in the scope (or
 * destructured variable, it is the same kind of expression allowed as in
 * {@link LetExpr}). This let allows {@link AtomicExpression}s in its {@link
 * LetAssertion#rhs}.
 *
 * @author Santiago Saavedra
 * @see CaseAssertion
 * @see LetExpr
 */
public class LetAssertion implements AssertionExpr {
    private final LetVarDecl lhs;
    private final AtomicExpression rhs;
    private final AssertionExpr expr;

    public LetAssertion(LetVarDecl lhs, AtomicExpression rhs, AssertionExpr expr) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.expr = expr;
    }

    public LetVarDecl getLhs() {
        return lhs;
    }

    public AtomicExpression getRhs() {
        return rhs;
    }

    public AssertionExpr getExpr() {
        return expr;
    }

    @Override
    public SexpParser.Expr unparse() {
        return consList(
                atom("let"),
                lhs.unparse(),
                rhs.unparse(),
                expr.unparse()
        );
    }
}
