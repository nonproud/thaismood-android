package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.MoodObject;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    private ArrayList<MoodObject> moodObjectList;
    private final String levelBand = "ระดับ: ";
    private Context mContext;

    public RecordListAdapter(Context context, ArrayList<MoodObject> list){
        moodObjectList = new ArrayList<>();
        moodObjectList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_record_list, parent, false);
        RecordListAdapter.RecordViewHolder dh = new RecordListAdapter.RecordViewHolder(v);
        return dh;

    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        MoodObject mood = moodObjectList.get(position);
        holder.moodTitle.setText(mood.getMoodType().getMoodName());
        holder.moodEmo.setImageDrawable(mContext.getResources().getDrawable(mood.getMoodType().getIcon()));
        holder.levelIcon.setImageDrawable(mContext.getResources().getDrawable(mood.getMoodType().getMoodLevelIcon(mood.getLevel())));
        holder.moodLevelTx.setText(levelBand + mood.getLevel());
        holder.date.setText(mood.getDateString());
        holder.colorTag.setBackgroundColor(mContext.getResources().getColor(mood.getMoodType().getColor()));
    }

    @Override
    public int getItemCount() {
        return moodObjectList.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder{

        TextView moodTitle, moodLevelTx, sleepTx, exerciseTx, activityTx, date;
        ImageView moodEmo, levelIcon;
        LinearLayout colorTag;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            moodTitle = itemView.findViewById(R.id.list_record_list_mood_title);
            moodLevelTx = itemView.findViewById(R.id.list_record_list_level_of_mood_title);
            sleepTx = itemView.findViewById(R.id.list_record_list_sleep_time_title);
            exerciseTx = itemView.findViewById(R.id.list_record_list_exercise_title);
            activityTx = itemView.findViewById(R.id.list_record_list_activity_title);
            levelIcon = itemView.findViewById(R.id.list_record_list_level_of_mood_icon);
            date = itemView.findViewById(R.id.list_record_date);
            moodEmo = itemView.findViewById(R.id.list_record_list_mood_icon);
            colorTag = itemView.findViewById(R.id.list_record_root_layout);
        }
    }
}
