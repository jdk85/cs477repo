package edu.nau.CS477.Contacts;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactObject implements Serializable, Parcelable{

	private static final long serialVersionUID = -8386098666918666048L;
	private int id;
	private String fullName = null;
	private String firstName = null;
	private String lastName = null;
	private String emailAddr = null;
	private String currentIP = null;
	
	/*Should we include a string array for permissioning?
	 * Something where we'd allow you to edit your contact and add
	 * in absolute path to folders that you want to grant them access to
	 */
	
	public ContactObject(){
	
	}
	public ContactObject(Parcel p){
		this.setId(p.readInt());
		this.setFirstName(p.readString());
		this.setLastName(p.readString());
		this.setFullName(p.readString());
		this.setEmailAddr(p.readString());
		this.setCurrentIP(p.readString());
	}
	public ContactObject(String firstName,String lastName, String emailAddr ){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmailAddr(emailAddr);
		this.setFullName(firstName + " " + lastName);
		
	}
	
	public ContactObject(int id,String firstName,String lastName, String emailAddr ){
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmailAddr(emailAddr);
		this.setFullName(firstName + " " + lastName);
		
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(getId());
		dest.writeString(getFirstName());
		dest.writeString(getLastName());
		dest.writeString(getFullName());
		dest.writeString(getEmailAddr());
		dest.writeString(getCurrentIP());
		
		
	}

	public String getCurrentIP() {
		return currentIP;
	}

	public void setCurrentIP(String currentIP) {
		this.currentIP = currentIP;
	}
	public static final Parcelable.Creator<ContactObject> CREATOR = new Creator<ContactObject>() {

	    public ContactObject createFromParcel(Parcel source) {

	        return new ContactObject(source);
	    }

		@Override
		public ContactObject[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}

	    

	};
}
