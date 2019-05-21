package com.nnspace.thaismoodandroid.HomeActivity.Graph;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentGraph extends Fragment {

    private FragmentManager fm;
    private FragmentTransaction ft;
    private int lastPage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_graph,container, false);
        // Setting ViewPager for each Tabs


//        return view;
        return inflater.inflate(R.layout.fragment_graph, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.graph_tabs);
        tabs.setupWithViewPager(viewPager);

//    fm = getActivity().getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        Fragment nextFragment = new GraphWeek();
//        ft.replace(R.id.graph_fragment_container, nextFragment);
//        ft.commit();
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new GraphWeek(), "สัปดาห์");
        adapter.addFragment(new GraphMonth(), "เดือน");
        adapter.addFragment(new GraphYear(), "ปี");
        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
