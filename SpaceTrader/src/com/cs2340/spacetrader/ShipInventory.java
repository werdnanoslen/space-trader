package com.cs2340.spacetrader;

public class ShipInventory extends Inventory {
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
