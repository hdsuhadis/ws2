package model;

public class Member {
	private String name;
	private long personalNumber;
	private String memberID;
	
	public Member(String name, long personalNumber, String memberID) {
		this.name = name;
		this.personalNumber = personalNumber;
		this.memberID = memberID;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setPersonalNumber(long number) {
		this.personalNumber = number;
	}
	
	public long getPersonalNumber() {
		return this.personalNumber;
	}
	
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	public String getMemberID() {
		return this.memberID;
	}
}
