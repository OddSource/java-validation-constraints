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

import net.nicholaswilliams.java.validation.Constants;
import net.nicholaswilliams.java.validation.ExpressionForClass;
import net.nicholaswilliams.java.validation.support.el.JuelExpressionValidator;

/**
 * Validator for {@link ExpressionForClass}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
public class ExpressionForClassValidator implements ConstraintValidator<ExpressionForClass, Object>
{
	static final boolean JUEL_SUPPORTED = ReflectionUtils.classExists("javax.el.ExpressionFactory");

	private String expression;

	private String beanAlias;

	private ScriptEngine scriptEngine;

	@Override
	public void initialize(ExpressionForClass annotation)
	{
		if(annotation.language().equals(Constants.JAVA_UNIFIED_EXPRESSION_LANGUAGE))
		{
			if(!JUEL_SUPPORTED)
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
		this.beanAlias = annotation.beanAlias();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		Bindings beans = new SimpleBindings();
		beans.put(this.beanAlias, value);

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
