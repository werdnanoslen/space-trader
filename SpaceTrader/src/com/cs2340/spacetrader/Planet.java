package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.*;

public class Planet implements Serializable {

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
	
	public MarketVisit visitMarket(ShipInventory shipInventory) { // takes in the inventory of the ship that wants to visit the market.
		return new MarketVisit(shipInventory);
	}
	
	/*
	 * An object of this class would represent a single visit to the market. Through this you can buy, sell, etc.
	 * 
	 * Call the methods of this class only on a visit to the market. To buy and sell you need to have checked in. 
	 * If you call it before checkin, it may give a wrong price or no price at all. This is because it regenerates 
	 * the inventory each time to simulate changes as a result of trade with other ships
	 */
	
	public class MarketVisit {
		private ShipInventory shipInventory;
		
		public MarketVisit(ShipInventory shipInventory) {
			this.shipInventory = shipInventory;
			inventory.regenerateInventory(); // this regenerates planet's inventory
		}
		
		public void checkIn(){
			marketBusy = true;
		}
		
		public ArrayList<GoodData> getGoodsList(){
			return inventory.getListofGoods();
		}
		
		
		public boolean willPlanetBuy(Good good){ 
			return (getNTechLevel() >= good.getMLTU());
		}
		
		/*
		 * returns -1 if planet will not buy.
		 */
		public int priceAtWhichPlanetBuys(Good good){
			if (inventory.contains(good)) {
				GoodData goodDetails = inventory.getGoodFromInventory(good);
				int originalPrice = goodDetails.getGood().price();
				int price = (int)(originalPrice - originalPrice/10.0);
				return price;
			}
			
			else if (willPlanetBuy(good) == true) // planet will buy but does not produce
				return good.importPriceforPlanet(nTechLevel);
			else // if planet will not buy
				return -1;
		}
		/*
		 *  returns false if planet will not buy a good or ship is trying to sell more than you have or you haven't checked in to the market. if sale is complete, it returns true.
		 */
		public boolean sellToPlanet(Good good, int quantityToSell){
			if (marketBusy == true) {
				if (willPlanetBuy(good) == false)
					return false;
				else if (inventory.contains(good)) {
					GoodData goodDetails = inventory.getGoodFromInventory(good);
					int quantityInShip = goodDetails.getQuantity();
					if (quantityToSell <= quantityInShip){
						shipInventory.remove(good, quantityToSell);
						inventory.add(good, quantityToSell);
						shipInventory.deltaCapacity(quantityToSell); // the capacity of the ship increases by quantityToSell (this is because 1 good = 1 capacity). Its not a realistic model. But it is a simple one.
						int deltaMoney = (int) (good.price() - good.price()/10.0);
						shipInventory.deltaMoney(deltaMoney);
						return true;
					}
					else // this is executed if ship is trying to sell more than it has.
						return false;
				}
				
				else if (inventory.contains(good) == false){
					GoodData goodDetails = inventory.getGoodFromInventory(good);
					int quantityInShip = goodDetails.getQuantity();
					if (willPlanetBuy(good) && quantityToSell <= quantityInShip){
						shipInventory.remove(good, quantityToSell);
						inventory.add(good, quantityToSell);
						shipInventory.deltaCapacity(quantityToSell); // the capacity of the ship increases by quantityToSell (this is because 1 good = 1 capacity). Its not a realistic model. But it is a simple one.
						int deltaMoney = good.importPriceforPlanet(nTechLevel); // importPriceforPlanet() is used because the price here is different from getPrice() which the planet will have. 
						shipInventory.deltaMoney(deltaMoney);
						return true;
					} 
					else // this is executed if ship is trying to sell more than it has or ship does not have the good at all.
						return false;
					
				}
				else
					return false;
			}
			else 
				return false;
		}
		
		public boolean willPlanetSell(Good good){
			return (inventory.contains(good));
		}
		
		/*
		 * returns -1 if planet does not sell
		 */
		public int priceAtWhichPlanetSells(Good good){
			if (willPlanetSell(good) == true){
				GoodData goodDetails = inventory.getGoodFromInventory(good);
				int price = goodDetails.getGood().price();
				return price;
			}
			else
				return -1;
		}
		
		/*
		 * returns true if buying is successful, else return false. buying can be unsuccessful if there isn't enough money left, enough capacity left 
		 * or if planet does not sell or if ship hasn't checked in to market.
		 */
		public boolean buyFromPlanet(Good good, int quantity){
			if (marketBusy = false) {
				int price = priceAtWhichPlanetSells(good);
				int moneyNeeded = price*quantity;
				int capacityNeeded = quantity; // 1 quantity of any object takes up 1 capacity. This may not be realistic but I thought this is enough for our purpose.
				
				if (willPlanetSell(good) == true && moneyNeeded <= shipInventory.getMoneyLeft() && capacityNeeded <= shipInventory.getCapacityLeft()){
					shipInventory.add(good, quantity);
					shipInventory.deltaMoney(-1*price);
					shipInventory.deltaMoney(-1*quantity);
					return true;
				}
				
				else
					return false;
			}
			else
				return false;
		}
	
		public void checkOut(){
			marketBusy = false;
		}
		
		
	}
}



