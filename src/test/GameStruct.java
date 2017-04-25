package src.test;

import processing.core.PApplet;

public class GameStruct extends GameObject
{
	private PApplet p;
	private float w, h;
	boolean[] keyStrokes = new boolean[500];
	
	GameStruct(float w, float h, PApplet p)
	{
		this.p = p;
		this.w = w;
		this.h = h;
	}
	
	public void update()
	{
		lane();
	}
	
	public void lane()
	{
		/*
		 * x * width / 5 = where notes spawn
		 */
		p.fill(255);
		for(int i = 1; i < 5; i++)
		{
			p.ellipse((i * w / 5) + 10, 200, 30, 30);
		}
	}
	
	public void render()
	{
		
	}
	
	public boolean checkKey(int a)
	{
	  if(keyStrokes.length >= a)
	  {
	    return keyStrokes[a] || keyStrokes[Character.toUpperCase(a)];
	  }
	  return false;
	}
	
}
