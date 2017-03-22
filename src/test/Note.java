package test;

import processing.core.PImage;
import processing.core.PVector;
i

public class Note extends GameObject {
	
	PImage sprites[];
	private int animationKey;
	
	
	public Note(float x, float y, float size)
	{
		pos = new PVector(x,y);
		this.size = size;
		
		//this is index for which image to be displayed. Make this bigger if you want more "animation" images
		animationKey = 1;
		sprites = new PImage[animationKey];
		//sprites[0] = loadImage("explosion.png");//loadImage note working
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
