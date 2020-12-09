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
package fr.recia.pronote.ws.dao;

import javax.validation.constraints.NotBlank;

import fr.recia.pronote.ws.model.rapprochementsso.Eleves;
import fr.recia.pronote.ws.model.rapprochementsso.Etablissement;
import fr.recia.pronote.ws.model.rapprochementsso.Personnels;
import fr.recia.pronote.ws.model.rapprochementsso.Professeurs;
import fr.recia.pronote.ws.model.rapprochementsso.Responsables;
import fr.recia.pronote.ws.service.bean.IIDMapper;

public interface ILdapDao {

  Eleves findAllEleves(@NotBlank final String idEtablissement, final IIDMapper userMapper);

  Personnels findAllPersonnels(@NotBlank final String idEtablissement, final IIDMapper userMapper);

  Professeurs findAllProfesseurs(@NotBlank final String idEtablissement, final IIDMapper userMapper);

  Responsables finadAllResponsables(@NotBlank final String idEtablissement, final IIDMapper userMapper);

  Etablissement findOneEtablissementById(@NotBlank final String id, final IIDMapper structMapper);
}