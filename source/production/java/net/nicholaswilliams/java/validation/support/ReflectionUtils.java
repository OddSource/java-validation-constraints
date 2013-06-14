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

import java.lang.reflect.Method;
import javax.validation.ValidationException;

/**
 * A class of useful reflection utilities to help out constraint validators.
 *
 * @author Nicholas Williams
 * @version 1.0.0
 */
public final class ReflectionUtils
{
	private static final ClassLoader CLASS_LOADER = ReflectionUtils.class.getClassLoader();

	private ReflectionUtils()
	{

	}

	static boolean classExists(String name)
	{
		try
		{
			Class.forName(name, false, ReflectionUtils.CLASS_LOADER);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static Method getMethodIfExists(Class<?> c, String methodName, Class<?>... parameterTypes)
	{
		try
		{
			return c.getMethod(methodName, parameterTypes);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public static <T> T invokeMethod(Method method, Object object, Class<T> returnType, Object... arguments)
	{
		try
		{
			return (T) method.invoke(object, arguments);
		}
		catch(Exception e)
		{
			throw new ValidationException("Failed to invoke method.", e);
		}
	}
}
