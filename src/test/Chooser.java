package src.test;

import processing.core.PApplet;

public class Chooser 
{
	public float x, y;
	public int state;
	PApplet p;
	
	
	public Chooser(PApplet p)
	{
		this.p = p;
		this.x = p.width/3-10;
		this.state = 0;
	}
	
	public int update(int option)
	{
		if(option == 0) {
			y = (float)(p.height * 0.72)+20;
			state = 2;
		}
		else if(option == 1) {
			y = (float)(p.height * 0.77) + 20;
			state = 4;
		}
		else if(option == 2) {
			y = (float)(p.height * 0.82) + 20;
			state = 5;
		}
		else if(option == 3) {
			y = (float)(p.height * 0.87) + 20;
			state = 6;
		}
		
		return state;
	}
	
	public void render()
	{
		p.pushMatrix();
		p.stroke(255);
		p.line(x-10, y-10, x, y);
		p.line(x-10, y+10, x, y);
		p.popMatrix();
		
	}

	
}
