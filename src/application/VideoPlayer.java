package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lib.DragAndDrop;
import uiComponents.ControllBox;
import uiComponents.SettingsBox;
import uiComponents.SpeedUpMoviePlayer;
import uiComponents.TimeSlider;

public class VideoPlayer extends Application 
{
	private Media movie = new Media("file:///D:/media.m4v");
	private SpeedUpMoviePlayer player = new SpeedUpMoviePlayer(movie);
	private MediaView mediaview = new MediaView(player.getPlayer());
	private VBox vbox = new VBox();
	protected Stage stage;
	private BorderPane root;
	private SettingsBox settingsBox;
	private ControllBox controlBox;
	
	@Override
	public void start(Stage stage) 
	{
		try 
		{
			this.stage = stage;
			root = new BorderPane();
			
			controlBox = new ControllBox(player);
			settingsBox = new SettingsBox(player);
			vbox.getChildren().add(controlBox);
			vbox.getChildren().add(settingsBox);
			root.getChildren().add(mediaview);
			root.getChildren().add(vbox);
			
			Scene scene = new Scene(root,400,470,Color.BLACK);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm());
			controlBox.initPlayButton(root);
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
			
			DragAndDrop.setupDragAndDrop(root, this);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	public Media getMovie() {
		return this.movie;
	}

	public SpeedUpMoviePlayer getPlayer() {
		return this.player;
	}

	public MediaView getMediaview() {
		return this.mediaview;
	}

	public TimeSlider getTimeSlider() {
		return this.controlBox.getTimeSlider();
	}

	public Stage getStage() {
		return this.stage;
	}

	public ToggleButton getPlayButton() {
		return this.controlBox.getPlayButton();
	}

	public VBox getVbox() {
		return this.vbox;
	}
}