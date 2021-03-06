package com.nnspace.thaismoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.Home2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class SynActivity extends AppCompatActivity {

    private  int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syn);
            try{
                setGetProfileToServer();
                setGetMoodToServer();
                setGetSleepToServer();
                setGetDiaryToServer();
                setGetEvaluationToServer();
                startActivity(new Intent(SynActivity.this, Home2.class));
                finish();
            }catch (Exception e){

            }
    }

    private synchronized void setGetProfileToServer(){

        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
        String url = getResources().getString(R.string.url_get_profile) + "?username=" + db.getUsername() + "&type=" + db.getType();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Sysn Response --> : " + response);
                if(!response.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
                        System.out.println(map);
                        if(db.getType().equals("p")){
                            db.insertPatientProfileLogin(map);
                        }else if(db.getType().equals("g")) {
                            db.insertGeneralProfileLogin(map);
                        }else{
                            throw new Exception("Profile type Error");
                        }
                        notifyAll();

                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
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

    private synchronized void setGetMoodToServer(){
        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
        String url = getResources().getString(R.string.url_get_mood) + "?username=" + db.getUsername();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        System.out.println("Get Mood Response -->: " + response);
                        db.insertSyncMood(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
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

    private synchronized void setGetSleepToServer(){
        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
        String url = getResources().getString(R.string.url_get_sleep) + "?username=" + db.getUsername();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        System.out.println("Get Sleep Response -->: " + response);
                        db.insertSyncSleep(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
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

    private synchronized void setGetDiaryToServer(){
        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
        String url = getResources().getString(R.string.url_get_diary) + "?username=" + db.getUsername();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")){
                    Map<String, String> map = new HashMap<String, String>();
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        System.out.println("Get Diary Response -->: " + response);
                        db.insertSyncDiary(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
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

    private synchronized void setGetEvaluationToServer(){
        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
        String url = getResources().getString(R.string.url_get_evaluation) + "?username=" + db.getUsername();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals("0")){
                    try {

                        ObjectMapper mapper = new ObjectMapper();
                        String json = response;
                        System.out.println("Get Evaluation Response -->: " + response);
                        db.insertSyncEvaluation(response);
                        notifyAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
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
}
