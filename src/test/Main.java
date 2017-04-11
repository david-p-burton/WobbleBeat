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
	BeatDetector beatDetector;

	private BeatDetect beat;
	static final int FRAME_SIZE = 1024;
	static final int SAMPLE_RATE = 44100;

	
	private static int eRadius;
	private static int hitLocation;
	private static int gameObjectCount;

	
	
	
	//ArrayLists
	ArrayList<GameObject> gameObjects;
	
	//Objects
	Note n;
	
	//Misc control stuff. Is this a good place to put these variables??
	private static Random rand = new Random();
	private static int randStartPos; 
	private static float timeDelta = 1.0f / 60.0f;
	private static float currentTime;
	private static float noteSpawnTime;
	private static int maxNotes;

	
	public void setup()
	{
		gameObjects = new ArrayList<GameObject>();
		
		minim = new Minim(this);
		
		//two different tracks to test, one with just drums one dense metal mix
		song = minim.loadFile("Rock drum loop 1 (160 bpm).mp3", FRAME_SIZE);	
		//song = minim.loadFile("Haunted Shores - Scarlet -instrumental-.mp3", FRAME_SIZE);	
		
		fft = new FFT(song.bufferSize(),song.sampleRate());
		//beat = new BeatDetect();//sound energy mode
		beat = new BeatDetect(song.bufferSize(), song.sampleRate());//Freq mode
		
		//this requires some tweaking to get it to trigger properly
		//beat.setSensitivity(275);//wait 30ms before another beat detected
		beatDetector = new BeatDetector(song,beat,this);
		
		//song.play();
		eRadius = 20;
		hitLocation = -50;//off sreen
		gameObjectCount = 0;
		maxNotes = 6;//max notes per second
		
		
	}//end setup()
	
	public void generateNote(int startPos)
	{
		//randStartPos = rand.nextInt(WIDTH) + 1;
		noteSpawnTime += timeDelta;
		//System.out.println("num of objects: " + gameObjectCount);
		
		if(gameObjectCount < maxNotes && noteSpawnTime < 1.00)
		{
			Note n = new Note(startPos,0, 20, this);
			gameObjects.add(n);
			gameObjectCount++;
		}
		else
		{
			gameObjectCount = 0;
			noteSpawnTime = 0;
		}
		
	}//end generateNote()
	
	public void processGameObject()
	{
		for(int i = 0; i < gameObjects.size();i++)
		{
			gameObjects.get(i).update();
			gameObjects.get(i).render();
		}
	}//end processGameObject()
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
		
	}//end settings()
	
	
	
	
	public void draw()
	{
		currentTime += timeDelta;
		background(0);
		
		//fft.window(FFT.HAMMING);
		//fft.forward( song.mix );
		beatDetector.detectBeats();
		
		processGameObject();
		//System.out.println("Time: " + currentTime);
	}//end draw()
	
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
