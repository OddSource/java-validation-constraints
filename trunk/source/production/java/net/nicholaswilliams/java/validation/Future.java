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
 * Constraint annotation that ensures that the target is in the future.<br>
 * <br>
 * For the following types, this ensures that the date and time are after the current date and time. For types that
 * include a time zone, the instant in time is compared to the current instant in time. For types that do not include
 * a time zone, the time zone is assumed to be the system default time zone.
 * <ul>
 *     <li>{@link java.util.Date}</li>
 *     <li>{@link java.util.Calendar}</li>
 *     <li>{@link org.joda.time.DateTime} (requires Joda Time)</li>
 *     <li>{@link org.joda.time.Instant} (requires Joda Time)</li>
 *     <li>{@link org.joda.time.LocalDateTime} (requires Joda Time)</li>
 *     <li>{@link org.joda.time.MutableDateTime} (requires Joda Time)</li>
 *     <li>{@code java.time.Clock} (requires Java SE 8)</li>
 *     <li>{@code java.time.Instant} (requires Java SE 8)</li>
 *     <li>{@code java.time.LocalDateTime} (requires Java SE 8)</li>
 *     <li>{@code java.time.OffsetDateTime} (requires Java SE 8)</li>
 *     <li>{@code java.time.ZonedDateTime} (requires Java SE 8)</li>
 * </ul>
 * <br>
 * For the following types, this ensures that the date is after the current date.
 * <ul>
 *     <li>{@link org.joda.time.DateMidnight} (requires Joda Time)</li>
 *     <li>{@link org.joda.time.LocalDate} (requires Joda Time)</li>
 *     <li>{@code java.time.LocalDate} (requires Java SE 8)</li>
 * </ul>
 * <br>
 * For the following types, this ensures that the time is after the current time on the same day. For types that include
 * an offset (such as a time zone), the time is adjusted to the offset for the current date and time. For types that do
 * not include an offset, the offset is assumed to be the system default offset for the current date and time.
 * <ul>
 *     <li>{@link org.joda.time.LocalTime} (requires Joda Time)</li>
 *     <li>{@code java.time.LocalTime} (requires Java SE 8)</li>
 *     <li>{@code java.time.OffsetTime} (requires Java SE 8)</li>
 * </ul>
 * <br>
 * If the type is {@link org.joda.time.Months} (requires Joda Time) or {@code java.time.Month} (requires Java SE 8),
 * this ensures that the month is after the current month in the same year.<br>
 * <br>
 * If the type is {@link org.joda.time.Years} (requires Joda Time) or {@code java.time.Year} (requires Java SE 8),
 * this ensures that the year is after the current year.<br>
 * <br>
 * If the type is {@link org.joda.time.Days} (requires Joda Time) or {@code java.time.DayOfWeek} (requires Java SE 8),
 * this ensures that the day is after the current day of the week.<br>
 * <br>
 * If the type is {@link org.joda.time.YearMonth} (requires Joda Time) or {@code java.time.YearMonth} (requires Java SE
 * 8), this ensures that the either the year is the same and the month is after the current month, or that the year
 * is after the current year. This is useful, for example, for checking whether a credit card expiration date indicates
 * that it is still valid.<br>
 * <br>
 * {@code null} values are considered valid.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 * @see Past
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		  ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { }) //TODO: Implement validators
public @interface Future
{
	String message() default "{net.nicholaswilliams.java.validation.Future.message}";

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
		Future[] value();
	}
}
