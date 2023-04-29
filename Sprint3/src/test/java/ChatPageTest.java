import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.TextField;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
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
public class ChatPageTest
{
	private MainController m;
	private NavigationModel Nav;
	private DataModel D;
	
	@Start
	public void start(Stage stage) throws Exception 
	{
		
		//mock = MockitoAnnotations.openMocks(this);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(NavigationModel.class.getResource("/views/ChatPage.fxml"));
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
		
		D = new DataModel(c);
		D.addUser("aj123", "howell22");
		D.UserName="aj123";
		//NavigationModel Nav = mock(NavigationModel.class);
	
		Nav=new NavigationModel(c);

	
		m.setModel(Nav,D);
		
		stage.show();
	}
	
	
	
	//@Test
	void TestChat(FxRobot robot)
	{
		createGroup(robot, "CSC360");
		createChannel(robot, "Homework");
		sendMessage(robot,"Hello");
		
		Assertions.assertThat(robot.lookup("#messages")
				.queryAs(VBox.class)
				.getChildren()
				.get(0)
				.toString()
				.equals("Hello"));
				
	}
	
	
	@Test
	void TestSwitchChannel(FxRobot robot)
	{
		createGroup(robot, "CSC360");
		createChannel(robot, "Homework");
		createChannel(robot,"Test");
		sendMessage(robot,"Hello");
		
		
		robot.clickOn("#Homework");
		
		
		Assertions.assertThat(robot.lookup("#messages")
				.queryAs(VBox.class)
				.getChildren()
				.stream()
				.filter(c->c.toString().equals("Hello"))
				.findFirst())
				.isNotPresent();
		
		Assertions.assertThat(robot.lookup("Channel: Homework")
				.queryAs(Label.class)
				.getText()
				.contains("Homework"));
	}
	
	
	//@Test
	void TestSwitchGroup(FxRobot robot)
	{
		createGroup(robot, "CSC360");
		createGroup(robot, "CSC362");
		createChannel(robot,"Test");
		sendMessage(robot,"Hello");
		
		
		robot.clickOn("#1");
		robot.clickOn("#0");
		
		
		Assertions.assertThat(robot.lookup("#messages")
				.queryAs(VBox.class)
				.getChildren()
				.stream()
				.filter(c->c.toString().equals("Hello"))
				.findFirst())
				.isNotPresent();
		
		Assertions.assertThat(robot.lookup("#0")
				.queryAs(MenuButton.class)
				.getText()
				.equals("CSC360"));
				
	}
	
	
	//@Test
	void TestGroup(FxRobot robot)
	{
		createGroup(robot, "CSC360");
		

				
	}
	
	//@Test
	void TestLogout(FxRobot robot)
	{
		robot.clickOn("#logout");
		Assertions.assertThat(Nav.online).isFalse();		
	}
	
	private void createChannel(FxRobot robot, String cName)
	{
		robot.clickOn("#text");
		robot.write(cName);	
		robot.clickOn("#createChannel");
		
	
	}
	
	
	private void createGroup(FxRobot robot, String gName)
	{
		robot.clickOn("#text");
		robot.write(gName);	
		
		robot.clickOn("#createGroup");
		
		
	}
	
	
	private void sendMessage(FxRobot robot, String text)
	{
		robot.clickOn("#text");
		robot.write(text);
		robot.clickOn("#send");
	}
	
}
