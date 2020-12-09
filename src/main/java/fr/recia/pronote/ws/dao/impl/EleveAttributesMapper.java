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
/**
 *
 */
package fr.recia.pronote.ws.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.validation.constraints.NotNull;

import fr.recia.pronote.ws.model.rapprochementsso.Eleve;
import fr.recia.pronote.ws.service.bean.IExtractOpaqueId;
import fr.recia.pronote.ws.service.bean.IExtractUIDFromDN;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import fr.recia.pronote.ws.service.exception.NotFoundIdException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;

@Data
@AllArgsConstructor
@Slf4j
public class EleveAttributesMapper implements ContextMapper<Eleve> {

	@NotNull
	private IIDMapper userMapper;

	@NotNull
	private IExtractOpaqueId extractOpaqueId;

	@NotNull
	private IExtractUIDFromDN extractUIDFromDN;

	@NotNull
	private Pattern responsablePattern;

	@Override
	public Eleve mapFromContext(Object ctx) throws NamingException {
		Eleve eleve = new Eleve();

		DirContextAdapter context = (DirContextAdapter) ctx;

		final String uid = context.getStringAttribute(LdapAttributes.UID);
		log.debug("Ldap to Object Mapping for uid '{}'", uid);
		Assert.hasText(uid, "L'uid ne peut pas être vide !");
		eleve.setIdent(userMapper.getPronoteID(uid));
		Assert.isTrue(eleve.getIdent() >= 0, "Le pronote Id ne peut pas être vide !");

		eleve.setIdPartenaire(extractOpaqueId.getOpaqueId(context));
		Assert.hasText(eleve.getIdPartenaire(), "L'id Opaque ne peut être vide !");

		eleve.setNom(context.getStringAttribute(LdapAttributes.SN));
		Assert.hasText(eleve.getNom(), "Le sn ne peut être vide !");
		eleve.setPrenom(context.getStringAttribute(LdapAttributes.GIVEN_NAME));
		Assert.hasText(eleve.getNom(), "Le givenName ne peut être vide !");

		String date = context.getStringAttribute(LdapAttributes.ENT_PERSON_DATE_NAISSANCE);
		Assert.hasText(date, "La date de naissance ne peut être vide");
		SimpleDateFormat formatterFrom = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatterTo = new SimpleDateFormat("yyyy-MM-dd");
		try {
			eleve.setDateNaissance(formatterTo.format(formatterFrom.parse(date)));
		} catch (ParseException e) {
			log.error("Impossible de parser la date", e);
		}

		final String[] parents = context.getStringAttributes(LdapAttributes.ENT_ELEVE_PERS_REL_ELEVE);
		List<Eleve.Responsable> resp = new ArrayList<>();
		if (parents != null) {
			for (String parent : parents) {
				if (responsablePattern.matcher(parent).matches()) {
					final String parentUid = extractUIDFromDN.getUidFromDN(parent);
					try {
						resp.add(new Eleve.Responsable(userMapper.findPronoteID(parentUid)));
					} catch (NotFoundIdException e) {
						log.warn("Personne en relation avec l'uid {} non trouvée dans les références", parentUid);
					}
				}
			}
		}
		eleve.setResponsable(resp);

		/*
		eleve.setCodePostal(context.getStringAttribute(ENT_PERSON_POSTAL_CODE));
		eleve.setVille(context.getStringAttribute(ENT_PERSON_VILLE));
		final String adresse = (context.getStringAttribute(LdapAttributes.ENT_PERSON_ADRESSE));
		if (StringUtils.hasText(adresse)) {
			final String[] addr = adresse.split("\\$");
			eleve.setAdresse1(addr[0]);
			if (addr.length > 1)
				eleve.setAdresse2(addr[1]);
			if (addr.length > 2)
				eleve.setAdresse3(addr[2]);
			if (addr.length > 3)
				eleve.setAdresse4(addr[3]);
		}
		eleve.setPays(context.getStringAttribute(ENT_PERSON_PAYS));
		eleve.setSexe();
		*/

		return eleve;
	}

}
