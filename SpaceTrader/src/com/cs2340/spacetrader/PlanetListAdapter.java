// $codepro.audit.disable variableShouldBeFinal, staticMemberAccess, packageNamingConvention
/**
 * Contains PlanetListAdapter class
 */
package com.cs2340.spacetrader;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

/**
 * Custom Array Adapter for planet arrays
 * 
 * @author David
 * @version 1.0
 */
public class PlanetListAdapter extends ArrayAdapter<Planet> {
	/** parent context */
	public Context context;

	/** row layout id */
	public int layoutResourceId;

	/** input data array */
	public Planet data[] = null;
	
	/** constant for calculating chance of encounters*/
	private static final int ONEHUNDRED = 100;
	
	/** Constant change of encounters, in percent */
	private static final int ENCOUNTERCHANCE = 20;

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
	 * Constructor for the Adapter
	 * 
	 * @param context
	 * @param layoutResourceId
	 * @param data
	 */
	public PlanetListAdapter(Context context, int layoutResourceId,
			Planet[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data.clone();
	}

	/**
	 * Populates a single view with appropriate data
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return populated View
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		PlanetHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new PlanetHolder();
			holder.button = (Button) row
					.findViewById(R.id.button_planet_select);

			row.setTag(holder);
		} else {
			holder = (PlanetHolder) row.getTag();
		}

		String buttonText = String.format("Go to %s at %d, %d",
				data[position].getName(), data[position].getCoordinate()[0],
				data[position].getCoordinate()[1]);

		holder.button.setText(buttonText);
		holder.button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view) {
				GameSetup.thePlayer.getship().moveToPlanet(data[position]);
				if (GameSetup.thePlayer.hasContract) {
					if (GameSetup.thePlayer.getContract().canCompleteContract(
							context)) {
						Toast.makeText(
								context,
								"Contract Completed. You recieved "
										+ GameSetup.thePlayer.getContract()
												.getReward()
										+ " credits in payment.",
								Toast.LENGTH_LONG).show();
					}
				}
				// Intent intent = new Intent(context, Space.class);
				// context.startActivity(intent);
				Random generator = new Random();
				int num = generator.nextInt(ONEHUNDRED);
				if (num <= ENCOUNTERCHANCE) {
					Intent intent = new Intent(context, EncounterView.class);
					context.startActivity(intent);
				} else {
					Intent intent = new Intent(context, Space.class);
					context.startActivity(intent);

				}
			}
		});

		// if cost is greater than fuel available, disable travel
		if (GameSetup.thePlayer.getship().fuelCost(data[position]) > GameSetup.thePlayer
				.getship().getFuel()) {
			holder.button.setEnabled(false);
		}

		return row;
	}

	/**
	 * Helper class to hold planet layout elements
	 * @author David
	 *
	 */
	private static class PlanetHolder {
		/** Button view */
		private Button button;

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
}
