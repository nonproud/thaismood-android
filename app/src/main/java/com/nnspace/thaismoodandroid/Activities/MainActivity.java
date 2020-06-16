package com.nnspace.thaismoodandroid.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    final ThaisMoodDB db = new ThaisMoodDB(this);
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
            try{
                if(db.isLogon()){
                    if(!db.getIsVerified()){
                        startActivity(new Intent(MainActivity.this, VerifyEmailActivity.class));
                    }else {
                        if(db.getType().equals("0")){
                            startActivity(new Intent(MainActivity.this, Register.class));
                        }else{
                            String type = db.getType();
                            if(db.isProfileEmtpy(type)){
                                sendGetProfileToServer(db.getUsername(), db.getType());
                                startActivity(new Intent(MainActivity.this, Home2.class));

                            }else{
                                startActivity(new Intent(MainActivity.this, Home2.class));
                            }
                        }
                    }
                }else{
                    startActivity(new Intent(MainActivity.this, SignInOn.class));
                }
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }


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

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    private void sendGetProfileToServer(final String username, final String type){
        String url_getProfile = getResources().getString(R.string.member_profile_url);
        String url_request = url_getProfile + "?username=" + username + "&type=" + type;
        RequestQueue MyRequestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url_getProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
                        System.out.println(map);
                        if(type.equals("p")){
                            db.insertGeneralProfileLogin(map);
                        }else{
                            db.insertPatientProfileLogin(map);
                        }

                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    db.updateToken(map.get("token"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("authorization", db.getToken());
                return params;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void sendGetMoodToServer(final String username){
        String url_getMood = getResources().getString(R.string.url_create_mood);
    }

    private void sendGetSleepToServer(final String username){
        String url_getSleep = getResources().getString(R.string.url_sleep);

    }

    private void sendGetDiaryToServer(final String username){
        String url_getDiary = getResources().getString(R.string.url_diary);

    }

    private void sendGetEvaluationToServer(final String username){
        String url_getEvaluation = getResources().getString(R.string.evaluation_url);

    }
}
