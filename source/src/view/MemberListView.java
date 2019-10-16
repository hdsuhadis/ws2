package view;

import javafx.geometry.Insets;
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
import model.MemberRegistry;

public class MemberListView extends BorderPane{
	private ScrollPane innerListViewPane;
	private GridPane compactList;
	private GridPane verboseList;
	private MemberRegistry registry;
	private Button compactListButton;
	private Button verboseListButton;

	public MemberListView() {
		this.registry = new MemberRegistry();
		this.registry.importMembersFromDatabase();

		this.setPadding(new Insets(20,0,0,0));

		this.innerListViewPane = new ScrollPane();
		
		// Removing selection highlighting for the list
		this.innerListViewPane.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color;-fx-background-insets: 0, 1, 2;-fx-background-radius: 1, 1, 1;");
		BorderPane.setMargin(innerListViewPane, new Insets(0,20,20,20));

		this.compactList = getCompactList();
		this.verboseList = getVerboseList();

		this.setTop(getListSelectionMenu()); // Menu to switch between compact/verbose list
		this.setCenter(innerListViewPane);	// The content to be displayed

		this.innerListViewPane.setContent(compactList);	// Setting start content to be compact list
	}

	//SelectionMenu for list view
	private GridPane getListSelectionMenu() {
		GridPane menuBar = new GridPane();
		menuBar.setPadding(new Insets(10, 10, 10, 20));
		menuBar.setHgap(10); // Button spacing

		// Button to show the list in a compact format
		this.compactListButton = getCompactListButton();
		GridPane.setConstraints(this.compactListButton, 0, 0);

		// Button to show the list in a verbose format
		this.verboseListButton = getVerboseListButton();
		GridPane.setConstraints(this.verboseListButton, 1, 0);

		menuBar.getChildren().addAll(this.compactListButton, this.verboseListButton);

		return menuBar;
	}

