package uk.org.sith.mjolnir;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DictionaryPasswordGenerator implements Attacker {

	private static final int MAX_NUMBER_OF_OTHER_CHARS = 2;

	private Iterator<Pair<String>> iterator;

	private List<Pair<String>> pairs;

	private boolean append = true;

	private String initial;

	public DictionaryPasswordGenerator(String initial) {

		this.initial = initial;

		init();

	}

	private void init() {
		List<Pair<String>> pairs = new LinkedList<Pair<String>>();

		CartesianProduct<String> cartesian = new CartesianProduct<String>(
				CharacterList.DIGITS.getAsArray(),
				CharacterList.SYMBOLS.getAsArray());

		Collections.addAll(pairs, cartesian.cartesianProductAxB());
		Collections.addAll(pairs, cartesian.cartesianProductBxA());

		cartesian = new CartesianProduct<String>(
				CharacterList.DIGITS.getAsArray(),
				CharacterList.DIGITS.getAsArray());
		Collections.addAll(pairs, cartesian.cartesianProductAxB());
		Collections.addAll(pairs, cartesian.cartesianProductBxA());

		cartesian = new CartesianProduct<String>(
				CharacterList.SYMBOLS.getAsArray(),
				CharacterList.SYMBOLS.getAsArray());
		Collections.addAll(pairs, cartesian.cartesianProductAxB());
		Collections.addAll(pairs, cartesian.cartesianProductBxA());

	}

	@Override
	public String attack() {

		for (Pair<String> pair : pairs) {
			String first = pair.getFirst();
			String second = pair.getSecond();
		}

		return null;
	}

	public String generate() {

		return getNext(initial);

	}

	private String getNext(String initial) {

		String next = "";
		if ((!iterator.hasNext()) && append) {
			append = false;
			init();
		}
		Pair<String> pair = iterator.next();

		String first = pair.getFirst();
		String second = pair.getSecond();

		if (append) {
			next = append(initial, first, second);
		} else {
			next = prepend(initial, first, second);
		}

		return next;

	}

	private String append(String initial, String first, String second) {

		String next = initial;
		next += first;
		next += second;

		return next;

	}

	private String prepend(String initial, String first, String second) {

		String next = first;
		next += second;
		next += initial;

		return next;

	}

}
