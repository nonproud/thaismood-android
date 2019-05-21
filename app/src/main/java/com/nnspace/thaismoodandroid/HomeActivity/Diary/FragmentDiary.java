package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nnspace.thaismoodandroid.Database.NoteDB;
import com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView.RecycleAdapter;
import com.nnspace.thaismoodandroid.R;

public class FragmentDiary extends Fragment {

    private RecyclerView recyclerView;
    private NoteDB db;

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
                startActivity(intent);
            }
        });

        recyclerView = getView().findViewById(R.id.recyclerView);
        db = new NoteDB(getContext());
        RecycleAdapter adapter = new RecycleAdapter();
//        adapter.setItemList(db.getAllNote());
//        recyclerView.setAdapter(adapter);

    }
}
