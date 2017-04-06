package jfx;

import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BalloonView extends Application
{	
	
	public void start(Stage stage) throws Exception 
	{
		Pane pane = new Pane();
		
		Scene mainScene = new Scene(pane, 800, 800);
		
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(100);
		box.setMinSize(pane.getWidth(), pane.getHeight());
		
		Button playButton = new Button("Play");
		playButton.setPadding(new Insets(10, 50, 10, 50));
		playButton.setOnAction((e)->
		{
			//Each button on the menu starts a different manager. The GamePlayManager is the one that is called with the 'play' button 
			
			GamePlayManager manager=new GamePlayManager(stage, mainScene);
			
			manager.start();
		});

		box.getChildren().add(playButton);
		pane.getChildren().add(box);
		
		stage.setScene(mainScene);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
