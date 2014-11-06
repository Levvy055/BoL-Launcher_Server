package pl.grm.bolls;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import pl.grm.boll.lib.LauncherDB;
import pl.grm.boll.lib.Result;

public class ClientTest {
	public static void main(String[] args) {
		try {
			LauncherDB dbHandler = connect();
			checkIfExists(dbHandler);
			checkPasswd(dbHandler);
			checkIfActivated(dbHandler);
			getPlayerData(dbHandler);
		}
		catch (RemoteException e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static LauncherDB connect() throws RemoteException, NotBoundException, AccessException {
		Registry registry = LocateRegistry.getRegistry("localhost", 1234);
		LauncherDB dbHandler = (LauncherDB) registry.lookup("dBConfBindHandler");
		return dbHandler;
	}
	
	private static void checkIfExists(LauncherDB dbHandler) throws RemoteException {
		Result result = dbHandler.checkIfExists("bann");
		String str = result.getResultString();
		System.out.println(str);
	}
	
	private static void checkPasswd(LauncherDB dbHandler) {
		// TODO Auto-generated method stub
		
	}
	
	private static void checkIfActivated(LauncherDB dbHandler) {
		// TODO Auto-generated method stub
		
	}
	
	private static void getPlayerData(LauncherDB dbHandler) {
		// TODO Auto-generated method stub
		
	}
}
