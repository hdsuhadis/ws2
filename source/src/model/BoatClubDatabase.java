package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BoatClubDatabase {
	private String savefileAddress;
	private JSONParser parser;

	public BoatClubDatabase(String savefileAddress) {
		this.savefileAddress = savefileAddress;
		parser = new JSONParser();
	}

	// Initial import of data from json file at program start
	public ArrayList<BoatClubMember> importMembers() throws IOException{
		// ArrayList to store the members in a more easily handled way
		ArrayList<BoatClubMember> members = new ArrayList<BoatClubMember>();
		
		try {
			// Import all members from the file
			JSONArray arrayJSON = (JSONArray) parser.parse(new FileReader(new File(this.savefileAddress).getAbsolutePath()));

			// Loop run for each imported member in the json array
			for (Object o : arrayJSON) {
				JSONObject memberJSON = (JSONObject) o;
				
				// Basic member info
				String name = (String) memberJSON.get("name");
				long personalNumber = (long) memberJSON.get("personalNumber");
				String memberID = (String) memberJSON.get("memberID");

				// Creating a member with the imported data
				BoatClubMember member = new BoatClubMember(name,personalNumber,memberID);

				JSONArray boatsJSON = (JSONArray) memberJSON.get("boats");
				// Only try to import boats if the member actually has any
				if (boatsJSON != null) {
					// Loop run for each boat related to the imported member
					for (Object b : boatsJSON) {
						JSONObject boatJSON = (JSONObject) b;
	
						// Set the values from the imported data
						int length = Long.valueOf(boatJSON.get("length").toString()).intValue();
						int width = Long.valueOf(boatJSON.get("width").toString()).intValue();
						String type = (String) boatJSON.get("type");
						
						// Create a new boat from the data and add it to the member's list of boats
						Boat boat = new Boat(length,width,type);					
						member.addBoat(boat);
					}
				}
				
				members.add(member);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return members;
	}

	// Whenever a change is made to the registry, this method is called to save the data on the computer
	@SuppressWarnings("unchecked")
	public void update(ArrayList<BoatClubMember> memberList) {
		JSONArray membersJSON = new JSONArray();
		
		// Loop run for each BoatClubMember in the current member list
		for (BoatClubMember member : memberList) {
			JSONObject memberJSON = new JSONObject();

			// Save member info
			memberJSON.put("name", member.getName());
			memberJSON.put("memberID", member.getMemberID());
			memberJSON.put("personalNumber",  member.getPersonalNumber());

			ArrayList<Boat> boatList= member.getBoatList();
			JSONArray boatsJSON = new JSONArray();
			if (boatList.size() > 0) {
				// Loop run for each boat in the list of boats of the current member
				for (Boat boat : boatList) {
					JSONObject boatJSON = new JSONObject();
					// Save boat info
					boatJSON.put("length", boat.getLength());
					boatJSON.put("width", boat.getWidth());
					boatJSON.put("type", boat.getType().toString());
	
					boatsJSON.add(boatJSON);
				}
			}
			// Add the list of boats to the member
			memberJSON.put("boats", boatsJSON);

			// Add the member with it's boats to the json array
			membersJSON.add(memberJSON);
		}
		
		// Save/override data to file
		try (FileWriter fileWriter = new FileWriter(new File(this.savefileAddress))) {
			fileWriter.write(membersJSON.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
