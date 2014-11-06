package pl.grm.bolls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
	private static Logger		log;
	private static FileHandler	fh;
	private static Registry		registry;
	private static InetAddress	myHost	= null;
	private static String		myIP;
	
	public static void main(String[] args) {
		setupLogger();
		log.info("Starting Server");
		createRegistry();
		setupHost();
		log.info("Server stopping");
	}
	
	private static void createRegistry() {
		try {
			registry = LocateRegistry.createRegistry(1234);
		}
		catch (RemoteException e) {
			log.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}
	
	private static void setupHost() {
		try {
			myHost = InetAddress.getLocalHost();
			myIP = myHost.getHostAddress();
		}
		catch (UnknownHostException e) {
			log.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		log.log(Level.INFO, "My Host/IP: " + myHost);
	}
	
	private static void setupLogger() {
		log = Logger.getLogger(Server.class.getName());
		try {
			fh = new FileHandler("server.log", 1048576, 1, true);
		}
		catch (SecurityException | IOException e) {
			log.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		log.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
	}
}
