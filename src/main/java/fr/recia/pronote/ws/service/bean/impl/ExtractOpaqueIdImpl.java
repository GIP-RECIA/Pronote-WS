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
package fr.recia.pronote.ws.service.bean.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.validation.constraints.NotBlank;

import fr.recia.pronote.ws.dao.impl.LdapAttributes;
import fr.recia.pronote.ws.service.bean.IExtractOpaqueId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;

@Slf4j
public class ExtractOpaqueIdImpl implements IExtractOpaqueId {

    private Pattern opaqueIdPattern;

    public ExtractOpaqueIdImpl(final @NotBlank String opaqueIdPattern) {
        this.opaqueIdPattern = Pattern.compile(opaqueIdPattern);
    }

    @Override
    public String getOpaqueId(DirContextAdapter context) throws NamingException {
        final String[] opaqueIds = context.getStringAttributes(LdapAttributes.ESCO_PERSON_EXTERNAL_IDS);
        Assert.isTrue(opaqueIds != null && opaqueIds.length > 0,
                "L'ESCOPersonExternalIds ne peut pas être vide pour l'utilisateur "
                + context.getStringAttribute(LdapAttributes.UID));
        for (String val : opaqueIds) {
            Matcher matcher = opaqueIdPattern.matcher(val);
            if (matcher.find()) {
                log.debug(String.format("Matcher found opaqueIdPattern [%s] in [%s]", opaqueIdPattern.pattern(), val));
                return matcher.group(1);
            }
        }
        return null;
    }
}
