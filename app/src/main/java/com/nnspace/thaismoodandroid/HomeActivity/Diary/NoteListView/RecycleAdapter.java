package com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<DiaryObject> noteList = new ArrayList<>();

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_diary, viewGroup, false);
        return new NoteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        DiaryBaseItem item = noteList.get(i);
        DiaryObject cardViewItem = (DiaryObject) item;

        noteViewHolder.setTitle(cardViewItem.getTitle());
        noteViewHolder.setAbStory(cardViewItem.getAbStory());
        noteViewHolder.setDate(cardViewItem.getDate());

    }

    @Override
    public int getItemCount() {
        if (!noteList.isEmpty() || noteList != null) {
            return noteList.size();
        }
        return 0;
    }

    public void setItemList(ArrayList<DiaryObject> list){
        noteList = list;
    }
}
