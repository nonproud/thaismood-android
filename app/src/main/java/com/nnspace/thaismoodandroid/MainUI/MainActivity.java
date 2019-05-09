package com.nnspace.thaismoodandroid.MainUI;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.nnspace.thaismoodandroid.Database.LogonDatabase;
import com.nnspace.thaismoodandroid.MainmenuTest;
import com.nnspace.thaismoodandroid.R;

public class MainActivity extends Activity {

    final LogonDatabase db = new LogonDatabase(this);
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adjustFontScale(getResources().getConfiguration());

        setContentView(R.layout.activity_main);


        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
//                Map<String, String> data = db.isLogon();
//                if(data != null){
//                    if(data.get("status").equals("0")){
//
//                    }
//                }else{
//                    Intent intent = new Intent(MainActivity.this, SignInOn.class);
//                    startActivity(intent);
//                    finish();
//                }
                Intent intent = new Intent(MainActivity.this, MainmenuTest.class);
                    startActivity(intent);
                    finish();
            }
        };

    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    public void adjustFontScale(Configuration configuration)
    {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
}