package com.cs2340.spacetrader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class PlanetView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        TextView name = (TextView) findViewById(R.id.planet_current_planet);
        name.setText(GameSetup.thePlayer.getship().getPlanetName());
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
     * Continue to TradeView from PlanetView
     * @param view PlanetView
     */
    public void startTradeView(View view)
    {
    	Intent intent = new Intent(PlanetView.this, TradeView.class);
    	startActivity(intent);
    }

    /**
     * GUI returns to Space from PlanetView
     * @param view PlanetView
     */
    public void startSpaceView(View view)
    {
    	Intent intent = new Intent(PlanetView.this, Space.class);
    	startActivity(intent);
    }
}
