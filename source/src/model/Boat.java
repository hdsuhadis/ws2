package model;

public class Boat {
	private Type type;
	private int length;
	private int width;

	// Enum used for handling boat types, to avoid String usage
	public enum Type {
		Sailboat, Motorsailer, Powerboat, Kayak, Canoe, Other 
	}

	// Standard constructor
	public Boat(int length, int width, Type type) {
		this.length = length;
		this.width = width;
		this.type = type;
	}
	
	// Constructor with string-based types, used when importing data from a file
	public Boat(int length, int width, String typeString) {
		this.length = length;
		this.width = width;
		this.setType(typeString);
	}

	// Set boat type
	public void setType(Type type) {
		this.type = type;
	}
	
	// Set boat type with a String, only used when importing data from file
	public void setType(String typeString) {
		boolean typeMatched = false;
		
		// Check if the String matches any of the available types
		for (Type type : Type.values()) {
			if (typeString.equals(type.toString())) {
				this.type = type;
				typeMatched = true;
			}
		}
		
		// If incorrect data is given, a default value is set
		if (!typeMatched)
			this.type = Type.Other;
	}

	// Return the set boat type
	public Type getType() {
		return this.type;
	}
	
	// Set boat length
	public void setLength(int length) {
		this.length = length;
	}

	// Return the set boat length
	public int getLength() {
		return this.length;
	}

	// Set boat width
	public void setWidth(int width) {
		this.width = width;
	}

	// Return the set boat width
	public int getWidth() {
		return this.width;
	}
}
