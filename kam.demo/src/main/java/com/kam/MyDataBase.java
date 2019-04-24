package com.kam;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyDataBase implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(Dao.class);
		AbstractBeanDefinition beanDefinition = bdb.getBeanDefinition();

		beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
		beanDefinition.setBeanClass(MyFactory.class);
		registry.registerBeanDefinition("sqlDao", beanDefinition);
	}
}
