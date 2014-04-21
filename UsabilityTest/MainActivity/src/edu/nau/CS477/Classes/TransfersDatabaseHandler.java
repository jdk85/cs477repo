package edu.nau.CS477.Classes;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nau.edu.CS477.Transfers.TransferObject;

import edu.nau.CS477.Chat.MessageObject;

public class TransfersDatabaseHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "transferDatabase";
 
    // Chat table name
    private static final String TABLE_TRANSFERS= "transfers";
 
    // Chat Table Columns names
    private static final String KEY_ID = "id";
    private static final String CONTACT_NAME = "fullName";
    private static final String CONTACT_ID = "contactID";
    private static final String DATE_TIME = "dateTime";
    private static final String TIME = "time";
    private static final String DATE = "date";
    private static final String FILE_NAME = "fileName";
    private static final String PROGRESS = "progress";
    
 
    public TransfersDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSFER_TABLE = "CREATE TABLE " 
        		+ TABLE_TRANSFERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
        		+ CONTACT_NAME + " TEXT,"
                + CONTACT_ID + " INTEGER,"                
        		+ FILE_NAME + " TEXT,"
                + DATE_TIME + " INTEGER,"
                + TIME + " TEXT,"
                + DATE + " TEXT,"
                + PROGRESS + "INTEGER" + ")";
        db.execSQL(CREATE_TRANSFER_TABLE);
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSFERS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new message
    public void addMessage(TransferObject trans) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, trans.getContactName());
        values.put(CONTACT_ID, trans.getContactID());
        values.put(DATE_TIME, trans.getDateTime());
        values.put(TIME, trans.getTime());
        values.put(DATE, trans.getDate());
        values.put(FILE_NAME, trans.getFileName());
        values.put(PROGRESS, trans.getProgress());
        
        // Inserting Row
        db.insert(TABLE_TRANSFERS, null, values);
        db.close(); // Closing database connection
    }


    public ArrayList<TransferObject> getAllTransfers() {
    	String getAllQuery = "SELECT  * FROM " + TABLE_TRANSFERS;  
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllQuery, null);
    	ArrayList<TransferObject> transfers = new ArrayList<TransferObject>();

        //Loop through contacts and create an ArrayList of each SQLite entry
        if (cursor.moveToFirst()) {
            do {
            	TransferObject trans = new TransferObject();
            	trans.setContactName(cursor.getString(1));
            	trans.setContactID(Integer.parseInt(cursor.getString(2)));
            	trans.setDateTime(Long.parseLong(cursor.getString(3)));
            	trans.setTime(cursor.getString(4));
            	trans.setDate(cursor.getString(5));
            	trans.setFileName(cursor.getString(5));
            	trans.setProgress(Integer.parseInt(cursor.getString(6)));             
                transfers.add(trans);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return transfers;
    }
    
    public void deleteAllTransfers(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_TRANSFERS, null, null);
        db.close();
    }
    public void deleteTransfer(TransferObject trans) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSFERS, KEY_ID + " = ?",
                new String[] { String.valueOf(trans.getId()) });
        db.close();
    }
    
    
  
    
}
