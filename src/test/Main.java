package test;

import java.util.ArrayList;
import java.util.Random;

import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class Main extends PApplet {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 675;
	
	//Audio stuff
	Minim minim;
	AudioSample audioInput;
	FFT fft;
	static final int FRAME_SIZE = 2048;
	static final int SAMPLE_RATE = 44100;
	
	
	//ArrayLists
	ArrayList<GameObject> gameObjects;
	
	//Objects
	Note n;
	
	//Misc control stuff. Is this a good place to put these variables??
	public static Random rand = new Random();
	public static int randStartPos; 
	public static float timeDelta = 1.0f / 60.0f;
	public static float noteSpawnTime;
	private static int freqCount;//increment this each time a "snare" 200hz hits and work out tempo?
	
	public void setup()
	{
		gameObjects = new ArrayList<GameObject>();
		
		minim = new Minim(this);
		audioInput = minim.loadSample("Haunted Shores - Scarlet -instrumental-.mp3", FRAME_SIZE);	
		fft = new FFT(audioInput.bufferSize(),audioInput.sampleRate());
		
		freqCount = 0;
		
		//testing 10 notes
		for(int i = 0;i < 10;i++)
		{
			randStartPos = rand.nextInt(WIDTH) + 1;
			n = new Note(randStartPos,0, 20, this);//this is the PApplet
			gameObjects.add(n);
		}
		
	}//end setup()
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
	}//end settings()
	
	boolean lastPressed = false;
	
	
	public void draw()
	{
		float freqAmplitude;
		background(0);
		//processGameObject();
		
		if (keyPressed && key == ' ' && ! lastPressed)
		{
			audioInput.trigger();
			lastPressed = true;
		}
		else
		{
			//audioInput.stop();
			lastPressed = false;
		}
		
		fft.window(FFT.HAMMING);
		fft.forward( audioInput.left );//mix is stereo left and right
		for(int i = 0; i < fft.specSize(); i++)
		 {
			//freqAmplitude = fft.getFreq(100);//get amplitude of 200hz(roughly snare drum)
			freqAmplitude = fft.calcAvg(100,200);
			
			stroke(255);
			textSize(25);
			text("Amplitude of 100hz-200hz " + freqAmplitude,0,height/3);
			
			if(freqAmplitude > 125 )
			{
				//freqCount++;
				//System.out.println("SNARE");
				//fill(0,0,255);
				//rect(width/2,height/2, 300,300);
				
			}
			else
			{
				//System.out.println("-------");
				//fill(255,0,0);
				//ellipse(width/2,height/2, 50,50);
			}
			
		    // draw the line for frequency band i, scaling it up a bit so we can see it
			stroke(255);
		    //line( i, height, i, height - fft.getBand(i)*8 );
		 }
		
		//System.out.println(freqCount);
		
	}//end draw()
	
	public void processGameObject()
	{
		for(int i = 0; i < gameObjects.size();i++)
		{
			gameObjects.get(i).update();
			gameObjects.get(i).render();
		}
	}//end processGameObject()
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
