package pl.grm.bolls;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerDBImpl extends UnicastRemoteObject implements LauncherDB {
	private String		dbLogin;
	private String		dbPasswd;
	ResultSet			rs			= null;
	Statement			statement	= null;
	private String		URL;
	private Connection	connection;
	
	protected ServerDBImpl() throws RemoteException {
		super();
	}
	
	public void prepareConnection(String login, String pass) {
		this.dbLogin = login;
		this.dbPasswd = pass;
		URL = "jdbc:mysql://91.230.204.135:3306/BattleOfLegends?user=" + dbLogin + "&password="
				+ dbPasswd;
	}
	
	private ResultSet executeQuery(String query) throws SQLException {
		connection = DriverManager.getConnection(URL);
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		return rs;
	}
}
