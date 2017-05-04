package jfx;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HighScoreManager 
{
	private Stage stage;
	private Scene mainScene;
	private Scene highScoreScene;
	private ArrayList<HighScore> scoreList;
	private TableView<HighScore> table;
	private VBox layout;
	private ArrayList<ActionListener> listeners;
	
	public HighScoreManager(Stage stage, Scene scene)
	{
		this.stage=stage;
		mainScene = scene;
		
		scoreList=new ArrayList<HighScore>();
		
		listeners=new ArrayList<ActionListener>();
		
		createHighScoreScene();
		populateArray();
		reRank();
		populateTable();
	}
	
	private void createHighScoreScene()
	{
		TableColumn rankColumn=new TableColumn("Rank");
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
		
		TableColumn nameColumn=new TableColumn("Username");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn scoreColumn=new TableColumn("Score");
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		
		table=new TableView<HighScore>();
		table.getColumns().addAll(rankColumn,nameColumn,scoreColumn);
		
		Button button=new Button("Back to Main Screen");
		button.setOnAction((e)->
		{
			quit();
		});
		
		layout=new VBox();
		layout.getChildren().addAll(button,table);
		
		highScoreScene=new Scene(layout);
		//Added
		layout.setMinSize(800, 800);
		stage.setScene(highScoreScene);
	}
	
	private void populateTable()
	{
		ObservableList<HighScore> data=FXCollections.observableArrayList(scoreList);
		table.setItems(data);
	}
	
	private void populateArray()
	{
		scoreList=new ArrayList<HighScore>();
		
		try
		{
			TextFileReader tfr=new TextFileReader("highScores.txt");
			ArrayList<ArrayList<String>> list=tfr.parseBy(':');
			
			for(ArrayList<String> aList:list)
			{
				String name=aList.get(0);
				int score=Integer.parseInt(aList.get(1));
				scoreList.add(new HighScore(name,score));
			}
			
			reRank();
		}
		catch(Exception e)
		{
			layout.getChildren().remove(table);
			Label label=new Label("Error loading high scores");
			layout.getChildren().add(label);
		}
	}
	
	private void quit()
	{
		writeBackScores();
		notifyListeners();
		//return to main menu
		stage.setScene(mainScene);
	}
	
	private void writeBackScores()
	{
		try
		{
			BufferedWriter writer=new BufferedWriter(new FileWriter("highScores.txt"));
			
			for(HighScore sc:scoreList)
			{
				writer.write(sc.getName()+":"+sc.getScore()+"\n");
			}
			
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void reRank()
	{
		scoreList.sort(new HighScoreComparator());
		
		for(int i=10;i<scoreList.size();++i)
		{
			scoreList.remove(i);
		}
		
		for(int i=0;i<scoreList.size();++i)
		{
			scoreList.get(i).setRank(i+1);
		}
	}
	
	public void start()
	{
		stage.setScene(highScoreScene);
	}
	
	public void addScore(String username,int score)
	{
		scoreList.add(new HighScore(username,score));
		reRank();
		populateTable();
	}
	
	public void addListener(ActionListener listener)
	{
		listeners.add(listener);
	}
	
	private void notifyListeners()
	{
		for(ActionListener a:listeners)
		{
			a.actionPerformed(new ActionEvent(this,-1,"High score manager completed"));
		}
	}
}