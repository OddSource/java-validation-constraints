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
 * Composite constraint annotation that enforces {@link NotNull @NotNull} and {@link Uri @Url}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 * @see Uri
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		  ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@NotNull
@Uri
public @interface NotNullUri
{
	/**
	 * Indicates which schemes the URI is allowed to have.
	 *
	 * @return the allowed schemes.
	 * @see Uri#schemes
	 */
	@OverridesAttribute(constraint = Uri.class, name = "schemes")
	String[] schemes() default { };

	/**
	 * Indicates which scheme-specific parts the URI is allowed to have.
	 *
	 * @return the allowed host names.
	 * @see Uri#ssp
	 */
	@OverridesAttribute(constraint = Uri.class, name = "ssp")
	String[] ssp() default { };

	/**
	 * Indicates which ports the URI is allowed to have.
	 *
	 * @return the allowed ports.
	 * @see Uri#port
	 */
	@OverridesAttribute(constraint = Uri.class, name = "port")
	int[] port() default { };

	/**
	 * Indicates whether the URI must have a path part.
	 *
	 * @return {@code true} if the URI must have a path part, {@code false} otherwise.
	 * @see Uri#requiresPath
	 */
	@OverridesAttribute(constraint = Uri.class, name = "requiresPath")
	boolean requiresPath() default false;

	/**
	 * Indicates whether the URI must have a query part.
	 *
	 * @return {@code true} if the URI must have a query part, {@code false} otherwise.
	 * @see Uri#requiresQuery
	 */
	@OverridesAttribute(constraint = Uri.class, name = "requiresQuery")
	boolean requiresQuery() default false;

	/**
	 * Indicates whether the URI must have a fragment.
	 *
	 * @return {@code true} if the URI must have a fragment, {@code false} otherwise.
	 * @see Uri#requiresFragment
	 */
	@OverridesAttribute(constraint = Uri.class, name = "requiresFragment")
	boolean requiresFragment() default false;

	/**
	 * Indicates whether the URI must have a user-info part.
	 *
	 * @return {@code true} if the URI must have a user-info part, {@code false} otherwise.
	 * @see Uri#requiresUserInfo
	 */
	@OverridesAttribute(constraint = Uri.class, name = "requiresUserInfo")
	boolean requiresUserInfo() default false;

	String message() default "{net.nicholaswilliams.java.validation.NotNullUri.message}";

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
		NotNullUri[] value();
	}
}
