package edu.nau.CS477.Contacts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.navigationdrawerexample.R;

import edu.nau.CS477.Classes.ContactsDatabaseHandler;
import edu.nau.CS477.Classes.MainActivity;

public class NewContactActivity extends Activity {

	private Button mCreateButton;
	private Button mCancelButton;
	private ContactsDatabaseHandler db;
	private ContactObject co;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_contacts);

		if (savedInstanceState == null) {

		}
		
		mCreateButton = (Button) this.findViewById(R.id.SubmitBtn);
		mCancelButton = (Button) this.findViewById(R.id.CancelBtn);
		mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	/*
            	AlertDialog.Builder builder = new AlertDialog.Builder(NewContactActivity.this);
		        builder.setTitle("Success!");
            	builder.setMessage(co.getFullName() + " has been added to contents");
            	builder.show();
            	Intent intent = new Intent(NewContactActivity.this, MainActivity.class);
                startActivity(intent);
                */
            	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	        switch (which){
            	        case DialogInterface.BUTTON_POSITIVE:
            	        	EditText mFirstNameEdit = (EditText)findViewById(R.id.firstNameEdit);
                        	EditText mLastNameEdit = (EditText)findViewById(R.id.lastNameEdit);
                        	EditText mEmailEdit  = (EditText)findViewById(R.id.emailEdit);
                        	ContactObject co  = new ContactObject(mFirstNameEdit.getText().toString(),mLastNameEdit.getText().toString(),mEmailEdit.getText().toString());
                        	db = new ContactsDatabaseHandler(NewContactActivity.this);
                        	db.addContact(co);
                        	db.close();
            	        	Intent intent = new Intent(NewContactActivity.this, MainActivity.class);
                            startActivity(intent);
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            break;
            	        }
            	    }
            	};
            	AlertDialog.Builder builder = new AlertDialog.Builder(NewContactActivity.this);
            	
            	builder.setMessage("Create " + co.getFullName() + "?").setPositiveButton("Update", dialogClickListener)
            	    .setNegativeButton("Cancel", dialogClickListener).show();
 
            }
        });
		mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	Intent intent = new Intent(NewContactActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
