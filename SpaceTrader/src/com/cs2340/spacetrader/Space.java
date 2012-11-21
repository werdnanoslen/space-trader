// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.unusedReturnValue
/**
 * Contains the class Space
 */
package com.cs2340.spacetrader;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An object of this class acts as the user
 * interface screen that displays game details 
 * like the current planet, its detailsa and fuel level
 * 
 * @author The Droids You Are Looking For
 * @version 1.0
 */
public class Space extends Activity {
	
	/**
	 * instance varialble for ship object
	 */
	private Ship ship;
	
	/**
	 * instance variable for the planet that is being currently visited.
	 */
	private Planet currentPlanet;

	/**
	 * runs at the creation of the new Activity, sets up initial state.
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_space);

		// Current ship and planet
		ship = GameSetup.thePlayer.getship();
		currentPlanet = GameSetup.theMap.getPlanet(ship.getPlanetName());

		// Fields
		final ProgressBar fuelBar = (ProgressBar) findViewById(R.id.bar_fuel_level);
		final TextView name = (TextView) findViewById(R.id.current_planet);
		final TextView techLevel = (TextView) findViewById(R.id.tech_level);
		final int[] coordinates = currentPlanet.getCoordinate();

		// Set values of fields
		fuelBar.setMax(ship.getFuelCapacity());
		fuelBar.setProgress(ship.getFuel());
		name.setText(currentPlanet.getName() + "(" + coordinates[0] + ", "
				+ coordinates[1] + ")");
		techLevel.setText(currentPlanet.getTechLevel());

		// TODO - add Code for recovering the person object passed through
		// intent...
		// as documented here:
		// http://stackoverflow.com/questions/2736389/how-to-pass-object-from-one-activity-to-another-in-android
	}

	/**
	 * Set's up the menu layout
	 * 
	 * @param menu
	 * @return boolean on sucessful create
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_options, menu);
		return true;
	}

	/**
	 * Populates the save/close menu
	 * 
	 * @param item
	 * @return boolean on successful run
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_game:
			item.setEnabled(false);
			item.setTitle("Saving");
			final SaveState state = new SaveState(GameSetup.thePlayer,
					GameSetup.theMap);
			if (MemoryService.saveGame(state, this)) {
				Toast.makeText(this, "Game saved successfully",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Failed to save game", Toast.LENGTH_SHORT)
						.show();
			}
			item.setTitle("Save");
			item.setEnabled(true);
			return true;
		case R.id.quit_game:
			final Intent intent = new Intent(this, Launcher.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Changes GUI to planet select view
	 * 
	 * @param view
	 */
	public void changeDestination(View view) {
		final Intent intent = new Intent(this, SelectPlanet.class);
		startActivity(intent);
	}

	/**
	 * Changes GUI to current planet's view
	 * 
	 * @param view
	 */
	public void startPlanetView(View view) {
		final Intent intent = new Intent(this, PlanetView.class);
		startActivity(intent);
	}

	/**
	 * Temporary method to test Encounters
	 * 
	 * @param view
	 */
	public void testEncounter(View view) {
		final Intent intent = new Intent(this, EncounterView.class);
		startActivity(intent);
	}

	/**
	 * Changes activity to Ship Inventory View
	 * 
	 * @param view
	 */
	public void startShipView(View view) {
		final Intent intent = new Intent(Space.this, ShipInventoryView.class);
		startActivity(intent);
	}
	
	/**
	 * retuns details of ship and planet.
	 * @return ship and planet
	 */
	@Override
	public String toString(){
		return (ship.toString() + " on " + currentPlanet.toString());
	}
}
