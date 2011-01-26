//this class will show a map plus which has a dropdown list of popular cities in sri lanka
//user can select a particular city and get the places within a distance of 100km from that city 

package com.gmt;

import java.util.List;
import java.util.Vector;

import com.gmt.GMTDatabase.BeachCursor;
import com.gmt.GMTDatabase.BirdWatchingCursor;
import com.gmt.GMTDatabase.GardenCursor;
import com.gmt.GMTDatabase.HotelCursor;
import com.gmt.GMTDatabase.MountainCursor;
import com.gmt.GMTDatabase.ParkCursor;
import com.gmt.GMTDatabase.WaterFallCursor;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;

public class CityList extends MapActivity {
	private MapView mpView;												//variable declaration
	private MapController mpcontrol;
	double lat, lon;
	MyGeoPoint point;
	Vector<MyGeoPoint> pointsToDraw = new Vector<MyGeoPoint>();
	List<Overlay>  listOfOverlays ;
	GMTDatabase dataBase = new GMTDatabase(this);
	String[] cities = new String[] { "Colombo", "Kandy", "Galle",
			"Kalutara", "Trinco", "Gampaha", "Matara", "Jaffna",
			"Batticalo", };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citymap);
		dataBase = new GMTDatabase(this);
		mpView = (MapView) findViewById(R.id.citymap);
		LinearLayout zoomlayout = (LinearLayout) findViewById(R.id.cityzoom);
		View zoomview = mpView.getZoomControls();
		zoomlayout.addView(zoomview, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mpView.displayZoomControls(true);
		mpcontrol = mpView.getController();		
		String coordinates[] = { "6.927467", "79.848358" };		
		lat = 6.927467;
		lon = 79.848358;
		point = new MyGeoPoint((int) (lat * 1e6), (int) (lon * 1e6),"Colombo","city");
		mpcontrol.animateTo(point);
		mpcontrol.setZoom(12);
		mpView.invalidate();
		
		Spinner cityList = (Spinner) findViewById(R.id.citySpinner);
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, cities);
		cityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cityList.setAdapter(cityAdapter);
		cityList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg,
					long arg3) {
				String city = null;
				switch (arg) {
				case 0:
					lat = 6.927467;
					lon = 79.848358;
					city = "Colombo";
					break;
				case 1:
					lat = 7.296961;
					lon = 80.638453;
					city = "Kandy";
					
					break;
				case 2:
					lat = 6.034158;
					lon = 80.216325;
					city = "Galle";
					break;
				case 3:
					lat = 6.579225;
					lon = 79.964728;
					city = "Kalutara";
					break;
				case 4:
					lat = 8.569150;
					lon = 81.232911;
					city = "Trinco";
					break;
				case 5:
					lat = 7.089894;
					lon = 79.999414;
					city = "Gampaha";
					break;
				case 6:
					lat = 5.949306;
					lon = 80.535347;
					city = "Matara";
					break;
				case 7:
					lat = 9.668333;
					lon = 80.006392;
					city = "Jaffna";
					break;
				case 8:
					lat = 7.716667;
					lon = 81.700000;
					city = "Batticalo";
					break;
				}
				point = new MyGeoPoint((int) (lat * 1e6), (int) (lon * 1e6),city,"city");		
				mpcontrol = mpView.getController();
				mpcontrol.animateTo(point);
				mpcontrol.setZoom(10);
				mpView.invalidate();	
				pointsToDraw.clear();
				pointsToDraw.add(point);
				selectPoints(point);
				MapOverlay mapOverlay = new MapOverlay();
				mapOverlay.setPoints(pointsToDraw);
				listOfOverlays = mpView.getOverlays();
				listOfOverlays.clear();
				listOfOverlays.add(mapOverlay);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	public void selectPoints(MyGeoPoint p1){
		double lat2=0,lon2=0; 
		String name = null,type= "beach";
		BeachCursor cursor = dataBase.getBeachDetails();	
		int size = cursor.getCount();
		double lat1 = p1.getLatitudeE6()/1e6;
		double lon1 = p1.getLongitudeE6()/1e6;
		
		for(int i=0;i<size;i++){
			 name = cursor.getColBeachName();
			 lat2 = cursor.getLatitude();
			 lon2 = cursor.getLongitude();
			cursor.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		cursor.close();
		
		HotelCursor hc = dataBase.getHotelDetails();	
		size = hc.getCount();
		type = "hotel";
		for(int i=0;i<size;i++){
			 name = hc.getColHotelName();
			 lat2 = hc.getLatitude();
			 lon2 = hc.getLongitude();
			hc.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		hc.close();
		
		ParkCursor pc = dataBase.getParkDetails();	
		size = pc.getCount();
		type = "park";
		for(int i=0;i<size;i++){
			 name = pc.getColParkName();
			 lat2 = pc.getLatitude();
			 lon2 = pc.getLongitude();
			pc.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		pc.close();
		
		WaterFallCursor wc = dataBase.getWaterFallDetails();	
		size = wc.getCount();
		type = "waterfall";
		for(int i=0;i<size;i++){
			 name = wc.getColWaterFallName();
			 lat2 = wc.getLatitude();
			 lon2 = wc.getLongitude();
			wc.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		wc.close();
		
		MountainCursor mc = dataBase.getMountainDetails();	
		size = mc.getCount();
		type = "mountain";
		for(int i=0;i<size;i++){
			 name = mc.getColMountainName();
			 lat2 = mc.getLatitude();
			 lon2 = mc.getLongitude();
			mc.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		mc.close();
		
		BirdWatchingCursor bwc = dataBase.getBirdWatchingDetails();	
		size = bwc.getCount();
		type = "birdwatching";
		for(int i=0;i<size;i++){
			 name = bwc.getColBirdWatchingName();
			 lat2 = bwc.getLatitude();
			 lon2 = bwc.getLongitude();
			bwc.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		bwc.close();
		
		GardenCursor gc = dataBase.getGardenDetails();	
		size = gc.getCount();
		type = "garden";
		for(int i=0;i<size;i++){
			 name = gc.getColGardenName();
			 lat2 = gc.getLatitude();
			 lon2 = gc.getLongitude();
			gc.moveToNext();
			double dlat = Math.toRadians(lat2-lat1);
			double dlon = Math.toRadians(lon2-lon1);
			double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
			double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
			double distance = 6371 *y;
			if(distance<70){
				pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
			}
		}
		gc.close();
	}
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	class MapOverlay extends Overlay {
		private Vector<MyGeoPoint> points;

		public Vector<MyGeoPoint> getPoints() {
			return points;
			
		}

		public void setPoints(Vector<MyGeoPoint> points) {
			this.points = points;
		}
	
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,long when) {
			super.draw(canvas, mapView, shadow, when);
			for(MyGeoPoint p : points){
				Point screenPt = new Point();
				Paint paint = new Paint();
				paint.setStrokeWidth(1);
				paint.setARGB(255, 0, 0, 0);
				paint.setStyle(Paint.Style.STROKE);
				mapView.getProjection().toPixels(p, screenPt);				
				if(p.getType().equals("city")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pushpin);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);					
				}else if(p.getType().equals("beach")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beach);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}else if(p.getType().equals("hotel")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hotel);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}else if(p.getType().equals("park")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.park);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}else if(p.getType().equals("waterfall")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waterfall);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}else if(p.getType().equals("mountain")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mountain);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}else if(p.getType().equals("birdwatching")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.birdwatching);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}else if(p.getType().equals("garden")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.garden);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				}
			}
			return true;
		}
	}
	class MyGeoPoint extends GeoPoint{
		private String name,type;
		public MyGeoPoint(int latitudeE6, int longitudeE6,String name,String type) {
			super(latitudeE6, longitudeE6);		
			this.name = name;
			this.type = type;
			
		}
		public String getName() {
			return name;
		}
		public String getType() {
			return type;
		}
	}
}
