package view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import model.BoatClubMember;


public class BoatClubMemberView extends GridPane{
	
	public BoatClubMemberView(BoatClubMember member) {
		this.setPadding(new Insets(10,10,10,10));
		this.setVgap(20);
		
		MemberView memberView = new MemberView(member.getName(), member.getPersonalNumber(), member.getMemberID());
		GridPane.setConstraints(memberView, 0, 0);
		
		BoatListView boatView = new BoatListView(member.getBoatList(), member.getMemberID());
		GridPane.setConstraints(boatView, 0, 1);
		
		this.getChildren().addAll(memberView, boatView);
	} 
	
}
