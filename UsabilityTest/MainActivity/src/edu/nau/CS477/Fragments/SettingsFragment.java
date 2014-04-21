package edu.nau.CS477.Fragments;



import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;
import android.preference.*;
import android.preference.Preference.OnPreferenceClickListener;

import edu.nau.CS477.Classes.ChatsDatabaseHandler;
import edu.nau.CS477.Classes.ContactsDatabaseHandler;
import edu.nau.CS477.Contacts.ContactObject;

/**
 * Fragment that appears in the "content_frame"
 */
public class SettingsFragment extends PreferenceFragment {
    public static final String MENU_ITEM_NUMBER = "menu_number";
    private View mDeleteContactsButton;
    private View mDeleteMessagesButton;
    private ContactsDatabaseHandler db;
    private ChatsDatabaseHandler chatDB;
    Context thiscontext;
    public SettingsFragment() {
        
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.settings_fragment);
		// Find and assign clickListener to delete contact preference 'button'
    	Preference deleteContacts = findPreference("deleteContacts");
    	deleteContacts.setOnPreferenceClickListener(new OnPreferenceClickListener()
    	{
            @Override
			public boolean onPreferenceClick(Preference preference) {
            	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	        	db = new ContactsDatabaseHandler(getActivity());
            				for(ContactObject co : db.getAllContacts()){
            					db.deleteContact(co);
            				}
            				db.close();
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            //No button clicked
            	            break;
            	        }
            	    }
            	};
            	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            	
            	builder.setMessage("Delete All Contacts?").setPositiveButton("Yes", dialogClickListener)
            	    .setNegativeButton("No", dialogClickListener).show();
				return true;
		        
            }
        });
    	//Find and assign clickListener to delete message preference 'button'
    	Preference deleteMessages = findPreference("deleteMessages");
    	deleteMessages.setOnPreferenceClickListener(new OnPreferenceClickListener()
    	{
            @Override
			public boolean onPreferenceClick(Preference preference) {
            	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	        	chatDB = new ChatsDatabaseHandler(getActivity());
            	        	chatDB.deleteAllMessages();
            				chatDB.close();
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            //No button clicked
            	            break;
            	        }
            	    }
            	};
            	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            	
            	builder.setMessage("Delete All Messages?").setPositiveButton("Yes", dialogClickListener)
            	    .setNegativeButton("No", dialogClickListener).show();
				return true;
		        
            }
        });
        
					
    	
        
    
}
}


