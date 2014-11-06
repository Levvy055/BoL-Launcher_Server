package pl.grm.bolls;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerDBImpl extends UnicastRemoteObject implements LauncherDB {
	private static final String	PORT		= "3306";
	private static final String	IP			= "91.230.204.135";
	private String				dbLogin;
	private String				dbPasswd;
	ResultSet					rs			= null;
	Statement					statement	= null;
	private String				URL;
	private Connection			connection;
	
	public ServerDBImpl() throws RemoteException {
		super();
	}
	
	public void prepareConnection(String login, String pass) {
		this.dbLogin = login;
		this.dbPasswd = pass;
		URL = "jdbc:mysql://" + IP + ":" + PORT + "/BattleOfLegends?user=" + dbLogin + "&password="
				+ dbPasswd;
	}
	
	@Override
	public Result checkIfExists(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result checkPasswd(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result checkIfActivated(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Player getPlayerData(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ResultSet executeQuery(String query) throws SQLException {
		connection = DriverManager.getConnection(URL);
		statement = connection.createStatement();
		rs = statement.executeQuery(query);
		return rs;
	}
}
