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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Constraint annotation that ensures that the target is within the given range. The value is inclusive, so
 * {@link #min()}{@code ≤ value ≤ } {@link #max}.<br>
 * <br>
 * Supported types are:
 * <ul>
 *     <li>{@link java.math.BigDecimal}</li>
 *     <li>{@link java.math.BigInteger}</li>
 *     <li>{@link byte}, {@link short}, {@link int}, and {@link long}, and their respective wrappers.</li>
 * </ul>
 * {@link double} and {@link float} values are not supported due to rounding issues.<br>
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
@Constraint(validatedBy = { })
@ReportAsSingleViolation
@Min(0)
@Max(Long.MAX_VALUE)
public @interface Range
{
	/**
	 * The minimum value allowed.
	 *
	 * @return the minimum.
	 */
	@OverridesAttribute(constraint = Min.class, name = "value")
	long min() default 0;

	/**
	 * The maximum value allowed.
	 *
	 * @return the maximum.
	 */
	@OverridesAttribute(constraint = Max.class, name = "value")
	long max() default Long.MAX_VALUE;

	String message() default "{net.nicholaswilliams.java.validation.Range.message}";

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
		Range[] value();
	}
}
