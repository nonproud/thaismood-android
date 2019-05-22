package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class DiaryListAdapter implements ListAdapter {
    private Context mContext;
    private ArrayList<DiaryObject> diayObjectList;

    public DiaryListAdapter(Context context, ArrayList<DiaryObject> diayObjectList){
        this.mContext = context;
        this.diayObjectList = diayObjectList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        try{
            diayObjectList.get(position);
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
        return diayObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return diayObjectList.get(position);
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
        DiaryObject diaryObject = (DiaryObject) getItem(position);

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.list_diary, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        TextView title = convertView.findViewById(R.id.diary_title);
        TextView abStory = convertView.findViewById(R.id.diary_ab_item_story);
        TextView dateText = convertView.findViewById(R.id.diary_item_date);

        title.setText(diaryObject.getTitle());
        abStory.setText(diaryObject.getAbStory());
        dateText.setText(diaryObject.getStory());

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
        if(diayObjectList.size() == 0){
            return true;
        }
        return false;
    }
}
