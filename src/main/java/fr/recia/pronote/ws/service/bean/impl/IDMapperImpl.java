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
package fr.recia.pronote.ws.service.bean.impl;

import java.util.LinkedList;

import fr.recia.pronote.ws.service.bean.IIDMapper;
import fr.recia.pronote.ws.service.exception.NotFoundIdException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
@NoArgsConstructor
public class IDMapperImpl implements IIDMapper {

    LinkedList<String> ids= new LinkedList<>();

    @Override
    public long getPronoteID(String ENTID) {
        Assert.hasText(ENTID, "Lidentifiant transmis ne peut pas être vide.");
        log.debug("Identifiant ENT transmis {}", ENTID);
        int index = ids.indexOf(ENTID);
        if (index < 0) {
            ids.addLast(ENTID);
            index = ids.size()-1;
        }
        log.debug("Identifiant ENT transmis {} returne l'identifiant pronote {}", ENTID, index);
        return index;
    }

    @Override
    public long findPronoteID(String ENTID) throws NotFoundIdException {
        Assert.hasText(ENTID, "Lidentifiant transmis ne peut pas être vide.");
        log.debug("Identifiant ENT transmis {}", ENTID);
        int index = ids.indexOf(ENTID);
        log.debug("Identifiant ENT transmis {} returne l'identifiant pronote {}", ENTID, index);
        if (index < 0) {
            throw new NotFoundIdException();
        }
        return index;
    }
}
