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
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.Home2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SynActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syn);
        setGetProfileToServer();
    }

    private void setGetProfileToServer(){
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
                        startActivity(new Intent(SynActivity.this, Home2.class));

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

//    private void setGetMoodToServer(){
//        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
//        String url = getResources().getString(R.string.url_get_mood) + "?username=" + db.getUsername();
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(!response.equals("0")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    try {
//
//                        ObjectMapper mapper = new ObjectMapper();
//                        String json = response;
//                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
//                        db.insertMood(map);
//
//                    } catch (JsonGenerationException e) {
//                        e.printStackTrace();
//                    } catch (JsonMappingException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                params.put("authorization", db.getToken());
//                return params;
//
//            }
//        };
//        MyRequestQueue.add(myStringRequest);
//        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
//
//    private void setGetSleepToServer(){
//        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
//        String url = getResources().getString(R.string.url_get_sleep) + "?username=" + db.getUsername();
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(!response.equals("0")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    try {
//
//                        ObjectMapper mapper = new ObjectMapper();
//                        String json = response;
//                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
//                        db.insertSleep(map);
//
//                    } catch (JsonGenerationException e) {
//                        e.printStackTrace();
//                    } catch (JsonMappingException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                params.put("authorization", db.getToken());
//                return params;
//
//            }
//        };
//        MyRequestQueue.add(myStringRequest);
//        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
//
//    private void setGetDiaryToServer(){
//        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
//        String url = getResources().getString(R.string.url_get_diary) + "?username=" + db.getUsername();
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(!response.equals("0")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    try {
//
//                        ObjectMapper mapper = new ObjectMapper();
//                        String json = response;
//                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
//                        db.insertNote(map);
//
//                    } catch (JsonGenerationException e) {
//                        e.printStackTrace();
//                    } catch (JsonMappingException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                params.put("authorization", db.getToken());
//                return params;
//
//            }
//        };
//        MyRequestQueue.add(myStringRequest);
//        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
//
//    private void setGetEvaluationToServer(){
//        final ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());
//        String url = getResources().getString(R.string.url_get_evaluation) + "?username=" + db.getUsername();
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest myStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(!response.equals("0")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    try {
//
//                        ObjectMapper mapper = new ObjectMapper();
//                        String json = response;
//                        map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
//                        db.insertEvaluationScore(map);
//
//                    } catch (JsonGenerationException e) {
//                        e.printStackTrace();
//                    } catch (JsonMappingException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(), "ไม่สามารถเชื่อมต่อได้ กรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("Content-Type","application/x-www-form-urlencoded");
//                params.put("authorization", db.getToken());
//                return params;
//
//            }
//        };
//        MyRequestQueue.add(myStringRequest);
//        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//    }
}
