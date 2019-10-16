package model;

import java.util.ArrayList;

public class BoatClubMember extends Member{
	private ArrayList<Boat> boatList;

	// Constructor
	public BoatClubMember(String name, long personalNumber, String memberID) {
		super(name, personalNumber, memberID);
		this.boatList = new ArrayList<Boat>();
	}
	
	// Add a new boat to the member's list of boats
	public void addBoat(Boat boat) {
		this.boatList.add(boat);
	}
	
	// Override the current list of boats with a new list
	public void setBoatList(ArrayList<Boat> boatList) {
		this.boatList = boatList;
	}
	
	// Return the current list of boats
	public ArrayList<Boat> getBoatList(){
		ArrayList<Boat> returnList = new ArrayList<Boat>();
		returnList.addAll(this.boatList);
		
		return returnList;
	}

}
