/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
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

package org.springframework.security.authentication.event;

import org.junit.jupiter.api.Test;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * Tests {@link LoggerListener}.
 *
 * @author Ben Alex
 */
public class LoggerListenerTests {

	private Authentication getAuthentication() {
		UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken
			.unauthenticated("Principal", "Credentials");
		authentication.setDetails("127.0.0.1");
		return authentication;
	}

	@Test
	public void testLogsEvents() {
		AuthenticationFailureDisabledEvent event = new AuthenticationFailureDisabledEvent(getAuthentication(),
				new LockedException("TEST"));
		LoggerListener listener = new LoggerListener();
		listener.onApplicationEvent(event);
	}

}
