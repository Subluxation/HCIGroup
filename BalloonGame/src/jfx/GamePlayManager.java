package jfx;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePlayManager
{
	private Scene gameScene;
	private Scene waveScene;
	private Scene mainScene;
	private Pane pane;
	private Stage stage;
	private ArrayList<Balloon> balloons;
	private Timeline timeline;
	private Label scoreLabel;
	private Label livesLabel;
	private int lives;
	private int score;
	private int wave;
	private int time;
	private int[] waves;
	private BackgroundImage bI;
	private String username;
	private int bombs;
	private int freezes;
	private int mult;
	private Label multTimer;
	private int time4Mult;
	private Label multLabel;
	private Label numMultLabel;
	private Label bombLabel;
	private Label freezeLabel;
	private boolean multBool;
	private MediaPlayer mediaPlayer;

	public GamePlayManager(Stage stage, Scene mainScene)
	{
		this.stage=stage;
		this.mainScene = mainScene;
		this.wave = 0;
		this.waves = new int[]{3, 45, 60};

		lives=3;
		score=0;
		mult = 1;
		bombs=2;
		freezes=2;
		multBool = false;
		time4Mult = 0;

		balloons=new ArrayList<Balloon>();
		//need to create a function that lets user input username for record
		inputUserName();
		createGameScene();
	}
	//First Prompt User for username before starting game
	private void inputUserName(){
		//SOUND
		String musicFile = "MainMenu.mp3";     // For example

		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		
		
		
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
		//SOUND
		mediaPlayer.play();
		//NEW BACKGROUND
		bI= new BackgroundImage(new Image(GamePlayManager.class.getResource("Clouds.jpeg").toExternalForm()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(800,800,false,false,false,false));
		pane.setBackground(new Background(bI));

		gameScene=new Scene(pane,800,800);
		gameScene.setOnKeyReleased((e)->
		{
			switch(e.getCode())
			{
			case Q:
				bombEvent();
				break;
			case W: 
				try
				{
					freezeEvent();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				break;
			case E:
				try {
					multiplierEvent();
				} 
				catch (InterruptedException e1) {

					e1.printStackTrace();
				}
				break;

			}
		});

		scoreLabel=new Label("Score: " + score);
		scoreLabel.setLayoutX(680);
		scoreLabel.setLayoutY(30);
		scoreLabel.setStyle("-fx-font: 24 arial;");

		livesLabel=new Label("Lives: " + lives);
		livesLabel.setLayoutX(680);
		livesLabel.setLayoutY(60);
		livesLabel.setStyle("-fx-font: 24 arial;");

		bombLabel=new Label("Bombs: " + bombs);
		bombLabel.setLayoutX(680);
		bombLabel.setLayoutY(90);
		bombLabel.setStyle("-fx-font: 24 arial;");

		freezeLabel=new Label("Freezes: " + freezes);
		freezeLabel.setLayoutX(680);
		freezeLabel.setLayoutY(120);
		freezeLabel.setStyle("-fx-font: 24 arial;");

		numMultLabel=new Label("Multipliers: " + mult);
		numMultLabel.setLayoutX(660);
		numMultLabel.setLayoutY(150);
		numMultLabel.setStyle("-fx-font: 18 arial;");

		multLabel=new Label("Multiplier Timer: " + time4Mult);
		multLabel.setLayoutX(608);
		multLabel.setLayoutY(180);
		multLabel.setStyle("-fx-font: 18 arial;");

		pane.getChildren().addAll(scoreLabel,livesLabel,bombLabel,freezeLabel, numMultLabel, multLabel);
	}

	private void bombEvent()
	{
		if(bombs>0)
		{
			for(Balloon b:balloons)
			{
				b.getTimeLine().stop();
				pane.getChildren().remove(b.getCircle());
				increaseScore();

			}

			balloons.clear();
			--bombs;
			bombLabel.setText("Bombs: "+Integer.toString(bombs));
		}
	}

	private void multiplierEvent() throws InterruptedException
	{
		if(mult > 0){
			time4Mult = 10;
			--mult;
			numMultLabel.setText("Multipliers: " + mult);
			multBool = true;
			multLabel.setText("Multiplier Timer: " + time4Mult);
			//TODO: java.lang.IllegalStateException: Not on FX application thread; ERROR
			new Thread( new Runnable() {

				public void run() {
					while(time4Mult > 0){
						try {

							Platform.runLater(new Runnable() {
								public void run(){
									--time4Mult;
									multBool = true;
									multLabel.setText("Multiplier Timer: " + time4Mult);
								}
							});
							Thread.sleep(1000);



						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					multBool = false;
				}
			} ).start();//read start()



			multLabel.setText("Multiplier Timer: " + time4Mult);
			multBool = false;

		}
	}

	private void freezeEvent() throws InterruptedException 
	{
		if(freezes>0)
		{
			for(Balloon b:balloons)
			{
				b.getTimeLine().pause();
			}

			new Thread( new Runnable() {
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(3);

						for(Balloon b:balloons)
						{
							b.getTimeLine().play();
						}

						--freezes;
						freezeLabel.setText("Freezes: "+Integer.toString(freezes));
					}
					catch( InterruptedException ie ) {
						//ignore
					}
				}
			} ).start();
		}
	}

	public void addBalloon(Balloon b)
	{
		balloons.add(b);
	}

	public void removeBalloon(Balloon b)
	{
		balloons.remove(b);
	}

	public void increaseScore()
	{
		if (multBool){
			score = score + 2;
		}
		else{
			++score;
		}

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

	public void start()
	{	
		stage.setScene(gameScene);

		timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			time++;
			if (time == waves[wave])
			{
				timeline.stop();
				for (Balloon b: balloons)
				{
					b.getTimeLine().stop();
					pane.getChildren().remove(b.getCircle());
				}
				balloons.clear();
				if (wave == 2)
				{
					try {
						quit();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
				}
				else
					changeWave();
			}
			else
			{
				Balloon b=new Balloon(pane,this);
				balloons.add(b);
			}
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

	public void changeWave()
	{
		time = 0;
		wave++;
		Pane pane = new Pane();
		pane.setBackground(new Background(bI));
		waveScene = new Scene(pane, 800, 800);

		Label status = new Label("Congratulations! Wave " + wave +  " completed!");
		status.setLayoutX(200);
		status.setLayoutY(60);
		status.setStyle("-fx-font: 24 arial;");

		Label currentScore = new Label(scoreLabel.getText());
		currentScore.setLayoutX(200);
		currentScore.setLayoutY(100);
		currentScore.setStyle("-fx-font: 24 arial;");

		Button play = new Button("Continue");
		play.setLayoutX(350);
		play.setLayoutY(400);
		play.setOnAction((e) ->
		{
			start();
		});

		Button store = new Button("Store");
		store.setLayoutX(350);
		store.setLayoutY(500);
		store.setOnAction(e -> {
			StoreManager storeManage = new StoreManager(stage, this);
		});

		pane.getChildren().addAll(status, currentScore, play, store);
		stage.setScene(waveScene);
	}
}