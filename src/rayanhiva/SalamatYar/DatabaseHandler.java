/**
 * 
 */
package rayanhiva.SalamatYar;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Administrator
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SalamatYar";

	// Contacts table name
	private static final String TABLE_USER_INFO = "UserInfo";

	// Contacts Table Columns names
	private static final String KEY_ID = "ID";
	private static final String KEY_PhoneNumber = "PhoneNumber";
	private static final String KEY_SIMSerial = "SIMSerial";
	private static final String KEY_MIME = "MIME";
	private static final String KEY_PASSWORD = "Password";

	// State Table
	private static final String TABLE_APP_STATE = "Application";
	private static final String APP_STATE_KEY_ID = "ID";
	private static final String APP_STATE_KEY_STATE = "State";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER_INFO + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_PhoneNumber + " TEXT,"
				+ KEY_SIMSerial + " TEXT," + KEY_MIME + " TEXT," + KEY_PASSWORD
				+ " TEXT" + ")";

		String CREATE_APP_TABLE = "CREATE TABLE " + TABLE_APP_STATE + "("
				+ APP_STATE_KEY_ID + " INTEGER PRIMARY KEY,"
				+ APP_STATE_KEY_STATE + " TEXT)";

		db.execSQL(CREATE_CONTACTS_TABLE);
		db.execSQL(CREATE_APP_TABLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_STATE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
		onCreate(db);
	}

	public void AddNewUserInfo(UserInfo userInfo) {
		if (GetUserInfoCount() == 0) {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_ID, 0); // Contact Name
			if (userInfo.getMIME() != "")
				values.put(KEY_MIME, userInfo.getMIME()); // Contact Name
			if (userInfo.getSIMID() != "")
				values.put(KEY_SIMSerial, userInfo.getSIMID()); // Contact Phone
																// Number
			if (userInfo.getPassword() != "")
				values.put(KEY_PASSWORD, userInfo.getPassword());
			if (userInfo.getPhoneNumber() != "")
				values.put(KEY_PhoneNumber, userInfo.getPhoneNumber());
			// Inserting Row
			db.insert(TABLE_USER_INFO, null, values);
			db.close(); // Closing database connection
		} else {
			UpdateUserInfo(userInfo);
		}
	}

	public void UpdateAppState(int state) {
		String countQuery = "SELECT  * FROM " + TABLE_APP_STATE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		if (count == 0) {
			ContentValues values = new ContentValues();
			values.put(APP_STATE_KEY_ID, 0);
			values.put(APP_STATE_KEY_STATE, state);
			db.insert(TABLE_APP_STATE, null, values);
			db.close();
		} else {
			ContentValues values = new ContentValues();
			values.put(APP_STATE_KEY_STATE, state);
			db.update(TABLE_APP_STATE, values, APP_STATE_KEY_ID + " = ?",
					new String[] { String.valueOf(0) });
		}
	}

	public int GetAppState() {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "Select * FROM " + TABLE_APP_STATE + " WHERE "
				+ APP_STATE_KEY_ID + " = 0 ";
		Cursor cursor = db.rawQuery(query, null);
		int state = -1;
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			state = Integer.parseInt(cursor.getString(1));
			cursor.close();
		}
		db.close();
		
		if(state == -1)
		{
			UpdateAppState(0);
		}
		return state;
	}

	public void UpdateUserInfo(UserInfo userInfo) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		if (userInfo.getMIME() != "")
			values.put(KEY_MIME, userInfo.getMIME()); // Contact Name
		if (userInfo.getSIMID() != "")
			values.put(KEY_SIMSerial, userInfo.getSIMID()); // Contact Phone
															// Number
		if (userInfo.getPassword() != "")
			values.put(KEY_PASSWORD, userInfo.getPassword());
		if (userInfo.getPhoneNumber() != "")
			values.put(KEY_PhoneNumber, userInfo.getPhoneNumber());
		// updating row
		db.update(TABLE_USER_INFO, values, KEY_ID + " = ?",
				new String[] { String.valueOf(0) });
	}

	public UserInfo GetUserInfo() {
		UserInfo info = new UserInfo();
		String query = "Select * FROM " + TABLE_USER_INFO + " WHERE " + KEY_ID
				+ " = 0 ";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			cursor.moveToFirst();
			info.setPhoneNumber(cursor.getString(1));
			info.setSIMID(cursor.getString(2));
			info.setMIME(cursor.getString(3));
			info.setPassword(cursor.getString(4));
			cursor.close();
		} else {
			info = null;
		}
		db.close();
		return info;
	}

	public int GetUserInfoCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER_INFO;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		// return count
		return count;
	}
}
