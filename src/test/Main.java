package test;

import processing.core.PApplet;

public class Main extends PApplet {

	public static final int WIDTH = 1024;
	public static final int HEIGHT = 512;
	
	Note n;
	
	public void setup()
	{
		n = new Note(WIDTH/2, HEIGHT/2, 20, this);
	}
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
	}
	
	public void draw()
	{
		n.render();//this does not work
		//ellipse(WIDTH/2,HEIGHT/2,50,50);//this works..
		
	}
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
