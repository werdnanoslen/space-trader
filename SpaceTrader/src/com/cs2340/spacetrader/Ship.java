package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.Random;

//import android.content.Context;
//import android.widget.Toast;
/**
 * @class Ship - Represents the player's ship inside the game, holds inventory and fuel
 * 
 */
public class Ship implements Serializable
{
	/**items the ship is carrying **/
	private ShipInventory inventory;
	
	/**planet the ship is currently on **/
	private String planetName;
	
	/**amount of fuel the ship is currently carrying **/
	private int fuel;
	
	/**maximum units of fuel the ship can carry **/
	private int fuelCapacity;
	
	/**provides reference into enum armour types**/
	private int weaponLevel;
	
	/**provides reference into enum armor types **/
	private int armorLevel;

	/**constructor for ship class
	 *@param gold - initial amount of money player starts with 
	 *@param nSlots - number of cargo slots the ship has
	 * **/
	public Ship(int gold, int nSlots)
	{
		this.inventory = new ShipInventory(gold, nSlots);
		this.planetName = GameSetup.theMap.getPlanetArray()[0].getName();
		//TODO think of a centralized place to put all these kinds of numbers.
		this.fuel = 100;
		this.fuelCapacity = 100;
		this.weaponLevel=1;
		this.armorLevel=1;
	}

	/**getter method for the ship's inventory**/
	public ShipInventory getInventory()
	{
		return inventory;
	}
	
	/**getter method for the ship's name
	 * @return planetName
	 **/
	public String getPlanetName()
	{
		return planetName;
	}
	
	/**getter method for the ship's fuel
	 * @return ship's fuel
	 **/
	public int getFuel()
	{
		return fuel;
	}

	/** getter method for the ship's fuel capacity
	 *@return ship's fuel capacity
	 **/
	public int getFuelCapacity()
	{
		return fuelCapacity;
	}
	
	/**method for moving between planets - takes care of reducing fuel by amount used  
	 * @param newPlanet - planet to be moved to
	**/
	public void moveToPlanet(Planet newPlanet)
	{
		//These must be ordered in this way. deltaFuel depends on being on the planet moved from
		deltaFuel(fuelCost(newPlanet));
		planetName = newPlanet.getName();
	}
	
	/** method for calculating fuel cost of moving to another planet
	 * @param newPlanet - planet to be moved to
	 * @return units of fuel needed to move
	 **/
	public int fuelCost(Planet newPlanet)
	{
		int[] destCoords = newPlanet.getCoordinate();
		int[] currentCoords =  GameSetup.theMap.getPlanet(this.planetName).getCoordinate();
		return fuelMetric((destCoords[0] - currentCoords[0]),(destCoords[1] - currentCoords[1]));
	}

	/**method for adding or subtracting from current fuel level
	 * @param fuelAmount - amount of fuel to add, can be negative
	**/
	public void deltaFuel(int fuelAmount)
	{
		int newFuel = this.fuel - fuelAmount;
		if (newFuel < 0)
		{
			this.fuel=0;
		}
		else if (newFuel > this.fuelCapacity)
		{
			this.fuel = this.fuelCapacity;
		}
		else
		{
			this.fuel = newFuel;
		}
	}
	
	/**resets fuel of ship to full, i.e. equal to fuelCapacity**/
	public void refuel()
	{
		this.fuel = this.fuelCapacity;
	}
	
	/** increments weapongLevel and subtracts cost of upgrade**/
	public void upgradeWeapons()
	{
		//TODO - check if there are any upgrades level
		int cost = wepUpgradeCost();
		weaponLevel = weaponLevel + 1;
		inventory.deltaMoney(-(cost));
	}
	
	/**@return the name of the next level's weapon
	 **/
	public String wepUpgradeName()
	{
		return Weapons.nextWeapon(weaponLevel).getName();
	}
	
	/**@return the cost of upgrading weapons **/
	public int wepUpgradeCost()
	{
		return Weapons.nextWeapon(weaponLevel).getPrice();
	}
	
	/**Increments armor level**/
	public void upgradeArmor()
	{
		//TODO - check if there are any upgrades level
		int cost = armorUpgradeCost();
		armorLevel = armorLevel + 1;
		inventory.deltaMoney(-(cost));
	}
	
	/**@return the name of the next level's armour
	 **/
	public String armorUpgradeName()
	{
		return Armor.nextArmor(armorLevel).getName();
	}

	/**@return the cost of upgrading armour **/
	public int armorUpgradeCost()
	{
		return Armor.nextArmor(armorLevel).getPrice();
	}
	
