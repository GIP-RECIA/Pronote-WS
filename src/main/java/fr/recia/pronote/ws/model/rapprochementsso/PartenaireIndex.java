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
//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source.
// Généré le : 2020.06.22 à 05:46:06 PM CEST
//


package fr.recia.pronote.ws.model.rapprochementsso;

import java.time.ZonedDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import fr.recia.pronote.ws.model.util.ZonedDateTimeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


/**
 * <p>Classe Java pour anonymous complex type.
 *
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Nomenclatures" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Civilites" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Civilite" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                                     &lt;attribute name="Libelle" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Etablissements">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Etablissement" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                           &lt;attribute name="Numero" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="TelSecretariat" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="TelScolarite" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="EMail" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="EtablissementsGeres">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Etablissement" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Personnels" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Personnel" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                           &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Prenom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ID_Partenaire" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}TypeIdentifiantPartenaire" />
 *                           &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Professeurs" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Professeur" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                           &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Prenom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="DateNaissance" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                           &lt;attribute name="ID_Partenaire" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}TypeIdentifiantPartenaire" />
 *                           &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Responsables" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Responsable" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Civilite" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                           &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Prenom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ID_Partenaire" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}TypeIdentifiantPartenaire" />
 *                           &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Eleves" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Eleve" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Responsable" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                           &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Prenom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="DateNaissance" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *                           &lt;attribute name="Sexe">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="Masculin"/>
 *                                 &lt;enumeration value="Feminin"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="ID_Partenaire" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}TypeIdentifiantPartenaire" />
 *                           &lt;attribute name="NumeroNational" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Date" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Partenaire" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ProtocoleDelegationAuthentification">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="CAS"/>
 *             &lt;enumeration value="ADFS"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://www.index-education.com/rapprochementssoV2.0",
        propOrder = {
    "Nomenclatures",
    "Etablissements",
    "EtablissementsGeres",
    "Personnels",
    "Professeurs",
    "Responsables",
    "Eleves"
})
@JacksonXmlRootElement(localName = "PARTENAIRE_INDEX", namespace = "http://www.index-education.com/rapprochementssoV2.0")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartenaireIndex {

    @JacksonXmlProperty( localName = "Nomenclatures", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected Nomenclatures nomenclatures;
    @JacksonXmlProperty( localName = "Etablissements", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected Etablissements etablissements;
    @JacksonXmlProperty( localName = "EtablissementsGeres", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected EtablissementsGeres etablissementsGeres;
    @JacksonXmlProperty( localName = "Personnels", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected Personnels personnels;
    @JacksonXmlProperty( localName = "Professeurs", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected Professeurs professeurs;
    @JacksonXmlProperty( localName = "Responsables", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected Responsables responsables;
    @JacksonXmlProperty( localName = "Eleves", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected Eleves eleves;
    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    protected ZonedDateTime date;
    @JacksonXmlProperty( localName = "Partenaire", isAttribute = true)
    protected String partenaire;
    @JacksonXmlProperty( localName = "ProtocoleDelegationAuthentification", isAttribute = true)
    protected String protocoleDelegationAuthentification;


}
