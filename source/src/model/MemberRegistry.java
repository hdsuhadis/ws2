package model;

import java.io.IOException;
import java.util.ArrayList;

public class MemberRegistry {
	private BoatClubDatabase database;
	private ArrayList<BoatClubMember> memberList;
	
	public MemberRegistry() {
		memberList = new ArrayList<BoatClubMember>();
		database = new BoatClubDatabase("src/members.json");
		
	}
	
	public BoatClubMember addMember(String name, long personalNumber) {
		importMembersFromDatabase();
		BoatClubMember newMember = new BoatClubMember(name, personalNumber, generateID(name));
		
		memberList.add(newMember);
		database.update(this.memberList);
		return newMember;
	}
	

	public void updateMember(String name, long personalNumber, String memberID) {
		importMembersFromDatabase();
		
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getMemberID().equals(memberID)) {
				BoatClubMember updatedMember = memberList.get(i);
				updatedMember.setName(name);
				updatedMember.setPersonalNumber(personalNumber);
				memberList.set(i, updatedMember);
				this.database.update(this.memberList);
			}
		}
		
		
	}
	public void updateBoatsForMember(String memberID, ArrayList<Boat> boats) {
		importMembersFromDatabase();
		
		for (int i = 0; i < memberList.size(); i++) {
			if (memberList.get(i).getMemberID().equals(memberID)) {
				BoatClubMember updatedMember = memberList.get(i);
				updatedMember.setBoatList(boats);
				memberList.set(i, updatedMember);
				this.database.update(this.memberList);
			}
		}
	}
	
	public void deleteMember(BoatClubMember member) {
		this.memberList.remove(member);
		this.database.update(this.memberList);
	}
	
	
	public ArrayList<BoatClubMember> getMemberList(){
		return this.memberList;
	}
	
	
	// Generates a unique memberID based the member's name
	private String generateID(String name) {
		String id = "";
		for (int i = 1; true; i++) {
			id += name.substring(0, 2).toLowerCase();	// Add first two chars of first name
			id += name.substring(name.indexOf(' ') + 1, name.indexOf(' ') + 3).toLowerCase();	// Add first two chars of surname
			id += i;// Add a digit to make the ID unique
			
			boolean memberIDIsUnique = true;
			for (BoatClubMember member: this.memberList) {
				if (member.getMemberID().equals(id)) {
					memberIDIsUnique = false;
					break;
				}
			}
			
			if (!memberIDIsUnique) // If the ID already exists
				id = ""; // Clear the ID for the next iteration
			else	// Otherwise, return the generated ID
				break;
		}
		
		return id;
	}
	
	public void importMembersFromDatabase() {
		try {
			this.memberList = this.database.importMembers();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
