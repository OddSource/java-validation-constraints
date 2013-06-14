/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.nicholaswilliams.java.validation.support;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.validation.ConstraintDeclarationException;

/**
 * An expression validator that uses the Java Scripting Engine.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
final class ScriptEngineExpressionValidator implements ExpressionValidator
{
	private final ScriptEngine scriptEngine;
	private final String expression;
	private final Bindings beans;

	/**
	 * Constructs a validator.
	 *
	 * @param scriptEngine The Java standard script engine
	 * @param expression The expression to evaluate
	 * @param beans The bean bindings
	 */
	public ScriptEngineExpressionValidator(ScriptEngine scriptEngine, String expression, Bindings beans)
	{
		this.scriptEngine = scriptEngine;
		this.expression = expression;
		this.beans = beans;
	}

	@Override
	public boolean validate()
	{
		Object evaluationResult;
		try
		{
			evaluationResult = this.scriptEngine.eval(this.expression, this.beans);
		}
		catch(ScriptException e)
		{
			throw new ConstraintDeclarationException(
					"Error occurred during execution of expression ${" + this.expression + "}."
			);
		}

		if(evaluationResult == null)
		{
			throw new ConstraintDeclarationException(
					"Execution of expression ${" + this.expression + "} returned null."
			);
		}

		if(!(evaluationResult instanceof Boolean))
		{
			throw new ConstraintDeclarationException(
					"Execution of expression ${" + this.expression + "} returned unsupported type [" +
					evaluationResult.getClass().getName() + "]."
			);
		}

		return Boolean.TRUE.equals(evaluationResult);
	}
}
