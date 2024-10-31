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
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@ConfigurationProperties(prefix = "app.conf")
@Data
@Validated
@Slf4j
public class AppConfProperties {

    @NotEmpty
    private Map<String, List<String>> structuresRegroupees = new HashMap<>();

    @NotEmpty
    private Map<String,APIClient> clients = new HashMap<>();

    private Map<String, String> APIKeysClientMap = new HashMap<>();
    private Map<String, Set<IpAddressMatcher>> IpAddressClientMap = new HashMap<>();
    private Map<String, Set<IpAddressMatcher>> IpAddressAPIPathMap = new HashMap<>();

    @PostConstruct
    public void setupAndDebug() {
        clients.forEach((clientId, clientDesc) -> {
            APIKeysClientMap.put(clientDesc.getApiKey(), clientId);
            //Compile IpAddressMatchers for clients access
            Set<IpAddressMatcher> ipsMatchers = new HashSet<>();
            clientDesc.getAuthorizedIPAccess().forEach(ip -> ipsMatchers.add(new IpAddressMatcher(ip)));
            IpAddressClientMap.put(clientId, ipsMatchers);
        });
        log.debug("AppConfProperties {}", this);
    }
}
