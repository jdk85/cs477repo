package edu.nau.CS477.Fragments;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.navigationdrawerexample.R;
import com.nau.edu.CS477.Transfers.TransferAdapter;
import com.nau.edu.CS477.Transfers.TransferObject;

import edu.nau.CS477.Chat.MessageAdapter;
import edu.nau.CS477.Classes.TransfersDatabaseHandler;
import edu.nau.CS477.Contacts.ContactObject;

/**
 * Fragment that appears in the "content_frame"
 */
public class FTPTransferFragment extends Fragment {
    public static final String MENU_ITEM_NUMBER = "menu_number";
    private static ArrayList<TransferObject> transfers = new ArrayList<TransferObject>();
    private TransferAdapter transferAdapter = null;
    private ListView listview;
    private View rootView;
    
    public FTPTransferFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ftp_transfers_fragment, container, false);
        
        int i = getArguments().getInt(MENU_ITEM_NUMBER);
        String menuItem = getResources().getStringArray(R.array.main_navigation_menu)[i];
        getActivity().setTitle(menuItem);
        return rootView;
    }
    
    public void onActivityCreated (Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	//db = new TransfersDatabaseHandler(getActivity()); 
    	listview = (ListView) getActivity().findViewById(R.id.list_transfers);
    	
    	Date timeNow = new Date();
        SimpleDateFormat dateFmt = new SimpleDateFormat("MMMM d ");
        SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm a");
        Random rand = new Random();
        int progress= rand.nextInt(100) ;
        ContactObject co = getArguments().getParcelable("contactObject");
        String fileName = getArguments().getString("fileName");
        if(co != null && fileName != null){
	    	transfers.add(new TransferObject(co,timeNow.getTime(),timeFmt.format(timeNow), dateFmt.format(timeNow),fileName,progress));
	    }
        
        if(transfers.size() != 0){
        	transferAdapter = new TransferAdapter(getActivity(),R.layout.transfer_list_item,transfers);
			listview.setAdapter(transferAdapter); 
	    	listview.setTextFilterEnabled(true);
        }
			
		
    	
    }
}

