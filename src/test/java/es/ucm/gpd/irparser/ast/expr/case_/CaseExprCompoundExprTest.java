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

import es.ucm.gpd.irparser.IRFileParser;
import es.ucm.gpd.irparser.ast.VerificationUnit;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.expr.let.LetExpr;
import es.ucm.gpd.irparser.ast.tld.FunctionDefinition;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Santiago Saavedra
 */
public class CaseExprCompoundExprTest {
    private static final String IN = "(verification-unit \"TEST\"\n" +
            ":sources (((:lang :clir) (:module :self)))\n" +
            ":uses (:ir)\n" +
            ":documentation \"This is a simple test\"\n" +
            ")\n\n" +
            "(define test ((v int)) ((r int))\n" +
            "  (declare (assertion \n" +
            "              (precd true) \n" +
            "              (postcd (@ = (@ - v 1) r))))\n" +
            "  (case v\n" +
            "    (default (let ((v1 int)) (@ - v 1) \n" +
            "               v1))))";

    private InputStream in;
    private IRFileParser parser;
    private CaseExpr expr;

    @org.junit.Before
    public void setUp() throws Exception {
        in = new ByteArrayInputStream(IN.getBytes());
        parser = new IRFileParser(in);
        List<VerificationUnit> list = parser.parseAllToplevel();
        FunctionDefinition definition = (FunctionDefinition) list.get(0).getToplevelForms().get(0);
        expr = (CaseExpr) definition.getExpr();
    }

    @Test
    public void getAlts() throws Exception {

        assertTrue(this.expr.getAlts().size() == 1);
        CaseAlt<Expression> alt = this.expr.getAlts().get(0);
        assertTrue(alt.getExpr() instanceof LetExpr);
        LetExpr expr = (LetExpr) alt.getExpr();

        assertTrue(expr.getLhs().getElements().size() == 1);
    }

}