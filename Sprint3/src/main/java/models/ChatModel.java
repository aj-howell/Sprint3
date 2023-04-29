package models;



import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;



public class ChatModel {

	VBox v;
	DataModel DB;
	
	public ChatModel() 
	{
		// TODO Auto-generated constructor stub
		v = new VBox();
	}
	
	
	
	public void SendMessage(TextField actual, ScrollPane scroll)
	{
		
		
		if(actual.getText().equals(null))
		{
			System.out.println("No Message Found");
		}
		
		Label message = new Label();
		message.setText(actual.getText());

		v.getChildren().add(message);
		v.setAlignment(Pos.BOTTOM_RIGHT);
	
		scroll.setContent(v);
	
		
	}


}
