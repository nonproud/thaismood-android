package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class FragmentDiary extends Fragment {

    private ThaisMoodDB db;
    private ArrayList<DiaryObject> diaryList;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            return inflater.inflate(R.layout.fragment_diary, container, false);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        diaryList = new ArrayList<>();

        FloatingActionButton pencil = getView().findViewById(R.id.diary_fragment_pencil_fab);
        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteNoteActivity.class);
                intent.putExtra("isCustom", false);
                intent.putExtra("isHasStory",false);
                startActivity(intent);
            }
        });

        ListView listView = getView().findViewById(R.id.diary_list_view);
        db = new ThaisMoodDB(getActivity());
        diaryList = db.getAllNote();

        DiaryListAdapter adapter = new DiaryListAdapter(getActivity(), diaryList);
        listView.setAdapter(adapter);

    }
}
