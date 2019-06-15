package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.HomeActivity.Add.AddSleepActivity;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_view_sleep);
                TextView band = dialog.findViewById(R.id.time_band);
                TextView start = dialog.findViewById(R.id.start_text);
                TextView end = dialog.findViewById(R.id.end_text);
                TextView total = dialog.findViewById(R.id.sleep_time_text);
                TextView date = dialog.findViewById(R.id.date_text);
                Button edit = dialog.findViewById(R.id.edit_btn);

                LinearLayout closeBtn = dialog.findViewById(R.id.close_btn);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                band.setText(String.format("%.0f", record.getToalTime()));
                start.setText(record.getStart());
                end.setText(record.getEnd());
                total.setText(String.format("%.0f ชั่วโมง", record.getToalTime()));
                date.setText(record.getDate());
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, AddSleepActivity.class);
                        intent.putExtra("isEdit", true);
                        intent.putExtra("date", record.getDate());
                        intent.putExtra("start", record.getStart());
                        intent.putExtra("end", record.getEnd());
                        intent.putExtra("id", record.getId());
                        intent.putExtra("total", record.getToalTime());
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
