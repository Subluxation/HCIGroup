package jfx;

import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BalloonView extends Application
{	
	
	public void start(Stage stage) throws Exception 
	{
		//Each button on the menu starts a different manager. The GamePlayManager is the one that is called with the 'play' button 
		
		GamePlayManager manager=new GamePlayManager(stage);
		
		manager.start();
		
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
