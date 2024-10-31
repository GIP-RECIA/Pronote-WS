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
/**
 *
 */
package fr.recia.si.ent.api.dao.impl;

import fr.recia.si.ent.api.config.bean.LDAPProperties;
import fr.recia.si.ent.api.service.bean.IExtractOpaqueId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;

import javax.naming.NamingException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@Slf4j
public class PersonAttributesMapper implements ContextMapper<Map<String,Object>> {

//	@NotNull
//	private IIDMapper userMapper;
	private static final String USERID_ATTR = "id";

	@NotNull
	private LDAPProperties ldapProperties;

	@NotEmpty
	private Set<String> attributes;

	private IExtractOpaqueId extractOpaqueId;


	@Override
	public Map<String,Object> mapFromContext(Object ctx) throws NamingException {
		Map<String,Object> person = new HashMap<>();

		DirContextAdapter context = (DirContextAdapter) ctx;

		final String uid = context.getStringAttribute(LdapAttributes.ENTPERSON_UID);
		final String userId = (extractOpaqueId != null) ? extractOpaqueId.getOpaqueId(context) : uid;

		log.debug("Ldap to Object Mapping for uid '{}' with userId {}", uid, userId);
		Assert.hasText(userId, "Le userId ne peut pas être vide !");
		person.put(USERID_ATTR, userId);

		for (String attr : attributes) {
			if (LdapAttributes.MONOVALUED_PERSON_ATTRs.contains(attr)) {
                person.put(attr, context.getStringAttribute(attr));
            } else if (LdapAttributes.MULTIVALUED_PERSON_ATTRs.contains(attr)) {
                person.put(attr, context.getStringAttributes(attr));
            } else {
                throw new IllegalArgumentException("You have a misconfiguration in your LDAPAttributes class from property '" + attr + "'");
            }
        }
		log.debug("Ldap to Object Mapping for uid '{}' return object '{}'", uid, person);
		return person;
	}

}
