package com.cs2340.spacetrader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SelectPlanet extends Activity {
	
	private ListView pListView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	//View settings
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_planet);
        
        //Initial values
        Button name = (Button) findViewById(R.id.button_stay_here);
        Planet currentPlanet = GameSetup.theMap.getPlanetArray()[0];
        
        //Initial settings
        name.setText("Stay on " + currentPlanet.getName());
        
        //get planet array to pass into list adapter
        Planet[] planetData = GameSetup.theMap.getPlanetArray();
        
        //set up list adapter
        PlanetListAdapter adapter = new PlanetListAdapter(this, R.layout.planet_row, planetData);
        pListView = (ListView)findViewById(R.id.selectplanet_list);
        pListView.setAdapter(adapter);
        
    }
    
    /**
     * Change Planets
     * @param view
     */
    public void changePlanet(View view)
    {
    	
    }
    
    /**
     * Return to PlanetView from SelectPlanet
     * @param view
     */
    public void startPlanetView(View view)
    {
    	Intent intent = new Intent(this, PlanetView.class);
    	startActivity(intent);
    }
}
