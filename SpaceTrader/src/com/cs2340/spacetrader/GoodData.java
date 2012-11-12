package com.cs2340.spacetrader;

import java.io.Serializable;
/*
 * This class helps to encapsulate the concept of having a certain quantity of a specific good.
 * This class has just two data fields, Good and the amount of it held in an inventory. 
 * The Inventory class holds a list of GoodData objects and not Good objects.
 */

public class GoodData implements Serializable {
	private Good good;
	private int quantity;

	public GoodData(Good good, int quantity) {
		this.good = good;
		this.quantity = quantity;
	}

	public Good getGood() {
		return good;
	}

	public int getQuantity() {
		return quantity;
	}

	public void increaseQuantityBy(int delta) {
		quantity = quantity + delta;
	}

	public void decreaseQuantityBy(int delta) { // with this implementation,
												// quantity can be negative.
												// This will be handled while
												// making a transaction.
		quantity = quantity - delta;
	}
}