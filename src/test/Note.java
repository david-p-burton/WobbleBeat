package test;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


public class Note extends GameObject {
	
	PImage sprites[];
	private int animationKey;
	private PApplet p;
	private int mousePadding;//used for offset for click
	private boolean overNote;
	//variable for moving the note according to tempo
	public float tempoRate;
	
	
	public Note(float x, float y, float size, float tempoRate, PApplet p)
	{
		//you can only have one class extend PApplet, the main class so we have to pass it to all other classes
		//so we can use the processing shapes and images etc
		this.p = p;
		pos = new PVector(x,y);
		this.size = size;
		this.tempoRate = tempoRate;
		mousePadding = 20;
		
		//this is index for which image to be displayed. Make this bigger if you want more "animation" images
		animationKey = 1;
		sprites = new PImage[animationKey];
		
		sprites[0] = p.loadImage("8th Note.png");
		
		
	}

	@Override
	public void render() {
		//when using any processing code in SEPERATE CLASS FILES. We need to put p.func() to use it
		p.fill(255);
		
		//resize not working
		sprites[0].resize(200,200);
		p.image(sprites[0],pos.x,pos.y, size, size);
	}

	@Override
	public void update() {
		//make it fall. This will be based off of tempo
		pos.y += tempoRate;
		
		
		
	}
	
	public boolean isClicked()
	{
		//(mouseX > x) && (mouseX < x  + b_width) && (mouseY > y) && (mouseY < y + b_height) )
		if( ((p.mouseX >= pos.x - mousePadding) && (p.mouseX <= pos.x + mousePadding))&& 
				((p.mouseY >= pos.y - mousePadding) && (p.mouseY <= pos.y + mousePadding))   )
		{
			if(p.mousePressed)
			{	
				overNote = true;
			}
		}
		else
		{
			overNote = false;
		}
		
		return overNote;
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
