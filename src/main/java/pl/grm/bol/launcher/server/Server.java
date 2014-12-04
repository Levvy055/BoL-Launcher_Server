package pl.grm.bol.launcher.server;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.grm.bol.lib.FileOperation;

public class Server {
	private static Logger		logger;
	private static Registry		registry;
	private static InetAddress	myHost	= null;
	private static String		login, pass;
	private static int			PORT	= 2234;
	private static ServerDBImpl	dbHandler;
	
	public static void main(String[] args) {
		try {
			logger = FileOperation.setupLogger("server.log");
			if (args.length == 2) {
				login = args[0];
				pass = args[1];
			} else {
				throw new NullPointerException("Bad arguments! \n Should be: login pass");
			}
			logger.info("Starting Server");
			registry = LocateRegistry.createRegistry(PORT);
			myHost = InetAddress.getLocalHost();
			dbHandler = new ServerDBImpl(logger);
			dbHandler.prepareConnection(login, pass);
			registry.bind("dBConfBindHandler", dbHandler);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		finally {
			if (registry != null && myHost != null && dbHandler != null) {
				logger.info("Server should be running");
				logger.info("My Host/IP: " + myHost);
				logger.info("Running on port: " + PORT);
			} else {
				logger.log(Level.SEVERE, "Server not started. Smth went wrong.");
			}
		}
		logger.info("Testing database connection ... ");
		if (dbHandler.isConnectionCorrect()) {
			logger.info("Connected succesfully.");
		} else {
			logger.info("Connection error. Exiting ...");
			System.exit(0);
		}
	}
}
