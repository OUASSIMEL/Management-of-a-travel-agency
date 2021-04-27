package application;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {
	
	public static Connection getConnection() {
		Connection connection ;
		try {
			//hostgator server 108.167.172.168
			//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/advtt?user=root&password=majid");
			//connection = DriverManager.getConnection("jdbc:mysql://almokhtassar.com:3306/ouassim_agencedevoyage?user=ouassim_advj&password=advj2021");
			connection = DriverManager.getConnection("jdbc:mysql://almokhtassar.com:3306/ouassim_agencedevoyage", "ouassim_advj", "advj2021");
			return connection;
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			e.fillInStackTrace();
			return null;
		}
	}
}
