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

import java.util.List;
import java.util.Map;

/**
 * @author Santiago Saavedra
 */
public class Assertion implements FunctionMetadata {
    private Map<AssertionType, List<AssertionExpr>> assertions;

    public Assertion(Map<AssertionType, List<AssertionExpr>> assertions) {
        this.assertions = assertions;
    }

    public Map<AssertionType, List<AssertionExpr>> getAssertions() {
        return assertions;
    }
}
