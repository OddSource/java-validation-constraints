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

/**
 * Constraint annotation that ensures that the target {@link CharSequence} passes either the Modulo 10 or Modulo 11
 * check algorithms. For more information, see the
 * <a href="http://en.wikipedia.org/wiki/Check_digit">Wikipedia article on check digits.</a><br>
 * <br>
 * {@code null} values are considered valid.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		  ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {  }) //TODO: implement validator
public @interface Modulus
{
	/**
	 * Indicates which modulo check algorithm to use.
	 *
	 * @return the modulo check algorithm to use.
	 */
	Modulo type();

	/**
	 * The multiplier that the modulo check algorithm should use.
	 *
	 * @return the multiplier.
	 */
	int multiplier();

	/**
	 * The inclusive start index of the section of the target value that should be checked. Defaults to 0 (the first
	 * character).
	 *
	 * @return the start index.
	 */
	int startIndex() default 0;

	/**
	 * The exclusive end index of the section of the target value that should be checked. By default the whole value
	 * is checked.
	 *
	 * @return the end index.
	 */
	int endIndex() default Integer.MAX_VALUE;

	/**
	 * By default the check digit is assumed to lie at a standard location within the string to be evaluated. If the
	 * check digit lies outside the string, use this attribute to specify the check digit's location in the string.
	 * If set, the check digit index must be less than {@link #startIndex} and greater than {@link #endIndex}.
	 *
	 * @return the check digit index, if non-standard. A value less than zero means the check digit lies at a
	 *         standard location.
	 */
	int checkDigitIndex() default -1;

	/**
	 * Indicates whether non-numeric characters should result in a validation error. By default, non-numeric characters
	 * are ignored.
	 *
	 * @return {@code true} if non-numeric characters should be ignored, {@code false} if they should result in an
	 *         exception.
	 */
	boolean ignoreNonDigits() default true;

	String message() default "{net.nicholaswilliams.java.validation.Modulus.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Used for specifying multiple constraints of the same type.
	 */
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
			  ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	static @interface List
	{
		Modulus[] value();
	}

	/**
	 * Supported modulo check algorithms.
	 */
	public enum Modulo
	{
		/**
		 * Represents the Modulo 10 check algorithm, also known as the
		 * <a href="http://en.wikipedia.org/wiki/Luhn_algorithm">Luhn algorithm</a>.
		 *
		 * @since 1.0.0
		 */
		MOD10,

		/**
		 * Represents the Module 11 check algorithm.
		 *
		 * @since 1.0.0
		 */
		MOD11
	}
}
