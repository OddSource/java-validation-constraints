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
 * Composite constraint annotation that enforces {@link NotNull @NotNull} and {@link PasswordContent @PasswordContent}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 * @see PasswordContent
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		  ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@NotNull
@PasswordContent
public @interface NotNullPasswordContent
{
	/**
	 * Indicates the minimum length that the target must be.
	 *
	 * @return the minimum length.
	 * @see PasswordContent#minLength
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "minLength")
	int minLength() default 5;

	/**
	 * Indicates the minimum number of uppercase characters that must be present in the target.
	 *
	 * @return the minimum number of uppercase characters.
	 * @see PasswordContent#minUppercase
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "minUppercase")
	int minUppercase() default 0;

	/**
	 * Indicates the minimum number of lowercase characters that must be present in the target.
	 *
	 * @return the minimum number of lowercase characters.
	 * @see PasswordContent#minLowercase
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "minLowercase")
	int minLowercase() default 0;

	/**
	 * Indicates the minimum number of number characters that must be present in the target.
	 *
	 * @return the minimum number of number characters.
	 * @see PasswordContent#minNumber
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "minNumber")
	int minNumber() default 0;

	/**
	 * Indicates the minimum number of special characters that must be present in the target.
	 *
	 * @return the minimum number of special characters.
	 * @see PasswordContent#minSpecial
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "minSpecial")
	int minSpecial() default 0;

	/**
	 * Indicates the maximum number of special characters that may be present in the target.
	 *
	 * @return the maximum number of special characters.
	 * @see PasswordContent#maxSpecial
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "maxSpecial")
	int maxSpecial() default Integer.MAX_VALUE;

	/**
	 * Indicates the minimum number of whitespace characters that must be present in the target.
	 *
	 * @return the minimum number of whitespace characters.
	 * @see PasswordContent#minWhitespace
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "minWhitespace")
	int minWhitespace() default 0;

	/**
	 * Indicates the maximum number of whitespace characters that may be present in the target.
	 *
	 * @return the maximum number of whitespace characters.
	 * @see PasswordContent#maxWhitespace
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "maxWhitespace")
	int maxWhitespace() default 0;

	/**
	 * Indicates which characters are considered whitespace.
	 *
	 * @return which characters are considered whitespace.
	 * @see PasswordContent#allowedWhitespace
	 */
	@OverridesAttribute(constraint = PasswordContent.class, name = "allowedWhitespace")
	char[] allowedWhitespace() default { };

	String message() default "{net.nicholaswilliams.java.validation.NotNullPasswordContent.message}";

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
		NotNullPasswordContent[] value();
	}
}
