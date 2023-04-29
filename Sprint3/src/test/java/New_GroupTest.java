import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import models.DataModel;
import models.NavigationModel;
import sprint1.Database;
import sprint2.Client;
import sprint2.Server;
import sprint2.ServerInterface;
import views.MainController;

@ExtendWith(ApplicationExtension.class)
public class New_GroupTest
{
	private MainController m;
	private NavigationModel Nav;
	private DataModel D;
	//private AutoCloseable mock;
	
	@Start
	public void start(Stage stage) throws Exception 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(NavigationModel.class.getResource("/views/New_Group_View.fxml"));
		
		VBox view = loader.load();
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
		
		D = new DataModel(c);
		D.addUser("aj12", "howell22");
		D.UserName="aj12";

		Nav=mock(NavigationModel.class);
		
		m.setModel(Nav,D);
			
		stage.show();
			
			
		}
	
	@Test
	public void TestMainPage(FxRobot robot)
	{	
		robot.clickOn("#Main");
		verify(Nav).showMainPage("aj12", "howell22");
	}
	
	
}
