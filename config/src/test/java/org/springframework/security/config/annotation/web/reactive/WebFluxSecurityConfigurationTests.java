/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.config.annotation.web.reactive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.test.SpringTestContext;
import org.springframework.security.config.test.SpringTestContextExtension;
import org.springframework.security.config.users.ReactiveAuthenticationTestConfiguration;
import org.springframework.security.web.server.WebFilterChainProxy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link WebFluxSecurityConfiguration}.
 *
 * @author Eleftheria Stein
 */
@ExtendWith(SpringTestContextExtension.class)
public class WebFluxSecurityConfigurationTests {

	public final SpringTestContext spring = new SpringTestContext(this);

	@Test
	public void loadConfigWhenReactiveUserDetailsServiceConfiguredThenWebFilterChainProxyExists() {
		this.spring
			.register(ServerHttpSecurityConfiguration.class, ReactiveAuthenticationTestConfiguration.class,
					WebFluxSecurityConfiguration.class)
			.autowire();
		WebFilterChainProxy webFilterChainProxy = this.spring.getContext().getBean(WebFilterChainProxy.class);
		assertThat(webFilterChainProxy).isNotNull();
	}

	@Test
	public void loadConfigWhenBeanProxyingEnabledAndSubclassThenWebFilterChainProxyExists() {
		this.spring
			.register(ServerHttpSecurityConfiguration.class, ReactiveAuthenticationTestConfiguration.class,
					WebFluxSecurityConfigurationTests.SubclassConfig.class)
			.autowire();
		WebFilterChainProxy webFilterChainProxy = this.spring.getContext().getBean(WebFilterChainProxy.class);
		assertThat(webFilterChainProxy).isNotNull();
	}

	@Configuration
	static class SubclassConfig extends WebFluxSecurityConfiguration {

	}

}
