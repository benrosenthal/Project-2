package com.brprog.hoodly;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper mHoodlyHelper;
    TextView mNameTextView;
    TextView mDescriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mHoodlyHelper = DatabaseHelper.getInstance(MainActivity.this);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        //instantiate

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, query + "", Toast.LENGTH_SHORT).show();

            Cursor c = mHoodlyHelper.getWordMatches(query, null);
            if (c == null) {
                Toast.makeText(MainActivity.this, "CURSOR NULL", Toast.LENGTH_SHORT).show();
            } else if (c.moveToFirst()) {
                while (c.isAfterLast() == false) {
                    Toast.makeText(MainActivity.this, "DB result " + c.getString(2), Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, c.getString(c.getColumnIndex(DatabaseHelper.COL_ITEM_DESCRIPTION)) + "", Toast.LENGTH_SHORT).show();
                    c.moveToNext();

                }

//      }      Toast.makeText(MainActivity.this, c.getString(0) + "", Toast.LENGTH_SHORT).show();
//            Toast.makeText(MainActivity.this, c.getColumnIndex(c.getString(0)) + "", Toast.LENGTH_SHORT).show();
//            mNameTextView.setText(c.getString(0));
            }

        }
    }
}