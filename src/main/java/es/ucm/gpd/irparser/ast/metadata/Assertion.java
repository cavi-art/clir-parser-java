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

package es.ucm.gpd.irparser.ast.metadata;

import es.ucm.gpd.irparser.ast.assertion.AssertionExpr;
import es.ucm.gpd.irparser.ast.assertion.AssertionType;
import es.ucm.sexp.SexpParser;

import java.util.Map;

import static es.ucm.gpd.irparser.ast.ASTUtils.consList;
import static es.ucm.gpd.irparser.ast.ASTUtils.mapToAlist;
import static java.util.Collections.unmodifiableMap;

/**
 * @author Santiago Saavedra
 */
public class Assertion implements FunctionMetadata {
    private final Map<AssertionType, AssertionExpr> assertions;

    public Assertion(Map<AssertionType, AssertionExpr> assertions) {
        this.assertions = assertions;
    }

    public Map<AssertionType, AssertionExpr> getAssertions() {
        return unmodifiableMap(assertions);
    }

    @Override
    public SexpParser.Expr unparse() {
        return mapToAlist(assertions, e -> consList(
                e.getKey().unparse(),
                e.getValue().unparse()
        ));
    }
}
