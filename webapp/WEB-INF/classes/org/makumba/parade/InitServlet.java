package org.makumba.parade;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;


public class InitServlet extends HttpServlet {
	
	static Logger logger = Logger.getLogger(InitServlet.class.getName());
	
    ServletContext context;
    
        public void init(ServletConfig conf) throws ServletException
        {
        	context = conf.getServletContext();
        	
        	System.out.println("init: Starting Parade initialization at "
                    + new java.util.Date());
            System.out.flush();
        	super.init();
            
        	 
        	/*
        	try {
        		RootSpring root = new RootSpring();
        		root.init();
        	} catch(Throwable t) {
            	logger.error(t); t.printStackTrace();
            }
        	 */
        	
        	try {
            	Root root = Root.getInstance();
                root.initRoot();
                
            } catch(Throwable t) {
            	logger.error(t); t.printStackTrace();
            }
            
            System.out.println("INFO: Launching ParaDe finished at "+new java.util.Date());
            
        }
        
}
    
    /*

    public void init(ServletConfig conf) {
        context = conf.getServletContext();

        new Thread(new Runnable() {
            public void run() {
                initialize();
            }
        }).start();
    }

    public void initialize() {
        System.out.println("init: Starting Parade initialization at "
                + new java.util.Date());
        System.out.flush();
        try {
            Config.invokeOperation("parade", "setParade",
                    new PageContextDummy() {
                        public ServletRequest getRequest() {
                            return new HttpServletRequestDummy() {
                                public String getServerName() {
                                    return "localhost";
                                }

                                public int getServerPort() {
                                    return Integer.parseInt(Config.getProperty(
                                            "http.port").trim());
                                }

                                public String getPathInfo() {
                                    return "/index.jsp";
                                }

                                public String getContextPath() {
                                    return "";
                                }

                            };
                        }
                    });
        } catch (ParadeException pe) {
            pe.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("init: Parade finished initializing at   "
                + new java.util.Date());
    }
    
    */

