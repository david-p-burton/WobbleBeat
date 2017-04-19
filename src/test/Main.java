package test;
/*
 * TO DO and NOTES:
 * Need to cap how many notes are generated
 * method to determine tempo (David) 
 * menu methods/class (David)
 *  (David)
 *  
 *  Notes spawn in numerous places for each hit. Makes the game impossible
 */
import java.util.ArrayList;
import java.util.Random;

import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.AudioSample;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import ddf.minim.effects.LowPassFS;
import ddf.minim.analysis.BeatDetect;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import ddf.minim.effects.*;
import ddf.minim.ugens.*;

public class Main extends PApplet {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 675;
	public static boolean running;
	//variable to determine what should be drawn on screen
	public int gameState; 
	/*Available game states
	 * 1 = main menu
	 * 2 = Game mode
	 * 3 = Game over screen
	 * 4 = 
	 * 
	 */
	
	//Audio stuff
	Minim minim;
	AudioPlayer song;
	AudioPlayer songToBeFiltered;
	FFT fft;
	BeatDetector kickDetector;
	BeatDetector snareDetector;
	BeatDetector hatDetector;
	BeatDetector beatDetector;

	private BeatDetect beat;
	
	static final int FRAME_SIZE = 1024;
	static final int SAMPLE_RATE = 44100;

	
	private static int gameObjectCount;
	
	//font
	PFont gameText;
	
	//Arrays
	boolean[] keyStrokes = new boolean[500];
	
	//ArrayLists
	ArrayList<GameObject> gameObjects;
	
	//Objects
	Note n;
	Player player;
	
	//Images
	PImage test;
	
	//Misc control stuff. Is this a good place to put these variables??
	private static Random rand = new Random();
	private static int randPosX;
	private static int randPosY;
	private static float timeDelta = 1.0f / 60.0f;
	private static float currentTime;
	private static float noteSpawnTime;
	private static int maxNotes;
	//David
	private static float tempoRate;
	private static int selecter;

	
	@SuppressWarnings("deprecation")
	public void setup()
	{
		gameObjects = new ArrayList<GameObject>();
		
		minim = new Minim(this);
		
		//two different tracks to test, one with just drums one dense metal mix
		song = minim.loadFile("Rock drum loop 1 (160 bpm).mp3", FRAME_SIZE);
		songToBeFiltered = minim.loadFile("Rock drum loop 1 (160 bpm).mp3", FRAME_SIZE);
		//song = minim.loadFile("Scarlett.mp3", FRAME_SIZE);	
		
		//game font
		gameText = createFont("data/game.ttf", 30, true);
		
		//Images
		test = loadImage("data/musicNote.png");
		
		//song.play();  - moved to gameState 2 for testing
		fft = new FFT(song.bufferSize(),song.sampleRate());
		//beat = new BeatDetect();//sound energy mode
		beat = new BeatDetect(song.bufferSize(), song.sampleRate());//Freq mode
		
		//this requires some tweaking to get it to trigger properly
		//the last parameter is the sensitivity. 
		//It seems to work best, if using multiple beatDetectors to have it set to zero
		//if using one to check for each beat.it can be adjusted. This seems to depend on song tempo.
		kickDetector = new BeatDetector(song,songToBeFiltered,beat,this,10);
		//snareDetector = new BeatDetector(song, beat, this,0);
		//hatDetector = new BeatDetector(song, beat, this,0);
		//beatDetector = new BeatDetector(song,beat, this,300);
		
		//init player
		player = new Player("Ro",10,this);
		gameObjects.add(player);
		
		
		gameObjectCount = 0;
		maxNotes = 1;//max notes per second
		
		running = true;
		gameState = 1;
		
		
	}//end setup()
	
	public void generateNote(int startPos)
	{
		//Find a better way to limit how many notes are created: Need one note per hit
		
		//System.out.println("num of objects: " + gameObjectCount);
		randPosX = rand.nextInt(WIDTH);
		//D - make game "fairer" by making notes spawn at fixed locations?
		//D - placeholder number = 200
		randPosY = 200;
		
		if(gameObjectCount < maxNotes)
		{
			Note n = new Note(randPosX,randPosY, 20, 1, this);
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
			text("GAME OVER", WIDTH/2,HEIGHT/2);
			running = false;
		}
		
		for(int i = 0; i < gameObjects.size();i++)
		{
			gameObjects.get(i).update();
			gameObjects.get(i).render();
			
			//get game object and check if it is a note
			GameObject o = gameObjects.get(i);
			
			//check for note off screen and if clicked, if so remove it
			if(o instanceof Note)
			{
				Note n = (Note)o;
				//check if note is clicked
				
				if(n.isClicked())
				{
					//text("Test", WIDTH/2,HEIGHT/2);
					gameObjects.remove(n);
					p.incrementScore();
				}
				
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
		
		switch(gameState)
		{
			case 0: //spare
			{
				break;
			}
			case 1: //game menu
			{
				textAlign(CENTER, CENTER);
				textFont(gameText, 20);
			    text("START", (width / 2), (float)(height * 0.8));
			    text("EXIT", (width / 2), (float)(height * 0.85));
			    selecter();
				break;
			}
			case 2: //game Mode
			{
				song.play();
				//game loop
				if(running)
				{
					currentTime += timeDelta;
					
					if(kickDetector.detectKick())
					{
						generateNote(WIDTH / 2);
					}
					processGameObject();
					//moved to a separate method to avoid clutter
					stats();
				}//end gameLoop
				break;
			}
			case 3: //game Over
			{
				break;
			}
			default: //switch should never get to this state - left blank
			{
				break;
			}
		}
	}//end draw()
	
	public void selecter()
	{
		if(checkKey('w'))
		{
			selecter = 1;
			
		}
		else if(checkKey('s'))
		{
			selecter = 0;
		}
		
		if(selecter == 1)
		{
		  image(test, width/3, (float)(height * 0.77) + 5, 30, 30);
		}
		if(selecter == 0)
		{
			image(test, width/3, (float)(height * 0.82) + 5, 30, 30);
		}
		   
		
		
		if(selecter == 1 && checkKey(' '))
		{
		    gameState = 2;
		}
		else if(selecter == 0 && checkKey(' '))
		{
			exit();
		}
	}
	
	public void stats()
	{
		textFont(gameText, 10);
		text("Time " + currentTime,10,10);
		text("Score " + player.getScore(),10,20);
		text("Lives " + player.getHealth(),10,30);
		//System.out.println("ArrayList size: " + gameObjects.size());
	}

	public void mouseClicked()
	{
		
	}
	
	public static void main(String[] args)
	{
		//boiler plate code
		String[] a = {"MAIN"};
        PApplet.runSketch( a, new Main());
	}
	
	public void keyPressed()
	{
	  keyStrokes[keyCode] = true;
	}

	public void keyReleased()
	{
	  keyStrokes[keyCode] = false;
	}

	public boolean checkKey(int a)
	{
	  if(keyStrokes.length >= a)
	  {
	    return keyStrokes[a] || keyStrokes[Character.toUpperCase(a)];
	  }
	  return false;
	}
}
