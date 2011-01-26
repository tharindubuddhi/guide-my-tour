//this class is to give the user a list of categories  
//of places which are stored in the application.

package com.gmt;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchList extends ListActivity {
	String[] categories = new String[]{"City","Beach","Hotel","Park","WaterFall","Mountain","Garden","BirdWatching"}; //create the list of categories
	Intent viewList,cityList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.searchlist);
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,categories));
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {					//when one category is selected this will run	
		switch(position){
		case 0:
			cityList = new Intent(getApplicationContext(),CityList.class);
			startActivity(cityList);
			break;
		case 1:System.out.println("1");
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "beach");
			startActivity(viewList);
		break;
		case 2:
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "hotel");
			startActivity(viewList);
			break;
		case 3:
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "park");
			startActivity(viewList);
			break;
		case 4:
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "waterfall");
			startActivity(viewList);
			break;
		case 5:
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "mountain");
			startActivity(viewList);
			break;
		case 6:
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "garden");
			startActivity(viewList);
			break;
		case 7:
			viewList = new Intent(getApplicationContext(), ViewList.class);
			viewList.putExtra("type", "birdwatching");
			startActivity(viewList);
			break;
		default:System.out.println("default");
		}				
	}
}
