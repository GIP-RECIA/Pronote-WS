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
package fr.recia.si.ent.api.config.bean;

import fr.recia.si.ent.api.dao.impl.LdapAttributes;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Data
@Validated
public class APIClient {
    /*@NotBlank
    private String groupRegex;
    @NotEmpty
    private Map<String, String> indexListReplacement;*/

    @NotBlank
    private String apiKey;
    @NotEmpty
    private List<String> authorizedIPAccess;
    private userOpaqueId userOpaqueId;
    @NotEmpty
    private Map<String, endPointsRequest> endPointsRequestMap;


    @Data
    @Validated
    @Slf4j
    public static class endPointsRequest {
        @NotBlank
        private String ldapFilter;
        @NotEmpty
        private Set<String> ldapAttributes;

        @PostConstruct
        private void checkSetUp(){
            Set<String> ldapAttributesNormalized = new HashSet<>();
            ldapAttributes.forEach(val -> {
                Optional<String> match = LdapAttributes.ALL_ATTRS.stream().filter(e -> e.equalsIgnoreCase(val)).findFirst();
                if (match.isPresent()) {
                    ldapAttributesNormalized.add(match.get());
                } else {
                    log.warn("The ldapAttribute '{}' doesn't exist in LdapAttributes list {}", val, LdapAttributes.ALL_ATTRS );
                }
            });
            this.ldapAttributes = ldapAttributesNormalized;
        }
    }
    @Data
    @Validated
    @Slf4j
    public static class userOpaqueId {
        @NotBlank
        private String regexp;
        @NotBlank
        private String ldapAttribute;

        @PostConstruct
        private void checkSetUp(){
            Optional<String> match = LdapAttributes.ALL_ATTRS.stream().filter(e -> e.equalsIgnoreCase(ldapAttribute)).findFirst();
            if (match.isPresent()) {
                this.ldapAttribute = match.get();
            } else {
                log.warn("The ldapAttribute '{}' for userOpaqueId doesn't exist in LdapAttributes list {}", ldapAttribute, LdapAttributes.ALL_ATTRS );
            }
        }
    }
}
