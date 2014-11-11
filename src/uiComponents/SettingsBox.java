package uiComponents;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lib.InputSanitizer;

public class SettingsBox extends HBox 
{

	public SettingsBox(SpeedUpMoviePlayer player) 
	{
		super();

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
		
		this.getChildren().add(limitBox);
		this.getChildren().add(windowsBox);
		this.getChildren().add(groundSpeedBox);
		this.getChildren().add(maxSpeedBox);
		this.getChildren().add(speedUpDelayBox);

	}
	
}
