package lib;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import application.VideoPlayer;

public class DragAndDrop 
{
	public static void setupDragAndDrop(Node node, VideoPlayer main)
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
					main.getPlayer().switchMedia(movie);
					main.getMediaview().setMediaPlayer(main.getPlayer().getPlayer());
					main.getPlayer().play(main);
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
}
