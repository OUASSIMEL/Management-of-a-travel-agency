package application;


import java.io.File;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.Statement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

import javafx.scene.control.ComboBox;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class AdminPanelController implements Initializable {
	@FXML
	private TextField idtravel;
	@FXML
	private DatePicker dateI;
	@FXML
	private DatePicker dateE;
	@FXML
	private TextField citys;
	@FXML
	private TextField cityD;
	@FXML
	private TextField nMax;
	@FXML
	private TextField country;
	@FXML
	private TextField logement;
	String opts[] = { "Hotel", "Motel", "Fotel","Xotel", "Notel" }; 
	@FXML
	private ComboBox<String> sub_logement;
	@FXML
    private TextField image;
	@FXML
	private TextField price;
	@FXML
	private TextField description;

	@FXML
	private Button btnSave;
	@FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
	@FXML
	private TableView<Travel> tblData;
	@FXML
	private TableColumn<Travel, Integer> cid_v;
	@FXML
	private TableColumn<Travel, Date> cdates;
	@FXML
	private TableColumn<Travel, Date> cdate;
	@FXML
	private TableColumn<Travel, String> ccitys;
	@FXML
	private TableColumn<Travel, String> ccityd;
	@FXML
	private TableColumn<Travel, Integer> cnmax;
	@FXML
	private TableColumn<Travel, String> ccountry;
	@FXML
	private TableColumn<Travel, String> clogment;
	@FXML
	private TableColumn<Travel, String> csub_logement;
	@FXML
    private TableColumn<Travel, String> cimgtravelURL;

    @FXML
    private TableColumn<Travel, Integer> cprice;
	@FXML
	private Label lblStatus;
	@FXML
	private TableColumn<Travel, String> cdescription;

	// Event Listener on Button[#btnSave].onMouseClicked
	@FXML
	public void HandleEvents(MouseEvent event) throws SQLException {
		if (event.getSource() == btnSave ) {
			AddTravel();
		}
		else if (event.getSource() == btnUpdate){
            UpdateTravel();
        }else if(event.getSource() == btnDelete){
            DeleteTravel();
        }
	}
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		sub_logement.setValue("Hotel");
		sub_logement.setItems(FXCollections.observableArrayList(opts));
		showTravels();
	}
	public ObservableList<Travel> getTravelsList(){
		ObservableList<Travel> travelList = FXCollections.observableArrayList();
		Connection connection = DBConnection.getConnection();
		String query = "SELECT * FROM Voyage";
		Statement statement;
		ResultSet resultSet;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			Travel travels;
			while (resultSet.next()) {
				travels = new Travel(resultSet.getInt("id_v"), resultSet.getString("dateI"), resultSet.getString("dateE"), resultSet.getString("CityS"), resultSet.getString("CityD"), resultSet.getInt("Nmax"), resultSet.getString("Country"), resultSet.getString("logement"), resultSet.getString("sub_logement"),resultSet.getString("imgtravelURL"),resultSet.getInt("price"),resultSet.getString("description"));
				travelList.add(travels);
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return travelList;
	}
	public void showTravels() {
		ObservableList<Travel> travelList = getTravelsList();
		cid_v.setCellValueFactory(new PropertyValueFactory<Travel, Integer>("id_v"));
		cdates.setCellValueFactory(new PropertyValueFactory<Travel, Date>("dateI"));
		cdate.setCellValueFactory(new PropertyValueFactory<Travel, Date>("dateE"));
		ccitys.setCellValueFactory(new PropertyValueFactory<Travel, String>("CityS"));
		ccityd.setCellValueFactory(new PropertyValueFactory<Travel, String>("CityD"));
		cnmax.setCellValueFactory(new PropertyValueFactory<Travel, Integer>("Nmax"));
		ccountry.setCellValueFactory(new PropertyValueFactory<Travel, String>("Country"));
		clogment.setCellValueFactory(new PropertyValueFactory<Travel, String>("logement"));
		csub_logement.setCellValueFactory(new PropertyValueFactory<Travel, String>("sub_logement"));
		cimgtravelURL.setCellValueFactory(new PropertyValueFactory<Travel, String>("imgtravelURL"));
		cprice.setCellValueFactory(new PropertyValueFactory<Travel, Integer>("price"));
		cdescription.setCellValueFactory(new PropertyValueFactory<Travel, String>("description"));
		tblData.setItems(travelList);
	}
	private void executeQuery(String query) {
		Connection conn = DBConnection.getConnection();
		Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
	
	private void AddTravel() throws SQLException{
       /* String query = "INSERT INTO Voyage VALUES (" + idtravel.getText() + ",'" + dateI.getValue() + "','" + dateE.getValue() + "','" + citys.getText() + "','"+ cityD.getText()  + "',"+ nMax.getText() + ",'"+ country.getText() + "','"
                + logement.getText() + "','" + sub_logement.getValue()+"','"+image.getText()+"'," + price.getText()+")";*/
        String queryString = "INSERT INTO `voyage`(`id_v`, `dateI`, `dateE`, `CityS`, `CityD`, `Nmax`, `Country`, `logement`, `sub_logement`, `imgtravelURL`, `price`,`description`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(queryString);
        ps.setString(1, idtravel.getText());
        ps.setString(2, dateI.getValue().toString());
        ps.setString(3, dateE.getValue().toString());
        ps.setString(4, citys.getText());
        ps.setString(5, cityD.getText());
        ps.setString(6, nMax.getText());
        ps.setString(7, country.getText());
        ps.setString(8, logement.getText());
        ps.setString(9, sub_logement.getValue());
        ps.setString(10, image.getText());
        ps.setString(11, price.getText());
        ps.setString(12, description.getText());
        ps.execute();
        //executeQuery(queryString);
        showTravels();
    }
	private void UpdateTravel(){
        String query = "UPDATE Voyage SET dateI = '" + dateI.getValue() + "',dateE = '" + dateE.getValue() + "',CityS = '" + citys.getText() + "',CityD = '"+ cityD.getText()  + "',Nmax = "+ nMax.getText() + ",Country ='"+ country.getText() + "', logement='"+ logement.getText() + "',sub_logement = '" + sub_logement.getValue()+"', imgtravelURL ='"+image.getText()+"', price = " + price.getText()+",description='" +description.getText()+"' WHERE id_v = "+idtravel.getText()+"";
        executeQuery(query);
        showTravels();
    }
	private void DeleteTravel(){
        String query = "DELETE FROM Voyage WHERE id_v = "+idtravel.getText()+"";
        executeQuery(query);
        showTravels();
    }
	
	@FXML
    void btnchooseimgURL(ActionEvent event) {
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Choose Image");
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(primaryStage);
		image.setText(selectedFile.getPath());
    }

}
