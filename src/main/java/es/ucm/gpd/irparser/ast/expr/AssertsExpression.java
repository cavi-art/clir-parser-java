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

package es.ucm.gpd.irparser.ast.expr;

import es.ucm.gpd.irparser.ast.assertion.AssertionExpr;
import es.ucm.gpd.irparser.ast.type.Type;
import es.ucm.sexp.SexpParser;

import java.util.Collections;
import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.*;
import static es.ucm.sexp.SexpUtils.listToCons;

/**
 * @author Santiago Saavedra
 */
public class AssertsExpression implements Expression {

    private final List<AssertionExpr> asserts;
    private final Expression expression;

    public AssertsExpression(
            List<AssertionExpr> asserts, Expression expression
    ) {
        this.asserts = Collections.unmodifiableList(asserts);
        this.expression = expression;
    }

    @Override
    public Type getType() {
        return expression.getType();
    }

    @Override
    public String toString() {
        return String.format("(asserts (%s)\n  %s)",
                listToCons(asserts),
                expression);
    }

    @Override
    public SexpParser.Expr unparse() {
        return consList(atom("asserts"),
                unparseList(asserts),
                expression.unparse());
    }
}
