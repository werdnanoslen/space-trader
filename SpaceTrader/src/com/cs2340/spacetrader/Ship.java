package com.cs2340.spacetrader;

import java.io.Serializable;

//planet
public class Ship implements Serializable {
	private ShipInventory inventory;
	private String planetName;
	private int fuel;
	private int fuelCapacity;
	private int weaponLevel;
	private int shieldLevel;

	public Ship(int gold, int nSlots)
	{
		this.inventory = new ShipInventory(gold, nSlots);
		this.planetName = GameSetup.theMap.getPlanetArray()[0].getName();
		//TODO think of a centralized place to put all these kinds of numbers.
		this.fuel = 100;
		this.fuelCapacity = 100;
		this.weaponLevel=1;
		this.shieldLevel=1;
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
			this.fuel=0;
		else if (newFuel > this.fuelCapacity)
			this.fuel = this.fuelCapacity;
		else
			this.fuel = newFuel;
	}
	
	
	public void upgradeWeapons(){
		//TODO - check if there are any upgrades level
		weaponLevel = weaponLevel +1;
		inventory.deltaMoney(-(wepUpgradeCost()));
	}
	
	public String wepUpgradeName() {
		return Weapons.nextWeapon(weaponLevel).getName();
	}
	
	public int wepUpgradeCost(){
		return Weapons.nextWeapon(weaponLevel).getPrice();
	}
	
	public void upgradeShields(){
		//TODO - check if there are any upgrades level
		shieldLevel = shieldLevel +1;
		inventory.deltaMoney(-(shieldUpgradeCost()));
	}
	
	public String shieldUpgradeName() {
		return Shields.nextShield(shieldLevel).getName();
	}
	
	public int shieldUpgradeCost(){
		return Shields.nextShield(shieldLevel).getPrice();
	}
	
	private int fuelMetric(int deltaX, int deltaY)
	{
		int squaredCost = (int) (Math.pow((int)deltaX,2)+ Math.pow((int)deltaY, 2));
		return (int) Math.round(Math.sqrt(squaredCost));
	}

	enum Weapons {
		Blaster("Blaster Pistol",1),
		Gat("Gattling Guns",3),
		Photon("Photon Torpedos",5),
		Antimatter("Antimatter",11);
		
		 final String name;
		 final int power;
		 
		 Weapons(String name,final int power){
			 this.name =name;
			 this.power=power;
		 }
		 
		 String getName() {
			 return name;
		 }
		 
		 int getPower() {
			 return power;
		 }
		 
		 int getPrice() {
			 return (int) Math.pow(power, 2)*500;
		 }
		 
		 static Weapons nextWeapon(int WeaponLevel) {
		 	return Weapons.values()[WeaponLevel];
		 }
	}
	
	enum Shields {
		Trashcans("Old Trashcans",1),
		Steel("Steel Plates",4),
		Force("Force Field",5),
		Temporal("Temporal Evasion",20);
		
		 final String name;
		 final int power;
		 
		 Shields(String name,final int power){
			 this.name =name;
			 this.power=power;
		 }
		 
		 String getName() {
			 return name;
		 }
		 
		 int getPower() {
			 return power;
		 }
		 
		 int getPrice() {
			 return (int) Math.pow(power, 2)*500;
		 }
		 
		 static Shields nextShield(int ShieldLevel) {
		 	return Shields.values()[ShieldLevel];
		 }
	}
}