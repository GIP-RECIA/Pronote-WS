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
// Généré le : 2020.06.22 à 05:48:27 PM CEST
//


package fr.recia.pronote.ws.model.conteneurimportchiffre;

import java.io.Serializable;
import java.time.ZonedDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.recia.pronote.ws.model.util.ZonedDateTimeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
 *         &lt;element name="Partenaire" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Cle">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Contenu" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="Verification" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="DateExport" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="UAI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType><PARTENAIRE_INDEX><Nomenclatures><Civilites><Civilite><Ident>2</Ident><Libelle>M</Libelle></Civilite><Civilite><Ident>1</Ident><Libelle>Mme</Libelle></Civilite></Civilites></Nomenclatures><Etablissements><Etablissement><adresse><Adresse1>9 AVENUE DE PARIS</Adresse1><Adresse2/><Adresse3/><Adresse4/><CodePostal>41206</CodePostal><Ville>ROMORANTIN LANTHENAY CEDEX</Ville><Pays/></adresse><Ident>19410017800012</Ident><Numero>0410017W</Numero><Nom>LGT\sCLAUDE DE FRANC\sS</Nom><TelSecretariat/><TelScolarite/><Fax/><EMail/></Etablissement></Etablissements><EtablissementsGeres><Etablissement><Ident>19410017800012</Ident></Etablissement></EtablissementsGeres><Personnels/><Professeurs/><Responsables/><Eleves><eleves/></Eleves><Date><nano>728225000</nano><year>2020</year><monthValue>12</monthValue><dayOfMonth>4</dayOfMonth><hour>15</hour><minute>11</minute><second>50</second><dayOfWeek>FRIDAY</dayOfWeek><dayOfYear>339</dayOfYear><month>DECEMBER</month><chronology><id>ISO</id><calendarType>iso8601</calendarType></chronology></Date><Partenaire>GIPRECIA</Partenaire><ProtocoleDelegationAuthentification>CAS</ProtocoleDelegationAuthentification></PARTENAIRE_INDEX>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", namespace = "http://www.index-education.com/containeurimportchiffreV1", propOrder = {
    "Partenaire",
    "Description",
    "Cle",
    "Contenu",
    "Verification",
    "DateExport",
    "UAI"
})
@XmlRootElement(name = "ImportChiffre", namespace = "http://www.index-education.com/containeurimportchiffreV1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportChiffre implements Serializable{

    @JacksonXmlProperty(localName = "Partenaire")
    @XmlElement(name = "Partenaire", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    protected String partenaire;
    @JacksonXmlProperty(localName = "Description")
    @XmlElement(name = "Description", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    protected String description;
    @JacksonXmlProperty(localName = "Cle")
    @XmlElement(name = "Cle", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    protected String cle;
    @JacksonXmlProperty(localName = "Contenu")
    @XmlElement(name = "Contenu", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    protected String contenu;
    @JacksonXmlProperty(localName = "Verification")
    @XmlElement(name = "Verification", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    protected byte[] verification;
    @JacksonXmlProperty(localName = "DateExport")
    @XmlElement(name = "DateExport", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    @XmlSchemaType(name = "dateTime")
    @XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    protected ZonedDateTime dateExport;
    @JacksonXmlProperty(localName = "UAI")
    @XmlElement(name = "UAI", namespace = "http://www.index-education.com/containeurimportchiffreV1", required = true)
    protected String uai;
    @JacksonXmlProperty(localName = "Version")
    @XmlAttribute(name = "Version", required = true)
    protected String version;


}
