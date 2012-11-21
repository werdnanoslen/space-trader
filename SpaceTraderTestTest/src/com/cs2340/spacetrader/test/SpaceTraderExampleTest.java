package com.cs2340.spacetrader.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cs2340.spacetrader.ShipInventoryView;
import com.cs2340.spacetrader.R;

public class SpaceTraderExampleTest extends
		ActivityInstrumentationTestCase2<ShipInventoryView> {

	public SpaceTraderExampleTest(){
		super("com.cs2340.spacetrader.ShipInventoryView", ShipInventoryView.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ShipInventoryView activity = getActivity();
		TextView cashField = (TextView) activity.findViewById(R.id.ship_cash);
	}

}
