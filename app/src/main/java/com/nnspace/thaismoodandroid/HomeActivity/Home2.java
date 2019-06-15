package com.nnspace.thaismoodandroid.HomeActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.nnspace.thaismoodandroid.AboutActivity;
import com.nnspace.thaismoodandroid.CheckBipolar;
import com.nnspace.thaismoodandroid.CheckDepress;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.EvaluationActivity.Evaluation;
import com.nnspace.thaismoodandroid.EvaluationHistory.EvaluationHistoryActivity;
import com.nnspace.thaismoodandroid.FIndHospitalActivity;
import com.nnspace.thaismoodandroid.GetTemporaryCredential;
import com.nnspace.thaismoodandroid.HomeActivity.Add.AddMoodActivity;
import com.nnspace.thaismoodandroid.HomeActivity.Add.AddSleepActivity;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.FragmentDiary;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.WriteNoteActivity;
import com.nnspace.thaismoodandroid.HomeActivity.Graph.FragmentGraph;
import com.nnspace.thaismoodandroid.HomeActivity.List.FragmentList;
import com.nnspace.thaismoodandroid.MainActivity;
import com.nnspace.thaismoodandroid.MyCalender;
import com.nnspace.thaismoodandroid.ProfileActivity;
import com.nnspace.thaismoodandroid.R;
import com.nnspace.thaismoodandroid.SettingActivity;
import com.nnspace.thaismoodandroid.SlideInAnimationHandler;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class Home2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private AHBottomNavigation navigation;
    private FloatingActionButton add;
    private int currentCount = 1;
    private boolean isDepressed = false, isBipolar = false, isDoEvaluation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.header_graph);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.getMenu().clear();
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        add = findViewById(R.id.add_btn);

        navigation = findViewById(R.id.navigation_bar);
        setUpBottomNav();
        setUpAddBtn();
        ThaisMoodDB db = new ThaisMoodDB(getApplicationContext());

        View headView = navigationView.getHeaderView(0);

        TextView usernameInHeader = headView.findViewById(R.id.add_header_username);
        usernameInHeader.setText(db.getUsername());
        TextView emailInHeader = headView.findViewById(R.id.app_header_email);
        emailInHeader.setText(db.getEmail());

        Intent intent = getIntent();


        try{
            isDepressed = intent.getExtras().getBoolean("depressed");
            isBipolar = intent.getExtras().getBoolean("isBipolar");
            isDoEvaluation = intent.getExtras().getBoolean("evaluation");
        }catch (NullPointerException e){

        }

        CheckDepress checkDepress = new CheckDepress(getApplicationContext());
        CheckBipolar checkBipolar = new CheckBipolar(getApplicationContext());

        if(checkDepress.getIsDepress()){
            final Dialog dialog = new Dialog(Home2.this);
            dialog.setContentView(R.layout.dialog_depress_warning);
            LinearLayout okAction = dialog.findViewById(R.id.ok_action);
            TextView doEvaluation = dialog.findViewById(R.id.do_evaluation);
            LinearLayout normalWarning = dialog.findViewById(R.id.normal_warning);
            LinearLayout evaluationWarn = dialog.findViewById(R.id.evaluation_warning);
            if(checkDepress.isDoEvaluation()){
                doEvaluation.setVisibility(View.VISIBLE);
                evaluationWarn.setVisibility(View.VISIBLE);
                normalWarning.setVisibility(View.GONE);
            }
            okAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            doEvaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Evaluation.class));
                }
            });
            dialog.show();
        }

        if(checkBipolar.getIsBipolar()){
            final Dialog dialog = new Dialog(Home2.this);
            dialog.setContentView(R.layout.dialog_bipolar_warning);
            LinearLayout okAction = dialog.findViewById(R.id.ok_action);
            LinearLayout doEvaluation = dialog.findViewById(R.id.do_evaluation);
            LinearLayout normalWarning = dialog.findViewById(R.id.normal_warning);
            LinearLayout evaluationWarn = dialog.findViewById(R.id.evaluation_warning);
            if(checkBipolar.isDoEvaluation()){
                doEvaluation.setVisibility(View.VISIBLE);
                evaluationWarn.setVisibility(View.VISIBLE);
                normalWarning.setVisibility(View.GONE);
            }
            okAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            doEvaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Evaluation.class));
                }
            });
            dialog.show();
        }

        try{
            String lastDateOfMood = db.getLastDateofMood();
            if(lastDateOfMood != null){
                String[] checkDateArray = MyCalender.findDiffDay(lastDateOfMood);
                int a;
                if((a = Integer.parseInt(checkDateArray[2])) > 0){
                    new IOSDialog.Builder(Home2.this)
                            .setMessage("คุณไม่ได้บันทึกอารมณ์มาแล้ว " + a + " วัน เพื่อความต่อเนื่องของกราฟอารมณ์ โปรดบันทึกย้อนหลังเท่าที่จำได้ให้มากที่สุด")
                            .setPositiveButton("เข้าใจแล้ว", null)
                            .show();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setUpAddBtn() {
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_sleep));
        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_mood));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_pencil));

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);

        SubActionButton addMoodSub = rLSubBuilder.setContentView(rlIcon1).build();
        SubActionButton addSleepSub = rLSubBuilder.setContentView(rlIcon2).build();
        SubActionButton addNoteSub = rLSubBuilder.setContentView(rlIcon4).build();

        addMoodSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2.this, AddMoodActivity.class);
                intent.putExtra("isEdit", false);
                startActivity(intent);
            }
        });

        addNoteSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2.this, WriteNoteActivity.class);
                intent.putExtra("isCustom", false);
                intent.putExtra("isHasStory", false);
                startActivity(intent);
            }
        });

        addSleepSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2.this, AddSleepActivity.class);
                intent.putExtra("isEdit", false);
                startActivity(intent);
            }
        });


        try{
            final FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                    .setStartAngle(0)
                    .setEndAngle(-180)
                    .setAnimationHandler(new SlideInAnimationHandler())
                    .addSubActionView(addSleepSub) // Case 2
                    .addSubActionView(addMoodSub) // Case 3
                    .addSubActionView(addNoteSub) // Case 4
                    .enableAnimations()
                    .attachTo(add)
                    .build();
        }catch (Exception e){

        }


    }

    private void setUpBottomNav() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_oneitem, R.drawable.list, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_graph, R.drawable.graph, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_blank, R.drawable.ic_home, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.title_diary, R.drawable.ic_diary, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.title_help, R.drawable.ic_help, R.color.colorPrimary);

        navigation.addItem(item1);
        navigation.addItem(item2);
        navigation.addItem(item3);
        navigation.addItem(item4);
        navigation.addItem(item5);
        navigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        navigation.setBehaviorTranslationEnabled(true);
        navigation.manageFloatingActionButtonBehavior(add);
        navigation.setAccentColor(Color.parseColor("#F63D2B"));
        navigation.setInactiveColor(Color.parseColor("#747474"));
        navigation.setForceTint(true);
        navigation.setTranslucentNavigationEnabled(true);
        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        navigation.setColored(true);
        navigation.setCurrentItem(1);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home2_fragment_container, new FragmentGraph())
                .commit();


        navigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                Fragment fragment = null;
                switch (position){
                    case 0:
                        currentCount = 0;
                        toolbar.setTitle(R.string.header_fragment_list);
                        fragment = new FragmentList();
                        break;
                    case 1:
                        currentCount = 1;
                        toolbar.setTitle(R.string.header_graph);
                        fragment = new FragmentGraph();
                        break;
                    case 2:
                        return false;
                    case 3:
                        currentCount = 3;
                        toolbar.setTitle(R.string.header_diary);
                        fragment = new FragmentDiary();
                        break;
                    case 4:
                        currentCount = 4;
                        toolbar.setTitle(R.string.header_help);
                        fragment = new FragmentEmergencyData();
                        break;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home2_fragment_container, fragment)
                        .commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            startActivity(new Intent(Home2.this, ProfileActivity.class));

        } else if (id == R.id.nav_evaluation_history) {

            startActivity(new Intent(Home2.this, EvaluationHistoryActivity.class));

        }else if (id == R.id.nav_temp_password) {

            startActivity(new Intent(Home2.this, GetTemporaryCredential.class));

        }else if (id == R.id.nav_evaluation) {

            startActivity(new Intent(Home2.this, Evaluation.class));

        }else if (id == R.id.nav_settings) {

            startActivity(new Intent(Home2.this, SettingActivity.class));

        }else if(id == R.id.nav_find_hospital){
            startActivity(new Intent(Home2.this, FIndHospitalActivity.class));

        } else if (id == R.id.nav_about) {

            startActivity(new Intent(Home2.this, AboutActivity.class));

        } else if (id == R.id.nav_logout) {
            new IOSDialog.Builder(Home2.this)
                    .setMessage("ท่านต้องการลงชื่อออกจากระบบหรือไม่")
                    .setPositiveButton("ยกเลิก", null)
                    .setNegativeButton("ออกจากระบบ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ThaisMoodDB db = new ThaisMoodDB(Home2.this);
                            db.signOut();
                            startActivity(new Intent(Home2.this, MainActivity.class));
                        }
                    })
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        CheckDepress checkDepress = new CheckDepress(getApplicationContext());
        CheckBipolar checkBipolar = new CheckBipolar(getApplicationContext());

        super.onResume();
        Fragment fragment = null;
        switch (currentCount){
            case 0:
                toolbar.setTitle(R.string.header_fragment_list);
                fragment = new FragmentList();
                break;
            case 1:
                toolbar.setTitle(R.string.header_graph);
                fragment = new FragmentGraph();
                break;
            case 3:
                toolbar.setTitle(R.string.header_diary);
                fragment = new FragmentDiary();
                break;
            case 4:
                toolbar.setTitle(R.string.header_help);
                fragment = new FragmentEmergencyData();
                break;

        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home2_fragment_container, fragment)
                .commit();

        if(checkDepress.getIsDepress()){
            final Dialog dialog = new Dialog(Home2.this);
            dialog.setContentView(R.layout.dialog_depress_warning);
            LinearLayout okAction = dialog.findViewById(R.id.ok_action);
            TextView doEvaluation = dialog.findViewById(R.id.do_evaluation);
            LinearLayout normalWarning = dialog.findViewById(R.id.normal_warning);
            LinearLayout evaluationWarn = dialog.findViewById(R.id.evaluation_warning);
            if(checkDepress.isDoEvaluation()){
                doEvaluation.setVisibility(View.VISIBLE);
                evaluationWarn.setVisibility(View.VISIBLE);
                normalWarning.setVisibility(View.GONE);
            }
            okAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            doEvaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Evaluation.class));
                }
            });
            dialog.show();
        }

        if(checkBipolar.getIsBipolar()){
            final Dialog dialog = new Dialog(Home2.this);
            dialog.setContentView(R.layout.dialog_bipolar_warning);
            LinearLayout okAction = dialog.findViewById(R.id.ok_action);
            LinearLayout doEvaluation = dialog.findViewById(R.id.do_evaluation);
            LinearLayout normalWarning = dialog.findViewById(R.id.normal_warning);
            LinearLayout evaluationWarn = dialog.findViewById(R.id.evaluation_warning);
            if(checkBipolar.isDoEvaluation()){
                doEvaluation.setVisibility(View.VISIBLE);
                evaluationWarn.setVisibility(View.VISIBLE);
                normalWarning.setVisibility(View.GONE);
            }
            okAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            doEvaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Evaluation.class));
                }
            });
            dialog.show();
        }
    }
}
