package uiComponents;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lib.SpectrumHandler;
import application.VideoPlayer;


public class SpeedUpMoviePlayer
{
	private Media movie;
	private MediaPlayer player;
	private SpeedUpMoviePlayer instance;

	private SpectrumHandler specHandler;

	private boolean playing = false;
	
	public SpeedUpMoviePlayer(Media media) 
	{
		instance = this;
		this.movie = media;
		this.player = new MediaPlayer(this.movie);
		this.specHandler = new SpectrumHandler(player);
	}
	
	
	public void play(VideoPlayer main)
	{
		this.playing = true;
		player.play();
		player.setOnReady(new Runnable() {
			
			@Override
			public void run() 
			{
				int playerViewHeight =player.getMedia().getHeight();
				int width = player.getMedia().getWidth();
				
				main.getStage().setMinHeight(playerViewHeight);
				main.getStage().setMinWidth(width);
				
				main.getVbox().setMinSize(100,width);
				main.getVbox().setTranslateY(playerViewHeight-20);
				
				main.getTimeSlider().init(instance, main.getPlayButton(), width);
				
				player.setRate(specHandler.groundspeed);
			}
		});
		this.initPlayer();
	}
	
	public void switchMedia(Media media)
	{

		player.stop();
		player = null;
		player = new MediaPlayer(media);
	}
	
	private void initPlayer()
	{
		player.setAudioSpectrumListener(new AudioSpectrumListener() {
			
			@Override
			public void spectrumDataUpdate(double arg0, double arg1, float[] magnitudes, float[] arg3) 
			{
				specHandler.spectrumDataUpdate(magnitudes);
			}
		});
	}
	
	public void switchPlay() 
	{
		if(playing)
		{
			playing = false;
			player.pause();
		}
		else
		{
			playing = true;
			player.play();
		}
	}
	
	public MediaPlayer getPlayer() 
	{
		return player;
	}
}
