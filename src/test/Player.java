package test;

import processing.core.PApplet;

public class Player extends GameObject{
	
	
	private String name;
	private int health;
	private boolean isDead;
	private PApplet p;
	
	public Player(String name, int health, PApplet p)
	{
		this.name = name;
		this.health = health;
		this.p = p;
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
		System.out.println("HellO");
	}
	
	public void decrementHealth()
	{
		health--;
	}
	
	public int getHealth()
	{
		return health;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean checkIsDead()
	{
		return isDead;
	}

}
