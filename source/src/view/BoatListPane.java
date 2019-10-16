package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.Boat;
import model.MemberRegistry;

public class BoatListPane extends GridPane{
	private String activeMemberID;
	private int activeBoats;
	private ArrayList<Boat> boatList;
	private GridPane boatListContainer;

	// Constructor
	public BoatListPane(ArrayList<Boat> boatList, String memberID) {
		this.activeMemberID = memberID;
		this.boatList = boatList;
		this.activeBoats = boatList.size();
		this.boatListContainer = new GridPane();
		
		this.getBoatListView();
	}
	
	// Creates and adds all the necessary elements of the boat list to the view
	private void getBoatListView() {
		GridPane.setConstraints(boatListContainer, 0, 1);
		
		Label boatHeader = new Label("Boats");
		boatHeader.setFont(new Font("Arial", 25));
		FlowPane headerPane = new FlowPane();
		GridPane.setConstraints(headerPane, 0, 0);
		headerPane.getChildren().addAll(boatHeader);
		
		this.drawBoatList();
		
		// Button which adds a new boat to the boat list
		Button addBoatButton = getAddBoatButton();
		
		// Pane which looks like an empty boat box, containing the button to add a new boat
		GridPane addButtonPane = new GridPane();
		addButtonPane.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		addButtonPane.setMinHeight(130);
		addButtonPane.setMaxWidth(221);
		addButtonPane.setPadding(new Insets(51, 0, 0, 71)); // This positions the button in the centre of the pane
		addButtonPane.getChildren().add(addBoatButton);
		
		// Box which creates spacing around the add button pane
		VBox marginBox = new VBox();
		marginBox.setPadding(new Insets(10));
		GridPane.setConstraints(marginBox, 0, 2); 
		
		marginBox.getChildren().add(addButtonPane);
		
		this.getChildren().addAll(headerPane,marginBox,boatListContainer);
	}

	
	// Button for adding boats
	private Button getAddBoatButton() {
		Button addBoatButton = new Button("+ Add Boat");

		addBoatButton.setOnAction(e -> {
			activeBoats++;
			
			// Create a new boat
			Boat newBoat = new Boat(0, 0, Boat.Type.Other);
			boatList.add(newBoat);
			
			// Add the new boat's info to a view pane
			BoatInfoPane newBoatView = new BoatInfoPane(activeBoats, Boat.Type.Other, 0, 0);
			GridPane.setConstraints(newBoatView, 0, activeBoats );
			
			// Add a button that can delete this particular boat in the future
			Button newDeleteButton = getDeleteBoatButton(newBoat, boatListContainer.getChildren().size()+1);
			GridPane.setConstraints(newDeleteButton, 1, activeBoats);
			
			boatListContainer.getChildren().addAll(newBoatView, newDeleteButton);

			this.updateBoatList();
		});
		
		return addBoatButton;
	}
	
	// Add all the boats to the view pane
	private void drawBoatList() {
		// Remove all currently drawn boats
		for (int i = boatListContainer.getChildren().size(); i > 0; i--) {
			boatListContainer.getChildren().remove(0);
		}
		
		// Add all boats in the current boat list. 
		// The reason all boats are removed and then added is because of index/anchoring inconsistencies when removing a boat from the pane
		for(int i = 0; i < activeBoats; i++) {
			Boat.Type boatType = boatList.get(i).getType();
			int boatLength = boatList.get(i).getLength();
			int boatWidth = boatList.get(i).getWidth();
			
			// Add the boat to the pane
			BoatInfoPane boatView = new BoatInfoPane((i + 1), boatType, boatLength, boatWidth);
			GridPane.setConstraints(boatView, 0, (i + 1));
			
			// Add a button to delete the current boat
			Button deleteBoatButton = getDeleteBoatButton(boatList.get(i), boatListContainer.getChildren().size());
			GridPane.setConstraints(deleteBoatButton, 1, (i + 1));
			GridPane.setValignment(deleteBoatButton, VPos.CENTER);
			
			boatListContainer.getChildren().addAll(boatView,deleteBoatButton);
		}
	}
	
	// Change the data of a specific boat
	private void changeBoatInfo(int index, Boat.Type type, int length, int width) {
		boatList.set(index, new Boat(length, width, type));
		
		updateBoatList();
		drawBoatList();
	}
	
	
	// Button to delete a boat, uses index in the pane to identify which to remove
	private Button getDeleteBoatButton(Boat boat, int index) {
		Button deleteBoatButton = new Button("X Delete Boat");
		deleteBoatButton.setOnAction(e -> {
			boatList.remove(boat);
			activeBoats = boatList.size();
			
			updateBoatList();
			drawBoatList();
		});
		return deleteBoatButton;
	}

	//Send update request to the registry
	private void updateBoatList() {
		MemberRegistry registry = new MemberRegistry();
		registry.updateBoatsForMember(activeMemberID, boatList);
	}

	// Private inner class that contains input fields and a save/edit button for one boat
	private class BoatInfoPane extends GridPane{
		private int boatIndex;
		private Boat.Type type;
		private int length;
		private int width;
		private InputValidator validator;

		// Constructor
		public BoatInfoPane(int boatIndex, Boat.Type type, int length, int width) {
			this.boatIndex = boatIndex;
			this.type = type;
			this.length = length; 
			this.width = width;
			
			this.validator = new InputValidator();
			
			this.setHgap(20);
			this.setPadding(new Insets(10));

			getBoatView();
		}

