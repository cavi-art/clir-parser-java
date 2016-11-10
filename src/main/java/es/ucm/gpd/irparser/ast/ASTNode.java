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

import es.ucm.sexp.SexpParser;

/**
 * Allows to return the S-Expression from the already parsed AST node.
 *
 * @author Santiago Saavedra
 */
public interface ASTNode {
    /**
     * Return the representation of the type as a Symbolic Expression again.
     *
     * @return the {@link es.ucm.sexp.SexpParser.Expr} from this type
     * expression.
     */
    SexpParser.Expr unparse();
}
