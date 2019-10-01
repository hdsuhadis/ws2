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

public class ListView extends BorderPane{
	private ScrollPane innerListViewPane;
	private GridPane compactList;
	private GridPane verboseList;
	private MemberRegistry registry;
	private Button compactListButton;
	private Button verboseListButton;

	public ListView() {
		registry = new MemberRegistry();
		registry.importMembersFromDatabase();

		this.setPadding(new Insets(20,0,0,0));

		innerListViewPane = new ScrollPane();
		// Removing selection highlighting for the list
		innerListViewPane.setStyle("-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color;-fx-background-insets: 0, 1, 2;-fx-background-radius: 1, 1, 1;");
		BorderPane.setMargin(innerListViewPane, new Insets(0,20,20,20));

		compactList = getCompactList();
		verboseList = getVerboseList();

		this.setTop(getListSelectionMenu()); // Menu to switch between compact/verbose list
		this.setCenter(innerListViewPane);	// The content to be displayed

		innerListViewPane.setContent(compactList);	//Setting start content to be compact list
	}

	//SelectionMenu for list view
	private GridPane getListSelectionMenu() {
		GridPane menuBar = new GridPane(); //using a grid pane for button control
		menuBar.setPadding(new Insets(10, 10, 10, 20));
		menuBar.setHgap(10); // Button spacing

		//compactListButton
		compactListButton = getCompactListButton();
		GridPane.setConstraints(compactListButton, 0, 0);

		//verboseListButton
		verboseListButton = getVerboseListButton();
		GridPane.setConstraints(verboseListButton, 1, 0);

		menuBar.getChildren().addAll(compactListButton, verboseListButton);

		return menuBar;
	}

