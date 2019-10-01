package view;

import java.util.ArrayList;

public class InputValidator {

	public boolean inputIsNumeric(String input) {
	    try {
	        Long.parseLong(input);
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public boolean validPersonalNumber(String input) {
	    try {
	        Long.parseLong(input);
	        if (input.length() != 10)
	        	return false;
	    } catch (NumberFormatException | NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public boolean validName(String input) {
        boolean nameValid = true;
        ArrayList<String> strings = new ArrayList<String>();
        
        char[] validCharacter = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Å', 'Ä', 'Ö'                   
        };
        
        ArrayList<Character> validCharacterList = new ArrayList<Character>();
        for (char ch: validCharacter) {
            validCharacterList.add(ch);
        }
        
        StringBuilder strBuilder = new StringBuilder();
        
        // Name must only consist of valid characters
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (validCharacterList.contains(currentChar)) {
                strBuilder.append(currentChar);
                if (i + 1 == input.length()) {
                    strings.add(strBuilder.toString());
                }
            } else if (currentChar == ' ' ) {
                strings.add(strBuilder.toString());
                strBuilder.setLength(0);
            } else {
                nameValid = false;
                break;
            }
        }
        
        // Name must consist of first name and last name only
        if (strings.size() != 2) 
            nameValid = false;
        
        // First and/or last name of less than two characters each is not allowed
        for (String str: strings) {
            if (str.length() < 2) 
                nameValid = false;
            
        }
        return nameValid;
    }
}
