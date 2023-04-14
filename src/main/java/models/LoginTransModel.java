package models;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginTransModel
{

	Stage stage;
	

	public LoginTransModel(Stage stage) 
	{
		this.stage = new Stage();
	}
	
	public void showNewGroup()
	{
		
		try 
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(LoginTransModel.class.getResource("../views/New_Group_View.fxml"));
			VBox view = loader.load();
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.show();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void showMainPage()
	{
		try 
		{
			FXMLLoader l = new FXMLLoader();
			l.setLocation(LoginTransModel.class.getResource("../views/Chat_Page_View.fxml"));
			
			BorderPane view = (BorderPane)l.load();
			Scene p = new Scene(view);
			
			stage.setScene(p);
			stage.show();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
