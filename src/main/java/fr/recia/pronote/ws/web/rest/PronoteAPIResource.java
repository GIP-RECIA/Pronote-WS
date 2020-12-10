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
package fr.recia.pronote.ws.web.rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import fr.recia.pronote.ws.model.conteneurimportchiffre.ImportChiffre;
import fr.recia.pronote.ws.service.PronoteExportService;
import fr.recia.pronote.ws.service.util.XmlValidatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class PronoteAPIResource {

	@Autowired
	private File debugDataPath;

	@Autowired
	private PronoteExportService pronoteExportService;

	@Autowired
	private File importChiffreXSD;

	private XmlValidatorImpl xmlValidator;

	@PostConstruct
	public void setUp() throws SAXException {
		xmlValidator = new XmlValidatorImpl(importChiffreXSD);
	}

	@RequestMapping(value = "/export/{uai}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<ImportChiffre> getXMLPronoteExport(@PathVariable String uai, HttpServletResponse response) {
		log.debug("Requesting Import Chiffré Pronote of etablissement {}", uai);
		try {
			return new ResponseEntity<ImportChiffre>(pronoteExportService.getPronoteExport(uai), HttpStatus.OK);
		} catch (IOException e) {
			log.error(String.format("Can't process ImportChiffre for %s", uai), e);
		} catch (EmptyResultDataAccessException ex) {
			log.error(String.format("Etablissement '%s' non trouvé", uai), ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NoSuchAlgorithmException ex) {
			log.error("Impossible de générer le fichier un algorithme n'existe pas", ex);
		} catch (SAXException ex) {
			log.error("Le flux xml est mal formé", ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/download/{uai}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getFilePronoteExport(@PathVariable String uai, HttpServletResponse response) {
		log.debug("Requesting Import Chiffré Pronote of etablissement {}", uai);
		try {
			ImportChiffre ic = pronoteExportService.getPronoteExport(uai);

			// set XmlMapper to transform object to XML
			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.registerModule(new JaxbAnnotationModule());
			xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true );

			final String xmlContent = xmlMapper.writeValueAsString(ic);

			if (log.isDebugEnabled()) {
				logIntoFileXmlContent(xmlContent, uai);
			}
			log.trace("Export ImportChiffre {}", xmlContent);

			// validating xml
			xmlValidator.validate(xmlContent);

			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_XML)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + uai +".xml\"")
					.body(xmlContent.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			log.error(String.format("Can't process ImportChiffre for %s", uai), e);
		} catch (EmptyResultDataAccessException ex) {
			log.error(String.format("Etablissement '%s' non trouvé", uai), ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NoSuchAlgorithmException ex) {
			log.error("Impossible de générer le fichier un algorithme n'existe pas", ex);
		} catch (SAXException ex) {
			log.error("Le flux xml est mal formé", ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void logIntoFileXmlContent(final String xmlContent, final String idEtablissement){
		final String fileLog = String.format("/ResultPronoteEncryptedFor-%s-%s.xml", idEtablissement,
				Instant.now().truncatedTo(ChronoUnit.SECONDS).atZone(ZoneId.systemDefault())
						.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		try {
			FileOutputStream outputStream = new FileOutputStream(debugDataPath.getPath() + fileLog);
			byte[] strToBytes = xmlContent.getBytes(StandardCharsets.UTF_8);
			outputStream.write(strToBytes);
			outputStream.close();
		} catch (FileNotFoundException ex) {
			log.error("Impossible d'écrire dans le fichier {}", fileLog, ex);
		} catch (IOException ex) {
			log.error("Impossible d'écrire dans le fichier IO exception", ex);
		}
	}
}
