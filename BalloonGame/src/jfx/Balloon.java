package jfx;
import java.io.File;
import java.io.IOException;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		//SOUND
				MediaPlayer mediaP;
				String mFile = "pop.m4a";     // For example

				Media sound1 = new Media(new File(mFile).toURI().toString());
				mediaP = new MediaPlayer(sound1);
				mediaP.setVolume(.5);
				
		int radius = (int)(Math.random()*31)+20;
		int xPosition=(int)(Math.random()*(800-2*radius))+radius;
		
		lives=(int)(Math.random()*3)+1;
		
		circle=new Circle(radius,generateColor(lives));
		circle.setCenterX(xPosition);
		circle.setCenterY(800-radius);
		circle.setOnMouseClicked((e)->
		{
			--lives;
			circle.setFill(generateColor(lives));
			
			if(lives==0)
			{
				if(timeline.getStatus()!=Status.STOPPED)
				{
					timeline.stop();
				}
				
				pane.getChildren().remove(circle);
				mediaP.play();
				manager.increaseScore();
				manager.removeBalloon(this);
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
	
	public Timeline getTimeLine()
	{
		return timeline;
	}
	
	public Circle getCircle()
	{
		return circle;
	}
	
	private Color generateColor(int lives)
	{
		switch(lives)
		{
			case 1: 
				return Color.AQUA;	
			case 2:
				return Color.GREEN;
			case 3: 
				return Color.RED;
		}
		return Color.BLACK;
	}
}
