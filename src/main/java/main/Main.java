package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{

	public Main()
	{
		
	}

	@Override
	// Our model will do all the functionality by calling this method
	// our controller will reference our model
	// we are transitioning scenes but need to have a hold of the 
	// the same stage
	public void start(Stage stage) throws Exception 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/views/Login_View.fxml"));
		AnchorPane view = loader.load();
		Scene s = new Scene(view);
		//LoginController cont = new 
		stage.setScene(s);
		
		stage.show();
		

	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
