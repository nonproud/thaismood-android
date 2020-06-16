package com.nnspace.thaismoodandroid.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ligl.android.widget.iosdialog.IOSDialog;
import com.nnspace.thaismoodandroid.Fragments.Q2QuestionFragment;
import com.nnspace.thaismoodandroid.Fragments.Q8QustionFragment;
import com.nnspace.thaismoodandroid.Fragments.Q9QuetionFragment;
import com.nnspace.thaismoodandroid.Fragments.QMDQFragment;
import com.nnspace.thaismoodandroid.R;

public class Evaluation extends AppCompatActivity {

    private int todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        adjustFontScale(getResources().getConfiguration());
        Intent intent = getIntent();
        todo = intent.getExtras().getInt("todo");
        Fragment fragment = null;
        switch (todo){
            case 1:
                fragment  = new Q2QuestionFragment();
                break;
            case 2:
                fragment = new Q9QuetionFragment();
                break;
            case 3:
                fragment = new Q8QustionFragment();
                break;
            case 4:
                fragment = new QMDQFragment();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.evaluation_fragment_container, fragment);
        ft.commit();
    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    @Override
    public void onBackPressed() {

        new IOSDialog.Builder(Evaluation.this)
                .setMessage("ท่านจำเป็นต้องทำแบบประเมินให้จบ")
                .setPositiveButton("ตกลง", null)
                .show();
    }
}
