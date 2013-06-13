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
import javax.validation.constraints.Pattern;

/**
 * Composite constraint annotation that enforces {@link NotNull @NotNull} and {@link Pattern @Pattern}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 * @see Pattern
 * @see java.util.regex.Pattern
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		  ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@NotNull
@Pattern(regexp = "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n", flags = { })
public @interface NotNullPattern
{
	/**
	 * The regular expression to match.
	 *
	 * @return the regular expression.
	 * @see java.util.regex.Pattern
	 */
	@OverridesAttribute(constraint = Pattern.class, name = "regexp")
	String regexp();

	/**
	 * The {@link Pattern.Flag}s to consider when matching the expression.
	 *
	 * @return the regular expression flags.
	 */
	@OverridesAttribute(constraint = Pattern.class, name = "flags")
	Pattern.Flag[] flags() default { };

	String message() default "{net.nicholaswilliams.java.validation.NotNullPattern.message}";

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
		NotNullPattern[] value();
	}
}
