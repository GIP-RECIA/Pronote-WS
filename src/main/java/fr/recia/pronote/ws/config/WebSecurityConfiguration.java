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
package fr.recia.pronote.ws.config;

import fr.recia.pronote.ws.config.bean.AppSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfiguration {

	private final AppSecurityProperties securityProperties;

	public WebSecurityConfiguration(AppSecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		StringBuilder hasIpAddress = new StringBuilder(
				"hasIpAddress('127.0.0.1') or hasIpAddress('::1')"
		);

		for (String ip : securityProperties.getAuthorizedIPAccess()) {
			hasIpAddress.append(" or hasIpAddress('").append(ip).append("')");
		}

		log.debug("WebSecurity configuration: authorize access on '/**' for {}", hasIpAddress);

		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/**")
						.access(new WebExpressionAuthorizationManager(hasIpAddress.toString()))				)
				.httpBasic(AbstractHttpConfigurer::disable);

		return http.build();
	}
}