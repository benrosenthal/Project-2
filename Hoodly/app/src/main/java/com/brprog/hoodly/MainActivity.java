package com.brprog.hoodly;

import android.app.SearchManager;
import android.content.Intent;
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

        mNameTextView = (TextView)findViewById(R.id.name_text_view);
        mDescriptionTextView = (TextView)findViewById(R.id.description_text_view);

        DatabaseHelper mHoodlyHelper = DatabaseHelper.getInstance(MainActivity.this);
        //Cursor cursor = mDb.query("Hoods", new String[] {"name"}, null, null, null, null, null);
       //mTextView.setText(hoodlyHelper.getDescriptionById(1));
        //mTextView.setText(cursor.getColumnName(0));
        //mNameTextView.setText(mHoodlyHelper.getDescriptionByName("Big Building"));
        mNameTextView.setText(mHoodlyHelper.getEntityAspectsByName("Big Building").get(0));
        mDescriptionTextView.setText(mHoodlyHelper.getEntityAspectsByName("Big Building").get(1));
        //handleIntent(getIntent());
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

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mHoodlyHelper.getDescriptionByName(query);
            Toast.makeText(MainActivity.this, "Searching for " + query, Toast.LENGTH_SHORT).show();
        }
    }
}
