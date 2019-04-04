package com.example.thaismoodandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Q2Q extends AppCompatActivity implements Evaluation {

    private int score = 0;
    private RadioGroup q2q1_answer, q2q2_answer;
    private ArrayList<RadioGroup> answerList;
    private final LogonDatabase db = new LogonDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2_q);

        q2q1_answer = (RadioGroup) findViewById(R.id.q_2q_1_answer);
        q2q2_answer = (RadioGroup) findViewById(R.id.q_2q_2_answer);
        answerList = new ArrayList<>();
        answerList.add(q2q1_answer);
        answerList.add(q2q2_answer);

        Button submitbtn = (Button) findViewById(R.id.q_2q_nextbtn);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canContinue;
                try{
                    for(RadioGroup x: answerList){
                        score += Integer.parseInt(((RadioButton)findViewById(x.getCheckedRadioButtonId())).getTag().toString());
                    }
                    canContinue = true;
                }catch (NullPointerException ex){
                    canContinue = false;
                    ShowDialog.evaluationNotComplete(Q2Q.this);

                }

                if(canContinue){
                    EvaluationResult rs = getResult();
//                  save2QToServer();


                    if(rs.isPositive()){
                        Intent intent = new Intent(Q2Q.this, Q9Q.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(Q2Q.this, QMDQ.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }

    @Override
    public EvaluationResult getResult() {
        if(score > 0){
            return new EvaluationResult(score, true, "เีความเสี่ยง/มีแนวโน้มที่จะเป็นโรคซึมเศร้า");
        }
        return new EvaluationResult(score, false, "ไม่มีเีความเสี่ยง/ไม่มีแนวโน้มที่จะเป็นโรคซึมเศร้า");
    }

    private void save2QToServer() {
        String q2q_url = getResources().getString(R.string.evaluation_url);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(Q2Q.this);
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, q2q_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                db.insertLogonResgist(response.toString(), email);
//                registFadeout();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Q2Q.this);
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
                MyData.put("type", "2Q");
                MyData.put("id", db.getUserID());
                MyData.put("score", score + "");
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
    }
}
