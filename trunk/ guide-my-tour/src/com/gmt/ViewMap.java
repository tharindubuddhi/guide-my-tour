//this is the class which shows the map of the Sri Lanka. this will detect the current location of the user and animate the 
//map location to that place. 
//It will contain a drop down list (spinner) which has 

package com.gmt;

import java.util.List;
import java.util.Vector;

import android.content.Context;								//all the import statements from android package
import android.database.sqlite.SQLiteCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.gmt.CityList.MapOverlay;
import com.gmt.CityList.MyGeoPoint;
import com.gmt.GMTDatabase.BeachCursor;
import com.gmt.GMTDatabase.BirdWatchingCursor;
import com.gmt.GMTDatabase.GardenCursor;
import com.gmt.GMTDatabase.HotelCursor;
import com.gmt.GMTDatabase.MountainCursor;
import com.gmt.GMTDatabase.ParkCursor;
import com.gmt.GMTDatabase.WaterFallCursor;
import com.google.android.maps.GeoPoint;					//all the import statements from google maps package
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class ViewMap extends MapActivity {									// the activity which creates the MapView of the application

	private MapView mapView;												//variable decleration
	private MapController mapController;
	private LocationManager locationManager;
	private LocationListener locationListener;
	String[] type = new String[] { "Beach", "Hotel", "Park",
			"Waterfall", "Mountain", "BirdWatching","Garden" };
	GMTDatabase dataBase = new GMTDatabase(this);
	private MyGeoPoint currentPoint = new MyGeoPoint((int)(6.927467*1e6), (int)(79.848358*1e6), "Colombo", "city");
	Vector<MyGeoPoint> pointsToDraw = new Vector<MyGeoPoint>();
	List<Overlay>  listOfOverlays ;
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.map);																//create the View of the Map
		mapView = (MapView) findViewById(R.id.mapView);
//		mapView.setStreetView(true); this is to set the view as the street view if wanted
		mapView.setBuiltInZoomControls(true);

		mapController = mapView.getController();
		mapController.setZoom(10);					//10 is the default zoom size for the view		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,					//avilable providers
				0, locationListener);
