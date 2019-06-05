package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryViewHolder> {

    private Context mContext;
    private ArrayList<DiaryObject> diayObjectList;

    public DiaryListAdapter(Context context, ArrayList<DiaryObject> diayObjectList){
        this.mContext = context;
        this.diayObjectList = diayObjectList;
    }


    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_diary, parent, false);
        DiaryViewHolder dh = new DiaryViewHolder(v);
        return dh;

    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, final int position) {
        final DiaryObject diaryObject = diayObjectList.get(position);
        holder.title.setText(diaryObject.getTitle());
        holder.abStory.setText(diaryObject.getAbStory());
        holder.date.setText(diaryObject.getDate());

        try{
            holder.moodIcon.setImageDrawable(mContext.getResources().getDrawable(diaryObject.getMood().getMoodType().getIcon()));
            holder.levelIcon.setImageDrawable(mContext.getResources().getDrawable(diaryObject.getMood().getMoodType().getMoodLevelIcon(diaryObject.getMood().getLevel())));
        }catch (NullPointerException e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WriteNoteActivity.class);
                intent.putExtra("isEditNote", true);
                intent.putExtra("title", diaryObject.getTitle());
                intent.putExtra("story", diaryObject.getStory());
                intent.putExtra("id", diaryObject.getId());
                intent.putExtra("date", diaryObject.getDate());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diayObjectList.size();
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView title, abStory, date;
        ImageView moodIcon, levelIcon;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.diary_item_title);
            abStory = itemView.findViewById(R.id.diary_ab_item_story);
            date = itemView.findViewById(R.id.diary_item_date);
            moodIcon = itemView.findViewById(R.id.diary_item_mood_emo);
            levelIcon = itemView.findViewById(R.id.diary_item_level_emo);
        }
    }
}
