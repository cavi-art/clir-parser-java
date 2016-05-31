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


import es.ucm.gpd.irparser.ast.tld.ToplevelDefinition;

import java.util.Collections;
import java.util.List;

import static es.ucm.sexp.SexpUtils.listToCons;

/**
 * A verification unit is contained in this class. This is the root block in a
 * verification session.
 *
 * @author Santiago Saavedra
 */
public class VerificationUnit {
    private final String packageName;
    private final List<ToplevelDefinition> toplevelForms;
    private final List<String> usedPackages;
    private final String documentation;
    private final List<String> verifyOnly;
    private final List<String> assumeVerified;

    public VerificationUnit(String packageName, List<ToplevelDefinition> toplevelForms, List<String> usedPackages, String documentation, List<String> verifyOnly, List<String> assumeVerified) {
        this.packageName = packageName;
        this.toplevelForms = toplevelForms != null
                ? Collections.unmodifiableList(toplevelForms)
                : null;

        this.usedPackages = Collections.unmodifiableList(usedPackages);
        this.documentation = documentation;
        this.verifyOnly = Collections.unmodifiableList(verifyOnly);
        this.assumeVerified = Collections.unmodifiableList(assumeVerified);
    }

    public String getPackageName() {
        return packageName;
    }

    public List<ToplevelDefinition> getToplevelForms() {
        return toplevelForms;
    }

    public List<String> getUsedPackages() {
        return usedPackages;
    }

    public String getDocumentation() {
        return documentation;
    }

    public List<String> getVerifyOnly() {
        return verifyOnly;
    }

    public List<String> getAssumeVerified() {
        return assumeVerified;
    }

    public String toString() {
        StringBuilder joinedToplevelForms = new StringBuilder();

        for (ToplevelDefinition d : toplevelForms) {
            joinedToplevelForms.append(d.toString());
            joinedToplevelForms.append("\n");
        }

        return String.format("(verification-unit %s \n" +
                        "  :uses %s \n" +
                        "  :documentation \"%s\" \n" +
                        "  :verify-only %s\n" +
                        "  :assume-verified %s)\n" +
                        "%s",
                packageName,
                listToCons(usedPackages),
                documentation,
                listToCons(verifyOnly),
                listToCons(assumeVerified),
                joinedToplevelForms.toString())
                ;
    }

}