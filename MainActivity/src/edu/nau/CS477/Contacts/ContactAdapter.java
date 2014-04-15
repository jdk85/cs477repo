package edu.nau.CS477.Contacts;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class ContactAdapter extends ArrayAdapter<ContactObject> {
	
	private ArrayList<ContactObject> contacts;

	// Pass along an ArrayList<ContactObject> to the ContactAdapter
	public ContactAdapter(Context context, int textViewResourceId, ArrayList<ContactObject> contacts) {
		super(context, textViewResourceId, contacts);
		this.contacts = contacts;
	}

	//Define view
	public View getView(int position, View convertView, ViewGroup parent){

		View view = convertView;

		// if view is null, inflate the layout
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.contacts_list_item, null);
		}

		//get the ContactObject at position
		ContactObject i = contacts.get(position);

		if (i != null) {

			//References to the TextView items in contact_list_item
			TextView tvFullName = (TextView) view.findViewById(R.id.fullName);
			TextView tvEmail = (TextView) view.findViewById(R.id.emailAddr);

			//fill TextView with items from ContactObject
			if (tvFullName != null){
				tvFullName.setText(i.getFullName());				
			}
			if (tvEmail != null){
				tvEmail.setText(i.getEmailAddr());
			}
			
		}

		// return to the activity
		return view;

	}

}
