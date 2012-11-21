package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.ArrayList;

public class ShipInventory extends Inventory implements Serializable
{
	private int moneyLeft;
	private int capacityLeft;
	private int capacityMax;
	
	public ShipInventory(int initialMoney,int initialCapacity)
	{
		this.moneyLeft = initialMoney;
		this.capacityLeft = initialCapacity;
		this.capacityMax = initialCapacity;
	}
	
	public int getMoneyLeft()
	{
		return moneyLeft;
	}
	
	public int getCapacityLeft()
	{
		return capacityLeft;
	}
	
	public int getCapacityMax()
	{
		return capacityMax;
	}

	void deltaMoney(int delta)
	{
		moneyLeft += delta;
	}
	
	void deltaCapacity (int delta)
	{
		capacityLeft += delta;
	}

	/**
	 * Removes goods from inventory that fail getLegal check
	 */
	public void removeIllicitGoods() 
	{
		ArrayList<GoodData> goods = this.getListofGoods();
		for (int i = 0; i < goods.size(); i++) 
		{
			Good currentGood = goods.get(i).getGood();
			if (!currentGood.getLegal()) 
			{
				int amount = getGoodAmount(currentGood.name);
				this.remove(currentGood, amount);
			}
		}	
	}
	
	/**
	 * Determines the price of a bribe to police encounters
	 * @return price of bribe
	 */
	public int priceBribe() 
	{
		ArrayList<GoodData> goods = this.getListofGoods();
		int totalValue=0;
		for (int i = 0; i < goods.size(); i++) 
		{
			Good currentGood = goods.get(i).getGood();
			if (!currentGood.getLegal()) 
			{
				totalValue += getGoodAmount(currentGood.name)*currentGood.basePrice();
			}
		}	
		return (int) 0.5*totalValue;
	}
}
