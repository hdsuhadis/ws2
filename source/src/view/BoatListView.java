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

public class BoatListView extends GridPane{
	private String activeMemberID;
	private int activeBoats;
	private ArrayList<Boat> boatList;
	private GridPane boatListPane;
	private InputValidator validator;

	public BoatListView(ArrayList<Boat> boatList, String memberID) {
		activeMemberID = memberID;
		this.boatList = boatList;
		activeBoats = boatList.size();
		boatListPane = new GridPane();
		validator = new InputValidator();
		
		getBoatListView();
	}

	private void getBoatListView() {
		GridPane.setConstraints(boatListPane, 0, 1);
		
		Label boatHeader = new Label("Boats");
		boatHeader.setFont(new Font("Arial", 25));
		FlowPane headerPane = new FlowPane();
		GridPane.setConstraints(headerPane, 0, 0);
		headerPane.getChildren().addAll(boatHeader);
		
		drawBoatList();
		
		Button addBoatButton = getAddBoatButton();
		VBox marginBox = new VBox();
		marginBox.setPadding(new Insets(10));
		GridPane.setConstraints(marginBox, 0, 2); 
		
		GridPane addButtonPane = new GridPane();
		addButtonPane.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
		addButtonPane.setMinHeight(130);
		addButtonPane.setMaxWidth(221);
		addButtonPane.setPadding(new Insets(51, 0, 0, 71)); // This positions the button in the center
		addButtonPane.getChildren().addAll(addBoatButton);
		
		marginBox.getChildren().add(addButtonPane);
		
		this.getChildren().addAll(headerPane,marginBox,boatListPane);
	}

	
	//Button for adding boats
	private Button getAddBoatButton() {
		Button addBoatButton = new Button("+ Add Boat");

		addBoatButton.setOnAction(e -> {
			
			activeBoats++;
			
			Boat newBoat = new Boat(0, 0, Boat.Type.Other);
			boatList.add(newBoat);
			
			BoatView newBoatView = new BoatView(activeBoats, Boat.Type.Other, 0, 0);
			GridPane.setConstraints(newBoatView, 0, activeBoats );
			
			Button newDeleteButton = getDeleteBoatButton(newBoat, boatListPane.getChildren().size()+1);
			GridPane.setConstraints(newDeleteButton, 1, activeBoats);
			
			boatListPane.getChildren().addAll(newBoatView, newDeleteButton);

			updateBoatList();
		});
		
		return addBoatButton;
	}
	
	private void drawBoatList() {
		for (int i = boatListPane.getChildren().size(); i > 0; i--) {
			boatListPane.getChildren().remove(0);
		}
		
		for(int i = 0; i < activeBoats; i++) {
			Boat.Type boatType = boatList.get(i).getType();
			int boatLength = boatList.get(i).getLength();
			int boatWidth = boatList.get(i).getWidth();
			
			BoatView boatView = new BoatView((i + 1), boatType, boatLength, boatWidth);
			GridPane.setConstraints(boatView, 0, (i + 1));
			boatListPane.getChildren().add(boatView);
			
			Button deleteBoatButton = getDeleteBoatButton(boatList.get(i), boatListPane.getChildren().size());
			GridPane.setConstraints(deleteBoatButton, 1, (i + 1));
			GridPane.setValignment(deleteBoatButton, VPos.CENTER);
			boatListPane.getChildren().add(deleteBoatButton);
		}
	}
	
	private void changeBoatInfo(int index, Boat.Type type, int length, int width) {
		boatList.set(index, new Boat(length, width, type));
		
		updateBoatList();
		
		drawBoatList();
	}
	
	
	//Button for deleting a boat
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


	private class BoatView extends GridPane{
		int boatIndex;
		Boat.Type type;
		int length;
		int width;

		public BoatView(int boatIndex, Boat.Type type, int length, int width) {
			this.boatIndex = boatIndex;
			this.type = type;
			this.length = length; 
			this.width = width;
			
			this.setHgap(20);
			this.setPadding(new Insets(10));

			getBoatView();
		}

