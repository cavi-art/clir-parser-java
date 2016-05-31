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

import es.ucm.gpd.irparser.ast.BaseFunctionDefinition;
import es.ucm.gpd.irparser.ast.VariableDeclaration;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.metadata.FunctionMetadata;
import es.ucm.gpd.irparser.ast.metadata.MetadataType;
import es.ucm.sexp.Atom;
import es.ucm.sexp.Cons;

import java.util.List;
import java.util.Map;

import static es.ucm.sexp.SexpUtils.listToCons;


/**
 * This is a toplevel function definition.
 *
 * @author Santiago Saavedra
 */
public class FunctionDefinition
        extends BaseFunctionDefinition implements ToplevelDefinition {

    public FunctionDefinition(String functionName,
                              List<VariableDeclaration> formalParameters,
                              List<VariableDeclaration> returnType,
                              Map<MetadataType, FunctionMetadata> functionMetadata,
                              Expression expr) {
        super(functionName, functionMetadata, formalParameters, returnType,
                expr);
    }


    public String toString() {
        return String.format("(define %s %s %s (declare %s) \n  %s)",
                functionName,
                listToCons(formalParameters),
                returnType,
                mapToAlist(functionMetadata),
                expr);
    }

    private Cons mapToAlist(Map<MetadataType, FunctionMetadata> map) {
        Cons c = null;
        for (Map.Entry<MetadataType, FunctionMetadata> e : map.entrySet()) {
            final Cons current = new Cons(new Atom(e.getKey().toString()),
                    new Atom(e.getValue().toString()));
            c = new Cons(current, c);
        }
        return c;

    }
}
