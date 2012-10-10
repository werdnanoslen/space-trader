package com.cs2340.spacetrader;

public class Player {
	private String name;
	private int engineerLevel;
	private int pilotLevel;
	private int fighterLevel;
	private int traderLevel;
	public static int startingPoints = 16;
	
	
	public Player(String name, int eLevel, int pLevel, int fLevel, int tLevel)
	{	
		this.name=name;
		this.engineerLevel=eLevel; 
		this.pilotLevel =pLevel;
		this.fighterLevel=fLevel;
		this.traderLevel=tLevel;
	}
}
