package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.IllegalCharsetNameException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.sun.prism.shader.FillCircle_Color_AlphaTest_Loader;

import javafx.scene.control.Label;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
public class InterfaceController implements Initializable {
	@FXML
	private AnchorPane left_holld;
	@FXML
	private Button btnexplore;
	@FXML
    private Button btnprofile;

    @FXML
    private Button btnservices;

    @FXML
    private Button btncart;

    @FXML
    private Button btncontact;
	@FXML
    private ScrollPane scrollInterface;

    @FXML
    private Button btnlogout;
    @FXML
    private ComboBox<String> combofilter;
    ObservableList<String> comboList= FXCollections.observableArrayList("Hotel","Motel","Auberge","Bangalow","Maison d'hôte","Appartement","All");


    @FXML
    private DatePicker datepickerfilter;
    @FXML
    private TextField searchfilter;

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        combofilter.setItems(comboList);
    }
	
	// Event Listener on Button[#btnexplore].onAction
	@FXML
	public void btn_explore(ActionEvent event) throws FileNotFoundException, URISyntaxException, SQLException {
		String rqtString="select * from Voyage;";
		makeeverything(rqtString);
	}
	void makeeverything(String rqt) throws FileNotFoundException, URISyntaxException, SQLException {
		AdminPanelController adm = new AdminPanelController();
		ObservableList<Travel> travels = adm.getTravelsList();
		int size = travels.size();
		Pane[][] cellAnchorPane = new Pane[size][size];	    
	    ImageView[][] btn = new ImageView[size][size];
	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(25);
	    gridPane.setVgap(25);
	    gridPane.setStyle("-fx-background-color : #ffffff");
	    Connection connection = DBConnection.getConnection();
		String query = rqt;
		Statement statement;
		ResultSet resultSet;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			int i=0;
			while (resultSet.next()) {
				int col = i%4;
				int lig = i/4;
				Label locatLabel = new Label(resultSet.getString("Country"));
		    	Label priceLabel = new Label("À partir de "+ resultSet.getInt("price"));
		    	priceLabel.setFont(new Font("Consolas", 15));
		    	locatLabel.setFont(new Font("Consolas", 18));
		    	Button cellButton = new Button("More");
		    	cellButton.setId("btn"+Integer.toString(i));
		    	cellButton.setStyle("-fx-background-color: #0056FF; -fx-background-radius: 20 20 20 20; -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.4) , 10,0.5,0,0 );  -fx-text-fill: white;");
		    	cellButton.getStylesheets().add("application/application.css");
		    	String nn=resultSet.getString("Country");
		    	String url=resultSet.getString("imgtravelURL");
		    	String desc=resultSet.getString("description");
		    	String price=resultSet.getString("price");
		    	String idvString =resultSet.getString("id_v");
		    	cellButton.setOnAction(new EventHandler<ActionEvent>()
		        {            
		            @Override
		            public void handle(ActionEvent event)
		            {
		                System.out.println("im button and my id is "+cellButton.getId()); 
		                try {
							Fill (url,nn,desc,price,idvString);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        });
		    	btn[col][lig] = new ImageView();
			    cellAnchorPane[col][lig] = new AnchorPane();
			    cellAnchorPane[col][lig].setMinHeight(320);      
			    cellAnchorPane[col][lig].setStyle("-fx-border-color: black;-fx-background-color : #ffffff;-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 10, 0.5, 0.0, 0.0);");
			    //InputStream stream = new FileInputStream(resultSet.getString("imgtravelURL"));
			    Image image = new Image(resultSet.getString("imgtravelURL"),299, 200, false, true,true);
			    btn[col][lig].setImage(image);
 			    cellAnchorPane[col][lig].getChildren().add(btn[col][lig]);
			    cellAnchorPane[col][lig].getChildren().add(priceLabel);
			    cellAnchorPane[col][lig].getChildren().add(locatLabel);
			    cellAnchorPane[col][lig].getChildren().add(cellButton);
			    cellAnchorPane[col][lig].setMinWidth(299);
			    AnchorPane.setLeftAnchor(btn[col][lig],(double) 0);
			    AnchorPane.setTopAnchor(btn[col][lig], (double) 0);
			    AnchorPane.setRightAnchor(btn[col][lig], (double) 0);
			    AnchorPane.setLeftAnchor(priceLabel,(double) 20);
			    AnchorPane.setBottomAnchor(priceLabel, (double) 24);
			    AnchorPane.setLeftAnchor(locatLabel,(double) 20);
			    AnchorPane.setBottomAnchor(locatLabel, (double) 70);
			    AnchorPane.setRightAnchor(cellButton,(double) 20);
			    AnchorPane.setBottomAnchor(cellButton, (double) 24);
			    gridPane.add(cellAnchorPane[col][lig], col, lig);
			    i++;
			}
			AnchorPane gridAnchorPane = new AnchorPane();
			gridAnchorPane.getChildren().add(gridPane);
			scrollInterface.setContent(gridAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();		}	
	    
	}
	
	public void Fill(String url,String name,String desc,String price,String idv) throws FileNotFoundException {
		AnchorPane parentAnchorPane = new AnchorPane();
		AnchorPane descAnchorPane = new AnchorPane();
		//InputStream stream = new FileInputStream(url);
	    Image image = new Image(url,600, 600, false, true);
	    ImageView imageView = new ImageView();
	    imageView.setImage(image);
	    AnchorPane.setTopAnchor(imageView, (double) 0);
	    AnchorPane.setLeftAnchor(imageView, (double) 0);
	    AnchorPane.setRightAnchor(imageView, (double) 0);
	    parentAnchorPane.getChildren().add(imageView);
	    Label namecity = new Label(name);
    	namecity.setFont(new Font("Consolas", 24)); 
    	AnchorPane.setLeftAnchor(namecity, 624.0);
	    AnchorPane.setTopAnchor(namecity, 24.0);
	    parentAnchorPane.getChildren().add(namecity);
	    //description
	    descAnchorPane.setMinSize(400, 100);
	    Text description = new Text();
	    description.setText(desc);
	    description.minHeight(400);
	    description.maxWidth(100);
	    description.setWrappingWidth(300);
	    description.setFont(new Font(24));
	    Label descrLabel = new Label("Description : ");
	    Label priceLabel = new Label(price);
	    priceLabel.setFont(new Font(30));
	    descrLabel.setFont(new Font(30));
	    AnchorPane.setLeftAnchor(descrLabel, 24.0);
	    AnchorPane.setTopAnchor(descrLabel, 24.0);
	    AnchorPane.setRightAnchor(priceLabel, 24.0);
	    AnchorPane.setBottomAnchor(priceLabel, 24.0);
	    AnchorPane.setRightAnchor(description, 24.0);
	    AnchorPane.setTopAnchor(description, 64.0);
	    AnchorPane.setLeftAnchor(description, 24.0);
	    AnchorPane.setBottomAnchor(description, 24.0);
	    descAnchorPane.getChildren().add(descrLabel);
	    descAnchorPane.getChildren().add(priceLabel);
	    descAnchorPane.getChildren().add(description);
	    AnchorPane.setRightAnchor(descAnchorPane, 24.0);
	    AnchorPane.setTopAnchor(descAnchorPane, 64.0);
	    AnchorPane.setLeftAnchor(descAnchorPane, 624.0);
	    AnchorPane.setBottomAnchor(descAnchorPane, 64.0);
	    parentAnchorPane.setStyle("-fx-background-color:#FFFFFF;");
	    descAnchorPane.setStyle("-fx-background-color:#FFFFFF; -fx-background-radius: 20 20 20 20; -fx-effect: dropshadow( gaussian , rgba(0,86,255,0.7) , 10,0,0,4 );");
	    Button cancelButton = new Button("Cancel");
	    Button bookedButton = new Button("Book");
	    String ssString = "-fx-text-fill: white;-fx-background-color: #0056FF; -fx-background-radius: 20 20 20 20; -fx-effect: dropshadow( gaussian , rgba(0,86,255,0.7) , 10,0,0,4 );";
	    cancelButton.setStyle(ssString);
	    bookedButton.setStyle(ssString);
	    cancelButton.setFont(new Font(16));
	    bookedButton.setFont(new Font(16));
	    cancelButton.setOnAction(new EventHandler<ActionEvent>()
		        {            
		            @Override
		            public void handle(ActionEvent event)
		            {
		            	Stage stage = (Stage) cancelButton.getScene().getWindow(); 
		            	stage.close(); 
		            }
		        });
	    int idcurr = Register_Login.currentId;
	    bookedButton.setOnAction(new EventHandler<ActionEvent>()
		        {            
		            @Override
		            public void handle(ActionEvent event)
		            {
		                 String registerUserQuery = "insert into booked(`id_p`, `id_v`, `price`) values (?,?,?)";
		                 try {
							PreparedStatement ps = DBConnection.getConnection().prepareStatement(registerUserQuery);
							ps.setString(1, Integer.toString(idcurr));
		                    ps.setString(2, idv);
		                    ps.setString(3, price);
		                    if(ps.executeUpdate() != 0){
				            	 JOptionPane.showMessageDialog(null, "You added this travel to the Cart");
				            	 Stage stage = (Stage) bookedButton.getScene().getWindow(); 
					             stage.close();
							 }else{
							     JOptionPane.showMessageDialog(null, "Error: Check Your Information");
							 }
		                } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
		            }
		        });
	    AnchorPane.setRightAnchor(cancelButton, 100.0);
	    AnchorPane.setBottomAnchor(cancelButton, 16.0);
	    AnchorPane.setRightAnchor(bookedButton, 24.0);
	    AnchorPane.setBottomAnchor(bookedButton, 16.0);
	    parentAnchorPane.getChildren().add(cancelButton);
	    parentAnchorPane.getChildren().add(bookedButton);
	    parentAnchorPane.getChildren().add(descAnchorPane);
		Stage stage = new Stage();
		Scene scene = new Scene(parentAnchorPane,1000,600);
		stage.setTitle("Travel");
		stage.setScene(scene);
		scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}
	@FXML
	public void search(ActionEvent event) throws FileNotFoundException, URISyntaxException, SQLException {
		String tempString = searchfilter.getText();
		String queryString = "select * from Voyage where (CityD='"+tempString+"' or Country='"+tempString+"')";
		makeeverything(queryString);
	}
	@FXML
    void searchdatepicker(ActionEvent event) throws FileNotFoundException, URISyntaxException, SQLException {
		String tempString = datepickerfilter.getValue().toString();
		String queryString = "select * from Voyage where DateI='"+tempString+"'";
		makeeverything(queryString);
    }
    
    @FXML
    void searchcombobox(ActionEvent event) throws FileNotFoundException, URISyntaxException, SQLException {
    	String tempString = combofilter.getValue().toString();
		String queryString = "select * from Voyage where logement='"+tempString+"'";
		makeeverything(queryString);
    }
	
	@FXML
    void btn_cart(ActionEvent event) {
		try{
			ScrollPane sp= FXMLLoader.load(getClass().getResource("cart.fxml"));
			scrollInterface.setContent(sp);
        }catch(IOException ex){
        }
    }

    @FXML
    void btn_contact(ActionEvent event) {
    	try{
			ScrollPane sp= FXMLLoader.load(getClass().getResource("contact.fxml"));
			scrollInterface.setContent(sp);
        }catch(IOException ex){
        	ex.printStackTrace();
        }
    }

    @FXML
    void btn_profile(ActionEvent event) throws IOException {
    	if(Register_Login.currentId==0) {
    		Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
    	    Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("ADV");
            stage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
    	}else {
    	
    	try{
			ScrollPane sp= FXMLLoader.load(getClass().getResource("person.fxml"));
			scrollInterface.setContent(sp);
        }catch(IOException ex){
        	JOptionPane.showMessageDialog(null, ex);
        }
    	}

    }
    
    @FXML
    void btn_services(ActionEvent event) {
    	try{
			ScrollPane sp= FXMLLoader.load(getClass().getResource("services.fxml"));
			scrollInterface.setContent(sp);
        }catch(IOException ex){
        	ex.printStackTrace();
        }
    	
    }
    @FXML
    void btnlogoutIN(ActionEvent event) throws FileNotFoundException, URISyntaxException, SQLException {
		btnlogout.setVisible(false);
		Register_Login.currentId=0;
		btn_explore(event);
    }

}
