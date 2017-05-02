package jfx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StoreManager {

	private Stage storeStage;
	private Scene storeScene;
	private GamePlayManager manager;
	private Pane pane;
	private BackgroundImage storeImage;
	
	
	public StoreManager(Stage stage, GamePlayManager manager){
		storeStage = stage;
		this.manager = manager;
		
		
		createStoreScreen();
	}
	
	public void createStoreScreen(){
		pane = new Pane();
		storeScene =  new Scene(pane,800,800);
		
		
		VBox box = new VBox();
		VBox box1 = new VBox();
		Label credits = new Label();
		Image play_image = new Image(getClass().getResourceAsStream("play_icon2.png"), 25, 25, true, false);
		Button play = new Button("Continue", new ImageView(play_image));
		
		Button bomb = new Button("Bomb (90 points): " + Integer.toString(manager.getBomb()));
		Button freeze = new Button("Freeze (50 points): " + Integer.toString(manager.getFreeze()));
		Button multiplier = new Button("Multiplier (80 points): " + Integer.toString(manager.getMult()));
		bomb.setLayoutX(100);
		bomb.setLayoutY(100);
		
		
		bomb.setOnAction((e)->
		{
			if ( manager.getScore() > 90){
				manager.setBomb();
				manager.reduceScore(90);
				credits.setText("Credits: " + manager.getScore());
				bomb.setText("Bomb (90 points): " + Integer.toString(manager.getBomb()));
			}
			
		});
		multiplier.setOnAction((e)->
		{
			if ( manager.getScore() > 80){
				manager.setMult();
				manager.reduceScore(80);
				credits.setText("Credits: " + manager.getScore());
				multiplier.setText("Multiplier (80 points): " + Integer.toString(manager.getMult()));
			}
			
		});
		freeze.setOnAction((e)->
		{
			if ( manager.getScore() > 50){
				manager.setFreeze();
				manager.reduceScore(50);
				credits.setText("Credits: " + manager.getScore());
				freeze.setText("Freeze (50 points): " + Integer.toString(manager.getFreeze()));
			}
			
		});
		
		
		
		box.setAlignment(Pos.TOP_RIGHT);
		box.setSpacing(5);
		box.setMinSize(800, 800);
		
		play.setStyle("-fx-font: 22 arial; -fx-base: #32cd32");
		play.setText("Continue");
		play.setOnAction((e)->
		{
			manager.start();
			
		});
		
		box.getChildren().add(play);
		box1.getChildren().addAll(bomb,freeze,multiplier);
		
		
		storeImage =new BackgroundImage(new Image(GamePlayManager.class.getResource("Store.png").toExternalForm()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(800,800,false,false,false,false));
		pane.setBackground(new Background(storeImage));
		
		
		
		
		if (manager.getScore() == 1)
		{
			credits.setText("Credit: " + manager.getScore());
		}
		else
		{
			credits.setText("Credits: " + manager.getScore());
		}
		credits.setStyle("-fx-font: 44 arial;");
		
		box.getChildren().add(credits);
		pane.getChildren().add(box);
		pane.getChildren().add(box1);
		
		storeStage.setResizable(false);
		
		
		storeStage.setScene(storeScene);
		
		
		
		
		
	}
	
	
}
