package com.cs2340.spacetrader;

import java.io.Serializable;

public class Planet implements Serializable{
	
	private int[] coordinate;
	private Inventory inventory;
	private Contract contract;
	
	public Planet(int[] coord, Inventory inventory, Contract contract){
		this.coordinate = coord;
		this.inventory = inventory;
		this.contract = contract;
	}
	
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public Contract getContract(){
		return contract;
	}
	
	public void createContract(){
		//randomly generate contract here
	}
}