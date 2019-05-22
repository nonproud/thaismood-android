package com.nnspace.thaismoodandroid.EvaluationActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.R;

import static android.content.Context.WINDOW_SERVICE;

public class QMDQFragment extends Fragment implements IEvaluation {

    private String[] question;
    private Button nextBtn, prevBtn, c1, c2, c1_3, c2_3,c3_3, c4_3;
    private TextView questionIntrotx, questiontx, indicator, question15tx;
    private static int countNo, part1Point, part2Point, part3Point;
    private Integer[] currentPoint;
    private LinearLayout part12, part3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qmdq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adjustFontScale(getActivity().getResources().getConfiguration());

        countNo = 0;
        part1Point = 0; part2Point = 0; part3Point = 0;
        currentPoint = new Integer[15];
        question = getResources().getStringArray(R.array.q_mdq_question);
        nextBtn = getView().findViewById(R.id.q_mdq_next_btn);
        prevBtn = getView().findViewById(R.id.q_mdq_prev_btn);
        questiontx = getView().findViewById(R.id.q_mdq_question_tx);
        questionIntrotx = getView().findViewById(R.id.q_mdq_intro_tx);
        questiontx.setText(question[0]);
        questionIntrotx.setText(getView().getResources().getString(R.string.mdq_1));
        c1 = getView().findViewById(R.id.q_mdq_choice_1);
        c2 = getView().findViewById(R.id.q_mdq_choice_2);
        c1_3 = getView().findViewById(R.id.q_mdq_part_3_choice_1);
        c2_3 = getView().findViewById(R.id.q_mdq_part_3_choice_2);
        c3_3 = getView().findViewById(R.id.q_mdq_part_3_choice_3);
        c4_3 = getView().findViewById(R.id.q_mdq_part_3_choice_4);
        indicator = getView().findViewById(R.id.q_mdq_indicator);
        part12 = getView().findViewById(R.id.q_mdq_part_1_2_section);
        part3 = getView().findViewById(R.id.q_mdq_part_3_section);
        question15tx = getView().findViewById(R.id.q_mdq_question_15_tx);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                c1.setTextColor(getResources().getColor(R.color.white));
                c2.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c2.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c1.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                c2.setTextColor(getResources().getColor(R.color.white));
                c1.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c1.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c2.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
            }
        });

        c1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                c1_3.setTextColor(getResources().getColor(R.color.white));
                c2_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c2_3.setTextColor(getResources().getColor(R.color.black));
                c3_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c3_3.setTextColor(getResources().getColor(R.color.black));
                c4_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c4_3.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c1_3.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
            }
        });

        c2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c1_3.setTextColor(getResources().getColor(R.color.black));
                c2_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                c2_3.setTextColor(getResources().getColor(R.color.white));
                c3_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c3_3.setTextColor(getResources().getColor(R.color.black));
                c4_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c4_3.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c2_3.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
            }
        });

        c3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c1_3.setTextColor(getResources().getColor(R.color.black));
                c2_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c2_3.setTextColor(getResources().getColor(R.color.black));
                c3_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                c3_3.setTextColor(getResources().getColor(R.color.white));
                c4_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c4_3.setTextColor(getResources().getColor(R.color.black));
                currentPoint[countNo] = Integer.parseInt(c3_3.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
            }
        });

        c4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c1_3.setTextColor(getResources().getColor(R.color.black));
                c2_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c2_3.setTextColor(getResources().getColor(R.color.black));
                c3_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
                c3_3.setTextColor(getResources().getColor(R.color.black));
                c4_3.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                c4_3.setTextColor(getResources().getColor(R.color.white));
                currentPoint[countNo] = Integer.parseInt(c4_3.getTag().toString());
                nextBtn.setEnabled(true);
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countNo == 14){
                    finishTask();
                }else{
                    if(countNo == 12){
                        questionIntrotx.setVisibility(View.GONE);
                        clearButton();
                        nextQuestion();
                    }else if(countNo == 13) {
                        part12.setVisibility(View.GONE);
                        part3.setVisibility(View.VISIBLE);
                        question15tx.setVisibility(View.VISIBLE);
                        question15tx.setText(question[14]);
                        questiontx.setVisibility(View.GONE);
                    }
                        countNo++;
                    nextQuestion();
                    if(currentPoint[countNo] != null){
                        nextBtn.setEnabled(true);
                        nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
                        prevBtn.setEnabled(true);
                        prevBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme_dark));
                        if(currentPoint[countNo] == 0){
                            c1.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
                            c1.setTextColor(getResources().getColor(R.color.white));
                        }else{
                            c2.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_selected));
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

    @Override
    public String getStringIndicator(int no) {
        no = no + 1;
        return "ข้อที่ " + no + " จาก 15 ข้อ";
    }

    @Override
    public void finishTask() {
        for(int i = 0; i < 12; i++){
            part1Point += currentPoint[i];
        }
        part2Point = currentPoint[13];
        part3Point = currentPoint[14];
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
        Bundle bundle = new Bundle();
        bundle.putString("from", "mdq");
        String[] msgResult = getResources().getStringArray(R.array.q_mdq_result);
        String point = "1: " + part1Point + " 2: " + part2Point + " 3: " + part3Point;
        if(part1Point >= 7 && part2Point > 0 && part3Point >= 2){
            bundle.putString("result", msgResult[0]);
            bundle.putString("todo", point);
            bundle.putString("next", "null");
        }else{
            bundle.putString("result", msgResult[1]);
            bundle.putString("todo", point);
            bundle.putString("next", "null");
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
        c1.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
        c1.setTextColor(getResources().getColor(R.color.black));
        c2.setBackground(getResources().getDrawable(R.drawable.q_mdq_choice_unselected));
        c2.setTextColor(getResources().getColor(R.color.black));
    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
}
