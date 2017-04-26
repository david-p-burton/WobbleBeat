package src.test;

import java.util.ArrayList;
import processing.core.PApplet;

public class GameStruct extends GameObject
{
	private PApplet p;
	private float w, h;
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	boolean[] keyStrokes = new boolean[500];
	
	GameStruct(float w, float h, PApplet p, ArrayList<GameObject> gameObjects)
	{
		this.p = p;
		this.w = w;
		this.h = h;
		this.gameObjects = gameObjects;
	}
	
	public void update()
	{
		lane();
		/*
		for(int i = 0; i < gameObjects.size(); i++)
	    {
	      GameObject t = gameObjects.get(i);
	      if(t instanceof Note)
	      {
	    	  float x, y;
	    	  if(t.endPoint == 5)
	    	  {
	    		  x = 512 / 4 + 10;
	    	  }
	    	  if( (p.dist(t.pos.x, t.pos.y, this.pos.x, this.pos.y) < t.size + 5) && checkKey('e') )
	    	  {
	    		  gameObjects.remove(t);
	    	  }
	      }
	    }*/
		
	}
	
	public void lane()
	{
		/*
		 * x * w / 5 = where notes spawn
		 */
		p.fill(255);
		p.stroke(255);
		//innerlines
		p.line(w / 2 - 45, 0, w / 2 - 95, h);
		p.line(w / 2 + 45, 0, w / 2 + 95, h);
		//outerlines
		p.line(w / 2 - 150, 0, 0, h);
		p.line(w / 2 + 150, 0, w, h);
	}
	
	public void render()
	{
		
		
	}
	
	public boolean checkKey(int a)
	{
	  if(keyStrokes.length >= a)
	  {
	    return keyStrokes[a] || keyStrokes[Character.toUpperCase(a)];
	  }
	  return false;
	}
	
}
