package pl.grm.bolls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
	private static Logger		logger;
	private static FileHandler	fh;
	private static Registry		registry;
	private static InetAddress	myHost	= null;
	private static String		myIP, login, pass;
	private static int			PORT	= 1234;
	private static ServerDBImpl	dbHandler;
	
	public static void main(String[] args) {
		try {
			setupLogger();
			checkArgs(args);
			logger.info("Starting Server");
			registry = LocateRegistry.createRegistry(PORT);
			myHost = InetAddress.getLocalHost();
			myIP = myHost.getHostAddress();
			dbHandler = new ServerDBImpl(logger);
			dbHandler.prepareConnection(login, pass);
			registry.bind("dBConfBindHandler", dbHandler);
		}
		catch (NullPointerException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		catch (RemoteException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		catch (AlreadyBoundException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		catch (UnknownHostException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
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
	
	private static void setupLogger() {
		logger = Logger.getLogger(Server.class.getName());
		try {
			fh = new FileHandler("server.log", 1048576, 1, true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		}
		catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}
	
	private static void checkArgs(String[] args) {
		if (args.length == 2) {
			login = args[0];
			pass = args[1];
		} else {
			throw new NullPointerException("Bad arguments! \n Should be: login pass");
		}
		
	}
}
