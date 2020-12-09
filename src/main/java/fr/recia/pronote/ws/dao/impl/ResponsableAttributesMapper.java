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

import javax.naming.NamingException;
import javax.validation.constraints.NotNull;

import fr.recia.pronote.ws.model.rapprochementsso.ObjectFactory;
import fr.recia.pronote.ws.model.rapprochementsso.Responsable;
import fr.recia.pronote.ws.service.bean.Civilite;
import fr.recia.pronote.ws.service.bean.IExtractOpaqueId;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@Slf4j
public class ResponsableAttributesMapper implements ContextMapper<Responsable> {

	@NotNull
	private IIDMapper userMapper;

	@NotNull
	private IExtractOpaqueId extractOpaqueId;

	private static final ObjectFactory objectFactory = new ObjectFactory();


	@Override
	public Responsable mapFromContext(Object ctx) throws NamingException {
		Responsable responsable = new Responsable();

		DirContextAdapter context = (DirContextAdapter) ctx;

		final String uid = context.getStringAttribute(LdapAttributes.UID);
		log.debug("Ldap to Object Mapping for uid '{}'", uid);
		Assert.hasText(uid, "L'uid ne peut pas être vide !");
		responsable.setIdent(userMapper.getPronoteID(uid));
		Assert.isTrue(responsable.getIdent() >= 0, "Le pronote Id ne peut pas être vide !");

		responsable.setIdPartenaire(extractOpaqueId.getOpaqueId(context));
		Assert.hasText(responsable.getIdPartenaire(), "L'id Opaque ne peut être vide !");

		responsable.setNom(context.getStringAttribute(LdapAttributes.SN));
		Assert.hasText(responsable.getNom(), "Le sn ne peut être vide !");
		responsable.setPrenom(context.getStringAttribute(LdapAttributes.GIVEN_NAME));
		Assert.hasText(responsable.getNom(), "Le givenName ne peut être vide !");

		final String civilite = context.getStringAttribute(LdapAttributes.PERSONAL_TITLE);
		if (StringUtils.hasText(civilite)) {
			responsable.setCivilite(new Responsable.Civilite(Civilite.valueOf(civilite).getCode()));
		} else {
			responsable.setCivilite(new Responsable.Civilite(Civilite.undefined.getCode()));
		}
		responsable.setCodePostal(context.getStringAttribute(LdapAttributes.ENT_PERSON_CODE_POSTAL));
		responsable.setVille(context.getStringAttribute(LdapAttributes.ENT_PERSON_VILLE));
		responsable.setPays(context.getStringAttribute(LdapAttributes.ENT_PERSON_PAYS));
		final String adresse = (context.getStringAttribute(LdapAttributes.ENT_PERSON_ADRESSE));
		if (StringUtils.hasText(adresse)) {
			final String[] addr = adresse.split("\\$");
			responsable.setAdresse1(addr[0]);
			if (addr.length > 1)
				responsable.setAdresse2(addr[1]);
			if (addr.length > 2)
				responsable.setAdresse3(addr[2]);
			if (addr.length > 3)
				responsable.setAdresse4(addr[3]);
		}

		return responsable;
	}

}
