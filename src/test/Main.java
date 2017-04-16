package test;
/*
 * TO DO and NOTES:
 * Need to cap how many notes are generated
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
	public static boolean running;
	
	//Audio stuff
	Minim minim;
	AudioPlayer song;
	FFT fft;
	BeatDetector kickDetector;
	BeatDetector snareDetector;
	BeatDetector hatDetector;
	BeatDetector beatDetector;

	private BeatDetect beat;
	static final int FRAME_SIZE = 1024;
	static final int SAMPLE_RATE = 44100;

	
	private static int gameObjectCount;

	//ArrayLists
	ArrayList<GameObject> gameObjects;
	
	//Objects
	Note n;
	Player player;
	
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
		//song = minim.loadFile("Scarlett.mp3", FRAME_SIZE);	
		
		song.play();
		fft = new FFT(song.bufferSize(),song.sampleRate());
		//beat = new BeatDetect();//sound energy mode
		beat = new BeatDetect(song.bufferSize(), song.sampleRate());//Freq mode
		
		//this requires some tweaking to get it to trigger properly
		//the last parameter is the sensitivity. 
		//It seems to work best, if using multiple beatDetectors to have it set to zero
		//if using one to check for each beat.it can be adjusted. This seems to depend on song tempo.
		kickDetector = new BeatDetector(song,beat,this,10);
		snareDetector = new BeatDetector(song, beat, this,0);
		hatDetector = new BeatDetector(song, beat, this,0);
		beatDetector = new BeatDetector(song,beat, this,300);
		
		//init player
		player = new Player("Ro",10,this);
		gameObjects.add(player);
		
		
		gameObjectCount = 0;
		maxNotes = 1;//max notes per second
		
		running = true;
		
		
	}//end setup()
	
	public void generateNote(int startPos)
	{
		//Find a better way to limit how many notes are created: Need one note per hit
		
		//System.out.println("num of objects: " + gameObjectCount);
		
		if(gameObjectCount < maxNotes)
		{
			Note n = new Note(startPos,0, 20, this);
			gameObjects.add(n);
			gameObjectCount++;
		}
		else
		{
			gameObjectCount = 0;
		}
		
	}//end generateNote()
	
	public void processGameObject()
	{
		
		//Player is always 1st object in ArrayList
		GameObject obj = gameObjects.get(0);
		
		Player p = (Player)obj;
		//p.sayHello();
		System.out.println(p.getHealth());
		
		
		if(p.checkIsDead())
		{
			song.close();
			running = false;
		}
		
		for(int i = 0; i < gameObjects.size();i++)
		{
			gameObjects.get(i).update();
			gameObjects.get(i).render();
			
			//get game object and check if it is a note
			GameObject o = gameObjects.get(i);
			
			//check for note off screen
			if(o instanceof Note)
			{
				Note n = (Note)o;
				n.isClicked();
				
				if(n.getY() > HEIGHT)
				{
					p.decrementHealth();
					gameObjects.remove(n);
				}
			}
		}
	
	}//end processGameObject()
	
	
	
	public void settings()
	{
		size(WIDTH, HEIGHT);
		
	}//end settings()
	
	
	public void draw()
	{
		
		background(0);
		
		
		//game loop
		if(running)
		{
			currentTime += timeDelta;
			
			if(kickDetector.detectKick())
			{
				generateNote(WIDTH/2);
			}
			
			
			processGameObject();
			
		}//end gameLoop
		
		
		text("Time " + currentTime,10,10);
		//System.out.println("ArrayList size: " + gameObjects.size());
	}//end draw()
	

	public void mouseClicked()
	{
		
	}
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
        
        
	}
}
