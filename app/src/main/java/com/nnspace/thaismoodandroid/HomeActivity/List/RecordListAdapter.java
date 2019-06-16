package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.HomeActivity.Add.AddMoodActivity;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    private ArrayList<RecordObject> list;
    private final String levelBand = "ระดับ: ";
    private Context mContext;

    public RecordListAdapter(Context context, ArrayList<RecordObject> list){
        this.list = new ArrayList<>();
        this.list = list;
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
        final RecordObject record = list.get(position);

        holder.moodTitle.setText(record.getMood().getMoodType().getMoodName());
        holder.moodEmo.setImageDrawable(mContext.getResources().getDrawable(record.getMood().getMoodType().getIcon()));
        holder.levelIcon.setImageDrawable(mContext.getResources().getDrawable(record.getMood().getMoodType().getMoodLevelIcon(record.getMood().getLevel())));
        holder.moodLevelTx.setText(levelBand + record.getMood().getLevel());
        holder.date.setText(record.getMood().getDateString());
        holder.colorTag.setBackgroundColor(mContext.getResources().getColor(record.getMood().getMoodType().getColor()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_view_record);

                LinearLayout rootBox = dialog.findViewById(R.id.root_box);
                rootBox.setBackground(mContext.getResources().getDrawable(record.getMood().getMoodType().getBox()));
                LinearLayout moodBox = dialog.findViewById(R.id.mood_box_section);
                moodBox.setBackground(mContext.getResources().getDrawable(record.getMood().getMoodType().getBox()));
                LinearLayout sleepBox = dialog.findViewById(R.id.sleep_time_section);
                sleepBox.setBackground(mContext.getResources().getDrawable(record.getMood().getMoodType().getBox()));

                ImageView moodIcon = dialog.findViewById(R.id.mood_emo);
                moodIcon.setImageDrawable(mContext.getResources().getDrawable(record.getMood().getMoodType().getIcon()));
                TextView moodText = dialog.findViewById(R.id.mood_text);
                moodText.setText("อารมณ์: " + record.getMood().getMoodType().getMoodName());

                ImageView levelIcon = dialog.findViewById(R.id.level_icon);
                levelIcon.setImageDrawable(mContext.getResources().getDrawable(record.getMood().getMoodType().getMoodLevelIcon(record.getMood().getLevel())));
                TextView levelText = dialog.findViewById(R.id.level_text);
                levelText.setText("ระดับ: " + record.getMood().getLevel());

                final TextView date = dialog.findViewById(R.id.record_date_text);
                date.setText(record.getMood().getDateString());

//                TextView startTime = dialog.findViewById(R.id.start_text);
//                TextView endTime = dialog.findViewById(R.id.end_text);
//                TextView sleepTime = dialog.findViewById(R.id.sleep_time_text);
//                startTime.setText("เวลานอน: " + );

                ImageView editMood = dialog.findViewById(R.id.edit_mood);
                editMood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AddMoodActivity.class);
                        intent.putExtra("isEdit", true);
                        intent.putExtra("date", record.getMood().getDateString());
                        intent.putExtra("selectedMood", record.getMood().getMoodType().getNumber());
                        intent.putExtra("level", record.getMood().getLevel());
                        intent.putExtra("id", record.getMood().getId());
                        mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });


                dialog.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
