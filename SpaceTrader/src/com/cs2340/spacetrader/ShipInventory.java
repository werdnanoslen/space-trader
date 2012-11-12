package com.cs2340.spacetrader;

import java.io.Serializable;

public class ShipInventory extends Inventory implements Serializable {
	private int moneyLeft;
	private int capacityLeft;
	private int capacityMax;
	
	public ShipInventory(int initialMoney,int initialCapacity) {
		super();
		this.moneyLeft = initialMoney;
		this.capacityLeft = initialCapacity;
		this.capacityMax = initialCapacity;
	}
	
	public int getMoneyLeft(){
		return moneyLeft;
	}
	
	public int getCapacityLeft(){
		return capacityLeft;
	}
	
	public int getCapacityMax()
	{
		return capacityMax;
	}

	void deltaMoney(int delta){
		moneyLeft += delta;
	}
	
	void deltaCapacity (int delta){
		capacityLeft += delta;
	}

}
