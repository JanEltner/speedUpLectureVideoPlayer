package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import uiComponents.SpeedUpMoviePlayer;
import uiComponents.TimeSlider;


public class Main extends Application 
{

	private Media movie = new Media("file:///D:/media.m4v");
	private SpeedUpMoviePlayer player2 = new SpeedUpMoviePlayer(movie);
	private MediaView mediaview = new MediaView(player2.getPlayer());
	private VBox vbox = new VBox();
	private TimeSlider timeSlider = new TimeSlider(player2);
	private Stage stage;
	private ToggleButton playButton = new ToggleButton();
	private ToggleGroup playButtonGroup = new ToggleGroup();
	
	
	@Override
	public void start(Stage stage) 
	{
		try 
		{
			this.stage = stage;
			
			HBox controlBox = new HBox();
			playButton.setToggleGroup(playButtonGroup);
			
			controlBox.getChildren().add(playButton);
			controlBox.getChildren().add(timeSlider);
			
			vbox.getChildren().add(controlBox);
			
			BorderPane root = new BorderPane();
			root.getChildren().add(mediaview);
			root.getChildren().add(vbox);
			Scene scene = new Scene(root,400,400,Color.BLACK);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm());
			playButton.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm());
			playButton.getStyleClass().add("playPauseButton");
			playButton.setScaleX(0.7);
			playButton.setScaleY(0.7);
			root.requestFocus();
			playButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

				@Override
				public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) 
				{
					switchPlay();
					root.requestFocus();
				}
			});
			stage.setScene(scene);
			stage.show();
			
			
			root.setOnKeyPressed(new EventHandler<KeyEvent>() {
				
				@Override
				public void handle(KeyEvent keyEvent) 
				{
					if(keyEvent.getCode()==KeyCode.SPACE)
					{
						switchPlay();
					}
				}
			});
			
			play();
			
			
			player2.getPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {


				@Override
				public void changed(ObservableValue<? extends Duration> observableValue, Duration arg1, Duration currentTime) 
				{
					timeSlider.setTimeValue(currentTime);
				}
			});
			
			
			
			setupDragAndDrop(root);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void switchPlay() 
	{
		player2.switchPlay();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	private void play()
	{
		this.player2.play(stage, vbox, timeSlider, playButton);
	}
	
	

	private void setupDragAndDrop(Node node)
	{

		node.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) 
			{
				Dragboard db = event.getDragboard();
				if(db.hasFiles())
				{
					event.acceptTransferModes(TransferMode.COPY);
				}
				else
				{
					event.consume();
				}
			}
		});
		
		node.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) 
			{
				Dragboard db = event.getDragboard();
				boolean success = false;
				if(db.hasFiles())
				{
					success = true;
					String filePath = db.getFiles().get(0).toString();
					movie = new Media("file:///" + filePath.replace("\\", "/"));
					player2.switchMedia(movie);
					mediaview.setMediaPlayer(player2.getPlayer());
					play();
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
}