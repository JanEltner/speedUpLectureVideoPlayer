package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lib.DragAndDrop;
import uiComponents.SpeedUpMoviePlayer;
import uiComponents.TimeSlider;

public class VideoPlayer extends Application 
{
	private Media movie = new Media("file:///D:/media.m4v");
	private SpeedUpMoviePlayer player = new SpeedUpMoviePlayer(movie);
	private MediaView mediaview = new MediaView(player.getPlayer());
	private VBox vbox = new VBox();
	private TimeSlider timeSlider = new TimeSlider(player);
	protected Stage stage;
	private ToggleButton playButton = new ToggleButton();
	private ToggleGroup playButtonGroup = new ToggleGroup();
	private BorderPane root;
	
	@Override
	public void start(Stage stage) 
	{
		try 
		{
			this.stage = stage;
			
			HBox controlBox = new HBox();
			controlBox.getChildren().add(playButton);
			controlBox.getChildren().add(timeSlider);
			vbox.getChildren().add(controlBox);
			
			root = new BorderPane();
			root.getChildren().add(mediaview);
			root.getChildren().add(vbox);
			Scene scene = new Scene(root,400,400,Color.BLACK);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm());
			initPlayButton();
			root.requestFocus();
			
			stage.setScene(scene);
			stage.show();
			
			
			root.setOnKeyPressed(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) 
				{
					if(keyEvent.getCode()==KeyCode.SPACE)
					{
						player.switchPlay();
					}
				}
			});
			
			this.player.play(this);
			player.getPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>(){
				
				@Override
				public void changed(ObservableValue<? extends Duration> observableValue, Duration arg1, Duration currentTime) 
				{
					timeSlider.setTimeValue(currentTime);
				}
			});
			
			DragAndDrop.setupDragAndDrop(root, this);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void initPlayButton()
	{
		playButton.setToggleGroup(playButtonGroup);
		playButton.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm());
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
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	public Media getMovie() {
		return movie;
	}

	public SpeedUpMoviePlayer getPlayer() {
		return player;
	}

	public MediaView getMediaview() {
		return mediaview;
	}

	public TimeSlider getTimeSlider() {
		return timeSlider;
	}

	public Stage getStage() {
		return stage;
	}

	public ToggleButton getPlayButton() {
		return playButton;
	}

	public VBox getVbox() {
		return vbox;
	}
	
}