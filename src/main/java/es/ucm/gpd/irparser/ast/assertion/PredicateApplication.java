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

import es.ucm.gpd.irparser.ast.expr.AtomicExpression;
import es.ucm.sexp.SexpParser;

import java.util.Collections;
import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.*;

/**
 * This is a predicate application to a list of arguments. The arguments may
 * be {@link AtomicExpression}s.
 *
 * @author Santiago Saavedra
 */
public class PredicateApplication implements AssertionExpr {
    private final String predicateName;
    private final List<AtomicExpression> arguments;

    public PredicateApplication(String predicateName, List<AtomicExpression> arguments) {
        this.predicateName = predicateName;
        this.arguments = arguments;
    }

    public String getPredicateName() {

        return predicateName;
    }

    public List<AtomicExpression> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    @Override
    public SexpParser.Expr unparse() {
        return reverseConsAfter(
                unparseList(arguments),

                atom("@"),
                atom(predicateName)
        );
    }
}
