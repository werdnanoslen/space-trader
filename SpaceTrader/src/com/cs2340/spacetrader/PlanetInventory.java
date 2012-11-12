package com.cs2340.spacetrader;

import java.io.Serializable;

public class PlanetInventory extends Inventory implements Serializable
{
	private int planetTechLevel;
	
	public PlanetInventory(int techLevel)
	{
		super();
		this.planetTechLevel = techLevel;
	}
	
	// On each new visit to the planet's market, the planet's inventory gets changed 
	// is different as a result of trade with other ships.
	public void regenerateInventory()
	{ 
		for (int i = 0; i < Good.getDataList().length; i++)
		{
			Good goodToAdd = Good.getDataList()[i];
			if (planetTechLevel > goodToAdd.getMLTP()) 
			{
				add(goodToAdd, goodToAdd.generateAmount(planetTechLevel));
			}
		}
	}
}
