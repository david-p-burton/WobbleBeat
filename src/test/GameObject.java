package test;

import processing.core.PVector;

public abstract class GameObject {
	
	protected PVector pos;
	protected float size;
	
	public abstract void render();
	public abstract void update();
	
}
