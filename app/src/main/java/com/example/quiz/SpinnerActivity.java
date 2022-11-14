package com.example.quiz;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.google.common.collect.ComparisonChain.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.quiz.SpinWheel.LuckyWheelView;
import com.example.quiz.SpinWheel.model.LuckyItem;
import com.example.quiz.databinding.ActivitySpinnerBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinnerActivity extends AppCompatActivity {

    ActivitySpinnerBinding binding;
    long xtime, hours, minutes, seconds;
    SharedPreferences prefs;
    Context context;
    String Cash;
    VideoView video;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<LuckyItem> data = new ArrayList<>();

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.topText = "5";
        luckyItem1.secondaryText = "COINS";
        luckyItem1.textColor = Color.parseColor("#212121");
        luckyItem1.color = Color.parseColor("#eceff1");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.topText = "10";
        luckyItem2.secondaryText = "COINS";
        luckyItem2.color = Color.parseColor("#00cf00");
        luckyItem2.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.topText = "15";
        luckyItem3.secondaryText = "COINS";
        luckyItem3.textColor = Color.parseColor("#212121");
        luckyItem3.color = Color.parseColor("#eceff1");
        data.add(luckyItem3);

        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.topText = "20";
        luckyItem4.secondaryText = "COINS";
        luckyItem4.color = Color.parseColor("#7f00d9");
        luckyItem4.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.topText = "25";
        luckyItem5.secondaryText = "COINS";
        luckyItem5.textColor = Color.parseColor("#212121");
        luckyItem5.color = Color.parseColor("#eceff1");
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.topText = "30";
        luckyItem6.secondaryText = "COINS";
        luckyItem6.color = Color.parseColor("#dc0000");
        luckyItem6.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem6);

        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.topText = "35";
        luckyItem7.secondaryText = "COINS";
        luckyItem7.textColor = Color.parseColor("#212121");
        luckyItem7.color = Color.parseColor("#eceff1");
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.topText = "0";
        luckyItem8.secondaryText = "COINS";
        luckyItem8.color = Color.parseColor("#008bff");
        luckyItem8.textColor = Color.parseColor("#ffffff");
        data.add(luckyItem8);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        xtime = System.currentTimeMillis() - prefs.getLong("TIME", System.currentTimeMillis());

        long timer=86400000-(xtime+prefs.getLong("TIME2",0));
        //long timer = 60000 - (xtime + prefs.getLong("TIME2", 0));

        final SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("TIME2", xtime + prefs.getLong("TIME2", 0));
        editor.commit();


        new CountDownTimer(timer, 1000) {

            public void onTick(long elapsed) {

                Log.d(TAG, "TIMER" + System.currentTimeMillis());
                long timer2 = elapsed;
                hours = timer2 / 3600000;
                timer2 %= 3600000;
                minutes = timer2 / 60000;
                timer2 %= 60000;
                seconds = timer2 / 1000;
                binding.time.setText(hours + ":" + minutes + ":" + seconds);


            }

            public void onFinish() { //Intent intent = new Intent(Hug.this, Hug_Accepted.class);
                // startActivity(intent);

            }

        }    .start();
        xtime = System.currentTimeMillis();
        SharedPreferences.Editor editor2 = prefs.edit();
        editor2.putLong("TIME", xtime);
        editor2.commit();




        binding.wheelview.setData(data);
        binding.wheelview.setRound(5);

        binding.time.setText(hours + ":" + minutes + ":" + seconds);

        binding.spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int randomNumber = r.nextInt(8);
                binding.wheelview.startLuckyWheelWithTargetIndex(randomNumber);

            }
        });

        binding.wheelview.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                updateCash(index);
            }



        });
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.coins);


        //String path = " android.resource://com.example.videobgrestart/" + R.raw.walletbg;
        // Uri u = Uri.parse(path);

        binding.videoView.setVideoURI(uri);
        binding.videoView.start();

        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });




    }

    void updateCash(int index) {
        // this.getSharedPreferences("prefs", 0).edit().clear().apply();
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().apply();
        if (binding.time.getText().toString().equals("0:0:0")) {
            long cash = 0;
            switch (index) {
                case 0:
                    cash = 5;
                    Cash = "5";
                    break;
                case 1:
                    cash = 10;
                    Cash = "10";
                    break;
                case 2:
                    cash = 15;
                    Cash = "15";
                    break;
                case 3:
                    cash = 20;
                    Cash = "20";
                    break;
                case 4:
                    cash = 25;
                    Cash = "25";
                    break;
                case 5:
                    cash = 30;
                    Cash = "30";
                    break;
                case 6:
                    cash = 35;
                    Cash = "35";
                    break;
                case 7:
                    cash = 0;
                    Cash = "0";
                    break;
            }

            FirebaseFirestore database = FirebaseFirestore.getInstance();

            database
                    .collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .update("coins", FieldValue.increment(cash)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SpinnerActivity.this, Cash + " Coins added to your wallet", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        } else if (!binding.time.getText().toString().equals("0:0:0")) {
            Toast.makeText(SpinnerActivity.this, binding.time.getText().toString() + " remaining in your daily spin", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        binding.videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        binding.videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }






}



