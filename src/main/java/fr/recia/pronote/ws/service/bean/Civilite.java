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
package fr.recia.pronote.ws.service.bean;

import fr.recia.pronote.ws.model.rapprochementsso.Civilites;

public enum Civilite {
    M(2),
    Mme(1),
    undefined(0);

    private long code;

    private Civilite(long code) {
        this.code = code;
    }

    public long getCode() {
		return code;
	}
	public void setCode(final long code) {
		this.code = code;
	}

	public static Civilites getNomenclature() {
        Civilites civilites = new Civilites();
        civilites.getCivilites().add(new fr.recia.pronote.ws.model.rapprochementsso.Civilite(M.getCode(), M.name()));
        civilites.getCivilites().add(new fr.recia.pronote.ws.model.rapprochementsso.Civilite(Mme.getCode(), Mme.name()));
        civilites.getCivilites().add(new fr.recia.pronote.ws.model.rapprochementsso.Civilite(undefined.getCode(), undefined.name()));
        return civilites;
    }


}
