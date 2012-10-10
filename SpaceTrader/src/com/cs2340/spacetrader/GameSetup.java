package com.cs2340.spacetrader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class GameSetup extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup);
    	NumberPicker t = (NumberPicker) findViewById(R.id.traderLevel);
    	NumberPicker p = (NumberPicker) findViewById(R.id.pilotLevel);
    	NumberPicker e = (NumberPicker) findViewById(R.id.engineerLevel);
    	NumberPicker f = (NumberPicker) findViewById(R.id.fighterLevel);
    	t.setMaxValue(16);
    	t.setMinValue(0);
    	p.setMaxValue(16);
    	p.setMinValue(0);
    	e.setMaxValue(16);
    	e.setMinValue(0);
    	f.setMaxValue(16);
    	f.setMinValue(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_setup, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkMakePlayer(View view)
    {
    	NumberPicker t = (NumberPicker) findViewById(R.id.traderLevel);
    	NumberPicker p = (NumberPicker) findViewById(R.id.pilotLevel);
    	NumberPicker e = (NumberPicker) findViewById(R.id.engineerLevel);
    	NumberPicker f = (NumberPicker) findViewById(R.id.fighterLevel);
    	
    	int levels = t.getValue()+p.getValue()+e.getValue()+f.getValue();
    	EditText feedbackArea = (EditText) findViewById(R.id.feedback_area);
    	if (levels > Player.startingPoints)
    	{
    		feedbackArea.setText(R.string.too_high_error);
    	}
    	else if (levels < Player.startingPoints)
    	{
    		feedbackArea.setText(R.string.too_low_error);
    	}
    	else
    	{
    	
    	}
    }
}
