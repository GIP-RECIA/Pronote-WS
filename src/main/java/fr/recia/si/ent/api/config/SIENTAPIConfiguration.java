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
package fr.recia.si.ent.api.config;

import fr.recia.si.ent.api.config.bean.AppConfProperties;
import fr.recia.si.ent.api.config.bean.LDAPProperties;
import fr.recia.si.ent.api.service.bean.IExtractOpaqueId;
import fr.recia.si.ent.api.service.bean.IExtractUIDFromDN;
import fr.recia.si.ent.api.service.bean.impl.ExtractOpaqueIdImpl;
import fr.recia.si.ent.api.service.bean.impl.ExtractUIDFromDNImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@Slf4j
public class SIENTAPIConfiguration {

    @Autowired
    private LDAPProperties ldapProperties;

    @Autowired
    private AppConfProperties appConfProperties;

    /**
     * Index opaqueIdExtractor per clientId
    **/
    @Bean
    public Map<String, IExtractOpaqueId> opaqueIdExtractors(){
        Map<String, IExtractOpaqueId> opaqueIdMap = new HashMap<>();
        appConfProperties.getClients().forEach((key, val) -> {
            if (val.getUserOpaqueId() != null)
                opaqueIdMap.put(key, new ExtractOpaqueIdImpl(val.getUserOpaqueId()));
        });
        return opaqueIdMap;
    }

    @Bean
    public IExtractUIDFromDN uidFromDNExtractor() {
        return new ExtractUIDFromDNImpl(ldapProperties.getUserUidExtractorPattern());
    }

//    @Bean
//    public List<IStringCleaner> etablissementNameCleaner() {
//        final List<IStringCleaner> cleaners = new ArrayList<>();
//        ldapProperties.getEtablissementNameProcessors().forEach(cleaner -> cleaners.add(new RegexGroupReplacementCleaner(cleaner)));
//        return cleaners;
//    }

    @Bean
    public LdapContextSource contextSource() {
        final LdapContextSource contextSource = new LdapContextSource();

        contextSource.setAnonymousReadOnly(ldapProperties.isAnonymousReadOnly());
        contextSource.setBase(ldapProperties.getBase());
        contextSource.setUrl(ldapProperties.getUrl());
        contextSource.setUserDn(ldapProperties.getUserDn());
        contextSource.setPassword(ldapProperties.getPassword());
        contextSource.setPooled(ldapProperties.isPooled());

        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() throws Exception{
        final LdapTemplate ldapTemplate = new LdapTemplate();
        ldapTemplate.setContextSource(contextSource());
        ldapTemplate.setDefaultCountLimit(ldapProperties.getCountLimit());
        ldapTemplate.setDefaultTimeLimit(ldapProperties.getTimeout());

        return ldapTemplate;
    }

    @Bean
    public Map<String, Set<String>> regroupementStructures() {
        Map<String, Set<String>> grouped = new HashMap<>();
        appConfProperties.getStructuresRegroupees().forEach((key, values) -> {
            values.replaceAll(String::toUpperCase);
            Set<String> concatened = new HashSet<>(values);
            concatened.add(key.toUpperCase());
            grouped.put(key.toUpperCase(), concatened);
        });
        return grouped;
    }
}
