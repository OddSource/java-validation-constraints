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
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

/**
 * Composite constraint annotation that enforces {@link NotNull @NotNull} and {@link NotNullModulus @NotNullModulus}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 * @see Modulus
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		  ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@NotNull
@Modulus(type = Modulus.Modulo.MOD10, multiplier = -1)
public @interface NotNullModulus
{
	/**
	 * Indicates which modulo check algorithm to use.
	 *
	 * @return the modulo check algorithm to use.
	 * @see Modulus#type
	 */
	@OverridesAttribute(constraint = Modulus.class, name = "type")
	Modulus.Modulo type();

	/**
	 * The multiplier that the modulo check algorithm should use.
	 *
	 * @return the multiplier.
	 * @see Modulus#multiplier
	 */
	@OverridesAttribute(constraint = Modulus.class, name = "multiplier")
	int multiplier();

	/**
	 * The inclusive start index of the section of the target value that should be checked.
	 *
	 * @return the start index.
	 * @see Modulus#startIndex
	 */
	@OverridesAttribute(constraint = Modulus.class, name = "startIndex")
	int startIndex() default 0;

	/**
	 * The exclusive end index of the section of the target value that should be checked.
	 *
	 * @return the end index.
	 * @see Modulus#endIndex
	 */
	@OverridesAttribute(constraint = Modulus.class, name = "endIndex")
	int endIndex() default Integer.MAX_VALUE;

	/**
	 * By default the check digit is assumed to lie at a standard location within the string to be evaluated.
	 *
	 * @return the check digit index, if non-standard.
	 * @see Modulus#checkDigitIndex
	 */
	@OverridesAttribute(constraint = Modulus.class, name = "checkDigitIndex")
	int checkDigitIndex() default -1;

	/**
	 * Indicates whether non-numeric characters should result in a validation error.
	 *
	 * @return {@code true} if non-numeric characters should be ignored, {@code false} otherwise.
	 * @see Modulus#ignoreNonDigits
	 */
	@OverridesAttribute(constraint = Modulus.class, name = "ignoreNonDigits")
	boolean ignoreNonDigits() default true;

	String message() default "{net.nicholaswilliams.java.validation.NotNullModulus.message}";

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
		NotNullModulus[] value();
	}
}
