package models;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import sprint2.*;

import views.MainController;

/*Anything function call that leads to a specific page needs that controller*/

public class NavigationModel 
{	
	Stage stage;
	Client C;
	public boolean online=true;

	public NavigationModel(Client C)
	{
		this.stage = new Stage();
		this.C=C;
	}
	
	public void showNewGroup(String UserName, String Password)
	{	
	
		
		C.insertUser(UserName, Password);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(NavigationModel.class.getResource("/views/New_Group_View.fxml"));
		try 
		{
			VBox view = loader.load();
			Scene s = new Scene(view);
			stage.setScene(s);
			
			MainController m = loader.getController();
			DataModel D = new DataModel(C);
			D.UserName=UserName;
	
			m.setModel(this,D);
			
			
			stage.show();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void showMainPage(String UserName, String Password)
	{	
		if(C.findUserID(UserName)>-1) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(NavigationModel.class.getResource("/views/ChatPage.fxml"));
	
		try 
		{
			AnchorPane view = loader.load();
	
			Scene s = new Scene(view);
			stage.setScene(s);
			
			MainController m = loader.getController();
			
		
			DataModel D = new DataModel(C);
			D.UserName=UserName;
		
		
			
			m.setModel(this,D);
			
			stage.show();
			
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	}
	
	public void showLogIn()
	{
		online=false;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/views/Login_View.fxml"));
		AnchorPane view;
		
		try{
			view = loader.load();
			Scene s = new Scene(view);
			stage.setScene(s);
			
			stage.show();
		} catch (IOException e)
		
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}
