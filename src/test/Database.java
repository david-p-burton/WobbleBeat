package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import processing.core.PApplet;

public class Database {
	
	//static String driver = "org.sqlite.JDBC";
	private String driver = "org.sqlite.JDBC";
	
	//How do we d this so it wont be just local database?
	private String url = "jdbc:mysql://localhost:3306/wobblebeatscores";
	private String user = "root";
	private String password = "";
	
	//used to store data from score objects(id is auto increment in database)
	private String playeName;
	private int playerScore;
	private float playerTime;
	
	//used for processing libray
	PApplet p;
	
	ResultSet rs;
	
	
	//stores the column names from the database for printing to screen
	private String[] columnName;
	private ArrayList<Score> listOfScores;
	private Score tempScore;
	
	//padding used for scoreScreen text
	private float textX, textY;
	private int textPadding;
	private int displayIndex;
	
	
	public Database(PApplet p) 
	{
		this.p = p;
		textX = 100;
		textY = 100;
		textPadding = 150;
		displayIndex = 1;
		listOfScores = new ArrayList<Score>();
		
		
		try
		{
			Class.forName(driver);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}
	
	public void loadScores()
	{
		try(Connection c = DriverManager.getConnection(url,user,password);
				PreparedStatement ps = c.prepareStatement("select * from scores ORDER BY PlayerScore DESC"))
		{
			rs = ps.executeQuery();
			
			//get num of columns
			ResultSetMetaData rsMetaData = (ResultSetMetaData) rs.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			
			//make array for column names
			columnName = new String[numberOfColumns];
			
			//fill string array with column names so they can be printed
			for (int i = 1; i <= numberOfColumns; i++)
			{
			   columnName[i-1] = rsMetaData.getColumnLabel(i);
			}
			
				
			//display contents of table
			while(rs.next())
			{
				
				String name = rs.getString("PlayerName");
				int s = rs.getInt("PlayerScore");
				float t = rs.getFloat("PlayerTime");
				tempScore = new Score(name,s,t);
				
				listOfScores.add(tempScore);
					
			}
			
		}			
		catch(SQLException e)
		{
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		
		
	}//end load
	
	public void writeScore(Score score) throws SQLException
	{
		try(Connection c = DriverManager.getConnection(url,user,password);
				PreparedStatement ps = c.prepareStatement("insert into scores (PlayerName,PlayerScore,PlayerTime) " +  "VALUES (?, ?, ?)"))
		{
			
			playeName = score.getPlayerName();
			playerScore = score.getPlayerScore();
			playerTime = score.getTimeLasted();
			
			ps.setString(1, playeName);
			ps.setInt(2, playerScore);
			ps.setFloat(3,playerTime);
			
			ps.executeUpdate();
			
			//Close once done??
			if(c != null)
				c.close();
		}
		catch(SQLException e)
		{
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		
			
               
	}//end write scores
	
	public void closeConnection() throws SQLException
	{
		
		if(rs != null)
			rs.close();
	}
	
	
	
	public void printScores()
	{
		displayIndex = 0;
		int yPadding = 50;
		int scoresToDisplay = 8;
		
		//display Column names
		p.textSize(20);
		p.text("Leaderboard", p.width/2, 50);
		p.textSize(12);
		p.text(columnName[1], textX, textY);
		p.text(columnName[2], textX + textPadding, textY);
		p.text(columnName[3], textX + (textPadding * 2), textY);
		
		//System.out.println(listOfScores.size());
		
		while(displayIndex < scoresToDisplay)
		{
			p.text(listOfScores.get(displayIndex).getPlayerName(), textX, textY + yPadding);
			p.text(listOfScores.get(displayIndex).getPlayerScore(), textX + 150, textY + yPadding);
			p.text(listOfScores.get(displayIndex).getTimeLasted(), textX + 300, textY + yPadding);
			displayIndex++;
			
			yPadding += 50;
		}
		
		
	}

}