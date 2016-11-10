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

package es.ucm.gpd.irparser.ast;

import es.ucm.sexp.Atom;
import es.ucm.sexp.Cons;
import es.ucm.sexp.SexpParser;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utilities class for transforming an AST back to a S-Expression.
 *
 * @author Santiago Saavedra
 */
public class ASTUtils {
    public static Atom atom(String a) {
        return new Atom(a);
    }

    public static Cons cons(SexpParser.Expr a, SexpParser.Expr b) {
        return new Cons(a, b);
    }

    public static Cons cons(String a, SexpParser.Expr b) {
        return new Cons(new Atom(a), b);
    }

    /**
     * Create a proper cons-list with the given arguments
     *
     * @param a the expressions to form the cons
     * @return a null-terminated cons-list (proper cons-list)
     */
    public static SexpParser.Expr consList(SexpParser.Expr... a) {
        return reverseConsAfter(null, a);
    }

    public static SexpParser.Expr
    reverseConsAfter(SexpParser.Expr tail, SexpParser.Expr... a) {
        SexpParser.Expr c = tail;

        for (int i = a.length - 1; i >= 0; i--) {
            c = new Cons(a[i], c);
        }
        return c;
    }

    public static Cons arrayToCons(Object[] objects) {
        Cons cons = null;

        for (int i = objects.length - 1; i >= 0; --i) {
            cons = new Cons(new Atom(objects[i].toString()), cons);
        }

        return cons;
    }

    public static SexpParser.Expr unparseList(List<? extends ASTNode> l) {
        return consList(l
                .stream()
                .map(ASTNode::unparse)
                .collect(Collectors.toList())
                .toArray(new SexpParser.Expr[l.size()])
        );
    }

    public static Cons
    mapToAlist(Map<? extends ASTNode, ? extends ASTNode> map) {

        Cons c = null;

        for (Map.Entry<? extends ASTNode, ? extends ASTNode> e
                : map.entrySet()) {

            final Cons current = new Cons(
                    e.getKey().unparse(),
                    e.getValue().unparse()
            );
            c = new Cons(current, c);
        }

        return c;
    }

    public static Cons
    mapToAlist(
            Map<? extends ASTNode, ? extends ASTNode> map,
            Function<
                    Map.Entry<
                            ? extends ASTNode,
                            ? extends ASTNode
                            >,
                    ? extends SexpParser.Expr
                    > f
    ) {

        Cons c = null;

        for (Map.Entry<? extends ASTNode, ? extends ASTNode> e
                : map.entrySet()) {

            final SexpParser.Expr current = f.apply(e);

            c = new Cons(current, c);
        }

        return c;
    }
}
