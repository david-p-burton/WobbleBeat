package test;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


public class Note extends GameObject {
	
	PImage sprites[];
	private int animationKey;
	private PApplet p;
	
	
	public Note(float x, float y, float size, PApplet p)
	{
		//you can only have one class extend PApplet, the main class so we have to pass it to all other classes
		//so we can use the processing shapes and images etc
		this.p = p;
		pos = new PVector(x,y);
		this.size = size;
		
		//this is index for which image to be displayed. Make this bigger if you want more "animation" images
		animationKey = 1;
		sprites = new PImage[animationKey];
		//sprites[0] = loadImage("explosion.png");//loadImage note working
		
	}

	@Override
	public void render() {
		p.fill(255);
		p.ellipse(pos.x,pos.y, size, size);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		pos.y++;
	}
	
	public float getX()
	{
		return pos.x;
	}
	
	public float getY()
	{
		return pos.y;
	}

}
