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
 * Constraint annotation that ensures that a password has a particular strength as set forth in the specific
 * requirements outlined by the {@link #minLength}, {@link #minUppercase}, {@link #minLowercase}, {@link #minNumber},
 * {@link #minSpecial}, {@link #maxSpecial}, {@link #minWhitespace}, {@link #maxWhitespace}, and
 * {@link #allowedWhitespace} attributes.<br>
 * <br>
 * This constraint intentionally does not support a maximum length because you should never restrict the length of a
 * password or (more importantly) passphrase. Database column length should never be a factor because you should never
 * store retrievable password values; instead, you should store password hashes.<br>
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
public @interface PasswordContent
{
	/**
	 * Indicates the minimum length that the target must be.
	 *
	 * @return the minimum length.
	 */
	int minLength() default 5;

	/**
	 * Indicates the minimum number of uppercase characters that must be present in the target.
	 *
	 * @return the minimum number of uppercase characters.
	 */
	int minUppercase() default 0;

	/**
	 * Indicates the minimum number of lowercase characters that must be present in the target.
	 *
	 * @return the minimum number of lowercase characters.
	 */
	int minLowercase() default 0;

	/**
	 * Indicates the minimum number of number characters that must be present in the target.
	 *
	 * @return the minimum number of number characters.
	 */
	int minNumber() default 0;

	/**
	 * Indicates the minimum number of special characters that must be present in the target. A character is considered
	 * whitespace if it does not match {@link Character#isLetter(char)} or {@link Character#isDigit(char)} and is not
	 * considered whitespace as per {@link #minWhitespace}.
	 *
	 * @return the minimum number of special characters.
	 * @see #maxSpecial
	 */
	int minSpecial() default 0;

	/**
	 * Indicates the maximum number of special characters that may be present in the target. A character is considered
	 * whitespace if it does not match {@link Character#isLetter(char)} or {@link Character#isDigit(char)} and is not
	 * considered whitespace as per {@link #minWhitespace}.
	 *
	 * @return the maximum number of special characters.
	 * @see #minSpecial
	 */
	int maxSpecial() default Integer.MAX_VALUE;

	/**
	 * Indicates the minimum number of whitespace characters that must be present in the target. By default whitespace
	 * is determined by calling {@link Character#isWhitespace(char)}. You can override this by specifying a non-empty
	 * character array in {@link #allowedWhitespace}.
	 *
	 * @return the minimum number of whitespace characters.
	 * @see Character#isWhitespace(char)
	 * @see #maxWhitespace
	 * @see #allowedWhitespace
	 */
	int minWhitespace() default 0;

	/**
	 * Indicates the maximum number of whitespace characters that may be present in the target.
	 *
	 * @return the maximum number of whitespace characters.
	 * @see Character#isWhitespace(char)
	 * @see #minWhitespace
	 * @see #allowedWhitespace
	 */
	int maxWhitespace() default 0;

	/**
	 * Indicates which characters are considered whitespace. If this array is empty (default), a character is considered
	 * whitespace if {@link Character#isWhitespace(char)} is {@code true}.
	 *
	 * @return which characters are considered whitespace.
	 * @see Character#isWhitespace(char)
	 * @see #maxWhitespace
	 * @see #minWhitespace
	 */
	char[] allowedWhitespace() default { };

	String message() default "{net.nicholaswilliams.java.validation.PasswordContent.message}";

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
		PasswordContent[] value();
	}
}
