package com.roomy.dbtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Screen extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);


        ImageView logoAnim = findViewById(R.id.logo_id);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_icon_anim);
        logoAnim.startAnimation(animation);


        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}