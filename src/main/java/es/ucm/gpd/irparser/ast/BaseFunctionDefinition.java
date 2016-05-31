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

import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.metadata.FunctionMetadata;
import es.ucm.gpd.irparser.ast.metadata.MetadataType;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Santiago Saavedra
 */
public class BaseFunctionDefinition {
    protected final String functionName;
    protected final List<VariableDeclaration> formalParameters;
    protected final List<VariableDeclaration> returnType;
    protected final Map<MetadataType, FunctionMetadata> functionMetadata;
    protected final Expression expr;

    public BaseFunctionDefinition(String functionName, Map<MetadataType, FunctionMetadata>
            functionMetadata, List<VariableDeclaration> formalParameters,
                                  List<VariableDeclaration> returnType,
                                  Expression expr) {
        this.functionName = functionName;
        this.functionMetadata = functionMetadata;
        this.formalParameters = formalParameters;
        this.returnType = returnType;
        this.expr = expr;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<VariableDeclaration> getFormalParameters() {
        return Collections.unmodifiableList(formalParameters);
    }

    public List<VariableDeclaration> getReturnType() {
        return returnType;
    }

    public Map<MetadataType, FunctionMetadata> getFunctionMetadata() {
        return functionMetadata;
    }

    public Expression getExpr() {
        return expr;
    }
}
