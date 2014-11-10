package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
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
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lib.SpectrumHandler;


public class Main extends Application 
{

	private Media movie = new Media("file:///D:/media.m4v");
	private MediaPlayer player = new MediaPlayer(movie);
	private MediaView mediaview = new MediaView(player);
	private VBox vbox = new VBox();
	private Slider timeSlider = new Slider();
	private Stage stage;
	private boolean playing = false;
	
	private SpectrumHandler specHandler = new SpectrumHandler(player);
	
	@Override
	public void start(Stage stage) 
	{
		try 
		{
			this.stage = stage;
			
			HBox controlBox = new HBox();
			ToggleButton playButton = new ToggleButton();
			ToggleGroup playButtonGroup = new ToggleGroup();
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
			
			
			player.currentTimeProperty().addListener(new ChangeListener<Duration>() {


				@Override
				public void changed(ObservableValue<? extends Duration> observableValue,
						Duration arg1, Duration currentTime) 
				{
					timeSlider.setValue(currentTime.toSeconds());
				}
			});
			
			initPlayer();
			setupDragAndDrop(root);
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
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
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	private void play()
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
				timeSlider.setMinWidth(width);
				
				player.setRate(specHandler.groundspeed);
			}
		});
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
					System.out.println(filePath);
					player.stop();
					player = null;
					movie = new Media("file:///" + filePath.replace("\\", "/"));
					player = new MediaPlayer(movie);
					mediaview.setMediaPlayer(player);
					play();
					initPlayer();
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
}