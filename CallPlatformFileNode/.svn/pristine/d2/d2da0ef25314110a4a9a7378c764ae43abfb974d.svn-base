package util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringUtil implements BeanFactoryAware {

	private static BeanFactory beanFactory = null;

	private static SpringUtil factoryUtils = null;
	
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		SpringUtil.beanFactory = beanFactory;
	}

	public static BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static SpringUtil getInstance() {
		if (factoryUtils == null) {
			factoryUtils = new SpringUtil();
		}
		return factoryUtils;
	}

	public static Object getBeanByName(String name) {
		return beanFactory.getBean(name);
	}

}
