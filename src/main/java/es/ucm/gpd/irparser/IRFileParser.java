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

package es.ucm.gpd.irparser;

import es.ucm.gpd.irparser.ast.VerificationUnit;
import es.ucm.sexp.SexpFileParser;
import es.ucm.sexp.SexpParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static es.ucm.gpd.irparser.Sexp2Ast.parseVU;

/**
 * This is the IR File Parser as implemented to return directly the AST in the
 * form of toplevel definitions.
 *
 * @author Santiago Saavedra
 * @see #parseAllToplevel()
 */
public class IRFileParser implements Iterator<VerificationUnit> {
    private final SexpFileParser sexp;

    public IRFileParser(InputStream in) {
        this(new SexpFileParser(in));
    }

    public IRFileParser(SexpFileParser sexp) {
        this.sexp = sexp;
    }


    public static void main(String[] args) {
        InputStream data = System.in;

        if (args.length > 0) {
            if (args[0].equals("--help")) {
                usage();
                return;
            }

            if (!args[0].equals("-")) {
                try {
                    data = new FileInputStream(args[0]);
                } catch (FileNotFoundException e) {
                    System.err.printf("File %s was not found", args[0]);
                    System.exit(1);
                }
            }
        }

        try {
            data = new FileInputStream
                    ("../cl-vc-gen/test/qsort.clir");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<VerificationUnit> file = new IRFileParser(data).parseAllToplevel();

        System.out.println(file);

    }

    private static void usage() {
        System.out.println("Usage: java -jar IRFileParser file.clir");
    }

    private List<VerificationUnit> parseAllToplevel() {
        List<VerificationUnit> toplevel = new ArrayList<>();
        while (sexp.hasNext()) {
            toplevel.add(next());
        }
        return toplevel;
    }

    @Override
    public boolean hasNext() {
        return sexp.hasNext();
    }

    @Override
    public VerificationUnit next() {
        try {
            return parseVU(sexp);
        } catch (SexpParser.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
