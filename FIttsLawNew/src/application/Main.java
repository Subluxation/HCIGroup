package application;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;



public class Main extends Application {
	private int numButtons1;
	private int numButtons2;
	private Pos[] positions;
	private double[] idArray;
	private int randNum;
	private int[] width;
	private int[] amp;
	private int i;
	private int j;
	private double[] time;
	private int total;
	private int hit;

	@Override
	public void start(Stage primaryStage) {
		try {
			i = 0;
			j = 0;
			total = 0;
			hit  = 0;
			primaryStage.setTitle("Fitts Law Test");
			BorderPane root = new BorderPane();

			Scene scene = new Scene(root,400,400);

			init(root, primaryStage);
			primaryStage.setScene(scene);
			primaryStage.show();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	public void init (BorderPane bP, Stage pStage){

		positions = new Pos[3];
		positions[0] = Pos.CENTER;
		positions[1] = Pos.CENTER_LEFT;
		positions[2] = Pos.CENTER_RIGHT;


		//Top of Pane
		Label label1 = new Label("Fitts Law Test");
		HBox hB2 = new HBox();
		label1.setFont(new Font("Ariel", 20));
		label1.setTextAlignment(TextAlignment.JUSTIFY);
		hB2.getChildren().add(label1);
		hB2.setAlignment(Pos.CENTER);
		bP.setTop(hB2);
		//Middle of Pane
		Label description = new Label();
		description.setText("This following program is set to test Fitt's Law. There will be a series of "
				+ "buttons that you wil be required to click as quickly as possible. You may adjust the number of buttons "
				+ "that will pop up by the box beside the 'Start' Button between 10 and 50. As soon as you press the start button, press the following"
				+ " buttons as quickly as possible. Enjoy!");
		description.setWrapText(true);
		HBox hB3 = new HBox();
		hB3.setAlignment(Pos.CENTER);
		hB3.getChildren().add(description);
		bP.setCenter(hB3);
		//Bottom of Pane
		Button start = new Button("Start");
		Spinner spin = new Spinner(10, 50, 10);
		HBox hB1 = new HBox();
		hB1.getChildren().add(spin);
		hB1.setAlignment(Pos.CENTER);
		hB1.getChildren().add(start);
		bP.setBottom(hB1);

		start.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				if(event.getSource() instanceof Button){
					if((((Button)event.getSource()).getText() == "Start")){
						numButtons1 = (Integer) spin.getValue();
						numButtons2 = numButtons1;
						amp = new int[numButtons1];
						width = new int[numButtons1];
						idArray = new double[numButtons2];
						time = new double[numButtons2];
						initTest(pStage);
					}
				}

			}

		});








	}
	public void initTest(Stage pStage){




		BorderPane afterStart = new BorderPane();

		Scene scene2 = new Scene(afterStart,800,600);

		buttonScene(afterStart, pStage, scene2);

		pStage.setScene(scene2);
		pStage.show();







	}
	public void buttonScene(BorderPane bP2, Stage stage, Scene scene){
		if(numButtons1 != 0){
			randNum = ThreadLocalRandom.current().nextInt(0, 2 + 1); //between 0 - 2
			double wid = (double)ThreadLocalRandom.current().nextInt(0, 200 + 1);
			double height = (double)ThreadLocalRandom.current().nextInt(0, 200 + 1);

			Button click = new Button("HERE");


			double start = System.nanoTime();


			width[i] = (int)wid;
			double minX = click.getBoundsInLocal().getMinX();
			double maxX = click.getBoundsInLocal().getMaxX();
			double xCenter = ( minX + maxX ) / 2;

			double minY = click.getBoundsInLocal().getMinY();
			double maxY = click.getBoundsInLocal().getMaxY();
			double yCenter = ( minY + maxY ) / 2;

			Point mouse =	MouseInfo.getPointerInfo().getLocation();
			double mouseX = mouse.getX();
			double mouseY = mouse.getY();

			double val = Math.sqrt(Math.pow((xCenter-mouseX),2) + Math.pow((yCenter-mouseY),2));
			amp[i] = (int) val;


			bP2.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					++total;
				}
			});




			click.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent event) {
					if(event.getSource() instanceof Button){
						if((((Button)event.getSource()).getText() == "HERE")){
							++hit;
							double finish = System.nanoTime();
							time[j] = (finish - start) / 1000000000;
							j++;
							initTest(stage);

						}
					}

				}

			});

			click.setPrefSize(wid, height);
			HBox hbox = new HBox();
			hbox.getChildren().add(click);
			hbox.setAlignment(positions[randNum]);

			switch(randNum){
			case 0: 
				bP2.setBottom(hbox);
				break;
			case 1:
				bP2.setTop(hbox);
				break;
			case 2:
				bP2.setCenter(hbox);
				break;
			}
			--numButtons1;
			i++;
		} 
		else{
			results(stage, bP2);
		}






	}

	//After finishing collecting data
	public void results(Stage stage, BorderPane conclude){


		//BorderPane last = new BorderPane();

		//	Scene sceneLast = new Scene(last,800,600);


		movementTime();

		NumberAxis xaxis = new NumberAxis();
		xaxis.setLabel("ID Value");
		NumberAxis yaxis = new NumberAxis();
		yaxis.setLabel("Time (sec)");

		final LineChart lc = new LineChart(xaxis,yaxis );
		lc.getXAxis().setAutoRanging(true);
		lc.getYAxis().setAutoRanging(true);
		lc.setLegendVisible(false);
		lc.setTitle("Fitt's Law");




		XYChart.Series<Number, Number> series = new XYChart.Series<Number,Number>();

		for(int i = 0; i < numButtons2; i++){
			//X-Axis is ID, Y-Axis is time
			series.getData().add( new XYChart.Data<Number,Number>(idArray[i], time[i]) );
			System.out.print(" ID Value: " + idArray[i] + "\n");
			System.out.print(" Time Value: " + time[i] + "\n");

		}

		lc.getData().add(series);


		conclude.setCenter(lc);

		Label hitlab = new Label();
		if(hit == total){
			hitlab.setText("Percentage Successful: 100%");
		}
		else{
			hitlab.setText("Percentage Successful: " + ((hit*100)/total)+ "%");
		}

		conclude.setTop(hitlab);



		//stage.setScene(sceneLast);
		//stage.show();



		//last.getChildren().add(lc);

	}
	public void movementTime(){
		for(int i = 0; i < numButtons2; i++){
			double id;
			if(width[i] == 0){
				id = 0.0;
			}
			else{
				id = Math.log((amp[i]/width[i]) + 1);
			}

			double mt = 50 + 150 * id;
			idArray[i] = id;
		}
	}

}
