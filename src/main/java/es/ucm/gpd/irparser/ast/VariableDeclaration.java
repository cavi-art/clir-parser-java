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

package es.ucm.gpd.irparser.ast;

import es.ucm.gpd.irparser.ast.type.Type;
import es.ucm.sexp.Atom;
import es.ucm.sexp.Cons;
import es.ucm.sexp.SexpParser;

/**
 * @author Santiago Saavedra
 */
public class VariableDeclaration implements ASTNode {
    private final Type type;
    private final String name;

    public VariableDeclaration(String name, Type type) {
        this.type = type;
        this.name = name;

    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("(%s %s)", name, type);
    }

    @Override
    public SexpParser.Expr unparse() {
        return new Cons(new Atom(getName()),
                new Cons(getType().unparse(), null));
    }
}
