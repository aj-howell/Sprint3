package views;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import models.DataModel;
import models.NavigationModel;
import sprint1.Channel;
import sprint2.Client;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainController 
{

	@FXML
    private Button Login;

    @FXML
    private Button register;
    
    @FXML
    private TextField textMessage;
    
    @FXML
    private ScrollPane scrollMessage;
    
    @FXML
    private ListView<Text> ChannelList;
    
    @FXML
    private Label channelName;
    
    @FXML
    private Button groupButton;

    @FXML
    private MenuButton MenuButton;
    
    @FXML
    private TextField password;

    @FXML
    private TextField UserName;
    
    @FXML
    private Button MainPageButton;

    @FXML
    private Label WelcomeSign;
   
    private DataModel D;
    private VBox v;
	private NavigationModel model;
	

	public MainController() 
	{
	
		
	}


	public void setModel(NavigationModel model, DataModel D)
	{
		this.model = model;
		this.D=D;
		this.v=new VBox();
		v.setId("messages");
		
	}

	public String getChannelName() {
		return channelName.getText();
	}


	@FXML
	public void onClickLogin(ActionEvent event)
	{
		model.showMainPage(UserName.getText(), password.getText());
		System.out.println("Login Clicked");
	}
	@FXML
	public void onClickRegister(ActionEvent event)
	{
		model.showNewGroup(UserName.getText(), password.getText());
		System.out.println("Register Clicked");
	}
	
	@FXML
	public void onClickLogOut()
	{
		model.showLogIn();
	}
	
	@FXML
	public void mainPage(ActionEvent Event)
	{
		int id=D.findUser(D.UserName);
		model.showMainPage(D.UserName, D.getUser(id).Password);
	}
	
	
    @FXML
    public void addGroup(ActionEvent event)
    {
    	int id = D.findUser(D.UserName);
    	int groupID=D.addGroup(textMessage.getText(), id);
    	D.getGroup(groupID).addAdmin(id);
    	MenuItem group = new MenuItem(textMessage.getText());
    	group.setId(String.valueOf(groupID));
    	group.setOnAction(new EventHandler<ActionEvent>() {
        	
        	@Override
        	public void handle(ActionEvent event)
        	{
        		textMessage.clear();
            	//when clicked change the name to selected menu item
        		MenuButton.setText(group.getText());
        		MenuButton.setId(group.getId());
        		System.out.println("GroupName: "+group.getText()+"id: "+group.getId());
        		
        		ChannelList.getItems().clear();
        		v.getChildren().clear();
        		D.getChannels(D.UserName, Integer.valueOf(MenuButton.getId())).forEach
        		(i->{
        			Text t= new Text(D.getChannel(i).getTopic());
        			t.setId(D.getChannel(i).getTopic());
        			ChannelList.getItems().add(t);
        		});

        	}});
    	
    		MenuButton.getItems().add(group);
    		v.getChildren().clear();
    		MenuButton.setId(group.getId());
    
    		//change to new group when added
    		MenuButton.setText(textMessage.getText());
    		ChannelList.getItems().clear(); //once a new group is added then clear Channel list
    		textMessage.clear();
   }
    
    @FXML
    public void onClickRegisterChannel(ActionEvent event)
    {
    	int cID=D.addChannel(textMessage.getText(), Integer.valueOf(MenuButton.getId()), D.UserName);
    	textMessage.clear();
    	Channel c = D.getChannel(cID);
    	channelName.setId(String.valueOf(cID)); //only way to recognize what channel you are currently in
    	channelName.setText("Channel: "+c.getTopic());
    	Text t= new Text(c.getTopic());
    	t.setId(c.getTopic());
    	ChannelList.getItems().add(t);
    	v.getChildren().clear();
    
    	ChannelList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	
            	textMessage.clear();
            	if(ChannelList.getSelectionModel().getSelectedItem().equals(null))
            	{
            		System.out.println("Cannot Change because it is already null");
            	}
            	 channelName.setText("Channel: "+ChannelList.getSelectionModel().getSelectedItem().getText());
            	 System.out.println("the channel switched to: "+channelName.getId());
            	 v.getChildren().clear();
                System.out.println("clicked on " + ChannelList.getSelectionModel().getSelectedItem());
                channelName.setId(String.valueOf(cID));
                int selected = ChannelList.getSelectionModel().getSelectedIndex();

                D.getChannel(selected).messageIDs.forEach
                (m->
                {
                	Label message = new Label();
            		message.setText(D.getMessage(m).getContent());
            		System.out.println("Message: "+D.getMessage(m).getContent());
            		v.getChildren().add(message);
                });
               
            }
        });
    	System.out.println("Channel Created "+ c.getTopic());
    	textMessage.clear();
    }

    @FXML
   public void onClickSend(ActionEvent event)
    {	
    	if(textMessage.getText().equals(null))
    		{
    			System.out.println("No Message Found");
    		}
    		
    	int c = Integer.valueOf(channelName.getId());

    	int g_id = Integer.valueOf(MenuButton.getId());
    	
    	int SenderID=D.findUser(D.UserName);
    	    	
    	D.SendMessage(c,textMessage.getText(),SenderID,g_id);
    	  	
    	Label message = new Label();
    	message.setText(textMessage.getText());
    		

    	v.getChildren().add(message);
    	v.setAlignment(Pos.BASELINE_RIGHT);
    	
    	System.out.println(v.alignmentProperty().get());
    	
    	scrollMessage.setContent(v);
    	textMessage.clear();
    }

}
