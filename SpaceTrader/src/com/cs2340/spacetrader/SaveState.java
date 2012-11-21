package com.cs2340.spacetrader;

import java.io.Serializable;

/**
 * This class encapsulates the information that is to be written to memory when a game is saved. Save and load mechanisms use an object of
 * this class to hold the information that is to be saved/loaded.
 * 
 * @author The Droids You Are Looking For
 * @version 1.0
 *
 */

public class SaveState implements Serializable {
	/**
	 * the game's map
	 */
	private Map map;
	
	/**
	 * the game's player
	 */
	private Player player;

	/**
	 * Constructor takes in a player and map object. These are the objects that hold the state of the game.
	 * @param player
	 * @param map
	 */
	public SaveState(Player player, Map map) {
		this.map = map;
		this.player = player;
	}

	/**
	 * Returns the map of the game.
	 * @return map
	 */
	public Map getMap() {
		return this.map;
	}

	/**
	 * sets the map of the game that is to be saved/loaded.
	 * @param map
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * returns the player of the game.
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * sets the player of the game that is to be saved/loaded.
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}
