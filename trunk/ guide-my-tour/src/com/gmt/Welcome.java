//this class corresponds to the welcome acitivity which is gonna be displayed at first time when the application is started
//this will have two options which one directs to view the map and other would direct to show the stored information in the 
//application

package com.gmt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Welcome extends Activity {														//the class which creates the welcome screen
    /** Called when the activity is first created. */
	ImageButton viewMap,searchList;
	GMTDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);													//create the content
        db = new GMTDatabase(this);
        //the following code used to store a image on the database file
//        db.saveImage("birdwatching", "1","http://www.lanka.com/sri-lanka/images/431-2-200-134-udawattekele-bird-sanctuary.jpg");         
//        db.modifyDatabase();
        viewMap = (ImageButton) findViewById(R.id.viewMap);
        
        viewMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {													//call the Map interface
				Intent viewMapIntent = new Intent(getApplicationContext(),ViewMap.class);
				startActivity(viewMapIntent);
			}
		});
        
        searchList = (ImageButton) findViewById(R.id.searchList);
        searchList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent searchListIntent = new Intent(getApplicationContext(),SearchList.class);	//call the searchList interface
				startActivity(searchListIntent);
			}
		});
        
    }
}