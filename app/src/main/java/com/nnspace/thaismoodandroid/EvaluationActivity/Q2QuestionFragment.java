package com.nnspace.thaismoodandroid.EvaluationActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;

public class Q2QuestionFragment extends Fragment implements IEvaluation {

    private String[] question;
    private Button prev_btn, next_btn, c1, c2;
    private TextView questiontx, indicator;
    private static int countNo, totalPoint;
    private Integer[] currentPoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_q2_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countNo = 0;
        totalPoint = 0;
        currentPoint = new Integer[2];
        question = getResources().getStringArray(R.array.q_2q_question);
        next_btn = getView().findViewById(R.id.q_2q_next_btn);
        prev_btn = getView().findViewById(R.id.q_2q_prev_btn);
        questiontx = getView().findViewById(R.id.q_2q_question_tx);
        c1 = getView().findViewById(R.id.q_2q_choice_1);
        c2 = getView().findViewById(R.id.q_2q_choice_2);
        indicator = getView().findViewById(R.id.q_2q_indicator);

        questiontx.setText(question[countNo]);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                c1.setTextColor(getResources().getColor(R.color.white));
                c2.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_unselected));
                c2.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c1.getTag().toString());
                next_btn.setEnabled(true);
                next_btn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                c2.setTextColor(getResources().getColor(R.color.white));
                c1.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_unselected));
                c1.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c2.getTag().toString());
                next_btn.setEnabled(true);
                next_btn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo == 1){
                    finishTask();
                }else{
                    countNo++;
                    nextQuestion();
                    if(currentPoint[countNo] != null){
                        next_btn.setEnabled(true);
                        next_btn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
                        prev_btn.setEnabled(true);
                        prev_btn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
                        if(currentPoint[countNo] == 1){
                            c1.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                            c1.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            c2.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                            c2.setTextColor(getResources().getColor(R.color.white));
                        }
                    }else{
                        next_btn.setEnabled(false);
                        next_btn.setBackground(getResources().getDrawable(R.drawable.button_border_unselected));
                        clearButton();
                    }

                }
            }
        });

        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo != 0){
                    clearButton();
                    countNo--;
                    prevQuestion();
                    setPrevbtn();
                    if(currentPoint[countNo] == 1){
                        if(currentPoint[countNo] == 1){
                            c1.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                            c1.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            c2.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                            c2.setTextColor(getResources().getColor(R.color.white));
                        }
                        next_btn.setEnabled(true);
                        next_btn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
                    }else {
                        next_btn.setEnabled(false);
                        next_btn.setBackground(getResources().getDrawable(R.drawable.button_border_unselected));
                    }

                }
            }
        });

    }

    private void setPrevbtn(){
        if(countNo > 0){
            prev_btn.setEnabled(true);
            prev_btn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
        }else {
            prev_btn.setEnabled(false);
            prev_btn.setBackground(getResources().getDrawable(R.drawable.button_border_unselected));
        }
    }

    public String getStringIndicator(int no){
        no = no + 1;
        return "ข้อที่ " + no + " จาก 2 ข้อ";
    }

    public void finishTask(){
        for(int i = 0; i < 2; i++){
            totalPoint += currentPoint[i];
        }
        next();
    }

    public void nextQuestion(){
        setPrevbtn();
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    public void prevQuestion(){
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    public void next(){
        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        db.insertEvaluationScore(totalPoint, EvaluationModel._2q, getDateString());
        Bundle bundle = new Bundle();
        bundle.putInt("from", 1);
        bundle.putInt("score", totalPoint);
        String[] msgResult = getResources().getStringArray(R.array.q_2q_result);
        if(totalPoint == 0){
            bundle.putString("result", msgResult[0]);
            bundle.putString("todo", msgResult[1]);
            bundle.putInt("next", 4);
        }else if(totalPoint > 0){
            bundle.putString("result", msgResult[2]);
            bundle.putString("todo", msgResult[3]);
            bundle.putInt("next", 2);
        }

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment nextFragment = new EvaluationResultFragment();
        nextFragment.setArguments(bundle);
        ft.replace(R.id.evaluation_fragment_container, nextFragment);
        ft.commit();
    }

    @Override
    public void clearButton() {
        c1.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_unselected));
        c1.setTextColor(getResources().getColor(R.color.black));
        c2.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_unselected));
        c2.setTextColor(getResources().getColor(R.color.black));
    }

    private String getDateString(){

        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

    }



}
