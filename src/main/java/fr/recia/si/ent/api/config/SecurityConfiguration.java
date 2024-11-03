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
package fr.recia.si.ent.api.config;

import fr.recia.si.ent.api.config.bean.AppConfProperties;
import fr.recia.si.ent.api.config.security.AuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {

	@Autowired
	private AppConfProperties appConfProperties;


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//log.debug("WebSecurity configuration: authorize access on '/api/**' for {}", hasIpAddress.toString());
		//.access(hasIpAddress(appConfProperties)).and().anyRequest()

		http.csrf(AbstractHttpConfigurer::disable).exceptionHandling()
				.and().authorizeHttpRequests(
					authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.antMatchers("/**").authenticated())
				.httpBasic(Customizer.withDefaults()).sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}


	private AuthorizationManager<RequestAuthorizationContext> hasIpAddress(AppConfProperties appConfProperties) {
		Set<IpAddressMatcher> IpAddressMatchers = new HashSet<>();
		appConfProperties.getIpAddressClientMap().values().forEach(IpAddressMatchers::addAll);
		return (authentication, context) -> {
			HttpServletRequest request = context.getRequest();
			return new AuthorizationDecision(IpAddressMatchers.stream().anyMatch(ipAddressMatchers -> ipAddressMatchers.matches(request)));
		};
	}
}
