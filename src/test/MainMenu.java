package src.test;

import processing.core.PApplet;
import processing.core.PConstants;

public class MainMenu {
	
	PApplet ap;
	Player p;
	
	public MainMenu(PApplet ap, Player p)
	{
		this.ap = ap;
		this.p = p;
	}
	
	
	public void render(int select)
	{
		Main.setScoreWritten(false); 
		ap.pushMatrix();
			ap.fill(255);
			ap.textAlign(PConstants.CENTER, PConstants.CENTER);
			ap.textFont(Main.getGameText(), 20);
		    ap.text("START", (ap.width / 2), (float)(ap.height * 0.75));
		    ap.text("TRACKS", (ap.width / 2), (float)(ap.height * 0.8));
		    ap.text("SCORES", (ap.width / 2), (float)(ap.height * 0.85));
		    ap.text("EXIT", (ap.width / 2), (float)(ap.height * 0.90));
		    Main.getControls().render();
		    Main.getControls().animate();
		    Main.setState(Main.getArrow().update(select)); 
		    Main.getArrow().render();
	    ap.popMatrix();
	}

}
