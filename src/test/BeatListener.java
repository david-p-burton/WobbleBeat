package test;

/*
 * This is straight from the minim site
 * 
 */

import ddf.minim.*;
import ddf.minim.analysis.*;
import processing.core.PApplet;


class BeatListener implements AudioListener
{
 
  private AudioSample source;
  private Minim minim;
  private BeatDetect beat;
  private PApplet p;
 
  
  
  float kickSize, snareSize, hatSize;
  
  public BeatListener(BeatDetect beat, AudioSample audioInput, PApplet p)
  {
    this.source = audioInput;
    this.source.addListener(this);
    this.beat = beat;
    this.p = p;
  }
  
  public void samples(float[] samps)
  {
    beat.detect(source.mix);
  }
  
  public void samples(float[] sampsL, float[] sampsR)
  {
    beat.detect(source.mix);
  }
}
