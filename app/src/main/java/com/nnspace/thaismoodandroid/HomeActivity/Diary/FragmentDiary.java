package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView.DiaryObject;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView.RecycleAdapter;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class FragmentDiary extends Fragment {

    private RecyclerView recyclerView;
    private ThaisMoodDB db;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            return inflater.inflate(R.layout.fragment_diary, container, false);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        recyclerView = getView().findViewById(R.id.recyclerView);
        db = new ThaisMoodDB(getActivity());
        RecycleAdapter adapter = new RecycleAdapter();
        ArrayList<DiaryObject> list = new ArrayList<>();
        list = db.getAllNote();
        adapter.setItemList(list);
        recyclerView.setAdapter(adapter);

    }
}
