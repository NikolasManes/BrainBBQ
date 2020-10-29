package com.papanoel.brainbbq;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.papanoel.brainbbq.data.PlayerContract.PlayerEntry;

public class GameOver extends AppCompatActivity implements View.OnClickListener {

    String playerName;
    int playerHighScore = GameActivity.HIGH_SCORE;
    int playerTopLevel = GameActivity.TOP_LEVEL;

    TextView textScore;
    TextView text2;
    TextView gameOverTitle;
    EditText playerNameEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

        textScore = findViewById(R.id.score);
        text2 = findViewById(R.id.textView2);
        gameOverTitle = findViewById(R.id.game_over_title);
        playerNameEdit = findViewById(R.id.PlayerName);
        textScore.setText("Score: " + GameActivity.HIGH_SCORE);

        final Button RestartButton = findViewById(R.id.restart);
        final Button MainMenuButton = findViewById(R.id.mainMenu);
        final Button OKButton = findViewById(R.id.okButton);
        RestartButton.setOnClickListener(this);
        MainMenuButton.setOnClickListener(this);
        OKButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        Intent j;
        Intent k;
        i = new Intent(this, GameActivity.class);
        j = new Intent(this, MainActivity.class);
        k = new Intent(this, HighScores.class);

        switch (view.getId()) {
            case R.id.restart:
                finish();
                startActivity(i);
                break;
            case R.id.mainMenu:
                finish();
                startActivity(j);
                break;
            case R.id.okButton:
                try {
                    saveScore();
                    finish();
                    startActivity(k);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void saveScore(){
        playerName = playerNameEdit.getText().toString().trim();
        // Create a ContentValues object, where the column names are the keys and the player result the attributes
        ContentValues values = new ContentValues();
        values.put(PlayerEntry.COLUMN_PLAYER_NAME, playerName);
        values.put(PlayerEntry.COLUMN_HIGH_SCORE, playerHighScore);
        values.put(PlayerEntry.COLUMN_TOP_LEVEL, playerTopLevel);

        /***************************************************************/
        values.put(PlayerEntry.COLUMN_LAST_GAME_SCORE, playerHighScore);    // NOT READY YET
        values.put(PlayerEntry.COLUMN_LAST_GAME_LEVEL, playerTopLevel);     // NOT READY YET
        /***************************************************************/


        // Enter player details to the database by calling the {@link PlayerProvider#insert()} method
        Uri newUri = getContentResolver().insert(PlayerEntry.CONTENT_URI, values);

        // Check if the player successfully inserted to the database
        if (newUri != null) {
            // Insertion was successful
            Toast.makeText(getApplicationContext(), "Thanks, " + playerNameEdit.getText().toString() + "!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Error player details cannot be saved...", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed(){
    }
}