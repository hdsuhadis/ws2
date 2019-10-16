package view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import model.BoatClubMember;


public class DetailedMemberView extends GridPane{
	
	// Constructor
	public DetailedMemberView(BoatClubMember member) {
		// Basic styling
		this.setPadding(new Insets(10,10,10,10));
		this.setVgap(20);
		
		// Container for the member's basic information
		MemberInfoPane memberView = new MemberInfoPane(member.getName(), member.getPersonalNumber(), member.getMemberID());
		GridPane.setConstraints(memberView, 0, 0);
		
		// Container for the member's boats and their information
		BoatListPane boatView = new BoatListPane(member.getBoatList(), member.getMemberID());
		GridPane.setConstraints(boatView, 0, 1);
		
		this.getChildren().addAll(memberView, boatView);
	} 
	
}
