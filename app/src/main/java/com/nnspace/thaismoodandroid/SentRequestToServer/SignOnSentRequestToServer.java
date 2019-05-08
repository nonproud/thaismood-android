package com.nnspace.thaismoodandroid.SentRequestToServer;

import android.content.Context;
import android.widget.Toast;

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
import com.nnspace.thaismoodandroid.Database.LogonDatabase;
import com.nnspace.thaismoodandroid.JWTUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public  class SignOnSentRequestToServer {

    private static boolean isRequestSuccess = false;
    private static Map<String, Object> loginResult = new HashMap<String, Object>();
    private static final String url_register = "https://thaismood.nn-space.me/member";
    private static final String url_login = "https://thaismood.nn-space.me/member/login";
    private static final String url_confirm_email = "https://thaismood.nn-space.me/member/email";
    private static final String getUrl_confirm_username = "https://thaismood.nn-space.me/member/email";

    public static  Map<String, Object> sendLoginRequest(final Context context, final String email, final String password) {
        final LogonDatabase db = new LogonDatabase(context);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
//                Map<String, Object> map = new HashMap<String, Object>();
                try {

                    ObjectMapper mapper = new ObjectMapper();
                    // convert JSON string to Map
                    loginResult = mapper.readValue(JWTUtils.decoded(response.toString()), new TypeReference<Map<String, String>>(){});
                    System.out.println("*******************"+JWTUtils.decoded(response.toString()));
                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loginResult.put("result", "true");
                db.insertLogonLogin(loginResult.get("id").toString(), loginResult.get("email").toString(), loginResult.get("is_verified").toString(), response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return loginResult;
    }

    public static boolean sendRegistRequest(final Context context, final String email, final String password) {
        isRequestSuccess = false;
        final LogonDatabase db = new LogonDatabase(context);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isRequestSuccess = true;
                db.insertLogonResgist(response.toString(), email);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                isRequestSuccess = false;
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return isRequestSuccess;
    }

    public static boolean isEmailDuplicate(final Context context, final String email){
        isRequestSuccess = false;
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, url_confirm_email, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("1")){
                    isRequestSuccess = true;
                }else{
                    isRequestSuccess = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return isRequestSuccess;
    }

    public static boolean isUsernameDuplicate(final Context context, final String username) {
        isRequestSuccess = false;
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, getUrl_confirm_username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("1")){
                    isRequestSuccess = true;
                }else{
                    isRequestSuccess = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", username);
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return isRequestSuccess;
    }
}
