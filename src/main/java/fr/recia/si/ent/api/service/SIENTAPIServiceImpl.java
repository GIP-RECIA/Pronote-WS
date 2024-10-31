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
package fr.recia.si.ent.api.service;

import fr.recia.si.ent.api.config.bean.APIClient;
import fr.recia.si.ent.api.config.bean.ApiEndpoints;
import fr.recia.si.ent.api.config.bean.AppConfProperties;
import fr.recia.si.ent.api.dao.ILdapDao;
import fr.recia.si.ent.api.dao.impl.LdapAttributes;
import fr.recia.si.ent.api.service.bean.IExtractOpaqueId;
import fr.recia.si.ent.api.service.exception.NotFoundIdException;
import fr.recia.si.ent.api.service.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class SIENTAPIServiceImpl implements SIENTAPIService {

    @Autowired
    private ILdapDao ldapDao;
    @Autowired
    private AppConfProperties appConfProperties;
    @Autowired
    private Map<String, IExtractOpaqueId> opaqueIdExtractors;

    @Autowired
    @Qualifier("regroupementStructures")
    private Map<String, Set<String>> regroupementStructures;


    @Override
    public List<Map<String, Object>> getChildrensOf(final String externalId) throws NotFoundIdException {
        final String clientId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final APIClient apiClient = appConfProperties.getClients().get(clientId);

        final Map<String, Object> parent = getUserInfos(externalId);
        if (parent.isEmpty()) return new ArrayList<>();

        if (apiClient != null) {
            final APIClient.endPointsRequest endPointsRequest = apiClient.getEndPointsRequestMap().get(ApiEndpoints.CHILDRENS_OF);
            if (endPointsRequest != null) {
                final String uid = (String) parent.get(LdapAttributes.ENTPERSON_UID);
                return ldapDao.findAllPersonFromStringFilter(String.format(endPointsRequest.getLdapFilter(), uid),
                        endPointsRequest.getLdapAttributes(), opaqueIdExtractors.get(clientId));
            }
        }
        log.warn("getChildrensOf: no child was found with id '{}' for clientId '{}'", externalId, clientId);
        return new ArrayList<>();
    }

    public Map<String, Object> getUserInfos(final String externalId) throws NotFoundIdException {
        final String clientId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final APIClient apiClient = appConfProperties.getClients().get(clientId);
        return getUserInfos(externalId, apiClient, clientId);
    }

    private Map<String, Object> getUserInfos(final String externalId, final APIClient apiClient, final String clientId) throws NotFoundIdException {
        if (apiClient != null) {
            final APIClient.endPointsRequest endPointsRequest = apiClient.getEndPointsRequestMap().get(ApiEndpoints.USER_INFO);
            if (endPointsRequest != null) {
                return ldapDao.findPersonFromStringFilter(String.format(endPointsRequest.getLdapFilter(), externalId),
                        endPointsRequest.getLdapAttributes(), opaqueIdExtractors.get(clientId));
            }
        }
        log.warn("getUserInfo: no person found with id '{}' for clientId '{}'", externalId, clientId);
        throw new NotFoundIdException(String.format("Person with provided id '%s' for clientId '%s' wasn't found !", externalId, clientId));
    }


    private Pair<String, Set<String>> getRegroupementStructures(final String idEtablissement) {
        for (Map.Entry<String, Set<String>> entry : regroupementStructures.entrySet()) {
            if (containsIgnoreCase(entry.getValue(), idEtablissement))
                return new Pair<>(entry.getKey(), entry.getValue());
        }
        return new Pair<>(idEtablissement.toUpperCase(), Set.of(idEtablissement.toUpperCase()));
    }

    private boolean containsIgnoreCase(final Set<String> list, final String string) {
        return list.stream().anyMatch(s -> s.equalsIgnoreCase(string));
    }

}


