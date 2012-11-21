package com.cs2340.spacetrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class GameSetup extends Activity {
	public static final String PLAYA = "com.example.myfirstapp.PLAYA";
	public static Player thePlayer;
	public static Map theMap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
    	NumberPicker traderLevel = (NumberPicker) findViewById(R.id.traderLevel);
    	NumberPicker pilotLevel = (NumberPicker) findViewById(R.id.pilotLevel);
    	NumberPicker engineerLevel = (NumberPicker) findViewById(R.id.engineerLevel);
    	NumberPicker fighterLevel = (NumberPicker) findViewById(R.id.fighterLevel);
    	traderLevel.setMaxValue(16);
    	traderLevel.setMinValue(0);
    	pilotLevel.setMaxValue(16);
    	pilotLevel.setMinValue(0);
    	engineerLevel.setMaxValue(16);
    	engineerLevel.setMinValue(0);
    	fighterLevel.setMaxValue(16);
    	fighterLevel.setMinValue(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_setup, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                NavUtils.navigateUpFromSameTask(this);
                return true;          	
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkMakePlayer(View view)
    {
    	//TODO - make this prettier
    	NumberPicker traderLevel = (NumberPicker) findViewById(R.id.traderLevel);
    	NumberPicker pilotLevel = (NumberPicker) findViewById(R.id.pilotLevel);
    	NumberPicker engineerLevel = (NumberPicker) findViewById(R.id.engineerLevel);
    	NumberPicker fighterLevel = (NumberPicker) findViewById(R.id.fighterLevel);
    	int tLevel = traderLevel.getValue();
    	int pLevel = pilotLevel.getValue();
    	int eLevel = engineerLevel.getValue();
    	int fLevel = fighterLevel.getValue();
    	int levels = traderLevel.getValue() + pilotLevel.getValue() + 
    			engineerLevel.getValue() + fighterLevel.getValue();
    	if (levels > Player.startingPoints)
    	{
    		Toast.makeText(this, R.string.too_high_error, Toast.LENGTH_LONG).show();
    	}
    	else if (levels < Player.startingPoints)
    	{
    		Toast.makeText(this, R.string.too_low_error, Toast.LENGTH_LONG).show();
    	}
    	else
    	{
    		EditText nameField = (EditText) findViewById(R.id.character_name);
    		String name = nameField.getText().toString();
    		//TODO - create game class, replace player instantiation with game
    		//TODO - MOVE CONSTANTS SOMEWHERE BETTER
    		int initGold = 2000;
    		int cargoSlots = 10;
    		theMap = new Map();
    		thePlayer = new Player(name,tLevel, pLevel, eLevel, fLevel, initGold, cargoSlots);
    		Intent intent = new Intent(this, Space.class);
    		startActivity(intent);
    	}
    }
}
