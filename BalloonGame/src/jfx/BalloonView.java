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

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

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
		

		//NEW BACKGROUND
		BackgroundImage balloonImage= new BackgroundImage(new Image(GamePlayManager.class.getResource("balloon#2.jpeg").toExternalForm()),
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(800,800,false,false,false,false));
		pane.setBackground(new Background(balloonImage));
		
		
		Button playButton = new Button("Play");
		Button tutButton = new Button("Tutorial");
		Button highScoreButton = new Button("HighScores");
		Label title = new Label();
		
		title.setText("Balloons: The Game");
		title.setStyle("-fx-font: 42 arial;");
		
		tutButton.setPadding(new Insets(10, 50, 10, 50));
		playButton.setPadding(new Insets(10, 50, 10, 50));
		playButton.setOnAction((e)->
		{
			//Each button on the menu starts a different manager. The GamePlayManager is the one that is called with the 'play' button 
			
			GamePlayManager manager=new GamePlayManager(stage, mainScene);
			
			//manager.start();
		});
		tutButton.setOnAction((e)->
		{
			//Each button on the menu starts a different manager. The StoreManager is the one that is called with the 'Main Menu' button 
			
			TutorialManager tutManage = new TutorialManager(stage, mainScene);			
			
		});
		highScoreButton.setOnAction((e)->
		{
			//Each button on the menu starts a different manager. The StoreManager is the one that is called with the 'Main Menu' button 
			
			HighScoreManager hsm = new HighScoreManager(stage, mainScene);
			
			
		});
		box.getChildren().add(title);
		
		box.getChildren().add(playButton);
		box.getChildren().add(tutButton);
		box.getChildren().add(highScoreButton);

		

		pane.getChildren().add(box);
		
		stage.setScene(mainScene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}