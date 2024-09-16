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
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
 *         &lt;element name="Etablissement" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="Ident" use="required" type="{http://www.index-education.com/rapprochementssoV2.0}IDENT" />
 *                 &lt;attribute name="Numero" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Nom" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Adresse1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Adresse2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Adresse3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Adresse4" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="CodePostal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Ville" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Pays" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="TelSecretariat" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="TelScolarite" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Fax" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="EMail" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
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
    "etablissement"
})
public class Etablissements {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Etablissement")
    @XmlElement(name = "Etablissement", namespace = "http://www.index-education.com/rapprochementssoV2.0", required = true)
    protected List<Etablissement> etablissements = new ArrayList<>();

}
