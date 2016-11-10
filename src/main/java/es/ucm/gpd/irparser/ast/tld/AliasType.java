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

package es.ucm.gpd.irparser.ast.tld;

import es.ucm.gpd.irparser.ast.type.Type;
import es.ucm.sexp.SexpParser;

import static es.ucm.gpd.irparser.ast.ASTUtils.atom;
import static es.ucm.gpd.irparser.ast.ASTUtils.consList;

/**
 * This is a type alias, so that the new type declaration should be
 * substituted by the original one, modulo type parameters.
 * <p>
 * WARNING: This class does not support the extended syntax of possible more
 * optional data after the original type expression.
 *
 * @author Santiago Saavedra
 */
public class AliasType implements ToplevelDefinition {
    private Type alias;
    private Type original;

    public AliasType(Type alias, Type original) {
        if (alias.equals(original)) {
            throw new RuntimeException("Cannot alias a type to itself.");
        }

        this.alias = alias;
        this.original = original;
    }

    public Type getAlias() {
        return alias;
    }

    public Type getOriginal() {
        return original;
    }

    // TODO: Syntax is not yet fixed here
    @Override
    public SexpParser.Expr unparse() {
        return consList(atom("type"),
                alias.unparse(),
                original.unparse()
        );
    }
}
