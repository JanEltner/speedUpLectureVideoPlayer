package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lib.DragAndDrop;
import lib.InputSanitizer;
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
			
			HBox settingsBox = new HBox();

			VBox limitBox = new VBox();
			Text limitText = new Text("Schwellwert");
			TextField limitInput = new TextField();
			limitBox.getChildren().add(limitText);
			limitBox.getChildren().add(limitInput);
			limitInput.setMaxWidth(50);
			limitBox.setMinWidth(100);
			limitInput.setText(player.getSpecHandler().getUpperLimitPerRound() + "");
			limitInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) {
					if(keyEvent.getCode()==KeyCode.ENTER)
					{
						int newLimits = InputSanitizer.stringToInt(limitInput.getText(), 1);
						player.getSpecHandler().setUpperLimitPerRound(newLimits);
						limitInput.setText(newLimits + "");
					}
				}
			});

			VBox windowsBox = new VBox();
			Text windowText = new Text("Windows");
			TextField windowInput = new TextField();
			windowsBox.getChildren().add(windowText);
			windowsBox.getChildren().add(windowInput);
			windowInput.setMaxWidth(50);
			windowsBox.setMinWidth(100);
			windowInput.setText(player.getSpecHandler().getWindows() + "");
			windowInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) {
					if(keyEvent.getCode()==KeyCode.ENTER)
					{
						int newwindows = InputSanitizer.stringToInt(windowInput.getText(), 1);
						player.getSpecHandler().setWindows(newwindows);
						windowInput.setText(newwindows + "");
					}
				}
			});

			VBox groundSpeedBox = new VBox();
			Text groundSpeedText = new Text("Basisgeschw.");
			TextField groundSpeedInput = new TextField();
			groundSpeedBox.getChildren().add(groundSpeedText);
			groundSpeedBox.getChildren().add(groundSpeedInput);
			groundSpeedInput.setMaxWidth(50);
			groundSpeedBox.setMinWidth(100);
			groundSpeedInput.setText(player.getSpecHandler().getGroundspeed()+ "");
			groundSpeedInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) {
					if(keyEvent.getCode()==KeyCode.ENTER)
					{
						float newGroundspeed = InputSanitizer.stringToFloat(groundSpeedInput.getText(), 1.0f);
						player.getSpecHandler().setGroundspeed(newGroundspeed);
						groundSpeedInput.setText(newGroundspeed + "");
					}
				}
			});

			VBox maxSpeedBox = new VBox();
			Text maxSpeedText = new Text("Maximalgeschw.");
			TextField maxSpeedInput = new TextField();
			maxSpeedBox.getChildren().add(maxSpeedText);
			maxSpeedBox.getChildren().add(maxSpeedInput);
			maxSpeedInput.setMaxWidth(50);
			maxSpeedBox.setMinWidth(100);
			maxSpeedInput.setText(player.getSpecHandler().getMaxspeed()+ "");
			maxSpeedInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) {
					if(keyEvent.getCode()==KeyCode.ENTER)
					{
						float newMaxspeed = InputSanitizer.stringToFloat(maxSpeedInput.getText(), 1.0f);
						player.getSpecHandler().setMaxspeed(newMaxspeed);
						maxSpeedInput.setText(newMaxspeed + "");
					}
				}
			});

			VBox speedUpDelayBox = new VBox();
			Text speedUpDelayText = new Text("Speed-Up Delay");
			TextField speedUpDelayInput = new TextField();
			speedUpDelayBox.getChildren().add(speedUpDelayText);
			speedUpDelayBox.getChildren().add(speedUpDelayInput);
			speedUpDelayInput.setMaxWidth(50);
			speedUpDelayBox.setMinWidth(100);
			speedUpDelayInput.setText(player.getSpecHandler().getSpeedUpDelay() + "");
			speedUpDelayInput.setOnKeyReleased(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) {
					if(keyEvent.getCode()==KeyCode.ENTER)
					{
						int newSpeedUpDelay = InputSanitizer.stringToInt(speedUpDelayInput.getText(), 1);
						player.getSpecHandler().setMaxspeed(newSpeedUpDelay);
						speedUpDelayInput.setText(newSpeedUpDelay + "");
					}
				}
			});
			
			settingsBox.getChildren().add(limitBox);
			settingsBox.getChildren().add(windowsBox);
			settingsBox.getChildren().add(groundSpeedBox);
			settingsBox.getChildren().add(maxSpeedBox);
			settingsBox.getChildren().add(speedUpDelayBox);

			vbox.getChildren().add(controlBox);
			vbox.getChildren().add(settingsBox);
			root = new BorderPane();
			root.getChildren().add(mediaview);
			root.getChildren().add(vbox);
			Scene scene = new Scene(root,400,470,Color.BLACK);
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