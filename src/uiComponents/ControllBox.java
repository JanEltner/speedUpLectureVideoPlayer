package uiComponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class ControllBox extends HBox
{

	private ToggleButton playButton = new ToggleButton();
	private ToggleGroup playButtonGroup = new ToggleGroup();
	private TimeSlider timeSlider;
	private SpeedUpMoviePlayer player;
	
	public ControllBox(SpeedUpMoviePlayer player) 
	{
		super();
		this.player = player;
		this.timeSlider = new TimeSlider(player);
		this.getChildren().add(playButton);
		this.getChildren().add(timeSlider);

		player.getPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>(){
			
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration arg1, Duration currentTime) 
			{
				timeSlider.setTimeValue(currentTime);
			}
		});
	}
	

	public void initPlayButton(BorderPane root)
	{
		playButton.setToggleGroup(playButtonGroup);
		playButton.getStylesheets().addAll(getClass().getResource("../application/application.css").toExternalForm());
		playButton.getStyleClass().add("playPauseButton");
		playButton.setScaleX(0.7);
		playButton.setScaleY(0.7);
		playButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) 
			{
				player.switchPlay();
				root.requestFocus();
			}
		});
	}

	public ToggleButton getPlayButton() {
		return playButton;
	}

	public TimeSlider getTimeSlider() {
		return timeSlider;
	}
}
