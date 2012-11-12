package com.cs2340.spacetrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeShipView extends Activity
{
	private ShipInventory sInventory;
	private int armorPrice;
	private int gunsPrice;
	
	private TextView cashDisplay;
	private Button upgradeArmorButton;
	private Button upgradeGunsButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_ship);
        
        sInventory = GameSetup.thePlayer.getship().getInventory();
        
        cashDisplay = (TextView) findViewById(R.id.trade_cash);
        
        upgradeArmorButton = (Button) findViewById(R.id.button_upgrade_armor);
        upgradeGunsButton = (Button) findViewById(R.id.button_upgrade_guns);
        
        updatePrices();
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
     * Fetches current armor and guns prices and toggles UI actions
     */
    public void updatePrices()
    {
    	cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
    	
    	//armorPrice = getArmorPrice();
    	//gunsPrice = getGunsPrice();
    	
    	upgradeArmorButton.setText(this.getString(R.string.button_upgrade_armor) + " ($" /* + getArmorPrice()*/ + ")");
    	upgradeGunsButton.setText(this.getString(R.string.button_upgrade_guns) + " ($" /* + getGunsPrice()*/ + ")");
    	
    	if (sInventory.getMoneyLeft() < armorPrice)
        {
        	upgradeArmorButton.setClickable(false);
        }
    	if (sInventory.getMoneyLeft() < gunsPrice)
        {
        	upgradeArmorButton.setClickable(false);
        }
    }
    
    /**
     * Upgrades ship's armor
     */
    public void upgradeArmor(View view)
    {
    	updatePrices();
    }
    
    /**
     * Upgrades ship's guns
     */
    public void upgradeGuns(View view)
    {
    	updatePrices();
    }
    
    /**
     * Return to PlanetView
     * @param view
     */
    public void gotoPlanet(View view)
    {
    	Intent intent = new Intent(UpgradeShipView.this, PlanetView.class);
        startActivity(intent);
    }
}