//		Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		currentPoint = new MyGeoPoint((int)loc.getLatitude(), (int)loc.getLongitude(), "", "city");
//		System.out.println(loc.getLatitude()+""+loc.getLongitude());
		
		Spinner cityList = (Spinner) findViewById(R.id.typespinner);
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, type);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cityList.setAdapter(cityAdapter);
		cityList.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg,
					long arg3) {	
				pointsToDraw.clear();
				double lat2=0,lon2=0; 
				int size=0;
				String name = null,type=null;
				switch (arg) {				
				case 0:										
					type= "beach";
					BeachCursor bc = dataBase.getBeachDetails();	
					size = bc.getCount();
					double lat1 = currentPoint.getLatitudeE6()/1e6;
					double lon1 = currentPoint.getLongitudeE6()/1e6;
					for(int i=0;i<size;i++){
						 name = bc.getColBeachName();
						 lat2 = bc.getLatitude();
						 lon2 = bc.getLongitude();
						bc.moveToNext();
						double dlat = Math.toRadians(lat2-lat1);
						double dlon = Math.toRadians(lon2-lon1);
						double x = Math.sin(dlat/2)*Math.sin(dlat/2)+Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(dlon/2)*Math.sin(dlon/2);
						double y = 2*Math.atan2(Math.sqrt(x),Math.sqrt(1-x));
						double distance = 6371 *y;
						if(distance<70){
							pointsToDraw.add(new MyGeoPoint((int) (lat2 * 1e6), (int) (lon2 * 1e6),name,type));
						}
					}
					bc.close();
					break;
				case 1:
					type= "hotel";
					HotelCursor hc = dataBase.getHotelDetails();	
					size = hc.getCount();
					lat1 = currentPoint.getLatitudeE6()/1e6;
					lon1 = currentPoint.getLongitudeE6()/1e6;
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
					break;
				case 2:
					type= "park";
					ParkCursor pc = dataBase.getParkDetails();	
					size = pc.getCount();
					lat1 = currentPoint.getLatitudeE6()/1e6;
					lon1 = currentPoint.getLongitudeE6()/1e6;
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
					break;
				case 3:
					type= "waterfall";
					WaterFallCursor wc = dataBase.getWaterFallDetails();	
					size = wc.getCount();
					lat1 = currentPoint.getLatitudeE6()/1e6;
					lon1 = currentPoint.getLongitudeE6()/1e6;
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
					break;
				case 4:
					type= "mountain";
					MountainCursor mc = dataBase.getMountainDetails();	
					size = mc.getCount();
					lat1 = currentPoint.getLatitudeE6()/1e6;
					lon1 = currentPoint.getLongitudeE6()/1e6;
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
					break;
				case 5:
					type= "birdwatching";
					BirdWatchingCursor bwc = dataBase.getBirdWatchingDetails();	
					size = bwc.getCount();
					lat1 = currentPoint.getLatitudeE6()/1e6;
					lon1 = currentPoint.getLongitudeE6()/1e6;
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
					break;
				case 6:
					type= "garden";
					GardenCursor gc = dataBase.getGardenDetails();	
					size = gc.getCount();
					lat1 = currentPoint.getLatitudeE6()/1e6;
					lon1 = currentPoint.getLongitudeE6()/1e6;
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
					break;
				}				
				pointsToDraw.add(currentPoint);
//				mapController = mapView.getController();
//				mapController.animateTo(currentPoint);
//				mapController.setZoom(10);
//				mapView.invalidate();								
				MapOverlay mapOverlay = new MapOverlay();
				mapOverlay.setPoints(pointsToDraw);
				listOfOverlays = mapView.getOverlays();
				listOfOverlays.clear();
				listOfOverlays.add(mapOverlay);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private class GPSLocationListener implements LocationListener {							//this is the class which uses to track the location of the device

		@Override
		public void onLocationChanged(Location location) {				//system will pass a location to this method when the current location has got changed.
			if (location != null) {
				currentPoint = new MyGeoPoint((int)(location.getLatitude()*1e6),					//create a Geo point using the detected location coordinates
						(int) (location.getLongitude()*1e6),"","city");
				mapController.animateTo(currentPoint);
				mapController.setZoom(10);    
				 mapView.invalidate();

				MapOverlay mapOverlay = new MapOverlay();
				pointsToDraw.clear();
				pointsToDraw.add(currentPoint);
				mapOverlay.setPoints(pointsToDraw);
				List<Overlay> listOfOverlays = mapView.getOverlays();
				listOfOverlays.clear();
				listOfOverlays.add(mapOverlay);

				String address = "Latitude " + location.getLatitude()
						+ " Longitude" + location.getLongitude();
				Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT)	//this will show the current location on a toast in the screen as soon as it get changed.
						.show();

			}

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}

	class MapOverlay extends Overlay {													//this class is used to draw the Geo point on the Map
		private Vector<MyGeoPoint> points;

		public Vector<MyGeoPoint> getPoints() {
			return points;
			
		}

		public void setPoints(Vector<MyGeoPoint> points) {
			this.points = points;
		}

		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow,					//the method used to draw the Geo point
				long when) {
			super.draw(canvas, mapView, shadow);
			for(MyGeoPoint p : points){
				Point screenPt = new Point();
				Paint paint = new Paint();
				paint.setStrokeWidth(1);
				paint.setARGB(255, 0, 0, 0);
				paint.setStyle(Paint.Style.STROKE);
				mapView.getProjection().toPixels(p, screenPt);
				
//				canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);				
				if(p.getType().equals("beach")){
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
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);
				}else if(p.getType().equals("waterfall")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waterfall);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);	
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);
				}else if(p.getType().equals("mountain")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mountain);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);	
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);
				}else if(p.getType().equals("garden")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.garden);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);	
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);
				}else if(p.getType().equals("city")){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pushpin);
					canvas.drawBitmap(bitmap, screenPt.x,screenPt.y,null);
					canvas.drawText(p.getName(), screenPt.x, screenPt.y, paint);
				}					
			}
			
			return true;
		}
@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		if(e.getAction()==1){
			GeoPoint p = mapView.getProjection().fromPixels((int)e.getX(),(int)e.getY());
			Toast.makeText(getBaseContext(),p.getLatitudeE6()/1E6+" , "+p.getLongitudeE6()/1E6,Toast.LENGTH_SHORT).show();
			//Log.e("a", p.getLatitudeE6()/1E6+" , "+p.getLongitudeE6()/1E6); these are log statements which hv used for the testing
		}
		return false;
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
