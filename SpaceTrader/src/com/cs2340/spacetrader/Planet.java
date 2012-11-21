// $codepro.audit.disable booleanMethodNamingConvention, numericLiterals
/**
 * Contains Planet class
 */

package com.cs2340.spacetrader;

import java.io.Serializable;


/**
 * This class encapsulates the concept of a Planet and stores vital 
 * information normally associated with a planet. It mainly has getters 
 * and setters to access the information.
 * @author The Droids You Are Looking For
 * @version 1.0
 */
public class Planet implements Serializable {
	/** 2-D coordinate of planet **/
	private final int[] coordinate;

	/** Whether market is currently being traded with **/
	private boolean marketBusy; // indicates whether a ship is presently
								// visiting a market

	/** Inventory the planet has available for trade **/
	private PlanetInventory inventory;

	/** Contract planet has available **/
	private Contract contract; // $codepro.audit.disable variableShouldBeFinal

	/** Name of the planet **/
	private final String name;

	/** Technology level of the planet **/
	private String techLevel;

	/** numeric tech level for calculating good prices **/
	private final int nTechLevel;

	/**
	 * Constructor for Planet class
	 * 
	 * @param name
	 * @param coord
	 * @param inventory
	 * @param contract
	 * @param nTechLevel
	 */
	public Planet(String name, int[] coord, PlanetInventory inventory,
			Contract contract, int nTechLevel) {
		this.marketBusy = false;
		this.coordinate = coord.clone();
		this.inventory = inventory;
		this.name = name;
		this.contract = contract;
		this.nTechLevel = nTechLevel;
		switch (nTechLevel) {
		case 0:
			techLevel = "Pre-Agriculture"; // $codepro.audit.disable
			break;
		case 1:
			this.techLevel = "Agriculture";
			break;
		case 2:
			this.techLevel = "Medieval";
			break;
		case 3:
			this.techLevel = "Renaissance";
			break;
		case 4:
			this.techLevel = "Early Industrial";
			break;
		case 5:
			this.techLevel = "Industrial";
			break;
		case 6:
			this.techLevel = "Post-Industrial";
			break;
		case 7:
			this.techLevel = "Hi-Tech";
			break;
		default:
			this.techLevel = "undefined";
			break;
		}
	}

	/**
	 * Setter method for inventory of planet
	 * 
	 * @param inventory
	 *            to set the planetInventory to
	 */
	public void setInventory(PlanetInventory inventory) {
		this.inventory = inventory;
	}

	/**
	 * getter for planet inventory
	 * 
	 * @return the inventory of the planet
	 */
	public PlanetInventory getInventory() {
		return this.inventory;
	}

	/**
	 * 
	 * @return the contract the planet has available
	 */
	public Contract getContract() {
		return this.contract;
	}

	/**
	 * 
	 * @return name of the planet
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return string tech level
	 */
	public String getTechLevel() {
		return this.techLevel;
	}

	/**
	 * 
	 * @return coordinates of planet
	 */
	public int[] getCoordinate() {
		return this.coordinate;
	}

	/**
	 * 
	 * @return numerical tech level of planet
	 */
	public int getNTechLevel() {
		return this.nTechLevel;
	}

	/**
	 * 
	 * @param bool
	 *            - state to set marketBusy to
	 */
	public void setMarketBusy(boolean bool) {
		this.marketBusy = bool;
	}

	/**
	 * 
	 * @return marketBusy
	 */
	public boolean getMarketBusy() {
		return this.marketBusy;
	}
	
	/**
	 * returns the name of the planet.
	 * @return name
	 */
	public String toString(){
		return this.name;
	}
}
