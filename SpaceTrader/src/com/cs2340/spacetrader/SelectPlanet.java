package com.cs2340.spacetrader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SelectPlanet extends Activity {
	
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
