package org.makumba.parade;

import java.io.File;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class RootSpring extends HibernateDaoSupport {

	static final Long one= new Long(1);
	public ApplicationContext ctx = null;

	public void init() {
	
		System.out.println("INFO: Session ...");
		Parade parade = (Parade) ctx.getBean("Parade");
		parade.id= one;
		
	}
	
	public RootSpring() {
		//TODO make private constructor to protect from mult instances
		String[] paths = {"applicationContext.xml"};
        ctx = new ClassPathXmlApplicationContext(paths);
	}
}
