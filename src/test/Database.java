package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database {
	
	//static String driver = "org.sqlite.JDBC";
	private String driver = "org.sqlite.JDBC";
	
	//How do we d this so it wont be just local database?
	private String url = "jdbc:mysql://loclalhost:3306/wobblebeatscores";
	//private String url = "jdbc:mysql://loclalhost/wobblebeatscores";
	private String user = "root";
	private String password = "";
	
	
	private Connection con;
	
	public Database() 
	{
		
	}
	
	public void connect() throws ClassNotFoundException
	{
		/*
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		if(con == null)
		{
			System.out.print("Failed");
		}
		*/
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wobblebeatscores",user,password);
			
			if(con != null)
			{
				System.out.println("CONNECTED");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	

	
	public void loadScores()
	{
		
	}
	
	public void writeScore(Score score)
	{
		
	}
	
	public void printScores()
	{
		System.out.println("Testing database clase");
	}

}