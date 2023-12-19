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

public class SubMain extends AppCompatActivity {
    ImageView iv_button1,iv_arrow1;
    TextView tv_points1, tv_highestScore1;
    ProgressBar progressBar1;

    Handler handler1;
    Runnable runnable1;

    Random r1;
    private final static  int STATE_ORANGE1 = 1;
    private final static  int STATE_GREEN1 = 2;



   int buttonState1 = STATE_ORANGE1;
   int arrowState1 = STATE_ORANGE1;
    int currentTime1 = 3000;
    int startTime1 = 3000;
    int highestScore1 = 0;
    int currentPoints1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        highestScore1 = getHighestScoreFromPreferences();
        tv_highestScore1 = findViewById(R.id.tv_highestScore1);
        iv_button1 = findViewById(R.id.iv_button12);
        iv_arrow1 = findViewById(R.id.iv_arrow12);
        tv_points1 = findViewById(R.id.text_points1);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar1.setMax(startTime1);
        progressBar1.setProgress(startTime1);
        tv_points1.setText("YOUR SCORE: " + currentPoints1);

        highestScore1 = 0;
        r1 = new Random();
        arrowState1 = r1.nextInt(2) + 1;
        setArrowImage1(arrowState1);

        iv_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonImage1(setButtonPosition1(buttonState1));
            }
        });
        updateHighestScoreTextView1();
        handler1 = new Handler();
        runnable1 = new Runnable() {
            @Override
            public void run() {
                currentTime1 = currentTime1 - 100;
                progressBar1.setProgress(currentTime1);

                if(currentTime1 > 0){
                    handler1.postDelayed(runnable1,100);
                }
                else{
                    if(buttonState1 == arrowState1){
                        currentPoints1 = currentPoints1 + 1;
                        tv_points1.setText("YOUR SCORE: " + currentPoints1);
                        startTime1 = startTime1 - 100;
                        if(startTime1 < 800){
                            startTime1 = 1500;
                        }
                        progressBar1.setMax(startTime1);
                        currentTime1 = startTime1;
                        progressBar1.setProgress(currentTime1);

                        arrowState1 = r1.nextInt(2) + 1;
                        setArrowImage1(arrowState1);
                        handler1.postDelayed(runnable1,100);
                    }
                    else{
                        iv_button1.setEnabled(false);
                        showGameOverDialog1();
                    }
                }
            }
        };
        handler1.postDelayed(runnable1,100);
    }


    private void showGameOverDialog1() {
        if (currentPoints1 > highestScore1) {
            if (highestScore1 == 0) {

            } else {
                Toast.makeText(SubMain.this, "Wow! You Beat the High Score!", Toast.LENGTH_SHORT).show();
            }
        }
        // Create an AlertDialog.Builder to display the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the dialog title and message
        builder.setTitle("GAME OVER!")
                .setMessage("Your score is : " + currentPoints1)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (currentPoints1 > highestScore1) {
                            highestScore1 = currentPoints1;
                            saveHighestScoreToPreferences1(highestScore1);
                            updateHighestScoreTextView1();
                        }
                        resetGame1();
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

    private void resetGame1() {
        // Reset game-related variables
        currentPoints1 = 0;
        startTime1 = 4000;
        currentTime1 = startTime1;
        buttonState1 = STATE_ORANGE1;
        arrowState1 = r1.nextInt(4) + 1;

        // Update UI components
        tv_points1.setText("YOUR SCORE: " + currentPoints1);
        progressBar1.setMax(startTime1);
        progressBar1.setProgress(currentTime1);
        iv_button1.setEnabled(true);
        setArrowImage1(arrowState1);
        handler1.postDelayed(runnable1, 100);
    }
    //display arrow image based on generated arrow number
    private void setArrowImage1(int state) {
        switch (state){
            case STATE_ORANGE1:
                iv_arrow1.setImageResource(R.drawable.orange_downpoint1);
                arrowState1 = STATE_ORANGE1;
                break;
            case STATE_GREEN1:
                iv_arrow1.setImageResource(R.drawable.green_downpoint1);
                arrowState1 = STATE_GREEN1;
                break;

        }



    }
    //rotate animation when image buton clicked
    private void setRotation1(final ImageView image, final int drawable){
        RotateAnimation rotateAnimation = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
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
    private int setButtonPosition1(int position){
        position = position + 1;
        if(position == 3){
            position = 1;
        }
        return position;
    }
    //display image buttons colors
    private void setButtonImage1(int state){
        switch (state){
            case STATE_ORANGE1:
                setRotation1(iv_button1, R.drawable.orange_pointtwo3);
                buttonState1 = STATE_ORANGE1;
                break;
            case STATE_GREEN1:
                setRotation1(iv_button1, R.drawable.green_pointtwo3);
                buttonState1 = STATE_GREEN1;
                break;


        }
    }

    private void updateHighestScoreTextView1() {
        tv_highestScore1.setText("HIGH SCORE: " + highestScore1);
    }
    private void saveHighestScoreToPreferences1(int score) {
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
        saveHighestScoreToPreferences1(highestScore1);
    }
}