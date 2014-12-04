package pl.grm.bol.launcher.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.grm.bol.lib.LauncherDB;
import pl.grm.bol.lib.Result;
import pl.grm.bol.lib.game.Player;

public class ServerDBImpl extends UnicastRemoteObject implements LauncherDB {
	private static final long	serialVersionUID	= 1L;
	private static final String	PORT				= "3306";
	private static final String	IP					= "91.230.204.135";
	private String				dbLogin;
	private String				dbPasswd;
	private Statement			statement			= null;
	private String				URL;
	private Connection			connection;
	private Logger				logger;
	
	public ServerDBImpl(Logger logger) throws RemoteException {
		super();
		this.logger = logger;
	}
	
	public void prepareConnection(String login, String pass) {
		this.dbLogin = login;
		this.dbPasswd = pass;
		URL = "jdbc:mysql://" + IP + ":" + PORT + "/BattleOfLegends?user=" + dbLogin + "&password="
				+ dbPasswd;
	}
	
	@Override
	public Result checkIfExists(String str) {
		logger.info("Client connected /n checkIfExists");
		ResultSet rs = null;
		Result result = new Result(1);
		try {
			String query = "SELECT login from bol_users WHERE login='" + str + "';";
			logger.info("Query execute: " + query);
			rs = executeQuery(query);
			if (rs.next()) {
				String tempS = rs.getString("login");
				result.setResultBoolean(true);
				result.setResultString(tempS);
				logger.info("Result gathered: String: " + tempS);
			} else {
				result.setResultBoolean(false);
			}
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			result.setException(e);
		}
		finally {
			closeConn(rs);
		}
		return result;
	}
	
	@Override
	public Result checkPasswd(String login, String hPasswd) {
		logger.info("Client connected /n checkPasswd on " + login);
		ResultSet rs = null;
		Result result = new Result(2);
		try {
			String query = "SELECT password FROM bol_users WHERE login='" + login
					+ "' AND password='" + hPasswd + "';";
			logger.info("Query execute: " + query);
			rs = executeQuery(query);
			if (rs.next()) {
				result.setResultBoolean(true);
			} else {
				result.setResultBoolean(false);
			}
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		finally {
			closeConn(rs);
		}
		return result;
	}
	
	@Override
	public Result checkIfActivated(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result checkSalt(String login) throws RemoteException {
		logger.info("Client connected /n checkSalt");
		ResultSet rs = null;
		Result result = new Result(4);
		try {
			rs = executeQuery("SELECT salt FROM bol_users WHERE login='" + login + "';");
			if (rs.next()) {
				result.setResultString(rs.getString("salt"));
				logger.info(rs.getString("salt"));
			}
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		finally {
			closeConn(rs);
		}
		return result;
	}
	
	@Override
	public Player getPlayerData(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ResultSet executeQuery(String query) throws SQLException {
		connection = DriverManager.getConnection(URL);
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		return rs;
	}
	
	private void closeConn(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
	}
	
	public boolean isConnectionCorrect() {
		boolean conn = false;
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			closeConn(null);
			conn = true;
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
		}
		return conn;
	}
	
	@Override
	public Result getPlayerPermissionLevel(String login) throws RemoteException {
		logger.info("Client connected /n getPlayerPermLvl");
		ResultSet rs = null;
		Result result = new Result(1);
		try {
			String query = "SELECT perm_level from bol_users WHERE login='" + login + "';";
			logger.info("Query execute: " + query);
			rs = executeQuery(query);
			if (rs.next()) {
				int tempI = rs.getInt("perm_level");
				result.setResultBoolean(true);
				result.setResultInt(tempI);
				logger.info("Result gathered: Int: " + tempI);
			} else {
				result.setResultBoolean(false);
			}
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			result.setException(e);
		}
		finally {
			closeConn(rs);
		}
		return result;
	}
}
