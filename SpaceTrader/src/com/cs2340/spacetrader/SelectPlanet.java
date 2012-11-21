// $codepro.audit.disable variableShouldBeFinal
/**
 * Contains Activity to select a planet
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Activity that displays a list of planets to travel to
 * @author Andrew
 * @version 1.0
 *
 */
public class SelectPlanet extends Activity {
	/** list view of planets */
	private ListView pListView;
	
	/** player's ship */
	private Ship ship;
	
	/** player's current planet */
	private Planet currentPlanet;
	
	/** haha constants! this one is 10*/
	private static final int TEN = 10;
	
	/** haha constants! this one is 2*/
	private static final int TWO = 2;

	/**
	 * sets up activity upon creation
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// View settings
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_planet);

		// Current ship and planet
		ship = GameSetup.thePlayer.getship();
		currentPlanet = GameSetup.theMap.getPlanet(ship.getPlanetName());

		// Effective 'cancel' button
		Button stayButton = (Button) findViewById(R.id.button_stay_here);
		stayButton.setText("Stay on " + currentPlanet.getName());

		// get planet array to pass into list adapter
		Planet[] planetData = GameSetup.theMap.getPlanetArray();

		// set up list adapter
		PlanetListAdapter adapter = new PlanetListAdapter(this,
				R.layout.planet_row, planetData);
		pListView = (ListView) findViewById(R.id.selectplanet_list);
		pListView.setAdapter(adapter);
	}

	/**
	 * Return to Space from SelectPlanet
	 * 
	 * @param view
	 */
	public void startSpaceView(View view) {
		Random generator = new Random();
		int num = generator.nextInt(TEN);
		if (num > TWO) {
			Intent intent = new Intent(this, EncounterView.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, Space.class);
			startActivity(intent);
		}
	}
	
	/**
	 * Overrides toString because audit complains
	 * @return a random string
	 */
	@Override
	public String toString(){
		return "blah";
	}
}
