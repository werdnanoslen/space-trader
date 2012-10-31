package com.cs2340.spacetrader;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShipInventoryView extends Activity {

	private ShipInventory sInventory;	//holds the ShipInventory
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_inventory_view);
        getInventory();
        
        ShipInventoryAdapter sAdapter = new ShipInventoryAdapter(this, R.layout.ship_item, sInventory);

        Log.i("goodSize",Integer.toString(sInventory.goods.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_ship_inventory_view, menu);
        return true;
    }
    
    private class ShipInventoryAdapter extends ArrayAdapter<GoodData> {

        private ArrayList<GoodData> goodArray;
        private Context context;

        public ShipInventoryAdapter(Context context, int textViewResourceId, ShipInventory sInventory) {
                super(context, textViewResourceId, sInventory.goods);
                goodArray = sInventory.goods;
                this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = ((Activity)context).getLayoutInflater();
                    v = vi.inflate(R.layout.ship_item, parent, false);
                }
                GoodData o = goodArray.get(position);
                if (o != null) {
                        TextView tt = (TextView) v.findViewById(R.id.toptext);
                        TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                        if (tt != null) {
                              tt.setText("Good: "+ o.getGood().getName());                            }
                        if(bt != null){
                              bt.setText("Amount "+ o.getQuantity());
                        }
                }
                return v;
        }
    }
    
    private void getInventory(){
    	//This should fetch sInventory from the player/ship
    	//Currently just a test to see if the data structure works
    	// and demonstrates how to create/use the data
    	int startingCash = 1000;	//initializing cash
    	int startingCap = 15;		//initializing capacity
    	sInventory = new ShipInventory(startingCash, startingCap);
    	Good[] goodList = Good.getDataList();	//getting the predefined goods
    	sInventory.add(goodList[0], 5);	//storing 5 units of water
    	sInventory.add(goodList[1], 3); //3 units of furs
    	sInventory.add(goodList[5], 2); //2 units of firearms
    	
    }
    
}
