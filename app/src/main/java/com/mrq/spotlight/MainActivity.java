package com.mrq.spotlight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView, textView2;
    private Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                spotlight();
            }
        }, 1000);
    }

    private void spotlight() {
        SimpleTarget oneTarget = new SimpleTarget.Builder(MainActivity.this)
                // set view
                .setPoint(textView)
                // set zoom
                .setRadius(150f)
                .setTitle("title")
                .setDescription("description")
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        SimpleTarget twoTarget = new SimpleTarget.Builder(MainActivity.this)
                // set view
                .setPoint(textView2)
                // set zoom
                .setRadius(150f)
                .setTitle("title")
                .setDescription("description")
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();


        SimpleTarget thirdTarget = new SimpleTarget.Builder(MainActivity.this)
                // set view
                .setPoint(button)
                // set zoom
                .setRadius(150f)
                .setTitle("title")
                .setDescription("description")
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onEnded(SimpleTarget target) {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        CustomTarget customTarget = new CustomTarget.Builder(MainActivity.this)
                .setPoint(button2)
                .setRadius(80f)
                .setView(R.layout.layout_target)
                .build();


        Spotlight.with(MainActivity.this)
                .setDuration(1000L)
                .setAnimation(new DecelerateInterpolator(2f))
                .setTargets(oneTarget, twoTarget, thirdTarget, customTarget)
                // or
//                .setTargets(oneTarget)
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                    @Override
                    public void onEnded() {
                        Toast.makeText(MainActivity.this, "onEnded", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                    @Override
                    public void onStarted() {
                        Toast.makeText(MainActivity.this, "onStarted", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }
}