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
package fr.recia.si.ent.api.config.security;

import fr.recia.si.ent.api.config.bean.AppConfProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    private static AppConfProperties appConfProperties;

    @Autowired
    private AppConfProperties config;

    @PostConstruct
    private void setUp() {
        appConfProperties = this.config;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        Assert.notNull(appConfProperties, "You have a misconfiguration of the class with the injected bean appConfProperties !");
        Assert.notEmpty(appConfProperties.getAPIKeysClientMap(), "You have a misconfiguration of bean appConfProperties.APIKeysClientMap, he is empty !");
        final String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        log.debug("getAuthentication - check for token {}", apiKey);
        if (apiKey == null || !appConfProperties.getAPIKeysClientMap().containsKey(apiKey)) {
            log.warn("Access with token '{}' isn't authorized - access from IP '{}'", apiKey, request.getRemoteAddr());
            throw new BadCredentialsException("Invalid API Key");
        }
        final String clientId = appConfProperties.getAPIKeysClientMap().get(apiKey);
        checkClientIpAccess(request, clientId);
        log.info("getAuthentication - authorization for clientId '{}' from IP '{}'", clientId, request.getRemoteAddr());
        return new ApiKeyAuthentication(clientId, AuthorityUtils.NO_AUTHORITIES);
    }

    private static void checkClientIpAccess(final HttpServletRequest request, final String clientId) throws AccessDeniedException {
        final String clientIpAddress = request.getRemoteAddr();
        boolean isAuthorized = false;
        for (String ip: appConfProperties.getClients().get(clientId).getAuthorizedIPAccess()) {
            if (new IpAddressMatcher(ip).matches(clientIpAddress)) {
                isAuthorized = true;
            }
        }
        if (!isAuthorized) {
            log.warn("Access from IP '{}' for clientId '{}' isn't authorized", clientIpAddress, clientId);
            throw new AccessDeniedException("Client Not Authorized");
        }
    }
}
