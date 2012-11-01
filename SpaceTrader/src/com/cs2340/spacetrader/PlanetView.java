package com.cs2340.spacetrader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class PlanetView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        TextView name = (TextView) findViewById(R.id.planet_current_planet);
        name.setText(GameSetup.theMap.getPlanetArray()[0].getName());
    }
    
    public void startTradeView(View view){
    	Intent intent = new Intent(PlanetView.this, TradeView.class);
    	startActivity(intent);
    }

}
