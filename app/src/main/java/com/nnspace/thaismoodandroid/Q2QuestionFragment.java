package com.nnspace.thaismoodandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Q2QuestionFragment extends Fragment implements IEvaluation {

    private String[] question;
    private Button nextBtn, prevBtn, c1, c2;
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
        nextBtn = getView().findViewById(R.id.q_2q_next_btn);
        prevBtn = getView().findViewById(R.id.q_2q_prev_btn);
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
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
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
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo == 1){
                    finishTask();
                }else{
                    countNo++;
                    nextQuestion();
                    if(currentPoint[countNo] != null){
                        nextBtn.setEnabled(true);
                        nextBtn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
                        prevBtn.setEnabled(true);
                        prevBtn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme_dark));
                        if(currentPoint[countNo] == 0){
                            c1.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                            c1.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            c2.setBackground(getResources().getDrawable(R.drawable.q_2q_choice_selected));
                            c2.setTextColor(getResources().getColor(R.color.white));
                        }
                    }else{
                        nextBtn.setEnabled(false);
                        nextBtn.setBackground(getResources().getDrawable(R.drawable.button_border_unselected));
                        clearButton();
                    }

                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo == 0){
                    prevBtn.setEnabled(false);
                    prevBtn.setBackground(getResources().getDrawable(R.drawable.button_border_unselected));
                }else {
                    countNo--;
                    prevQuestion();
                }
            }
        });

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
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    public void prevQuestion(){
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    public void next(){
        Bundle bundle = new Bundle();
        bundle.putString("from", "2q");
        String[] msgResult = getResources().getStringArray(R.array.q_2q_result);

        if(totalPoint == 0){
            bundle.putString("result", msgResult[0]);
            bundle.putString("todo", msgResult[1]);
            bundle.putString("next", "mdq");
        }else if(totalPoint > 0){
            bundle.putString("result", msgResult[2]);
            bundle.putString("todo", msgResult[3]);
            bundle.putString("next", "9q");
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

}
