/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.nau.CS477.Classes;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.navigationdrawerexample.R;

import edu.nau.CS477.Contacts.ContactObject;
import edu.nau.CS477.Fragments.ChatFragment;
import edu.nau.CS477.Fragments.ContactsFragment;
import edu.nau.CS477.Fragments.FTPTransferFragment;
import edu.nau.CS477.Fragments.FileBrowserFragment;
import edu.nau.CS477.Fragments.SettingsFragment;


public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ContactsDatabaseHandler contactDB = new ContactsDatabaseHandler(this);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        if(contactDB.getAllContacts().size() == 0){
	        Log.d("Insert: ", "Inserting ..");
	        contactDB.addContact(new ContactObject("JD","Knapp","jdk85@nau.edu"));
	        contactDB.addContact(new ContactObject("Ben","Olayinka","bo97@nau.edu"));
	        contactDB.addContact(new ContactObject("Brandon","Phillips","bmp57@nau.edu"));
	        contactDB.addContact(new ContactObject("Mike","Stek","mikestek@nau.edu"));
        }
        else{
        	//Log.d("INSERT: ", "Not performed, DB is populated");
        }
        /*
        // How to read contacts and print to log
        Log.d("Reading: ", "Reading all contacts..");
        ArrayList<ContactObject> contacts = contactDB.getAllContacts();      
         
        for (ContactObject cn : contacts) {
            String log = "Id: "+cn.getId()+" ,Name: " + cn.getFullName() + " ,Email: " + cn.getEmailAddr();
                // Writing Contacts to
        Log.d("Name: ", log);
        }
        */
        contactDB.close();
        
        mTitle = mDrawerTitle = getTitle();
        mMenuItems = getResources().getStringArray(R.array.main_navigation_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
    	Bundle args = new Bundle();
        
    	Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new ContactsFragment();
            args.putInt(ContactsFragment.MENU_ITEM_NUMBER, position);
            fragment.setArguments(args);
            break;
        case 1:
            fragment = new ChatFragment();
            args.putInt(ChatFragment.MENU_ITEM_NUMBER, position);
            fragment.setArguments(args);
            break;
        case 2:
            fragment = new FileBrowserFragment();
            args.putInt(FileBrowserFragment.MENU_ITEM_NUMBER, position);
            fragment.setArguments(args);
            break;
        case 3:
            fragment = new FTPTransferFragment();
            args.putInt(FTPTransferFragment.MENU_ITEM_NUMBER, position);
            fragment.setArguments(args);
            break;
        case 4:
            fragment = new SettingsFragment();
            args.putInt(SettingsFragment.MENU_ITEM_NUMBER, position);
            fragment.setArguments(args);
            break;
 
        default:
            break;
        }
        
    	if (fragment != null) {
        	
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mMenuItems[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
        
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }




    
}