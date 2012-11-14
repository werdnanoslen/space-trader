package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Contract implements Serializable{
	private String destination;
	private String type;
	private int reward;
	private String[] possibleTypes = new String[]{
			"goTo",
			"fightPirate",
			"bringGood"
		};
	private String reqGood;
	private int reqAmount;
	
	public Contract(){
		//generateContract();
	}
	
	/**
	 * Generate the fields of the contract randomly
	 */
	public void generateContract(){
		Random r = new Random();
		int rand = r.nextInt(GameSetup.theMap.getPlanetArray().length);
		//reselect planet if it's the planet you're currently on
		while (GameSetup.theMap.getPlanetArray()[rand].getName() == GameSetup.thePlayer.getship().getPlanetName()){
			rand = r.nextInt(GameSetup.theMap.getPlanetArray().length);
		}
		int minReward = 500;
		int maxReward = 1500;
		//random destination planet
		destination = GameSetup.theMap.getPlanetArray()[rand].getName();
		//random type
		type = possibleTypes[r.nextInt(possibleTypes.length)];
		if (type == "bringGood"){
			//random required good from the good datalist
			reqGood = Good.getDataList()[r.nextInt(Good.getDataList().length)].getName();
			//required amount = random between 1 and half max capacity + 2
			reqAmount = r.nextInt(GameSetup.thePlayer.getship().getInventory().getCapacityMax()/2) + 2;
		}
		reward = minReward + r.nextInt(maxReward-minReward);
	}
	
	/**
	 * Generate a contract of the given type, for testing purposes
	 * @param type
	 */
	public void generateContract(String type){
		Random r = new Random();
		int rand = r.nextInt(GameSetup.theMap.getPlanetArray().length);
		//reselect planet if it's the planet you're currently on
		while (GameSetup.theMap.getPlanetArray()[rand].getName() == GameSetup.thePlayer.getship().getPlanetName()){
			rand = r.nextInt(GameSetup.theMap.getPlanetArray().length);
		}
		int minReward = 500;
		int maxReward = 2000;
		//random destination planet
		destination = GameSetup.theMap.getPlanetArray()[rand].getName();
		//random type
		this.type = type;
		if (type == "bringGood"){
			//random required good from the good datalist
			int goodNum = r.nextInt(Good.getDataList().length);
			reqGood = Good.getDataList()[goodNum].getName();
			//required amount = random between 1 and half max capacity + 2
			reqAmount = r.nextInt(GameSetup.thePlayer.getship().getInventory().getCapacityMax()/2) + 2;
			minReward = Good.getDataList()[goodNum].basePrice();
			maxReward = Good.getDataList()[goodNum].basePrice() * (reqAmount + 2);
		}
		reward = minReward + r.nextInt(maxReward-minReward);
	}
	
	/**
	 * Checks to see if a contract is completed. If it is, trigger any events, reward the player,
	 * and set the contract flag to false
	 * @return Returns true if contract is completed, false otherwise
	 */
	public boolean checkContract(Context context){
		String curPlanet = GameSetup.thePlayer.getship().getPlanetName();
		if (destination == curPlanet){
			if (type == "goTo"){
				GameSetup.thePlayer.getship().getInventory().deltaMoney(reward);
				GameSetup.thePlayer.hasContract = false;
				return true;
			}
			else if (type == "fightPirate"){
				Intent intent = new Intent(context, EncounterView.class);
	        	context.startActivity(intent);
				GameSetup.thePlayer.getship().getInventory().deltaMoney(reward);
				GameSetup.thePlayer.hasContract = false;
				return true;
			}
			else if (type == "bringGood"){
					if (GameSetup.thePlayer.getship().getInventory().getGoodAmount(reqGood) >= reqAmount){
						GameSetup.thePlayer.getship().getInventory().deltaMoney(reward);
						GameSetup.thePlayer.hasContract = false;
						return true;
					}
			}
		}
		return false;
	}
	
	/**
	 * Add money to player inventory, reset states
	 */
	private void contractComplete(){
		GameSetup.thePlayer.getship().getInventory().deltaMoney(reward);
		GameSetup.thePlayer.hasContract = false;
	}
	
	/**
	 * Returns a string containing the details of the contract to display to the user
	 * @return string with the contract details
	 */
	public String getString(){
		String out = "Invalid Contract";
		if (type == "goTo"){
			out = String.format("Please deliver this important message to %s. You will be paid %d credits upon your arrival.", destination, reward);
		}			
		else if (type == "fightPirate"){
			out = String.format("Pirates are terrorizing %s! A bounty of %d credits is offered to anyone who confronts them.", destination, reward);
		}
		else if (type == "bringGood"){
			out = String.format("A client on %s is offering %d credits for the safe delivery of %d units of %s.", destination, reward, reqAmount, reqGood);
		}
		return out;
	}
	
	/**
	 * Returns the name of the planet that completes the contract
	 * @return string planet name
	 */
	public String getDestination(){
		return destination;
	}
	
	/**
	 * Returns the money paid if the contract is completed
	 * @return integer reward value
	 */
	public int getReward(){
		return reward;
	}
}