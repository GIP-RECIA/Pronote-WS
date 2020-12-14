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
package fr.recia.pronote.ws;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.stream.StreamSupport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class PronoteWsApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PronoteWsApplication.class);
	}

	public static void main(String[] args) {

		for(String arg:args) {
			System.out.println(arg);
		}


		ConfigurableApplicationContext app = SpringApplication.run(PronoteWsApplication.class, args);

//		BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
//		Security.addProvider(bouncyCastleProvider);
//		Security.setProperty("crypto.policy", "unlimited");
//
//		log.info("Bouncy Catle Provider Version " + bouncyCastleProvider.getVersionStr()
//				+ " \nName " + bouncyCastleProvider.getName()
//				+ " | \n" + bouncyCastleProvider.getInfo());

		Provider[] providers = Security.getProviders();

		for (Provider provider : providers) {
			log.info("Installed security providers" + provider.getInfo() + "\n");
		}

		Environment env = app.getEnvironment();

		log.info("====== Environment and configuration ======");
		log.info("Arguments passed : {}", Arrays.toString(args));
		log.info("Active profiles: {}", Arrays.toString(env.getActiveProfiles()));
		final MutablePropertySources sources = ((AbstractEnvironment) env).getPropertySources();
		StreamSupport.stream(sources.spliterator(), false).forEach(val -> log.info("source {}", val.getName()));
		StreamSupport.stream(sources.spliterator(), false)
				.filter(ps -> ps instanceof EnumerablePropertySource)
				.map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
				.flatMap(Arrays::stream)
				.distinct()
				.filter(prop -> !(prop.contains("credentials") || prop.contains("password")))
				.forEach(prop -> log.info("{}: {}", prop, env.getProperty(prop)));
		log.info("===========================================");

	}

}
