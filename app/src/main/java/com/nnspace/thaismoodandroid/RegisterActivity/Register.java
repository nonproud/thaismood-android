package com.nnspace.thaismoodandroid.RegisterActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nnspace.thaismoodandroid.R;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Register extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private Button nextbtn, prevbtn;
    private Fragment nextFragment;
    private FragmentManager fm;
    private FragmentTransaction ft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fragmentContainer = (FrameLayout) findViewById(R.id.register_fragment_container);

//        nextbtn = (Button) findViewById(R.id.register_activity_next_btn);
//        prevbtn = (Button) findViewById(R.id.register_activity_prev_btn);

        nextFragment = new Register_ask_type();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.register_fragment_container, nextFragment);
        ft.commit();

    }

    public void nextBtnMng(int choice){
        switch (choice){
            case 1:
                nextbtn.setEnabled(true);
                break;
            case 2:
                nextbtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
            case 3:
                nextbtn.setEnabled(false);
                break;
            case 4:
                nextbtn.setBackgroundColor(getResources().getColor(R.color.non_select_color));
                break;
        }
    }

    public void nextFragment(Bundle bundle, int next){
        switch (next){
            case 1:
                nextFragment = new Register_ask_sex();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }

        ft = fm.beginTransaction();
        nextFragment.setArguments(bundle);
        ft.replace(R.id.register_fragment_container, nextFragment);
        ft.commit();
    }
}
