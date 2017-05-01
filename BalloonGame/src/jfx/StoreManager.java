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
		Label credits = new Label();
		Image play_image = new Image(getClass().getResourceAsStream("play_icon2.png"), 25, 25, true, false);
		Button play = new Button("Continue", new ImageView(play_image));
		
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
		
		storeStage.setResizable(false);
		
		
		storeStage.setScene(storeScene);
		
		
		
		
		
	}
	
	
}
