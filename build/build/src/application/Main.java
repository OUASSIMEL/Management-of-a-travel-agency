package application;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	
	@Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("Interface.fxml"));
    
        Scene scene = new Scene(root);
        stage.setTitle("ADV");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
	public static void main(String[] args) {
		launch(args);
	}
}
