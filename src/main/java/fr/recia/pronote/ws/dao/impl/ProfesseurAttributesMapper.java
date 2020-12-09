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

import fr.recia.pronote.ws.model.rapprochementsso.Professeur;
import fr.recia.pronote.ws.service.bean.IExtractOpaqueId;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;

@Data
@AllArgsConstructor
@Slf4j
public class ProfesseurAttributesMapper implements ContextMapper<Professeur> {

	@NotNull
	private IIDMapper userMapper;

	@NotNull
	private IExtractOpaqueId extractOpaqueId;


	@Override
	public Professeur mapFromContext(Object ctx) throws NamingException {
		Professeur professeur = new Professeur();

		DirContextAdapter context = (DirContextAdapter) ctx;

		final String uid = context.getStringAttribute(LdapAttributes.UID);
		log.debug("Ldap to Object Mapping for uid '{}'", uid);
		Assert.hasText(uid, "L'uid ne peut pas être vide !");
		professeur.setIdent(userMapper.getPronoteID(uid));
		Assert.isTrue(professeur.getIdent() >= 0, "Le pronote Id ne peut pas être vide !");

		professeur.setIdPartenaire(extractOpaqueId.getOpaqueId(context));
		Assert.hasText(professeur.getIdPartenaire(), "L'id Opaque ne peut être vide !");

		professeur.setNom(context.getStringAttribute(LdapAttributes.SN));
		Assert.hasText(professeur.getNom(), "Le sn ne peut être vide !");
		professeur.setPrenom(context.getStringAttribute(LdapAttributes.GIVEN_NAME));
		Assert.hasText(professeur.getNom(), "Le givenName ne peut être vide !");

		return professeur;
	}

}
