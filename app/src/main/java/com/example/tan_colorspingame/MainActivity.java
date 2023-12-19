package com.example.tan_colorspingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView iv_button,iv_arrow;
    TextView tv_points, tv_highestScore;
    ProgressBar progressBar;

    Handler handler;
    Runnable runnable;

    Random r;
    private final static  int STATE_BLUE = 1;
    private final static  int STATE_GREEN = 2;
    private final static  int STATE_BLACK = 3;
    private final static  int STATE_ORANGE = 4;





    int buttonState = STATE_BLUE;
    int arrowState = STATE_BLUE;
    int currentTime = 3000;
    int startTime = 3000;
    int highestScore = 0;
    int currentPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highestScore = getHighestScoreFromPreferences();
        tv_highestScore = findViewById(R.id.tv_highestScore);
        iv_button = findViewById(R.id.iv_button);
        iv_arrow = findViewById(R.id.iv_arrow);
        tv_points = findViewById(R.id.text_points);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);
        tv_points.setText("YOUR SCORE: " + currentPoints);

        highestScore = 0;
        r = new Random();
        arrowState = r.nextInt(4) + 1;
        setArrowImage(arrowState);

        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonImage(setButtonPosition(buttonState));
            }
        });
        updateHighestScoreTextView();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentTime = currentTime - 100;
                progressBar.setProgress(currentTime);

                if(currentTime > 0){
                    handler.postDelayed(runnable,100);
                }
                else{
                    if(buttonState == arrowState){
                        currentPoints = currentPoints + 1;
                        tv_points.setText("YOUR SCORE: " + currentPoints);
                        startTime = startTime - 100;
                        if(startTime < 800){
                            startTime = 1500;
                        }
                        progressBar.setMax(startTime);
                        currentTime = startTime;
                        progressBar.setProgress(currentTime);

                        arrowState = r.nextInt(4) + 1;
                        setArrowImage(arrowState);
                        handler.postDelayed(runnable,100);
                    }
                    else{
                        iv_button.setEnabled(false);
                        showGameOverDialog();
                    }
                }
            }
        };
        handler.postDelayed(runnable,100);
    }


    private void showGameOverDialog() {
        if (currentPoints > highestScore) {
            if (highestScore == 0) {

            } else {
                Toast.makeText(MainActivity.this, "Wow! You Beat the High Score!", Toast.LENGTH_SHORT).show();
            }
        }
        // Create an AlertDialog.Builder to display the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the dialog title and message
        builder.setTitle("GAME OVER!")
                .setMessage("Your score is : " + currentPoints)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (currentPoints > highestScore) {
                            highestScore = currentPoints;
                            saveHighestScoreToPreferences(highestScore);
                            updateHighestScoreTextView();
                        }
                        resetGame();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Exit the game
                        finish();
                    }
                });
        // Create and show the AlertDialog
        builder.create().show();
    }

    private void resetGame() {
        // Reset game-related variables
        currentPoints = 0;
        startTime = 4000;
        currentTime = startTime;
        buttonState = STATE_BLUE;
        arrowState = r.nextInt(4) + 1;

        // Update UI components
        tv_points.setText("YOUR SCORE: " + currentPoints);
        progressBar.setMax(startTime);
        progressBar.setProgress(currentTime);
        iv_button.setEnabled(true);
        setArrowImage(arrowState);
        handler.postDelayed(runnable, 100);
    }
    //display arrow image based on generated arrow number
    private void setArrowImage(int state) {
        switch (state){
            case STATE_BLUE:
                iv_arrow.setImageResource(R.drawable.blue_downpoint1);
                arrowState = STATE_BLUE;
                break;
            case STATE_GREEN:
                iv_arrow.setImageResource(R.drawable.green_downpoint1);
                arrowState = STATE_GREEN;
                break;
            case STATE_BLACK:
                iv_arrow.setImageResource(R.drawable.black_downpoint1);
                arrowState = STATE_BLACK;
                break;
            case STATE_ORANGE:
                iv_arrow.setImageResource(R.drawable.orange_downpoint1);
                arrowState = STATE_ORANGE;
                break;
        }
    }
    //rotate animation when image buton clicked
    private void setRotation(final ImageView image, final int drawable){
        RotateAnimation rotateAnimation = new RotateAnimation(0,90, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(drawable);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(rotateAnimation);
    }
    //set button colors position 1-4
    private int setButtonPosition(int position){
        position = position + 1;
        if(position == 5){
            position = 1;
        }
        return position;
    }
    //display image buttons colors
    private void setButtonImage(int state){
        switch (state){
            case STATE_BLUE:
                setRotation(iv_button, R.drawable.blue_pointfour3);
                buttonState = STATE_BLUE;
                break;
            case STATE_GREEN:
                setRotation(iv_button, R.drawable.green_pointfour3);
                buttonState = STATE_GREEN;
                break;
            case STATE_BLACK:
                setRotation(iv_button, R.drawable.black_pointfour3);
                buttonState = STATE_BLACK;
                break;
            case STATE_ORANGE:
                setRotation(iv_button, R.drawable.orange_pointfour3);
                buttonState = STATE_ORANGE;
                break;
        }
    }

    private void updateHighestScoreTextView() {
        tv_highestScore.setText("HIGH SCORE: " + highestScore);
    }
    private void saveHighestScoreToPreferences(int score) {
        // Use SharedPreferences to save the highest score
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("highest_score", score);
        editor.apply();
    }

    private int getHighestScoreFromPreferences() {
        // Retrieve the highest score from SharedPreferences
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        return preferences.getInt("highest_score", 0);
    }
    protected void onDestroy() {
        super.onDestroy();

        // Save the highest score to shared preferences when the app is closed
        saveHighestScoreToPreferences(highestScore);
    }
}