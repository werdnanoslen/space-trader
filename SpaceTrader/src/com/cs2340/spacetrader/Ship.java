package com.cs2340.spacetrader;

public class Ship {
	private ShipInventory inventory;
	
	

	public Ship(int gold, int nSlots)
	{
		this.inventory = new ShipInventory(gold, nSlots);
	}
}
