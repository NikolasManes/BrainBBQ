package com.papanoel.brainbbq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstancePhase) {
        super.onCreate(savedInstancePhase);
        setContentView(R.layout.activity_main);

        // Initialize textViews and buttons
        TextView tittle1 = (TextView)findViewById(R.id.tittle1);
        TextView tittle2 = (TextView)findViewById(R.id.tittle2);
        Button NewGameButton = (Button)findViewById(R.id.NewGameButton);
        Button HighScoresButton = (Button)findViewById(R.id.HighScoresButton);
        Button QuitButton = (Button)findViewById(R.id.QuitButton);

        // Attach the click listener to the buttons
        NewGameButton.setOnClickListener(this);
        HighScoresButton.setOnClickListener(this);
        QuitButton.setOnClickListener(this);

    }

    // Create the on click method to set the App behavior according witch button clicked
    @Override
    public void onClick(View view) {
        // There are 3 button at the activity but only 2 lead us to other activities...
        // So only 2 intents needed.
        Intent i, j;
        // Intent to start a new game
        i = new Intent(this, GameActivity.class);
        // Intent to view the highScores
        j = new Intent(this, HighScores.class);

        switch (view.getId()){
            // User wants to start a new game
            case R.id.NewGameButton:
                startActivity(i);       // start game activity
                finish();               // end current activity
                break;
            // User wants to view the highScores
            case R.id.HighScoresButton:
                startActivity(j);       // start highScores activity
                finish();               // end current activity
                break;
            // User wants to quit the App
            case R.id.QuitButton:
                finish();               // end current activity
                break;
        }
    }
    // The Application's response when the back button is pressed
    // Left blank no need to perform any action
    @Override
    public void onBackPressed(){
    }
}
