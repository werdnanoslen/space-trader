// $codepro.audit.disable variableShouldBeFinal, staticMemberAccess
/**
 * Contains TradeAdapter class for populating market trade list
 */

package com.cs2340.spacetrader; // $codepro.audit.disable packageNamingConvention

import com.cs2340.spacetrader.TradeView.GoodInfo; // $codepro.audit.disable unnecessaryImport

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * TradeAdapter class adapts array lists to populate a list view
 * 
 * @author David
 * @version 1.0
 * 
 */
public class TradeAdapter extends ArrayAdapter<GoodInfo> {
	/** parent context */
	private Context context;

	/** ID of the row layout */
	private int layoutResourceId;

	/** data holder for input array */
	private GoodInfo data[] = null;

	/**
	 * Overrides toString because audit complains
	 * @return a random string
	 */
	@Override
	public String toString(){
		return "blah";
	}
	
	/**
	 * Constructor for the adapter
	 * 
	 * @param context
	 * @param layoutResourceId
	 * @param data
	 */
	public TradeAdapter(Context context, int layoutResourceId, GoodInfo[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data.clone();
	}

	/**
	 * Populates a single view in the list view
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return the populated view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		GoodHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new GoodHolder();
			holder.txtName = (TextView) row.findViewById(R.id.name_good);
			holder.txtBuyGood = (Button) row.findViewById(R.id.buy_good);
			holder.txtSellGood = (Button) row.findViewById(R.id.sell_good);

			row.setTag(holder);
		} else {
			holder = (GoodHolder) row.getTag();
		}
		String buyText;
		String sellText;
		String planetName = GameSetup.thePlayer.getship().getPlanetName();

		if (!Good.getDataList()[position].canBuy(GameSetup.theMap.getPlanet(
				planetName).getNTechLevel())) {
			buyText = ("Can't Buy\nHere");
			holder.txtBuyGood.setEnabled(false);
		} else {
			buyText = String.format("BUY $%d\n%d Available",
					data[position].buyPrice, data[position].planetAmount);
			holder.txtBuyGood.setEnabled(true);
		}

		if (!Good.getDataList()[position].canSell(GameSetup.theMap.getPlanet(
				planetName).getNTechLevel())) {
			sellText = ("Can't Sell\nHere");
			holder.txtSellGood.setEnabled(false);
		} else {
			sellText = String.format("SELL $%d\n%d Available",
					data[position].sellPrice, data[position].shipAmount);
			holder.txtSellGood.setEnabled(true);
		}

		final String gName = data[position].name;
		holder.txtName.setText(data[position].name);
		holder.txtBuyGood.setText(buyText);
		holder.txtSellGood.setText(sellText);

		holder.txtBuyGood.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view) {
				((TradeView) context).buy(gName);
			}
		});

		holder.txtSellGood.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View view) {
				((TradeView) context).sell(gName);
			}
		});
		return row;
	}

	/**
	 * Class that handles view storing for the Adapter
	 * 
	 * @author David
	 * 
	 */
	private static class GoodHolder {
		/** good name field */
		private TextView txtName;
		
		/** good buy button */
		private Button txtBuyGood;
		
		/** good sell button */
		private Button txtSellGood;
		
		/**
		 * Overrides toString because audit complains
		 * @return a random string
		 */
		@Override
		public String toString(){
			return "blah";
		}
	}
}
