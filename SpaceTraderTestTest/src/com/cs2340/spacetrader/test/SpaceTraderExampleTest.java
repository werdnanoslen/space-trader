package com.cs2340.spacetrader.test;

import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cs2340.spacetrader.Contract;
import com.cs2340.spacetrader.GameSetup;
import com.cs2340.spacetrader.Good;
import com.cs2340.spacetrader.Map;
import com.cs2340.spacetrader.Planet;
import com.cs2340.spacetrader.Player;
import com.cs2340.spacetrader.ShipInventoryView;
import com.cs2340.spacetrader.R;

/**
 * Tests all aspects of the Contract class,
 * including three non-trivial methods
 * @author David and Andrew
 *
 */
public class SpaceTraderExampleTest extends TestCase {

	public SpaceTraderExampleTest(String name) {
		super(name);
	}

	public void testContract(){
		Contract contract = new Contract();
		GameSetup game = new GameSetup();
		game.theMap = new Map();
		game.thePlayer = new Player("Guy", 4, 4, 4, 4, 2000, 10);
		String testString;
		
		//test generating random contract
		contract.generateContract();		
		Assert.assertNotNull(contract.getString());
		Assert.assertNotNull(contract.getDestination());
		Assert.assertNotNull(contract.getReward());
		if (contract.getDestination() == game.thePlayer.getship().getPlanetName()){
			Assert.assertTrue("Cannot complete contract when on correct planet", contract.canCompleteContract(game));
		}
		else {
			Assert.assertFalse("Can complete contract when on wrong planet", contract.canCompleteContract(game));
		}
		
		//testing each random type is correct
		String type = contract.getType();
		if (type == "goTo"){
			testString = String.format("Please deliver this important message to %s."
					+ " You will be paid %d credits upon your arrival.",
					contract.getDestination(), contract.getReward());
			Assert.assertEquals(testString, contract.getString());
		}
		else if (type == "fightPirate"){
			testString = String.format("Pirates are terrorizing %s! A bounty of %d credits "
					+ "is offered to anyone who confronts them.",
					contract.getDestination(), contract.getReward());
			Assert.assertEquals(testString, contract.getString());
		}
		else if (type == "bringGood"){
			testString = String.format("A client on %s is offering %d credits for the safe delivery"
					+ " of %d units of %s.", contract.getDestination(), contract.getReward(),
			contract.getReqAmount(), contract.getReqGood());
			Assert.assertEquals(testString, contract.getString());
		}
		else{
			Assert.assertEquals("Invalid Type", contract.getString());
		}
			
		
		//example of failure
		/*if (contract.getDestination() == game.thePlayer.getship().getPlanetName()){
			Assert.assertFalse("Cannot complete contract when on correct planet", contract.canCompleteContract(game));
		}
		else {
			Assert.assertTrue("Can complete contract when on wrong planet", contract.canCompleteContract(game));
		}*/
		
		//test generating contracts of specific types
		//test goTo type
			contract.generateContract("goTo");
			Assert.assertNotNull(contract.getString());
			if (contract.getDestination() == game.thePlayer.getship().getPlanetName()){
				Assert.assertTrue("Cannot complete contract when on correct planet", contract.canCompleteContract(game));
			}
			else {
				Assert.assertFalse("Can complete contract when on wrong planet", contract.canCompleteContract(game));
			}
			
			testString = String.format("Please deliver this important message to %s."
					+ " You will be paid %d credits upon your arrival.",
					contract.getDestination(), contract.getReward());
			Assert.assertEquals(testString, contract.getString());
		
		//test fightPirate type
			contract.generateContract("fightPirate");
			Assert.assertNotNull(contract.getString());
			if (contract.getDestination() == game.thePlayer.getship().getPlanetName()){
				Assert.assertTrue("Cannot complete contract when on correct planet", contract.canCompleteContract(game));
			}
			else {
				Assert.assertFalse("Can complete contract when on wrong planet", contract.canCompleteContract(game));
			}
			
			testString = String.format("Pirates are terrorizing %s! A bounty of %d credits "
							+ "is offered to anyone who confronts them.",
							contract.getDestination(), contract.getReward());
			Assert.assertEquals(testString, contract.getString());
		
		//test bringGood type
			contract.generateContract("bringGood");
			Assert.assertNotNull(contract.getString());
			
			testString = String.format("A client on %s is offering %d credits for the safe delivery"
					+ " of %d units of %s.", contract.getDestination(), contract.getReward(),
			contract.getReqAmount(), contract.getReqGood());
			Assert.assertEquals(testString, contract.getString());
			
		//test invalid type
			contract.generateContract("blahblahblah");
			Assert.assertNotNull(contract.getString());
			Assert.assertEquals("Invalid Type", contract.getString());
			
		//test contract completion
		int prevMoney = game.thePlayer.getship().getInventory().getMoneyLeft();
		
		contract.generateContract("goTo");
		if (contract.getDestination() == game.thePlayer.getship().getPlanetName()){
			Assert.assertTrue("Cannot complete contract when on correct planet", contract.canCompleteContract(game));
		}
		else {
			Assert.assertFalse("Can complete contract when on wrong planet", contract.canCompleteContract(game));
		}
		int curMoney = game.thePlayer.getship().getInventory().getMoneyLeft();
		if (game.thePlayer.hasContract){
			Assert.assertEquals(prevMoney + contract.getReward(), curMoney);
			Assert.assertTrue(game.thePlayer.hasContract);
		}
		else{
			Assert.assertFalse(game.thePlayer.hasContract);
		}
		
		prevMoney = curMoney;
		contract.generateContract("fightPirate");
		if (contract.getDestination() == game.thePlayer.getship().getPlanetName()){
			Assert.assertTrue("Cannot complete contract when on correct planet", contract.canCompleteContract(game));
		}
		else {
			Assert.assertFalse("Can complete contract when on wrong planet", contract.canCompleteContract(game));
		}
		curMoney = game.thePlayer.getship().getInventory().getMoneyLeft();
		if (game.thePlayer.hasContract){
			Assert.assertEquals(prevMoney + contract.getReward(), curMoney);
			Assert.assertTrue(game.thePlayer.hasContract);
		}
		else{
			Assert.assertFalse(game.thePlayer.hasContract);
		}
		
		prevMoney = curMoney;
		contract.generateContract("bringGood");
		//case: at wrong planet
		Random rand = new Random();
		while (game.thePlayer.getship().getPlanetName() == contract.getDestination()){
			game.thePlayer.getship().moveToPlanet(game.theMap.getPlanet(game.theMap.planetNames[rand.nextInt(7)]));
		}
		Assert.assertFalse(contract.canCompleteContract(game));
		//case: at planet, no goods
		game.thePlayer.getship().moveToPlanet(game.theMap.getPlanet(contract.getDestination()));
		Assert.assertFalse(contract.canCompleteContract(game));
		//case: at planet, have goods, have correct amount
		game.thePlayer.getship().moveToPlanet(game.theMap.getPlanet(contract.getDestination()));
		game.thePlayer.getship().getInventory().add(game.thePlayer.getship().getInventory().getGood(contract.getReqGood()), contract.getReqAmount());
		curMoney = game.thePlayer.getship().getInventory().getMoneyLeft();
		contract.canCompleteContract(game);
		if (game.thePlayer.hasContract){
			Assert.assertEquals(prevMoney + contract.getReward(), curMoney);
			Assert.assertTrue(game.thePlayer.hasContract);
		}
		else{
			Assert.assertFalse(game.thePlayer.hasContract);
		}
		//case: at planet, have goods, not enough amount
		game.thePlayer.getship().moveToPlanet(game.theMap.getPlanet(contract.getDestination()));
		game.thePlayer.getship().getInventory().add(game.thePlayer.getship().getInventory().getGood(contract.getReqGood()), contract.getReqAmount() - 1);
		Assert.assertFalse(contract.canCompleteContract(game));
	}
	

	
}
