package com.cs2340.spacetrader;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
        
        Resources rs = getResources();
        LinearLayout pv = (LinearLayout) findViewById(R.id.upgrade_layout);
        if (GameSetup.theMap.getPlanet(GameSetup.thePlayer.getship().getPlanetName()).getNTechLevel() > 2)
        {
        	pv.setBackgroundDrawable(rs.getDrawable((R.drawable.hightechback)));
        }
        else
        {
        	pv.setBackgroundDrawable(rs.getDrawable((R.drawable.lowtechback)));
        }
        
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
        switch (item.getItemId())
        {
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
     * Fetches current armor and guns prices;
     * Toggles UI actions;
     * Displays updated price/names.
     */
    public void updatePrices()
    {
    	cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
    	
    	int armorPrice = GameSetup.thePlayer.getship().armorUpgradeCost();
    	int gunsPrice = GameSetup.thePlayer.getship().wepUpgradeCost();
    	
    	upgradeArmorButton.setText("Upgrade armor to \n" + GameSetup.thePlayer.getship().armorUpgradeName() + " ($" + armorPrice + ")");
    	upgradeGunsButton.setText("Upgrade guns to \n" + GameSetup.thePlayer.getship().wepUpgradeName() + " ($" + gunsPrice + ")");
    	
    	if (sInventory.getMoneyLeft() < armorPrice)
        {
        	upgradeArmorButton.setClickable(false);
        }
    	if (sInventory.getMoneyLeft() < gunsPrice)
        {
        	upgradeGunsButton.setClickable(false);
        }
    }
    
    /**
     * Upgrades ship's armor
     */
    public void upgradeArmor(View view)
    {
    	GameSetup.thePlayer.getship().upgradeArmor();
    	updatePrices();
    }
    
    /**
     * Upgrades ship's guns
     */
    public void upgradeGuns(View view)
    {
    	GameSetup.thePlayer.getship().upgradeWeapons();
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