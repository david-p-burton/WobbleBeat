package src.test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AudioLoader {
	
		private ArrayList<String> tracks;
		private ArrayList<String> tPaths;
		
		public AudioLoader() throws IOException
		{
			tracks = new ArrayList<String>();
			tPaths = new ArrayList<String>();
			getMusic();
		}
		
		public void getMusic() throws IOException 
		{
			try {
				File dir = new File(System.getProperty("user.home", "/desktop/music"));
				if(dir != null) {
					ArrayList<File> files = new ArrayList<File>(Arrays.asList(dir.listFiles()));
					recListFile(files);	
				}
	        } catch(NullPointerException n) {
	            // more general: catch(Error n)
	            // anything: catch(Throwable n)
	            System.out.println("Caught "+n);
	            n.printStackTrace();
	        }
			
		}//end getMusic()
		
		public void recListFile(ArrayList<File> list) throws IOException {//prints all files in list and files within sub directories from that list
			for(File i : list )
			{
				if(i.isDirectory())
				{//directory in directory case
					if(i.listFiles() != null) 
					{
						ArrayList<File> files = new ArrayList<File>(Arrays.asList(i.listFiles()));
						recListFile(files);
					}
				}
				else
				{//normal case (is a file)
					String filePath = i.toString();
					String fileExt = filePath.substring(filePath.lastIndexOf(".")+1);
					if(fileExt.equals("mp3"))
					{
						String trackName = i.getName().substring(0, i.getName().lastIndexOf("."));
						tracks.add(trackName);
						tPaths.add(filePath);
							
					}//end if mp3
				}//end else
			}//end for
		}//end recListFile
		
		
		public ArrayList<String> getTracks()
		{
			return tracks;
		}
		
		public ArrayList<String> getFilePaths()
		{
			return tPaths;
		}

}



