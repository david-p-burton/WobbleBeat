package test;

import java.util.ArrayList;
import java.util.Random;

import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
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
	AudioPlayer song;
	FFT fft;

	private BeatDetect beat;
	static final int FRAME_SIZE = 1024;
	static final int SAMPLE_RATE = 44100;

	
	private static int eRadius;
	private static int hitLocation;

	
	
	
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
		song = minim.loadFile("Rock drum loop 1 (160 bpm).mp3", FRAME_SIZE);	
		//song = minim.loadFile("Haunted Shores - Scarlet -instrumental-.mp3", FRAME_SIZE);	
		
		fft = new FFT(song.bufferSize(),song.sampleRate());
		//beat = new BeatDetect();//sound energy mode
		beat = new BeatDetect(song.bufferSize(), song.sampleRate());//Freq mode
		
		//beat.setSensitivity(100);//wait 100ms before another beat detected
		
		song.play();
		eRadius = 20;
		hitLocation = width/2;
		
	
		
		
		
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
	
	
	
	
	public void draw()
	{
		
		background(0);
		
		
		//fft.window(FFT.HAMMING);
		//fft.forward( song.mix );
		
		/*
		beat.detect(song.mix);
		
		  float a = map(eRadius, 20, 80, 60, 255);
		  fill(60, 255, 0, a);
		  
		  if ( beat.isOnset() ) 
		  {
			  eRadius = 80;
			  
		  }
		  
		  ellipse(width/2, height/2, eRadius, eRadius);
		  eRadius *= 0.95;
		  if ( eRadius < 20 ) eRadius = 20;
		
		
		
		  if(beat.isKick())
		  {
				text("KICK",50,50); 
		  }
		   */
		ellipse(hitLocation, height/2, eRadius,eRadius);
		beat.detect(song.mix);
		
		for(int i = 0;i < beat.detectSize();i++)
		{
			if(beat.isKick())
			{
				hitLocation = 100;
			}
			
			if(beat.isSnare())
			{
				hitLocation = 300;
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
