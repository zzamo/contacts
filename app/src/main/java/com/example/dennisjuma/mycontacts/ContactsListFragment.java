package com.example.dennisjuma.mycontacts;

import android.annotation.SuppressLint;
import android.content.CursorLoader;
import android.content.Loader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.app.LoaderManager;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by DennisJuma on 29/11/2017.
 */

public class ContactsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {



    /* Defines an array that contains column names to move from
    *the Cursor to the ListView.
     */

    @SuppressLint("InlineApi")

    private final static String[] FROM_COLUMNS = new String[]{
            Build.VERSION_CODES.HONEYCOMB ?
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                    ContactsContract.Contacts.DISPLAY_NAME
    };
    /*Defines an Array that contains resource ids for the layout views
    *that get the Cursor column contents. The id is pre-defined in
     *the andorid framework, so it is prefaced with android.R.id
     */

    private final static int[] TO_IDS = {
            android.R.id.text1
    };
    //Define global mutable variables
    //Define a ListView object

    ListView mContactsList;
    //Define variables for the contact the user selects
    //The contract's _ID value
    long mContactsId;
    //The contact's LOOKUP_KEY
    String mContactsKey;
    //A content URI for the selected contact
    Uri mContactUri;
    //An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;

    //Empty public constructor, required by the system
    public ContactsFragment() {}

    //A UI Fragment must inflate its view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the fragment Layout
        return inflater. inflate(R.layout.contact_list_fragment,container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Initializes the loader
        getLoaderManager().initLoader(0, null,this);

        //Gets the ListView from the view list of the parent activity
        mContactsList =
                (ListView) getActivity().findViewById(R.layout.contact_list_view);
        //Gets a CursorAdapter

        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contact_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        //Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        //set the item click listener to be the current fragment.
        mContactsList.setOnItemClickListener(this);
    }

    //The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for teh LOOKUP_KEY column
    private static void final int LOOKUP_KEY_INDEX = 1;

    //Defines a variable for the search string
    //Defines a variable for the search String
    private String mSearchString;
    //Defines tah array to hold values that replace the ?
    private String [] mSelectionArgs = {mSearchString};

@Override
    public void onItemClick(
            AdapterView<?> parent, View item, int position, long rowID) {
    //Get the Cursor
    Cursor cursor = parent.getAdapter().getCursor();
    //Move to the selected contact
    cursor.moveToPosition(position);
    // Gte the Id Value
    mContactsId = getLong(CONTACT_ID_INDEX);
    //GeT the selected LOOKUP KEY

    //Create the contacts content uri
    mContactUri = ContactsContract.Contacts.getLookupUri(mContactsId,mContactsKey);
    /*
    *You can use mContactUri as the content URI for retrieving
    * *the details for a contact
     */
}

    private long getLong(int contactIdIndex) {
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int loaderId,Bundle args) {
    /*
    *Make search string into pattern and
    *store it in the selection array

    mSelectionArgs[0] = "%" + mSearchString + "%";
    //Starts the query
    return new CursorLoader(
            getActivity(),
            ContactsContract.Contacts.CONTENT_URI,
            PROJECTION,
            SELECTION,
            mSelectionArgs,
            null
    );
}*/
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(cursor);

    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader) [
            //Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
@SuppressLint("InLinedApi")
    private static final String[] PROJECTION = {
        /*
        *The detail data row ID. To make a ListView work
        * this column is required.

         */
        ContactsContract.Data._ID,
        //This primarily display name
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                ContactsContract.Data.DISPLAY_NAME_PRIMARY :
                //The contract's _ID, to construct a content URI
                ContactsContract.Data.CONTACT_ID
        //The contract's lookup _KEY, to construct a content URI
        ContactsContract.Data.LOOKUP_KEY
      };
/*
Construct search criteria from the search string
 and EMAIL MIME type
 */
private static final String SELECTION =
        /*
        *Searches for an email address
        * *that matches the search string
         */
        ContactsContract.CommonDataKinds.Email.ADDRESS + "LIKE ?" + "AND" +
                /*
                *Searches for the MIME type that matches
                * the value of the constant
                * Email.CONTENT_ITEM_TYPE. Note the
                * single quote surrounding EMAIL.CONTENT_ITEM_TYPE.

                 */
                ContactsContract.Data.MIMETYPE + "= '" + ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE + "'";
                 String getmSearchString;
                String[] mSelectArgs = { "" };
                /*@Override
    public Loader<Cursor> onCreateLoader (int loaderId, Bundle args) {
                    //OPTIMAL: Makes search string into pattern
                    mSearchString = "%" + mSearchString + "%";
                    //puts the search string into the selection criteria
                mSelectionArgs[0] = mSearchString;
                //return new query
                    return new CursorLoader(
                            getActivity(),
                            ContactsContract.Data.CONTENT_URI,
                            PROJECTION,
                            SELECTION,
                            mSelectionArgs,
                            null
                    );
                } */
@Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
              /*
              *
              * Appends the search string to the base URI. Always
              * encode search strings to ensure they're in proper
              * format
               */
              Uri contentUri = Uri.withAppendedPath(
                      ContactsContract.Contacts.CONTENT_FILTER_URI,
                      Uri.encode(mSearchString));
              //start the query
                return new CursorLoader(
                        getActivity(),
                        contentUri,
                        PROJECTION,
                        null,
                        null,
                        null

                );

    }

}
