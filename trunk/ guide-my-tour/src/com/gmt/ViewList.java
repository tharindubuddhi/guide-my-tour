//this class would give a list of places for a selected category in the previous interface.
//for example if the user has selected the beaches in the previous UI then this will show a list of beaches which are stored 
//in the application

package com.gmt;

import java.io.IOException;

import com.gmt.GMTDatabase.BeachCursor;
import com.gmt.GMTDatabase.BirdWatchingCursor;
import com.gmt.GMTDatabase.GardenCursor;
import com.gmt.GMTDatabase.HotelCursor;
import com.gmt.GMTDatabase.MountainCursor;
import com.gmt.GMTDatabase.ParkCursor;
import com.gmt.GMTDatabase.WaterFallCursor;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ViewList extends ListActivity {
	GMTDatabase db;								//varaible declaration
	String[] names, details;
	String type;
	Intent viewDetails;
	LinearLayout layout;
	Bitmap bmp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewlist);
		Bundle b = getIntent().getExtras();
		type = b.getString("type");
		db = new GMTDatabase(this);
		if (type.equals("beach")) {											//according to the selected category of the places 
			BeachCursor bc = db.getBeachDetails();							//in the previous search list this will show you the 
			names = new String[bc.getCount()];								//list of places according to that category
			details = new String[bc.getCount()];
			for (int i = 0; i < bc.getCount(); i++) {
				names[i] = bc.getColBeachName();
				details[i] = bc.getColBeachDiscription();
				bc.moveToNext();
			}
			bc.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
			
		} else if (type.equals("hotel")) {
			HotelCursor hc = db.getHotelDetails();
			names = new String[hc.getCount()];
			details = new String[hc.getCount()];
			for (int i = 0; i < hc.getCount(); i++) {
				names[i] = hc.getColHotelName();
				details[i] = hc.getColHotelDiscription();
				hc.moveToNext();			
			}
			hc.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		}else if (type.equals("park")){			
			ParkCursor pc = db.getParkDetails();		
			names = new String[pc.getCount()];
			details = new String[pc.getCount()];
			for (int i = 0; i < pc.getCount(); i++) {
				names[i] = pc.getColParkName();
				details[i] = pc.getColParkDiscription();
				pc.moveToNext();			
			}
			pc.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		}else if (type.equals("waterfall")){			
			WaterFallCursor wc = db.getWaterFallDetails();		
			names = new String[wc.getCount()];
			details = new String[wc.getCount()];
			for (int i = 0; i < wc.getCount(); i++) {
				names[i] = wc.getColWaterFallName();
				details[i] = wc.getColWaterFallDiscription();
				wc.moveToNext();			
			}
			wc.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		}else if (type.equals("mountain")){			
			MountainCursor mc = db.getMountainDetails();		
			names = new String[mc.getCount()];
			details = new String[mc.getCount()];
			for (int i = 0; i < mc.getCount(); i++) {
				names[i] = mc.getColMountainName();
				details[i] = mc.getColMountainDiscription();
				mc.moveToNext();			
			}
			mc.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		}else if (type.equals("birdwatching")){			
			BirdWatchingCursor c = db.getBirdWatchingDetails();		
			names = new String[c.getCount()];
			details = new String[c.getCount()];
			for (int i = 0; i < c.getCount(); i++) {
				names[i] = c.getColBirdWatchingName();
				details[i] = c.getColBirdWatchingDiscription();
				c.moveToNext();			
			}
			c.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		}else if (type.equals("garden")){			
			GardenCursor c = db.getGardenDetails();		
			names = new String[c.getCount()];
			details = new String[c.getCount()];
			for (int i = 0; i < c.getCount(); i++) {
				names[i] = c.getColGardenName();
				details[i] = c.getColGardenDiscription();
				c.moveToNext();			
			}
			c.close();
			setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, names));
		}
		
		db.close();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
			viewDetails = new Intent(getApplicationContext(), ViewDetails.class);
			viewDetails.putExtra("name", names[position]);
			viewDetails.putExtra("details", details[position]);	
			viewDetails.putExtra("type", type);	
			viewDetails.putExtra("id", position+1);
			startActivity(viewDetails);		
	}
}
