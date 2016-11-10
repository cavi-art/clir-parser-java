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
import es.ucm.gpd.irparser.ast.ASTUtils;
import es.ucm.gpd.irparser.ast.VerificationUnit;
import es.ucm.gpd.irparser.ast.expr.Expression;
import es.ucm.gpd.irparser.ast.expr.Var;
import es.ucm.gpd.irparser.ast.tld.FunctionDefinition;
import es.ucm.sexp.Atom;
import es.ucm.sexp.SexpParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Santiago Saavedra
 */
public class CaseExprSimpleTest {
    private static final String IN = "(verification-unit \"TEST\"\n" +
            ":sources (((:lang :clir) (:module :self)))\n" +
            ":uses (:ir)\n" +
            ":documentation \"This is a simple test\"\n" +
            ")\n\n" +
            "(define test ((v int)) ((r int))\n" +
            "  (declare (assertion (precd true) (postcd (@ = v r))))\n" +
            "  (case v (default v)))";

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

    @org.junit.Test
    public void getDiscriminant() throws Exception {
        assertTrue(expr.getDiscriminant() instanceof Var);
        assertTrue(((Var) expr.getDiscriminant()).getName().equals("v"));
    }

    @org.junit.Test
    public void getAlts() throws Exception {
        assertTrue(this.expr.getAlts().size() == 1);
        CaseAlt<Expression> alt = this.expr.getAlts().get(0);

        assertTrue(alt.getPattern() instanceof DefaultCase);
        assertTrue(alt.getExpr() instanceof Var);

        Var expr = (Var) alt.getExpr();

        assertTrue(expr.getName().equals("v"));
    }

    @org.junit.Test
    public void unparse() throws Exception {
        SexpParser.Expr expected = consList(a("case"), a("v"), consList("default", "v"));

        assertEquals(expected, this.expr.unparse());
    }

    private Atom a(String s) {
        return new Atom(s);
    }

    private SexpParser.Expr consList(String... atoms) {
        SexpParser.Expr[] a = Arrays
                .stream(atoms)
                .map(this::a)
                .collect(Collectors.toList())
                .toArray(new SexpParser.Expr[atoms.length]);

        return consList(a);
    }

    private SexpParser.Expr consList(SexpParser.Expr... exprs) {
        return ASTUtils.consList(exprs);
    }

}
