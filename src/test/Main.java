package test;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

public class Main extends PApplet {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 675;
	
	//ArrayLists
	ArrayList<Note> notes;
	
	//Objects
	Note n;
	
	//Misc control stuff
	public static Random rand = new Random();
	public static int randStartPos; 
	
	public void setup()
	{
		randStartPos = rand.nextInt(WIDTH) + 1;
		notes = new ArrayList<Note>();
		n = new Note(randStartPos,0, 20, this);
	}
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
	}
	
	public void draw()
	{
		n.render();
		n.update();
		
		
	}
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
