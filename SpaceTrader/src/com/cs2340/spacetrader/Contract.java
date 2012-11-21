// $codepro.audit.disable packageNamingConvention
/**
 * This class handles creating and completing contracts
 */
// $codepro.audit.disable variableShouldBeFinal
package com.cs2340.spacetrader;

import java.io.Serializable;
import java.util.Random;

import android.content.Context;
import android.content.Intent;

/**
 * This class handles generating, giving details of, and completing contracts
 * 
 * @author David
 * @version 1.1
 * 
 */
public class Contract implements Serializable {

	/**
	 * Serial ID to prevent bad saves
	 */
	private static final long serialVersionUID = 1L;

	/** destination planet to complete contract */
	private String destination;

	/** type of contract: goto, bringGoods, fightPirates */
	private String type;

	/** reward amount */
	private int reward;

	/** possible contract types */
	private final String[] possibleTypes = new String[] { "goTo",
			"fightPirate", "bringGood" };

	/** required good for bringGood type */
	private String reqGood;

	/** required amount for bringGood type */
	private int reqAmount;

	/** minimum reward for a contract */
	private static final int MINREWARD = 500;

	/** maximum reward for a contract */
	private static final int MAXREWARD = 1500;

	/**
	 * Generate the fields of the contract randomly
	 */
	public void generateContract() {
		Random random = new Random();
		int rand = random.nextInt(GameSetup.theMap.getPlanetArray().length);
		// reselect planet if it's the planet you're currently on
		while (GameSetup.theMap.getPlanetArray()[rand].getName().equals(
				GameSetup.thePlayer.getship().getPlanetName())) {
			rand = random.nextInt(GameSetup.theMap.getPlanetArray().length);
		}
		// random destination planet
		destination = GameSetup.theMap.getPlanetArray()[rand].getName();
		// random type
		type = possibleTypes[random.nextInt(possibleTypes.length)];
		if (type.equals("bringGood")) {
			// random required good from the good datalist
			reqGood = Good.getDataList()[random
					.nextInt(Good.getDataList().length)].getName();
			// required amount = random between 1 and half max capacity + 2
			reqAmount = random.nextInt(GameSetup.thePlayer.getship()
					.getInventory().getCapacityMax() >> 1) + 1;
		}
		reward = MINREWARD + random.nextInt(MAXREWARD - MINREWARD); // $codepro.audit.disable
																	// expressionValue
	}

	/**
	 * Generate a contract of the given type, for testing purposes
	 * 
	 * @param type
	 */
	public void generateContract(String type) {
		Random random = new Random();
		int rand = random.nextInt(GameSetup.theMap.getPlanetArray().length);
		// reselect planet if it's the planet you're currently on
		while (GameSetup.theMap.getPlanetArray()[rand].getName().equals(
				GameSetup.thePlayer.getship().getPlanetName())) {
			rand = random.nextInt(GameSetup.theMap.getPlanetArray().length);
		}
		int minRewardBring;
		int maxRewardBring;
		// random destination planet
		destination = GameSetup.theMap.getPlanetArray()[rand].getName();
		// random type
		this.type = type;
		if (type.equals("bringGood")) {
			// random required good from the good datalist
			int goodNum = random.nextInt(Good.getDataList().length);
			reqGood = Good.getDataList()[goodNum].getName();
			// required amount = random between 1 and half max capacity + 2
			reqAmount = random.nextInt(GameSetup.thePlayer.getship()
					.getInventory().getCapacityMax() >> 1) + 1;
			minRewardBring = Good.getDataList()[goodNum].basePrice();
			maxRewardBring = Good.getDataList()[goodNum].basePrice()
					* (reqAmount + 1);
			reward = minRewardBring
					+ random.nextInt(maxRewardBring - minRewardBring);
		} else {
			reward = MINREWARD + random.nextInt(MAXREWARD - MINREWARD); // $codepro.audit.disable expressionValue
		}
	}

	/**
	 * Checks to see if a contract is completed. If it is, trigger any events,
	 * reward the player, and set the contract flag to false
	 * 
	 * @return Returns true if contract is completed, false otherwise
	 * @param context
	 */
	public boolean canCompleteContract(Context context) {
		String curPlanet = GameSetup.thePlayer.getship().getPlanetName();
		if (destination.equals(curPlanet)) {
			if (type.equals("goTo")) {
				GameSetup.thePlayer.getship().getInventory().deltaMoney(reward);
				GameSetup.thePlayer.hasContract = false;
				return true;
			} else if (type.equals("fightPirate")) {
				Intent intent = new Intent(context, EncounterView.class);
				context.startActivity(intent);
				GameSetup.thePlayer.getship().getInventory().deltaMoney(reward);
				GameSetup.thePlayer.hasContract = false;
				return true;
			} else if (type.equals("bringGood")) {
				if (GameSetup.thePlayer.getship().getInventory()
						.getGoodAmount(reqGood) >= reqAmount) {
					GameSetup.thePlayer.getship().getInventory()
							.deltaMoney(reward);
					GameSetup.thePlayer.hasContract = false;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns a string containing the details of the contract to display to the
	 * user
	 * 
	 * @return string with the contract details
	 */
	public String getString() {
		String out = "Invalid Contract";
		if (type.equals("goTo")) {
			out = String.format("Please deliver this important message to %s."
					+ "" + " You will be paid %d credits upon your arrival.",
					destination, reward);
		} else if (type.equals("fightPirate")) {
			out = String.format(
					"Pirates are terrorizing %s! A bounty of %d credits " + ""
							+ "is offered to anyone who confronts them.",
					destination, reward);
		} else if (type.equals("bringGood")) {
			out = String.format(
					"A client on %s is offering %d credits for the safe delivery"
							+ " of %d units of %s.", destination, reward,
					reqAmount, reqGood);
		}
		return out;
	}

	/**
	 * Returns the name of the planet that completes the contract
	 * 
	 * @return string planet name
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Returns the money paid if the contract is completed
	 * 
	 * @return integer reward value
	 */
	public int getReward() {
		return reward;
	}

	/**
	 * Overridden toString, same as getString()
	 * 
	 * @return Returns a string with all relevant info
	 */
	@Override
	public String toString() {
		return getString();
	}
}