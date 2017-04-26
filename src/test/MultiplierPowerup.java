package src.test;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class MultiplierPowerup extends GameObject implements Powerup{

	private PApplet p;
	PImage sprite;
	public float tempoRate;
	private float endPoint;
	
	private int mousePadding;//used for offset for click
	private boolean overPowerup;
	
	public MultiplierPowerup(float x, float y, float tempoRate, PApplet p, float endPoint)
	{
		this.pos = new PVector(x,y);
		this.p = p;
		this.sprite = p.loadImage("Treble.png");
		this.tempoRate = tempoRate;
		this.endPoint = endPoint;
		mousePadding = 50;
	}
	
	@Override
	public void applyTo(Player player) {
		// TODO Auto-generated method stub
		player.incrementScoreWithMultiplier();
	}

	@Override
	public void applyToGameObjects(GameObject go) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		//p.ellipse(pos.x,pos.y, 50, 50);
		p.image(sprite,pos.x,pos.y, 50, 50);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		pos.y += tempoRate;
	    pos.x = p.lerp(pos.x, endPoint, (0.0015f * tempoRate));
	    size = p.lerp(size, size + 30, (0.002f * tempoRate));
	}
	
	public boolean isClicked()
	{
		//(mouseX > x) && (mouseX < x  + b_width) && (mouseY > y) && (mouseY < y + b_height) )
		if( ((p.mouseX >= pos.x - mousePadding) && (p.mouseX <= pos.x + mousePadding))&& 
				((p.mouseY >= pos.y - mousePadding) && (p.mouseY <= pos.y + mousePadding))   )
		{
			if(p.mousePressed)
			{	
				overPowerup = true;
				
			}
		}
		else
		{
			overPowerup = false;
		}
		
		return overPowerup;
	}
	
	public float getY()
	{
		return pos.y;
	}

}