		// Add all the fields to the view pane
		private void getBoatView() {
			// Index saves which row is currently being written to, used when anchoring elements on the Y-axis
			int index = 0;
			
			GridPane boatInfoPane = new GridPane();
			GridPane.setConstraints(boatInfoPane, 0, 0);
			
			// Basic styling
			boatInfoPane.setPadding(new Insets(10));
			boatInfoPane.setVgap(5);
			boatInfoPane.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
			
			// Header, says which index in the boat list it has
			Label boatNumberLabel = new Label("Boat: " + boatIndex);
			boatNumberLabel.setFont(new Font("Arial", 15));
			GridPane.setConstraints(boatNumberLabel, 0, index);
			boatInfoPane.getChildren().add(boatNumberLabel);
			index++;

			// Boat type label text
			Label boatTypeText = new Label("Type: ");
			boatTypeText.setStyle("-fx-font-weight: bold;");
			GridPane.setConstraints(boatTypeText, 0, index);

			// Placeholder for type dropdown, displays the currently saved type
			Label boatTypePlaceholder = new Label(type.toString());
			GridPane.setConstraints(boatTypePlaceholder, 1, index);

			// Dropdown containing all available boat types, starts hidden
			ChoiceBox<Boat.Type> boatTypeChoiceBox = new ChoiceBox<Boat.Type>();
			boatTypeChoiceBox.setItems(FXCollections.observableArrayList(Boat.Type.values()));
			boatTypeChoiceBox.setValue(type);
			boatTypeChoiceBox.setTooltip(new Tooltip("Select the Type"));
			boatTypeChoiceBox.setVisible(false);
			GridPane.setConstraints(boatTypeChoiceBox, 1, index);

			boatInfoPane.getChildren().addAll(boatTypeText, boatTypePlaceholder, boatTypeChoiceBox);

			index++;

			// Boat length label text
			Label boatLengthText = new Label("Length: ");
			boatLengthText.setStyle("-fx-font-weight: bold;");
			GridPane.setConstraints(boatLengthText, 0, index);

			// Placeholder for length input field, displays the currently saved length
			Label boatLengthPlaceholder = new Label(String.valueOf(length)+" cm");
			GridPane.setConstraints(boatLengthPlaceholder, 1, index);

			// Input field for the boat length, starts hidden
			TextField boatLengthTextField = new TextField(String.valueOf(length));
			boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
			boatLengthTextField.setVisible(false);
			GridPane.setConstraints(boatLengthTextField, 1, index);	

			boatInfoPane.getChildren().addAll(boatLengthText, boatLengthPlaceholder, boatLengthTextField);

			index++;

			// Boat width label text
			Label boatWidthText = new Label("Width: ");
			boatWidthText.setStyle("-fx-font-weight: bold;");
			GridPane.setConstraints(boatWidthText, 0, index);

			// Placeholder for width input field, displays the currently saved width
			Label boatWidthLabel = new Label(String.valueOf(width)+" cm");
			GridPane.setConstraints(boatWidthLabel, 1, index);

			// Input field for the boat width, starts hidden
			TextField boatWidthTextField = new TextField(String.valueOf(width));
			boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
			boatWidthTextField.setVisible(false);
			GridPane.setConstraints(boatWidthTextField, 1, index);	

			boatInfoPane.getChildren().addAll(boatWidthText, boatWidthLabel, boatWidthTextField);

			index++;

			// Button for editing a specific boat
			Button editBoatButton = getEditBoatButton(boatTypePlaceholder, boatTypeChoiceBox, boatLengthPlaceholder, boatLengthTextField, boatWidthLabel, boatWidthTextField);
			GridPane.setConstraints(editBoatButton, 1, 0);
			GridPane.setValignment(editBoatButton, VPos.CENTER);
			
			this.getChildren().addAll(boatInfoPane, editBoatButton);
		}

		// Get the button for editing boats
		private Button getEditBoatButton(
				Label boatTypeLabel,
				ChoiceBox<Boat.Type> boatTypeChoiceBox,
				Label boatLengthLabel,
				TextField boatLengthTextField,
				Label boatWidthLabel,
				TextField boatWidthTextField
				) {
			Button editBoatsButton = new Button("Edit");
			editBoatsButton.setMinWidth(50);
			editBoatsButton.setMaxWidth(50);

			editBoatsButton.setOnAction(e -> { 
				if(boatTypeChoiceBox.isVisible()) { // If the fields were visible (meaning input has been given), save the input data (if input is valid)
					// User input error responses; changes the border colour to red on the fields with invalid input data
					if (!validator.inputIsNumeric(boatLengthTextField.getText()) && !validator.inputIsNumeric(boatWidthTextField.getText())) {
						boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
						boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					} else if (!validator.inputIsNumeric(boatLengthTextField.getText())) {
						boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
						boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					} else if (!validator.inputIsNumeric(boatWidthTextField.getText())) {
						boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
						boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					} else 
						changeBoatInfo(this.boatIndex-1, boatTypeChoiceBox.getValue(), Integer.parseInt(boatLengthTextField.getText()), Integer.parseInt(boatWidthTextField.getText()));
					
				} else { // If the input fields were hidden, show them to enable user input
					// Hide non-editable placeholders
					boatTypeLabel.setVisible(false);
					boatLengthLabel.setVisible(false);
					boatWidthLabel.setVisible(false);
					
					// Show the editable fields
					boatTypeChoiceBox.setVisible(true);
					boatLengthTextField.setVisible(true);
					boatWidthTextField.setVisible(true);
					
					// Reset border styling of input fields
					boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));

					editBoatsButton.setText("Save");
				}

			});

			return editBoatsButton;
		}

	}
	
}


