package com.cs2340.spacetrader;

import java.util.Random;

import android.util.Log;

public class Good {
	public String name;				//name of good
	private int minProd;			//min lvl to produce (buy)
	private int minUse;				//min lvl to use (sell)
	private int idealLvl;			//base lvl
	private int basePrice;			//base price
	private int incPerLvl;			//multiplier when off base lvl
	private int var;				//random variance range
	private String incEvent;		//incease cost event
	private String cheapCond;		//decrease cost planet condition
	private String expensiveCond;	//increase cost planet condition

	public Good(String name, int minProd, int minUse, int idealLvl,
			int basePrice, int incPerLvl, int var, String incEvent,
			String cheapCond, String expensiveCond) {
		
		this.name = name;
		this.minProd = minProd;
		this.minUse = minUse;
		this.idealLvl = idealLvl;
		this.basePrice = basePrice;
		this.incPerLvl = incPerLvl;
		this.var = var;
		this.incEvent = incEvent;
		this.cheapCond = cheapCond;
		this.expensiveCond = expensiveCond;
	}
	
	public int getPrice(int planetTechLvl, String event, String cond){
		int price;		//returned buy price, sell price = 10% lower
		int eventMod = 0;
		Random rand = new Random();
		int variance = rand.nextInt(var*2) - var;
		
		if (event.equals(incEvent))
			eventMod += 200;
		
		if (cond.equals(cheapCond))
			eventMod -= 50;
		
		if (cond.equals(expensiveCond))
			eventMod += 50;
		
		price = basePrice + Math.abs(planetTechLvl-minProd)*incPerLvl + basePrice*(variance + eventMod)/100;
		
		//test code, prints to LogCat
		Log.d("BasePrice", Integer.toString(basePrice));
		Log.d("EventMod", Integer.toString(eventMod));
		Log.d("Variance", Integer.toString(variance));
		Log.d("Variance Result", Integer.toString(basePrice*(variance+ eventMod)/100));
		Log.d("TechDiff*inc", Integer.toString(Math.abs(planetTechLvl-minProd)*incPerLvl));
		
		return price;
	}
	
	public int generateAmount(int planetTechLvl, String event, String cond){
		int amount;		//returned amount of item on the planet
		int baseAmount = 50;
		Random rand = new Random();
		
		amount = rand.nextInt(baseAmount - Math.abs(idealLvl-planetTechLvl));
		
		return amount;
	}
	
	public boolean canSell(int planetTechLvl){
		//returns true if the player can sell the good at the planet
		return (planetTechLvl>=minUse);
	}
	
	public boolean canBuy(int planetTechLvl){
		//returns true if the player can buy the good at the planet
		return (planetTechLvl>=minProd);
	}
	
	public String getName(){
		return name;
	}
}