package lib;

import uiComponents.SpeedUpMoviePlayer;
import uiComponents.TimeSlider;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class DragAndDrop 
{
	public static void setupDragAndDrop(Node node, MediaView mediaview, SpeedUpMoviePlayer player,Stage stage, VBox vbox, TimeSlider timeSlider, ToggleButton playButton)
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
					Media movie = new Media("file:///" + filePath.replace("\\", "/"));
					player.switchMedia(movie);
					mediaview.setMediaPlayer(player.getPlayer());
					player.play(stage, vbox, timeSlider, playButton);
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
}
