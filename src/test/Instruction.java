package src.test;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;

public class Instruction extends GameObject
{
	public PVector pos;
	private PFont gameText;
	private float fontSize, increment, rNumber, PI;
	private PApplet p;
	private String words = "W: Start, S: Scores, X: Exit,\nSpace to Select \nClick on the notes to raise score\nDon't let the notes reach the bottom of the page!";
	
	Instruction(float x, float y, PApplet p, PFont gameText)
	{
		this.pos = new PVector(x,y);
		this.p = p;
		this.gameText = gameText;
		this.fontSize = 8;
		this.increment = 0.03f;
		this.PI = (float)(22/7);
	}
	
	public void render()
	{
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		p.textFont(gameText, fontSize);
		rotating();
	}
	
	public void animate()
	{
		if(fontSize < 7 || fontSize > 10)
		{
			increment *= -1;
		}
	
		if(increment > 0)
		{
			fontSize = PApplet.lerp(fontSize, 11, 0.008f);
		}
		else
		{
			fontSize = PApplet.lerp(fontSize, 6, 0.008f);
		}
		
		if(fontSize <= 7.5 || fontSize >= 9)
		{
			rNumber = PApplet.lerp(rNumber, -PI/6, 0.01f);
		}
		else
		{
			rNumber = PApplet.lerp(rNumber, PI/6, 0.01f);
		}
	}
	
	
	public void rotating()
	{
		p.pushMatrix();
		
			p.translate(pos.x, pos.y);
			p.rotate(rNumber);
			p.text(words, 0, 0);
	
		p.popMatrix();
	}
	
	
	public void update()
	{
		
	}
}
