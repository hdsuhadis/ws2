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
	private ScrollPane centerScrollPane; 

	// Constructor
	public MainView(double width, double height) {
		this.setHeight(height);
		this.setWidth(width);
		
		// Set the content of the main container
		this.centerScrollPane = new ScrollPane();
		this.centerScrollPane.setContent(new HomeView());
		this.centerScrollPane.setFitToWidth(true);
		
		// Remove selection highlighting from main content container
		this.centerScrollPane.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color;-fx-background-insets: 0, 1, 2;-fx-background-radius: 1, 1, 1;");
		
		// Add three content containers, stacked vertically
		this.setTop(this.getTopPanel());
		this.setCenter(this.centerScrollPane);	
		this.setBottom(this.getBottomPanel());
	}
	
	// Returns the top panel
	private BorderPane getTopPanel() {
		BorderPane topPanel = new BorderPane();
		topPanel.setPadding(new Insets(10, 10, 10, 10));
		
		topPanel.setTop(getHeader());	// Add header to topPanel
		topPanel.setCenter(getMenuBar()); // Add menu bar to topPanel		
		
		topPanel.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2.0))));
		
		return topPanel;
		
	}
	
	// Returns the bottom panel; a footer with the creators
	private BorderPane getBottomPanel() {
		BorderPane bottomPanel = new BorderPane();
		bottomPanel.setPadding(new Insets(5,5,5,5));
		
		// Creator info for footer
		Label bottomPanelLabel = new Label("Created by Adam Elfström & Andreas Petersson");
		bottomPanelLabel.setFont(new Font("Arial", 14));
		BorderPane.setAlignment(bottomPanelLabel, Pos.CENTER_RIGHT);
		bottomPanel.setRight(bottomPanelLabel);
		bottomPanel.setBorder(new Border(new BorderStroke(Paint.valueOf("Grey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		
		return bottomPanel;
	}
	
	// Returns the menu bar to be placed in the top panel
	private GridPane getMenuBar() {
		GridPane menuBar = new GridPane();
		menuBar.setHgap(10);
		int index = 0;
		
		// Show home view button
		Button homeButton = getHomeButton();
		GridPane.setMargin(homeButton, new Insets(5, 0, 5, 0));
		GridPane.setConstraints(homeButton, index, 0);
		index++;
		
		// Show add member view button
		Button addMemberButton = getAddMemberButton();
		GridPane.setConstraints(addMemberButton, index, 0);
		index++;
				
		// Show member list view button
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
	
	// Button for entering AddMemberView
	private Button getAddMemberButton() {
		Button addMemberButton = new Button("Add Member");
		
		addMemberButton.setOnAction(e -> {
			AddMemberView newAddMemberView = new AddMemberView();
			centerScrollPane.setContent(newAddMemberView);
		});
		
		return addMemberButton;
	}
	
	// Button for entering MemberListView
	private Button getViewListButton() {
		Button viewListButton = new Button("Member List");	
		
		viewListButton.setOnAction(e -> {
			MemberListView newListView = new MemberListView();
			centerScrollPane.setContent(newListView);
		});
		
		return viewListButton;
	}
	
	// Return header to place over the menu
	private Label getHeader() {
		Label header = new Label("Boat Club Program");
		header.setStyle("-fx-font-weight: bold;");
		header.setFont(new Font("Arial", 30));
		return header;
	}

}
