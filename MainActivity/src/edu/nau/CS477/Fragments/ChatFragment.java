package edu.nau.CS477.Fragments;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.android.navigationdrawerexample.R;

import edu.nau.CS477.Chat.ChatAdapter;
import edu.nau.CS477.Chat.MessageAdapter;
import edu.nau.CS477.Chat.MessageObject;
import edu.nau.CS477.Classes.ChatsDatabaseHandler;
import edu.nau.CS477.Classes.ContactsDatabaseHandler;
import edu.nau.CS477.Contacts.ContactObject;

/**
 * Fragment that appears in the "content_frame"
 */
public class ChatFragment extends Fragment {
    public static final String MENU_ITEM_NUMBER = "menu_number";
    private ContactObject co = null;
    private ChatsDatabaseHandler db;
    private ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
    private MessageAdapter messageAdapter = null;
    private ChatAdapter chatAdapter = null;
    private ListView listview;
    private View rootView;
    
    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuItems;

    
    public ChatFragment() {
        
    }
    
        
        
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_fragment, container, false);
        int i = getArguments().getInt(MENU_ITEM_NUMBER);
        String menuItem = getResources().getStringArray(R.array.main_navigation_menu)[i];
        co = getArguments().getParcelable("contactObject");
        if(co != null){
        	getActivity().setTitle(menuItem + " with " + co.getFullName());
        	
        }
        else 
        	getActivity().setTitle(menuItem);
       
        return rootView;
    }
    
    public void onActivityCreated (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	final Button mSendMessage = (Button) getActivity().findViewById(R.id.sendBtn);
        mSendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Date timeNow = new Date();
                SimpleDateFormat dateFmt = new SimpleDateFormat("MMMM d ");
                SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm a");
                EditText chatET = (EditText) getActivity().findViewById(R.id.chatET);
                //Create message
                
            	MessageObject msg = new MessageObject(co,timeNow.getTime(),timeFmt.format(timeNow), dateFmt.format(timeNow), chatET.getText().toString());
            	//Upload to database
            	db = new ChatsDatabaseHandler(getActivity());
            	db.addMessage(msg);
            	//Display - refresh display
            	messages = db.getMessage(co.getId());
    			db.close();
				
				messageAdapter = new MessageAdapter(getActivity(), R.layout.message_list_item, messages);
    			//fetch the listview to be filled with contacts    				
    	        listview = (ListView) getActivity().findViewById(R.id.list_messages);
    	        //associate the adapter
    	        listview.setAdapter(messageAdapter); 
    	        //there isn't actually any filtering logic yet
    	        listview.setTextFilterEnabled(true);
    			
            	chatET.setText("");
            	
            }
            
        });
        
        //onclick listener for file sending button
        final ImageButton mSendFile = (ImageButton) getActivity().findViewById(R.id.fileSendButton);
        mSendFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Bundle args = new Bundle();
            	Fragment fragment = null;
            	fragment = new FileBrowserFragment();
            	args.putParcelable("contactObject", co);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment).commit();
            }
            
        });
        
        try{
        	db = new ChatsDatabaseHandler(getActivity()); 
        	listview = (ListView) getActivity().findViewById(R.id.list_messages);
    		if(co != null){    			
    			messages = db.getMessage(co.getId());
    			if(messages.size() != 0){
    				messageAdapter = new MessageAdapter(getActivity(), R.layout.message_list_item, messages);
	    	        //associate the adapter
	    	        listview.setAdapter(messageAdapter); 
	    	        //there isn't actually any filtering logic yet
	    	        listview.setTextFilterEnabled(true);
    			}
    		}
			else {				
				messages = db.getLatestMessageFromAllContacts(getActivity()); 
				if(messages.size() != 0){
					getActivity().findViewById(R.id.message_results).setVisibility(View.GONE);
					getActivity().findViewById(R.id.ChatMessageInput).setVisibility(View.VISIBLE);
    				chatAdapter = new ChatAdapter(getActivity(), R.layout.chat_list_item, messages);
	    	        //associate the adapter
	    	        listview.setAdapter(chatAdapter); 
	    	        //there isn't actually any filtering logic yet
	    	        listview.setTextFilterEnabled(true);
	    	        listview.setOnItemClickListener(new OnItemClickListener()
	    	        {
	    		        public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
	    		        {
	    		                
	    			        //get the ContactObject of the clicked chat 
	    		        	final MessageObject mo = (MessageObject)listview.getItemAtPosition(position);
	    		        	ContactsDatabaseHandler db = new ContactsDatabaseHandler(getActivity());	    		        	
	    			        final ContactObject co = db.getContact(mo.getContactID());
	    			        db.close();
							Bundle args = new Bundle();			                
							Fragment fragment = new ChatFragment();
							args.putInt(ChatFragment.MENU_ITEM_NUMBER,1);
							    args.putParcelable("contactObject", co);
							        fragment.setArguments(args);
							        mTitle = mDrawerTitle = getActivity().getTitle();
							        mMenuItems = getActivity().getResources().getStringArray(R.array.main_navigation_menu);
							        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
							        mDrawerList = (ListView) getActivity().findViewById(R.id.left_drawer);
							        
							        if (fragment != null) {
							        	
							            FragmentManager fragmentManager = getFragmentManager();
							            fragmentManager.beginTransaction()
							                    .replace(R.id.content_frame, fragment).commit();
							 
							            // update selected item and title, then close the drawer
							mDrawerList.setItemChecked(position, true);
							mDrawerList.setSelection(position);
							getActivity().setTitle(mMenuItems[position]);
							mDrawerLayout.closeDrawer(mDrawerList);
							        }
	    		        
    			}
    				
    					
    		});
				}
				//No messages
				else{
					getActivity().findViewById(R.id.message_results).setVisibility(View.VISIBLE);
					getActivity().findViewById(R.id.ChatMessageInput).setVisibility(View.GONE);
				}
			}
				

    			db.close();
    		
    		
    	}catch(Exception e){
    		Log.d("Exception: ",e.toString());
    	}
    	
    }
    

}

