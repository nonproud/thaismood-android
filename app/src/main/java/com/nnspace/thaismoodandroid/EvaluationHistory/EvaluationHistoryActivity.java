package com.nnspace.thaismoodandroid.EvaluationHistory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class EvaluationHistoryActivity extends AppCompatActivity {

    private ImageView backBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_history);

        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.evaluation_list_view);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(EvaluationHistoryActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ThaisMoodDB db = new ThaisMoodDB(EvaluationHistoryActivity.this);
        ArrayList<EvaluationObject> evaluationList = db.getAllEvaluation();
        EvaluationListAdapter adapter = new EvaluationListAdapter(EvaluationHistoryActivity.this, evaluationList);
        recyclerView.setAdapter(adapter);
    }
}
