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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.DatabaseModel.EvaluationModel;
import com.nnspace.thaismoodandroid.EmergencyContactActivity;
import com.nnspace.thaismoodandroid.HomeActivity.Home2;
import com.nnspace.thaismoodandroid.R;

import java.util.Calendar;

public class EvaluationResultFragment extends Fragment {

    private String from, result, todo, next;
    private TextView band_tx, result_tx, whatTodo_tx;
    private Button nextBtn;
    private int score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        score = bundle.getInt("score");
        from = bundle.getString("from");
        result = bundle.getString("result");
        todo = bundle.getString("todo");
        next = bundle.getString("next");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_evaluation_result, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        band_tx = getView().findViewById(R.id.evaluation_band_text_view);
        result_tx = getView().findViewById(R.id.evaluation_result_text_view);
        whatTodo_tx = getView().findViewById(R.id.evaluation_todo_text_view);
        nextBtn = getView().findViewById(R.id.evaluation_result_next_btn);
        setBand();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment = null;
                switch (next){
                    case "9q":
                        nextFragment = new Q9QuetionFragment();
                        break;
                    case "8q":
                        nextFragment = new Q8QustionFragment();
                        break;
                    case "mdq":
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
        ThaisMoodDB db = new ThaisMoodDB(getContext());
        switch (from){
            case "2q":
                db.insertEvaluationScore(score, EvaluationModel._2q, getDateString());
                band_tx.setText(getResources().getString(R.string.result_2q_band));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_2q_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_2q_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_2q_theme));
                break;
            case "9q":
                db.insertEvaluationScore(score, EvaluationModel._9q, getDateString());
                band_tx.setText(getResources().getString(R.string.result_9q_band));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_9q_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_9q_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_9q_theme));
                break;
            case "8q":
                db.insertEvaluationScore(score, EvaluationModel._8q, getDateString());
                band_tx.setText(getResources().getString(R.string.result_8q_band));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_8q_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_8q_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_8q_theme));
                if(score > 0){
                    q8qWaring();
                }
                break;
            case "mdq":
                db.insertEvaluationScore(score, EvaluationModel.mdq, getDateString());
                band_tx.setText(getResources().getString(R.string.result_mdq_band));
                band_tx.setBackground(getResources().getDrawable(R.drawable.circle_mdq_band));
                result_tx.setBackground(getResources().getDrawable(R.drawable.q_mdq_result_box));
                nextBtn.setBackgroundColor(getResources().getColor(R.color.q_mdq_theme));
                break;
        }

        result_tx.setText(result);
        whatTodo_tx.setText(todo);
    }

    private String getDateString(){

        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

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


}
