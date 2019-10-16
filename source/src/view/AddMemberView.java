package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import model.Boat;
import model.BoatClubMember;
import model.MemberRegistry;

public class AddMemberView extends FlowPane{
	private MemberRegistry registry;
	private InputValidator validator;

	// Constructor
	public AddMemberView() {
		this.setPadding(new Insets(20,10,10,10));
		this.setAlignment(Pos.TOP_CENTER);
		
		this.registry = new MemberRegistry();
		this.validator = new InputValidator();

		this.getChildren().add(getForm());	
	}

	//Add new member form
	private GridPane getForm() {
		GridPane form = new GridPane();
		
		// Basic form styling
		form.setPadding(new Insets(10,20,20,20));
		form.setHgap(30);
		form.setVgap(10);
		form.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));

		// Index is used to anchor the elements in the correct position on the Y-axis
		int index = 0;
		
		// Form header, simply a row of text
		Label header = new Label("New Member");
		GridPane.setConstraints(header, 0, index);
		GridPane.setHalignment(header, HPos.CENTER);
		form.getChildren().add(header);
		index++;


		// GridPane containing the input fields for member information
		GridPane memberInfoGroup = new GridPane();
		memberInfoGroup.setHgap(10);
		memberInfoGroup.setVgap(10);
		memberInfoGroup.setPadding(new Insets(10));
		memberInfoGroup.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		GridPane.setConstraints(memberInfoGroup, 0, index);
		form.getChildren().add(memberInfoGroup);

			// Input field for name
			TextField nameField = new TextField();
			nameField.setPromptText("Firstname Lastname"); // Placeholder text that explains what input is expected
			nameField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
			GridPane.setConstraints(nameField, 0, 0);
	
			// Label for the name input field
			Label nameLabel = new Label("Name");
			GridPane.setConstraints(nameLabel, 1, 0);
	
			// Input field for personal Number
			TextField personalNumberField = new TextField();
			personalNumberField.setPromptText("YYMMDDXXXX"); // Placeholder text that explains what input is expected
			personalNumberField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
			GridPane.setConstraints(personalNumberField, 0, 1);
			
			// Label for the personal Number input field
			Label personalNumberLabel = new Label("Personal Number");
			GridPane.setConstraints(personalNumberLabel, 1, 1);

		memberInfoGroup.getChildren().addAll(nameField, nameLabel, personalNumberField, personalNumberLabel);
		index++;

		// GridPane containing the input fields for the added boats, starts empty
		GridPane boatListGroup = new GridPane();
		boatListGroup.setHgap(10);
		boatListGroup.setVgap(10);
		boatListGroup.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		GridPane.setConstraints(boatListGroup, 0, index);
		form.getChildren().add(boatListGroup);
		index++;	

		// Button to add boats to the boat list
		Button addBoatToFormButton = getAddBoatToGroupButton(boatListGroup);
		GridPane.setConstraints(addBoatToFormButton, 0, index);
		index++;

		// Button to save the member and it's boats to the database
		Button saveMemberButton = getSaveMemberButton(nameField, personalNumberField, boatListGroup);
		GridPane.setConstraints(saveMemberButton, 0, index);
		GridPane.setHalignment(saveMemberButton, HPos.CENTER);

		form.getChildren().addAll(addBoatToFormButton, saveMemberButton);

		return form;
	}

	// Button that adds a new form for entering boat info to boatListGroup
	private Button getAddBoatToGroupButton(GridPane boatGroup) {
		Button addBoatToFormButton = new Button("+ Add Boat");

		addBoatToFormButton.setOnAction(e -> {
			BoatInfoPane boatInfoPane = new BoatInfoPane(boatGroup);
			GridPane.setConstraints(boatInfoPane, 0, boatGroup.getChildren().size());
			boatGroup.getChildren().add(boatInfoPane);

		});

		return addBoatToFormButton;
	}

	// Button that saves all the input data 
	private Button getSaveMemberButton(TextField nameField, TextField personalNumberField, GridPane boatGroup) {
		Button saveMemberButton = new Button("Save new member");

		saveMemberButton.setOnAction(e -> {
			// Only save the data if the input is OK
			if(validInput(nameField, personalNumberField, boatGroup)){
				BoatClubMember newMember = registry.addMember(nameField.getText(), Long.parseLong(personalNumberField.getText()));
				ArrayList<Boat> newBoatList = new ArrayList<Boat>();

				for(int i = 0; i < boatGroup.getChildren().size(); i++) {
					newBoatList.add(new Boat(
							Integer.parseInt(((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatLengthField().getText()),
							Integer.parseInt(((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatWidthField().getText()),
							((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatType()
							));
				}
				registry.updateBoatsForMember(newMember.getMemberID(), newBoatList); 
				this.getChildren().clear();
				this.getChildren().add(getForm());
			}
		});

		return saveMemberButton;
	}

	
	// Large input validation method, checks all fields at once and returns a boolean to stop incorrect data being saved
	// The method also changes the border colour of all fields to indicate which of them have incorrect data
	private boolean validInput(TextField nameField, TextField personalNumberField, GridPane boatGroup) {

        boolean inputValid = true;
        
        // Check name input
        if(!validator.validName(nameField.getText())) {
        	// Change the border of the field to red, to indicate invalid input
            nameField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            inputValid = false;
        } else 
        	nameField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
        
        // Check personal number input
        if(!validator.validPersonalNumber(personalNumberField.getText())) {
        	// Change the border of the field to red, to indicate invalid input
            personalNumberField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            inputValid = false;
        } else 
        	personalNumberField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
        
        // Boat check loop
        for(int i = 0; i < boatGroup.getChildren().size(); i++) {
        	// Get the fields related to the boat currently being checked
            TextField currentLengthField = ((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatLengthField();
            TextField currentWidthField = ((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatWidthField();
            
            // Check length input
            if(!validator.inputIsNumeric(currentLengthField.getText())) {
            	// Change the border of the field to red, to indicate invalid input
                currentLengthField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
                inputValid = false;
            } else 
            	 currentLengthField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            
            // Check width input
            if(!validator.inputIsNumeric(currentWidthField.getText())) {
            	// Change the border of the field to red, to indicate invalid input
                currentWidthField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
                inputValid = false;
            } else 
            	currentWidthField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            
        }
        
        return inputValid;
    }
	
	// Private inner class that contains input fields for one boat
	private class BoatInfoPane extends GridPane{
		private Label boatCounterLabel;
		private ChoiceBox<Boat.Type> boatTypeChoiceBox;
		private Label boatTypeLabel;
		private TextField lengthBox;
		private Label boatLengthLabel;
		private TextField widthBox;
		private Label boatWidthLabel;

		// Constructor
		public BoatInfoPane(GridPane boatGroup) {
			// Basic styling
			this.setVgap(5);
			this.setHgap(10);
			this.setPadding(new Insets(10));
			
			// Header to separate from other added boats
			boatCounterLabel = new Label("Boat " + (boatGroup.getChildren().size() + 1));

			// Boat type selection box
			boatTypeChoiceBox = new ChoiceBox<Boat.Type>();
			boatTypeChoiceBox.setItems(FXCollections.observableArrayList(Boat.Type.values()));
			boatTypeChoiceBox.getSelectionModel().select(Boat.Type.Other);
			boatTypeLabel = new Label("Type");

			// Boat length input field
			lengthBox = new TextField();
			lengthBox.setPromptText("Length (cm)");
			lengthBox.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
			boatLengthLabel = new Label("Length");
			
			// Boat width input field
			widthBox = new TextField();
			widthBox.setPromptText("Width (cm)");
			widthBox.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
			boatWidthLabel = new Label("Width");
			
			// Anchoring all the elements
			GridPane.setConstraints(boatCounterLabel, 0, 0);
			GridPane.setConstraints(boatTypeChoiceBox, 0, 1);
			GridPane.setConstraints(boatTypeLabel, 1, 1);
			GridPane.setConstraints(lengthBox, 0, 2);
			GridPane.setConstraints(boatLengthLabel, 1, 2);
			GridPane.setConstraints(widthBox, 0, 3);
			GridPane.setConstraints(boatWidthLabel, 1, 3);

			this.getChildren().addAll(boatCounterLabel, boatTypeChoiceBox, boatTypeLabel, lengthBox, boatLengthLabel, widthBox, boatWidthLabel);
		}
		
		// Returns the selected boat type
		public Boat.Type getBoatType() {
			return boatTypeChoiceBox.getValue();
		}
		
		// Returns the input field for boat length, can be used to edit it's styling when incorrect input is given
		public TextField getBoatLengthField() {
			return lengthBox;
		}
		
		// Returns the input field for boat width, can be used to edit it's styling when incorrect input is given
		public TextField getBoatWidthField() {
			return widthBox;
		}

	}


}
