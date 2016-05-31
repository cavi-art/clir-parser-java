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

import java.util.HashMap;
import java.util.Map;

import static es.ucm.gpd.irparser.Sexp2Ast.parseAssertion;
import static es.ucm.sexp.SexpUtils.*;

/**
 * This is a declaration which can be either assertion, optimize and metadata
 * TODO make subclasses
 *
 * @author Santiago Saavedra
 */
public interface FunctionMetadata {
    static Map<MetadataType, FunctionMetadata> fromAList(SexpParser.Expr metacons) {
        final Map<MetadataType, FunctionMetadata> map = new HashMap<>();

        while (metacons != null) {
            final SexpParser.Expr elt = car(metacons);

            switch (car(elt).getAtom().toString().toLowerCase()) {
                case "assertion":
                    SexpParser.Expr assertions = cdr(elt);
                    Map<AssertionType, AssertionExpr> assertionsMap = new
                            HashMap<>();

                    while (assertions != null) {
                        final SexpParser.Expr key = car(assertions);
                        switch (car(key).getAtom().toString().toLowerCase()) {
                            case "precd":
                                assertionsMap.put(AssertionType.PreCD,
                                        parseAssertion(cadr(key)));
                                break;
                            case "postcd":
                                assertionsMap.put(AssertionType.PostCD,
                                        parseAssertion(cadr(key)));
                                break;
                            default:
                                throw new RuntimeException(
                                        "Unknown assertion type " + car(key)
                                );
                        }
                        assertions = cdr(assertions);
                    }
                    map.put(MetadataType.Assertion, new Assertion
                            (assertionsMap));
            }

            metacons = cdr(metacons);
        }

        return map;
    }
}
