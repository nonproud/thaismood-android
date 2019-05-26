package com.nnspace.thaismoodandroid.HomeActivity.Diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        holder.title.setText(diayObjectList.get(position).getTitle());
        holder.abStory.setText(diayObjectList.get(position).getAbStory());
        holder.date.setText(diayObjectList.get(position).getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, diayObjectList.get(position).getStory(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return diayObjectList.size();
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView title, abStory, date;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.diary_item_title);
            abStory = itemView.findViewById(R.id.diary_ab_item_story);
            date = itemView.findViewById(R.id.diary_item_date);
        }
    }
}
