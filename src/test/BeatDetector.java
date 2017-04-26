package src.test;

import ddf.minim.AudioListener;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;

public class BeatDetector implements AudioListener{
	
	private AudioPlayer song;
	
	
	
	private PApplet p;
	private BeatDetect beat;
	private int sensitivity;
	
	//filters
	
	//temporary
	private float radius;//temporary
	private float kickPos;
	private float snrPos;
	
	
	
	public BeatDetector(AudioPlayer song,BeatDetect beat, PApplet p, int sensitivity)
	{
		this.song = song;
		//this.songWithFilter =  song;
		this.beat = beat;
		this.p = p;
	
		this.sensitivity = sensitivity;
		
		kickPos = 100;
		snrPos = 300;
		
		//50ms to 75ms seems to work for kick/snare individually,NOT togehter 0 seems to be a close as it can go
		beat.setSensitivity(sensitivity);//wait Xms before another beat detected
		
		radius = 100;
		beat.detectMode(0);//(0) = freqeuncey mode (1) = soundEnergy mode
		
		
		
		//song.addEffect(lpf);
		//ong.addEffect(hpf);
		
	}
	
	
	public boolean detectKick()
	{
		//song.play();
		beat.detect(song.mix);
		
	
		return beat.isKick();
		
			
	}//end detectKick()
	
	public boolean detectHat()
	{
		//song.play();
		beat.detect(song.mix);
		return beat.isHat();

	}//end detectHat()
	
	//uses custom freq range. isKick uses isRange but might not detect for all music styles
	public void detectKickFREQ()
	{
		//song.play();
		beat.detect(song.mix);
		
		if(beat.isRange(12, 18, 4))//50hz to 85hz roughly
		{
			p.fill(0,200,0);
		    p.ellipse(kickPos, p.height/2, radius, radius);
		}
	
	}//end detectKick()
	
	public boolean detectSnare()
	{
		
		beat.detect(song.mix);
			
		return beat.isSnare();

	}//end detectSnare()
	
	public void detectSnareFREQ()
	{
		//song.play();
		beat.detect(song.mix);
		
		if(beat.isRange(4, 10, 4))//100hz to 250hz roughly
		{
			p.fill(255,0,0);
		    p.ellipse(snrPos, p.height/2, radius, radius);
		}
	
	}//end detectSnareFREQ()
	
	
	
	//detect any beat(Look up how to return stuff in java)
	public boolean detectBeat()
	{
		//song.play();
		beat.detect(song.mix);
		boolean b = false;
		
		for(int i = 0; i < beat.dectectSize(); ++i)
		  {
		    // test one frequency band for an onset
		    if ( beat.isOnset(i) )
		    {
		       b = true;
		    }
		  }
		  
		
		return b;
			
		  
		//return b;
	}//end detectBeat


	@Override
	public void samples(float[] arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void samples(float[] arg0, float[] arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}
