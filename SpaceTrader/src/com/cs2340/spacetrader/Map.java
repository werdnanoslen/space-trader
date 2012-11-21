// $codepro.audit.disable variableShouldBeFinal
/**
 * Contains Map class
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import java.io.Serializable;
import java.util.Random;

/**
 * Class contains all non-player objects in one singleton
 * 
 * @author Nikhil
 * @version 1.0
 */
public class Map implements Serializable {
	/**
	 * Default Serialization ID
	 */
	private static final long serialVersionUID = 1L;

	/** Array that contains all planets */
	private Planet[] planetArray;

	/** Maximum x/y value for coordinates */
	private static final int XYMAX = 150;

	/** Number of planets */
	private static final int NUMPLANET = 8;

	/** List of planet names*/
	private final String[] planetNames = { "Kashyyk", "Tatooine", "Naboo",
			"Alderon", "Xeon", "Zion", "Mordor", "Krypton" };

	/**
	 * Constructs the map by randomly generating a list of planets
	 */
	public Map() {
		planetArray = generatePlanetArray();
	}

	/**
	 * Generates all the planets on the map
	 * 
	 * @return array of planet objects
	 */
	public final Planet[] generatePlanetArray() {
		Random rand = new Random();
		PlanetInventory inventory = new PlanetInventory(NUMPLANET);
		Contract contract = new Contract();
		planetArray = new Planet[NUMPLANET];

		int[] techLevels = new int[NUMPLANET];
		for (int i = 0; i < NUMPLANET; i++) {
			techLevels[i] = i;
		}

		for (int i = 0; i < planetArray.length; i++) {
			int[] coord = { rand.nextInt(XYMAX), rand.nextInt(XYMAX) };
			planetArray[i] = new Planet(planetNames[i], coord, inventory,
					contract, techLevels[i]);
		}

		return planetArray;
	}

	/**
	 * Returns the entire planet array
	 * 
	 * @return Returns an array of planets
	 */
	public Planet[] getPlanetArray() {
		return planetArray;
	}

	/**
	 * Returns a planet with the specified name
	 * 
	 * @param name
	 * @return Returns a Planet object
	 */
	public Planet getPlanet(String name) {
		int numel = planetArray.length;
		for (int i = 0; i < numel; i++) {
			if (planetArray[i].getName().equals(name)) {
				return planetArray[i];
			}
		}
		return null;
	}

	/**
	 * Returns number of planets
	 * 
	 * @return List of planet names
	 */
	public String toString() {
		return "" + planetArray.length;
	}
}