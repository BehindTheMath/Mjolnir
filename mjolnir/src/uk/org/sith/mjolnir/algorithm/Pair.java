package uk.org.sith.mjolnir.algorithm;

/**
 * Taken from http://icodesnip.com/snippet/java/cartesian-product-dreamincode
 * 
 */
public class Pair<T> // Support class
{
	private T _first;
	private T _second;

	public Pair(T first, T second) {
		_first = first;
		_second = second;
	}

	public T getFirst() {
		return _first;
	}

	public T getSecond() {
		return _second;
	}

	@Override
	public String toString() {
		return "<" + _first + ", " + _second + ">";
	}

	public boolean equals(Pair other) {
		return other._first.equals(this._first)
				&& other._second.equals(this._second);
	}
}