package edu.nau.CS477.Chat;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class MessageAdapter extends ArrayAdapter<MessageObject> {
	
	private ArrayList<MessageObject> messages;

	// Pass along an ArrayList<ContactObject> to the ContactAdapter
	public MessageAdapter(Context context, int textViewResourceId, ArrayList<MessageObject> messages) {
		super(context, textViewResourceId, messages);
		this.messages = messages;
	}

	//Define view
	public View getView(int position, View convertView, ViewGroup parent){

		View view = convertView;

		// if view is null, inflate the layout
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.message_list_item, null);
		}

		//get the ContactObject at position
		MessageObject i = messages.get(position);

		if (i != null) {

			//References to the TextView items in contact_list_item
			TextView tvDateTime = (TextView) view.findViewById(R.id.chat_date_time);
			TextView tvMessage = (TextView) view.findViewById(R.id.chat_message_content);

			//fill TextView with items from ContactObject
			if (tvDateTime != null){
				tvDateTime.setText(i.getDate() + " - " + i.getTime());
			}
			if (tvMessage != null){
				tvMessage.setText(i.getMessage());
			}
			
		}

		// return to the activity
		return view;

	}

}
