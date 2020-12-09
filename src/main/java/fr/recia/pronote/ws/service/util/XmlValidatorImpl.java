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
package fr.recia.pronote.ws.service.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;

@Slf4j
public class XmlValidatorImpl {

    private final SchemaFactory schemaFactory = SchemaFactory
            .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private Validator validator;

    public XmlValidatorImpl(final File schemaFile) throws SAXException {
        super();
        final Schema schema = schemaFactory.newSchema(schemaFile);
        validator = schema.newValidator();
    }

    public boolean validate(final String xml) throws IOException, SAXException {
        Source xmlSource = new StreamSource(new StringReader(xml));
        try {
            validator.validate(xmlSource);
            return true;
        } catch (SAXException | IOException e) {
            log.error(xmlSource.getSystemId() + " is NOT valid reason:", e);
            throw e;
        }
    }
}
