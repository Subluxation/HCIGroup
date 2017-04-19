package jfx;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Balloon 
{
	private Circle circle;
	private Pane pane;
	private GamePlayManager manager;
	private Timeline timeline;
	private int lives;
	
	public Balloon(Pane pane,GamePlayManager manager)
	{
		this.manager=manager;
		
		int radius = (int)(Math.random()*31)+20;
		int xPosition=(int)(Math.random()*(800-2*radius))+radius;
		
		circle=new Circle(radius,generateColor());
		circle.setCenterX(xPosition);
		circle.setCenterY(800-radius);
		circle.setOnMouseClicked((e)->
		{
			--lives;
			
			if(lives==0)
			{
				timeline.stop();
				pane.getChildren().remove(circle);
				manager.increaseScore();
			}
		});
		
		this.pane=pane;
		pane.getChildren().add(circle);
		
		timeline=new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		timeline.setOnFinished((e)->
		{
			pane.getChildren().remove(circle);
			try
			{
				manager.removeLife();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		});
		
		KeyValue kv=new KeyValue(circle.centerYProperty(),radius);
		KeyFrame kf=new KeyFrame(Duration.millis(Math.random()*3000+3000),kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
	
	private Color generateColor()
	{
		int i=(int)(Math.random()*5);
		
		switch(i)
		{
			case 0: 
				lives=1;
				return Color.AQUA;	
			case 1: 
				lives=2;
				return Color.RED;
			case 2: 
				lives=1;
				return Color.BLUE;
			case 3: 
				lives=2;
				return Color.YELLOW;
			case 4: 
				lives=1;
				return Color.GREEN;
		}
		
		lives=1;
		return Color.BLACK;
	}
}
