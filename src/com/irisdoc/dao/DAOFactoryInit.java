package com.irisdoc.dao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listener d'initialisation de la DAO Factory
 */
public class DAOFactoryInit implements ServletContextListener {
	private DAOFactory daoFactory;
	
	private static final String DAO_FACTORY = "daofactory";

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent contextEvent)  {
    	DAOFactory.closePool();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent contextEvent)  {
        // Initialisation de la factory
    	ServletContext servletContext = contextEvent.getServletContext();
        this.daoFactory = DAOFactory.getInstance();
        servletContext.setAttribute(DAO_FACTORY, this.daoFactory);
    }
}