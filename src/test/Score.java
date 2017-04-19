package test;

/*
 * Scores will be added to database
 * 
 */

public class Score {
	
	private String playerName;
	private int playerScore;
	private float timeLasted;
	
	
	public Score(String playerName,int playerScore, float timeLasted) 
	{
		this.playerName = playerName;
		this.playerScore = playerScore;
		this.timeLasted = timeLasted;
	
	}

	//setters used for testing. Score should be only made when game is played and finished/lost
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}
	
	public void setPlayerScore(int playerScore)
	{
		this.playerScore = playerScore;
	}
	
	
	//getters
	public String getPlayerName()
	{
		return playerName;
	}
	
	public int getPlayerScore()
	{
		return playerScore;
	}
	
	public float getTimeLasted()
	{
		return timeLasted;
	}
	
	
}