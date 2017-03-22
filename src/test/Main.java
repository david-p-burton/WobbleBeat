package test;

import processing.core.PApplet;

public class Main extends PApplet {

	
	public void settings()
	{
		size(1024, 512);
	}
	
	public static void main(String[] args)
	{
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
	}
}
