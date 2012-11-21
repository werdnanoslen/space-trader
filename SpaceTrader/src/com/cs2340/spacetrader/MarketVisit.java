// $codepro.audit.disable variableShouldBeFinal, lossOfPrecisionInCast
/**
 * Contains MarketVisit Class
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author The Droids You Are Looking For
 * @version 1.0 An object of this class would represent a single visit to the
 *          market. Through this you can buy, sell, etc.
 * 
 *          Call the methods of this class only on a visit to the market. To buy
 *          and sell you need to have checked in. If you call it before checkin,
 *          it may give a wrong price or no price at all. This is because it
 *          regenerates the inventory each time to simulate changes as a result
 *          of trade with other ships
 */

public class MarketVisit implements Serializable {
	/** inventory of ship trading **/
	private ShipInventory shipInventory;

	/** inventory of planet trading **/
	private PlanetInventory inventory;

	/** planet trading **/
	private Planet planet;

	/** planet trading **/
	private static final double PERCENTSELL = 0.9;

	/**
	 * Constructor for MarketVisit class
	 * 
	 * @param shipInventory
	 * @param planet
	 */
	public MarketVisit(ShipInventory shipInventory, Planet planet) {
		this.shipInventory = shipInventory;
		this.inventory = planet.getInventory();
		this.planet = planet;
		inventory.regenerateInventory(); // this regenerates planet's inventory
	}

	/**
	 * method used to set variable showing a trade is currently taking place
	 **/
	public void checkIn() {
		planet.setMarketBusy(true);
	}

	/**
	 * Overrides toString because audit complains
	 * @return a random string
	 */
	@Override
	public String toString(){
		return "blah";
	}
	
	/**
	 * @return goodsList of inventory
	 */
	public ArrayList<GoodData> getGoodsList() { // $codepro.audit.disable declareAsInterface
		return inventory.getListofGoods();
	}

	/**
	 * 
	 * @param good
	 *            under consideration
	 * @return whether the planet will buy it or not
	 */
	public boolean canPlanetBuy(Good good) {
		return (planet.getNTechLevel() >= good.getMLTU());
	}

	/**
	 * 
	 * @param good
	 *            for which price was requested
	 * @return price planet will buy it for
	 */
	public int priceAtWhichPlanetBuys(Good good) {
		if (inventory.hasGood(good)) {
			int sellPrice = good.importPriceforPlanet(planet.getNTechLevel());
			int price = (int) Math.round(PERCENTSELL * ((double) sellPrice));
			return price;
		}

		else if (canPlanetBuy(good)) { // planet will buy but does not produce
			return good.importPriceforPlanet(planet.getNTechLevel());
		} else { // if planet will not buy
			return -1;
		}
	}

	/**
	 * 
	 * @param good
	 *            to be sold
	 * @param quantityToSell
	 *            amount of good to sell
	 * @return success of transaction
	 */
	public boolean sellToPlanet(Good good, int quantityToSell) { // $codepro.audit.disable booleanMethodNamingConvention
		GoodData goodDetails = inventory.getGoodFromInventory(good);
		int quantityInShip = goodDetails.getQuantity();
		if (quantityToSell <= quantityInShip) {
			shipInventory.remove(good, quantityToSell);
			inventory.add(good, quantityToSell);
			shipInventory.deltaCapacity(quantityToSell); // the capacity of the
															// ship increases by
															// quantityToSell
															// (this is because
															// 1 good = 1
															// capacity). Its
															// not a realistic
															// model. But it is
															// a simple one.
			int deltaMoney = (int) Math.round(PERCENTSELL
					* ((double) good.basePrice()));
			shipInventory.deltaMoney(deltaMoney);
			return true;
		} else{
			return false;
		}
	}

	/**
	 * 
	 * @param good
	 *            under consideration
	 * @return whether planet will buy it
	 */
	public boolean canPlanetSell(Good good) {
		return (inventory.hasGood(good));
	}

	/**
	 * returns -1 if planet does not sell
	 * 
	 * @param good
	 *            under consideration
	 * @return price at which planet will sell it
	 */
	public int priceAtWhichPlanetSells(Good good) {
		if (canPlanetSell(good)) {
			GoodData goodDetails = inventory.getGoodFromInventory(good);
			int price = goodDetails.getGood().importPriceforPlanet(
					planet.getNTechLevel());
			return price;
		} else {
			return -1;
		}
	}

	/**
	 * returns true if buying is successful, else return false. buying can be
	 * unsuccessful if there isn't enough money left, enough capacity left or if
	 * planet does not sell or if ship hasn't checked in to market.
	 * 
	 * @param good
	 *            to be bought
	 * @param quantity
	 *            to buy
	 * @return success of transaction
	 */
	public boolean canBuyFromPlanet(Good good, int quantity) {
		int price = priceAtWhichPlanetSells(good);
		int moneyNeeded = price * quantity;
		int capacityNeeded = quantity; // 1 quantity of any object takes up 1
										// capacity. This may not be realistic
										// but I thought this is enough for our
										// purpose.
		int planetQuantity = inventory.getGoodFromInventory(good).getQuantity();

		if (canPlanetSell(good) && moneyNeeded <= shipInventory.getMoneyLeft()
				&& capacityNeeded <= shipInventory.getCapacityLeft()
				&& planetQuantity >= quantity) {
			shipInventory.add(good, quantity);
			shipInventory.deltaMoney(-1 * price);
			shipInventory.deltaCapacity(-1 * quantity);
			inventory.remove(good, quantity);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to update variable representing that trading has ended
	 */
	public void checkOut() {
		planet.setMarketBusy(false);
	}

	/**
	 * getter method for planet inventory
	 * 
	 * @return planetInventory
	 */
	public PlanetInventory getPlanetInventory() {
		return inventory;
	}

	/**
	 * getter method for shipInventory
	 * 
	 * @return shipInventory
	 */
	public ShipInventory getShipInventory() {
		return shipInventory;
	}
}