package application;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application 
{
	private float groundspeed = (float) 1.3;
	private float maxspeed = (float) 2.3;
	
	private int upperLimitPerRound = 13;
	private int speedUpDelay = 7;
	private boolean slowDowned = false;
	private int delayCounter;

	private Media movie = new Media("file:///D:/media.m4v");
	private MediaPlayer player = new MediaPlayer(movie);
	private MediaView mediaview = new MediaView(player);
	
	@Override
	public void start(Stage stage) 
	{
		try 
		{
			Slider timeSlider = new Slider();
			VBox vbox = new VBox();
			vbox.getChildren().add(timeSlider);
			
			BorderPane root = new BorderPane();
			root.getChildren().add(mediaview);
			root.getChildren().add(vbox);
			Scene scene = new Scene(root,400,400,Color.BLACK);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
			
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
					
					player.setRate(groundspeed);
				}
			});
			
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
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	private void initPlayer()
	{
		float[] last = new float[5];
		for(@SuppressWarnings("unused") float f : last)
		{
			f = (float) 0.0;
		}
		int upperLimit = upperLimitPerRound*last.length;
		
		player.setAudioSpectrumListener(new AudioSpectrumListener() {
			
			@Override
			public void spectrumDataUpdate(double arg0, double arg1, float[] magnitudes, float[] arg3) 
			{
				float max = (float) 0.0;
				for(int i = 0; i < magnitudes.length;i++)
				{
					float tmp = magnitudes[i] + 60;
					if(tmp>max)
					{
						max = tmp;
					}
				}
				float sum = 0;
				for(int n=1;n<last.length;n++)
				{
					last[n-1] = last[n];
					sum += last[n];
				}
				last[last.length-1] = max;
				sum += max;
				if(sum>upperLimit)
				{
					if(!slowDowned)
					{
						player.setRate(groundspeed);
						player.setVolume(1.0);
						slowDowned = true;
					}
					delayCounter = speedUpDelay;
				}
				else if(slowDowned && sum<= upperLimit)
				{
					if(delayCounter == 0)
					{
						player.setRate(maxspeed);
						player.setVolume(0.3);
						slowDowned = false;
					}
					else
					{
						delayCounter -= 1;
					}
				}				
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
					player.play();
					initPlayer();
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
}