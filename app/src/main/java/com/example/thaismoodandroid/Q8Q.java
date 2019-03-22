package com.example.thaismoodandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Q8Q extends AppCompatActivity implements Evaluation {

    private int score = 0;
    private RadioGroup q8q1_answer, q8q2_answer, q8q3_answer, q8q4_answer, q8q5_answer,
            q8q6_answer, q8q7_answer, q8q8_answer;
    private ArrayList<RadioGroup> answerList;
    private final LogonDatabase db = new LogonDatabase(this);
    private boolean is31Selected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_8_q);

        answerList = new ArrayList<>();
        q8q1_answer = (RadioGroup) findViewById(R.id.q_8q_1_answer);
        q8q2_answer = (RadioGroup) findViewById(R.id.q_8q_2_answer);
        q8q3_answer = (RadioGroup) findViewById(R.id.q_8q_3_answer);
        q8q4_answer = (RadioGroup) findViewById(R.id.q_8q_4_answer);
        q8q5_answer = (RadioGroup) findViewById(R.id.q_8q_5_answer);
        q8q6_answer = (RadioGroup) findViewById(R.id.q_8q_6_answer);
        q8q7_answer = (RadioGroup) findViewById(R.id.q_8q_7_answer);
        q8q8_answer = (RadioGroup) findViewById(R.id.q_8q_8_answer);
        answerList.add(q8q1_answer);
        answerList.add(q8q2_answer);
        answerList.add(q8q3_answer);
        answerList.add(q8q4_answer);
        answerList.add(q8q5_answer);
        answerList.add(q8q6_answer);
        answerList.add(q8q7_answer);
        answerList.add(q8q8_answer);

        Button submitbtn = (Button) findViewById(R.id.q_8q_nextbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(RadioGroup x: answerList){
                    score += Integer.parseInt(((RadioButton)findViewById(x.getCheckedRadioButtonId())).getTag().toString());
                }

                EvaluationResult rs = getResult();
                save8QToServer();

                if(rs.isPositive()){

                }else{

                }
            }
        });

        final RadioButton q8_3_1 = (RadioButton) findViewById(R.id.q_8q_3_1);
        final RadioButton q8_3_2 = (RadioButton) findViewById(R.id.q_8q_3_2);
        q8_3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is31Selected){
                    score = score - Integer.parseInt(q8_3_2.getTag().toString());
                    is31Selected = false;
                }
            }
        });


        q8_3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is31Selected = true;
                new IOSDialog.Builder(Q8Q.this)
                        .setMessage(getResources().getString(R.string.q_8q_3_extra))
                        .setPositiveButton("ไม่มี", null)
                        .setNegativeButton("มี", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                score += Integer.parseInt(q8_3_2.getTag().toString());
                            }
                        }).show();
            }
        });
    }

    @Override
    public EvaluationResult getResult() {
        if(score >= 7 && score <= 12){
            return new EvaluationResult(score, true, "มีอาการของโรคซึมเศร้า ระดับน้อย");
        }else if(score >= 13 && score <= 18){
            return new EvaluationResult(score, true, "มีอาการของโรคซึมเศร้า ระดับปานกลาง");
        }else if(score >= 19){
            return new EvaluationResult(score, true, "มีอาการของโรคซึมเศร้า ระดับรุนแรง");
        }
        return new EvaluationResult(score, false, "ไม่มีอาการของโรคซึมเศร้าหรือมีอาการของโรคซึมเศร้าระดับน้อยมาก");
    }

    private void save8QToServer() {
        String q2q_url = getResources().getString(R.string.evaluation_url);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Q8Q.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, q2q_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                db.insertLogonResgist(response.toString(), email);
//                registFadeout();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Q8Q.this);
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
