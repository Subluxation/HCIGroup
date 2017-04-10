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

public class StoreManager {

	private Stage storeStage;
	private Scene storeScene;
	private Scene mainScene;
	private Pane pane;
	private BackgroundImage storeImage;
	
	
	public StoreManager(Stage stage, Scene scene){
		storeStage = stage;
		mainScene = scene;
		
		
		createStoreScreen();
	}
	
	public void createStoreScreen(){
		pane = new Pane();
		storeScene =  new Scene(pane,800,800);
		
		
		VBox box = new VBox();
		Label credits = new Label();
		Button menu = new Button();
		
		box.setAlignment(Pos.TOP_RIGHT);
		box.setSpacing(5);
		box.setMinSize(mainScene.getWidth(), mainScene.getHeight());
		
	
		
		menu.setText("Main Menu");
		menu.setOnAction((e)->
		{
			storeStage.setScene(mainScene);
			
		});
		
		box.getChildren().add(menu);
		
		
		storeImage =new BackgroundImage(new Image(GamePlayManager.class.getResource("Store.png").toExternalForm()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(800,800,false,false,false,false));
		pane.setBackground(new Background(storeImage));
		
		
		
		
		credits.setText("Credits: XXX");
		credits.setStyle("-fx-font: 44 arial;");
		
		box.getChildren().add(credits);
		pane.getChildren().add(box);
		
		storeStage.setResizable(false);
		
		
		storeStage.setScene(storeScene);
		
		
		
		
		
	}
	
	
}
