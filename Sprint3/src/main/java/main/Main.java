package main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.MainController;
import models.*;
import sprint1.Database;
import sprint2.Client;
import sprint2.Server;
import sprint2.ServerInterface;

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
		stage.setScene(s);
		
		MainController m = loader.getController();
		Database DB = new Database();
		Server mc = new Server(DB);
		Registry registry = LocateRegistry.createRegistry(3099); //acts as local "host"
		registry.rebind("Concord", mc); // renames server (remote obejct) for lookup
		
		ServerInterface proxy;
		proxy = (ServerInterface)registry.lookup("Concord"); //sets the proxy so that it limits access
		Client c = new Client(proxy);
		
		DataModel D = new DataModel(c);
		NavigationModel Nav = new NavigationModel(c);
		
		
		
		m.setModel(Nav,D);
		
		stage.show();
		

	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
