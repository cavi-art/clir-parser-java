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

import java.util.Collections;
import java.util.List;

/**
 * This is an assertion made of a list of assertions adjoined by a determined
 * {@link BooleanOperator}.
 *
 * @author Santiago Saavedra
 * @see BooleanOperator
 * @see AssertionExpr
 */
public class BooleanAssertion implements AssertionExpr {
    private final BooleanOperator op;
    private final List<AssertionExpr> exprList;

    public BooleanAssertion(BooleanOperator op, List<AssertionExpr> exprList) {
        this.op = op;

        this.exprList = exprList;
    }

    public BooleanOperator getOp() {
        return op;
    }

    public List<AssertionExpr> getExprList() {
        return Collections.unmodifiableList(exprList);
    }
}
