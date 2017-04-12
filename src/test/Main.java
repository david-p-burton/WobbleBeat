package test;
/*
 * TO DO and NOTES:
 * -cant seem to trigger even remotely close when either using 2 seperate beatDetectors
 * -or using one and calling seperate functions
 * 
 *
 */
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
	BeatDetector kickDetector;
	BeatDetector snareDetector;
	BeatDetector hatDetector;

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
		//song = minim.loadFile("Rock drum loop 1 (160 bpm).mp3", FRAME_SIZE);	
		song = minim.loadFile("Scarlett.mp3", FRAME_SIZE);	
		
		song.play();
		fft = new FFT(song.bufferSize(),song.sampleRate());
		beat = new BeatDetect();//sound energy mode
		//beat = new BeatDetect(song.bufferSize(), song.sampleRate());//Freq mode
		
		//this requires some tweaking to get it to trigger properly
		//the last parameter is the sensitivity. 
		//It seems to work best, if using multiple beatDetectors to have it set to zero
		//if using one to check for each beat.it can be adjusted. This seems to depend on song tempo.
		kickDetector = new BeatDetector(song,beat,this,0);
		snareDetector = new BeatDetector(song, beat, this,0);
		hatDetector = new BeatDetector(song, beat, this,0);
		
		
		//BeatDetector.playSong();
		
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
		
		
		kickDetector.detectKick();		
		snareDetector.detectSnare();
		hatDetector.detectHat();
		
		
		//testing to see if using one beatDetector works than having seperate
		//for each part
		
		//kickDetector.detectKick();
		//kickDetector.detectSnare();
		
		
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
