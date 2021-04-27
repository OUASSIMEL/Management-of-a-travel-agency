package application;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	/*public static Connection getConnection() {
		Connection connection = null;
		try {
			//ec2-54-163-47-62.compute-1.amazonaws.com
			//hostgator server 108.167.172.168 //gator4265.hostgator.com //ns8529.hostgator.com //ns8530.hostgator.com
			//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/advtt?user=root&password=majid");
		    //connection = DriverManager.getConnection("jdbc:mysql://185.27.134.10:3306/epiz_27824314_adv?user=epiz_27824314&password=2ynPU4kFU4fchI");
			String url = "jdbc:postgresql://ec2-54-163-47-62.compute-1.amazonaws.com/d4ngsnqhj6immd";
			Properties props = new Properties();
			props.setProperty("zgvyflcmjxucjr","fred");
			props.setProperty("c605d18fb73811402b6bd857b1f7a52d39e27be9defda6531e5866db12d486a8","secret");
			props.setProperty("ssl","true");
			Connection conn = DriverManager.getConnection(url, props);

			String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
			Connection conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			System.out.println("Error: "+e.getMessage());
			e.fillInStackTrace();
		}
		return connection;
	}*/

	public static Connection getConnection() throws URISyntaxException, SQLException {
		String host = "ec2-54-163-47-62.compute-1.amazonaws.com";
		String port = "5432";
		String database = "d4ngsnqhj6immd";
		String user = "zgvyflcmjxucjr";
		String password = "c605d18fb73811402b6bd857b1f7a52d39e27be9defda6531e5866db12d486a8";
		return DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+database+"?user="+user+"&password="+password+"&sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
	}
}