package com.cs2340.spacetrader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Launcher extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_launcher, menu);
        return true;
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    }
    
    public void startGameSetup(View view){
    	//Intent intent = new Intent(this, gameSetup.class);
    	//startActivity(intent);
    }
    
}
