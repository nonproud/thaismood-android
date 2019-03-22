package com.example.thaismoodandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.thaismoodandroid.Database.LogonDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Q9Q extends AppCompatActivity implements Evaluation {

    private int score = 0;
    private RadioGroup q9q1_answer, q9q2_answer, q9q3_answer, q9q4_answer, q9q5_answer,
            q9q6_answer, q9q7_answer, q9q8_answer, q9q9_answer;
    private ArrayList<RadioGroup> answerList;
    private final LogonDatabase db = new LogonDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_9_q);
        answerList = new ArrayList<>();
        q9q1_answer = (RadioGroup) findViewById(R.id.q_9q_1_answer);
        q9q2_answer = (RadioGroup) findViewById(R.id.q_9q_2_answer);
        q9q3_answer = (RadioGroup) findViewById(R.id.q_9q_3_answer);
        q9q4_answer = (RadioGroup) findViewById(R.id.q_9q_4_answer);
        q9q5_answer = (RadioGroup) findViewById(R.id.q_9q_5_answer);
        q9q6_answer = (RadioGroup) findViewById(R.id.q_9q_6_answer);
        q9q7_answer = (RadioGroup) findViewById(R.id.q_9q_7_answer);
        q9q8_answer = (RadioGroup) findViewById(R.id.q_9q_8_answer);
        q9q9_answer = (RadioGroup) findViewById(R.id.q_9q_9_answer);
        answerList.add(q9q1_answer);
        answerList.add(q9q2_answer);
        answerList.add(q9q3_answer);
        answerList.add(q9q4_answer);
        answerList.add(q9q5_answer);
        answerList.add(q9q6_answer);
        answerList.add(q9q7_answer);
        answerList.add(q9q8_answer);
        answerList.add(q9q9_answer);

        Button submitbtn = (Button) findViewById(R.id.q_9q_nextbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(RadioGroup x: answerList){
                    score += Integer.parseInt(((RadioButton)findViewById(x.getCheckedRadioButtonId())).getTag().toString());
                }

                EvaluationResult rs = getResult();
                save9QToServer();

                if(rs.isPositive()){

                }else{

                }

            }
        });
    }

    @Override
    public EvaluationResult getResult() {
        if(score >= 1 && score <= 8){
            return new EvaluationResult(score, true, "มีแนวโน้มที่จะฆ่าตัวตายในปัจจุบัน  ระดับน้อย");
        }else if(score >= 9 && score <= 16){
            return new EvaluationResult(score, true, "มีแนวโน้มที่จะฆ่าตัวตายในปัจจุบัน  ระดับปานกลาง");
        }else if(score >= 17){
            return new EvaluationResult(score, true, "มีแนวโน้มที่จะฆ่าตัวตายในปัจจุบัน  ระดับรุนแรง");
        }
        return new EvaluationResult(score, false, "ไม่มีแนวโน้มฆ่าตัวตายในปัจจุบัน");
    }

    private void save9QToServer() {
        String q2q_url = getResources().getString(R.string.evaluation_url);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Q9Q.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, q2q_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                db.insertLogonResgist(response.toString(), email);
//                registFadeout();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Q9Q.this);
                builder.setTitle("Error!");
                builder.setMessage(error.getMessage()).create();
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.create();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("type", "9Q");
                MyData.put("id", db.getUserID());
                MyData.put("score", score + "");
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
    }
}
