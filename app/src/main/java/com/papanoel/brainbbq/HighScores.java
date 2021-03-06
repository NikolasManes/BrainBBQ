package com.papanoel.brainbbq;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.papanoel.brainbbq.data.PlayerContract.PlayerEntry;

public class HighScores extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYER_LOADER = 0;

    PlayerCursorAdapter mPlayerCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        ListView HighScoresList = findViewById(R.id.highScoreList);

        mPlayerCursorAdapter = new PlayerCursorAdapter(this, null);

        HighScoresList.setAdapter(mPlayerCursorAdapter);

        final Button MainMenuButton = findViewById(R.id.MainMenuButton);
        MainMenuButton.setOnClickListener(this);

        // Initialize Loader
        getLoaderManager().initLoader(PLAYER_LOADER, null, this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        i = new Intent(this, MainActivity.class);

        finish();
        startActivity(i);
    }
    @Override
    public void onBackPressed(){
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PlayerEntry._ID,
                PlayerEntry.COLUMN_PLAYER_NAME,
                PlayerEntry.COLUMN_HIGH_SCORE,
                PlayerEntry.COLUMN_TOP_LEVEL,
                PlayerEntry.COLUMN_LAST_GAME_SCORE,
                PlayerEntry.COLUMN_LAST_GAME_LEVEL};

        // Define the sort order
        String sortOrder = PlayerEntry.COLUMN_HIGH_SCORE + " DESC";

        // Return the Loader
        return new CursorLoader(this,       // CONTEXT:         The activity context
                PlayerEntry.CONTENT_URI,    // URI:             The URI to load data from
                projection,                 // PROJECTION:      Specify witch columns will be returned
                null,                       // SELECTION:       Filter witch rows will be returned (WHERE clause)
                null,                       // SELECTION ARGS:  The values for the selection
                sortOrder);                 // SHORT ORDER:     The order the entries will be shown

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Update the {@link PlayerCursorAdapter} with this new cursor containing the pet data
        mPlayerCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // When the data is deleted
        mPlayerCursorAdapter.swapCursor(null);
    }
}
