package src.test;


public class Player extends GameObject{
	
	
	private String name;
	private int health;
	public boolean isDead;
	private int score;
	
	public Player(String name, int health)
	{
		this.name = name;
		this.health = health;
		score = 0;
	}
	
	public void update()
	{
		if(health <= 0)
		{
			isDead = true;
		}
	}
	
	//testing
	public void sayHello()
	{
		System.out.println("Hello, my name is " + name);
	}
	
	public void decrementHealth()
	{
		health--;
	}
	
	public int getHealth()
	{
		return health;
	}

	public void incrementScore()
	{
		score++;
	}
	
	public void reset()
	{
		score = 0;
		health = 10;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public String getName()
	{
		return name;
	}
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkIsDead()
	{
		return isDead;
	}
	
	public void setName(String s)
	{
		name = s;
	}

}
