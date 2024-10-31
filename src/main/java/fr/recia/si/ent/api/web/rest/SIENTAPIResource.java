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
package fr.recia.si.ent.api.web.rest;

import fr.recia.si.ent.api.config.bean.ApiEndpoints;
import fr.recia.si.ent.api.service.SIENTAPIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class SIENTAPIResource {

	@Autowired
	private SIENTAPIService sientapiService;

	@RequestMapping(value = "/" + ApiEndpoints.CHILDRENS_OF + "/{externalId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Map<String,Object>>> getChildrensOf(@PathVariable String externalId, HttpServletResponse response) {
		log.info("Requesting childrens of {}", externalId);
		try {
			return new ResponseEntity<List<Map<String,Object>>>(sientapiService.getChildrensOf(externalId), HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			log.error(String.format(ApiEndpoints.CHILDRENS_OF + " - Personne '%s' non trouvée", externalId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(ApiEndpoints.CHILDRENS_OF + " - ", e);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/" + ApiEndpoints.USER_INFO + "/{externalId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String,Object>> getUserInfos(@PathVariable String externalId, HttpServletResponse response) {
		log.info("Requesting User Infos of {}", externalId);
		try {
			return new ResponseEntity<Map<String,Object>>(sientapiService.getUserInfos(externalId), HttpStatus.OK);
		} catch (EmptyResultDataAccessException ex) {
			log.error(String.format(ApiEndpoints.USER_INFO + " - Personne '%s' non trouvée", externalId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(ApiEndpoints.USER_INFO + " - ", e);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
