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

public class MemberView extends GridPane{
	private String activeMemberID;
	private InputValidator validator;
	
	public MemberView(String name, long personalNumber, String memberID) {
		validator = new InputValidator();
		activeMemberID = memberID;
		getMemberInfoPane(name, personalNumber);

	}


	private void getMemberInfoPane(String name, long personalNumber) {
		this.setHgap(30);
		this.setVgap(5);
		int index = 0;

		//HEADER
		Label memberHeader = new Label("Member");
		memberHeader.setFont(new Font("Arial", 30));
		GridPane.setConstraints(memberHeader, 0, index);
		this.getChildren().add(memberHeader);

		index++;

		//NAME
		Label nameText = new Label("Name: ");
		nameText.setStyle("-fx-font-weight: bold;");
		GridPane.setConstraints(nameText, 0, index);

		Label nameLabel = new Label(name);
		GridPane.setConstraints(nameLabel, 1, index);

		TextField nameTextField = new TextField(name);
		nameTextField.setVisible(false);
		GridPane.setConstraints(nameTextField, 1, index);

		this.getChildren().addAll(nameText, nameLabel, nameTextField);

		index++;


		//PERSONAL NUMBER
		Label numberText = new Label("Personal Number: ");
		numberText.setStyle("-fx-font-weight: bold;");
		GridPane.setConstraints(numberText, 0, index);

		Label personalNumberLabel = new Label("" + personalNumber);
		GridPane.setConstraints(personalNumberLabel, 1, index);

		TextField personalNumberTextField = new TextField("" + personalNumber);
		personalNumberTextField.setVisible(false);
		GridPane.setConstraints(personalNumberTextField, 1, index);

		this.getChildren().addAll(numberText, personalNumberLabel, personalNumberTextField);

		index++;


		//MEMBER ID
		Label idText = new Label("Member ID: ");
		idText.setStyle("-fx-font-weight: bold;");
		GridPane.setConstraints(idText, 0, index);

		Label memberIDLabel = new Label(activeMemberID);
		GridPane.setConstraints(memberIDLabel, 1, index);	

		this.getChildren().addAll(idText, memberIDLabel);

		//CHANGE MEMBERINFO BUTTON
		Button changeMemberInfoButton = getChangeMemberInfoButton(nameLabel, nameTextField, personalNumberLabel, personalNumberTextField);
		GridPane.setConstraints(changeMemberInfoButton, 1, 0);

		this.getChildren().add(changeMemberInfoButton);

	}
	
	private Button getChangeMemberInfoButton(Label name, TextField nameField, Label personalNumber, TextField personalNumberField) {
		Button changeMemberInfoButton = new Button("Edit member info");

		changeMemberInfoButton.setOnAction(e -> { 
			if(nameField.isVisible()) { // Save member information
				// User input error responses
				if (!validator.validPersonalNumber(personalNumberField.getText()) && !validator.validName(nameField.getText())) { // Both input fields invalid
					nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				} else if (!validator.validPersonalNumber(personalNumberField.getText())) { // Invalid personal number
					nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				} else if (!validator.validName(nameField.getText())) {	// Invalid name
					nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("Red"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				} else { // Valid input
					name.setText(nameField.getText());
					name.setVisible(true);

					personalNumber.setText(personalNumberField.getText());
					personalNumber.setVisible(true);

					nameField.setVisible(false);
					personalNumberField.setVisible(false);
					changeMemberInfoButton.setText("Edit member info");
					saveNewMemberInformation(name.getText(), Long.parseLong(personalNumber.getText()));	//Call a save on the new information
				}
			} else { // Edit member information
				// Hide labels
				name.setVisible(false);
				personalNumber.setVisible(false);

				personalNumberField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				nameField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
				
				// Show editable fields
				nameField.setVisible(true);
				personalNumberField.setVisible(true);

				changeMemberInfoButton.setText("Save member info");
			}

		});

		return changeMemberInfoButton;
	}
	
	
	


	private void saveNewMemberInformation(String name, long personalNumber) {
		MemberRegistry registry = new MemberRegistry();
		registry.updateMember(name, personalNumber, this.activeMemberID);
	}

}
