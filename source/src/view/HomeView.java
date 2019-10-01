package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public class HomeView extends FlowPane{
	
	public HomeView() {
		this.setAlignment(Pos.TOP_CENTER);
		Label welcomingText = new Label("Welcome to the Boat Club Application!");
		welcomingText.setPadding(new Insets(40,0,0,0));
		welcomingText.setStyle("-fx-font-weight: bold;");
		welcomingText.setFont(new Font("Arial", 30));
		this.getChildren().add(welcomingText);	
	}
}
