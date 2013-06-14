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
package net.nicholaswilliams.java.validation.support.el;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.validation.ConstraintDeclarationException;

import net.nicholaswilliams.java.validation.support.ExpressionValidator;
import net.nicholaswilliams.java.validation.support.ReflectionUtils;

/**
 * An expression validator that uses the Java Unified Expression Language.
 *
 * @author Nicholas Williams
 * @since 1.0.0
 */
public class JuelExpressionValidator extends ELContext implements ExpressionValidator
{
	private static final Method GET_STREAM_EL_RESOLVER =
			ReflectionUtils.getMethodIfExists(ExpressionFactory.class, "getStreamELResolver");

	private static final Method GET_INIT_FUNCTION_MAP =
			ReflectionUtils.getMethodIfExists(ExpressionFactory.class, "getInitFunctionMap");

	private static final ExpressionFactory EXPRESSION_FACTORY = ExpressionFactory.newInstance();

	private static final ELResolver ARRAY_EL_RESOLVER = new ArrayELResolver();
	private static final ELResolver BEAN_EL_RESOLVER = new BeanELResolver();
	private static final ELResolver LIST_EL_RESOLVER = new ListELResolver();
	private static final ELResolver MAP_EL_RESOLVER = new MapELResolver();
	private static final ELResolver RESOURCE_BUNDLE_EL_RESOLVER = new ResourceBundleELResolver();
	private static final ELResolver STREAM_EL_RESOLVER;

	private static final Map<String, Method> INIT_FUNCTION_MAP;

	static
	{
		// initialize EL 3.0 types
		STREAM_EL_RESOLVER = GET_STREAM_EL_RESOLVER == null ? null :
							 ReflectionUtils.invokeMethod(GET_STREAM_EL_RESOLVER, EXPRESSION_FACTORY, ELResolver.class);

		if(GET_INIT_FUNCTION_MAP != null)
		{
			@SuppressWarnings({ "unchecked", "UnnecessaryLocalVariable" })
			Map<String, Method> m = ReflectionUtils.invokeMethod(GET_INIT_FUNCTION_MAP, EXPRESSION_FACTORY, Map.class);
			INIT_FUNCTION_MAP = m;
		}
		else
		{
			INIT_FUNCTION_MAP = new HashMap<String, Method>();
		}
	}

	private final String expression;
	private final ValueExpression valueExpression;
	private final CompositeELResolver elResolver;
	private DefaultFunctionMapper functionMapper;
	private DefaultVariableMapper variableMapper;

	public JuelExpressionValidator(String expression, Map<String, Object> beans)
	{
		this.elResolver = new CompositeELResolver();
		this.elResolver.add(ARRAY_EL_RESOLVER);
		this.elResolver.add(BEAN_EL_RESOLVER);
		this.elResolver.add(new BeanNameELResolver(beans));
		this.elResolver.add(LIST_EL_RESOLVER);
		this.elResolver.add(MAP_EL_RESOLVER);
		this.elResolver.add(RESOURCE_BUNDLE_EL_RESOLVER);
		if(STREAM_EL_RESOLVER != null)
		{
			this.elResolver.add(STREAM_EL_RESOLVER);
		}

		this.expression = expression;
		try
		{
			this.valueExpression = EXPRESSION_FACTORY.createValueExpression(this, this.expression, Boolean.class);
		}
		catch(ELException e)
		{
			throw new ConstraintDeclarationException("The provided expression is not valid.", e);
		}
	}

	@Override
	public boolean validate()
	{
		Object evaluationResult;
		try
		{
			evaluationResult = this.valueExpression.getValue(this);
		}
		catch(ELException e)
		{
			throw new ConstraintDeclarationException(
					"Error occurred during execution of expression ${" + this.expression + "}."
			);
		}

		if(evaluationResult == null)
		{
			throw new ConstraintDeclarationException(
					"Execution of expression ${" + this.expression + "} returned null."
			);
		}

		if(!(evaluationResult instanceof Boolean))
		{
			throw new ConstraintDeclarationException(
					"Execution of expression ${" + this.expression + "} returned unsupported type [" +
					evaluationResult.getClass().getName() + "]."
			);
		}

		return Boolean.TRUE.equals(evaluationResult);
	}

