package uiComponents;

import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import lib.SpectrumHandler;


public class SpeedUpMoviePlayer
{
	private Media movie;
	private MediaPlayer player;

	private SpectrumHandler specHandler;

	private boolean playing = false;
	
	public SpeedUpMoviePlayer(Media media) 
	{
		this.movie = media;
		this.player = new MediaPlayer(this.movie);
		this.specHandler = new SpectrumHandler(player);
	}
	
	
	public void play(Stage stage, VBox vbox, Slider timeSlider, ToggleButton playButton)
	{
		this.playing = true;
		player.play();
		player.setOnReady(new Runnable() {
			
			@Override
			public void run() 
			{
				int playerViewHeight =player.getMedia().getHeight();
				int width = player.getMedia().getWidth();
				
				stage.setMinHeight(playerViewHeight);
				stage.setMinWidth(width);
				
				vbox.setMinSize(100,width);
				vbox.setTranslateY(playerViewHeight-20);
				
				timeSlider.setMin(0.0);
				timeSlider.setMax(player.getTotalDuration().toSeconds());
				timeSlider.setMinWidth(0.97*(width-playButton.getWidth()));
				timeSlider.setTranslateY(playButton.getHeight()/2.0);
				player.setRate(specHandler.groundspeed);
			}
		});
		this.initPlayer();
	}
	
	public void switchMedia(Media media)
	{

		player.stop();
		player = null;
		player = new MediaPlayer(movie);
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
