package model;

public class Member {
	private String name;
	private long personalNumber;
	private String memberID;
	
	// Constructor
	public Member(String name, long personalNumber, String memberID) {
		this.name = name;
		this.personalNumber = personalNumber;
		this.memberID = memberID;
	}
	
	// Set name
	public void setName(String name) {
		this.name = name;
	}
	
	// Return the set name
	public String getName() {
		return this.name;
	}
	
	// Set personal number
	public void setPersonalNumber(long number) {
		this.personalNumber = number;
	}
	
	// Return the set personal number
	public long getPersonalNumber() {
		return this.personalNumber;
	}
	
	// Set memberID
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	// Return the set memberID
	public String getMemberID() {
		return this.memberID;
	}
}
