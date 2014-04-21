package edu.nau.CS477.Fragments;



import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

import edu.nau.CS477.Classes.ContactsDatabaseHandler;
import edu.nau.CS477.Classes.FileDialog;
import edu.nau.CS477.Classes.MainActivity;
import edu.nau.CS477.Classes.SelectionMode;
import edu.nau.CS477.Contacts.ContactObject;
import edu.nau.CS477.Contacts.NewContactActivity;

/**
 * Fragment that appears in the "content_frame"
 */
public class FileBrowserFragment extends Fragment {
    public static final String MENU_ITEM_NUMBER = "menu_number";
    public View rootView;
    private ContactObject co = null;
    private Button mSendFileBtn;
    
    public FileBrowserFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.file_browser_fragment, container, false);
        
        int i = getArguments().getInt(MENU_ITEM_NUMBER);
        String menuItem = getResources().getStringArray(R.array.main_navigation_menu)[i];
        getActivity().setTitle(menuItem);
        co = getArguments().getParcelable("contactObject");
        if(co != null){
        	((TextView) rootView.findViewById(R.id.textViewNameSend)).setText(co.getFullName());
        }
        return rootView;
    }
    
    public void onActivityCreated (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
        
    	final TextView mNameSend = (TextView) getActivity().findViewById(R.id.textViewNameSend);
        final ImageView mSendFile = (ImageView) getActivity().findViewById(R.id.fileSelectionIcon);
        mSendFile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), FileDialog.class);
                //intent.putExtra(FileDialog.START_PATH, "/sdcard");
                
                //can user select directories or not
                //intent.putExtra(FileDialog.CAN_SELECT_DIR, true);
                
                //alternatively you can set file filter
                //intent.putExtra(FileDialog.FORMAT_FILTER, new String[] { "png" });
    			
    			//rootView = inflater.inflate(R.layout.file_send_fragment_screen_detail,
        		//		container, false);
    			
    			intent.putExtra(FileDialog.SELECTION_MODE, SelectionMode.MODE_OPEN);
                
                startActivityForResult(intent, 1);
            }
            
        });
        
        final TextView mFileSendText = (TextView) getActivity().findViewById(R.id.textViewFileSend);
        mFileSendText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), FileDialog.class);                
    			
    			intent.putExtra(FileDialog.SELECTION_MODE, SelectionMode.MODE_OPEN);
                
                startActivityForResult(intent, 1);
            }
            
        });
        
        mNameSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final ContactsDatabaseHandler db = new ContactsDatabaseHandler(getActivity());
            	final ArrayList<ContactObject> contacts = db.getAllContacts();
            	CharSequence[] cs =  new CharSequence[contacts.size()];
            	for(int i = 0; i < contacts.size();i++){
            		cs[i] = contacts.get(i).getFullName();
            	}
            	 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			        builder.setTitle("Choose Contact");
			        //add click listeners to the dialog options
			        builder.setItems(cs, new DialogInterface.OnClickListener() {
			        	//TODO: add logic, pass co along to the selected option
			            @Override
			            public void onClick(DialogInterface dialog, int dialogPosition) {
			            	co = contacts.get(dialogPosition);
			            	if(co != null){
			                	((TextView) rootView.findViewById(R.id.textViewNameSend)).setText(co.getFullName());
			                }
			                
			            }
			        });
		        //display the dialog options
		        builder.show();
            	
            	db.close();
            }
            
        });

        
        mSendFileBtn = (Button)getActivity().findViewById(R.id.SendFileButton);
        mSendFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	        	Bundle args = new Bundle();
                        	Fragment fragment = null;
                        	fragment = new FTPTransferFragment();
                        	args.putInt(FTPTransferFragment.MENU_ITEM_NUMBER, 3);
                        	args.putParcelable("contactObject", co);
                        	args.putString("fileName", mFileSendText.getText().toString());
                            fragment.setArguments(args);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, fragment).commit();
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            break;
            	        }
            	    }
            	};
            	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            	
            	builder.setMessage("File transfer started. " +
            			"Press 'OK' to go to the FTP transfers or press 'Go Back' " +
            			"to return to the file browser menu").setPositiveButton("OK", dialogClickListener)
            	    .setNegativeButton("Go Back", dialogClickListener).show();
 
            }
        });
        
    	
    }
    
    public void onActivityResult(int requestCode, int resultCode,
            Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
            	String Path = data.getStringExtra(FileDialog.RESULT_PATH);
            	((TextView) rootView.findViewById(R.id.textViewFileSend))
    			.setText(Path);
            }
        }
    }
}

