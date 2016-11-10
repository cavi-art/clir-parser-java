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

package es.ucm.gpd.irparser.ast.expr.let;

import es.ucm.gpd.irparser.ast.ASTNode;
import es.ucm.gpd.irparser.ast.VariableDeclaration;
import es.ucm.sexp.SexpParser;

import java.util.Collections;
import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.unparseList;

/**
 * @author Santiago Saavedra
 */
public class LetVarDecl implements ASTNode {
    private final List<VariableDeclaration> elements;

    public LetVarDecl(List<VariableDeclaration> elements) {

        this.elements = elements;
    }

    public List<VariableDeclaration> getElements() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public SexpParser.Expr unparse() {
        return unparseList(elements);
    }
}
