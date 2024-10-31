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
package fr.recia.si.ent.api.service.bean.impl;

import fr.recia.si.ent.api.config.bean.APIClient;
import fr.recia.si.ent.api.dao.impl.LdapAttributes;
import fr.recia.si.ent.api.service.bean.IExtractOpaqueId;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.util.Assert;

import javax.naming.NamingException;
import javax.validation.constraints.NotEmpty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExtractOpaqueIdImpl implements IExtractOpaqueId {

    private final Pattern opaqueIdPattern;

    @Getter
    private final String ldapAttribute;

    public ExtractOpaqueIdImpl(final @NotEmpty APIClient.userOpaqueId userOpaqueId) {
        this.opaqueIdPattern = Pattern.compile(userOpaqueId.getRegexp());
        this.ldapAttribute = userOpaqueId.getLdapAttribute();

        Assert.isTrue(
                LdapAttributes.ALL_ATTRS.stream().anyMatch(e -> e.equalsIgnoreCase(this.ldapAttribute)),
                String.format("The ldapAttribute '%s' doesn't exist in LdapAttributes list %s", this.ldapAttribute, LdapAttributes.ALL_ATTRS ));

    }

    @Override
    public String getOpaqueId(DirContextAdapter context) throws NamingException {
        final String[] opaqueIds = context.getStringAttributes(ldapAttribute);
        Assert.isTrue(opaqueIds != null && opaqueIds.length > 0,
                "L'attribut '" + ldapAttribute + "' ne peut pas être vide pour l'utilisateur "
                + context.getStringAttribute(LdapAttributes.ENTPERSON_UID));
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
