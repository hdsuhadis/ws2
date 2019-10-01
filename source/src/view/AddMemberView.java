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

		form.setPadding(new Insets(10,20,20,20));
		form.setHgap(30);
		form.setVgap(10);
		form.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));

		int index = 0;
		//Header
		Label header = new Label("New Member");
		GridPane.setConstraints(header, 0, index);
		GridPane.setHalignment(header, HPos.CENTER);
		form.getChildren().add(header);
		index++;


		//MemberInfoGroup of Form >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		GridPane memberInfoGroup = new GridPane();
		memberInfoGroup.setHgap(10);
		memberInfoGroup.setVgap(10);
		memberInfoGroup.setPadding(new Insets(10));
		memberInfoGroup.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		GridPane.setConstraints(memberInfoGroup, 0, index);
		form.getChildren().add(memberInfoGroup);
		index++;

		//Name
		TextField nameField = new TextField();
		nameField.setPromptText("Firstname Lastname");
		nameField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
		GridPane.setConstraints(nameField, 0, 0);

		Label nameLabel = new Label("Name");
		//GridPane.setMargin(nameLabel, new Insets(0, 0, 0, 10));
		GridPane.setConstraints(nameLabel, 1, 0);

		//Personal Number
		TextField personalNumberField = new TextField();
		personalNumberField.setPromptText("YYMMDDXXXX");
		personalNumberField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
		GridPane.setConstraints(personalNumberField, 0, 1);

		Label personalNumberLabel = new Label("Personal Number");
		//GridPane.setMargin(personalNumberLabel, new Insets(0, 0, 0, 10));
		GridPane.setConstraints(personalNumberLabel, 1, 1);

		memberInfoGroup.getChildren().addAll(nameField, nameLabel, personalNumberField, personalNumberLabel);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		//BoatListGroup of Form >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		GridPane boatListGroup = new GridPane();
		boatListGroup.setHgap(10);
		boatListGroup.setVgap(10);
		boatListGroup.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		GridPane.setConstraints(boatListGroup, 0, index);
		form.getChildren().add(boatListGroup);
		index++;	

		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		//addBoatButton
		Button addBoatToFormButton = getAddBoatToGroupButton(boatListGroup);
		GridPane.setConstraints(addBoatToFormButton, 0, index);
		index++;

		//AddMemberButton
		Button addMemberButton = getAddMemberButton(nameField, personalNumberField, boatListGroup);
		GridPane.setConstraints(addMemberButton, 0, index);
		GridPane.setHalignment(addMemberButton, HPos.CENTER);

		form.getChildren().addAll(addBoatToFormButton, addMemberButton);

		return form;
	}

	//Button that adds a new form for entering boat info to boatListGroup
	private Button getAddBoatToGroupButton(GridPane boatGroup) {
		Button addBoatToFormButton = new Button("+ Add Boat");

		addBoatToFormButton.setOnAction(e -> {

			BoatInfoPane boatInfoPane = new BoatInfoPane(boatGroup);
			GridPane.setConstraints(boatInfoPane, 0, boatGroup.getChildren().size());
			boatGroup.getChildren().add(boatInfoPane);

		});

		return addBoatToFormButton;
	}

	private Button getAddMemberButton(TextField nameField, TextField personalNumberField, GridPane boatGroup) {
		Button addMemberButton = new Button("Save new member");

		addMemberButton.setOnAction(e -> {
			if(checkIfValidInput(nameField, personalNumberField, boatGroup)){
				BoatClubMember newMember = registry.addMember(nameField.getText(), Long.parseLong(personalNumberField.getText()));
				ArrayList<Boat> newBoatList = new ArrayList<Boat>();

				for(int i = 0; i < boatGroup.getChildren().size(); i++) {
					newBoatList.add(new Boat(
							Integer.parseInt(((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatLength().getText()),
							Integer.parseInt(((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatWidth().getText()),
							((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatType().getValue()
							));
				}
				registry.updateBoatsForMember(newMember.getMemberID(), newBoatList); 
				this.getChildren().clear();
				this.getChildren().add(getForm());
			}
		});

		return addMemberButton;
	}


	private boolean checkIfValidInput(TextField nameField, TextField personalNumberField, GridPane boatGroup) {

        boolean inputValid = true;
        
        // Basic member data control
        if(!validator.validName(nameField.getText())) {
            nameField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            inputValid = false;
        } else 
        	nameField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
        
        
        // Boat control
        if(!validator.validPersonalNumber(personalNumberField.getText())) {
            personalNumberField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            inputValid = false;
        } else 
        	personalNumberField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
        
        
        for(int i = 0; i < boatGroup.getChildren().size(); i++) {
            TextField currentLengthField = ((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatLength();
            TextField currentWidthField = ((BoatInfoPane)boatGroup.getChildren().get(i)).getBoatWidth();
            
            // Check length input
            if(!validator.inputIsNumeric(currentLengthField.getText())) {
                currentLengthField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
                inputValid = false;
            } else 
            	 currentLengthField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            
            
            // Check width input
            if(!validator.inputIsNumeric(currentWidthField.getText())) {
                currentWidthField.setBorder((new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
                inputValid = false;
            } else 
            	currentWidthField.setBorder((new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75)))));
            
        }
        
        return inputValid;
    }
	private class BoatInfoPane extends GridPane{
		private Label boatCounterLabel;
		private ChoiceBox<Boat.Type> boatTypeChoiceBox;
		private Label boatTypeLabel;
		private TextField lengthBox;
		private Label boatLengthLabel;
		private TextField widthBox;
		private Label boatWidthLabel;

		public BoatInfoPane(GridPane boatGroup) {
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
			
			GridPane.setConstraints(boatCounterLabel, 0, 0);
			GridPane.setConstraints(boatTypeChoiceBox, 0, 1);
			GridPane.setConstraints(boatTypeLabel, 1, 1);
			GridPane.setConstraints(lengthBox, 0, 2);
			GridPane.setConstraints(boatLengthLabel, 1, 2);
			GridPane.setConstraints(widthBox, 0, 3);
			GridPane.setConstraints(boatWidthLabel, 1, 3);

			this.setVgap(5);
			this.setHgap(10);
			this.setPadding(new Insets(10));
			this.getChildren().addAll(boatCounterLabel, boatTypeChoiceBox, boatTypeLabel, lengthBox, boatLengthLabel, widthBox, boatWidthLabel);
		}

		public ChoiceBox<Boat.Type> getBoatType() {
			return boatTypeChoiceBox;
		}

		public TextField getBoatLength() {
			return lengthBox;
		}

		public TextField getBoatWidth() {
			return widthBox;
		}

	}


}
