package com.cs2340.spacetrader;

import java.util.ArrayList;

import com.cs2340.spacetrader.TradeView.GoodInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TradeAdapter extends ArrayAdapter<GoodInfo> {
	Context context;
	int layoutResourceId;
	GoodInfo data[] = null;
	
	public TradeAdapter(Context context, int layoutResourceId, GoodInfo[] data){
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		GoodHolder holder = null;
		
		if (row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new GoodHolder();
			holder.txtName = (TextView)row.findViewById(R.id.name_good);
			holder.txtBuyGood = (Button)row.findViewById(R.id.buy_good);
			holder.txtSellGood = (Button)row.findViewById(R.id.sell_good);
		
			row.setTag(holder);
		}
		else
		{
			holder = (GoodHolder)row.getTag();
		}
		
		String buyText = String.format("BUY $%d\n%d Available", data[position].buyPrice, data[position].planetAmount);
		String sellText = String.format("SELL $%d\n%d Available", data[position].sellPrice, data[position].shipAmount);
		
		final String gName = data[position].name;
		holder.txtName.setText(data[position].name);
		holder.txtBuyGood.setText(buyText);
		holder.txtSellGood.setText(sellText);
		
		holder.txtBuyGood.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View view) 
			{
				((TradeView)context).buy(gName);
			}
		});
		
		holder.txtSellGood.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View view) 
			{
				((TradeView)context).sell(gName);
			}
		});
		return row;
	}
	
	static class GoodHolder
	{
		TextView txtName;
		Button txtBuyGood;
		Button txtSellGood;
	}
}
