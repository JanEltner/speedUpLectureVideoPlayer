package lib;

import javafx.scene.media.MediaPlayer;

public class SpectrumHandler 
{
	public float groundspeed = (float) 1.3;
	private float maxspeed = (float) 2.3;
	
	private int upperLimitPerRound = 13;
	private int speedUpDelay = 7;
	private boolean slowDowned = false;
	private int delayCounter;
	private float[] last;
	private int upperLimit = upperLimitPerRound*this.last.length;
	private MediaPlayer player;
	
	public SpectrumHandler(MediaPlayer player) 
	{
		this.player = player;
		this.last = new float[5];
		for(@SuppressWarnings("unused") float f : this.last)
		{
			f = (float) 0.0;
		}
		
		
	}
	
	public void spectrumDataUpdate(float[] magnitudes) 
	{
		float max = (float) 0.0;
		for(int i = 0; i < magnitudes.length;i++)
		{
			float tmp = magnitudes[i] + 60;
			if(tmp>max)
			{
				max = tmp;
			}
		}
		float sum = 0;
		for(int n=1;n<last.length;n++)
		{
			last[n-1] = last[n];
			sum += last[n];
		}
		last[last.length-1] = max;
		sum += max;
		if(sum>upperLimit)
		{
			if(!slowDowned)
			{
				player.setRate(groundspeed);
				player.setVolume(1.0);
				slowDowned = true;
			}
			delayCounter = speedUpDelay;
		}
		else if(slowDowned && sum<= upperLimit)
		{
			if(delayCounter == 0)
			{
				player.setRate(maxspeed);
				player.setVolume(0.3);
				slowDowned = false;
			}
			else
			{
				delayCounter -= 1;
			}
		}	
	}
}
