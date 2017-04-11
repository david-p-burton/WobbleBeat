package test;

import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;

public class BeatDetector {
	
	private AudioPlayer song;
	private PApplet p;
	private BeatDetect beat;
	private float radius;//temporary
	
	
	public BeatDetector(AudioPlayer song,BeatDetect beat, PApplet p)
	{
		this.song = song;
		this.beat = beat;
		this.p = p;
		
		//50ms to 75ms seems to work for kick
		beat.setSensitivity(70);//wait Xms before another beat detected
		
		radius = 100;
		beat.detectMode(0);//freqeuncey mode
		
		
	}
	
	public void detectKick()
	{
		song.play();
		beat.detect(song.mix);
		
		for(int i = 0;i < beat.detectSize();i++)
		{
			if(beat.isKick())
			{
				p.ellipse(p.width/2, p.height/2, radius, radius);
			}
			
		}
		
		//radius *= 0.75;
		
		if(radius < 20){
			radius = 100;
		}
	
	}//end detectKick()
	
	//uses custom freq range. isKick uses isRange but might not detect for all music styles
	public void detectKickFREQ()
	{
		song.play();
		beat.detect(song.mix);
		
		if(beat.isRange(12, 18, 4))//50hz to 85hz roughly
		{
			p.fill(0,200,0);
		    p.ellipse(p.width/2, p.height/2, radius, radius);
		}
	
	}//end detectKick()
	
	public void detectSnare()
	{
		song.play();
		beat.detect(song.mix);
		
		for(int i = 0;i < beat.detectSize();i++)
		{
			if(beat.isSnare())
			{
				p.fill(255,0,0);
			    p.ellipse(p.width/2, p.height/2, radius, radius);
			}
			radius *= 0.95;
			
			if(radius < 20){
				radius = 100;
			}
			
		}
	
	}//end detectSnare()
	
	public void detectSnareFREQ()
	{
		song.play();
		beat.detect(song.mix);
		
		if(beat.isRange(4, 10, 4))//100hz to 250hz roughly
		{
			p.fill(0,200,0);
		    p.ellipse(p.width/2, p.height/2, radius, radius);
		}
	
	}//end detectSnareFREQ()
	
	
	
	//detect any beat
	public void detectBeat()
	{
		song.play();
		beat.detect(song.mix);
		
		for(int i = 0; i < beat.detectSize(); ++i)
		  {
		    // test one frequency band for an onset
		    if ( beat.isOnset(i) )
		    {
		      p.fill(0,200,0);
		      p.ellipse(p.width/2, p.height/2, radius, radius);
		    }
		    radius *= 0.75;
			
			if(radius < 20){
				radius = 100;
			}
		  }
		
		
	}//end detectBeat
	
	

}
