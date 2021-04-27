package application;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class cartController implements Initializable{
	@FXML
	private TableView<booked> tablev;
	@FXML
	private TableColumn<booked,Integer> cperson;
	@FXML
	private TableColumn<booked,Integer> ctravel;
	@FXML
	private TableColumn<booked,Integer> cprice;
	@FXML
	private Spinner<Integer> spinner;
	final int initialValue = 1;
	// Value factory.
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialValue);

	@FXML
	private Label total;
	@FXML
    private Label somme;
	@FXML
	private Button btnpayment;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		spinner.setValueFactory(valueFactory);
		try {
			showBooked();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static int idp = Register_Login.currentId;
	public ObservableList<booked> getBookedList() throws URISyntaxException, SQLException{
		ObservableList<booked> bookedList = FXCollections.observableArrayList();
		Connection connection = DBConnection.getConnection();
		String query = "SELECT * FROM booked where id_p="+idp+"";
		Statement statement;
		ResultSet resultSet;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			booked bookeds;
			while (resultSet.next()) {
				bookeds = new booked(resultSet.getInt("id_p"), resultSet.getInt("id_v"), resultSet.getInt("price"));
				bookedList.add(bookeds);
			}
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return bookedList;
	}
	public void showBooked() throws SQLException, URISyntaxException {
		ObservableList<booked> bookedlist = getBookedList();
		cperson.setCellValueFactory(new PropertyValueFactory<booked,Integer>("id_p"));
		ctravel.setCellValueFactory(new PropertyValueFactory<booked,Integer>("id_v"));
		cprice.setCellValueFactory(new PropertyValueFactory<booked,Integer>("price"));
		String numbidString = "Select SUM(price) from booked where id_p="+idp+"";
        Statement statement = DBConnection.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(numbidString);
        rs.next();
        int totalp = rs.getInt(1);
		total.setText(Integer.toString(totalp)+" DH");
		somme.setText(Integer.toString(sommecal())+" DH");
		tablev.setItems(bookedlist);
	}
	int sommecal() throws SQLException, URISyntaxException {
		String numbidString = "Select SUM(price) from booked where id_p="+idp+"";
        Statement statement = DBConnection.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(numbidString);
        rs.next();
        int totalp = rs.getInt(1);
        int spi = spinner.getValue();
        return totalp*spi;
	}
	@FXML
    void refresh(MouseEvent event) throws SQLException, URISyntaxException {
		showBooked();
    }
	@FXML
    void paypalpayment(ActionEvent event) throws SQLException, URISyntaxException {
		WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        String checkoutb = getClass().getResource("checkout.html").toString();
        String checkouta = checkoutb;
        checkouta.replace("0.00", Integer.toString(sommecal()));
        webEngine.load(checkouta);
        Scene scene = new Scene(webView,600,600);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Payment");
        primaryStage.show();
    }
}
