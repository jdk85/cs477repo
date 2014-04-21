package com.nau.edu.CS477.Transfers;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.navigationdrawerexample.R;

public class TransferAdapter extends ArrayAdapter<TransferObject> {
	
	private ArrayList<TransferObject> transfers;

	// Pass along an ArrayList<ContactObject> to the ContactAdapter
	public TransferAdapter(Context context, int textViewResourceId, ArrayList<TransferObject> transfers) {
		super(context, textViewResourceId, transfers);
		this.transfers = transfers;
	}

	//Define view
	public View getView(int position, View convertView, ViewGroup parent){

		View view = convertView;

		// if view is null, inflate the layout
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.transfer_list_item, null);
		}

		//get the ContactObject at position
		TransferObject i = transfers.get(position);

		if (i != null) {

			//References to the TextView items in contact_list_item
			TextView transDateTime = (TextView) view.findViewById(R.id.transfer_date_time);
			TextView transName = (TextView)view.findViewById(R.id.transfer_name);
			TextView transFile = (TextView)view.findViewById(R.id.transfer_file);
			ProgressBar transProgress = (ProgressBar) view.findViewById(R.id.transfer_progress_bar);

			//fill TextView with items from ContactObject
			if (transDateTime != null){
				transDateTime.setText(i.getDate() + " - " + i.getTime());
			}
			if (transName != null){
				transName.setText("Transferring to " + i.getContactName());
			}
			if (transFile != null){
				transFile.setText(i.getFileName());
			}
			if (transProgress != null){
				transProgress.setProgress(i.getProgress());
			}
			
		}

		// return to the activity
		return view;

	}

}
