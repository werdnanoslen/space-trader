// $codepro.audit.disable variableShouldBeFinal, com.instantiations.assist.eclipse.analysis.unusedReturnValue
/**
 * COntains Activity to display the trade menu
 */
package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity to show the trade menu
 * 
 * @author Andrew, David
 * @version 1.0
 * 
 */
public class TradeView extends Activity {
	/** ship's inventory */
	private ShipInventory sInventory;

	/** "Cash" label text view */
	private TextView cashDisplay;

	/** "Capacity" label text view */
	private TextView capacityDisplay;

	/** list view of all goods */
	private ListView tradeList;

	/** Current planet */
	private Planet planet;

	/** MarketVisit Object, handles transactions */
	public MarketVisit market;

	/**
	 * onCreate method, run at creation of Activity, set's up initial states
	 * 
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade);

		planet = GameSetup.theMap.getPlanet(GameSetup.thePlayer.getship()
				.getPlanetName());
		sInventory = GameSetup.thePlayer.getship().getInventory();
		market = new MarketVisit(sInventory, planet);
		// market.checkIn();

		// slots on display to display current balances
		cashDisplay = (TextView) findViewById(R.id.trade_cash);
		capacityDisplay = (TextView) findViewById(R.id.trade_capacity);

		Resources rs = getResources();
		LinearLayout pv = (LinearLayout) findViewById(R.id.trade_layout);
		if (GameSetup.theMap.getPlanet(
				GameSetup.thePlayer.getship().getPlanetName()).getNTechLevel() > 0) {
			pv.setBackgroundDrawable(rs.getDrawable((R.drawable.hightechback)));
		} else {
			pv.setBackgroundDrawable(rs.getDrawable((R.drawable.hightechback)));
		}

		// initialize values in slots
		refreshDisplays();
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
	 * Overrides toString because audit complains
	 * 
	 * @return a random string
	 */
	@Override
	public String toString() {
		return "blah";
	}

	/**
	 * This class serves as a data holder class to pass into TradeAdapter
	 * 
	 * @author David
	 */
	public class GoodInfo {
		/** name of the good */
		public String name;

		/** buy price of the good */
		public int buyPrice;

		/** amount the planet has */
		public int planetAmount;

		/** sell price of good */
		public int sellPrice;

		/** amount player has */
		public int shipAmount;

		/**
		 * Constructor for GoodInfo
		 * 
		 * @param name
		 */
		public GoodInfo(String name) {
			this.name = name;
			this.buyPrice = market.priceAtWhichPlanetSells(planet
					.getInventory().getGood(name));
			this.planetAmount = planet.getInventory().getGoodAmount(name);
			this.sellPrice = market.priceAtWhichPlanetBuys(planet
					.getInventory().getGood(name));
			this.shipAmount = sInventory.getGoodAmount(name);
		}

		/**
		 * Overrides toString because audit complains
		 * 
		 * @return a random string
		 */
		@Override
		public String toString() {
			return "blah";
		}
	}

	/**
	 * Reloads the data in the screen so all quantities are updated
	 */
	private void refreshDisplays() {
		cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
		capacityDisplay.setText(String.valueOf(sInventory.getCapacityLeft()));
		// extract good info
		String[] goodlist = { "Water", "Furs", "Food", "Ore", "Games",
				"Firearms", "Medicine", "Machines", "Narcotics", "Robots" };
		GoodInfo[] gi = new GoodInfo[goodlist.length];
		int i = 0;
		for (String good : goodlist) {
			gi[i] = new GoodInfo(good);
			i++;
		}

		// set the adapter
		TradeAdapter adapter = new TradeAdapter(this, R.layout.market_row, gi);
		// display the list view
		tradeList = (ListView) findViewById(R.id.trade_list);

		tradeList.setAdapter(adapter);
	}

	/**
	 * Initiates buy action sequence
	 * 
	 * @param goodName
	 */
	public void buy(String goodName) {
		// quick weedouts
		if (market.getShipInventory().getMoneyLeft() <= 0) {
			return;
		}
		if (market.getShipInventory().getCapacityLeft() <= 0) {
			return;
		}
		Good theGood = market.getPlanetInventory().getGood(goodName);
		if (market.getPlanetInventory().getGoodFromInventory(theGood)
				.getQuantity() <= 0) {
			return;
		}

		market.canBuyFromPlanet(market.getPlanetInventory().getGood(goodName), 1);

		refreshDisplays();
	}

	/**
	 * Initiates sell action sequence
	 * 
	 * @param goodName
	 */
	public void sell(String goodName) {
		// quick weedout
		if (sInventory.getCapacityLeft() >= sInventory.getCapacityMax()) {
			return;
		}
		if (sInventory.getGoodAmount(goodName) <= 0) {
			return;
		}

		market.sellToPlanet(market.getPlanetInventory().getGood(goodName), 1);

		refreshDisplays();
	}

	/**
	 * Moves player to Planet screen
	 * 
	 * @param view
	 */
	public void gotoPlanet(View view) {
		Intent intent = new Intent(TradeView.this, PlanetView.class);
		startActivity(intent);
	}
}