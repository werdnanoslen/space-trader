package com.cs2340.spacetrader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Space extends Activity {

	private Ship ship;
	private Planet currentPlanet;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);
        
        //Current ship and planet
        ship = GameSetup.thePlayer.getship();
        currentPlanet = GameSetup.theMap.getPlanet(ship.getPlanetName());
        
        //Fields
        ProgressBar fuelBar = (ProgressBar) findViewById(R.id.bar_fuel_level);
        TextView name = (TextView) findViewById(R.id.current_planet);
        TextView techLevel = (TextView) findViewById(R.id.tech_level);        
        int[] coordinates = currentPlanet.getCoordinate();
        
        //Set values of fields
        fuelBar.setMax(ship.getFuelCapacity());
        fuelBar.setProgress(ship.getFuel());
        name.setText(currentPlanet.getName() + "(" + coordinates[0] + ", " + coordinates[1] + ")");
        techLevel.setText(currentPlanet.getTechLevel());
        
        //TODO - add Code for recovering the person object passed through intent...
        //as documented here: http://stackoverflow.com/questions/2736389/how-to-pass-object-from-one-activity-to-another-in-android
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_space, menu);
        return true;
    }
    
    /**
     * Changes GUI to planet select view
     * @param view
     */
    public void changeDestination(View view)
    {
    	Intent intent = new Intent(this, SelectPlanet.class);
    	startActivity(intent);
    }
    
    /**
     * Changes GUI to current planet's view
     * @param view
     */
    public void startPlanetView(View view)
    {
    	Intent intent = new Intent(this, PlanetView.class);
    	startActivity(intent);
    }
}
