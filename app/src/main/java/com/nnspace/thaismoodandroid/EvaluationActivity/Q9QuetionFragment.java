package com.nnspace.thaismoodandroid.EvaluationActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.R;

public class Q9QuetionFragment extends Fragment implements IEvaluation {

    private String[] question;
    private Button nextBtn, prevBtn, c1, c2, c3, c4;
    private TextView questiontx, indicator;
    private static int countNo, totalPoint;
    private Integer[] currentPoint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_q9_quetion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countNo = 0;
        totalPoint = 0;
        currentPoint = new Integer[9];
        question = getResources().getStringArray(R.array.q_9q_question);
        nextBtn = getView().findViewById(R.id.q_9q_next_btn);
        prevBtn = getView().findViewById(R.id.q_9q_prev_btn);
        questiontx = getView().findViewById(R.id.q_9q_question_tx);
        indicator = getView().findViewById(R.id.q_9q_indicator);
        c1 = getView().findViewById(R.id.q_9q_choice_1);
        c2 = getView().findViewById(R.id.q_9q_choice_2);
        c3 = getView().findViewById(R.id.q_9q_choice_3);
        c4 = getView().findViewById(R.id.q_9q_choice_4);
        questiontx.setText(question[countNo]);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                c1.setTextColor(getResources().getColor(R.color.white));
                c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c2.setTextColor(getResources().getColor(R.color.black));
                c3.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c3.setTextColor(getResources().getColor(R.color.black));
                c4.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c4.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c1.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme_drak));
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c1.setTextColor(getResources().getColor(R.color.black));
                c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                c2.setTextColor(getResources().getColor(R.color.white));
                c3.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c3.setTextColor(getResources().getColor(R.color.black));
                c4.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c4.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c2.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme_drak));
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c1.setTextColor(getResources().getColor(R.color.black));
                c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c2.setTextColor(getResources().getColor(R.color.black));
                c3.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                c3.setTextColor(getResources().getColor(R.color.white));
                c4.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c4.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c3.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme_drak));
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c1.setTextColor(getResources().getColor(R.color.black));
                c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c2.setTextColor(getResources().getColor(R.color.black));
                c3.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
                c3.setTextColor(getResources().getColor(R.color.black));
                c4.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                c4.setTextColor(getResources().getColor(R.color.white));
                currentPoint[countNo] = Integer.parseInt(c4.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme_drak));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo == 8){
                    finishTask();
                }else{
                    countNo++;
                    nextQuestion();
                    if(currentPoint[countNo] != null){
                        nextBtn.setEnabled(true);
                        nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme_drak));
                        prevBtn.setEnabled(true);
                        prevBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme_drak));
                        if(currentPoint[countNo] == 0){
                            c1.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                            c1.setTextColor(getResources().getColor(R.color.white));
                        }else if(currentPoint[countNo] == 1){
                            c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                            c2.setTextColor(getResources().getColor(R.color.white));
                        }else if(currentPoint[countNo] == 2){
                            c3.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                            c3.setTextColor(getResources().getColor(R.color.white));
                        }else if(currentPoint[countNo] == 3){
                            c4.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
                            c4.setTextColor(getResources().getColor(R.color.white));
                        }
                    }else{
                        nextBtn.setEnabled(false);
                        nextBtn.setBackground(getResources().getDrawable(R.drawable.button_border_unselected));
                        clearButton();
                    }

                }
            }
        });
    }

    public void nextQuestion(){
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    public void prevQuestion(){
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    @Override
    public String getStringIndicator(int no) {
        no = no + 1;
        return "ข้อที่ " + no + " จาก 9 ข้อ";
    }

    public void finishTask(){
        for(int i = 0; i < 9; i++){
            totalPoint += currentPoint[i];
        }
        next();
    }

    public void next(){
        Bundle bundle = new Bundle();
        bundle.putString("from", "9q");
        String[] msgResult = getResources().getStringArray(R.array.q_9q_result);

        if(totalPoint < 7){
            bundle.putString("result", msgResult[0]);
            bundle.putString("todo", msgResult[1]);
            bundle.putString("next", "mdq");
        }else if(totalPoint >=7 && totalPoint < 13){
            bundle.putString("result", msgResult[2]);
            bundle.putString("todo", msgResult[5]);
            bundle.putString("next", "8q");
        }else if(totalPoint >=7 && totalPoint <= 13){
            bundle.putString("result", msgResult[3]);
            bundle.putString("todo", msgResult[5]);
            bundle.putString("next", "8q");
        }else if(totalPoint >= 19){
            bundle.putString("result", msgResult[4]);
            bundle.putString("todo", msgResult[5]);
            bundle.putString("next", "8q");
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
        c1.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
        c1.setTextColor(getResources().getColor(R.color.black));
        c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
        c2.setTextColor(getResources().getColor(R.color.black));
        c3.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
        c3.setTextColor(getResources().getColor(R.color.black));
        c4.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_unselected));
        c4.setTextColor(getResources().getColor(R.color.black));
    }

}
