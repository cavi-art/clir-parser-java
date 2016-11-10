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

import es.ucm.gpd.irparser.ast.ASTNode;
import es.ucm.sexp.SexpParser;

import static es.ucm.gpd.irparser.ast.ASTUtils.atom;

/**
 * These are the two quantifiers that we have in our logic.
 *
 * @author Santiago Saavedra
 */
public enum Quantifier implements ASTNode {

    /**
     * The universal quantifier.
     */
    Forall,

    /**
     * The existential quantifier.
     */
    Exists;

    @Override
    public SexpParser.Expr unparse() {
        return atom(name().toLowerCase());
    }
}
