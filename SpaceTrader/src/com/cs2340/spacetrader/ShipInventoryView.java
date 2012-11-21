package com.cs2340.spacetrader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShipInventoryView extends Activity
{
	private ShipInventory sInventory;	//holds the ShipInventory
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_inventory_view);
        sInventory = GameSetup.thePlayer.getship().getInventory();
        
        TextView cashDisplay = (TextView) findViewById(R.id.ship_cash);
        TextView capacityDisplay = (TextView) findViewById(R.id.ship_capacity);
        TextView weaponDisplay = (TextView) findViewById(R.id.ship_weapon);
        TextView armorDisplay = (TextView) findViewById(R.id.ship_armor);
        TextView contractDisplay = (TextView) findViewById(R.id.player_contract);
        
        cashDisplay.setText('$' + String.valueOf(sInventory.getMoneyLeft()));
		capacityDisplay.setText(String.valueOf(sInventory.getCapacityLeft()));
		weaponDisplay.setText("Current Weapon: " + GameSetup.thePlayer.getship().getWeaponName());
		armorDisplay.setText("Current Armor: " + GameSetup.thePlayer.getship().getArmorName());
        
		if (GameSetup.thePlayer.hasContract)
		{
			contractDisplay.setText("Current Contract:\n" + GameSetup.thePlayer.getContract().getString());
		}
		else
		{
			contractDisplay.setText("Current Contract:\nNone Accepted");
		}
		
        ShipInventoryAdapter sAdapter = new ShipInventoryAdapter(this, R.layout.ship_item, sInventory);

        ListView inventoryList = (ListView) findViewById(R.id.ship_inventory_list);
        
        inventoryList.setAdapter(sAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }
    
    /**
     * This class acts as an Adapter for the ListView in the ShipInventoryView activity
     * It's used to populate the ListView upon the activity's creation
     * @author David
     *
     */
    private class ShipInventoryAdapter extends ArrayAdapter<GoodData>
    {
        private ArrayList<GoodData> goodArray;
        private Context context;

        private ShipInventoryAdapter(Context context, int textViewResourceId, ShipInventory sInventory) 
        {
            super(context, textViewResourceId, sInventory.goods);
            goodArray = sInventory.goods;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
			View v = convertView;
			if (v == null)
			{
			    LayoutInflater vi = ((Activity)context).getLayoutInflater();
			    v = vi.inflate(R.layout.ship_item, parent, false);
			}
			GoodData o = goodArray.get(position);
			if (o != null)
			{
			    TextView tt = (TextView) v.findViewById(R.id.toptext);
			    TextView bt = (TextView) v.findViewById(R.id.bottomtext);
			    if (tt != null)
			    {
			          tt.setText(o.getGood().getName());
			    }
			    if(bt != null)
			    {
			          bt.setText("Amount: "+ o.getQuantity());
			    }
			}
			return v;
        }
    }
    
    /**
     * Changes the activity to Space
     * @param view
     */
    public void goToSpace(View view)
    {
    	Intent intent = new Intent(this, Space.class);
    	startActivity(intent);
    }
}
