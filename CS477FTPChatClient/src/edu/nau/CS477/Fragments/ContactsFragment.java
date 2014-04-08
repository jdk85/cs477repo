package edu.nau.CS477.Fragments;



import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.android.navigationdrawerexample.R;

import edu.nau.CS477.Classes.ContactsDatabaseHandler;
import edu.nau.CS477.Contacts.ContactAdapter;
import edu.nau.CS477.Contacts.ContactObject;
import edu.nau.CS477.Contacts.NewContactActivity;

/**
 * Fragment that appears in the "content_frame"
 */
public class ContactsFragment extends Fragment {
	//MENU_ITEM_NUMBER used to ref 
    public static final String MENU_ITEM_NUMBER = "menu_number";
    //Options for the dialog box when a contact is selected
    private final CharSequence[] contactDialogMenu = new CharSequence[] {"Open Chat", "Send File", "View Transfers", "Edit Contact"};
    //ArrayList of displayed contacts
    ArrayList<ContactObject> contacts = new ArrayList<ContactObject>();
    //Init the ContactAdapter used to display contacts from the ArrayList contacts
    ContactAdapter contactAdapter = null;
    //Init the DatabaseHandler used to fetch contacts
    private ContactsDatabaseHandler db;
    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuItems;
    
    private ListView listview;
    private View mNewContactButton;
    
    //Empty constructor
    public ContactsFragment() {   	
    	
    }
    //Override onCreateView and inflate with contact list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//rootView holds the layout associated with the contacts menu
        View rootView = inflater.inflate(R.layout.contacts_fragment, container, false);
        //get the position of this menu object - Contacts is the first option
        int i = getArguments().getInt(MENU_ITEM_NUMBER);
        //get the menu options string at position i
        String menuItem = getResources().getStringArray(R.array.main_navigation_menu)[i];
        //set the activity title to Contacts 
        getActivity().setTitle(menuItem);
        //Return the view
        return rootView;
    }

    //fill the contact listview
    public void onActivityCreated (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	try{
    		//init the database handler
			db = new ContactsDatabaseHandler(getActivity()); 
			//init the contactAdapter (used to fill the ListView with the fields we're interested in)
			contactAdapter = new ContactAdapter(getActivity(), R.layout.contacts_list_item, db.getAllContacts());
			
			db.close();
			mNewContactButton = getActivity().findViewById(R.id.new_contact);
			mNewContactButton.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	Intent intent = new Intent(getActivity(), NewContactActivity.class);
	                startActivity(intent);
	            }
	        });
			//fetch the listview to be filled with contacts
	        listview = (ListView) getActivity().findViewById(R.id.list_container);
	        //associate the adapter
	        listview.setAdapter(contactAdapter); 
	        //there isn't actually any filtering logic yet
	        listview.setTextFilterEnabled(true);
	        //add onclick listeners to the contact list
	        listview.setOnItemClickListener(new OnItemClickListener()
	        {
		        public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
		        {
		                
			        //get the ContactObject of the clicked contact
			        final ContactObject co = (ContactObject)listview.getItemAtPosition(position);
			        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			        builder.setTitle(co.getFullName());
			        //add click listeners to the dialog options
			        builder.setItems(contactDialogMenu, new DialogInterface.OnClickListener() {
			        	//TODO: add logic, pass co along to the selected option
			            @Override
			            public void onClick(DialogInterface dialog, int dialogPosition) {
			            	Bundle args = new Bundle();			                
			            	Fragment fragment = null;
			            	
			                switch(dialogPosition){
			                //Open chat window with selected contact
			                case 0:
			                	fragment = new ChatFragment();
			                    args.putInt(ChatFragment.MENU_ITEM_NUMBER,1);
			                    args.putParcelable("contactObject", co);
			                    fragment.setArguments(args);
			                    break;
			                	
		                	//Open file browser to send file to selected contact 
			                case 1:
			                	 fragment = new FileBrowserFragment();
			                     args.putInt(FileBrowserFragment.MENU_ITEM_NUMBER, 2);
			                     args.putParcelable("contactObject", co);
			                     fragment.setArguments(args);
			                	break;
		                	//See if there are any currently transferring files to selected contact
			                case 2:
			                	fragment = new FTPTransferFragment();
			                    args.putInt(FTPTransferFragment.MENU_ITEM_NUMBER, 3);
			                    fragment.setArguments(args);
			                	break;
		                	//Edit the contact
			                case 3:
			                	break;
		                	default:
		                		break;
			                }
			                mTitle = mDrawerTitle = getActivity().getTitle();
			                mMenuItems = getActivity().getResources().getStringArray(R.array.main_navigation_menu);
			                mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
			                mDrawerList = (ListView) getActivity().findViewById(R.id.left_drawer);
			                
			                if (fragment != null) {
			                	
			                    FragmentManager fragmentManager = getFragmentManager();
			                    fragmentManager.beginTransaction()
			                            .replace(R.id.content_frame, fragment).commit();
			         
			                    // update selected item and title, then close the drawer
			                    mDrawerList.setItemChecked(dialogPosition, true);
			                    mDrawerList.setSelection(dialogPosition);
			                    getActivity().setTitle(mMenuItems[dialogPosition]);
			                    mDrawerLayout.closeDrawer(mDrawerList);
			                } else {
			                    // error in creating fragment
			                    Log.e("MainActivity", "Error in creating fragment");
			                }
			            }
			        });
		        //display the dialog options
		        builder.show();
	            }
	        });
	        
    	}catch(Exception e){
    		e.printStackTrace();
    		Log.d("Error:",e.toString());
    	}
    }
    
    
}