	// Returns a compact list view of data from database
	private GridPane getCompactList() {
		GridPane compactList = new GridPane();
		
		// Basic styling
		compactList.setHgap(20);
		compactList.setVgap(5);
		compactList.setPadding(new Insets(10,10,10,10));

		// Loop that runs for each member in the registry
		for(int i = 0; i < this.registry.getMemberList().size(); i++) {
			GridPane memberInfoPane = new GridPane();
			memberInfoPane.setPadding(new Insets(10,10,10,10));
			memberInfoPane.setMinWidth(250);
			memberInfoPane.setBorder(new Border(new BorderStroke(Paint.valueOf("Grey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
			
			int index = 0;

			// Name text 
			Label name = new Label("Name: " + this.registry.getMemberList().get(i).getName());
			GridPane.setConstraints(name, 0, index);
			index++;

			// Member ID text
			Label memberID = new Label("ID: " + this.registry.getMemberList().get(i).getMemberID());
			GridPane.setConstraints(memberID, 0, index);
			index++;
			
			// Number of boats text
			Label numberOfBoats = new Label("Boats: " + this.registry.getMemberList().get(i).getBoatList().size());
			GridPane.setConstraints(numberOfBoats, 0, index);

			memberInfoPane.getChildren().addAll(name, memberID, numberOfBoats);

			
			GridPane buttonPane = new GridPane();
			buttonPane.setVgap(10);
			// Button showing all details about a specific member instead of showing the list
			Button memberButton = getViewMemberButton(i);
			// Button for deleting a member
			Button deleteMemberButton = getDeleteMemberButton(i);

			GridPane.setConstraints(memberButton, 0, 0);
			GridPane.setConstraints(deleteMemberButton, 0, 1);

			buttonPane.getChildren().addAll(memberButton, deleteMemberButton);

			GridPane.setConstraints(memberInfoPane, 0, i);
			GridPane.setConstraints(buttonPane, 1, i);

			compactList.getChildren().addAll(memberInfoPane, buttonPane);
		}

		return compactList;
	}


	// Returns a verbose list view of data from database
	private GridPane getVerboseList() {
		GridPane verboseList = new GridPane();
		
		// Basic styling
		verboseList.setHgap(20);
		verboseList.setVgap(5);
		verboseList.setPadding(new Insets(10,10,10,10));

		// Loop that runs for each member in the registry
		for(int i = 0; i < this.registry.getMemberList().size(); i++) {

			GridPane memberInfoPane = new GridPane();
			memberInfoPane.setPadding(new Insets(10,10,10,10));
			memberInfoPane.setMinWidth(400);
			memberInfoPane.setBorder(new Border(new BorderStroke(Paint.valueOf("Grey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
			int index = 0;

			// Name text
			Label name = new Label("Name: " + this.registry.getMemberList().get(i).getName());
			GridPane.setConstraints(name, 0, index);
			index++;
			
			// Personal number text
			Label personalNumber = new Label("Personal Number: " + this.registry.getMemberList().get(i).getPersonalNumber());
			GridPane.setConstraints(personalNumber, 0, index);
			index++;

			// Member ID text
			Label memberID = new Label("ID: " + this.registry.getMemberList().get(i).getMemberID());
			GridPane.setConstraints(memberID, 0, index);
			index++;

			memberInfoPane.getChildren().addAll(name, personalNumber, memberID);

			// Loop that runs for each boat in the member's boat list, if any
			for(int j = 0; j < this.registry.getMemberList().get(i).getBoatList().size(); j++) {
				// Boat number text
				Label boatNumber = new Label("Boat " + (j + 1) + ": ");
				GridPane.setConstraints(boatNumber, 0, index);
				index++;

				// Boat type text
				Label boatType = new Label("\tType: " + this.registry.getMemberList().get(i).getBoatList().get(j).getType());
				GridPane.setConstraints(boatType, 0, index);
				index++;

				// Boat length text
				Label boatLength = new Label("\tLength: " + this.registry.getMemberList().get(i).getBoatList().get(j).getLength()  + " cm");
				GridPane.setConstraints(boatLength, 0, index);
				index++;
				
				// Boat width text
				Label boatWidth = new Label("\tWidth: " + this.registry.getMemberList().get(i).getBoatList().get(j).getWidth() + " cm");
				GridPane.setConstraints(boatWidth, 0, index);
				index++;

				memberInfoPane.getChildren().addAll(boatNumber, boatType, boatLength, boatWidth);
			}

			GridPane buttonPane = new GridPane();
			buttonPane.setVgap(10);
			// Button showing all details about a specific member instead of showing the list
			Button memberButton = getViewMemberButton(i);
			// Button for deleting a member
			Button deleteMemberButton = getDeleteMemberButton(i);

			GridPane.setConstraints(memberButton, 0, 0);
			GridPane.setConstraints(deleteMemberButton, 0, 1);

			buttonPane.getChildren().addAll(memberButton, deleteMemberButton);

			GridPane.setConstraints(memberInfoPane, 0, i);
			GridPane.setConstraints(buttonPane, 1, i);

			verboseList.getChildren().addAll(memberInfoPane, buttonPane);
		}

		return verboseList;
	}

	// Returns a button to show a compact member list
	private Button getCompactListButton() {
		Button compactListButton = new Button("Compact List");
		compactListButton.setMinWidth(100);
		compactListButton.setStyle("-fx-font-weight: bold;");

		compactListButton.setOnAction(e -> {
			this.innerListViewPane.setContent(this.compactList);
			this.compactListButton.setStyle("-fx-font-weight: bold;");
			this.verboseListButton.setStyle("-fx-font-weight: normal;");
		});

		return compactListButton;
	}

	// Returns a button to show a verbose member list
	private Button getVerboseListButton() {
		Button verboseListButton = new Button("Verbose List");
		verboseListButton.setMinWidth(100);

		verboseListButton.setOnAction(e -> {
			this.innerListViewPane.setContent(this.verboseList);
			this.compactListButton.setStyle("-fx-font-weight: normal;");
			this.verboseListButton.setStyle("-fx-font-weight: bold;");
		});

		return verboseListButton;
	}

	// Returns a button to show a detailed view one member
	private Button getViewMemberButton(int index) {
		Button viewMemberButton = new Button("View Member");
		viewMemberButton.setMinWidth(100);

		viewMemberButton.setOnAction(e ->{
			this.innerListViewPane.setContent(new DetailedMemberView(this.registry.getMemberList().get(index)));

			// Remove Verbose/Compact list buttons when entering detailed member view
			GridPane emptyPane = new GridPane();
			this.setTop(emptyPane);
		});

		return viewMemberButton;
	}


	// Returns a button to delete a member from the registry
	private Button getDeleteMemberButton(int index) {
		Button deleteMemberButton = new Button ("Delete Member");
		deleteMemberButton.setMinWidth(100);

		deleteMemberButton.setOnAction(e -> {
			this.registry.deleteMember(this.registry.getMemberList().get(index));
			this.compactList = getCompactList();
			this.verboseList = getVerboseList();

			this.innerListViewPane.setContent(this.compactList);
		});

		return deleteMemberButton;
	}

}
