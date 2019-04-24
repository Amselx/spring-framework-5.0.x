/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.context.annotation;

import java.util.function.Supplier;

import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 *
 * AnnotaionConfigApplicationContext
 * 这个类拥的父类GenericApplicationContext构建出了一个工厂DefaultListableBeanFactory
 * GenericApplicationContext，可以注册BeanDefinition
 * 所以AnnotaionConfigApplicationContext环境拥有了一个工厂，拥有了BeanDefinition的能力
 * BeanDefinitionRegistry = GenericApplicationContext = AnnotaionConfigApplicationContext
 *
 * Standalone application context, accepting annotated classes as input - in particular
 * {@link Configuration @Configuration}-annotated classes, but also plain
 * {@link org.springframework.stereotype.Component @Component} types and JSR-330 compliant
 * classes using {@code javax.inject} annotations. Allows for registering classes one by
 * one using {@link #register(Class...)} as well as for classpath scanning using
 * {@link #scan(String...)}.
 *
 * <p>In case of multiple {@code @Configuration} classes, @{@link Bean} methods defined in
 * later classes will override those defined in earlier classes. This can be leveraged to
 * deliberately override certain bean definitions via an extra {@code @Configuration}
 * class.
 *
 * <p>See @{@link Configuration}'s javadoc for usage examples.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 3.0
 * @see #register
 * @see #scan
 * @see AnnotatedBeanDefinitionReader
 * @see ClassPathBeanDefinitionScanner
 * @see org.springframework.context.support.GenericXmlApplicationContext
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

	private final AnnotatedBeanDefinitionReader reader;

	private final ClassPathBeanDefinitionScanner scanner;


	/**
	 * Create a new AnnotationConfigApplicationContext that needs to be populated
	 * through {@link #register} calls and then manually {@linkplain #refresh refreshed}.
	 */
	public AnnotationConfigApplicationContext() {
		/**
		 * 1.
		 * 调用父类GenericApplicationContext构造
		 * 创建DefaultListableBeanFactory工厂
		 */

		/**
		 * 2.
		 * BeanDefinition读取器
		 * 将类转换为bd
		 * 注册一系列spring内部的bd
		 * 其中有
		 * BeanDefinitionRegistry = this(AnnotationConfigApplicationContext)
		 * spring内部用的扫描器并不是这个，会重新创建一个
		 *
		 *
		 * this是BeanDefinitionRegistry  那么AnnotationConfigApplicationContext就是BeanDefinitionRegistry
		 * 因为父类GenericApplicationContext实现了BeanDefinitionRegistry接口
		 * BeanDefinitionRegistry接口中的方法由一开始创建的工厂DefaultListableBeanFactory实现。
		 * 而工厂也是个BeanDefinitionRegistry， 那么AnnotationConfigApplicationContext 可以说是=DefaultListableBeanFactory
		 *
		 *
		 * reader可以将对象转换成bean definition，然后将转换的bean definition 装入DefaultListableBeanFactory（this）
		 */
		this.reader = new AnnotatedBeanDefinitionReader(this);


		/**
		 * 3.
		 * 扫描被加了注解的类，转换为BeanDefinition后加入BeanFactory中的BeanDefinitionMap
		 * spring内部并没有使用这个扫描器
		 */
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * Create a new AnnotationConfigApplicationContext with the given DefaultListableBeanFactory.
	 * @param beanFactory the DefaultListableBeanFactory instance to use for this context
	 */
	public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * Create a new AnnotationConfigApplicationContext, deriving bean definitions
	 * from the given annotated classes and automatically refreshing the context.
	 * @param annotatedClasses one or more annotated classes,
	 * e.g. {@link Configuration @Configuration} classes
	 */
	public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
		this();
		register(annotatedClasses);
		refresh();
	}

	/**
	 * Create a new AnnotationConfigApplicationContext, scanning for bean definitions
	 * in the given packages and automatically refreshing the context.
	 * @param basePackages the packages to check for annotated classes
	 */
	public AnnotationConfigApplicationContext(String... basePackages) {
		this();
		scan(basePackages);
		refresh();
	}


	/**
	 * Propagates the given custom {@code Environment} to the underlying
	 * {@link AnnotatedBeanDefinitionReader} and {@link ClassPathBeanDefinitionScanner}.
	 */
	@Override
	public void setEnvironment(ConfigurableEnvironment environment) {
		super.setEnvironment(environment);
		this.reader.setEnvironment(environment);
		this.scanner.setEnvironment(environment);
	}

	/**
	 * Provide a custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
	 * and/or {@link ClassPathBeanDefinitionScanner}, if any.
	 * <p>Default is {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
	 * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
	 */
	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.reader.setBeanNameGenerator(beanNameGenerator);
		this.scanner.setBeanNameGenerator(beanNameGenerator);
		getBeanFactory().registerSingleton(
				AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
	}

	/**
	 * Set the {@link ScopeMetadataResolver} to use for detected bean classes.
	 * <p>The default is an {@link AnnotationScopeMetadataResolver}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 */
	public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
		this.reader.setScopeMetadataResolver(scopeMetadataResolver);
		this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
	}


	//---------------------------------------------------------------------
	// Implementation of AnnotationConfigRegistry
	//---------------------------------------------------------------------

	/**
	 * Register one or more annotated classes to be processed.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 * @param annotatedClasses one or more annotated classes,
	 * e.g. {@link Configuration @Configuration} classes
	 * @see #scan(String...)
	 * @see #refresh()
	 */
	public void register(Class<?>... annotatedClasses) {
		Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
		this.reader.register(annotatedClasses);
	}

	/**
	 * Perform a scan within the specified base packages.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 * @param basePackages the packages to check for annotated classes
	 * @see #register(Class...)
	 * @see #refresh()
	 */
	public void scan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		this.scanner.scan(basePackages);
	}


	//---------------------------------------------------------------------
	// Convenient methods for registering individual beans
	//---------------------------------------------------------------------

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations, and optionally providing explicit constructor
	 * arguments for consideration in the autowiring process.
	 * <p>The bean name will be generated according to annotated component rules.
	 * @param annotatedClass the class of the bean
	 * @param constructorArguments argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.0
	 */
	public <T> void registerBean(Class<T> annotatedClass, Object... constructorArguments) {
		registerBean(null, annotatedClass, constructorArguments);
	}

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations, and optionally providing explicit constructor
	 * arguments for consideration in the autowiring process.
	 * @param beanName the name of the bean (may be {@code null})
	 * @param annotatedClass the class of the bean
	 * @param constructorArguments argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.0
	 */
	public <T> void registerBean(@Nullable String beanName, Class<T> annotatedClass, Object... constructorArguments) {
		this.reader.doRegisterBean(annotatedClass, null, beanName, null,
				bd -> {
					for (Object arg : constructorArguments) {
						bd.getConstructorArgumentValues().addGenericArgumentValue(arg);
					}
				});
	}

	@Override
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass, @Nullable Supplier<T> supplier,
			BeanDefinitionCustomizer... customizers) {

		this.reader.doRegisterBean(beanClass, supplier, beanName, null, customizers);
	}

}
