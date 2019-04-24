package com.kam;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyFactory<T> implements FactoryBean<T>,InvocationHandler {

	private final Class<T> clazz;

	public MyFactory(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T getObject() throws Exception {
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, this);
	}

	@Override
	public Class<?> getObjectType() {
		return clazz;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Annotation[] annotations = method.getAnnotations();
		if (method.isAnnotationPresent(Select.class)) {
			Select annotation = method.getAnnotation(Select.class);
			System.out.println(annotation.value());
		}
		return null;
	}
}