	//Compact list view of data from database
	private GridPane getCompactList() {
		GridPane compactList = new GridPane();
		compactList.setHgap(20);
		compactList.setVgap(5);
		compactList.setPadding(new Insets(10,10,10,10));


		for(int i = 0; i < registry.getMemberList().size(); i++) {
			GridPane memberInfoPane = new GridPane();
			memberInfoPane.setPadding(new Insets(10,10,10,10));
			memberInfoPane.setMinWidth(250);
			memberInfoPane.setBorder(new Border(new BorderStroke(Paint.valueOf("Grey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
			int index = 0;

			Label name = new Label("Name: " + registry.getMemberList().get(i).getName());
			GridPane.setConstraints(name, 0, index);
			index++;

			Label memberID = new Label("ID: " + registry.getMemberList().get(i).getMemberID());
			GridPane.setConstraints(memberID, 0, index);
			index++;

			Label numberOfBoats = new Label("Boats: " + registry.getMemberList().get(i).getBoatList().size());
			GridPane.setConstraints(numberOfBoats, 0, index);

			memberInfoPane.getChildren().addAll(name, memberID, numberOfBoats);

			//Buttons
			GridPane buttonPane = new GridPane();
			buttonPane.setVgap(10);
			Button memberButton = getViewMemberButton(i);
			Button deleteMemberButton = getDeleteMemberButton(i);

			GridPane.setConstraints(memberButton, 0, 0);
			GridPane.setConstraints(deleteMemberButton, 0, 1);

			buttonPane.getChildren().addAll(memberButton, deleteMemberButton);


			//adding everything up
			GridPane.setConstraints(memberInfoPane, 0, i);
			GridPane.setConstraints(buttonPane, 1, i);

			compactList.getChildren().addAll(memberInfoPane, buttonPane);
		}

		return compactList;
	}


	//Verbose list view of data from database
	private GridPane getVerboseList() {
		GridPane verboseList = new GridPane();
		verboseList.setHgap(20);
		verboseList.setVgap(5);
		verboseList.setPadding(new Insets(10,10,10,10));

		for(int i = 0; i < registry.getMemberList().size(); i++) {

			GridPane memberInfoPane = new GridPane();
			memberInfoPane.setPadding(new Insets(10,10,10,10));
			memberInfoPane.setMinWidth(400);
			memberInfoPane.setBorder(new Border(new BorderStroke(Paint.valueOf("Grey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
			int index = 0;

			Label name = new Label("Name: " + registry.getMemberList().get(i).getName());
			GridPane.setConstraints(name, 0, index);
			index++;

			Label personalNumber = new Label("Personal Number: " + registry.getMemberList().get(i).getPersonalNumber());
			GridPane.setConstraints(personalNumber, 0, index);
			index++;

			Label memberID = new Label("ID: " + registry.getMemberList().get(i).getMemberID());
			GridPane.setConstraints(memberID, 0, index);
			index++;

			memberInfoPane.getChildren().addAll(name, personalNumber, memberID);

			for(int j = 0; j < registry.getMemberList().get(i).getBoatList().size(); j++) {
				Label boatNumber = new Label("Boat " + (j + 1) + ": ");
				GridPane.setConstraints(boatNumber, 0, index);
				index++;

				Label boatType = new Label("\tType: " + registry.getMemberList().get(i).getBoatList().get(j).getType());
				GridPane.setConstraints(boatType, 0, index);
				index++;

				Label boatLength = new Label("\tLength: " + registry.getMemberList().get(i).getBoatList().get(j).getLength()  + " cm");
				GridPane.setConstraints(boatLength, 0, index);
				index++;
				
				Label boatWidth = new Label("\tWidth: " + registry.getMemberList().get(i).getBoatList().get(j).getWidth() + " cm");
				GridPane.setConstraints(boatWidth, 0, index);
				index++;

				memberInfoPane.getChildren().addAll(boatNumber, boatType, boatLength, boatWidth);
			}

			//Buttons
			GridPane buttonPane = new GridPane();
			buttonPane.setVgap(10);
			Button memberButton = getViewMemberButton(i);
			Button deleteMemberButton = getDeleteMemberButton(i);

			GridPane.setConstraints(memberButton, 0, 0);
			GridPane.setConstraints(deleteMemberButton, 0, 1);

			buttonPane.getChildren().addAll(memberButton, deleteMemberButton);


			//adding everything up
			GridPane.setConstraints(memberInfoPane, 0, i);
			GridPane.setConstraints(buttonPane, 1, i);

			verboseList.getChildren().addAll(memberInfoPane, buttonPane);
		}

		return verboseList;
	}


	//----BUTTONS----
	//CompactListButton
	private Button getCompactListButton() {
		Button compactListButton = new Button("Compact List");
		compactListButton.setMinWidth(100);
		compactListButton.setStyle("-fx-font-weight: bold;");

		compactListButton.setOnAction(e -> {
			innerListViewPane.setContent(compactList);
			this.compactListButton.setStyle("-fx-font-weight: bold;");
			this.verboseListButton.setStyle("-fx-font-weight: normal;");
		});

		return compactListButton;
	}

	//VerboseListButton
	private Button getVerboseListButton() {
		Button verboseListButton = new Button("Verbose List");
		verboseListButton.setMinWidth(100);

		verboseListButton.setOnAction(e -> {
			innerListViewPane.setContent(verboseList);
			this.compactListButton.setStyle("-fx-font-weight: normal;");
			this.verboseListButton.setStyle("-fx-font-weight: bold;");
		});

		return verboseListButton;
	}

	//for displaying a single members information
	private Button getViewMemberButton(int index) {
		Button viewMemberButton = new Button("View Member");
		viewMemberButton.setMinWidth(100);

		viewMemberButton.setOnAction(e ->{
			innerListViewPane.setContent(new BoatClubMemberView(registry.getMemberList().get(index)));

			// Remove Verbose/Compact list buttons when entering detailed member view
			GridPane emptyPane = new GridPane();
			this.setTop(emptyPane);
		});

		return viewMemberButton;
	}


	// Button to delete a member from the registry
	private Button getDeleteMemberButton(int index) {
		Button deleteMemberButton = new Button ("Delete Member");
		deleteMemberButton.setMinWidth(100);

		deleteMemberButton.setOnAction(e -> {
			registry.deleteMember(registry.getMemberList().get(index));
			compactList = getCompactList();
			verboseList = getVerboseList();

			innerListViewPane.setContent(compactList);
		});

		return deleteMemberButton;
	}

}
