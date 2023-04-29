import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import models.DataModel;
import models.NavigationModel;
import sprint1.Database;
import sprint2.Client;
import sprint2.Server;
import sprint2.ServerInterface;
import views.MainController;

import static org.mockito.Mockito.*;
/*when using Mockito use 4.8.1  as its compatible with ByteBuddy and previous versions aren't*/

@ExtendWith(ApplicationExtension.class)
public class Login_ViewTest
{
	private MainController m;
	private NavigationModel Nav;
	private DataModel D;
	//private AutoCloseable mock;
	
	@Start
	public void start(Stage stage) throws Exception 
	{
		
		//mock = MockitoAnnotations.openMocks(this);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/views/Login_View.fxml"));
		AnchorPane view = loader.load();
		Scene s = new Scene(view);
		stage.setScene(s);
		
		m = loader.getController();
		Database DB = new Database();
		Server mc = new Server(DB);
		Registry registry = LocateRegistry.createRegistry(3099); //acts as local "host"
		registry.rebind("Concord", mc); // renames server (remote obejct) for lookup
		
		ServerInterface proxy;
		proxy = (ServerInterface)registry.lookup("Concord"); //sets the proxy so that it limits access
		Client c = new Client(proxy);
		
		D = new DataModel(c);
		D.addUser("aj123", "howell22");
		//NavigationModel Nav = mock(NavigationModel.class);
		//new NavigationModel(c)
		Nav=mock(NavigationModel.class);
	
		m.setModel(Nav,D);
		
		stage.show();
		

	}
	
	@Test
	public void TestLogin(FxRobot robot )
	{
		Login(robot,"aj123","howell22");
	}

	@Test
	public void TestRegister(FxRobot robot)
	{
		Register(robot, "Thomas12","spiderman123");
	}

	
	private void Login(FxRobot robot, String name, String pass)
	{
		
		
		robot.clickOn("#UserName");
		robot.write(name);
		
		robot.clickOn("#password");
		robot.write(pass);
		
		
		robot.clickOn("#Login");
		
		verify(Nav).showMainPage(name, pass);
	}
	
	private void Register(FxRobot robot, String name, String pass)
	{
		robot.clickOn("#UserName");
		robot.write(name);
		
		robot.clickOn("#password");
		robot.write(pass);
		
		robot.clickOn("#register");
		
		verify(Nav).showNewGroup(name, pass);
	}

}
