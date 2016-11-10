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

import es.ucm.gpd.irparser.ast.ASTNode;
import es.ucm.gpd.irparser.ast.BaseFunctionDefinition;
import es.ucm.gpd.irparser.ast.VariableDeclaration;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.metadata.FunctionMetadata;
import es.ucm.gpd.irparser.ast.metadata.MetadataType;

import java.util.List;
import java.util.Map;

import static es.ucm.sexp.SexpUtils.listToCons;


/**
 * @author Santiago Saavedra
 */
public class DFun extends BaseFunctionDefinition implements ASTNode {
    public DFun(String functionName, Map<MetadataType, FunctionMetadata> functionMetadata,
                List<VariableDeclaration> formalParameters,
                List<VariableDeclaration> returnType, Expression expr) {
        super(functionName, functionMetadata, formalParameters,
                returnType, expr);
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s (declare %s) %s)",
                functionName,
                listToCons(formalParameters),
                returnType,
                functionMetadata,
                expr);
    }
}
