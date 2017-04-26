package src.test;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class HealthPowerup extends GameObject implements Powerup{

	PApplet p;
	PImage sprite;
	
	public HealthPowerup(float x, float y, PApplet p)
	{
		this.pos = new PVector(x,y);
		this.p = p;
		//sprite = p.loadImage("Treble.png"); //sort an image out for this
		
	}
	@Override
	public void applyTo(Player player) {
		// TODO Auto-generated method stub
		player.increaseHealth();
	}

	@Override
	public void applyToGameObjects(GameObject go) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		p.image(sprite, pos.x, pos.y);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		pos.y = pos.y + 5;
	}

}
