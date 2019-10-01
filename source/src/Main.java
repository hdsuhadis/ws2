
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application{

	public static void main(String[] args) {
		
		launch(args);
		
	} 

	@Override
	public void start(Stage primaryStage) throws Exception {

		double windowWidth = 800;
		double windowHeight = 800;
		
		// Creating the program window
		Scene scene = new Scene(new MainView(windowWidth, windowHeight), windowWidth, windowHeight);
		
		primaryStage.setTitle("Boat Club Program");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
