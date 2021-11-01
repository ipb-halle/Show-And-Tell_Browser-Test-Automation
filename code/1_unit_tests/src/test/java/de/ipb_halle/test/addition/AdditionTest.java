/*
 * Copyright 2021 Leibniz-Institut für Pflanzenbiochemie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package de.ipb_halle.test.addition;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AdditionTest {
	private Addition add;

	@Before
	public void init() {
		add = new Addition();
	}

	@Test
	public void test_apply() {
		assertEquals(42, add.apply(30, 12));
		assertEquals(42, add.apply(60, -18));
	}

	@Test
	public void test_apply_overflow() {
		assertEquals(Integer.MIN_VALUE + 1, add.apply(Integer.MAX_VALUE, 2));
	}
}