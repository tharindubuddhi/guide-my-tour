//this is one of the most important classes in the application which create and manupulate the database of the application
//all the code for create db, access data are here in this class.

package com.gmt;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Blob;
import java.sql.SQLData;

import org.apache.http.util.ByteArrayBuffer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class GMTDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "GMTDATABASE";
	private static final int DATABASE_VERSION = 4;
	private final Context mContext;

	public static class BeachCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, beachname, beachdiscription, latitude, longitude "+
			"FROM beach ";				

		private BeachCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {		
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {	
				return new BeachCursor(db, driver, editTable, query);				
			}
		}

		public int getColBeachId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColBeachName() {
			return getString(getColumnIndexOrThrow("beachname"));
		}

		public String getColBeachDiscription() {
			return getString(getColumnIndexOrThrow("beachdiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	public static class HotelCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, hotelname, hoteldiscription, latitude,longitude "+
			"FROM hotel";				

		private HotelCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new HotelCursor(db, driver, editTable, query);				
			}
		}

		public int getColHotelId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColHotelName() {
			return getString(getColumnIndexOrThrow("hotelname"));
		}

		public String getColHotelDiscription() {
			return getString(getColumnIndexOrThrow("hoteldiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	public static class ParkCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, parkname, parkdiscription, latitude,longitude "+
			"FROM park";				

		private ParkCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new ParkCursor(db, driver, editTable, query);				
			}
		}

		public int getColParkId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColParkName() {
			return getString(getColumnIndexOrThrow("parkname"));
		}
		
		public String getColParkDiscription() {
			return getString(getColumnIndexOrThrow("parkdiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	public static class WaterFallCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, waterfallname, waterfalldiscription, latitude,longitude "+
			"FROM waterfall";				

		private WaterFallCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new WaterFallCursor(db, driver, editTable, query);				
			}
		}

		public int getColWaterFallId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColWaterFallName() {
			return getString(getColumnIndexOrThrow("waterfallname"));
		}
		
		public String getColWaterFallDiscription() {
			return getString(getColumnIndexOrThrow("waterfalldiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	public static class MountainCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, mountainname, mountaindiscription, latitude,longitude "+
			"FROM mountain";				

		private MountainCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new MountainCursor(db, driver, editTable, query);				
			}
		}

		public int getColMountainId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColMountainName() {
			return getString(getColumnIndexOrThrow("mountainname"));
		}
		
		public String getColMountainDiscription() {
			return getString(getColumnIndexOrThrow("mountaindiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	public static class GardenCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, gardenname, gardendiscription, latitude,longitude "+
			"FROM garden";				

		private GardenCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new GardenCursor(db, driver, editTable, query);				
			}
		}

		public int getColGardenId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColGardenName() {
			return getString(getColumnIndexOrThrow("gardenname"));
		}
		
		public String getColGardenDiscription() {
			return getString(getColumnIndexOrThrow("gardendiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	public static class BirdWatchingCursor extends SQLiteCursor {

		private static final String Query = 
			"SELECT _id, birdname, birddiscription, latitude,longitude "+
			"FROM birdwatching";				

		private BirdWatchingCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new BirdWatchingCursor(db, driver, editTable, query);				
			}
		}

		public int getColBirdWatchingId() {
			return getInt(getColumnIndexOrThrow("_id"));
		}

		public String getColBirdWatchingName() {
			return getString(getColumnIndexOrThrow("birdname"));
		}
		
		public String getColBirdWatchingDiscription() {
			return getString(getColumnIndexOrThrow("birddiscription"));
		}
		public double getLatitude() {
			return getDouble(getColumnIndexOrThrow("latitude"));
		}
		public Double getLongitude() {
			return getDouble(getColumnIndexOrThrow("longitude"));
		}
	}
	
	public static class ImageCursor extends SQLiteCursor {
			
		private ImageCursor(SQLiteDatabase db, SQLiteCursorDriver driver,
				String editTable, SQLiteQuery query) {
			super(db, driver, editTable, query);
		}

		private static class Factory implements SQLiteDatabase.CursorFactory {
			@Override
			public Cursor newCursor(SQLiteDatabase db,
					SQLiteCursorDriver driver, String editTable,
					SQLiteQuery query) {
				return new ImageCursor(db, driver, editTable, query);				
			}
		}
		
		public byte[] getColImg() {
			return getBlob(getColumnIndexOrThrow("img"));
		}
	}
	public GMTDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("im in oncreate");
		String[] sql = mContext.getString(R.string.GMT_onCreate).split("\n");
		try {
			db.beginTransaction();
			execMultipleSql(db, sql);
			db.setTransactionSuccessful();			
		} catch (SQLException e) {
			Log.e("Error Creating db", e.toString());
			throw e;
		} finally {
			db.endTransaction();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("im in onupgrade");
		Log.w("LOG TAG", "Upgrading from " + oldVersion + "to " + newVersion);
		String[] sql = mContext.getString(R.string.GMT_onUpgrade).split("\n");
		try {
			db.beginTransaction();
			execMultipleSql(db, sql);
			db.setTransactionSuccessful();		
		} catch (SQLException e) {
			Log.e("Error Creating db", e.toString());
			throw e;
		} finally {
			db.endTransaction();
		}
		onCreate(db);

	}

	private void execMultipleSql(SQLiteDatabase db, String[] sql) {
		for (String s : sql) {
			if (s.trim().length() > 0)
				db.execSQL(s);
		}

	}
	public void modifyDatabase(){
		String[] sql = mContext.getString(R.string.GMT_Modify).split("\n");
		SQLiteDatabase db = getReadableDatabase();
		try {
			db.beginTransaction();
			execMultipleSql(db, sql);
			db.setTransactionSuccessful();		
		} catch (SQLException e) {
			Log.e("Error Creating db", e.toString());
			throw e;
		} finally {
			db.endTransaction();
		}
	}
	public BeachCursor getBeachDetails(){
		String sql = BeachCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		BeachCursor bc = (BeachCursor) d.rawQueryWithFactory(new BeachCursor.Factory(), sql, null,null);
		bc.moveToFirst();
		return bc;
		
	}
	public HotelCursor getHotelDetails(){
		String sql = HotelCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		HotelCursor bc = (HotelCursor) d.rawQueryWithFactory(new HotelCursor.Factory(), sql, null,null);
		bc.moveToFirst();
		return bc;
		
	}
	public ParkCursor getParkDetails(){
		String sql = ParkCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		ParkCursor pc = (ParkCursor) d.rawQueryWithFactory(new ParkCursor.Factory(), sql, null,null);
		pc.moveToFirst();
		return pc;
		
	}
	public WaterFallCursor getWaterFallDetails(){
		String sql = WaterFallCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		WaterFallCursor c = (WaterFallCursor) d.rawQueryWithFactory(new WaterFallCursor.Factory(), sql, null,null);
		c.moveToFirst();
		return c;
		
	}
	public MountainCursor getMountainDetails(){
		String sql = MountainCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		MountainCursor c = (MountainCursor) d.rawQueryWithFactory(new MountainCursor.Factory(), sql, null,null);
		c.moveToFirst();
		return c;
		
	}
	public BirdWatchingCursor getBirdWatchingDetails(){
		String sql = BirdWatchingCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		BirdWatchingCursor c = (BirdWatchingCursor) d.rawQueryWithFactory(new BirdWatchingCursor.Factory(), sql, null,null);
		c.moveToFirst();
		return c;
		
	}
	public GardenCursor getGardenDetails(){
		String sql = GardenCursor.Query;
		SQLiteDatabase d = getReadableDatabase();
		GardenCursor c = (GardenCursor) d.rawQueryWithFactory(new GardenCursor.Factory(), sql, null,null);
		c.moveToFirst();
		return c;
		
	}
	public void saveImage(String table,String id,String url1){
		try{
			Log.e("a", "im here");
		//where we want to download it from
		URL url = new URL(url1);  //http://example.com/image.jpg
		//open the connection
		System.setProperty("http.proxyHost", url1);
		System.setProperty("http.proxyPort", "8700");
		URLConnection ucon = url.openConnection();
		//buffer the download
//		File f = new File("travelicon.jpg");
		InputStream is = ucon.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is,128);
		ByteArrayBuffer baf = new ByteArrayBuffer(128);
		//get the bytes one by one
		int current = 0;
		while ((current = bis.read()) != -1) {
		        baf.append((byte) current);
		}

		//store the data as a ByteArray
		//db is a SQLiteDatabase object
		ContentValues updateImg = new ContentValues(); 
		updateImg.put("img",baf.toByteArray());
		getReadableDatabase().update(table, updateImg, "_id=?", new String[]{id});
		Log.e("a", "im done");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public ImageCursor getImage(String table,String id){

			String sql = "SELECT img FROM "+table+" WHERE _id = "+id;
			SQLiteDatabase db = getReadableDatabase();
			ImageCursor ic = (ImageCursor) db.rawQueryWithFactory(new ImageCursor.Factory(), sql, null,null);
			ic.moveToFirst();
			return ic;
	
	 }
//	public BeachCursor getLatLon(String table){
//		if(table.equals("hotel")){
//			String sql = HotelCursor.Query;
//			SQLiteDatabase d = getReadableDatabase();
//			HotelCursor pc = (HotelCursor) d.rawQueryWithFactory(new HotelCursor.Factory(), sql, null,null);
//			pc.moveToFirst();
//			return pc;
//		}else 
//			if(table.equals("beach")){
//			return getBeachDetails();
//		}else if(table.equals("park")){
//			String sql = ParkCursor.Query;
//			SQLiteDatabase d = getReadableDatabase();
//			ParkCursor pc = (ParkCursor) d.rawQueryWithFactory(new ParkCursor.Factory(), sql, null,null);
//			pc.moveToFirst();
//			return pc;
//		}else return null;			
// }
	public void updateTable(){
		ContentValues updateValues = new ContentValues();
		updateValues.put("hotelname", "Mandalay");
		getReadableDatabase().update("hotel", updateValues,"_id=?",new String[]{"14"});
		
	}
	
}
