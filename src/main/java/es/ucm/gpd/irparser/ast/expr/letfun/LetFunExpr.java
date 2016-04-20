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

package es.ucm.gpd.irparser.ast.expr.letfun;

import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.type.Type;

import java.util.Collections;
import java.util.List;

/**
 * @author Santiago Saavedra
 */
public class LetFunExpr implements Expression {
    private final List<DFun> definitions;
    private final Expression expr;

    public LetFunExpr(List<DFun> definitions, Expression expr) {

        this.definitions = definitions;
        this.expr = expr;
    }

    public List<DFun> getDefinitions() {
        return Collections.unmodifiableList(definitions);
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
        StringBuilder definitions_str = new StringBuilder();
        definitions_str.append("(");

        for (DFun d : definitions) {
            definitions_str.append(d);
            definitions_str.append("\n    ");
        }

        definitions_str.append(")");

        return String.format("(letfun %s\n  %s)",
                definitions_str,
                expr);
    }
}
