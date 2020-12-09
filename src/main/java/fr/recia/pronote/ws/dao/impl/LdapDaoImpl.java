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
package fr.recia.pronote.ws.dao.impl;

import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import fr.recia.pronote.ws.config.bean.LDAPProperties;
import fr.recia.pronote.ws.dao.ILdapDao;
import fr.recia.pronote.ws.model.rapprochementsso.Eleve;
import fr.recia.pronote.ws.model.rapprochementsso.Eleves;
import fr.recia.pronote.ws.model.rapprochementsso.Etablissement;
import fr.recia.pronote.ws.model.rapprochementsso.Personnel;
import fr.recia.pronote.ws.model.rapprochementsso.Personnels;
import fr.recia.pronote.ws.model.rapprochementsso.Professeur;
import fr.recia.pronote.ws.model.rapprochementsso.Professeurs;
import fr.recia.pronote.ws.model.rapprochementsso.Responsable;
import fr.recia.pronote.ws.model.rapprochementsso.Responsables;
import fr.recia.pronote.ws.service.bean.IExtractOpaqueId;
import fr.recia.pronote.ws.service.bean.IExtractUIDFromDN;
import fr.recia.pronote.ws.service.bean.IIDMapper;
import fr.recia.pronote.ws.service.bean.IStringCleaner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.HardcodedFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

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
    private IExtractOpaqueId extractOpaqueId;

    @Autowired
    private IExtractUIDFromDN extractUIDFromDN;

    @Autowired
    private List<IStringCleaner> structNameCleaner;

    private Pattern responsablePattern;

    @PostConstruct
    private void setUp(){
        this.responsablePattern = Pattern.compile(this.ldapProperties.getAutorizedResponsablePattern());
    }

    @Override
    public Eleves findAllEleves(final String idEtablissement, IIDMapper userMapper) {
        final Filter filter= new HardcodedFilter(String.format(ldapProperties.getFilters().getEleve(), idEtablissement));
        if (log.isDebugEnabled()) {
            log.debug("LDAP filter applied : " + filter);
        }
        ContextMapper<Eleve> mapper = new EleveAttributesMapper(userMapper, extractOpaqueId, extractUIDFromDN, responsablePattern);
        LdapQuery query = LdapQueryBuilder.query()
                .attributes(LdapAttributes.PERSON_ATTRS.toArray(new String[LdapAttributes.PERSON_ATTRS.size()]))
                .base(ldapProperties.getPeopleRootDn()).filter(filter);

        Eleves eleves = new Eleves();
        eleves.setEleves(ldapTemplate.search(query, mapper));
        return eleves;
    }

    @Override
    public Personnels findAllPersonnels(final String idEtablissement, IIDMapper userMapper) {
        final Filter filter= new HardcodedFilter(String.format(ldapProperties.getFilters().getPersonnel(), idEtablissement));
        if (log.isDebugEnabled()) {
            log.debug("LDAP filter applied : " + filter);
        }
        ContextMapper<Personnel> mapper = new PersonnelAttributesMapper(userMapper, extractOpaqueId);
        LdapQuery query = LdapQueryBuilder.query()
                .attributes(LdapAttributes.PERSON_ATTRS.toArray(new String[LdapAttributes.PERSON_ATTRS.size()]))
                .base(ldapProperties.getPeopleRootDn()).filter(filter);

        Personnels personnels = new Personnels();
        personnels.setPersonnels(ldapTemplate.search(query, mapper));
        return personnels;
    }

    @Override
    public Professeurs findAllProfesseurs(final String idEtablissement, IIDMapper userMapper) {
        final Filter filter= new HardcodedFilter(String.format(ldapProperties.getFilters().getProfesseur(), idEtablissement));
        if (log.isDebugEnabled()) {
            log.debug("LDAP filter applied : " + filter);
        }
        ContextMapper<Professeur> mapper = new ProfesseurAttributesMapper(userMapper, extractOpaqueId);
        LdapQuery query = LdapQueryBuilder.query()
                .attributes(LdapAttributes.PERSON_ATTRS.toArray(new String[LdapAttributes.PERSON_ATTRS.size()]))
                .base(ldapProperties.getPeopleRootDn()).filter(filter);

        Professeurs professeurs = new Professeurs();
        professeurs.setProfesseurs(ldapTemplate.search(query, mapper));
        return professeurs;
    }

    @Override
    public Responsables finadAllResponsables(final String idEtablissement, IIDMapper userMapper) {
        final Filter filter= new HardcodedFilter(String.format(ldapProperties.getFilters().getResponsable(), idEtablissement));
        if (log.isDebugEnabled()) {
            log.debug("LDAP filter applied : " + filter);
        }
        ContextMapper<Responsable> mapper = new ResponsableAttributesMapper(userMapper, extractOpaqueId);
        LdapQuery query = LdapQueryBuilder.query()
                .attributes(LdapAttributes.PERSON_ATTRS.toArray(new String[LdapAttributes.PERSON_ATTRS.size()]))
                .base(ldapProperties.getPeopleRootDn()).filter(filter);

        Responsables responsables = new Responsables();
        responsables.setResponsables(ldapTemplate.search(query, mapper));
        return responsables;
    }

    @Override
    public Etablissement findOneEtablissementById(final String idEtablissement, IIDMapper structMapper) {
        final Filter filter= new HardcodedFilter(String.format(ldapProperties.getFilters().getEtablissement(), idEtablissement));
        if (log.isDebugEnabled()) {
            log.debug("LDAP filter applied : " + filter);
        }
        ContextMapper<Etablissement> mapper = new EtablissementAttributesMapper(structMapper, structNameCleaner);
        LdapQuery query = LdapQueryBuilder.query()
                .attributes(LdapAttributes.STRUCTURE_ATTRS.toArray(new String[LdapAttributes.STRUCTURE_ATTRS.size()]))
                .base(ldapProperties.getStructureRootDn()).filter(filter);

        Etablissement etablissement = ldapTemplate.searchForObject(query, mapper);

        return etablissement;
    }
}
