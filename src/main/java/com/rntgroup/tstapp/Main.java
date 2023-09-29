package com.rntgroup.tstapp;

import com.rntgroup.tstapp.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		Application application = applicationContext.getBean("application", Application.class);
		application.run();
	}
}