		private void getBoatView() {
			int index = 0;
			GridPane boatInfoPane = new GridPane();
			GridPane.setConstraints(boatInfoPane, 0, 0);
			boatInfoPane.setPadding(new Insets(10));
			boatInfoPane.setVgap(5);
			boatInfoPane.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0))));
			
			Label boatNumberLabel = new Label("Boat: " + boatIndex);
			boatNumberLabel.setFont(new Font("Arial", 15));
			GridPane.setConstraints(boatNumberLabel, 0, index);
			boatInfoPane.getChildren().add(boatNumberLabel);
			index++;

			//BoatType
			Label boatTypeText = new Label("Type: ");
			boatTypeText.setStyle("-fx-font-weight: bold;");
			GridPane.setConstraints(boatTypeText, 0, index);

			Label boatTypeLabel = new Label(type.toString());
			GridPane.setConstraints(boatTypeLabel, 1, index);

			ChoiceBox<Boat.Type> boatTypeChoiceBox = new ChoiceBox<Boat.Type>();
			boatTypeChoiceBox.setItems(FXCollections.observableArrayList(Boat.Type.values()));
			boatTypeChoiceBox.setValue(type);
			boatTypeChoiceBox.setTooltip(new Tooltip("Select the Type"));
			boatTypeChoiceBox.setVisible(false);
			GridPane.setConstraints(boatTypeChoiceBox, 1, index);

			boatInfoPane.getChildren().addAll(boatTypeText, boatTypeLabel, boatTypeChoiceBox);

			index++;

			//BoatLength
			Label boatLengthText = new Label("Length: ");
			boatLengthText.setStyle("-fx-font-weight: bold;");
			GridPane.setConstraints(boatLengthText, 0, index);

			Label boatLengthLabel = new Label(String.valueOf(length)+" cm");
			GridPane.setConstraints(boatLengthLabel, 1, index);

			TextField boatLengthTextField = new TextField(String.valueOf(length));
			boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
			boatLengthTextField.setVisible(false);
			GridPane.setConstraints(boatLengthTextField, 1, index);	

			boatInfoPane.getChildren().addAll(boatLengthText, boatLengthLabel, boatLengthTextField);

			index++;

			//BoatWidth
			Label boatWidthText = new Label("Width: ");
			boatWidthText.setStyle("-fx-font-weight: bold;");
			GridPane.setConstraints(boatWidthText, 0, index);

			Label boatWidthLabel = new Label(String.valueOf(width)+" cm");
			GridPane.setConstraints(boatWidthLabel, 1, index);

			TextField boatWidthTextField = new TextField(String.valueOf(width));
			boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
			boatWidthTextField.setVisible(false);
			GridPane.setConstraints(boatWidthTextField, 1, index);	

			boatInfoPane.getChildren().addAll(boatWidthText, boatWidthLabel, boatWidthTextField);

			index++;

			//Button for editing boats
			Button editBoatButton = getEditBoatButton(boatTypeLabel, boatTypeChoiceBox, boatLengthLabel, boatLengthTextField, boatWidthLabel, boatWidthTextField);
			GridPane.setConstraints(editBoatButton, 1, 0);
			GridPane.setValignment(editBoatButton, VPos.CENTER);
			
			this.getChildren().addAll(boatInfoPane, editBoatButton);
		}

		//Get the button for editing boats
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
				if(boatTypeChoiceBox.isVisible()) { // Save data
					// User input error responses
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
					
				} else { // Change data
					// Hide labels
					boatTypeLabel.setVisible(false);
					boatLengthLabel.setVisible(false);
					boatWidthLabel.setVisible(false);

					boatLengthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					boatWidthTextField.setBorder(new Border(new BorderStroke(Paint.valueOf("LightGrey"), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(0.75))));
					
					// Show editable fields
					boatTypeChoiceBox.setVisible(true);
					boatLengthTextField.setVisible(true);
					boatWidthTextField.setVisible(true);

					editBoatsButton.setText("Save");
				}

			});

			return editBoatsButton;
		}

	}
	
}


