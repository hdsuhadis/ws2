package model;

import java.io.IOException;
import java.util.ArrayList;

public class MemberRegistry {
	private BoatClubDatabase database;
	private ArrayList<BoatClubMember> memberList;
	
	// Constructor
	public MemberRegistry() {
		this.database = new BoatClubDatabase("src/members.json");
		this.memberList = new ArrayList<BoatClubMember>();
		this.importMembersFromDatabase(); // Import current member list
	}
	
	// Add a new member to the registry and return it
	public BoatClubMember addMember(String name, long personalNumber) {
		this.importMembersFromDatabase(); // Import current member list before editing
		
		BoatClubMember newMember = new BoatClubMember(name, personalNumber, generateID(name));
		
		// Add the new member and update the database
		memberList.add(newMember);
		database.update(this.memberList);
		
		return newMember;
	}
	
	// Update information about a specific member
	public void updateMember(String name, long personalNumber, String memberID) {
		this.importMembersFromDatabase(); // Import current member list before editing
		
		for (int i = 0; i < memberList.size(); i++) {
			// Search for the requested member
			if (memberList.get(i).getMemberID().equals(memberID)) {
				BoatClubMember updatedMember = memberList.get(i);
				
				// Update the member's information
				updatedMember.setName(name);
				updatedMember.setPersonalNumber(personalNumber);
				
				// Save the updated member and update the database
				memberList.set(i, updatedMember);
				this.database.update(this.memberList);
			}
		}
		
		
	}
	
	// Update the list of boats for a specific member
	public void updateBoatsForMember(String memberID, ArrayList<Boat> boats) {
		this.importMembersFromDatabase(); // Import current member list before editing
		
		for (int i = 0; i < memberList.size(); i++) {
			// Search for the requested member
			if (memberList.get(i).getMemberID().equals(memberID)) {
				BoatClubMember updatedMember = memberList.get(i);
				
				// Override the current boat list with the new one
				updatedMember.setBoatList(boats);
				
				// Save the updated member and update the database
				memberList.set(i, updatedMember);
				this.database.update(this.memberList);
			}
		}
	}
	
	// Delete a member
	public void deleteMember(BoatClubMember member) {
		this.memberList.remove(member);
		this.database.update(this.memberList);
	}
	
	// Return the current member list
	public ArrayList<BoatClubMember> getMemberList(){
		ArrayList<BoatClubMember> returnList = new ArrayList<BoatClubMember>();
		returnList.addAll(this.memberList);
		
		return returnList;
	}
	
	// Generate a unique memberID based the member's name
	private String generateID(String name) {
		String id = "";
		for (int i = 1; true; i++) {
			// Add first two chars of first name
			id += name.substring(0, 2).toLowerCase();
			// Add first two chars of surname
			id += name.substring(name.indexOf(' ') + 1, name.indexOf(' ') + 3).toLowerCase();	
			// Add a digit to make the ID unique
			id += i;
			
			// Check if the generated ID already exists
			boolean memberIDIsUnique = true;
			for (BoatClubMember member: this.memberList) {
				if (member.getMemberID().equals(id)) {
					memberIDIsUnique = false;
					break;
				}
			}
			
			if (!memberIDIsUnique) 	// If the ID already exists
				id = ""; 			// Clear the ID for the next iteration
			else					// Otherwise, return the generated ID
				break;
		}
		
		return id;
	}
	
	// Imports data from physical file
	public void importMembersFromDatabase() {
		try {
			this.memberList = this.database.importMembers();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
