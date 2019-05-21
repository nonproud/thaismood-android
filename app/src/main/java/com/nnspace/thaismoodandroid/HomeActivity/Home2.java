package com.nnspace.thaismoodandroid.HomeActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
import com.nnspace.thaismoodandroid.HomeActivity.Add.AddNewRecord;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.FragmentDiary;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.WriteNoteActivity;
import com.nnspace.thaismoodandroid.HomeActivity.Graph.FragmentGraph;
import com.nnspace.thaismoodandroid.HomeActivity.List.FragmentList;
import com.nnspace.thaismoodandroid.R;
import com.nnspace.thaismoodandroid.SlideInAnimationHandler;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class Home2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private AHBottomNavigation navigation;
    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        toolbar = findViewById(R.id.toolbar);
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
    }

    private void setUpAddBtn() {
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this );
        ImageView rlIcon4 = new ImageView(this);
        ImageView rlIcon5 = new ImageView(this);
        rlIcon1.getLayoutParams().width = 40;
        rlIcon2.getLayoutParams().width = 40;
        rlIcon3.getLayoutParams().width = 40;
        rlIcon4.getLayoutParams().width = 40;
        rlIcon5.getLayoutParams().width = 40;

        rlIcon1.getLayoutParams().height = 40;
        rlIcon2.getLayoutParams().height = 40;
        rlIcon3.getLayoutParams().height = 40;
        rlIcon4.getLayoutParams().height = 40;
        rlIcon5.getLayoutParams().height = 40;

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);


        SubActionButton addMoodSub = rLSubBuilder.setContentView(rlIcon1).build();
        SubActionButton addSleepSub = rLSubBuilder.setContentView(rlIcon2).build();
        SubActionButton addActivitySub = rLSubBuilder.setContentView(rlIcon3).build();
        SubActionButton addExerciseSub = rLSubBuilder.setContentView(rlIcon4).build();
        SubActionButton addNoteSub = rLSubBuilder.setContentView(rlIcon5).build();

        addMoodSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this, AddNewRecord.class));
            }
        });

        addNoteSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home2.this, WriteNoteActivity.class);
                startActivity(intent);
            }
        });

        rlIcon1.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_mood));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_sleep));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_activity));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_excercise));
        rlIcon5.setImageDrawable(getResources().getDrawable(R.drawable.ic_pencil));



        FloatingActionMenu centerBottomMenu = new FloatingActionMenu.Builder(this)
                .setStartAngle(0)
                .setEndAngle(-180)
                .setAnimationHandler(new SlideInAnimationHandler())
                .addSubActionView(addMoodSub)
                .addSubActionView(addSleepSub)
                .addSubActionView(addActivitySub)
                .addSubActionView(addExerciseSub)
                .addSubActionView(addNoteSub)
                .attachTo(add)
                .build();


    }

    private void setUpBottomNav() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_oneitem, R.drawable.list, R.color.mood_red);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_graph, R.drawable.graph, R.color.mood_yellow);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_blank, R.drawable.ic_home, R.color.mood_green);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.title_help, R.drawable.ic_help, R.color.mood_grey);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.title_diary, R.drawable.ic_diary, R.color.mood_violet);

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
        navigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
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
                        fragment = new FragmentList();
                        break;
                    case 1:
                        fragment = new FragmentGraph();
                        break;
                    case 2:
                        return false;
                    case 3:
                        fragment = new FragmentEmergencyData();
                        break;
                    case 4:
                        fragment = new FragmentDiary();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home2_fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }
}
