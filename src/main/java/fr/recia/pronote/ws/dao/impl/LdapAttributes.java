/**
 * Copyright (C) 2020 GIP-RECIA https://www.recia.fr/
 * @Author (C) 2020 Julien Gribonvald <julien.gribonvald@recia.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *                 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.recia.pronote.ws.dao.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LdapAttributes {
    public static final String POSTAL_CODE = "postalCode";
    public static final String VILLE = "l";
    public static final String STREET = "street";

    public static final String ENTSTRUCTURE_IDENT_ATTRIBUTE = "ENTStructureSIREN";
    public static final String ENTSTRUCTURE_UAI = "ENTStructureUAI";
    public static final String ENTSTRUCTURE_NOM_COURANT = "ENTStructureNomCourant";
    public static final String UID = "uid";
    public static final String SN = "sn";
    public static final String GIVEN_NAME = "givenName";
    public static final String ENT_PERSON_ADRESSE = "ENTPersonAdresse";
    public static final String ENT_PERSON_CODE_POSTAL = "ENTPersonCodePostal";
    public static final String ENT_PERSON_VILLE = "ENTPersonVille";
    public static final String ENT_PERSON_PAYS = "ENTPersonPays";
    public static final String ENT_PERSON_DATE_NAISSANCE = "ENTPersonDateNaissance";
    public static final String ENT_ELEVE_PERS_REL_ELEVE = "ENTElevePersRelEleve";
    public static final String PERSONAL_TITLE = "personalTitle";
    public static final String ESCO_PERSON_EXTERNAL_IDS = "ESCOPersonExternalIds";

    public static final Set<String> STRUCTURE_ATTRS =
            Stream.of(POSTAL_CODE, VILLE, STREET, ENTSTRUCTURE_IDENT_ATTRIBUTE, ENTSTRUCTURE_NOM_COURANT, ENTSTRUCTURE_UAI).collect(Collectors.toSet());

    public static final Set<String> PERSON_ATTRS =
            Stream.of(UID, SN, GIVEN_NAME, PERSONAL_TITLE, ENT_PERSON_CODE_POSTAL, ENT_PERSON_VILLE, ENT_PERSON_ADRESSE, ENT_PERSON_DATE_NAISSANCE, ENT_PERSON_PAYS, ENT_ELEVE_PERS_REL_ELEVE, ESCO_PERSON_EXTERNAL_IDS)
                    .collect(Collectors.toSet());
}
