package pl.grm.bolls;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import pl.grm.boll.lib.LauncherDB;
import pl.grm.boll.lib.Player;
import pl.grm.boll.lib.Result;

public class ServerDBImpl extends UnicastRemoteObject implements LauncherDB {
	private static final String	PORT		= "3306";
	private static final String	IP			= "91.230.204.135";
	private String				dbLogin;
	private String				dbPasswd;
	private Statement			statement	= null;
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
		URL = "jdbc:mysql://" + IP + ":" + PORT + "/BattleOfLegends?user=" + dbLogin
				+ "&password=" + dbPasswd;
	}
	
	@Override
	public Result checkIfExists(String str) {
		logger.info("Client connected");
		ResultSet rs = null;
		Result result = new Result(1);
		try {
			rs = executeQuery("SELECT login from bol_users WHERE login='" + str + "';");
			if (!rs.next()) {
				result.setException(new Exception());
			} else {
				if (rs.next()) {
					String tempS = rs.getString("login");
					result.setResultString(tempS);
					logger.info("Result gathered: String: " + tempS);
				}
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
	public Result checkPasswd(String login, String hPasswd) {
		logger.info("Client connected. checkPasswd");
		ResultSet rs = null;
		Result result = new Result(2);
		try {
			rs = executeQuery("SELECT password FROM bol_users WHERE login='" + login
					+ "' AND password='" + hPasswd + "';");
			if (rs.next()) {
				result.setResultBoolean(true);
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
	public Result checkIfActivated(String str) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Result checkSalt(String login) throws RemoteException {
		logger.info("Client connected. checkSalt");
		ResultSet rs = null;
		Result result = new Result(4);
		try {
			rs = executeQuery("SELECT salt FROM bol_users WHERE login='" + login + "';");
			if (rs.next()) {
				result.setResultString(rs.getString("salt"));
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
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			closeConn(null);
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
