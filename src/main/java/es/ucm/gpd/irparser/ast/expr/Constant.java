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

import es.ucm.gpd.irparser.ast.type.Type;
import es.ucm.sexp.SexpParser;
import es.ucm.sexp.StringAtom;

import static es.ucm.gpd.irparser.ast.ASTUtils.atom;
import static es.ucm.gpd.irparser.ast.ASTUtils.consList;

/**
 * @author Santiago Saavedra
 */
public class Constant<T> implements Atom {
    private final Type type;
    private final T value;

    public Constant(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    private static String _quotedString(Object value) {
        final char[] ca = value.toString().toCharArray();

        final StringBuilder sb = new StringBuilder();

        sb.append('"');

        for (char c : ca) {
            switch (c) {
                case '"':
                    sb.append('\\'); // Prepend a backslash in escaped values
                    break;
                case '\\':
                    sb.append("\\");
                    break;
            }
            sb.append(c);

        }

        sb.append('"');

        return sb.toString();
    }

    @Override
    public Type getType() {

        return type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("(the %s %s)", type, value);
    }

    @Override
    public SexpParser.Expr unparse() {
        es.ucm.sexp.Atom strValue;

        if (value == value.toString()) {
            strValue = new StringAtom(value.toString());
        } else {
            strValue = new es.ucm.sexp.Atom(value.toString());
        }

        return consList(
                atom("the"),
                type.unparse(),
                strValue
        );
    }

}
