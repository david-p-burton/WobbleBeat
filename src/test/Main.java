package test;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

 

public class Main extends PApplet {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 675;
	
	//ArrayLists
	ArrayList<GameObject> gameObjects;
	
	//Objects
	Note n;
	
	//Misc control stuff. Is this a good place to put these variables??
	public static Random rand = new Random();
	public static int randStartPos; 
	
	public void setup()
	{
		randStartPos = rand.nextInt(WIDTH) + 1;
		gameObjects = new ArrayList<GameObject>();
		n = new Note(randStartPos,0, 20, this);
		
		gameObjects.add(n);
	}
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
	}
	
	public void draw()
	{
		for(int i = 0; i < gameObjects.size();i++)
		{
			gameObjects.get(i).update();
			gameObjects.get(i).render();
		}
		
		
		
		
	}
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
