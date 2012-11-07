package com.cs2340.spacetrader;

import java.util.ArrayList;

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
        
        //extract good info PROBLEMS HERE        
        GoodInfo[] gi = getGoodInfoArray();
        
        //set the adapter
        TradeAdapter adapter = new TradeAdapter(this, R.layout.market_row, gi);
        //display the list view
        TradeList = (ListView)findViewById(R.id.trade_list);
        
        TradeList.setAdapter(adapter);
        
    }
    
    private GoodInfo[] getGoodInfoArray(){
    	GoodData[] pGoods = market.getPlanetInventory().getArray();
        GoodData[] sGoods = market.getShipInventory().getArray();
        ArrayList<GoodInfo> list = new ArrayList<GoodInfo>();
        for (int i = 0; i < pGoods.length; i++){
        	list.add(new GoodInfo(pGoods[i].getGood().getName()));
        	list.get(i).setPlanetAmount(pGoods[i].getQuantity());
        	list.get(i).setSellPrice((int)0.9*pGoods[i].getGood().basePrice());
        	list.get(i).setBuyPrice(pGoods[i].getGood().price());
        }
        for (int i = 0; i < sGoods.length; i++){
        	//String sName = sGoods[i].getGood().getName();
        	String sName = "Water";
        	boolean present = false;
        	int index = 0;
        	for (int j = 0; j < list.size(); j++){
        		GoodInfo good = list.get(i);
        		if (good.name == sName)
        			present = true;
        			index = j;
        	}
        	if (present){
        		
        		list.get(index).setShipAmount(sGoods[i].getQuantity());
        	}
        	else{
        		list.add(new GoodInfo(sGoods[i].getGood().getName()));
            	list.get(list.size()-1).setPlanetAmount(sGoods[i].getQuantity());
            	list.get(list.size()-1).setSellPrice((int)0.9*sGoods[i].getGood().basePrice());
            	list.get(list.size()-1).setBuyPrice(sGoods[i].getGood().price());
            	list.get(list.size()-1).setShipAmount(sGoods[index].getQuantity());
        	}
        
        }
        
        GoodInfo returnArray [] = new GoodInfo[1];
        return list.toArray(returnArray);
        
    }
    
    public class GoodInfo{
    	public String name;
    	public int buyPrice;
    	public int planetAmount;
    	public int sellPrice;
    	public int shipAmount;
    	
    	public GoodInfo(String name){
    		this.name = name;
    		shipAmount = 0;
    		planetAmount = 0;
    	}
    	
    	public void setBuyPrice(int price){
    		buyPrice = price;
    	}
    	
    	public void setSellPrice(int price){
    		sellPrice = price;
    	}
    	
    	public void setShipAmount (int amount){
    		shipAmount = amount;
    	}
    	
    	public void setPlanetAmount (int amount){
    		planetAmount = planetAmount;
    	}
    }
    
    private void refreshDisplays()
    {
		cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
		capacityDisplay.setText(String.valueOf(sInventory.getCapacityLeft()));
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