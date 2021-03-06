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

package es.ucm.gpd.irparser.ast.type;

import es.ucm.sexp.Cons;
import es.ucm.sexp.SexpParser;

import java.util.Collections;
import java.util.List;

import static es.ucm.sexp.SexpUtils.listToCons;


/**
 * @author Santiago Saavedra
 */
public class CompoundType extends Type {
    private final String name;
    private final List<Type> expr;

    public CompoundType(String name, List<Type> expr) {

        this.name = name;
        this.expr = expr;
    }

    public String getName() {
        return name;
    }

    public List<Type> getExpr() {
        return Collections.unmodifiableList(expr);
    }

    public String toString() {
        return String.format("(%s %s)", name, listToCons(expr));
    }

    @Override
    public SexpParser.Expr unparse() {
        SexpParser.Expr e = null;
        for (int i = expr.size(); i >= 0; i--) {
            e = new Cons(expr.get(i).unparse(), e);
        }
        return e;
    }
}
