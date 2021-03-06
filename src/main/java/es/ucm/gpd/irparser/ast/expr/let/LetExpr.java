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

package es.ucm.gpd.irparser.ast.expr.let;

import es.ucm.gpd.irparser.ast.expr.AtomicExpression;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.type.Type;
import es.ucm.sexp.SexpParser;

import static es.ucm.gpd.irparser.ast.ASTUtils.atom;
import static es.ucm.gpd.irparser.ast.ASTUtils.consList;

/**
 * @author Santiago Saavedra
 */
public class LetExpr implements Expression {
    private final LetVarDecl lhs;
    private final AtomicExpression rhs;
    private final Expression expr;

    public LetExpr(LetVarDecl lhs, AtomicExpression rhs, Expression expr) {

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

    public Expression getExpr() {
        return expr;
    }

    @Override
    public Type getType() {
        return expr.getType();
    }

    @Override
    public String toString() {
        return String.format("(let (%s) %s \n    %s)",
                lhs, rhs, expr);
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
