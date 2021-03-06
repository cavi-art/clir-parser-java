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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.*;

/**
 * @author Santiago Saavedra
 */
public class FunctionApplication implements AtomicExpression {
    private final String functionName;
    private final List<Atom> arguments;

    public FunctionApplication(String functionName, List<Atom> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Atom> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public Type getType() {
        throw new NotImplementedException();
    }

    @Override
    public String toString() {
        StringBuilder splicedArguments = new StringBuilder();
        for (Atom a : arguments) {
            splicedArguments.append(" ");
            splicedArguments.append(a);
        }

        return String.format("(@ %s %s)", functionName, splicedArguments
                .toString());
    }

    @Override
    public SexpParser.Expr unparse() {
        return reverseConsAfter(
                unparseList(arguments),

                atom("@"),
                atom(functionName)
        );
    }
}
