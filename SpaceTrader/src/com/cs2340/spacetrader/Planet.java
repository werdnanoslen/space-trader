package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.*;

public class Planet implements Serializable{

	private int[] coordinate;
	private boolean marketBusy; // indicates whether a ship is presently visiting a market 
	private PlanetInventory inventory;
	private Contract contract;
	private String name;
	private String techLevel;
	private int nTechLevel; // numeric tech level for calculating good prices

	public Planet(String name, int[] coord, PlanetInventory inventory,
			Contract contract, int nTechLevel) {
		this.marketBusy = false;
		this.coordinate = coord;
		this.inventory = inventory;
		this.name = name;
		this.contract = contract;
		this.nTechLevel = nTechLevel;
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

	public void setInventory(PlanetInventory inventory) {
		this.inventory = inventory;
	}

	public PlanetInventory getInventory() {
		return inventory;
	}

	public Contract getContract() {
		return contract;
	}

	public String getName() {
		return name;
	}

	public String getTechLevel() {
		return techLevel;
	}
	
	public int getTechLevelInt(){
		return nTechLevel;
	}

	public void createContract() {
		// randomly generate contract here
	}

	public int[] getCoordinate() {
		return coordinate;
	}

	public int getNTechLevel() {
		return nTechLevel;
	}
	
	public void setMarketBusy(boolean bool){
		marketBusy = bool;
	}
	
	public boolean getMarketBusy(){
		return marketBusy;
	}
	
	
}



