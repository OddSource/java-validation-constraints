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
 * Constraint annotation that ensures that the {@link CharSequence} target is a valid URL, and optionally that the
 * {@code CharSequence} or {@link java.net.URL} target has a restricted set of protocols, host names, or ports.<br>
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
public @interface Url
{
	/**
	 * Indicates which protocols the URL is allowed to have. By default, all protocols are allowed.
	 *
	 * @return the allowed protocols.
	 */
	String[] protocols() default { };

	/**
	 * Indicates which host names the URL is allowed to have. By default, all host names are allowed.
	 *
	 * @return the allowed host names.
	 */
	String[] host() default { };

	/**
	 * Indicates which ports the URL is allowed to have. By default, all ports are allowed.
	 *
	 * @return the allowed ports.
	 */
	int[] port() default { };

	String message() default "{net.nicholaswilliams.java.validation.Url.message}";

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
		Url[] value();
	}
}
