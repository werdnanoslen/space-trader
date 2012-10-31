package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.*;

public class ShipInventory extends Inventory {
	private int moneyLeft;
	private int capacityLeft;
	
	public ShipInventory(int initialMoney,int initialCapacity) {
		super();
		this.moneyLeft = initialMoney;
		this.capacityLeft = initialCapacity;
	}
	
	public int getMoneyLeft(){
		return moneyLeft;
	}
	
	public int getCapacityLeft(){
		return capacityLeft;
	}

	void deltaMoney(int delta){
		moneyLeft += delta;
	}
	
	void deltaCapacity (int delta){
		capacityLeft += delta;
	}

}
