package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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