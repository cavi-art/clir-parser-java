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

import es.ucm.gpd.irparser.ast.ASTNode;
import es.ucm.gpd.irparser.ast.type.Type;
import es.ucm.sexp.SexpParser;

import java.util.Collections;
import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.*;

/**
 * @author Santiago Saavedra
 */
public class DataType implements ToplevelDefinition {

    private final Type newName;
    private final List<ConstructedType> alternatives;

    public DataType(Type newName, List<ConstructedType> alternatives) {
        this.newName = newName;
        this.alternatives = alternatives;
    }

    public Type getNewName() {
        return newName;
    }

    public List<ConstructedType> getAlternatives() {
        return
                Collections.unmodifiableList(alternatives);
    }

    // TODO: Syntax is not yet fixed here
    @Override
    public SexpParser.Expr unparse() {
        return consList(
                atom("datatype"),
                newName.unparse(),
                unparseList(alternatives)
        );
    }

    /**
     * This class represents the alternatives of the constructed types for a
     * <b>data</b> declaration.
     */
    public static class ConstructedType implements ASTNode {
        private final String constructor;
        private final Type typeExpr;

        public ConstructedType(String constructor, Type typeExpr) {
            this.constructor = constructor;
            this.typeExpr = typeExpr;
        }

        public String getConstructor() {
            return constructor;
        }

        public Type getTypeExpr() {
            return typeExpr;
        }

        // TODO: Syntax not yet fixed
        @Override
        public SexpParser.Expr unparse() {
            return consList(atom("@@"),
                    atom(constructor),
                    typeExpr.unparse()
            );
        }
    }

}
