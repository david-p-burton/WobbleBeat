package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;
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
	
	private String[] columnName;
	
	public Database() 
	{
		
	}
	
	public void loadScores()
	{
		try(Connection c = DriverManager.getConnection(url,user,password);
				PreparedStatement ps = c.prepareStatement("select * from scores"))
		{
			rs = ps.executeQuery();
			
			//get num of columns
			ResultSetMetaData rsMetaData = (ResultSetMetaData) rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			
			//make array for column names
			columnName = new String[numberOfColumns];
			
			for (int i = 1; i <= numberOfColumns; i++)
			{
			   columnName[i-1] = rsMetaData.getColumnLabel(i);
			   System.out.print(columnName[i-1] + "|\t");
			}
			
			System.out.println();
			
			//display contents of table
			while(rs.next())
			{
				//starts at 1
				for(int i = 1; i < numberOfColumns;i++)
				{
					String val = rs.getString(i);
					System.out.print(val + "\t\t");
				}
				System.out.println();	
			}
			
		}			
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		
		
	}//end load
	
	public void closeConnection() throws SQLException
	{
		if(con != null)
			con.close();
		
		if(rs != null)
			rs.close();
	}
	
	public void writeScore(Score score)
	{
		
	}
	
	public void printScores()
	{
		System.out.println("Testing database clase");
	}

}