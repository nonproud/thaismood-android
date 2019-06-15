package com.nnspace.thaismoodandroid.EvaluationActivity;

import android.content.DialogInterface;
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

import com.ligl.android.widget.iosdialog.IOSDialog;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;

public class Q8QustionFragment extends Fragment implements IEvaluation {

    private String[] question;
    private Button nextBtn, prevBtn, c1, c2;
    private TextView questiontx, indicator;
    private static int countNo, totalPoint;
    private Boolean[] checkNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_q8_qustion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        countNo = 0;
        totalPoint = 0;
        checkNo = new Boolean[9];
        question = getResources().getStringArray(R.array.q_8q_question);
        nextBtn = getView().findViewById(R.id.q_8q_next_btn);
        prevBtn = getView().findViewById(R.id.q_8q_prev_btn);
        questiontx = getView().findViewById(R.id.q_8q_question_tx);
        indicator = getView().findViewById(R.id.q_8q_indicator);
        c1 = getView().findViewById(R.id.q_8q_choice_1);
        c2 = getView().findViewById(R.id.q_8q_choice_2);
        questiontx.setText(question[countNo]);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_selected));
                c1.setTextColor(getResources().getColor(R.color.white));
                c2.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_unselected));
                c2.setTextColor(getResources().getColor(R.color.black));
                checkNo[countNo] = false;
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_8q_theme_dark));
                if(countNo == 2){checkNo[8] = false;}
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_selected));
                c2.setTextColor(getResources().getColor(R.color.white));
                c1.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_unselected));
                c1.setTextColor(getResources().getColor(R.color.black));
                checkNo[countNo] = true;
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_8q_theme_dark));
                if(countNo == 2){
                    new IOSDialog.Builder(getActivity())
                            .setMessage(question[8])
                            .setPositiveButton(getResources().getString(R.string.q_8q_ans_2_ex), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkNo[8] = true;
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.q_8q_ans_1_ex), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkNo[8] = false;
                                }
                            }).show();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo == 7){
                    finishTask();
                }else{
                    countNo++;
                    nextQuestion();
                    if(checkNo[countNo] != null){
                        nextBtn.setEnabled(true);
                        nextBtn.setBackgroundColor(getResources().getColor(R.color.q_8q_theme_dark));
                        prevBtn.setEnabled(true);
                        prevBtn.setBackgroundColor(getResources().getColor(R.color.q_8q_theme_dark));
                        if(checkNo[countNo] == false){
                            c1.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_selected));
                            c1.setTextColor(getResources().getColor(R.color.white));
                        }else if(checkNo[countNo] == true){
                            c2.setBackground(getResources().getDrawable(R.drawable.q_9q_choice_selected));
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

    }

    @Override
    public String getStringIndicator(int no) {
        no = no + 1;
        return "ข้อที่ " + no + " จาก 8 ข้อ";
    }

    @Override
    public void finishTask() {
        for(int i = 0; i < 9; i++){
            totalPoint += 0;
            switch (i){
                case 0:
                    if(checkNo[i])
                        totalPoint += 1;
                    break;
                case 1:
                    if(checkNo[i])
                        totalPoint += 2;
                    break;
                case 2:
                    if(checkNo[i])
                        totalPoint += 6;
                    break;
                case 3:
                    if(checkNo[i])
                        totalPoint += 8;
                    break;
                case 4:
                    if(checkNo[i])
                        totalPoint += 9;
                    break;
                case 5:
                    if(checkNo[i])
                        totalPoint += 4;
                    break;
                case 6:
                    if(checkNo[i])
                        totalPoint += 10;
                    break;
                case 7:
                    if(checkNo[i])
                        totalPoint += 4;
                    break;
                case 8:
                    if(checkNo[i])
                        totalPoint += 8;
                    break;
            }
        }

        next();
    }

    @Override
    public void nextQuestion() {
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    @Override
    public void prevQuestion() {
        questiontx.setText(question[countNo]);
        indicator.setText(getStringIndicator(countNo));
    }

    @Override
    public void next() {
        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        db.insertEvaluationScore(totalPoint, EvaluationModel._8q, getDateString());
        Bundle bundle = new Bundle();
        bundle.putInt("from", 3);
        bundle.putInt("score", totalPoint);
        String[] msgResult = getResources().getStringArray(R.array.q_8q_result);

        if(totalPoint == 0){
            bundle.putString("result", msgResult[0]);
            bundle.putString("todo", msgResult[1]);
            bundle.putInt("next", 4);
            bundle.putString("emegency", "false");
        }else if(totalPoint >= 1 && totalPoint <= 8){
            bundle.putString("result", msgResult[1]);
            bundle.putString("todo", msgResult[4]);
            bundle.putInt("next", 4);
            bundle.putString("emegency", "false");
        }else if(totalPoint >=9 && totalPoint <= 16){
            bundle.putString("result", msgResult[2]);
            bundle.putString("todo", msgResult[4]);
            bundle.putInt("next", 4);
            bundle.putString("emegency", "false");
        }else if(totalPoint >= 17){
            bundle.putString("result", msgResult[3]);
            bundle.putString("todo", msgResult[4]);
            bundle.putInt("next", 4);
            bundle.putString("emegency", "true");
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
        c1.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_unselected));
        c1.setTextColor(getResources().getColor(R.color.black));
        c2.setBackground(getResources().getDrawable(R.drawable.q_8q_choice_unselected));
        c2.setTextColor(getResources().getColor(R.color.black));
    }

    private String getDateString(){

        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

    }
}
