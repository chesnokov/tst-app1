package com.rntgroup.tstapp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext annotationContext= new AnnotationConfigApplicationContext("com.rntgroup.tstapp");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String [] {"beans.xml"}, annotationContext);
		Application application = applicationContext.getBean("application", Application.class);
		application.run();
	}
}
