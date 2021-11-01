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

import java.util.Map;

/**
 * A simple calculator.
 * 
 * @author flange
 */
public class Calculator {
	private final Map<String, Operation> operations;

	/**
	 * @param operations
	 */
	public Calculator(Map<String, Operation> operations) {
		this.operations = operations;
	}

	/**
	 * Evaluate the given mathematical expression from left to right without
	 * consideration of the order of operation.
	 * 
	 * @param expression mathematical expression with spaces between all tokens,
	 *                   e.g. "5 - 7 / 9"
	 * @return result of the calculation
	 */
	public int calculate(String expression) {
		String[] tokens = expression.split(" ");
		Operation previousOperation = null;
		int previousValue = 0;
		boolean firstNumber = true;

		for (String token : tokens) {
			Operation operation = operations.get(token);
			if (operation != null) {
				previousOperation = operation;
			} else {
				int number = Integer.parseInt(token);
				if (firstNumber) {
					firstNumber = false;
					previousValue = number;
				} else {
					previousValue = previousOperation.apply(previousValue, number);
				}
			}
		}

		return previousValue;
	}
}