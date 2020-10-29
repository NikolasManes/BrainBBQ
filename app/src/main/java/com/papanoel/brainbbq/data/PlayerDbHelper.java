package com.papanoel.brainbbq.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.papanoel.brainbbq.data.PlayerContract.PlayerEntry;

/**
 * Created by Nikolas on 13/4/2017.
 */

public class PlayerDbHelper extends SQLiteOpenHelper {

    // Database details
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HighScoresList.db";

    // Database commands
    public static final String CREATE_DATABASE = "CREATE TABLE "
            + PlayerEntry.TABLE_NAME + " ("
            + PlayerEntry._ID + " INTEGER PRIMARY KEY, "
            + PlayerEntry.COLUMN_PLAYER_NAME + " TEXT, "
            + PlayerEntry.COLUMN_LAST_GAME_LEVEL + " INTEGER, "
            + PlayerEntry.COLUMN_LAST_GAME_SCORE + " INTEGER, "
            + PlayerEntry.COLUMN_TOP_LEVEL + " INTEGER, "
            + PlayerEntry.COLUMN_HIGH_SCORE + " INTEGER)";

    public PlayerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
