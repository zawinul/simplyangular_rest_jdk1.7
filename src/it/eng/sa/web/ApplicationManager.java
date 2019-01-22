package it.eng.sa.web;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationManager {
	
	public static class ContextListener implements  ServletContextListener{
		public void contextDestroyed(ServletContextEvent arg0) {
			try {
				System.out.println(arg0.getServletContext().getContextPath()+" shutdown  on "+(new Date()));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {}
		}

		public void contextInitialized(ServletContextEvent arg0) {
			try {
				System.out.println(arg0.getServletContext().getContextPath()+" started  on "+(new Date()));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {}
		}
	}
	
}
