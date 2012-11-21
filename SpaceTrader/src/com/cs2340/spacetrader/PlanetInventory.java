// $codepro.audit.disable variableShouldBeFinal, packageNamingConvention
/**
 * Contains PlanetInventory
 */
package com.cs2340.spacetrader;

import java.io.Serializable;

/**
 * Inventory of a planet, contains info specific for a planet
 * @author Nikhil
 * @version 1.0
 *
 */
public class PlanetInventory extends Inventory implements Serializable {
	/** the planet's tech level */
	private int planetTechLevel;

	/**
	 * Constructor for PlanetInventory
	 * @param techLevel
	 */
	public PlanetInventory(int techLevel) {
		this.planetTechLevel = techLevel;
	}

	/**
	 * On each new visit to the planet's market, the planet's inventory gets
	 * changed
	 */
	public void regenerateInventory() {
		for (int i = 0; i < Good.getDataList().length; i++) {
			Good goodToAdd = Good.getDataList()[i];
			if (planetTechLevel > goodToAdd.getMLTP()) {
				add(goodToAdd, goodToAdd.generateAmount(planetTechLevel));
			}
		}
	}
}
