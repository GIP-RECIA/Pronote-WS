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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import fr.recia.pronote.ws.config.bean.Cleaner;
import fr.recia.pronote.ws.service.bean.IStringCleaner;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
@Data
@NoArgsConstructor
public class RegexGroupReplacementCleaner implements IStringCleaner {

    @NotBlank
	private String groupsRegex;
	private Pattern groupsRegexPattern;

	@NotEmpty
	private Map<Integer, String> indexListReplacement = new HashMap<>();

    public RegexGroupReplacementCleaner(Cleaner properties) {
        this.groupsRegex = properties.getGroupRegex();
        properties.getIndexListReplacement().forEach((key, val) -> this.indexListReplacement.put(Integer.parseInt(key), val));
        this.groupsRegexPattern = Pattern.compile(groupsRegex);
        log.debug("Pattern initialized from {}", this.groupsRegex);
    }

    public String clean(String input) {
        Assert.notNull(groupsRegexPattern, "The Pattern wasn't initialized from the regex String");
        if (input != null && !input.isEmpty()) {
            Matcher group = this.groupsRegexPattern.matcher(input);
            if (group.find()) {
                StringBuilder displayName = new StringBuilder(input);
                for (Map.Entry<Integer, String> entry : this.indexListReplacement.entrySet()) {
                    displayName.replace(group.start(entry.getKey()), group.end(entry.getKey()), entry.getValue());
                }

                log.debug("Matcher found groups displayName, and applied replacement value is : " + displayName);
                return displayName.toString();
            }

        }
        return input;
    }
}
