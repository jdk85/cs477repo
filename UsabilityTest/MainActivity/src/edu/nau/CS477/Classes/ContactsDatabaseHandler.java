package edu.nau.CS477.Classes;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.nau.CS477.Contacts.ContactObject;

public class ContactsDatabaseHandler extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsDatabase";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FULL_NAME = "fullName";
    private static final String EMAIL_ADDR = "emailAddr";
    private static final String IP_ADDR = "ipAddr";
 
    public ContactsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " 
        		+ TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," 
        		+ FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
        		+ FULL_NAME + " TEXT,"
                + EMAIL_ADDR + " TEXT," 
        		+ IP_ADDR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new contact
    public void addContact(ContactObject contact) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, contact.getFirstName()); // First Name
        values.put(LAST_NAME, contact.getLastName()); // Last Name
        values.put(FULL_NAME, contact.getFullName()); // Full Name
        values.put(EMAIL_ADDR, contact.getEmailAddr()); // Email Address
        values.put(IP_ADDR, contact.getCurrentIP()); //IP Address
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
     
    // Getting single contact
    public ContactObject getContact(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_ID,
                FIRST_NAME,LAST_NAME, EMAIL_ADDR }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        ContactObject contact = new ContactObject(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),cursor.getString(3));

        return contact;
    }
     
    // Getting All Contacts
    public ArrayList<ContactObject> getAllContacts() {
    	ArrayList<ContactObject> contactList = new ArrayList<ContactObject>();

    	SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, FIRST_NAME + " ASC");
        //Loop through contacts and create an ArrayList of each SQLite entry
        if (cursor.moveToFirst()) {
            do {
                ContactObject contact = new ContactObject();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setFirstName(cursor.getString(1));
                contact.setLastName(cursor.getString(2));
                contact.setFullName(cursor.getString(3));
                contact.setEmailAddr(cursor.getString(4));
                contact.setCurrentIP(cursor.getString(5));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return contactList;
    }
     
    // Getting contacts Count
    public int getContactsCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        
 
        // return count
        return cursor.getCount();
    }
    //TODO: this will probably be changed to accept which column to update rather than the whole entry
    public int updateContact(ContactObject contact){
    	SQLiteDatabase db = this.getWritableDatabase();    	 
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME, contact.getFirstName()); // First Name
        values.put(LAST_NAME, contact.getLastName()); // Last Name
        values.put(FULL_NAME, contact.getFullName()); // Full Name
        values.put(EMAIL_ADDR, contact.getEmailAddr()); // Email Address
        values.put(IP_ADDR, contact.getCurrentIP()); // IP Address
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }
     
    // Deleting single contact
    public void deleteContact(ContactObject contact) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
    
  
    
}
