package com.cs2340.spacetrader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TradeView extends Activity {

	private ShipInventory sInventory;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        
        //slots on display to display current balances
        TextView cashDisplay = (TextView) findViewById(R.id.trade_cash);
        TextView capacityDisplay = (TextView) findViewById(R.id.trade_capacity); 

        //initialize values in slots
        cashDisplay.setText('$' + sInventory.getMoneyLeft());
        capacityDisplay.setText(sInventory.getCapacityLeft());
    }
}
