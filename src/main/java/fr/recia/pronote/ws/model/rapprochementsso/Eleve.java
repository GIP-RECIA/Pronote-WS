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
package fr.recia.pronote.ws.model.rapprochementsso;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.recia.pronote.ws.service.bean.Sexe;
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
 *         &lt;element name="Responsable" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *       &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Prenom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DateNaissance" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="Sexe">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Masculin"/>
 *             &lt;enumeration value="Feminin"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ID_Partenaire" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}TypeIdentifiantPartenaire" />
 *       &lt;attribute name="NumeroNational" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "responsable"
})
public class Eleve {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Responsable", namespace = "http://www.index-education.com/rapprochementssoV2.0")
    protected List<Responsable> responsable = new ArrayList<>();
    @JacksonXmlProperty(localName = "Ident", isAttribute = true)
    protected long ident;
    @JacksonXmlProperty(localName = "Nom", isAttribute = true)
    protected String nom;
    @JacksonXmlProperty(localName = "Prenom", isAttribute = true)
    protected String prenom;
    @JacksonXmlProperty(localName = "DateNaissance", isAttribute = true)
    protected String dateNaissance;
    @JacksonXmlProperty(localName = "Sexe", isAttribute = true)
    protected Sexe sexe;
    @JacksonXmlProperty(localName = "ID_Partenaire", isAttribute = true)
    protected String idPartenaire;
    @JacksonXmlProperty(localName = "NumeroNational", isAttribute = true)
    protected String numeroNational;
    @JacksonXmlProperty(localName = "Adresse1", isAttribute = true)
    protected String adresse1;
    @JacksonXmlProperty(localName = "Adresse2", isAttribute = true)
    protected String adresse2;
    @JacksonXmlProperty(localName = "Adresse3", isAttribute = true)
    protected String adresse3;
    @JacksonXmlProperty(localName = "Adresse4", isAttribute = true)
    protected String adresse4;
    @JacksonXmlProperty(localName = "CodePostal", isAttribute = true)
    protected String codePostal;
    @JacksonXmlProperty(localName = "Ville", isAttribute = true)
    protected String ville;
    @JacksonXmlProperty(localName = "Pays", isAttribute = true)
    protected String pays;

    /**
     * <p>Classe Java pour anonymous complex type.
     *
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Responsable {

        @JacksonXmlProperty(localName = "Ident", isAttribute = true)
        protected long ident;

    }

}
