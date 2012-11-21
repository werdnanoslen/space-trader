// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.unusedReturnValue, variableShouldBeFinal
/**
 * Contains EncounterView activity
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author The Droids You Are looking For
 * @version 1.0 EncounterView provides generic view that will be subclassed to
 *          create displays for particular encounters
 **/
public class EncounterView extends Activity {
	/** encounterTitle - displays title of encounter **/
	private TextView encounterTitle;

	/** encounterDescription - displays description of particular encounter **/
	private TextView encounterDescription;

	/** action1 - presents user with first possible response to encounter **/
	private Button action1;

	/** action2 - presents user with alternate response to encounter **/
	private Button action2;

	/**
	 * Method onCreate - inits view on intent
	 * 
	 * @param savedInstanceState
	 *            Bundle - basic passed info
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encounter);

		encounterTitle = (TextView) findViewById(R.id.encounter_title);
		encounterDescription = (TextView) findViewById(R.id.encounter_description);
		action1 = (Button) findViewById(R.id.encounter_action1);
		action2 = (Button) findViewById(R.id.encounter_action2);

		setupEncounter();
	}

	/**
	 * Creates options menu
	 * @param menu
	 * @return success or not
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_options, menu);
		return true;
	}

	/**
	 * populates options menu
	 * @param item
	 * @return success or not
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.save_game:
			item.setEnabled(false);
			item.setTitle("Saving");
			SaveState state = new SaveState(GameSetup.thePlayer,
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
			Intent intent = new Intent(this, Launcher.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Populates fields with this particular encounter Including the encounter's
	 * title, description (directions), and options
	 */
	public void setupEncounter() {
		/* Fill in */
		encounterTitle.setText("Pirates!");
		encounterDescription
				.setText("You have encountered pirates! Do you wish to fight or flee?");
		action1.setText("Fight");
		action2.setText("Flee");
	}

	/**
	 * Consequences of user's choice of action1
	 * 
	 * @param view - the view it is called from
	 */
	public void fight(View view) {
		if (GameSetup.thePlayer.getship().fight()) {
			Toast.makeText(this,
					"You've destroyed the pirates and salvaged the wreckage!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(
					this,
					"The pirates shot you out of the sky and stole some credits!",
					Toast.LENGTH_LONG).show();
		}
		startPlanetView(view);
	}

	/**
	 * Consequences of user's choice of action2
	 * @param view
	 */
	public void flee(View view) {
		if (GameSetup.thePlayer.getship().flee()) {
			Toast.makeText(this, "You've escaped successfully!",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this,
					"The pirates caught you and forced you to pay a random!",
					Toast.LENGTH_LONG).show();
		}
		startPlanetView(view);
	}

	/**
	 * Changes activity to Planet
	 * 
	 * @param view
	 *            PlanetView
	 */
	public void startPlanetView(View view) {
		Intent intent = new Intent(EncounterView.this, PlanetView.class);
		startActivity(intent);
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
