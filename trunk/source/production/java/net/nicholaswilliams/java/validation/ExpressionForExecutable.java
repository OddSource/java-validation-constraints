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
package net.nicholaswilliams.java.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import net.nicholaswilliams.java.validation.support.ExpressionForExecutableValidator;

/**
 * Special constraint annotation that defines an arbitrary expression that validates the parameters of an executable.
 * This is useful for ensuring that a property has a certain value based on the value of another property, for example.
 * As such, this constraint can only be applied to a class or interface, and not to a constructor, method, field, or
 * other annotation type. <em>Important note: This constraint requires Bean Validation 1.1, since Bean Validation 1.0
 * does not support validation on execution.</em><br>
 * <br>
 * Expressions can be written in the Java Unified Expression Language (which requires a JUEL provider to exist on the
 * class path) or in any scripting language that exists on the class path as a
 * <a href="http://jcp.org/en/jsr/detail?id=223">JSR 223</a> ("Scripting for the Java<sup>TM</sup> Platform")
 * engine.<br>
 * <br>
 * By default, this constraint assumes that the expression is written in the Java Unified Expression Language unless
 * otherwise specified.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ExpressionForExecutableValidator.class })
public @interface ExpressionForExecutable
{
	/**
	 * Indicates the expression to evaluate the method parameters with. The name of the reference variables to access
	 * the method parameters within the expression are specified using the {@link #parameterAliases} attribute.
	 *
	 * @return the expression to evaluate the method parameters with.
	 */
	String expression();

	/**
	 * Indicates the name of the reference variables that will be exposed to the expression for referencing the method
	 * parameters. These must be in the same order as the the parameters are declared in the method signature.
	 *
	 * @return the name of the reference variables for the method parameters
	 */
	String[] parameterAliases();

	/**
	 * Indicates the language the expression is written in. Defaults to the Java Unified Expression Language.
	 *
	 * @return the language the expression is written in.
	 */
	String language() default Constants.JAVA_UNIFIED_EXPRESSION_LANGUAGE;

	String message() default "{net.nicholaswilliams.java.validation.ExpressionForExecutable.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Used for specifying multiple constraints of the same type.
	 */
	@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	static @interface List
	{
		ExpressionForExecutable[] value();
	}
}
