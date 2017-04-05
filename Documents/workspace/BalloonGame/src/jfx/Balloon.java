package jfx;
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
	
	public Balloon(Pane pane,GamePlayManager manager)
	{
		this.manager=manager;
		
		int radius = (int)(Math.random()*31)+20;
		int xPosition=(int)(Math.random()*(800-2*radius))+radius;
		
		circle=new Circle(radius,generateColor());
		circle.setCenterX(xPosition);
		circle.setCenterY(800-radius);
		
		this.pane=pane;
		pane.getChildren().add(circle);
		
		timeline=new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);
		timeline.setOnFinished((e)->
		{
			hide();
			manager.removeLife();
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
			case 0: return Color.AQUA;	
			case 1: return Color.RED;
			case 2: return Color.BLUE;
			case 3: return Color.YELLOW;
			case 4: return Color.GREEN;
		}
		
		return Color.BLACK;
	}
	
	public void hide()
	{
		pane.getChildren().remove(circle);
	}
	
	public boolean wasHit(double x, double y)
	{
		double xCenter=circle.getCenterX();
		double yCenter=circle.getCenterY();
		double xDiff=xCenter-x;
		double yDiff=yCenter-y;
		double distance=Math.sqrt(xDiff*xDiff+yDiff*yDiff);
		
		if(distance<=circle.getRadius())
		{
			timeline.stop();
			return true;
		}
		else
		{
			return false;
		}
	}
}
