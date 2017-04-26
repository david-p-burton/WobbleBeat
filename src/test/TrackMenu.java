package src.test;

import java.io.IOException;
import java.util.ArrayList;
import processing.core.PApplet;

public class TrackMenu {

	PApplet p;
	AudioLoader audio;
	ArrayList<String> tracks;
	int bufferSize, bufferHead, bufferTail;
	float x, y, wwidth, wheight, blockSize;
	String[] buffer;
	
	public TrackMenu(PApplet p) throws IOException
	{
		this.p = p;
		audio = new AudioLoader();
		tracks = new ArrayList<String>(audio.getTracks());
		this.x = (float)(p.width*0.1);
		this.y = (float)(p.height*0.1);
		this.wwidth = (float)(p.width*0.8);
		this.wheight = (float)(p.height*0.8);
		this.bufferSize = 6;
		this.bufferHead = 0;
		this.bufferTail = bufferSize;
		this.blockSize = wheight/bufferSize;
		this.buffer = new String[bufferSize];
	}
	
	public void loadBuffer(int head, int tail)
	{
		for(int i=0; i<bufferSize; i++)
		{
			buffer[i] = null;
		}
		for(int i=0; i<bufferSize; i++)
		{
			if(head+i < tracks.size())
			{
				buffer[i] = tracks.get(i+head);
			}
		}
	}
	
	public ArrayList<String> getTPaths()
	{
		return audio.getFilePaths();
	}
	
	public int numTracks()
	{
		return tracks.size();
	}
	
	public void render()
	{
		Main.setInTMenu(true);
		loadBuffer(bufferHead, bufferTail);
		p.pushMatrix();
			p.background(0);
			p.fill(255);
			p.stroke(255);
			p.text("PRESS X TO RETURN OR ENTER TO SELECT", x+210, y-10);
			p.rect(x, y, wwidth, wheight);
			p.stroke(255,0,0);
			
			bufferHead = Main.getTrackSelect();
			bufferTail = (bufferHead+bufferSize)%tracks.size();
			
			for(int i=0; i<bufferSize; i++)
			{
				float h = blockSize*i+y;
				p.line(x, h, wwidth+x, h);
				if(buffer[i] != null)
				{
					System.out.println("buffer: "+buffer[i]);
					System.out.println("selected: "+tracks.get(Main.getTrackSelect()));
					if(buffer[i] == tracks.get(Main.getTrackSelect()))
					{
						p.fill(0,255,255);
						p.rect(x, h, wwidth, blockSize);
					}
					p.fill(0);
					p.textSize(12);
					p.text(buffer[i], x+wwidth/2, h+(blockSize/2));
				}
			}
		p.popMatrix();
	}

}

