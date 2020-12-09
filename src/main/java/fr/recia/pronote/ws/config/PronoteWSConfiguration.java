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
package fr.recia.pronote.ws.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import fr.recia.pronote.ws.config.bean.AppConfProperties;
import fr.recia.pronote.ws.config.bean.AppIndexEducationProperties;
import fr.recia.pronote.ws.config.bean.LDAPProperties;
import fr.recia.pronote.ws.service.bean.IExtractOpaqueId;
import fr.recia.pronote.ws.service.bean.IExtractUIDFromDN;
import fr.recia.pronote.ws.service.bean.IStringCleaner;
import fr.recia.pronote.ws.service.bean.impl.ExtractOpaqueIdImpl;
import fr.recia.pronote.ws.service.bean.impl.ExtractUIDFromDNImpl;
import fr.recia.pronote.ws.service.bean.impl.RegexGroupReplacementCleaner;
import fr.recia.pronote.ws.service.util.CertificateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@Slf4j
public class PronoteWSConfiguration {

    @Autowired
    private LDAPProperties ldapProperties;

    @Autowired
    private AppIndexEducationProperties indexEducationProperties;

    @Autowired
    private AppConfProperties appConfProperties;

    @Bean
    public IExtractOpaqueId opaqueIdExtractor() {
        return new ExtractOpaqueIdImpl(ldapProperties.getUserOpaqueIdPattern());
    }

    @Bean
    public IExtractUIDFromDN uidFromDNExtractor() {
        return new ExtractUIDFromDNImpl(ldapProperties.getUserUidExtractorPattern());
    }

    @Bean
    public List<IStringCleaner> etablissementNameCleaner() {
        final List<IStringCleaner> cleaners = new ArrayList<>();
        ldapProperties.getEtablissementNameProcessors().forEach(cleaner -> cleaners.add(new RegexGroupReplacementCleaner(cleaner)));
        return cleaners;
    }

    @Bean
    public LdapContextSource contextSource() {
        final LdapContextSource contextSource = new LdapContextSource();

        contextSource.setAnonymousReadOnly(ldapProperties.isAnonimousReadOnly());
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
    @Lazy
    public PublicKey publicKey() {
        try {
            final File file = Paths.get(indexEducationProperties.getPublicKeyPath()).toFile();
            final String content = Files.readString(file.toPath());
            log.debug("key read: {}", content);
            return CertificateUtils.parseSSHPublicKey(content);
        } catch (IOException | InvalidKeyException e) {
            log.error("Impossible de lire la clé publique d'Index Education sous le chemin {}", indexEducationProperties.getPublicKeyPath(), e);
            throw new IllegalStateException(
                    String.format("Impossible de lire la clé publique d'Index Education sous le chemin %s", indexEducationProperties.getPublicKeyPath()), e);
        }
    }

    @Bean
    public File debugDataPath() {
        final File fs = Paths.get(appConfProperties.getDebugDataFilePath()).toFile();
        if (fs.isDirectory() && fs.canWrite()) return fs;

        throw new IllegalStateException("Le chemin spécifié pour générer les fichiers de débug n'est pas un répertoire autorisé en écriture.");
    }

    @Bean
    public File importChiffreXSD() throws IOException {
        final File fs = new ClassPathResource("xsd/ConteneurImportChiffre.xsd").getFile();
        if (fs.exists() && fs.isFile() && fs.canRead()) return fs;

        throw new IllegalStateException("Le fichier xsd/RapprochementSSO.xsd n'a pas été trouvé.");
    }

    @Bean
    public File rapprochementSSOXSD() throws IOException {
        final File fs = new ClassPathResource("xsd/RapprochementSSO.xsd").getFile();
        if (fs.exists() && fs.isFile() && fs.canRead()) return fs;

        throw new IllegalStateException("Le fichier xsd/RapprochementSSO.xsd n'a pas été trouvé.");
    }
}
