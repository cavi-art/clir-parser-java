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

package es.ucm.gpd.irparser.ast.expr.case_;

import es.ucm.gpd.irparser.ast.expr.Constant;
import es.ucm.sexp.SexpParser;

/**
 * @author Santiago Saavedra
 */
public class CaseConstant<T> implements CasePattern {
    private final Constant<T> constant;

    public CaseConstant(Constant<T> constant) {
        this.constant = constant;
    }

    public Constant<T> getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        return constant.toString();
    }

    @Override
    public SexpParser.Expr unparse() {
        return constant.unparse();
    }
}
