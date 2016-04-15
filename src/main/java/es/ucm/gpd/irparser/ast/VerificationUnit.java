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

import java.util.List;

/**
 * A verification unit is contained in this class. This is the root block in a
 * verification session.
 *
 * @author Santiago Saavedra
 */
public class VerificationUnit {
    private List<ToplevelDefinition> toplevelForms;
    private List<String> usedPackages;
    private List<String> documentation;
    private List<String> verifyOnly;
    private List<String> assumeVerified;

    public VerificationUnit(List<ToplevelDefinition> toplevelForms, List<String> usedPackages, List<String> documentation, List<String> verifyOnly, List<String> assumeVerified) {
        this.toplevelForms = toplevelForms;
        this.usedPackages = usedPackages;
        this.documentation = documentation;
        this.verifyOnly = verifyOnly;
        this.assumeVerified = assumeVerified;
    }

    public List<ToplevelDefinition> getToplevelForms() {
        return toplevelForms;
    }

    public List<String> getUsedPackages() {
        return usedPackages;
    }

    public List<String> getDocumentation() {
        return documentation;
    }

    public List<String> getVerifyOnly() {
        return verifyOnly;
    }

    public List<String> getAssumeVerified() {
        return assumeVerified;
    }
}