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
package fr.recia.si.ent.api.dao.impl;

import fr.recia.si.ent.api.config.bean.LDAPProperties;
import fr.recia.si.ent.api.dao.ILdapDao;
import fr.recia.si.ent.api.service.bean.IExtractOpaqueId;
import fr.recia.si.ent.api.service.bean.IExtractUIDFromDN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class LdapDaoImpl implements ILdapDao {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private LDAPProperties ldapProperties;

    @Autowired
    private IExtractUIDFromDN extractUIDFromDN;


    @Override
    public Map<String,Object> findPersonFromStringFilter(@NotBlank final String filter, @NotEmpty final Set<String> attributes, final IExtractOpaqueId extractOpaqueId) throws EmptyResultDataAccessException {
        log.debug("findPersonFromStringFilter - LDAP filter applied '{}' and attributes requested '{}'", filter, attributes);

        // for security concern remove ids attributes from default building
        ContextMapper<Map<String, Object>> mapper = new PersonAttributesMapper(ldapProperties, attributes, extractOpaqueId);
        LdapQuery query = LdapQueryBuilder.query().attributes(Stream.concat(LdapAttributes.PERSON_IDS_ATTRS.stream(), attributes.stream())
                        .collect(Collectors.toSet()).toArray(String[]::new))
                .base(ldapProperties.getPeopleRootDn())
                .filter(filter);
        return ldapTemplate.searchForObject(query, mapper);
    }

    @Override
    public List<Map<String,Object>> findAllPersonFromStringFilter(@NotBlank final String filter, @NotEmpty final Set<String> attributes, final IExtractOpaqueId extractOpaqueId) throws EmptyResultDataAccessException {
        log.debug("findAllPersonFromStringFilter - LDAP filter applied '{}' and attributes requested '{}'", filter, attributes);


        // for security concern remove ids attributes from default building
        ContextMapper<Map<String, Object>> mapper = new PersonAttributesMapper(ldapProperties, attributes, extractOpaqueId);
        LdapQuery query = LdapQueryBuilder.query().attributes(Stream.concat(LdapAttributes.PERSON_IDS_ATTRS.stream(), attributes.stream())
                        .collect(Collectors.toSet()).toArray(String[]::new))
                .base(ldapProperties.getPeopleRootDn())
                .filter(filter);
        return ldapTemplate.search(query, mapper);
    }
}
