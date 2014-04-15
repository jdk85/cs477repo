package edu.nau.CS477.Chat;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import edu.nau.CS477.Contacts.ContactObject;

public class MessageObject implements Serializable, Parcelable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1784174332998404267L;
	private int id;
	private String contactName = null;
	private int contactID;
	private long dateTime;
	private String time = null;
	private String date = null;
	private String message = null;
	
	/*Should we include a string array for permissioning?
	 * Something where we'd allow you to edit your contact and add
	 * in absolute path to folders that you want to grant them access to
	 */
	
	public MessageObject(){
	
	}
	
	public MessageObject(ContactObject co, long dateTime, String time, String date, String message ){
		this.setContactName(co.getFullName());
		this.setContactID(co.getId());
		this.setDateTime(dateTime);
		this.setTime(time);
		this.setDate(date);
		this.setMessage(message);
		
		
	}

	

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}
