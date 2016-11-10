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

import es.ucm.sexp.SexpParser;

import static es.ucm.gpd.irparser.ast.ASTUtils.atom;
import static es.ucm.gpd.irparser.ast.ASTUtils.consList;

/**
 * This assertion contradicts the inner one.
 *
 * @author Santiago Saavedra
 */
public class NotAssertion implements AssertionExpr {
    private final AssertionExpr expr;

    public NotAssertion(AssertionExpr expr) {
        this.expr = expr;
    }

    public AssertionExpr getExpr() {
        return expr;
    }

    @Override
    public SexpParser.Expr unparse() {
        return consList(
                atom("not"),
                expr.unparse()
        );
    }
}
