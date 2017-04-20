package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class Database {
	
	//static String driver = "org.sqlite.JDBC";
	private String driver = "org.sqlite.JDBC";
	
	//How do we d this so it wont be just local database?
	private String url = "jdbc:mysql://localhost:3306/wobblebeatscores";
	private String user = "root";
	private String password = "";
	
	ResultSet rs;
	private Connection con;
	
	public Database() 
	{
		
	}
	
	public void connect() throws ClassNotFoundException
	{
	
		try{
			Connection con = DriverManager.getConnection(url,user,password);
			
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
		try(Connection c = DriverManager.getConnection(url,user,password);
				PreparedStatement ps = c.prepareStatement("select * from scores"))
		{
			rs = ps.executeQuery();
			
			while(rs.next())
			{
					int index = 1;
					String val = rs.getString(index);
					System.out.println(val);//prints id number
					index++;
			}
			
		}			
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		
		
	}
	
	public void writeScore(Score score)
	{
		
	}
	
	public void printScores()
	{
		System.out.println("Testing database clase");
	}

}