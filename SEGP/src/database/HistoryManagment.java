package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class HistoryManagment {
	private static Connection c = null;
	private static PreparedStatement perp = null;
	@SuppressWarnings("unused")
	private static java.util.Date date = new java.util.Date();
	@SuppressWarnings("unused")
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	public static void CreateDataBase() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:History.db");
			System.out.println("Opened database successfully");
			perp = c.prepareStatement(
					"CREATE TABLE if not exists history(url text primary key ,Time text,Date DEFAULT CURRENT_DATE );");
			perp.executeUpdate();
			System.out.println("table created");
			perp.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("issues");
		}
	}

	public static void insertUrl(String url) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formate_time = new SimpleDateFormat("HH:mm:ss");

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:History.db");
			String insert = "insert or replace into history(url,Time)" + "values(?,?)";
			String time = formate_time.format(date);
			perp = c.prepareStatement(insert);
			perp.setString(1, url);
			perp.setString(2, time);
			perp.executeUpdate();
			System.out.println("data has been inserted");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

	}
	// ---------------------------------Full HISTORY
	// Show-----------------------------------------//

	public static ResultSet showHistory() {
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:History.db");
			String str = "select * from(select * from history order by Time DESC) history order by Date DESC";
			perp = c.prepareStatement(str);
			rs = perp.executeQuery();
		} catch (Exception e) {
			System.out.println("There are issues while showing the history");
		}
		return rs;
	}
}