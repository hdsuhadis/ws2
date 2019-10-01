package model;

public class Boat {
	private Type type;
	private int length;
	private int width;

	public enum Type{
		Sailboat, Motorsailer, Powerboat, Kayak, Canoe, Other 
	}

	public Boat(int length, int width, Type type) {
		this.length = length;
		this.width = width;
		this.type = type;
	}
	public Boat(int length, int width, String typeString) {
		this.length = length;
		this.width = width;
		this.setType(typeString);
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setType(String typeString) {
		for (Type type : Type.values()) {
			if (typeString.equals(type.toString()))
				this.type = type;
		}
	}

	public Type getType() {
		return this.type;
	}
	
	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return this.width;
	}



}
