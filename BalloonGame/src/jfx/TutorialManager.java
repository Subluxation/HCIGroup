package jfx;

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
import javafx.stage.Stage;

public class TutorialManager {

	private Stage tutStage;
	private Scene tutScene;
	private GamePlayManager manager;
	private Pane pane;
	private BackgroundImage tutImage;
	private Scene mainScene;
	
	
	public TutorialManager(Stage stage, Scene scene){
		tutStage = stage;
		mainScene = scene;
		
		
		createTutScreen();
	}
	
	public void createTutScreen(){
		pane = new Pane();
		tutScene =  new Scene(pane,800,700);
		
		
		VBox box = new VBox();
		Label credits = new Label();
		Button play = new Button();
		
		box.setAlignment(Pos.BASELINE_RIGHT);
		box.setSpacing(5);
		box.setMinSize(700, 700);
		
	
		
		play.setText("Main Menu");
		play.setOnAction((e)->
		{
			tutStage.setScene(mainScene);
			
		});
		
		box.getChildren().add(play);
		
		
		tutImage =new BackgroundImage(new Image(GamePlayManager.class.getResource("HowToPlay.png").toExternalForm()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(800,700,false,false,false,false));
		pane.setBackground(new Background(tutImage));
		
		
		
		
		
		
		
		pane.getChildren().add(box);
		
		tutStage.setResizable(false);
		
		
		tutStage.setScene(tutScene);
		
		
		
		
		
	}
	
	
}
