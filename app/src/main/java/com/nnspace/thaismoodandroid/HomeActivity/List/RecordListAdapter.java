package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.MoodObject;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class RecordListAdapter implements ListAdapter {

    private Context mContext;
    private ArrayList<MoodObject> moodObjectsList;
    private final String levelLabel = "ระดับ: ";

    public RecordListAdapter(Context context, ArrayList<MoodObject> moodObjectsList){
        this.mContext = context;
        this.moodObjectsList = moodObjectsList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        try{
            moodObjectsList.get(position);
        }catch (NullPointerException e){
            return false;
        }
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return moodObjectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return moodObjectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoodObject moodobj = (MoodObject) getItem(position);
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.list_record_list, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        LinearLayout rootBox = convertView.findViewById(R.id.list_record_root_layout);
        TextView moodHeader = convertView.findViewById(R.id.list_record_list_mood_title);
        TextView moodLevel = convertView.findViewById(R.id.list_record_list_level_of_mood_title);
        TextView sleepTime = convertView.findViewById(R.id.list_record_list_sleep_time_title);
        TextView exercise = convertView.findViewById(R.id.list_record_list_excercise_title);
        TextView activity = convertView.findViewById(R.id.list_record_list_activity_title);
        TextView date = convertView.findViewById(R.id.list_record_date);
        ImageView moodEmo = convertView.findViewById(R.id.list_record_list_mood_icon);
        ImageView moodLevelIcon = convertView.findViewById(R.id.list_record_list_level_of_mood_icon);

        moodHeader.setText(moodobj.getMoodType().getMoodName());
        moodLevel.setText(levelLabel + moodobj.getLevel());
        rootBox.setBackgroundColor(mContext.getResources().getColor(moodobj.getMoodType().getColor()));
        moodEmo.setImageDrawable(mContext.getResources().getDrawable(moodobj.getMoodType().getIcon()));
//        date.setText(moodobj.getDateCreated().getDate() + "/" +
//                moodobj.getDateCreated().getMonth() + "/" + moodobj.getDateCreated().getYear());
        date.setText(moodobj.getDateString());
        moodLevelIcon.setImageDrawable(mContext.getResources().getDrawable(moodobj.getMoodType().getMoodLevelIcon(moodobj.getLevel())));

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {

        return 1;
    }

    @Override
    public boolean isEmpty() {
        if(moodObjectsList.size() == 0){
            return true;
        }
        return false;
    }
}
