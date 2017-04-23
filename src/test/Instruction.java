package test;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class Instruction extends GameObject
{
	public PVector pos;
	private PFont gameText;
	private PApplet p;
	private String words = "Use W and S to move the selection arrow\nWhen the song begins, click on the notes\nDon't let the notes reach the bottom of the page!";
	
	Instruction(float x, float y, PApplet p, PFont gameText)
	{
		this.pos = new PVector(x,y);
		this.p = p;
		this.gameText = gameText;
	}
	
	public void render()
	{
		p.textAlign(p.CENTER, p.CENTER);
		p.textFont(gameText, 8);
		p.text(words, pos.x, pos.y);
	}
	public void update()
	{
		
	}
}
