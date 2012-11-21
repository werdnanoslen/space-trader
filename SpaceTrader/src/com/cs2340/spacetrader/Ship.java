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
	private ShipInventory inventory;
	private String planetName;
	private int fuel;
	private int fuelCapacity;
	private int weaponLevel;
	private int armorLevel;

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

	public ShipInventory getInventory()
	{
		return inventory;
	}
	
	public String getPlanetName()
	{
		return planetName;
	}
	
	public int getFuel()
	{
		return fuel;
	}

	public int getFuelCapacity()
	{
		return fuelCapacity;
	}
	
	public void moveToPlanet(Planet newPlanet)
	{
		//These must be ordered in this way. deltaFuel depends on being on the planet moved from
		deltaFuel(fuelCost(newPlanet));
		planetName = newPlanet.getName();
	}
	
	public int fuelCost(Planet newPlanet)
	{
		int[] destCoords = newPlanet.getCoordinate();
		int[] currentCoords =  GameSetup.theMap.getPlanet(this.planetName).getCoordinate();
		return fuelMetric((destCoords[0]-currentCoords[0]),(destCoords[1]-currentCoords[1]));
	}

	public void deltaFuel(int fuelAmount)
	{
		int newFuel = this.fuel-fuelAmount;
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
	
	//TODO - implement buying individual units of fuel and cost for it
	public void refuel()
	{
		this.fuel = this.fuelCapacity;
	}
	
	public void upgradeWeapons()
	{
		//TODO - check if there are any upgrades level
		int cost = wepUpgradeCost();
		weaponLevel = weaponLevel +1;
		inventory.deltaMoney(-(cost));
	}
	
	public String wepUpgradeName()
	{
		return Weapons.nextWeapon(weaponLevel).getName();
	}
	
	public int wepUpgradeCost()
	{
		return Weapons.nextWeapon(weaponLevel).getPrice();
	}
	
	public void upgradeArmor()
	{
		//TODO - check if there are any upgrades level
		int cost = armorUpgradeCost();
		armorLevel = armorLevel +1;
		inventory.deltaMoney(-(cost));
	}
	
	public String armorUpgradeName()
	{
		return Armor.nextArmor(armorLevel).getName();
	}
	
	public int armorUpgradeCost()
	{
		return Armor.nextArmor(armorLevel).getPrice();
	}
	
	
	public void removeIllicitGoods()
	{
		this.inventory.removeIllicitGoods();
	}
	
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
			this.inventory.deltaMoney((val*25));
			return true;
		}
		else
		{
			this.inventory.deltaMoney(-(val*50));
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
			this.inventory.deltaMoney(-(val*50));
			return false;
		}
		
	}
	
	/**
	 * Returns an amount of fuel necessary to fly proportional to euclidean distance between planets
	 * @return integer fuel cost
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
		return Weapons.values()[weaponLevel-1].getName();
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
		return Armor.values()[armorLevel-1].getName();
	}

	private enum Weapons
	{
		BLASTER("Blaster Pistol",1),
		GAT("Gattling Guns",3),
		PHOTON("Photon Torpedos",5),
		ANTIMATTER("Antimatter",11);
		
		 public final String name;
		 public final int power;
		 
		 private Weapons(String name,final int power)
		 {
			 this.name =name;
			 this.power=power;
		 }
		 
		 public String getName()
		 {
			 return name;
		 }
		 
		 public int getPower()
		 {
			 return power;
		 }
		 
		 public int getPrice()
		 {
			 return (int) Math.pow(power, 2)*500;
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
		
		final String name;
		final int power;
 
		private Armor(String name,final int power)
		{
			 this.name =name;
			 this.power=power;
		}
 
		public String getName()
		{
			return name;
		}
		 
		public int getPower()
		{
			return power;
		}
		 
		public int getPrice()
		{
			return (int) Math.pow(power, 2)*500;
		}
		 
		private static Armor nextArmor(int armorLevel)
		{
			 return Armor.values()[armorLevel];
		}
	}
}