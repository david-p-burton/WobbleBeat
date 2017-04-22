package test;

import processing.core.*;
import processing.core.PApplet;

public class Instruction extends GameObject
{
	public PVector pos;
	private String words = "Use W and S to move the selection arrow\nWhen the song begins, click on the notes\nDon't let the notes reach the bottom of the page!";
	
	Instruction(float x, float y)
	{
		this.pos = new PVector(x,y);
	}
	
	public void render()
	{
		p.textAlign(CENTER, CENTER);
		p.textFont(words, 15);
		p.text(words, pos.x, pos.y);
	}
	public void update()
	{
		
	}
}
