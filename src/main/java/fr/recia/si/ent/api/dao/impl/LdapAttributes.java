/*
 * Copyright (C) 2024 GIP-RECIA https://www.recia.fr/
 * @Author (C) 2024 Julien Gribonvald <julien.gribonvald@recia.fr>
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
package fr.recia.si.ent.api.dao.impl;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LdapAttributes {

    public static final String ENTSTRUCTURE_IDENT_ATTRIBUTE = "ENTStructureSIREN";
    public static final String ENTSTRUCTURE_UAI = "ENTStructureUAI";



    public static final String ENTPERSON_DN = "distinguishedName";
    public static final String ENTPERSON_UID = "uid";
    public static final String ENTPERSON_GIVENNAME = "givenName";
    public static final String ENTPERSON_ISMEMBEROF = "isMemberOf";
    public static final String ENTPERSON_MAIL = "mail";
    public static final String ENTPERSON_PERSONALTITLE = "personalTitle";
    public static final String ENTPERSON_SN = "sn";
    public static final String ENTPERSON_CN = "cn";
    public static final String ENTPERSON_DISPLAYNAME = "displayName";
    public static final String ENTPERSON_ENTAUXENSCATEGODISCIPLINE = "ENTAuxEnsCategoDiscipline";
    public static final String ENTPERSON_ENTAUXENSGROUPES = "ENTAuxEnsGroupes";
    public static final String ENTPERSON_ENTAUXENSGROUPESMATIERES = "ENTAuxEnsGroupesMatieres";
    public static final String ENTPERSON_ENTAUXENSMATIEREENSEIGNETAB = "ENTAuxEnsMatiereEnseignEtab";
    public static final String ENTPERSON_ENTAUXENSMEF = "ENTAuxEnsMEF";
    public static final String ENTPERSON_ENTAUXNONENSCOLLLOCSERVICE = "ENTAuxNonEnsCollLocService";
    public static final String ENTPERSON_ENTELEVEADRESSEREL = "ENTEleveAdresseRel";
    public static final String ENTPERSON_ENTELEVECLASSES = "ENTEleveClasses";
    public static final String ENTPERSON_ENTELEVECODEENSEIGNEMENTS = "ENTEleveCodeEnseignements";
    public static final String ENTPERSON_ENTELEVEENSEIGNEMENTS = "ENTEleveEnseignements";
    public static final String ENTPERSON_ENTELEVEFILIERE = "ENTEleveFiliere";
    public static final String ENTPERSON_ENTELEVEGROUPES = "ENTEleveGroupes";
    public static final String ENTPERSON_ENTELEVELIBELLEMEF = "ENTEleveLibelleMEF";
    public static final String ENTPERSON_ENTELEVEMAJEUR = "ENTEleveMajeur";
    public static final String ENTPERSON_ENTELEVEMEF = "ENTEleveMEF";
    public static final String ENTPERSON_ENTELEVENIVFORMATION = "ENTEleveNivFormation";
    public static final String ENTPERSON_ENTELEVEPERSRELELEVE = "ENTElevePersRelEleve";
    public static final String ENTPERSON_ENTELEVEREGIME = "ENTEleveRegime";
    public static final String ENTPERSON_ENTELEVESTATUTELEVE = "ENTEleveStatutEleve";
    public static final String ENTPERSON_ENTELEVETRANSPORT = "ENTEleveTransport";
    public static final String ENTPERSON_ENTPERSONADRESSEDIFFUSION = "ENTPersonAdresseDiffusion";
    public static final String ENTPERSON_ENTPERSONAUTRESMAILS = "ENTPersonAutresMails";
    public static final String ENTPERSON_ENTPERSONAUTRESPRENOMS = "ENTPersonAutresPrenoms";
    public static final String ENTPERSON_ENTPERSONCODEPOSTAL = "ENTPersonCodePostal";
    public static final String ENTPERSON_ENTPERSONDATENAISSANCE = "ENTPersonDateNaissance";
    public static final String ENTPERSON_ENTPERSONFONCTIONS = "ENTPersonFonctions";
    public static final String ENTPERSON_ENTPERSONGARIDENTIFIANT = "ENTPersonGARIdentifiant";
    public static final String ENTPERSON_ENTPERSONNOMPATRO = "ENTPersonNomPatro";
    public static final String ENTPERSON_ENTPERSONPAYS = "ENTPersonPays";
    public static final String ENTPERSON_ENTPERSONPROFILS = "ENTPersonProfils";
    public static final String ENTPERSON_ENTPERSONSEXE = "ENTPersonSexe";
    public static final String ENTPERSON_ENTPERSONSTRUCTRATTACH = "ENTPersonStructRattach";
    public static final String ENTPERSON_ENTPERSONVILLE = "ENTPersonVille";
    public static final String ENTPERSON_ESCOAUXENSCODEMATIEREENSEIGNETAB = "ESCOAuxEnsCodeMatiereEnseignEtab";
    public static final String ENTPERSON_ESCODOMAINES = "ESCODomaines";
    public static final String ENTPERSON_ESCOELEVECODEENSEIGNEMENTS = "ESCOEleveCodeEnseignements";
    public static final String ENTPERSON_ESCOPERSONETATCOMPTE = "ESCOPersonEtatCompte";
    public static final String ENTPERSON_ESCOPERSONEXTERNALIDS = "ESCOPersonExternalIds";
    public static final String ENTPERSON_ESCOPERSONPROFILS = "ESCOPersonProfils";
    public static final String ENTPERSON_ESCOSIREN = "ESCOSIREN";
    public static final String ENTPERSON_ESCOSIRENCOURANT = "ESCOSIRENCourant";
    public static final String ENTPERSON_ESCOUAI = "ESCOUAI";
    public static final String ENTPERSON_ESCOUAICOURANT = "ESCOUAICourant";
    public static final String ENTPERSON_ESCOUAIRATTACHEMENT = "ESCOUAIRattachement";


    public static final Set<String> STRUCTURE_ATTRS =
            Stream.of(ENTSTRUCTURE_IDENT_ATTRIBUTE, ENTSTRUCTURE_UAI).collect(Collectors.toSet());

    public static final Set<String> PERSON_ATTRS =
            Stream.of(ENTPERSON_DN, ENTPERSON_UID, ENTPERSON_GIVENNAME, ENTPERSON_ISMEMBEROF, ENTPERSON_MAIL, ENTPERSON_PERSONALTITLE,
                            ENTPERSON_SN, ENTPERSON_CN, ENTPERSON_DISPLAYNAME, ENTPERSON_ENTAUXENSCATEGODISCIPLINE, ENTPERSON_ENTAUXENSGROUPES,
                            ENTPERSON_ENTAUXENSGROUPESMATIERES, ENTPERSON_ENTAUXENSMATIEREENSEIGNETAB, ENTPERSON_ENTAUXENSMEF,
                            ENTPERSON_ENTAUXNONENSCOLLLOCSERVICE, ENTPERSON_ENTELEVEADRESSEREL, ENTPERSON_ENTELEVECLASSES,
                            ENTPERSON_ENTELEVECODEENSEIGNEMENTS, ENTPERSON_ENTELEVEENSEIGNEMENTS, ENTPERSON_ENTELEVEFILIERE,
                            ENTPERSON_ENTELEVEGROUPES, ENTPERSON_ENTELEVELIBELLEMEF, ENTPERSON_ENTELEVEMAJEUR, ENTPERSON_ENTELEVEMEF,
                            ENTPERSON_ENTELEVENIVFORMATION, ENTPERSON_ENTELEVEPERSRELELEVE, ENTPERSON_ENTELEVEREGIME,
                            ENTPERSON_ENTELEVESTATUTELEVE, ENTPERSON_ENTELEVETRANSPORT, ENTPERSON_ENTPERSONADRESSEDIFFUSION,
                            ENTPERSON_ENTPERSONAUTRESMAILS, ENTPERSON_ENTPERSONAUTRESPRENOMS, ENTPERSON_ENTPERSONCODEPOSTAL,
                            ENTPERSON_ENTPERSONDATENAISSANCE, ENTPERSON_ENTPERSONFONCTIONS, ENTPERSON_ENTPERSONGARIDENTIFIANT,
                            ENTPERSON_ENTPERSONNOMPATRO, ENTPERSON_ENTPERSONPAYS, ENTPERSON_ENTPERSONPROFILS, ENTPERSON_ENTPERSONSEXE,
                            ENTPERSON_ENTPERSONSTRUCTRATTACH, ENTPERSON_ENTPERSONVILLE, ENTPERSON_ESCOAUXENSCODEMATIEREENSEIGNETAB,
                            ENTPERSON_ESCODOMAINES, ENTPERSON_ESCOELEVECODEENSEIGNEMENTS, ENTPERSON_ESCOPERSONETATCOMPTE,
                            ENTPERSON_ESCOPERSONEXTERNALIDS, ENTPERSON_ESCOPERSONPROFILS, ENTPERSON_ESCOSIREN, ENTPERSON_ESCOSIRENCOURANT,
                            ENTPERSON_ESCOUAI, ENTPERSON_ESCOUAICOURANT, ENTPERSON_ESCOUAIRATTACHEMENT)
                    .collect(Collectors.toSet());

    public static final Set<String> ALL_ATTRS = Stream.concat(PERSON_ATTRS.stream(), STRUCTURE_ATTRS.stream()).collect(Collectors.toSet());
    public static final Set<String> PERSON_IDS_ATTRS = Stream.of(ENTPERSON_DN, ENTPERSON_UID, ENTPERSON_ESCOPERSONEXTERNALIDS, ENTPERSON_ENTPERSONGARIDENTIFIANT).collect(Collectors.toSet());

    public static final Set<String> MULTIVALUED_PERSON_ATTRs =
            Stream.of(ENTPERSON_ISMEMBEROF, ENTPERSON_ENTAUXENSCATEGODISCIPLINE, ENTPERSON_ENTAUXENSGROUPES, ENTPERSON_ENTAUXENSGROUPESMATIERES,
                            ENTPERSON_ENTAUXENSMATIEREENSEIGNETAB, ENTPERSON_ENTAUXENSMEF,
                            ENTPERSON_ENTAUXNONENSCOLLLOCSERVICE, ENTPERSON_ENTELEVEADRESSEREL, ENTPERSON_ENTELEVECLASSES,
                            ENTPERSON_ENTELEVECODEENSEIGNEMENTS, ENTPERSON_ENTELEVEENSEIGNEMENTS, ENTPERSON_ENTELEVEGROUPES,
                            ENTPERSON_ENTELEVEPERSRELELEVE, ENTPERSON_ENTPERSONAUTRESMAILS, ENTPERSON_ENTPERSONAUTRESPRENOMS,
                            ENTPERSON_ENTPERSONFONCTIONS, ENTPERSON_ENTPERSONPROFILS, ENTPERSON_ESCOAUXENSCODEMATIEREENSEIGNETAB,
                            ENTPERSON_ESCODOMAINES, ENTPERSON_ESCOELEVECODEENSEIGNEMENTS, ENTPERSON_ESCOPERSONEXTERNALIDS,
                            ENTPERSON_ESCOPERSONPROFILS, ENTPERSON_ESCOSIREN, ENTPERSON_ESCOUAI)
                    .collect(Collectors.toSet());

    public static final Set<String> MONOVALUED_PERSON_ATTRs =
            Stream.of(ENTPERSON_DN, ENTPERSON_UID, ENTPERSON_GIVENNAME, ENTPERSON_MAIL, ENTPERSON_PERSONALTITLE,
                            ENTPERSON_SN, ENTPERSON_CN, ENTPERSON_DISPLAYNAME, ENTPERSON_ENTELEVEFILIERE, ENTPERSON_ENTELEVELIBELLEMEF,
                            ENTPERSON_ENTELEVEMAJEUR, ENTPERSON_ENTELEVEMEF, ENTPERSON_ENTELEVENIVFORMATION, ENTPERSON_ENTELEVEREGIME,
                            ENTPERSON_ENTELEVESTATUTELEVE, ENTPERSON_ENTELEVETRANSPORT, ENTPERSON_ENTPERSONADRESSEDIFFUSION,
                            ENTPERSON_ENTPERSONCODEPOSTAL, ENTPERSON_ENTPERSONDATENAISSANCE, ENTPERSON_ENTPERSONGARIDENTIFIANT,
                            ENTPERSON_ENTPERSONNOMPATRO, ENTPERSON_ENTPERSONPAYS, ENTPERSON_ENTPERSONSEXE,
                            ENTPERSON_ENTPERSONSTRUCTRATTACH, ENTPERSON_ENTPERSONVILLE,ENTPERSON_ESCOPERSONETATCOMPTE, ENTPERSON_ESCOSIRENCOURANT,
                            ENTPERSON_ESCOUAICOURANT, ENTPERSON_ESCOUAIRATTACHEMENT)
                    .collect(Collectors.toSet());
}
