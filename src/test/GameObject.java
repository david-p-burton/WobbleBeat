package src.test;

//import processing.core.PVector;
//import processing.core.PApplet;
import processing.core.*;

public abstract class GameObject {
	
	protected PVector pos;
	protected float size;
	
	public abstract void render();
	public abstract void update();
		
}
