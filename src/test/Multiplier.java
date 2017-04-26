package src.test;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Multiplier extends GameObject implements Powerup{

	PApplet p;
	PImage sprite;
	
	public Multiplier(float x, float y, PApplet p)
	{
		this.pos = new PVector(x,y);
		this.p = p;
		sprite = p.loadImage("Treble.png");
	}
	@Override
	public void applyTo(Player player) {
		// TODO Auto-generated method stub
		player.scoreMultiplier();
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
