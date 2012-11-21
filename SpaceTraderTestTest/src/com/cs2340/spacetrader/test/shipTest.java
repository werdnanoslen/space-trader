package com.cs2340.spacetrader.test;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.cs2340.spacetrader.Ship;

public class shipTest extends TestCase{

	public final void testDeltaFuel() {
		Ship aShip = new Ship(5000,10, "Walter");
		//Test Lower Bound Checking
		aShip.deltaFuel(200);
		Assert.assertEquals(0, aShip.getFuel());
		//Test normal Incrementation
		aShip.deltaFuel(-20);
		Assert.assertEquals(20, aShip.getFuel());
		aShip.deltaFuel(10);
		//Test Normal Decrementation
		Assert.assertEquals(10, aShip.getFuel());
		//Test Upper Bound Checking
		aShip.deltaFuel(-200);
		Assert.assertEquals(100, aShip.getFuel());
	}
}
