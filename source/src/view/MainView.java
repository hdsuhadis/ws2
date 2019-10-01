package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MainView extends BorderPane{
	ScrollPane centerScrollPane; 

	public MainView(double width, double height) {
		this.setHeight(height);
		this.setWidth(width);
		
		centerScrollPane = new ScrollPane();
		centerScrollPane.setContent(new HomeView());
		centerScrollPane.setFitToWidth(true);
		// Remove selection highlighting from main content container
		centerScrollPane.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color;-fx-background-insets: 0, 1, 2;-fx-background-radius: 1, 1, 1;");
		
		this.setTop(getTopPanel());
		this.setBottom(getBottomPanel());
		this.setCenter(centerScrollPane);	
	}
	
	private BorderPane getTopPanel() {
		BorderPane topPanel = new BorderPane();
		topPanel.setPadding(new Insets(10, 10, 10, 10));
		
		topPanel.setTop(getHeader());	//setting label to topPanel
		topPanel.setCenter(getMenuBar()); //setting menu bar to topPanel		
		
		topPanel.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0))));
		
		return topPanel;
		
	}
	
	private BorderPane getBottomPanel() {
		BorderPane bottomPanel = new BorderPane();
		bottomPanel.setPadding(new Insets(5,5,5,5));
		Label bottomPanelLabel = new Label("Created by Hidden Member 1 & Hidden Member 2");
		bottomPanelLabel.setFont(new Font("Arial", 14));
		BorderPane.setAlignment(bottomPanelLabel, Pos.CENTER_RIGHT);
		
		bottomPanel.setRight(bottomPanelLabel);
		bottomPanel.setBorder(new Border(new BorderStroke(Paint.valueOf("Grey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		
		return bottomPanel;
	}
	
	private GridPane getMenuBar() {
		GridPane menuBar = new GridPane(); //using a grid pane for button control
		menuBar.setHgap(10);
		int index = 0;
		
		//HomeButton
		Button homeButton = getHomeButton();
		GridPane.setMargin(homeButton, new Insets(5, 0, 5, 0));
		GridPane.setConstraints(homeButton, index, 0);
		index++;
		
		//addMemberButton
		Button addMemberButton = getAddMemberButton();
		GridPane.setConstraints(addMemberButton, index, 0);
		index++;
				
		//ShowListButton
		Button viewListButton = getViewListButton();
		GridPane.setConstraints(viewListButton, index, 0);
		index++;
		
		menuBar.getChildren().addAll(homeButton, addMemberButton, viewListButton);
		
		return menuBar;
	}
	
	private Button getHomeButton() {
		Button homeButton = new Button();
		homeButton.setText("Home");
		
		homeButton.setOnAction(e ->{
			centerScrollPane.setContent(new HomeView());
		});
		
		return homeButton;
	}
	
	//Button for entering addMemberView
	private Button getAddMemberButton() {
		Button addMemberButton = new Button("Add Member");
		
		addMemberButton.setOnAction(e -> {
			AddMemberView newAddMemberView = new AddMemberView();
			centerScrollPane.setContent(newAddMemberView);
		});
		
		return addMemberButton;
	}
	
	//for entering listView
	private Button getViewListButton() {
		Button viewListButton = new Button("Member List");	
		
		viewListButton.setOnAction(e -> {
			ListView newListView = new ListView();
			centerScrollPane.setContent(newListView);
		});
		
		return viewListButton;
	}
	
	
	private Label getHeader() {
		Label header = new Label("Boat Club Program");
		header.setStyle("-fx-font-weight: bold;");
		header.setFont(new Font("Arial", 30));
		return header;
	}

}
