package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * An object of this class would represent a single visit to the market. Through this you can buy, sell, etc.
 * 
 * Call the methods of this class only on a visit to the market. To buy and sell you need to have checked in. 
 * If you call it before checkin, it may give a wrong price or no price at all. This is because it regenerates 
 * the inventory each time to simulate changes as a result of trade with other ships
 */

public class MarketVisit implements Serializable 
{
		private ShipInventory shipInventory;
		private PlanetInventory inventory;
		private Planet planet;
		
		public MarketVisit(ShipInventory shipInventory, Planet planet)
		{
			this.shipInventory = shipInventory;
			this.inventory = planet.getInventory();
			this.planet = planet;
			inventory.regenerateInventory(); // this regenerates planet's inventory
		}
		
		public void checkIn()
		{
			planet.setMarketBusy(true);
		}
		
		public ArrayList<GoodData> getGoodsList()
		{
			return inventory.getListofGoods();
		}
		
		
		public boolean willPlanetBuy(Good good)
		{ 
			return (planet.getNTechLevel() >= good.getMLTU());
		}
		
		/*
		 * returns -1 if planet will not buy.
		 */
		public int priceAtWhichPlanetBuys(Good good)
		{
			if (inventory.contains(good)) 
			{
				int sellPrice = good.importPriceforPlanet(planet.getNTechLevel());
				int price = (int)(0.9*((double)sellPrice));
				return price;
			}
			
			else if (willPlanetBuy(good))
			{ // planet will buy but does not produce
				return good.importPriceforPlanet(planet.getNTechLevel());
			}
			else
			{ // if planet will not buy
				return -1;
			}
		}
		/*
		 *  returns false if planet will not buy a good or ship is trying to sell more than you have or you haven't checked in to the market. if sale is complete, it returns true.
		 */
		public boolean sellToPlanet(Good good, int quantityToSell)
		{
			GoodData goodDetails = inventory.getGoodFromInventory(good);
			int quantityInShip = goodDetails.getQuantity();
			if (quantityToSell <= quantityInShip)
			{
				shipInventory.remove(good, quantityToSell);
				inventory.add(good, quantityToSell);
				shipInventory.deltaCapacity(quantityToSell); // the capacity of the ship increases by quantityToSell (this is because 1 good = 1 capacity). Its not a realistic model. But it is a simple one.
				int deltaMoney = (int) (0.9*((double) good.basePrice()));
				shipInventory.deltaMoney(deltaMoney);
				return true;
			}
			else // this is executed if ship is trying to sell more than it has.
			{	
				return false;
			}
		}
		
		public boolean willPlanetSell(Good good)
		{
			return (inventory.contains(good));
		}
		
		/*
		 * returns -1 if planet does not sell
		 */
		public int priceAtWhichPlanetSells(Good good)
		{
			if (willPlanetSell(good))
			{
				GoodData goodDetails = inventory.getGoodFromInventory(good);
				int price = goodDetails.getGood().importPriceforPlanet(planet.getNTechLevel());
				return price;
			}
			else
			{
				return -1;
			}
		}
		
		/*
		 * returns true if buying is successful, else return false. buying can be unsuccessful if there isn't enough money left, enough capacity left 
		 * or if planet does not sell or if ship hasn't checked in to market.
		 */
		public boolean buyFromPlanet(Good good, int quantity)
		{
			int price = priceAtWhichPlanetSells(good);
			int moneyNeeded = price*quantity;
			int capacityNeeded = quantity; // 1 quantity of any object takes up 1 capacity. This may not be realistic but I thought this is enough for our purpose.
			int planetQuantity = inventory.getGoodFromInventory(good).getQuantity();
			
			if (willPlanetSell(good) && moneyNeeded <= shipInventory.getMoneyLeft() && capacityNeeded <= shipInventory.getCapacityLeft() && planetQuantity >= quantity)
			{
				shipInventory.add(good, quantity);
				shipInventory.deltaMoney(-1*price);
				shipInventory.deltaCapacity(-1*quantity);
				inventory.remove(good, quantity);
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public void checkOut()
		{
			planet.setMarketBusy(false);
		}
		
		public PlanetInventory getPlanetInventory()
		{
			return inventory;
		}
		
		public ShipInventory getShipInventory()
		{
			return shipInventory;
		}
	}