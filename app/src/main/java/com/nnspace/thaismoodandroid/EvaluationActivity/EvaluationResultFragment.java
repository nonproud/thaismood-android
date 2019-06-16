package com.nnspace.thaismoodandroid.EvaluationActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;
import com.nnspace.thaismoodandroid.EmergencyContactActivity;
import com.nnspace.thaismoodandroid.HomeActivity.Home2;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EvaluationResultFragment extends Fragment {

    private String evaluation_url;
    private String result, todo;
    private TextView band_tx, band_des_tx, result_tx, whatTodo_tx;
    private Button nextBtn;
    private int score;
    private ThaisMoodDB db;
    private int next, from;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        score = bundle.getInt("score");
        from = bundle.getInt("from");
        result = bundle.getString("result");
        todo = bundle.getString("todo");
        next = bundle.getInt("next");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluation_result, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new ThaisMoodDB(getActivity());
        evaluation_url = getActivity().getResources().getString(R.string.evaluation_url);
        band_tx = getView().findViewById(R.id.evaluation_band_text_view);
        band_des_tx = getView().findViewById(R.id.evaluation_header_text_view);
        result_tx = getView().findViewById(R.id.evaluation_result_text_view);
        whatTodo_tx = getView().findViewById(R.id.evaluation_todo_text_view);
        nextBtn = getView().findViewById(R.id.evaluation_result_next_btn);
        setBand();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = null;
                switch (next){
                    case 2:
                        nextFragment = new Q9QuetionFragment();
                        break;
                    case 3:
                        nextFragment = new Q8QustionFragment();
                        break;
                    case 4:
                        nextFragment = new QMDQFragment();
                        break;
                    default:
                        startActivity(new Intent(getActivity(), Home2.class));
                        getActivity().finish();
                        return;
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.evaluation_fragment_container, nextFragment);
                ft.commit();
            }
        });
    }

    private void setBand(){
        switch (from){
            case 1:
                band_tx.setText(getResources().getString(R.string.result_2q_band));
                band_des_tx.setText(getView().getResources().getString(R.string.q_2q_header));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_2q_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_2q_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme));
                sentEvaluationResultToServer("2q", score, getDateString());
                break;
            case 2:
                band_tx.setText(getResources().getString(R.string.result_9q_band));
                band_des_tx.setText(getView().getResources().getString(R.string.q_9q_header));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_9q_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_9q_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme));
                db.insertEvaluationScore(score, EvaluationModel._9q, getDateString());
                sentEvaluationResultToServer("9q", score, getDateString());
                break;
            case 3:
                db.insertEvaluationScore(score, EvaluationModel._8q, getDateString());
                band_tx.setText(getResources().getString(R.string.result_8q_band));
                band_des_tx.setText(getView().getResources().getString(R.string.q_8q_header));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_8q_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_8q_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_8q_theme));
                sentEvaluationResultToServer("8q", score, getDateString());
                if(score > 0){
                    q8qWaring();
                }
                break;
            case 4:
                db.insertEvaluationScore(score, EvaluationModel.mdq, getDateString());
                band_tx.setText(getResources().getString(R.string.result_mdq_band));
                band_des_tx.setText(getView().getResources().getString(R.string.q_mdq_header));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_mdq_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_mdq_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme));
                sentEvaluationResultToServer("mdq", score, getDateString());
                break;
        }

        result_tx.setText(result);
        whatTodo_tx.setText(todo);
    }

    private String getDateString(){

        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void q8qWaring() {
        final Dialog warning = new Dialog(getContext());
        warning.setContentView(R.layout.dialog_8q_warning);
        TextView level = warning.findViewById(R.id.level_text);
        level.setText("ท่าน" + result);
        LinearLayout closeBtn = warning.findViewById(R.id.close_btn);
        LinearLayout consultBtn = warning.findViewById(R.id.consult_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning.dismiss();
            }
        });

        consultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EmergencyContactActivity.class));
                warning.dismiss();
            }
        });

        warning.show();
    }

    private void sentEvaluationResultToServer(final String type, final int score, final String date){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getActivity());
        StringRequest myStringRequest = new StringRequest(Request.Method.POST, evaluation_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.toString().equals("1")){
                    try{
                        Toast.makeText(getActivity(), "บันทึกผล " + type + " แล้ว", Toast.LENGTH_LONG).show();
                    }catch (Exception e){

                    }

                }else {
                    try {
                        Toast.makeText(getActivity(), "ยังไม่ได้บันทึกผล " + type, Toast.LENGTH_LONG).show();
                    }catch (Exception e){

                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("username", db.getUsername());
                MyData.put("type", type);
                MyData.put("score", score + "");
                MyData.put("date", date);
                return MyData;
            }

            public Map<String, String> getHeaders(){
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("authorization", db.getToken());
                return MyData;
            }
        };
        MyRequestQueue.add(myStringRequest);
        myStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
