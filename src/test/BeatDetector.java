package test;

import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;

public class BeatDetector {
	
	private AudioPlayer song;
	private PApplet p;
	private BeatDetect beat;
	
	
	public BeatDetector(AudioPlayer song,BeatDetect beat, PApplet p)
	{
		this.song = song;
		this.beat = beat;
		this.p = p;
		
		
	}
	
	public void detectBeats()
	{
		song.play();
		beat.detect(song.mix);
		
		for(int i = 0;i < beat.detectSize();i++)
		{
			if(beat.isKick())
			{
				//hitLocation = 100;
				//eRadius = 100;
				//generateNote(100);
				p.text("KICK", p.width/2, p.height/2);
			}
			
			if(beat.isSnare())
			{
				//hitLocation = 300;
				//eRadius = 100;
				//generateNote(400);
			}
		}
	}

}
