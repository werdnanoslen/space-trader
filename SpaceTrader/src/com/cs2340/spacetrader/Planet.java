package com.cs2340.spacetrader;

import java.io.Serializable;

public class Planet implements Serializable{
	
	private int[] coordinate;
	private Inventory inventory;
	private Contract contract;
	private String name;
	private String techLevel;
	
	public Planet(String name, int[] coord, Inventory inventory, Contract contract, int nTechLevel){
		this.coordinate = coord;
		this.inventory = inventory;
		this.name = name;	
		this.contract = contract;
		switch (nTechLevel) {
 				case 0: 
	    			techLevel = "Pre-Agriculture";
	    			break;
			   case 1: 
			   	techLevel = "Agriculture";
			   	break;
			   case 2: 
			   	techLevel = "Medieval";
			   	break;
			  	case 3: 
			   	techLevel = "Renaissance";
			    	break;
			  	case 4: 
			    	techLevel = "Early Industrial";
			    	break;
			  	case 5: 
			    	techLevel = "Industrial";
			    	break;
				case 6: 
			    	techLevel = "Post-Industrial";
			    	break;
				case 7: 
			    	techLevel = "Hi-Tech";
			    	break;
		}		
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
	
	public String getName() {
		return name;
	}
	
	public String getTechLevel(){
		return techLevel;
	}
	
	public void createContract(){
		//randomly generate contract here
	}
	
	public int[] getCoordinate(){
		return coordinate;
	}
}