package com.cs2340.spacetrader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Launcher extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_launcher, menu);
        return true;
    }
  
    
    /**
     * Changes view to setup screen to create a new game
     * @param view
     */
    public void startGameSetup(View view)
    {
    	Intent intent = new Intent(this, GameSetup.class);
    	startActivity(intent);
    }
    
    /**
     * Loads a game
     * @param view
     */
    public void loadGame(View view)
    {
    	SaveState state = null;
    	Toast.makeText(this, "Loading game", Toast.LENGTH_SHORT).show();
    	if (MemoryService.loadGame(state, this))
    	{
    		Toast.makeText(this, "Loaded game", Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent(this, Space.class);
    		startActivity(intent);
    	}
    	else
    	{
    		Toast.makeText(this, "Failed to load game", Toast.LENGTH_SHORT).show();
    	}
    }
    
}
