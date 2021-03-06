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
package fr.recia.pronote.ws.config.bean;

import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class Filter {
    @NotNull
    private String eleve;
    @NotNull
    private String personnel;
    @NotNull
    private String professeur;
    @NotNull
    private String responsable;
    @NotNull
    private String etablissement;
}
