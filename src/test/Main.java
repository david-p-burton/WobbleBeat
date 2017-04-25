package test;
import java.sql.SQLException;
/*
 * TO DO and NOTES:
 * Need to cap how many notes are generated
 * method to determine tempo (David) 
 * menu methods/class (David)
 *  (David)
 *  
 *	5)Powerup to give health and clear screen  
 *	6)File system
 */
import java.util.ArrayList;
import java.util.Random;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import controlP5.*;

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
	Score score;
	Instruction controls;
	Database db;
	
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

	private static float tempoRate;
	private static int counter;
	private static int selecter = 1;
	private static boolean scoreWritten;
	private static boolean scoresLoaded;
	private ControlP5 cp5;
	

	
	@SuppressWarnings("deprecation")
	public void setup()
	{
		gameObjects = new ArrayList<GameObject>();
		
		minim = new Minim(this);
		
		//two different tracks to test, one with just drums one dense metal mix
		song = minim.loadFile("Rock drum loop 1 (160 bpm).mp3", FRAME_SIZE);
		
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
		kickDetector = new BeatDetector(song,beat,this,10);
		//snareDetector = new BeatDetector(song, beat, this,0);
		//hatDetector = new BeatDetector(song, beat, this,0);
		//beatDetector = new BeatDetector(song,beat, this,300);
		
		//init player
		player = new Player("Default",10,this);
		gameObjects.add(player);
		
		
		gameObjectCount = 0;
		maxNotes = 1;//max notes per second
		
		running = true;
		gameState = 0;
		
		
		//database stuff
		db = new Database(this);
		//ensures scores are written and read only once
		scoreWritten = false;
		scoresLoaded = false;
		
		//for user to enter name
		 cp5 = new ControlP5(this);

	}//end setup()
	
	public void drawCP5Button()
	{
		cp5.addTextfield("Player Name").setPosition(WIDTH/3,HEIGHT/2).setSize(200, 40).setFocus(false);
		 //cp5.setColorForeground(0);
  		cp5.setColorBackground(125);
  		
		
	}//end drawCP5
	
	//when this call it will get the contents of the CP5 box
	public String submit()
	{
		return cp5.get(Textfield.class, "Player Name").getText();
	}//end submit
	
	public void generateNote(int startPos)
	{
		//Find a better way to limit how many notes are created: Need one note per hit
		
		//System.out.println("num of objects: " + gameObjectCount);
		randPosY = 200;
		//D - make game "fairer" by making notes spawn at fixed locations?
		//D - placeholder number = 200
		int guessWork;
		guessWork = (int)random(1, 5);
		if(guessWork == 1)
		{
			randPosX = WIDTH / 5;
		}
		else if(guessWork == 2)
		{
			randPosX = 2 * (WIDTH / 5);
		}
		else if (guessWork == 3)
		{
			randPosX = 3 * (WIDTH / 5);
		}
		else
		{
			randPosX = 4 * (WIDTH / 5);
		}
		
		if(gameObjectCount < maxNotes)
		{
			Note n = new Note(randPosX, randPosY, 20, 1.0f, this);
			gameObjects.add(n);
			counter++;
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
		
		if(p.checkIsDead())
		{
			gameState = 3;
			song.pause();
			song.rewind();
			
			score = new Score(p.getName(),p.getScore(),currentTime);
			
			
			//db.loadScores();
			
			//remove everything bar player
			for(int i = gameObjects.size() - 1; i >= 1; i--)
		      {
		        GameObject use = gameObjects.get(i);
		        gameObjects.remove(use);
		      }
			
			textAlign(CENTER, CENTER);
			text("GAME OVER\nPRESS ANY KEY TO RETURN TO MENU", WIDTH/2,HEIGHT/2);
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
					//counter for counting amount of notes
					counter--;
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
		//System.out.println(player.isDead + " " + gameState);
		background(0);
		
		switch(gameState)
		{
			case 0: //spare
			{
				player.isDead = false;
				scoreWritten = false;//reset this so game can be played again and new score written
				
				//control instructions object
				controls = new Instruction(width/2, 150, this, gameText);
				gameObjects.add(controls);
				drawCP5Button();
				
				gameState = 1;
				break;
			}
			case 1: //game menu
			{
				scoreWritten = false;
				textAlign(CENTER, CENTER);
				textFont(gameText, 20);
			    text("START", (width / 2), (float)(height * 0.8));
			    text("SCORES", (width / 2), (float)(height * 0.85));
			    text("EXIT", (width / 2), (float)(height * 0.90));
			    controls.render();
			    controls.animate();
			    selecter();
			    //set the player name with whatever was typed in the CP5 box
			    player.setName(submit());
			    
				break;
			}
			case 2: //game Mode
			{
				runGame();
				processGameObject();
				cp5.remove("Player Name");
				
				break;
			}
			case 3: //game Over
			{
				processGameObject();
				counter = 0;
				player.reset();
				cp5.remove("Player Name");
				
				//write score only once to database
				if(!scoreWritten)
				{
					try {
						db.writeScore(score);
						scoreWritten = true;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				running = true;
				if(keyPressed)
				{
					gameState = 0;
				}
				break;
			}
			case 4:
			{
				cp5.remove("Player Name");
				text("Press x to go back", (width / 2), (float)(height * 0.85));
				if(!scoresLoaded)
				{
					db.loadScores();
					
					scoresLoaded = true;
				}
				else
				{
					db.printScores();
				}
				
				if(checkKey('x'))
				{
					gameState = 0;
				}

				break;
			}
			default: //switch should never get to this state - left blank
			{
				break;
			}
		}
	}//end draw()
	
	public void runGame()
	{
		//game loop
		if(running)
		{
			song.play();
			currentTime += timeDelta;
			
			if(kickDetector.detectKick() && counter < 20)
			{
				generateNote(WIDTH / 2);
			}
			
			//moved to a separate method to avoid clutter
			stats();
		}//end gameLoop
		
	}//end game
	
	public void selecter()
	{
		
		if(checkKey('w'))//play
		{
			selecter = 0;
			
		}
		else if(checkKey('s'))//leaderboard
		{
			selecter = 1;
		}
		
	
		else if(checkKey('x'))//exit
		{
			selecter = 2;
		    
		}
		
		//draw arrow that moves
		if(selecter == 0)
		{
			image(test, width/3 - 10, (float)(height * 0.77) + 5, 30, 30);
		}
		if(selecter == 1)
		{
			image(test, width/3 - 10, (float)(height * 0.82) + 5, 30, 30);
		}
		
		if(selecter == 2)
		{
			image(test, width/3 - 10, (float)(height * 0.87) + 5, 30, 30);
		}
		
		//change game states
		if(selecter == 1 && checkKey(' '))
		{
			gameState = 4;//score board
		}
		   
		//remove arrow objects once game is running
		if(selecter == 0 && checkKey(' '))
		{
			for(int i = 0; i < gameObjects.size();i++)
			{
				GameObject o = gameObjects.get(i);
				
				if(o instanceof Instruction)
				{
					gameObjects.remove(o);
				}
			}

		    gameState = 2;
		}
	
		
		
		else if(selecter == 2 && checkKey(' '))
		{
			exit();
		}
	}
	
	public void stats()
	{
		textAlign(LEFT, CENTER);
		textFont(gameText, 10);
		text("Time " + currentTime,10,10);
		text("Score " + player.getScore(),10,20);
		text("Lives " + player.getHealth(),10,30);
	}
	
	//close connection on exit
	public void stop()
	{
		try {
			db.closeConnection();
			super.stop();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	//Methods for registering keystrokes
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
