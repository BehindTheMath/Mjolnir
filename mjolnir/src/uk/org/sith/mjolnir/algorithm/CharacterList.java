package uk.org.sith.mjolnir.algorithm;

import java.util.Arrays;
import java.util.List;

public enum CharacterList {

	LOWER_CASE("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"), UPPER_CASE(
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"), DIGITS(
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"), SYMBOLS(" ",
			"!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-",
			".", "/", ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^",
			"_", "`", "{", "|", "}", "~");

	private String[] array;

	private CharacterList(String... array) {
		this.array = array;
	}

	public String[] getAsArray() {
		return array;
	}

	public List<String> getAsList() {
		return Arrays.asList(array);
	}

}
