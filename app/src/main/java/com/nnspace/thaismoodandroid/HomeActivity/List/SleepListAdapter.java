package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class SleepListAdapter extends RecyclerView.Adapter<SleepListAdapter.RecordViewHolder> {

    private ArrayList<SleepObject> list;
    private Context mContext;

    public SleepListAdapter(Context context, ArrayList<SleepObject> list){
        this.list = new ArrayList<>();
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sleep_time, parent, false);
        SleepListAdapter.RecordViewHolder dh = new SleepListAdapter.RecordViewHolder(v);
        return dh;

    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        final SleepObject record = list.get(position);

        holder.date.setText(record.getDate());
        holder.icon.setText(String.format("%.0f", record.getToalTime()));
        holder.start.setText(record.getStart());
        holder.end.setText(record.getEnd());
        holder.total.setText(String.format("%.0f ชั่วโมง", record.getToalTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder{

        TextView date, total, start, end, icon;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.sleep_date);
            total = itemView.findViewById(R.id.sleep_time);
            start = itemView.findViewById(R.id.start_time);
            end = itemView.findViewById(R.id.wake_time);
            icon = itemView.findViewById(R.id.sleep_total_tumb);
        }
    }
}
