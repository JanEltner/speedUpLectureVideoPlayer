package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private Scene scene;
	protected Stage stage;
	private BorderPane root;
	private SettingsBox settingsBox;
	private ControllBox controlBox;
	
	private boolean isFullscreen = false;
	
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
			
			this.scene = new Scene(root,400,470,Color.BLACK);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm());
			controlBox.initPlayButton(root);
			root.requestFocus();
			
			
			stage.setScene(scene);
			stage.show();
			
			
			root.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) 
				{
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
					{
						switchFullscreen();
					}
				}
			});
			
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
	
	private void switchFullscreen()
	{
		double height = 400;
		double width = 470;
		if(!this.isFullscreen)
		{
			Screen primaryScreen = Screen.getPrimary();
			height = primaryScreen.getVisualBounds().getHeight();
			width = primaryScreen.getVisualBounds().getWidth();
		}
		changeFullscreenHelper(height, width, !isFullscreen);
		System.out.println(height + " - " + width );
	}
	
	private void changeFullscreenHelper(double height, double width, boolean fullscreen)
	{
		stage.hide();
		stage = new Stage();
		stage.setScene(scene);
		stage.setHeight(height);
		stage.setWidth(width);
		mediaview.fitHeightProperty().set(height);
		mediaview.fitWidthProperty().set(width);
		stage.setFullScreen(fullscreen);
		stage.initStyle(fullscreen?StageStyle.UNDECORATED:StageStyle.DECORATED);
		vbox.setTranslateY(height-90);
		stage.show();
		this.isFullscreen = fullscreen;
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