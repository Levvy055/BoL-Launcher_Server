package pl.grm.bolls;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ServerLogger {
	private String		APP_DATA		= System.getenv("APPDATA");
	private String		BoL_Conf_Loc	= APP_DATA + "\\BoL-Server\\";
	private String		logFileName		= "server.log";
	private Logger		logger;
	private FileHandler	fHandler;
	
	public ServerLogger() {
		setupLogger();
	}
	
	private void setupLogger() {
		File dir = new File(BoL_Conf_Loc);
		if (!dir.exists()) {
			dir.mkdir();
		}
		logger = Logger.getLogger(ServerLogger.class.getName());
		try {
			fHandler = new FileHandler(BoL_Conf_Loc + logFileName, 1048476, 1, true);
			logger.addHandler(fHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fHandler.setFormatter(formatter);
		}
		catch (SecurityException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		logger.info("Server Log Location: " + BoL_Conf_Loc);
	}
	
	public Logger getLogger() {
		return this.logger;
	}
}
