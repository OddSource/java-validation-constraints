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
package net.nicholaswilliams.java.validation.support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.nicholaswilliams.java.validation.NotBlank;

/**
 * Validator for {@link NotBlank}.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, CharSequence>
{
	@Override
	public void initialize(NotBlank notBlank)
	{
		// Nothing to do here
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context)
	{
		return value == null || value.toString().trim().length() > 0;
	}
}
