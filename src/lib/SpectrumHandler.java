package lib;

import javafx.scene.media.MediaPlayer;

public class SpectrumHandler 
{
	public float groundspeed = (float) 1.3;
	private float maxspeed = (float) 2.3;
	
	private int upperLimitPerRound = 20;
	private int speedUpDelay = 7;
	private boolean slowDowned = false;
	private int delayCounter;
	private float[] last;
	private int upperLimit;
	private MediaPlayer player;
	
	public SpectrumHandler(MediaPlayer player) 
	{
		this.player = player;
		this.last = new float[5];
		for(@SuppressWarnings("unused") float f : this.last)
		{
			f = (float) 0.0;
		}
		this.upperLimit = this.upperLimitPerRound*this.last.length;
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

	public float getGroundspeed() {
		return groundspeed;
	}

	public void setGroundspeed(float groundspeed) {
		this.groundspeed = groundspeed;
	}

	public float getMaxspeed() {
		return maxspeed;
	}

	public void setMaxspeed(float maxspeed) {
		this.maxspeed = maxspeed;
	}

	public int getUpperLimitPerRound() {
		return upperLimitPerRound;
	}

	public void setUpperLimitPerRound(int upperLimitPerRound) {
		this.upperLimitPerRound = upperLimitPerRound;
		this.upperLimit = this.upperLimitPerRound*this.last.length;
	}

	public int getSpeedUpDelay() {
		return speedUpDelay;
	}

	public void setSpeedUpDelay(int speedUpDelay) {
		this.speedUpDelay = speedUpDelay;
	}
	
	public int getWindows(){
		return last.length;
	}
	
	public void setWindows(int windows){
		float[] tmp = new float[windows];
		int smallerWindowsBegin = (windows<last.length)?last.length-windows:0;
		for(int i = 0;smallerWindowsBegin+i<last.length;i++)
		{
			tmp[i] = last[smallerWindowsBegin+i];
		}
		last = tmp;
	}
}
