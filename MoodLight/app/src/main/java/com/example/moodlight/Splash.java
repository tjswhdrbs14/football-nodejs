package com.example.moodlight;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed((Runnable) new splashHandler(), 3000);

    }

    private class splashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class));
            Splash.this.finish();
        }
    }
}
