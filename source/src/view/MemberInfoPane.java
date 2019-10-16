package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.MemberRegistry;

public class MemberInfoPane extends GridPane{
	private String activeMemberID;
	private InputValidator validator;
	
	// Constructor
	public MemberInfoPane(String name, long personalNumber, String memberID) {
		this.validator = new InputValidator();
		this.activeMemberID = memberID;
		this.getMemberInfoPane(name, personalNumber);
	}

	// Add all the member info to the view pane
	private void getMemberInfoPane(String name, long personalNumber) {
		// Basic styling
		this.setHgap(30);
		this.setVgap(5);
		
		int index = 0;

		// Header
		Label memberHeader = new Label("Member");
		memberHeader.setFont(new Font("Arial", 30));
		GridPane.setConstraints(memberHeader, 0, index);
		this.getChildren().add(memberHeader);

		index++;

		// Name label text
		Label nameText = new Label("Name: ");
		nameText.setStyle("-fx-font-weight: bold;");
		GridPane.setConstraints(nameText, 0, index);

		// Placeholder for name input field, displays currently saved name
		Label namePlaceholder = new Label(name);
		GridPane.setConstraints(namePlaceholder, 1, index);

		// Input field for name, starts hidden
		TextField nameTextField = new TextField(name);
		nameTextField.setVisible(false);
		GridPane.setConstraints(nameTextField, 1, index);

		this.getChildren().addAll(nameText, namePlaceholder, nameTextField);

		index++;


		// Personal number label text
		Label numberText = new Label("Personal Number: ");
		numberText.setStyle("-fx-font-weight: bold;");
		GridPane.setConstraints(numberText, 0, index);

		// Placeholder for personal number input field, displays currently saved personal number
		Label personalNumberPlaceholder = new Label("" + personalNumber);
		GridPane.setConstraints(personalNumberPlaceholder, 1, index);

		// Input field for personal number, starts hidden
		TextField personalNumberTextField = new TextField("" + personalNumber);
		personalNumberTextField.setVisible(false);
		GridPane.setConstraints(personalNumberTextField, 1, index);

		this.getChildren().addAll(numberText, personalNumberPlaceholder, personalNumberTextField);

		index++;


		// Member ID label text
		Label idText = new Label("Member ID: ");
		idText.setStyle("-fx-font-weight: bold;");
		GridPane.setConstraints(idText, 0, index);

		// Placeholder for memberID input field, displays currently saved memberID
		Label memberIDLabel = new Label(activeMemberID);
		GridPane.setConstraints(memberIDLabel, 1, index);	

		this.getChildren().addAll(idText, memberIDLabel);

		// Change member info button
		Button changeMemberInfoButton = getChangeMemberInfoButton(namePlaceholder, nameTextField, personalNumberPlaceholder, personalNumberTextField);
		GridPane.setConstraints(changeMemberInfoButton, 1, 0);

		this.getChildren().add(changeMemberInfoButton);

	}
	
	// Returns the button for editing member data
	private Button getChangeMemberInfoButton(Label name, TextField nameField, Label personalNumber, TextField personalNumberField) {
		Button changeMemberInfoButton = new Button("Edit member info");

		changeMemberInfoButton.setOnAction(e -> { 
			if(nameField.isVisible()) { // If the fields were visible (meaning input has been given), save the input data (if input is valid)
				// User input error responses; changes the border colour to red on the fields with invalid input data
				if (!validator.validPersonalNumber(personalNumberField.getText()) && !validator.validName(nameField.getText())) { // Both input fields invalid
					nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				} else if (!validator.validPersonalNumber(personalNumberField.getText())) { // Invalid personal number
					nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				} else { // Valid input
					// Update the placeholders 
					name.setText(nameField.getText());
					personalNumber.setText(personalNumberField.getText());
					
					// Show the placeholders
					name.setVisible(true);
					personalNumber.setVisible(true);
					
					// Hide the input fields
					nameField.setVisible(false);
					personalNumberField.setVisible(false);
					
					changeMemberInfoButton.setText("Edit member info");
					saveNewMemberInformation(name.getText(), Long.parseLong(personalNumber.getText()));	//Call a save on the new information
				}
			} else { // If the input fields were hidden, show them to enable user input
				// Hide placeholders
				name.setVisible(false);
				personalNumber.setVisible(false);

				personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				
				// Show input fields
				nameField.setVisible(true);
				personalNumberField.setVisible(true);

				changeMemberInfoButton.setText("Save member info");
			}

		});

		return changeMemberInfoButton;
	}
	
	// Save new member info to the registry
	private void saveNewMemberInformation(String name, long personalNumber) {
		MemberRegistry registry = new MemberRegistry();
		registry.updateMember(name, personalNumber, this.activeMemberID);
	}

}
