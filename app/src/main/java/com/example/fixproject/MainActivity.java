package com.example.fixproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MaterialButton left_BTN;
    private MaterialButton right_BTN;
    private ShapeableImageView carPlacementCenter;
    private ShapeableImageView carPlacementLeft;
    private ShapeableImageView carPlacementRight;
    private ShapeableImageView asteroid1;
    private ShapeableImageView asteroid2;
    private ShapeableImageView asteroid3;
    final int DELAY = 1000;
    private Timer timer = new Timer();
    private ShapeableImageView[][] myImages;
    private ShapeableImageView[] game_IMG_hearts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        startTimer();
        left_BTN.setOnClickListener(view -> {
            moveCar(-1);
        });
        right_BTN.setOnClickListener(view -> {
            moveCar(1);
        });

    }

    private void moveCar(int direction) {

        if (direction == 1) {
            if (carPlacementCenter.getVisibility() == View.VISIBLE
                    && carPlacementLeft.getVisibility() == View.INVISIBLE &&
                    carPlacementRight.getVisibility() == View.INVISIBLE) {

                carPlacementCenter.setVisibility(View.INVISIBLE);
                carPlacementRight.setVisibility(View.VISIBLE);
            }
            if (carPlacementCenter.getVisibility() == View.INVISIBLE && carPlacementRight.getVisibility() == View.INVISIBLE && carPlacementLeft.getVisibility() == View.VISIBLE) {
                carPlacementCenter.setVisibility(View.VISIBLE);
                carPlacementLeft.setVisibility(View.INVISIBLE);
            }
        }
        if (direction == -1) {
            if (carPlacementCenter.getVisibility() == View.VISIBLE
                    && carPlacementLeft.getVisibility() == View.INVISIBLE &&
                    carPlacementRight.getVisibility() == View.INVISIBLE) {
                carPlacementLeft.setVisibility(View.VISIBLE);
                carPlacementCenter.setVisibility(View.INVISIBLE);
            }
            if (carPlacementCenter.getVisibility() == View.INVISIBLE
                    && carPlacementLeft.getVisibility() == View.INVISIBLE &&
                    carPlacementRight.getVisibility() == View.VISIBLE) {
                carPlacementCenter.setVisibility(View.VISIBLE);
                carPlacementRight.setVisibility(View.INVISIBLE);
            }
        }

    }


    long startTime = 0;


    private void startTimer() {
        startTime = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                randomlyAddMeteor();
                                moveMeteor();
                                checkCollision();

                            }
                        });
                    }
                }
                , 1000, 1000);
    }

    int strikeCount = 0;
    //Vibrator vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    private void checkCollision() {
        if (this.myImages[3][0].getVisibility() == View.VISIBLE && carPlacementLeft.getVisibility() == View.VISIBLE) {
            toast("COLLISION!");
            //vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            strikeCount++;
            removeMeteor(myImages[3][0]);

        }
        if (this.myImages[3][1].getVisibility() == View.VISIBLE && carPlacementCenter.getVisibility() == View.VISIBLE) {
            toast("COLLISION!");
          // vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        //vibrator.vibrate(500);
            strikeCount++;
            removeMeteor(myImages[3][1]);

        }
        if (this.myImages[3][2].getVisibility() == View.VISIBLE && carPlacementRight.getVisibility() == View.VISIBLE) {
            toast("COLLISION!");
           // vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

            strikeCount++;

            removeMeteor(myImages[3][2]);


        }
    if(strikeCount>0&&strikeCount<4) {
        game_IMG_hearts[game_IMG_hearts.length - strikeCount].setVisibility(View.INVISIBLE);
    }
    }
void removeMeteor(ShapeableImageView img){
    final Handler handler = new Handler(Looper.getMainLooper());
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            //myImages[3][2].setVisibility(View.INVISIBLE);
            img.setVisibility(View.INVISIBLE);
        }
    }, 300);

}
    private void toast(String str) {

        Toast
                .makeText(this, str, Toast.LENGTH_LONG)
                .show();
    }

    private void randomlyAddMeteor() {
        Random rand = new Random();
        int n = rand.nextInt(3);
        this.myImages[0][n].setVisibility(View.VISIBLE);


    }

    private void moveMeteor() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (this.myImages[i][j].getVisibility() == View.VISIBLE) {

                    this.myImages[i + 1][j].setVisibility(View.VISIBLE);

                    this.myImages[i][j].setVisibility(View.INVISIBLE);

                    i++;
                    if(i+1==3){
                      removeMeteor(myImages[3][j]);
                  }

                }
            }
        }
    }


    private void findViews() {
        initViewArray(myImages);
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),

        };
        left_BTN = findViewById(R.id.left_BTN);
        right_BTN = findViewById(R.id.right_BTN);
        carPlacementRight = findViewById(R.id.car3);
        carPlacementRight.setVisibility(View.INVISIBLE);
        carPlacementCenter = findViewById(R.id.car2);
        carPlacementLeft = findViewById(R.id.car1);
        carPlacementLeft.setVisibility(View.INVISIBLE);
        asteroid1 = findViewById(R.id.m1);
        asteroid2 = findViewById(R.id.m2);
        asteroid3 = findViewById(R.id.m3);
    }

    private void initViewArray(ShapeableImageView[][] myImages) {

        this.myImages = new ShapeableImageView[4][3];
        this.myImages[0][0] = findViewById(R.id.m1);

        this.myImages[0][1] = findViewById(R.id.m2);
        this.myImages[0][2] = findViewById(R.id.m3);
        this.myImages[1][0] = findViewById(R.id.m4);
        this.myImages[1][1] = findViewById(R.id.m5);
        this.myImages[1][2] = findViewById(R.id.m6);

        this.myImages[2][0] = findViewById(R.id.m7);
        this.myImages[2][1] = findViewById(R.id.m8);
        this.myImages[2][2] = findViewById(R.id.m9);
        this.myImages[3][0] = findViewById(R.id.m10);
        this.myImages[3][1] = findViewById(R.id.m11);
        this.myImages[3][2] = findViewById(R.id.m12);
        setMeteorsInvisible();

//        this.myImages[4][0] = findViewById(R.id.car1);
//        this.myImages[4][1] = findViewById(R.id.car2);
//        this.myImages[4][2] = findViewById(R.id.car3);

    }

    private void setMeteorsInvisible() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                this.myImages[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }
}

