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
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.expr.case_.CaseAlt;
import es.ucm.gpd.irparser.ast.expr.case_.CaseExpr;
import es.ucm.sexp.SexpParser;

import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.*;


/**
 * This branches an assertion following a condition set by its {@link
 * #discriminant}, which is then matched to the various {@link #alts}.
 * <p>
 * The matching is performed just like in {@link CaseExpr}. This case allows
 * {@link AtomicExpression}s in its {@link LetAssertion#rhs}.
 *
 * @author Santiago Saavedra
 * @see LetAssertion
 * @see CaseExpr
 */
public class CaseAssertion implements AssertionExpr {
    private final AtomicExpression discriminant;
    private final List<CaseAlt<AssertionExpr>> alts;

    public CaseAssertion(AtomicExpression discriminant, List<CaseAlt<AssertionExpr>> alts) {
        this.discriminant = discriminant;
        this.alts = alts;
    }

    public Expression getDiscriminant() {
        return discriminant;
    }

    public List<CaseAlt<AssertionExpr>> getAlts() {
        return alts;
    }

    @Override
    public SexpParser.Expr unparse() {
        return consList(
                atom("case"),
                discriminant.unparse(),
                unparseList(alts)
        );
    }
}
