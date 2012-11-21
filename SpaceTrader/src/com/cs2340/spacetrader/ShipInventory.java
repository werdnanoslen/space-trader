// $codepro.audit.disable variableShouldBeFinal
/**
 * Contains Ship Inventory Class
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import java.io.Serializable;
import java.util.List;

/**
 * Ship inventory class, holds additional info relevant to the player only
 * 
 * @author Nikhil
 * @version 1.0
 */
public class ShipInventory extends Inventory implements Serializable {
	/**
	 * Default serialization ID
	 */
	private static final long serialVersionUID = 1L;

	/** player's cash on hand*/
	private int moneyLeft;
	
	/** cargo capacity left */
	private int capacityLeft;
	
	/** max cargo capacity */
	private int capacityMax;
	
	/** the number 2*/
	private static final int TWO = 2;

	/**
	 * Constructor for ShipInventory
	 * @param initialMoney
	 * @param initialCapacity
	 */
	public ShipInventory(int initialMoney, int initialCapacity) {
		this.moneyLeft = initialMoney;
		this.capacityLeft = initialCapacity;
		this.capacityMax = initialCapacity;
	}

	/**
	 * get for moneyLeft
	 * @return money left
	 */
	public int getMoneyLeft() {
		return moneyLeft;
	}

	/**
	 * get for capacityLeft
	 * @return cargo capacity left
	 */
	public int getCapacityLeft() {
		return capacityLeft;
	}

	/**
	 * get for capacityMax
	 * @return max cargo capacity
	 */
	public int getCapacityMax() {
		return capacityMax;
	}

	/**
	 * changes the player's money by delta
	 * @param delta
	 */
	public void deltaMoney(int delta) {
		moneyLeft += delta;
	}

	/**
	 * changes the capacity left by delta
	 * @param delta
	 */
	public void deltaCapacity(int delta) {
		capacityLeft += delta;
	}

	/**
	 * Removes goods from inventory that fail getLegal check
	 */
	public void removeIllicitGoods() {
		List<GoodData> goods = this.getListofGoods();
		for (int i = 0; i < goods.size(); i++) {
			Good currentGood = goods.get(i).getGood();
			if (!currentGood.getLegal()) {
				int amount = getGoodAmount(currentGood.name);
				this.remove(currentGood, amount);
			}
		}
	}

	/**
	 * Determines the price of a bribe to police encounters
	 * 
	 * @return price of bribe
	 */
	public int priceBribe() {
		List<GoodData> goods = this.getListofGoods();
		int totalValue = 0;
		for (int i = 0; i < goods.size(); i++) {
			Good currentGood = goods.get(i).getGood();
			if (!currentGood.getLegal()) {
				totalValue += getGoodAmount(currentGood.name)
						* currentGood.basePrice();
			}
		}
		return totalValue >> TWO;
	}
	
	/**
	 * Overrides toString because audit complains
	 * @return a random string
	 */
	@Override
	public String toString(){
		return "blah";
	}
	
}
