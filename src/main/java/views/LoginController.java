package views;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.LoginTransModel;

public class LoginController
{
	LoginTransModel model;
	Stage stage;
	

    @FXML
    private Button Login;

    @FXML
    private Button register;

	public LoginController() 
	{
		this.model = new LoginTransModel(stage);
	}
	
	@FXML
	void onClickLogin(ActionEvent event)
	{
		
		model.showNewGroup();
		System.out.println("Login Clicked");
	}
	
	
	@FXML
	void onClickRegister(ActionEvent event)
	{
		model.showMainPage();
		System.out.println("Register Clicked");
	}

}
