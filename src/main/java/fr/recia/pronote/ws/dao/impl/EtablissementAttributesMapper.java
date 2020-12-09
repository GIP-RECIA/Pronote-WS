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

import java.util.List;

import javax.naming.NamingException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.recia.pronote.ws.model.rapprochementsso.Etablissement;
import fr.recia.pronote.ws.model.rapprochementsso.ObjectFactory;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import fr.recia.pronote.ws.service.bean.IStringCleaner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;

@Data
@AllArgsConstructor
@Slf4j
public class EtablissementAttributesMapper implements ContextMapper<Etablissement> {

	@NotNull
	private IIDMapper structMapper;

	@NotEmpty
	private List<IStringCleaner> structNameCleaner;

	private static final ObjectFactory objectFactory = new ObjectFactory();

	@Override
	public Etablissement mapFromContext(Object ctx) throws NamingException {
		Etablissement struct = new Etablissement();

		DirContextAdapter context = (DirContextAdapter) ctx;

		final String siren = context.getStringAttribute(LdapAttributes.ENTSTRUCTURE_IDENT_ATTRIBUTE);
		log.debug("Ldap to Object Mapping for siren '{}'", siren);
		Assert.hasText(siren, "Le siren ne peut pas être vide !");
		struct.setIdent(structMapper.getPronoteID(siren));
		Assert.isTrue(struct.getIdent() >= 0, "Le pronote Id ne peut pas être vide !");

		struct.setNumero(context.getStringAttribute(LdapAttributes.ENTSTRUCTURE_UAI).toUpperCase());
		Assert.hasText(struct.getNumero(), "L'UAI ne peut pas être vide !");

		String cleanedName = null;
		for (IStringCleaner cleaner : structNameCleaner) {
			final String toClean = cleanedName != null ? cleanedName : context.getStringAttribute(LdapAttributes.ENTSTRUCTURE_NOM_COURANT);
			cleanedName = cleaner.clean(toClean);
		}
		struct.setNom(cleanedName);
		
		struct.setCodePostal(context.getStringAttribute(LdapAttributes.POSTAL_CODE));
		struct.setVille(context.getStringAttribute(LdapAttributes.VILLE));
		struct.setAdresse1(context.getStringAttribute(LdapAttributes.STREET));

		Assert.isTrue(struct.getIdent() >= 0, "No ident attribute found in LDAP for UniteAdministrativeImmatriculee !");
		Assert.hasText(struct.getNumero(), "No numero attribute found in LDAP for UniteAdministrativeImmatriculee !");
		Assert.hasText(struct.getNom(), "No nom attribute found in LDAP for UniteAdministrativeImmatriculee !");
		return struct;
	}

}