	/**removes all illicit goods on ship -for use in police encounter **/
	public void removeIllicitGoods()
	{
		this.inventory.removeIllicitGoods();
	}
	
	/**determines a reasonable amount to charge for a bribe to the police
	 * @return bribe amount
	 **/
	public int priceBribe()
	{
		return this.inventory.priceBribe();
	}
	
	public void bribePolice()
	{
		this.inventory.deltaMoney(this.priceBribe());
	}
	
	/**
	 * Carries out logic behind attempting to fight a pirate
	 * @return boolean player won fight
	 */
	public boolean fight()
	{
		
		Random generator = new Random();
		int val = generator.nextInt(100);
		if (val > 49)
		{
			this.inventory.deltaMoney((val * 25));
			return true;
		}
		else
		{
			this.inventory.deltaMoney(-(val * 50));
			return false;
		}
	}
	
	/**
	 * Carries out logic behind attempting to flee a fight
	 * @return boolean whether fleeing was successful
	 */
	public boolean flee()
	{
		Random generator = new Random();
		int val = generator.nextInt(100);
		if (val > 20)
		{
			return true;
		}
		else 
		{
			this.inventory.deltaMoney(-(val * 50));
			return false;
		}
		
	}
	
	/**
	 * Returns an amount of fuel necessary to fly proportional to euclidean distance between planets
	 * @return integer fuel cost
	 * @param deltaX - distance in x direction between planets
	 * @param deltaY - distance in y direction between planets
	 */
	private int fuelMetric(int deltaX, int deltaY)
	{
		int squaredCost = (int) (Math.pow(deltaX,2) + Math.pow(deltaY, 2));
		return (int) Math.round(Math.sqrt(squaredCost));
	}
	
	/**
	 * Returns the level of weaponry the ship has
	 * @return integer weapon level
	 */
	public int getWeaponLevel()
	{
		return weaponLevel;
	}
	
	/**
	 * Returns the name of the current weapon the ship has
	 * @return string weapon name
	 */
	public String getWeaponName()
	{
		return Weapons.values()[weaponLevel - 1].getName();
	}
	
	/**
	 * Returns the level of armor the ship has
	 * @return integer armor level
	 */
	public int getArmorLevel()
	{
		return armorLevel;
	}
	
	/**
	 * Returns the name of the current armor the ship has
	 * @return string armor name
	 */
	public String getArmorName()
	{
		return Armor.values()[armorLevel - 1].getName();
	}

	private enum Weapons
	{
		BLASTER("Blaster Pistol",1),
		GAT("Gattling Guns",3),
		PHOTON("Photon Torpedos",5),
		ANTIMATTER("Antimatter",11);
		
		/**name of the weapon**/
		 public final String name;
		 
		 /**power of the weapon**/
		 public final int power;
		 
		 /**Constructor for Weapons enum
		  * @param name of weapon
		  * @param power of weapon
		  **/
		 private Weapons(String name,final int power)
		 {
			 this.name =name;
			 this.power=power;
		 }
		 
		 /**
		  * @return name of weapon
		  * **/
		 public String getName()
		 {
			 return name;
		 }
		 
		 /** 
		  * @return power of weapon
		  **/
		 public int getPower()
		 {
			 return power;
		 }
		 
		 /**
		  * @return returns price of next weapon upgrade
		  * **/
		 public int getPrice()
		 {
			 return (int) Math.pow(power, 2) * 500;
		 }
		 
		 public static Weapons nextWeapon(int weaponLevel)
		 {
		 	return Weapons.values()[weaponLevel];
		 }
	}
	
	private enum Armor
	{
		TRASHCANS("Old Trashcans",1),
		STEEL("Steel Plates",4),
		FORCE("Force Field",5),
		TEMPORAL("Temporal Evasion",20);
		
		/**name of the armour**/
		 public final String name;
		 
		 /**power of the armor**/
		 public final int power;		
 
		 /**Constructor method for enum Armor type 
		  * @param name - armor name
		  * @param power - power of armour
		 **/
		private Armor(String name, final int power)
		{
			 this.name =name;
			 this.power=power;
		}
 
		/**
		 * @return name of the armor
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * @return power of the armor
		 */
		public int getPower()
		{
			return power;
		}
		 
		/**
		 * @return price of next armor upgrade
		 */
		public int getPrice()
		{
			return (int) Math.pow(power, 2) * 500;
		}
		 
		/**
		 * 
		 * @param armorLevel - armor level information desired for
		 * @return - armor object for given level
		 */
		public static Armor nextArmor(int armorLevel)
		{
			 return Armor.values()[armorLevel];
		}
	}
}