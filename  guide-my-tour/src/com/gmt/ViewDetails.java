package com.gmt;

//this class will create the activity to show the user a details of the 
//place which has been selected by the previous interface
//this will show the title, an image, a discription of the place the user has been selected.

import java.io.IOException;

import com.gmt.GMTDatabase.ImageCursor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewDetails extends Activity {
	TextView nameText,detailText;
	ImageView img;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.viewdetails);
	Bundle b = getIntent().getExtras();
	String name = b.getString("name");
	String detail = b.getString("details");
	String type = b.getString("type");
	int id = b.getInt("id");
	nameText = (TextView) findViewById(R.id.nameText);
	detailText = (TextView) findViewById(R.id.detailText);
	nameText.setText(name);
	detailText.setText(detail);
	setImage(type,id);
	try {
		getBaseContext().setWallpaper(BitmapFactory.decodeResource(getResources(), R.drawable.wall));
	} catch (IOException e) {
//		 TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void setImage(String table,int id){							//read the database and set the image on the image view
	
	img = (ImageView) findViewById(R.id.ImageView01);
	GMTDatabase db = new GMTDatabase(this);
	ImageCursor ic = db.getImage(table,String.valueOf(id));
	byte[] byteArray = ic.getColImg();
	ic.close();
	db.close();
	if(byteArray!=null){
		img.setImageBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));	
	}else{
		img.setImageResource(R.drawable.photo_not_available);
	}
	
}
}
