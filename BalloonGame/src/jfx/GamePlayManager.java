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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GamePlayManager
{
	private final int PERFECT = 100;
	private final int WAVE_COMPLETED = 50;
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
	private boolean perfect = true;
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
		this.waves = new int[]{10, 20, 30}; //increase numbers for longer game

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
		//SOUND
		MediaPlayer mediaPlayer;
		String musicFile = "MainMenu.mp3";     // For example

		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);



		pane = new Pane();
		VBox box = new VBox();
		box.setAlignment(Pos.BASELINE_CENTER);
		
		box.setMinSize(pane.getWidth(), pane.getHeight());
		Label name = new Label("Please Enter Username: ");
		TextField input = new TextField();
		Button enter = new Button("Enter");
		box.getChildren().add(name);
		box.getChildren().add(input);
		box.getChildren().add(enter);
		
		pane.getChildren().add(box);
		gameScene=new Scene(pane,800,800);
		stage.setScene(gameScene);
		enter.setDefaultButton(true);
		enter.setOnAction((e)->
		{
			username = input.getText();
			start();
		});

	}

	private void createGameScene()
	{
		pane=new Pane();


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

		bombLabel=new Label("Bombs 'Q' : " + bombs);
		bombLabel.setLayoutX(680);
		bombLabel.setLayoutY(90);
		bombLabel.setStyle("-fx-font: 14 arial;");

		freezeLabel=new Label("Freezes 'W' : " + freezes);
		freezeLabel.setLayoutX(680);
		freezeLabel.setLayoutY(120);
		freezeLabel.setStyle("-fx-font: 14 arial;");

		numMultLabel=new Label("Multipliers 'E' : " + mult);
		numMultLabel.setLayoutX(680);
		numMultLabel.setLayoutY(150);
		numMultLabel.setStyle("-fx-font: 14 arial;");

		multLabel=new Label("Multiplier Timer: " + time4Mult);
		multLabel.setLayoutX(680);
		multLabel.setLayoutY(180);
		multLabel.setStyle("-fx-font: 14 arial;");

		pane.getChildren().addAll(scoreLabel,livesLabel,bombLabel,freezeLabel, numMultLabel, multLabel);
	}

	private void bombEvent()
	{
		MediaPlayer mediaP;
		String mFile = "pop.m4a";     // For example

		Media sound1 = new Media(new File(mFile).toURI().toString());
		mediaP = new MediaPlayer(sound1);
		mediaP.setVolume(.75);
		if(bombs>0)
		{
			for(Balloon b:balloons)
			{
				b.getTimeLine().stop();
				pane.getChildren().remove(b.getCircle());
				mediaP.play();
				increaseScore();

			}

			balloons.clear();
			--bombs;
			bombLabel.setText("Bombs 'Q' : "+Integer.toString(bombs));
		}
	}

	private void multiplierEvent() throws InterruptedException
	{
		if(mult > 0){
			time4Mult = 10;
			--mult;
			numMultLabel.setText("Multipliers 'E' : " + mult);
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
						Thread.sleep(3000);
						Platform.runLater(new Runnable() {
							public void run(){

								for(Balloon b:balloons)
								{
									b.getTimeLine().play();
								}

								--freezes;
								freezeLabel.setText("Freezes 'W' : "+Integer.toString(freezes));
							}
						});
						Thread.sleep(3000);

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
	//	timeline.stop();
		//add User to HighScore list


		HighScoreManager hsm = new HighScoreManager(stage, mainScene);
		hsm.addScore(username, score);

		//Once lives have depleted, send to Game Over screen with option for store, main menu, etc.

		//stage.setScene(mainScene);
	}
	public void update(){
		freezeLabel.setText("Freezes 'W' : "+Integer.toString(freezes));
		bombLabel.setText("Bombs 'Q' : "+Integer.toString(bombs));
		numMultLabel.setText("Multipliers 'E' : "+Integer.toString(mult));
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
					finishGame();
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
		perfect = false;
		--lives;
		livesLabel.setText("Lives: "+Integer.toString(lives));

		if(lives==0)
		{
			gameOver();
		}
	}

	public int getScore()
	{
		return score;
	}
	public void reduceScore(int i){
		score = score - i;
	}
	public int getBomb(){
		return bombs;
	}
	public int getFreeze(){
		return freezes;
	}
	public int getMult(){
		return mult;
	}
	public void setBomb(){
		bombs = bombs + 1;
	}
	public void setMult(){
		mult = mult + 1;
	}
	public void setFreeze(){
		freezes = freezes + 1;
	}

	public void changeWave()
	{
		time = 0;
		wave++;
		Pane pane = new Pane();
		pane.setBackground(new Background(bI));

		waveScene = new Scene(pane, 800, 800);

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(100);
		box.setMinSize(pane.getWidth(), pane.getHeight());

		Label status;
		if (perfect == false)
		{
			score += 50;
			status = new Label("Congratulations! Wave " + wave +  " completed!\n"
					+ "Completed Wave: +50\n"
					+ "Score: " + score);
		}
		else
		{
			score += 150;
			status = new Label("Congratulations! Wave " + wave +  " completed!\n"
					+ "Completed Wave: +50\n"
					+ "No Lives Lost: +100\n"
					+ "Score: " + score);
		}
		scoreLabel.setText("Score: "+Integer.toString(score));
		status.setBackground(new Background(new BackgroundFill(
				Paint.valueOf("LightSkyBlue"), new CornerRadii(status.getWidth() + 10), new Insets(status.getWidth()-10))));
		status.setStyle("-fx-font: 24 arial;");

		Image play_image = new Image(getClass().getResourceAsStream("play_icon2.png"), 25, 25, true, false);
		Button play = new Button("Continue", new ImageView(play_image));
		play.setStyle("-fx-font: 22 arial; -fx-base: #32cd32");
		play.setOnAction((e) ->
		{
			start();
		});

		Image store_image = new Image(getClass().getResourceAsStream("store_icon.png"), 25, 25, true, false);
		Button store = new Button("Store", new ImageView(store_image));
		store.setStyle("-fx-font: 22 arial; -fx-base: #ffd700");
		store.setOnAction(e -> {
			StoreManager storeManage = new StoreManager(stage, this);
		});

		box.getChildren().addAll(status, play, store);
		pane.getChildren().add(box);
		stage.setScene(waveScene);
	}
	
	public void gameOver()
	{
		timeline.stop();
		Pane pane = new Pane();
		pane.setBackground(new Background(bI));

		waveScene = new Scene(pane, 800, 800);

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(100);
		box.setMinSize(pane.getWidth(), pane.getHeight());

		Label status;
		status = new Label("Game Over! You lost on wave " + wave + "!\n"
					+ "Score: " + score);
		scoreLabel.setText("Score: "+Integer.toString(score));
		status.setBackground(new Background(new BackgroundFill(
				Paint.valueOf("LightSkyBlue"), new CornerRadii(status.getWidth() + 10), new Insets(status.getWidth()-10))));
		status.setStyle("-fx-font: 24 arial;");

		Button play = new Button("Finish");
		play.setStyle("-fx-font: 22 arial; -fx-base: #32cd32");
		play.setOnAction((e) ->
		{
			try {
				quit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		box.getChildren().addAll(status, play);
		pane.getChildren().add(box);
		stage.setScene(waveScene);
	}

	public void finishGame()
	{
		Pane pane = new Pane();
		pane.setBackground(new Background(bI));

		waveScene = new Scene(pane, 800, 800);

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(100);
		box.setMinSize(pane.getWidth(), pane.getHeight());

		Label status;
		if (perfect == false)
		{
			score += 50;
			status = new Label("Congratulations! You beat the game!\n"
					+ "Completed Wave: +50\n"
					+ "Score: " + score);
		}
		else
		{
			score += 150;
			status = new Label("Congratulations! You beat the game!\n"
					+ "Completed Wave: +50\n"
					+ "No Lives Lost: +100\n"
					+ "Score: " + score);
		}
		scoreLabel.setText("Score: "+Integer.toString(score));
		status.setBackground(new Background(new BackgroundFill(
				Paint.valueOf("LightSkyBlue"), new CornerRadii(status.getWidth() + 10), new Insets(status.getWidth()-10))));
		status.setStyle("-fx-font: 24 arial;");

		Button play = new Button("Finish");
		play.setStyle("-fx-font: 22 arial; -fx-base: #32cd32");
		play.setOnAction((e) ->
		{
			try {
				quit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		box.getChildren().addAll(status, play);
		pane.getChildren().add(box);
		stage.setScene(waveScene);
	}
}