package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
	
	//Bryans boiler player code
	static String driver = "org.sqlite.JDBC";
	String url = "jdbc:sqlite:tunes.sqlite";
	
	static
	{
		try
		{
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public Database()
	{
		
	}
	
	public void loadScores()
	{
		
	}
	
	public void printScores()
	{
		System.out.println("Testing database clase");
	}

}