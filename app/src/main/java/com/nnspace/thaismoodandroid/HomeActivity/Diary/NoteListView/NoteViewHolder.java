package com.nnspace.thaismoodandroid.HomeActivity.Diary.NoteListView;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView abStory;
    private TextView date;
    private ImageView moodEmo;
    private ImageView levelEmo;
    private Context mContext;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.diary_item_title);
        abStory = itemView.findViewById(R.id.diary_item_story);
        date = itemView.findViewById(R.id.diary_item_date);
        moodEmo = itemView.findViewById(R.id.diary_item_mood_emo);
        levelEmo = itemView.findViewById(R.id.diary_item_level_emo);
        mContext = itemView.getContext();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setAbStory(String abStory) {
        this.abStory.setText(abStory);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setMoodEmo(int moodEmo) {
        this.moodEmo.setImageDrawable(mContext.getResources().getDrawable(moodEmo));
    }

    public void setLevelEmo(int levelEmo) {
        this.levelEmo.setImageDrawable(mContext.getResources().getDrawable(levelEmo));
    }


}
