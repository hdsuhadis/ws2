package model;

import java.util.ArrayList;

public class BoatClubMember extends Member{
	private ArrayList<Boat> registeredBoats;

	public BoatClubMember(String name, long personalNumber, String memberID) {
		super(name, personalNumber, memberID);
		registeredBoats = new ArrayList<Boat>();
	}
	
	public void addBoat(Boat boat) {
		registeredBoats.add(boat);
	}
	
	public void setBoatList(ArrayList<Boat> boatList) {
		this.registeredBoats = boatList;
	}
	
	public ArrayList<Boat> getBoatList(){
		return registeredBoats;
	}

}
