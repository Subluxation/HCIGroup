package jfx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePlayManager
{
	private Scene gameScene;
	private Scene mainScene;
	private Pane pane;
	private Stage stage;
	private ArrayList<Balloon> balloons;
	private Timeline timeline;
	private Label scoreLabel;
	private Label livesLabel;
	private int lives;
	private int score;
	private BackgroundImage bI;
	private String username;
	
	public GamePlayManager(Stage stage, Scene mainScene)
	{
		this.stage=stage;
		this.mainScene = mainScene;
		
		
		balloons=new ArrayList<Balloon>();
		//need to create a function that lets user input username for record
		inputUserName();
		createGameScene();
	}
	//First Prompt User for username before starting game
	private void inputUserName(){
		stage.setScene(gameScene);
		pane = new Pane();
		VBox box = new VBox();
		Label name = new Label("Please Enter Username: ");
		TextField input = new TextField();
		Button enter = new Button("Enter");
		box.getChildren().add(name);
		box.getChildren().add(input);
		box.getChildren().add(enter);
		
		pane.getChildren().add(box);
		gameScene=new Scene(pane,800,800);
		stage.setScene(gameScene);
		enter.setOnAction((e)->
		{
			username = input.getText();
			start();
		});
		
	}
	
	private void createGameScene()
	{
		pane=new Pane();
		//testing bomb position
		pane.setOnMouseClicked((e)->
		{
			double x=e.getSceneX();
			double y=e.getSceneY();
			
			System.out.println("x: "+x+"       y: "+y);
		});
		
		//NEW BACKGROUND
		bI= new BackgroundImage(new Image(GamePlayManager.class.getResource("Clouds.jpeg").toExternalForm()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(800,800,false,false,false,false));
		pane.setBackground(new Background(bI));
		
		gameScene=new Scene(pane,800,800);
		
		scoreLabel=new Label("Score: 0");
		scoreLabel.setLayoutX(680);
		scoreLabel.setLayoutY(30);
		scoreLabel.setStyle("-fx-font: 24 arial;");
		
		livesLabel=new Label("Lives: 3");
		livesLabel.setLayoutX(680);
		livesLabel.setLayoutY(60);
		livesLabel.setStyle("-fx-font: 24 arial;");
		
		pane.getChildren().addAll(scoreLabel,livesLabel);
	}
	
	public void increaseScore()
	{
		++score;
		scoreLabel.setText("Score: "+Integer.toString(score));
	}
	
	private void quit() throws IOException
	{
		timeline.stop();
		//add User to HighScore list
		
		
		HighScoreManager hsm = new HighScoreManager(stage, mainScene);
		hsm.addScore(username, score);
		
		//Once lives have depleted, send to Game Over screen with option for store, main menu, etc.
		
		//stage.setScene(mainScene);
	}
	
	public void addBalloon(Balloon b)
	{
		balloons.add(b);
	}
	
	public void start()
	{
		lives=3;
		score=0;
		
		
		stage.setScene(gameScene);
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			Balloon b=new Balloon(pane,this);
			addBalloon(b);
	    }));
	    timeline.setCycleCount(Animation.INDEFINITE);
	    timeline.play();
		
	}
	
	public void removeLife() throws IOException
	{
		--lives;
		livesLabel.setText("Lives: "+Integer.toString(lives));
		
		if(lives==0)
		{
			quit();
		}
	}
}