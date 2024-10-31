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
package fr.recia.si.ent.api.dao;

import fr.recia.si.ent.api.service.bean.IExtractOpaqueId;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ILdapDao {

 Map<String,Object> findPersonFromStringFilter(@NotBlank final String filter, @NotEmpty final Set<String> attributes, final IExtractOpaqueId extractOpaqueId) throws EmptyResultDataAccessException;

 List<Map<String,Object>> findAllPersonFromStringFilter(@NotBlank final String filter, @NotEmpty final Set<String> attributes, final IExtractOpaqueId extractOpaqueId) throws EmptyResultDataAccessException;

}