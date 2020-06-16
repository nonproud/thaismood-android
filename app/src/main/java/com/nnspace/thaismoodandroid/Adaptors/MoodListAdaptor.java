package com.nnspace.thaismoodandroid.Adaptors;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.Activities.AddMoodActivity;
import com.nnspace.thaismoodandroid.Models.MoodObject;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class MoodListAdaptor extends RecyclerView.Adapter<MoodListAdaptor.RecordViewHolder> {

    private ArrayList<MoodObject> list;
    private Context mContext;

    public MoodListAdaptor(Context context, ArrayList<MoodObject> list){
        this.list = new ArrayList<>();
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mood, parent, false);
        MoodListAdaptor.RecordViewHolder dh = new MoodListAdaptor.RecordViewHolder(v);
        return dh;

    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        final MoodObject record = list.get(position);

        holder.dateTextView.setText(record.getDateString());
        holder.mood.setImageDrawable(mContext.getResources().getDrawable(record.getMoodType().getIcon()));
        holder.level.setImageDrawable(mContext.getResources().getDrawable(record.getMoodType().getMoodLevelIcon(record.getLevel())));
        holder.moodTitle.setText(record.getMoodType().getMoodName());
        holder.levelTitle.setText("ระดับ: " + record.getLevel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_view_mood);
                ImageView moodEmo = dialog.findViewById(R.id.mood_emo);
                TextView moodText = dialog.findViewById(R.id.mood_text);
                ImageView levelIcon = dialog.findViewById(R.id.level_icon);
                TextView levelText = dialog.findViewById(R.id.level_text);
                TextView date = dialog.findViewById(R.id.date_text);
                Button edit = dialog.findViewById(R.id.edit_btn);
                LinearLayout closeBtn = dialog.findViewById(R.id.close_btn);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                moodEmo.setImageDrawable(mContext.getResources().getDrawable(record.getMoodType().getIcon()));
                moodText.setText(record.getMoodType().getMoodName());
                levelIcon.setImageDrawable(mContext.getResources().getDrawable(record.getMoodType().getMoodLevelIcon(record.getLevel())));
                levelText.setText(record.getLevel() + "");
                date.setText(record.getDateString());
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AddMoodActivity.class);
                        intent.putExtra("isEdit", true);
                        intent.putExtra("date", record.getDateString());
                        intent.putExtra("selectedMood", record.getMoodType().getNumber());
                        intent.putExtra("level", record.getLevel());
                        intent.putExtra("id", record.getId());
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder{

        TextView dateTextView, moodTitle, levelTitle;
        ImageView mood, level;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.mood_list_date);
            moodTitle = itemView.findViewById(R.id.mood_title);
            levelTitle = itemView.findViewById(R.id.level_title);
            mood = itemView.findViewById(R.id.mood_emo);
            level = itemView.findViewById(R.id.level_icon);
        }
    }
}