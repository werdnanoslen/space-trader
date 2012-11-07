package com.cs2340.spacetrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TradeView extends Activity
{
	private ShipInventory sInventory;
	private TextView cashDisplay;
	private TextView capacityDisplay;
	private ListView TradeList;
	private Planet planet;
	private MarketVisit market;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        
        planet = GameSetup.theMap.getPlanet(GameSetup.thePlayer.getship().getPlanetName());
        sInventory = GameSetup.thePlayer.getship().getInventory();
        market = new MarketVisit(sInventory, planet);
        //market.checkIn();
        
        //slots on display to display current balances
        cashDisplay = (TextView) findViewById(R.id.trade_cash);
        capacityDisplay = (TextView) findViewById(R.id.trade_capacity);


        //initialize values in slots
        refreshDisplays();
    }
    
    public class GoodInfo{
    	public String name;
    	public int buyPrice;
    	public int planetAmount;
    	public int sellPrice;
    	public int shipAmount;
    	
    	public GoodInfo(){
    		super();
    		
    	}
    	
    	public GoodInfo(String name, int buyPrice, int planetAmount, int sellPrice, int shipAmount){
    		super();
    		this.name = name;
        	this.buyPrice = buyPrice;
        	this.planetAmount = planetAmount;
        	this.sellPrice = sellPrice;
        	this.shipAmount = shipAmount;
    	}
    }
    
    private void refreshDisplays()
    {
		cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
		capacityDisplay.setText(String.valueOf(sInventory.getCapacityLeft()));
		//extract good info
        Good good = planet.getInventory().getGood("Water");
        GoodInfo[] gi = new GoodInfo[]{
        		new GoodInfo("Water", market.priceAtWhichPlanetSells(good), planet.getInventory().getGoodAmount("Water"), market.priceAtWhichPlanetBuys(good), sInventory.getGoodAmount("Water")),
        		new GoodInfo("Furs", market.priceAtWhichPlanetSells(planet.getInventory().getGood("Furs")), planet.getInventory().getGoodAmount("Furs"), market.priceAtWhichPlanetBuys(planet.getInventory().getGood("Furs")), sInventory.getGoodAmount("Furs"))
        };
        
        
        //set the adapter
        TradeAdapter adapter = new TradeAdapter(this, R.layout.market_row, gi);
        //display the list view
        TradeList = (ListView)findViewById(R.id.trade_list);
        
        TradeList.setAdapter(adapter);
    }
    
    public void buy(View view)
    {
    	//quick weedouts
    	if (market.getShipInventory().getMoneyLeft() <= 0) return;
    	if (market.getShipInventory().getCapacityLeft() <= 0) return;
    	Good theGood = market.getPlanetInventory().getGood("Water");
    	if (market.getPlanetInventory().getGoodFromInventory(theGood).getQuantity() <= 0) return;

    	market.buyFromPlanet(market.getPlanetInventory().getGood("Water"), 1);
    	
    	refreshDisplays();
    }
    
    public void sell(View view)
    {
    	//quick weedout
    	if (sInventory.getCapacityLeft() >= sInventory.getCapacityMax()) return;
    	
    	market.sellToPlanet(market.getPlanetInventory().getGood("Water"), 1);
    	
    	refreshDisplays();
    }
    
    public void gotoPlanet(View view)
    {
    	Intent intent = new Intent(TradeView.this, PlanetView.class);
        startActivity(intent);
    }
}