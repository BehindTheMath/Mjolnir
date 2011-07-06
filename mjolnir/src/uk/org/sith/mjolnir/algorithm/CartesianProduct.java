package uk.org.sith.mjolnir.algorithm;

/**
 * Taken from http://icodesnip.com/snippet/java/cartesian-product-dreamincode
 * 
 */
public class CartesianProduct<T> {
	T[] arrayA;
	T[] arrayB;

	public CartesianProduct(T[] arrayA, T[] arrayB) {
		this.arrayA = arrayA;
		this.arrayB = arrayB;
	}

	public Pair<T>[] cartesianProductAxB() {
		Pair<T>[] product = new Pair[arrayA.length * arrayB.length];

		int amount = 0;
		for (int i = 0; i < arrayA.length; i++) {
			for (int j = 0; j < arrayB.length; j++) {
				product[amount++] = new Pair<T>(arrayA[i], arrayB[j]);
			}
		}
		return product;
	}

	public Pair<T>[] cartesianProductBxA() {
		Pair<T>[] product = new Pair[arrayA.length * arrayB.length];

		int amount = 0;
		for (int i = 0; i < arrayB.length; i++) {
			for (int j = 0; j < arrayA.length; j++) {
				product[amount++] = new Pair<T>(arrayB[i], arrayA[j]);
			}
		}
		return product;
	}
}
