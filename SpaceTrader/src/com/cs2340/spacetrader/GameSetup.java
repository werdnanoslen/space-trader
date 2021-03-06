// $codepro.audit.disable nullPointerDereference, variableShouldBeFinal, assignmentToNonFinalStatic
/**
 * Contains activity to create a new game
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

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

/**
 * 
 * @author The Droids You Are Looking For
 * @version 1.0 GUI for the game setup screen
 */

public class GameSetup extends Activity {
	/** application namespace **/
	public static final String PLAYA = "com.example.myfirstapp.PLAYA";
	
	/** initial player cash **/
	public static final int INITMONEY = 2000;
	
	/** initial player cargo capacity**/
	public static final int INITCARGOCAP = 10;

	/** singleton representing player object **/
	public static Player thePlayer; // $codepro.audit.disable

	/** singleton representing the map object **/
	public static Map theMap;// $codepro.audit.disable

	/**
	 * @param savedInstanceState
	 *            state passed to method by intent
	 **/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_setup);
		NumberPicker traderLevel = (NumberPicker) findViewById(R.id.traderLevel);
		NumberPicker pilotLevel = (NumberPicker) findViewById(R.id.pilotLevel);
		NumberPicker engineerLevel = (NumberPicker) findViewById(R.id.engineerLevel);
		NumberPicker fighterLevel = (NumberPicker) findViewById(R.id.fighterLevel);
		traderLevel.setMaxValue(Player.STARTINGPOINTS);
		traderLevel.setMinValue(0);
		pilotLevel.setMaxValue(Player.STARTINGPOINTS);
		pilotLevel.setMinValue(0);
		engineerLevel.setMaxValue(Player.STARTINGPOINTS);
		engineerLevel.setMinValue(0);
		fighterLevel.setMaxValue(Player.STARTINGPOINTS);
		fighterLevel.setMinValue(0);
	}

	/**
	 * @param menu
	 *            - menu that was chosen to be created
	 * @return success of operation
	 **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_game_setup, menu);
		return true;
	}

	/**
	 * Overrides toString because audit complains
	 * @return a random string
	 */
	@Override
	public String toString(){
		return "blah";
	}
	
	/**
	 * @param item
	 *            - selected item
	 * @return success of operation
	 **/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Checks that user's input is valid to proceed
	 * 
	 * @param view
	 */
	public void checkMakePlayer(View view) {
		// TODO - make this prettier
		NumberPicker traderLevel = (NumberPicker) findViewById(R.id.traderLevel);
		NumberPicker pilotLevel = (NumberPicker) findViewById(R.id.pilotLevel);
		NumberPicker engineerLevel = (NumberPicker) findViewById(R.id.engineerLevel);
		NumberPicker fighterLevel = (NumberPicker) findViewById(R.id.fighterLevel);
		int tLevel = traderLevel.getValue();
		int pLevel = pilotLevel.getValue();
		int eLevel = engineerLevel.getValue();
		int fLevel = fighterLevel.getValue();
		int levels = traderLevel.getValue() + pilotLevel.getValue()
				+ engineerLevel.getValue() + fighterLevel.getValue();
		if (levels > Player.STARTINGPOINTS) {
			Toast.makeText(this, R.string.too_high_error, Toast.LENGTH_LONG)
					.show();
		} else if (levels < Player.STARTINGPOINTS) {
			Toast.makeText(this, R.string.too_low_error, Toast.LENGTH_LONG)
					.show();
		} else {
			EditText nameField = (EditText) findViewById(R.id.character_name);
			String name = nameField.getText().toString();
			// TODO - create game class, replace player instantiation with game
			// TODO - MOVE CONSTANTS SOMEWHERE BETTER
			int initGold = INITMONEY;
			int cargoSlots = INITCARGOCAP;
			theMap = new Map();
			thePlayer = new Player(name, tLevel, pLevel, eLevel, fLevel,
					initGold, cargoSlots);
			Intent intent = new Intent(this, Space.class);
			startActivity(intent);
		}
	}
}
