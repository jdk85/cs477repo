package edu.nau.CS477.Classes;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.nau.CS477.Chat.MessageObject;

public class ChatsDatabaseHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "chatDatabase";
 
    // Chat table name
    private static final String TABLE_CHATS = "chats";
 
    // Chat Table Columns names
    private static final String KEY_ID = "id";
    private static final String CONTACT_NAME = "fullName";
    private static final String CONTACT_ID = "contactID";
    private static final String DATE_TIME = "dateTime";
    private static final String TIME = "time";
    private static final String DATE = "date";
    private static final String MESSAGE = "message";
    
 
    public ChatsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHAT_TABLE = "CREATE TABLE " 
        		+ TABLE_CHATS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
        		+ CONTACT_NAME + " TEXT,"
                + CONTACT_ID + " INTEGER,"
                + DATE_TIME + " INTEGER,"
                + TIME + " TEXT,"
                + DATE + " TEXT,"
                + MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_CHAT_TABLE);
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHATS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new message
    public void addMessage(MessageObject msg) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, msg.getContactName());
        values.put(CONTACT_ID, msg.getContactID());
        values.put(DATE_TIME, msg.getDateTime());
        values.put(TIME, msg.getTime());
        values.put(DATE, msg.getDate());
        values.put(MESSAGE, msg.getMessage());
        
        // Inserting Row
        db.insert(TABLE_CHATS, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting all messages from a contact 
    public ArrayList<MessageObject> getMessage(int contactID) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
    	Cursor cursor = db.query(TABLE_CHATS, null, CONTACT_ID + " = " + contactID  , null, null, null, null);
    	if (cursor.moveToFirst()) {
            do {
                MessageObject msg = new MessageObject();
                msg.setContactName(cursor.getString(1));
                msg.setContactID(Integer.parseInt(cursor.getString(2)));
                msg.setDateTime(Long.parseLong(cursor.getString(3)));
                msg.setTime(cursor.getString(4));
                msg.setDate(cursor.getString(5));
                msg.setMessage(cursor.getString(6));             
                messages.add(msg);
            } while (cursor.moveToNext());
        }
        
    	db.close();
        return messages;
    }
     
 // Getting the last message from all contacts
    public ArrayList<MessageObject> getLatestMessageFromAllContacts(Context context) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor;
    	ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
    	
    	String lastMsgQuery = "select * from " + TABLE_CHATS + " group by " + CONTACT_ID + " order by " + DATE_TIME + " desc";
    	
        
        cursor = db.rawQuery(lastMsgQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MessageObject msg = new MessageObject();
                msg.setContactName(cursor.getString(1));
                msg.setContactID(Integer.parseInt(cursor.getString(2)));
                msg.setDateTime(Long.parseLong(cursor.getString(3)));
                msg.setTime(cursor.getString(4));
                msg.setDate(cursor.getString(5));
                msg.setMessage(cursor.getString(6));             
                messages.add(msg);
            } while (cursor.moveToNext());
        }
    	db.close();
    	return messages;
    }
   
    public ArrayList<MessageObject> getAllMessages() {
    	String getAllQuery = "SELECT  * FROM " + TABLE_CHATS;  
    	SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(getAllQuery, null);
    	ArrayList<MessageObject> messages = new ArrayList<MessageObject>();

        //Loop through contacts and create an ArrayList of each SQLite entry
        if (cursor.moveToFirst()) {
            do {
            	MessageObject msg = new MessageObject();
                msg.setContactName(cursor.getString(1));
                msg.setContactID(Integer.parseInt(cursor.getString(2)));
                msg.setDateTime(Long.parseLong(cursor.getString(3)));
                msg.setTime(cursor.getString(4));
                msg.setDate(cursor.getString(5));
                msg.setMessage(cursor.getString(6));             
                messages.add(msg);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return messages;
    }
    // Getting message Count
    public int getMessageCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_CHATS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close();
        // return count
        return cursor.getCount();
    }
    public void deleteAllMessages(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CHATS, null, null);
        db.close();
    }
    public void deleteMessage(MessageObject msg) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHATS, KEY_ID + " = ?",
                new String[] { String.valueOf(msg.getId()) });
        db.close();
    }
    
    
  
    
}
