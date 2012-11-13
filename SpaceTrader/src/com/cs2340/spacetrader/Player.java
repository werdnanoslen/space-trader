package com.cs2340.spacetrader;

import java.io.Serializable;


public class Player implements Serializable{
	//TODO add other attributes
	private String name;
	private int engineerLevel;
	private int pilotLevel;
	private int fighterLevel;
	private int traderLevel;
	private Ship ship;
	public static int startingPoints = 16;
	public boolean hasContract = false;
	
	
	public Player(String name, int eLevel, int pLevel, int fLevel, int tLevel, int gold, int slots)
	{	
		this.name=name;
		this.engineerLevel=eLevel; 
		this.pilotLevel =pLevel;
		this.fighterLevel=fLevel;
		this.traderLevel=tLevel;
		this.ship = new Ship(gold, slots);
	}
	
	Ship getship()
	{
		return ship;
	}
}
