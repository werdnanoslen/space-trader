// $codepro.audit.disable staticFieldNamingConvention, modifierOrder
/**
 * Contains the class Player
 */

package com.cs2340.spacetrader;

import java.io.Serializable;

/**
 * 
 * @author The Droids You Are Looking For
 * @version 1.0 Top level representation of player in game
 */

public class Player implements Serializable {
	/** Name of Player **/
	private final String name;

	/** Engineer Level of Player **/
	private final int engineerLevel;

	/** Pilot Level of Player **/
	private final int pilotLevel;

	/** Fighter level of player **/
	private final int fighterLevel;

	/** Trader level of player **/
	private final int traderLevel;

	/** Ship piloted by player **/
	private final Ship ship;

	/** Contract player has if any **/
	private Contract contract;

	/** Initial number of skill points allowed **/
	public static final int startingPoints = 16;

	/** Whether Player has constract **/
	public boolean hasContract = false; // $codepro.audit.disable instanceFieldVisibility

	/**
	 * Constructor for Player Class
	 * 
	 * @param name
	 * @param eLevel
	 * @param pLevel
	 * @param fLevel
	 * @param tLevel
	 * @param gold
	 * @param slots
	 */
	public Player(String name, int eLevel, int pLevel, int fLevel, int tLevel,
			int gold, int slots) {
		this.name = name;
		engineerLevel = eLevel;
		pilotLevel = pLevel;
		fighterLevel = fLevel;
		traderLevel = tLevel;
		ship = new Ship(gold, slots);
	}

	/**
	 * 
	 * @return player's ship
	 */
	public Ship getship() {
		return ship;
	}

	/**
	 * @return the player's current contract
	 */
	public Contract getContract() {
		return contract;
	}

	/**
	 * Sets the player's current contract
	 * 
	 * @param contract
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	/**
	 * returns the name of the player.
	 * @return name
	 */
	public String toString(){
		return name;
	}
}
