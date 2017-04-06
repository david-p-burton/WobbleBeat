package test;

import java.util.ArrayList;
import java.util.Random;

import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;

public class Main extends PApplet {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 675;
	
	//Audio stuff
	Minim minim;
	AudioSample audioInput;
	FFT fft;
	private BeatListener beatListener;
	private BeatDetect beat;
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

	
	public void setup()
	{
		gameObjects = new ArrayList<GameObject>();
		
		minim = new Minim(this);
		audioInput = minim.loadSample("Haunted Shores - Scarlet -instrumental-.mp3", FRAME_SIZE);	
		fft = new FFT(audioInput.bufferSize(),audioInput.sampleRate());
		beat = new BeatDetect(audioInput.bufferSize(), audioInput.sampleRate());
		beatListener = new BeatListener(beat, audioInput, this);
		 beat.setSensitivity(300);  
		
		
		
	}//end setup()
	
	public void generateNote()
	{
		randStartPos = rand.nextInt(WIDTH) + 1;
		Note n = new Note(randStartPos,0, 20, this);
		gameObjects.add(n);
	}
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
	}//end settings()
	
	boolean lastPressed = false;
	
	
	public void draw()
	{
		
		background(0);
		
		
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
		fft.forward( audioInput.left );
		
		 for(int i = 0; i < beat.detectSize(); ++i)
		  {
		    // test one frequency band for an onset
		    if ( beat.isOnset(i) )
		    {
		      fill(0,200,0);
		      rect( width/2, height/2, 20, 20);
		    }
		  }
			
		
		
		
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
