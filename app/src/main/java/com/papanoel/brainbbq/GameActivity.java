package com.papanoel.brainbbq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener, Animation.AnimationListener {

    // Game Variables
    private int numA;
    private int numB;
    private int wrong1;
    private int wrong2;
    int wrong3;
    private int correct;
    private int lives = 3;
    private int exp = 1;
    public int level = 1;
    public int score = 0;
    public static int HIGH_SCORE = 0;
    public static int TOP_LEVEL = 1;
    private boolean gameOn;
    private String operator;
    private final Random random = new Random();
    private Button buttonChoice1;
    private Button buttonChoice2;
    private Button buttonChoice3;
    private TextView textNumA;
    private TextView textNumB;
    private TextView textOpp;
    private TextView textScore;
    private TextView textLives;
    private TextView textLevel;
    private TextView textTime;
    private TextView textEqual;
    private ImageView resultImg;
    private CountDownTimer timer;
    private Animation endTurn;
    private boolean clickEnable = true;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameOn = true;

        // Link the Strings on buttons or textView classes
        textNumA = findViewById(R.id.numA);
        textNumB = findViewById(R.id.numB);
        textOpp = findViewById(R.id.opperator);
        textEqual = findViewById(R.id.equal);
        textLives = findViewById(R.id.lives);
        textLevel = findViewById(R.id.level);
        textScore = findViewById(R.id.score);
        textTime = findViewById(R.id.time);
        resultImg = findViewById(R.id.result_img);
        buttonChoice1 = findViewById(R.id.choice1);
        buttonChoice2 = findViewById(R.id.choice2);
        buttonChoice3 = findViewById(R.id.choice3);
        endTurn = AnimationUtils.loadAnimation(this, R.anim.end_turn);
        endTurn.setAnimationListener(this);
        buttonChoice1.setOnClickListener(this);
        buttonChoice2.setOnClickListener(this);
        buttonChoice3.setOnClickListener(this);

        setQuestion();
    }

    @Override
    public void onClick(View view) {
        if(!clickEnable){
            return;
        }
        clickEnable = false;
        // Declare a new int to be used in all the cases
        int answer = 0;

        switch (view.getId()) {
            case R.id.choice1 :
                // Initialize a new int with the value contained in button
                answer = Integer.parseInt("" + buttonChoice1.getText());
                break;
            case R.id.choice2:
                // Initialize a new int with the value contained in button
                answer = Integer.parseInt("" + buttonChoice2.getText());
                break;
            case R.id.choice3:
                // Initialize a new int with the value contained in button
                answer = Integer.parseInt("" + buttonChoice3.getText());
                break;
        }

        update(answer);

    }

    private void setQuestion() {
        resultImg.setVisibility(View.GONE);
        // Set the Timer...
        timer = new CountDownTimer(10000 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textTime.setText("" + millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                update(wrong3);
            }
        };

        // Creating the question...
        int ope = random.nextInt(4);
        switch (ope) {
            // Multiplication...
            case 0: {
                operator = "x";
                numA = random.nextInt(10 + (5 * level)) + 1;
                numB = random.nextInt(5) + level;
                correct = numA * numB;
                break;
            }
            // Addition...
            case 1: {
                operator = "+";
                numA = random.nextInt(25 * level) + level;
                numB = random.nextInt(25 * level) + level;
                correct = numA + numB;
                break;
            }
            // Subtraction...
            case 2: {
                operator = "-";
                numA = random.nextInt(25 * level) + 3;
                numB = random.nextInt(numA - 1);
                correct = numA - numB;
                break;
            }
            // Division...
            case 3: {
                operator = "/";
                numB = random.nextInt(5) + level + 1;
                numA = numB * (random.nextInt(10) + level + 1);
                correct = numA / numB;
                break;
            }
        }
        int[] ArrayWrong = {correct + 1, correct + 2, correct + 3, correct + 4, correct + 5, correct - 1, correct - 2, correct - 3, correct - 4, correct - 5, correct + 6, correct + 7, correct - 6, correct - 7, correct + 8, correct + 9, correct + 10, correct - 8, correct - 9, correct - 10};
        wrong1 = ArrayWrong[random.nextInt(19)];
        wrong2 = ArrayWrong[random.nextInt(19)];
        if (wrong1 < 0) {
            wrong1 = wrong1 + 10;
        }
        if (wrong2 < 0) {
            wrong2 = wrong2 + 10;
        }
        if (wrong1 == wrong2) {
            wrong2 = wrong2 + 1;
        }

        textNumA.setText("" + numA);
        textNumB.setText("" + numB);
        textOpp.setText("" + operator);
        textEqual.setText("=");

        // 3 random Choices...
        switch (random.nextInt(2)) {
            case 0 :
                buttonChoice1.setText("" + correct);
                buttonChoice2.setText("" + wrong1);
                buttonChoice3.setText("" + wrong2);
                break;
            case 1:
                buttonChoice1.setText("" + wrong1);
                buttonChoice2.setText("" + correct);
                buttonChoice3.setText("" + wrong2);
                break;
            case 2:
                buttonChoice1.setText("" + wrong1);
                buttonChoice2.setText("" + wrong2);
                buttonChoice3.setText("" + correct);
                break;
        }
        timer.start();
    }

    private void update(int answer) {
        if (isCorrect(answer)) {
            score = score + level * 10;
            exp++;
            if (exp == 3) {
                level++;
                exp = 0;
            }
        }else {
            lives--;
        }
        textScore.setText("Score: " + score);
        textLives.setText("Lives: " + lives);
        textLevel.setText("Level: " + level);
        resultImg.startAnimation(endTurn);
    }

    private boolean isCorrect(int answer) {
        if (answer == correct) {
            resultImg.setImageResource(R.drawable.correct_answer);
            resultImg.setVisibility(View.VISIBLE);
            return true;
        }
        if (answer == wrong1 || answer == wrong2) {
            resultImg.setImageResource(R.drawable.wrong_answer);
            resultImg.setVisibility(View.VISIBLE);
            return false;
        }
        if (answer == wrong3) {
            resultImg.setImageResource(R.drawable.too_late);
            resultImg.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            return false;
        }
    }

    @Override
    public void onBackPressed(){
    }

    @Override
    public void onAnimationStart(Animation animation) {
        timer.cancel();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent i;
        i = new Intent(this, GameOver.class);
        if (gameOn) {
            setQuestion();
        }
        if (lives == 0) {
            gameOn = false;
            TOP_LEVEL = level;
            HIGH_SCORE = score;
            finish();
            startActivity(i);
        }
        clickEnable = true;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}