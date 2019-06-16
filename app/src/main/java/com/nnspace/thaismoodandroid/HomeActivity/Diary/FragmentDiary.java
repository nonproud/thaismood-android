package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class FragmentDiary extends Fragment {

    private ThaisMoodDB db;
    private ArrayList<DiaryObject> diaryList;
    private RecyclerView recyclerView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_diary, container, false);

        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new ThaisMoodDB(getActivity());
        diaryList = new ArrayList<>();
        diaryList = db.getAllNote();
        recyclerView = getView().findViewById(R.id.diary_list_view);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        DiaryListAdapter adapter = new DiaryListAdapter(getActivity(), diaryList);
        recyclerView.setAdapter(adapter);

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

        if(diaryList.size() == 0){
            TextView emptyText = getView().findViewById(R.id.empty_note_text);
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }


    }
}
