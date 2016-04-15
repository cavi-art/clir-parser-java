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

import es.ucm.gpd.irparser.ast.expr.AtomicExpression;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.type.Type;

import java.util.List;


/**
 * This is a case expression, which means that an alternative is chosen,
 * according to the matching of its {@link #discriminant} to the various
 * {@link #alts}.
 *
 * @author Santiago Saavedra
 */
public class CaseExpr implements Expression {

    /* TODO Should the default case be here as a distinct variable to
    enforce the fact that there must always be a default case? */

    private AtomicExpression discriminant;
    private List<CaseAlt<Expression>> alts;

    public CaseExpr(AtomicExpression discriminant, List<CaseAlt<Expression>> alts) {
        this.discriminant = discriminant;
        this.alts = alts;
    }

    public AtomicExpression getDiscriminant() {

        return discriminant;
    }

    public List<CaseAlt<Expression>> getAlts() {
        return alts;
    }

    @Override
    public Type getType() {
        return alts.get(0).getExpr().getType();
    }

}
