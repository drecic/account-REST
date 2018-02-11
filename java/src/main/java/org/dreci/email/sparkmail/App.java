package org.drecic.email.sparkmail;

import spark.Spark;

import org.bgalusha.email.sparkmail.dao.postgres.DatabaseConnection;

public class App {
		
	public static void logFatal(Throwable error) {
		error.printStackTrace();
	}
	
	public static void teardown() throws Throwable {
		// in the event of a shutdown, this code will be run to gracefully shut down the app
		
		// Close the Postgresql database connection
		if (DatabaseConnection.hasInstance()) {
			DatabaseConnection.getInstance().getConnection().close();
		}
	}
	
	public static void run(String[] args) throws ServiceException {
		Spark.port(8080);
		Routes.setupRoutes();
	}
	
    public static void main(String[] args) {
    	
    	try {
    		App.run(args);
    	} catch (Throwable t) {
    		logFatal(t);
    	} finally {
    		
    		try {
    			App.teardown();
    		} catch (Throwable t) {
    			System.err.println("WARNING: Graceful teardown failed");
    			logFatal(t);
    		}
    	
    	}
    	
    	
    }
}