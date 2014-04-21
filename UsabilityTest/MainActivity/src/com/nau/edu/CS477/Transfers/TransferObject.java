package com.nau.edu.CS477.Transfers;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import edu.nau.CS477.Contacts.ContactObject;

public class TransferObject implements Serializable, Parcelable{

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
	private String fileName = null;
	private int progress;
	
	public TransferObject(){
	
	}
	
	public TransferObject(ContactObject co, long dateTime, String time, String date, String fileName, int progress ){
		this.setContactName(co.getFullName());
		this.setContactID(co.getId());
		this.setDateTime(dateTime);
		this.setTime(time);
		this.setDate(date);
		this.setFileName(fileName);
		this.setProgress(progress);
		
		
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

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
