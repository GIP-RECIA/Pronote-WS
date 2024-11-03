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

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@ConfigurationProperties(prefix = "app.ldap")
@Data
@Slf4j
@Validated
public class LDAPProperties {

    @NotNull
    private String base;
    @NotNull
    private String url;
    @NotNull
    private String userDn;
    @NotNull
    private String password;
    private boolean pooled;
    private boolean anonymousReadOnly;
    @NotNull
    private String structureRootDn;
    @NotNull
    private String peopleRootDn;
    private int timeout = 10000;
    private int countLimit = 1500;

    @NotBlank
    private String userUidExtractorPattern;

    @PostConstruct
    public void debug() {
        log.debug("LDAPProperties {}", this);
    }

}
