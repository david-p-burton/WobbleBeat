package test;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Instruction extends GameObject
{
	public PVector pos;
	private PFont gameText;
	private float fontSize, increment;
	private PApplet p;
	private String words = "W & S to move selection arrow\nClick on the notes to raise score\nDon't let the notes reach the bottom of the page!";
	
	Instruction(float x, float y, PApplet p, PFont gameText)
	{
		this.pos = new PVector(x,y);
		this.p = p;
		this.gameText = gameText;
		this.fontSize = 8;
		this.increment = 0.03f;
	}
	
	public void render()
	{
		p.textAlign(p.CENTER, p.CENTER);
		p.textFont(gameText, fontSize);
		p.text(words, pos.x, pos.y);
		p.translate(0, 0);
	}
	
	public void animate()
	{
		//fontSize = p.lerp((float)8, (float)16, (float)0.1);
		if(fontSize < 7 || fontSize > 10)
		{
			increment *= -1;
		}
	
		if(increment > 0)
		{
			fontSize = p.lerp(fontSize, 11, 0.02f);
		}
		else
		{
			fontSize = p.lerp(fontSize, 6, 0.02f);
		}
	}
	public void update()
	{
		
	}
}
