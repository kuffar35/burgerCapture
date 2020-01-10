package com.example.burgercapture;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView score_txt;
    TextView time_txt;
    TextView bestScore_txt;
    int score;
    int Pscore;
    int ScoreBest ;

    ImageView convictImage1;
    ImageView convictImage2;
    ImageView convictImage3;
    ImageView convictImage4;
    ImageView convictImage5;
    ImageView convictImage6;
    ImageView convictImage7;
    ImageView convictImage8;
    ImageView convictImage9;
    ImageView[] convictArray;

    ImageView policeImage1;
    ImageView policeImage2;
    ImageView policeImage3;
    ImageView policeImage4;
    ImageView policeImage5;
    ImageView policeImage6;
    ImageView policeImage7;
    ImageView policeImage8;
    ImageView policeImage9;
    TextView Pscore_txt;

    ImageView[] policeArray;

    ImageView retryImage;

    Handler handler;
    Runnable runnable;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        score_txt = (TextView)findViewById(R.id.score_txt);
        score_txt.setText("convict : 0");
        ScoreBest=0;
        bestScore_txt = (TextView)findViewById(R.id.bestScore_txt);


        Pscore_txt=(TextView)findViewById(R.id.Pscore_txt);

        time_txt = (TextView)findViewById(R.id.time_txt);

        convictImage1 = findViewById(R.id.convictImage1);
        convictImage2 = findViewById(R.id.convictImage2);
        convictImage3 = findViewById(R.id.convictImage3);
        convictImage4 = findViewById(R.id.convictImage4);
        convictImage5 = findViewById(R.id.convictImage5);
        convictImage6 = findViewById(R.id.convictImage6);
        convictImage7 = findViewById(R.id.convictImage7);
        convictImage8 = findViewById(R.id.convictImage8);
        convictImage9 = findViewById(R.id.convictImage9);

        convictArray = new ImageView[] {convictImage1,convictImage2,convictImage3,convictImage4,convictImage5,convictImage6,convictImage7,convictImage8,convictImage9};

        policeImage1 = findViewById(R.id.policeImage1);
        policeImage2 = findViewById(R.id.policeImage2);
        policeImage3 = findViewById(R.id.policeImage3);
        policeImage4 = findViewById(R.id.policeImage4);
        policeImage5 = findViewById(R.id.policeImage5);
        policeImage6 = findViewById(R.id.policeImage6);
        policeImage7 = findViewById(R.id.policeImage7);
        policeImage8 = findViewById(R.id.policeImage8);
        policeImage9 = findViewById(R.id.policeImage9);

        policeArray = new ImageView[]{policeImage1,policeImage2,policeImage3,policeImage4,policeImage5,policeImage6,policeImage7,policeImage8,policeImage9};

        retryImage = findViewById(R.id.retryImage);

        hideImages();

        score = 0;
        Pscore =0;

        sharedPreferences = this.getSharedPreferences("com.example.burgercapture", Context.MODE_PRIVATE);

        int bestScore2 = sharedPreferences.getInt("CBestScore",0);
        bestScore_txt.setText(""+bestScore2);



        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                time_txt.setText("Time : " + millisUntilFinished/1000);
                retryImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish() {
                 time_txt.setText("Time off");
                 retryImage.setVisibility(View.VISIBLE);
                 retryImage.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = getIntent();
                         finish();
                         startActivity(intent);
                     }
                 });

                 handler.removeCallbacks(runnable);
                for(ImageView image : convictArray){
                    image.setVisibility(View.INVISIBLE);
                }
                for(ImageView Pimage : policeArray){
                    Pimage.setVisibility(View.INVISIBLE);
                }
/*
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart ? ");
                alert.setMessage("Are you sure restart");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Game Over",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();*/
            }
        }.start();

    }

    public void PIncreaseScore(View view){
        Pscore-=2;
        Pscore_txt.setText("Police convict : "+Pscore);



    }

    public void IncreaseScore(View view){

        score++;

        score_txt.setText("convict : "+score);

        if(score > ScoreBest) {
            ScoreBest = score;

            sharedPreferences.edit().putInt("CBestScore",ScoreBest).apply();
        }

    }



    public void hideImages(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image : convictArray){
                    image.setVisibility(View.INVISIBLE);
                }

                for(ImageView image2 : policeArray){
                    image2.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(9);
                int a = random.nextInt(9);
                if(convictArray[i]!=policeArray[a]){
                 policeArray[a].setVisibility(View.VISIBLE);
                 convictArray[i].setVisibility(View.VISIBLE);
                }
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);


    }
}
