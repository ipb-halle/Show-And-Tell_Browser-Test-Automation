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
package de.ipb_halle.test.calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.ipb_halle.test.addition.Addition;
import de.ipb_halle.test.multiplication.Multiplication;

public class CalculatorTest {
	@Test
	public void test_calculate_withRealOperations() {
		Map<String, Operation> operations = new HashMap<>();
		operations.put("+", new Addition());
		operations.put("*", new Multiplication());
		Calculator calc = new Calculator(operations);

		assertEquals(20, calc.calculate("2 + 3 * 4"));
	}

	@Test
	public void test_calculate_withMocks() {
		Map<String, Operation> operations = new HashMap<>();
		operations.put("+", new OperationStub(10));
		operations.put("*", new OperationStub(20));
		Calculator calc = new Calculator(operations);

		assertEquals(20, calc.calculate("10 + 20 * 30"));
	}

	public class OperationStub implements Operation {
		private final int returnValue;

		public OperationStub(int returnValue) {
			this.returnValue = returnValue;
		}

		@Override
		public int apply(int a, int b) {
			return returnValue;
		}
	}

	@Test
	public void test_calculate_throwsException() {
		Map<String, Operation> operations = new HashMap<>();
		operations.put("+", new OperationStub(10));
		operations.put("*", new OperationStub(20));
		Calculator calc = new Calculator(operations);

		assertThrows(NumberFormatException.class, () -> calc.calculate("10+20*30"));
	}
}