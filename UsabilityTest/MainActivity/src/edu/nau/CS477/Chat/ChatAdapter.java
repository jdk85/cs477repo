package edu.nau.CS477.Chat;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class ChatAdapter extends ArrayAdapter<MessageObject> {
	
	private ArrayList<MessageObject> messages;

	// Pass along an ArrayList<ContactObject> to the ContactAdapter
	public ChatAdapter(Context context, int textViewResourceId, ArrayList<MessageObject> messages) {
		super(context, textViewResourceId, messages);
		this.messages = messages;
	}

	//Define view
	public View getView(int position, View convertView, ViewGroup parent){

		View view = convertView;

		// if view is null, inflate the layout
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.chat_list_item, null);
		}

		//get the ContactObject at position
		MessageObject i = messages.get(position);

		if (i != null) {

			//References to the TextView items in contact_list_item
			TextView tvContact = (TextView) view.findViewById(R.id.chat_contact_display);
			TextView tvMessage = (TextView) view.findViewById(R.id.chat_last_message);
			TextView tvDateTime = (TextView) view.findViewById(R.id.chat_last_message_date_time);
			
			//fill TextView with items from ContactObject
			if (tvContact != null){
				tvContact.setText(i.getContactName());
			}
			if (tvMessage != null){
				tvMessage.setText(i.getMessage());
			}
			if (tvDateTime != null){
				tvDateTime.setText(i.getDate() + i.getTime());
			}
		}

		// return to the activity
		return view;

	}

}
