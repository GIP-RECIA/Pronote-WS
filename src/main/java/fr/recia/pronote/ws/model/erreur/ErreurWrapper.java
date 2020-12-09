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
package fr.recia.pronote.ws.model.erreur;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Erreur"
})
@ToString
@EqualsAndHashCode
public class ErreurWrapper implements Serializable {

    @Valid
    @JsonProperty(value = "Erreur", required = true)
    @NotNull
    private Erreur erreur;

    /**
     * No args constructor for use in serialization
     */
    public ErreurWrapper() {
        super();
    }

    /**
     * Constructor with all args
     * @param erreur
     */
    public ErreurWrapper(@NotNull final Erreur erreur) {
        super();
        this.erreur = erreur;
    }

    @JsonProperty("Erreur")
    public Erreur getErreur() {
        return erreur;
    }

    @JsonProperty("Erreur")
    public void setErreur(@NotNull final Erreur erreur) {
        this.erreur = erreur;
    }
}
