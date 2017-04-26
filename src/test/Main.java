package src.test;

import java.io.IOException;
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
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;
import processing.core.PFont;
import controlP5.*;

public class Main extends PApplet {

	public static final int WIDTH = 512;
	public static final int HEIGHT = 675;
	public static boolean running;
	//variable to determine what should be drawn on screen
	private static int gameState; 
	private static int state;
	
	public static void setState(int s)
	{
		state = s;
	}
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
	
	//phil
	private static Chooser arrow;

	private BeatDetect beat;
	
	static final int FRAME_SIZE = 1024;
	static final int SAMPLE_RATE = 44100;

	
	private static int gameObjectCount;
	
	//font
	private static PFont gameText;
	
	//ArrayLists
	ArrayList<GameObject> gameObjects;
	
	//Objects
	Note n;
	private static Player player;
	Score score;
	private static Instruction controls;
	Database db;
	MainMenu mMenu;
	TrackMenu tMenu;
	
	//Misc control stuff. Is this a good place to put these variables??
	//private static Random rand = new Random();
	private static int randPosX;
	private static int randPosY;
	private static float timeDelta = 1.0f / 60.0f;
	private static float currentTime;
	//private static float noteSpawnTime;
	private static int maxNotes;

	//private static float tempoRate;
	private static int counter;
	private static int selecter;
	private static int trackSelect;
	private static boolean scoreWritten;
	private static boolean scoresLoaded;
	private static boolean trackSelected;
	private static boolean inTMenu;
	private static ControlP5 cp5;
	
	public static ControlP5 getCp5()
	{
		return cp5;
	}
	
	public void setup()
	{
		gameObjects = new ArrayList<GameObject>();
		selecter = 1;
		trackSelect = 0;
		trackSelected = false;
		inTMenu = false;
		minim = new Minim(this);
		
		//song = minim.loadFile("Scarlett.mp3", FRAME_SIZE);	
		
		//game font
		gameText = createFont("data/game.ttf", 30, true);
		
		arrow = new Chooser(this);
		
		
		//init player
		player = new Player("Default",10);
		gameObjects.add(player);

		mMenu = new MainMenu(this, player);
		try {
			tMenu = new TrackMenu(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	public static String submit()
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
				resetGame();
				break;
			}
			case 1: //game menu
			{
				inTMenu = false;
			    mMenu.render(selecter);
			    //set the player name with whatever was typed in the CP5 box
			    player.setName(submit());
				break;
			}
			case 2: //game Mode
			{
				inTMenu = false;
				if(trackSelected)
				{
					runGame();
					processGameObject();
	
					cp5.remove("Player Name");
					
					for(int i = 0; i < gameObjects.size();i++)
					{
						GameObject o = gameObjects.get(i);
						
						if(o instanceof Instruction)
						{
							gameObjects.remove(o);
						}
					}
				}
				else
				{
					gameState = 1;
				}
				break;
			}
			case 3: //game Over
			{
				inTMenu = false;
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
				tMenu.render();
				cp5.remove("Player Name");
				break;
			}
			case 5:
			{
				inTMenu = false;
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
				break;
			}
			case 6:
			{
				exit();
			}
			default: //switch should never get to this state - left blank
			{
				break;
			}
		}
	}//end draw()
	
	public void resetGame()
	{
		inTMenu = false;
		player.isDead = false;
		drawCP5Button();
		scoreWritten = false;//reset this so game can be played again and new score written
		
		//control instructions object
		controls = new Instruction(width/2, 150, this, gameText);
		gameObjects.add(controls);
		
		gameState = 1;
	}
	
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
		if(keyCode == UP)
		{
			if(gameState == 1)
			{
				if(selecter == 0) {
					selecter = 3;
				}
				else {
					selecter--;
				}
			}
			
			if(gameState == 4)
			{
				if(trackSelect == 0)
				{
					trackSelect = tMenu.numTracks()-1;
				}
				else
				{
					trackSelect--;
				}
			}
		}
		
		if(keyCode == DOWN)
		{
			if(gameState == 1)
			{
				if(selecter == 3) {
					selecter = 0;
				}
				else {
					selecter++;
				}
			}
			
			if(gameState == 4)
			{
				if(trackSelect == tMenu.numTracks()-1)
				{
					trackSelect = 0;
				}
				else
				{
					trackSelect++;
				}
			}
		}
		
		if(key == 10)//if return key
		{
			if(state == 2)
			{
				for(int i = 0; i < gameObjects.size();i++)
				{
					GameObject o = gameObjects.get(i);
					
					if(o instanceof Instruction)
					{
						gameObjects.remove(o);
					}
				}//end for
			}//end if state == 2
			
			if(state == 4 && inTMenu)
			{
				song = minim.loadFile(tMenu.getTPaths().get(trackSelect), FRAME_SIZE);
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
				state = 0;
				trackSelected = true;
			}
			
			gameState = state;
		}//end if return key
		
		if(key == 'x')
		{
			if(gameState == 4 || gameState == 5)//if @ track select or score screen
			{
				gameState = 0;//reset and go back to main menu
			}
		}
	}
	
	public static boolean setInTMenu(boolean s)
	{
		return inTMenu = s;
	}
	
	public static int getSelecter()
	{
		return selecter;
	}
	
	public static int getTrackSelect()
	{
		return trackSelect;
	}
	
	public static void setTrackSelect(int t)
	{
		trackSelect = t;
	}
	
	public static boolean getScoreWritten()
	{
		return scoreWritten;
	}
	
	public static void setScoreWritten(boolean scoreW)
	{
		scoreWritten = scoreW;
	}
	
	public static PFont getGameText()
	{
		return gameText;
	}
	
	public static int getState()
	{
		return state;
	}
	
	public static Player getPlayer()
	{
		return player;
	}
	
	public static Instruction getControls()
	{
		return controls;
	}
	
	public static Chooser getArrow()
	{
		return arrow;
	}

}
