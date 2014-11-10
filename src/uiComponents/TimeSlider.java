package uiComponents;

import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class TimeSlider extends Slider 
{
	protected SpeedUpMoviePlayer player;
	private boolean changingTimePosition = false;
	
	public TimeSlider(SpeedUpMoviePlayer player) 
	{
		super();
		this.player = player;
		this.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) 
			{
				changingTimePosition = true;
			}
		});
		
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) 
			{
				changingTimePosition = false;
			}
		});
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) 
			{
				player.getPlayer().seek(Duration.seconds(getValue()));
			}
		});
	}
	
	public void setTimeValue(Duration value)
	{
		if(!changingTimePosition)
		{
			this.setValue(value.toSeconds());
		}
	}
	
	public void updatePlayer(SpeedUpMoviePlayer player)
	{
		this.player = player;
		this.setMin(0.0);
		this.setMax(player.getPlayer().getTotalDuration().toSeconds());
	}
	
	public void init(SpeedUpMoviePlayer player, ToggleButton playButton, int width)
	{
		updatePlayer(player);
		this.setMinWidth(0.97*(width-playButton.getWidth()));
		this.setTranslateY(playButton.getHeight()/2.0);
	}
}
