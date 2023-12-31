/*
 * Copyright 2002-2022 the original author or authors.
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

package org.springframework.security.authorization;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AuthorizationManagers}.
 *
 * @author Evgeniy Cheban
 */
class AuthorizationManagersTests {

	@Test
	void checkAnyOfWhenOneGrantedThenGrantedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.anyOf((a, o) -> new AuthorizationDecision(false),
				(a, o) -> new AuthorizationDecision(true));
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isTrue();
	}

	// gh-13069
	@Test
	void checkAnyOfWhenAllNonAbstainingDeniesThenDeniedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.anyOf((a, o) -> new AuthorizationDecision(false),
				(a, o) -> null);
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isFalse();
	}

	@Test
	void checkAnyOfWhenEmptyThenDeniedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.anyOf();
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isFalse();
	}

	@Test
	void checkAllOfWhenAllGrantedThenGrantedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.allOf((a, o) -> new AuthorizationDecision(true),
				(a, o) -> new AuthorizationDecision(true));
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isTrue();
	}

	// gh-13069
	@Test
	void checkAllOfWhenAllNonAbstainingGrantsThenGrantedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.allOf((a, o) -> new AuthorizationDecision(true),
				(a, o) -> null);
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isTrue();
	}

	@Test
	void checkAllOfWhenOneDeniedThenDeniedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.allOf((a, o) -> new AuthorizationDecision(true),
				(a, o) -> new AuthorizationDecision(false));
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isFalse();
	}

	@Test
	void checkAllOfWhenEmptyThenGrantedDecision() {
		AuthorizationManager<?> composed = AuthorizationManagers.allOf();
		AuthorizationDecision decision = composed.check(null, null);
		assertThat(decision).isNotNull();
		assertThat(decision.isGranted()).isTrue();
	}

}
