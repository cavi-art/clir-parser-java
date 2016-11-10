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

import es.ucm.gpd.irparser.ast.VariableDeclaration;
import es.ucm.sexp.SexpParser;

import java.util.Collections;
import java.util.List;

import static es.ucm.gpd.irparser.ast.ASTUtils.consList;
import static es.ucm.gpd.irparser.ast.ASTUtils.unparseList;

/**
 * This assertion introduces existentially- or universally-quantified typed
 * variables as declared in {@link #declarations} for the inner expression
 * {@link #expr}.
 *
 * @author Santiago Saavedra
 * @see Quantifier#Exists
 * @see Quantifier#Forall
 */
public class QuantifiedAssertion implements AssertionExpr {
    private final Quantifier quantifier;
    private final List<VariableDeclaration> declarations;
    private final AssertionExpr expr;

    public QuantifiedAssertion(Quantifier quantifier, List<VariableDeclaration> declarations, AssertionExpr expr) {

        this.quantifier = quantifier;
        this.declarations = declarations;
        this.expr = expr;
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }

    public List<VariableDeclaration> getDeclarations() {
        return Collections.unmodifiableList(declarations);
    }

    public AssertionExpr getExpr() {
        return expr;
    }

    @Override
    public SexpParser.Expr unparse() {
        return consList(
                quantifier.unparse(),
                unparseList(declarations),
                expr.unparse()
        );
    }
}
