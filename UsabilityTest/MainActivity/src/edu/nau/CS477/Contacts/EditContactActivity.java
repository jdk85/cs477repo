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

public class EditContactActivity extends Activity {

	private Button mCreateButton;
	private Button mCancelButton;
	private Button mUpdateButton;
	private ContactsDatabaseHandler db;
	private ContactObject co;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contacts);
		Bundle bundle = this.getIntent().getExtras();
		bundle.setClassLoader(getClassLoader());
		if(bundle != null)
			co = (ContactObject) bundle.getParcelable("contactObject");
		
		EditText mFirstNameEdit = (EditText)findViewById(R.id.firstNameEdit);
    	EditText mLastNameEdit = (EditText)findViewById(R.id.lastNameEdit);
    	EditText mEmailEdit  = (EditText)findViewById(R.id.emailEdit);
    	
    	mFirstNameEdit.setText(co.getFirstName());
    	mLastNameEdit.setText(co.getLastName());
    	mEmailEdit.setText(co.getEmailAddr());
    	
    	//ContactObject co  = new ContactObject(mFirstNameEdit.getText().toString(),mLastNameEdit.getText().toString(),mEmailEdit.getText().toString());
    	
    	
		
		
		mCreateButton = (Button) this.findViewById(R.id.SubmitBtn);
		mCancelButton = (Button) this.findViewById(R.id.CancelBtn);
		mUpdateButton = (Button) this.findViewById(R.id.updateIP);
		
		mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	
            	/*AlertDialog.Builder builder = new AlertDialog.Builder(EditContactActivity.this);
		        builder.setTitle("Success!");
            	builder.setMessage(co.getFullName() + " has been updated");
            	builder.show();
            	Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
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
                        	EditText mIPEdit = (EditText)findViewById(R.id.ipEdit);
                        	
                        	co.setFirstName(mFirstNameEdit.getText().toString());
                        	co.setLastName(mLastNameEdit.getText().toString());
                        	co.setFullName(mFirstNameEdit.getText().toString() + " " + mLastNameEdit.getText().toString());
                        	co.setEmailAddr(mEmailEdit.getText().toString());
                        	co.setCurrentIP(mIPEdit.getText().toString());
                        	
                        	db = new ContactsDatabaseHandler(EditContactActivity.this);
                        	db.updateContact(co);
                        	db.close();
            	        	Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
                            startActivity(intent);
            	            break;

            	        case DialogInterface.BUTTON_NEGATIVE:
            	            break;
            	        }
            	    }
            	};
            	AlertDialog.Builder builder = new AlertDialog.Builder(EditContactActivity.this);
            	
            	builder.setMessage("Update " + co.getFullName() + "?").setPositiveButton("Update", dialogClickListener)
            	    .setNegativeButton("Cancel", dialogClickListener).show();
 
            }
        });
		mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
		mUpdateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO:
				/*
				 * This should activate the following code on the client 
				 * machine and return the IP result:
				 */
/*
try{
	String OS = System.getProperty("os.name").toLowerCase();
	if ((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ))
	{
    	Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netint : Collections.list(nets))
        {
	        if(netint.getDisplayName().equals("eth0"))
	        {
		        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
		        for (InetAddress inetAddress : Collections.list(inetAddresses)) 
		        {
			        ipAddr = inetAddress.toString();
			        ipAddr = ipAddr.substring(1);
			        return ipAddr;
		        }
	        }
        }
    }		
	else return java.net.InetAddress.getLocalHost().getHostAddress();
}catch (Throwable t) { 
	System.out.println("THROWN");
	return "localhost"; 
}

*/
				
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
