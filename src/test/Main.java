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
		gameObjects = new ArrayList<GameObject>();
		
		//testing 10 notes
		for(int i = 0;i < 10;i++)
		{
			randStartPos = rand.nextInt(WIDTH) + 1;
			n = new Note(randStartPos,0, 20, this);
			gameObjects.add(n);
		}
	}//end setup()
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
	}//end settings()
	
	public void draw()
	{
		background(0);
		processGameObject();
		
	}//end draw()
	
	public void processGameObject()
	{
		for(int i = 0; i < gameObjects.size();i++)
		{
			gameObjects.get(i).update();
			gameObjects.get(i).render();
		}
	}//end processGameObject()
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
