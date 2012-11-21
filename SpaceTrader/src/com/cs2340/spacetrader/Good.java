package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.Random;

//import android.util.Log;

public class Good implements Serializable
{
	public String name; // name of good
	private int minProd; // min lvl to produce (buy)
	private int minUse; // min lvl to use (sell)
	private int idealLvl; // base lvl
	private int basePrice; // base price
	private int incPerLvl; // multiplier when off base lvl
	private int var; // random variance range
	private int price;
	private boolean legal;

	public Good(String name, int minProd, int minUse, int idealLvl,
			int basePrice, int incPerLvl, int var, boolean legal)
	{
		this.name = name;
		this.minProd = minProd;
		this.minUse = minUse;
		this.idealLvl = idealLvl;
		this.basePrice = basePrice;
		this.incPerLvl = incPerLvl;
		this.var = var;
		this.legal = legal;
	}
	
	/**
	 * Uses Planet's tech level to change the variance in prices
	 * @param planetTechLvl
	 */
	public void resetPrice(int planetTechLvl)
	{
		Random rand = new Random();
		int variance = rand.nextInt(var * 2) - var;

		price = basePrice + Math.abs(planetTechLvl - minProd) * incPerLvl
				+ basePrice * (variance) / 100;

		// test code, prints to LogCat
		// Log.d("BasePrice", Integer.toString(basePrice));
		// Log.d("EventMod", Integer.toString(eventMod)); // eventMod feature
		// has been removed.
		// Log.d("Variance", Integer.toString(variance));
		// Log.d("Variance Result", Integer.toString(basePrice*(variance+
		// eventMod)/100));
		// Log.d("TechDiff*inc",
		// Integer.toString(Math.abs(planetTechLvl-minProd)*incPerLvl));
	}

	/**
	 * Gets the price of a good
	 * @return price
	 */
	public int price()
	{
		return price;
	}
	
	/**
	 * Gets the base price of a good, without variance
	 * @return base price
	 */
	public int basePrice()
	{
		return basePrice;
	}
	
	/**
	 * Determines the final price of good
	 * @param planetTechLvl
	 * @return final price
	 */
	public int importPriceforPlanet(int planetTechLvl)
	{
		//Log.i("Price of " + name, "" + basePrice + Math.abs(planetTechLvl - minProd) * incPerLvl + basePrice*var/100);
		return basePrice + Math.abs(planetTechLvl - minProd) * incPerLvl + basePrice*var/100;
	}
	
	/**
	 * Generates the number of goods available
	 * @param planetTechLvl
	 * @return amount of goods
	 */
	public int generateAmount(int planetTechLvl)
	{
		resetPrice(planetTechLvl);
		int amount; // returned amount of item on the planet
		int baseAmount = 50;
		Random rand = new Random();

		amount = rand.nextInt(baseAmount - Math.abs(idealLvl - planetTechLvl));

		return amount;
	}

	/**
	 * Determines whether the planet can sell a good
	 * @param planetTechLvl
	 * @return returns true if the player can sell the good at the planet
	 */
	public boolean canSell(int planetTechLvl)
	{
		return (planetTechLvl >= minUse);
	}

	/**
	 * Determiens whether the planet can buy a good
	 * @param planetTechLvl
	 * @return returns true if the player can buy the good at the planet
	 */
	public boolean canBuy(int planetTechLvl)
	{
		return (planetTechLvl >= minProd);
	}

	/**
	 * Gets the name of an item
	 * @return item name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets minimum level to produce
	 * @return MLTP
	 */
	public int getMLTP()
	{
		return minProd;
	}
	
	/**
	 * Gets the Minimum Level To Use
	 * @return Minimum Level To Use
	 */
	public int getMLTU()
	{
		return minProd;
	}
	
	/**
	 * Gets the legal status of a good
	 * @return legal status
	 */
	public boolean getLegal()
	{
		return legal;
	}
	

	/**
	* This method is serves like a database that holds the values of all the goods. 	 
	*/
	public static Good[] getDataList()
	{
		Good[] dataList;

		dataList = new Good[10];
		dataList[0] = new Good("Water", 0, 0, 2, 30, 3, 4, true);
		dataList[1] = new Good("Furs", 0, 0, 0, 250, 10, 10, true);
		dataList[2] = new Good("Food", 1, 0, 1, 100, 5, 5, true);
		dataList[3] = new Good("Ore", 2, 2, 3, 350, 20, 10, true);
		dataList[4] = new Good("Games", 3, 1, 6, 250, -10, 5, true);
		dataList[5] = new Good("Firearms", 3, 1, 5, 1250, -75, 100, false);
		dataList[6] = new Good("Medicine", 4, 1, 6, 650, -20, 10, true);
		dataList[7] = new Good("Machines", 4, 3, 5, 900, -30, 5, true);
		dataList[8] = new Good("Narcotics", 5, 0, 5, 3500, -125, 150, false);
		dataList[9] = new Good("Robots", 6, 4, 7, 5000, -150, 100, true);

		return dataList;
	}
}