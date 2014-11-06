package pl.grm.bolls;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientTest {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 1234);
			LauncherDB dbHandler = (LauncherDB) registry.lookup("dBConfBindHandler");
			Result result = dbHandler.checkIfExists("ann");
			System.out.println(result.getResultString());
			
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
}