	/*
	 * The methods below are specified in ELContext.
	 */

	@Override
	public ELResolver getELResolver()
	{
		return this.elResolver;
	}

	@Override
	public FunctionMapper getFunctionMapper()
	{
		if(this.functionMapper == null)
		{
			this.functionMapper = new DefaultFunctionMapper(INIT_FUNCTION_MAP);
		}
		return this.functionMapper;
	}

	@Override
	public VariableMapper getVariableMapper()
	{
		if(this.variableMapper == null)
		{
			this.variableMapper = new DefaultVariableMapper();
		}
		return this.variableMapper;
	}

	/**
	 * Serves as a function mapper.
	 */
	private static final class DefaultFunctionMapper extends FunctionMapper
	{
		private final Map<String, Method> functions;

		DefaultFunctionMapper(Map<String, Method> functions)
		{
			this.functions = functions == null ? new HashMap<String, Method>() : new HashMap<String, Method>(functions);
		}

		@Override
		public Method resolveFunction(String prefix, String localName)
		{
			return functions.get(prefix + ":" + localName);
		}

		/**
		 * This method is defined in EL 3.0 and must be defined here without {@code @Override} for
		 * backwards-compatibility.
		 *
		 * @param prefix The function prefix
		 * @param localName The function name
		 * @param method The method
		 */
		@SuppressWarnings("unused") // defined in EL 3.0
		public void mapFunction(String prefix, String localName, Method method)
		{
			this.functions.put(prefix + ":" + localName, method);
		}
	}

	/**
	 * Serves as a variable mapper.
	 */
	private static final class DefaultVariableMapper extends VariableMapper
	{
		private Map<String, ValueExpression> variables = null;

		@Override
		public ValueExpression resolveVariable(String name)
		{
			return this.variables == null ? null : this.variables.get(name);
		}

		@Override
		public ValueExpression setVariable(String name, ValueExpression value)
		{
			if(this.variables == null)
			{
				this.variables = new HashMap<String, ValueExpression>();
			}

			ValueExpression previous;
			if(value == null)
			{
				previous = this.variables.remove(name);
			}
			else
			{
				previous = this.variables.put(name, value);
			}
			return previous;
		}
	}

	/**
	 * Serves as a resolver for the bean or beans being validated.
	 */
	private static final class BeanNameELResolver extends ELResolver
	{
		private static final Method SET_PROPERTY_RESOLVED = ReflectionUtils.getMethodIfExists(
				ELContext.class, "setPropertyResolved", Object.class, Object.class
		);

		private final Map<String, Object> beans;

		public BeanNameELResolver(Map<String, Object> beans)
		{
			this.beans = beans;
		}

		@Override
		public Object getValue(ELContext context, Object base, Object property)
		{
			if(base == null && property instanceof String)
			{
				if(this.beans.containsKey(property))
				{
					this.setPropertyResolved(context, base, property);
					return this.beans.get(property);
				}
			}
			return null;
		}

		@Override
		public Class<?> getType(ELContext context, Object base, Object property)
		{
			if(base == null && property instanceof String)
			{
				if(this.beans.containsKey(property))
				{
					this.setPropertyResolved(context, base, property);
					return this.beans.get(property).getClass();
				}
			}
			return null;
		}

		private void setPropertyResolved(ELContext context, Object base, Object property)
		{
			if(SET_PROPERTY_RESOLVED != null)
			{
				ReflectionUtils.invokeMethod(SET_PROPERTY_RESOLVED, context, Void.class, base, property);
			}
			else
			{
				context.setPropertyResolved(true);
			}
		}

		@Override
		public void setValue(ELContext context, Object base, Object property, Object value)
		{
			throw new UnsupportedOperationException("Cannot set bean values using Bean Validation EL expressions.");
		}

		@Override
		public boolean isReadOnly(ELContext context, Object base, Object property)
		{
			return true;
		}

		@Override
		public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base)
		{
			return null;
		}

		@Override
		public Class<?> getCommonPropertyType(ELContext context, Object base)
		{
			return String.class;
		}
	}
}
