package com.cs2340.spacetrader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TradeView extends Activity
{
	private ShipInventory sInventory;
	private TextView cashDisplay;
	private TextView capacityDisplay;
	private ListView tradeList;
	private Planet planet;
	public MarketVisit market;
	
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

        Resources rs = getResources();
        LinearLayout pv = (LinearLayout) findViewById(R.id.trade_layout);
        if (GameSetup.theMap.getPlanet(GameSetup.thePlayer.getship().getPlanetName()).getNTechLevel() > 2){
        	pv.setBackgroundDrawable(rs.getDrawable((R.drawable.hightechback)));
        }
        else{
        	pv.setBackgroundDrawable(rs.getDrawable((R.drawable.hightechback)));
        }
        

        //initialize values in slots
        refreshDisplays();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) {
            case R.id.save_game:
            	item.setEnabled(false);
            	item.setTitle("Saving");
            	SaveState state = new SaveState(GameSetup.thePlayer, GameSetup.theMap);
            	if (MemoryService.saveGame(state, this))
        		{
            		Toast.makeText(this, "Game saved successfully", Toast.LENGTH_SHORT).show();
        		}
            	else
            	{
            		Toast.makeText(this, "Failed to save game", Toast.LENGTH_SHORT).show();
            	}
            	item.setTitle("Save");
            	item.setEnabled(true);
                return true;
            case R.id.quit_game:
            	Intent intent = new Intent(this, Launcher.class);
            	startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * This class serves as a data holder class to pass into TradeAdapter
     * @author David
     */
    public class GoodInfo
    {
    	public String name;
    	public int buyPrice;
    	public int planetAmount;
    	public int sellPrice;
    	public int shipAmount;
    	
    	public GoodInfo()
    	{    		
    	}
    	
    	public GoodInfo(String name)
    	{
    		this.name = name;
        	this.buyPrice = market.priceAtWhichPlanetSells(planet.getInventory().getGood(name));
        	this.planetAmount = planet.getInventory().getGoodAmount(name);
        	this.sellPrice = market.priceAtWhichPlanetBuys(planet.getInventory().getGood(name));
        	this.shipAmount =  sInventory.getGoodAmount(name);
    	}
    }
    
    /**
     * Reloads the data in the screen so all quantities are updated
     */
    private void refreshDisplays()
    {
		cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
		capacityDisplay.setText(String.valueOf(sInventory.getCapacityLeft()));
		//extract good info
		String[] goodlist = {"Water", "Furs", "Food", "Ore", "Games", "Firearms", "Medicine", "Machines", "Narcotics", "Robots"};
        GoodInfo[] gi = new GoodInfo[goodlist.length];
        int i = 0;
		for (String good : goodlist)
		{
			gi[i] = new GoodInfo(good);
			i ++;
		}
        
        
        //set the adapter
        TradeAdapter adapter = new TradeAdapter(this, R.layout.market_row, gi);
        //display the list view
        tradeList = (ListView)findViewById(R.id.trade_list);
        
        tradeList.setAdapter(adapter);
    }
    
    /**
     * Initiates buy action sequence
     * @param goodName
     */
    public void buy(String goodName)
    {
    	//quick weedouts
    	if (market.getShipInventory().getMoneyLeft() <= 0) return;
    	if (market.getShipInventory().getCapacityLeft() <= 0) return;
    	Good theGood = market.getPlanetInventory().getGood(goodName);
    	if (market.getPlanetInventory().getGoodFromInventory(theGood).getQuantity() <= 0) return;

    	market.buyFromPlanet(market.getPlanetInventory().getGood(goodName), 1);
    	
    	refreshDisplays();
    }
    
    /**
     * Initiates sell action sequence
     * @param goodName
     */
    public void sell(String goodName)
    {
    	//quick weedout
    	if (sInventory.getCapacityLeft() >= sInventory.getCapacityMax()) return;
    	if (sInventory.getGoodAmount(goodName) <= 0) return;
    	
    	market.sellToPlanet(market.getPlanetInventory().getGood(goodName), 1);
    	
    	refreshDisplays();
    }
    
    /**
     * Moves player to Planet screen
     * @param view
     */
    public void gotoPlanet(View view)
    {
    	Intent intent = new Intent(TradeView.this, PlanetView.class);
        startActivity(intent);
    }
}