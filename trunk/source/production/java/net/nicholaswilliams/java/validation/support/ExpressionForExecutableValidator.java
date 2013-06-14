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
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import net.nicholaswilliams.java.validation.Constants;
import net.nicholaswilliams.java.validation.ExpressionForExecutable;
import net.nicholaswilliams.java.validation.support.el.JuelExpressionValidator;

/**
 * Validator for {@link ExpressionForExecutable}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class ExpressionForExecutableValidator implements ConstraintValidator<ExpressionForExecutable, Object[]>
{
	private ScriptEngine scriptEngine;

	private String expression;

	private String[] parameterAliases;

	private int numParameters;

	@Override
	public void initialize(ExpressionForExecutable annotation)
	{
		if(annotation.language().equals(Constants.JAVA_UNIFIED_EXPRESSION_LANGUAGE))
		{
			if(!ExpressionForClassValidator.JUEL_SUPPORTED)
			{
				throw new ConstraintDeclarationException(
						"The Java Unified Expression Language API was not found on the class path."
				);
			}

			JuelExpressionValidator.class.getPackage(); // initialize class to make sure classes on classpath
		}
		else
		{
			this.scriptEngine = new ScriptEngineManager().getEngineByName(annotation.language());
			if(this.scriptEngine == null)
			{
				throw new ConstraintDeclarationException(
						"The Java Scripting Engine for language [" + annotation.language() + "] was not found."
				);
			}
		}

		this.expression = annotation.expression();
		this.parameterAliases = annotation.parameterAliases();
		this.numParameters = this.parameterAliases.length;
	}

	@Override
	public boolean isValid(Object[] parameters, ConstraintValidatorContext context)
	{
		if(parameters.length != this.numParameters)
		{
			throw new ConstraintDeclarationException(
					"The number of parameter aliases provided (" + parameters.length +
					") does not match the number of method arguments (" + this.numParameters + ") resolved."
			);
		}

		Bindings beans = new SimpleBindings();
		for(int i = 0; i < this.numParameters; i++)
		{
			beans.put(this.parameterAliases[i], parameters[i]);
		}

		ExpressionValidator validator;
		if(this.scriptEngine == null)
		{
			validator = new JuelExpressionValidator(this.expression, beans);
		}
		else
		{
			validator = new ScriptEngineExpressionValidator(this.scriptEngine, this.expression, beans);
		}

		return validator.validate();
	}
}
