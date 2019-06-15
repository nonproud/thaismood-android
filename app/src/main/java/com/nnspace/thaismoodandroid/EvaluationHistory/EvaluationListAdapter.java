package com.nnspace.thaismoodandroid.EvaluationHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class EvaluationListAdapter extends RecyclerView.Adapter<EvaluationListAdapter.EvaluationViewHolder> {

    private ArrayList<EvaluationObject> list;
    private Context mContext;

    public EvaluationListAdapter(Context context, ArrayList<EvaluationObject> list){
        mContext = context;
        this.list = new ArrayList<>();
        this.list = list;
    }

    @NonNull
    @Override
    public EvaluationListAdapter.EvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_evaluation, parent, false);
        EvaluationListAdapter.EvaluationViewHolder dh = new EvaluationListAdapter.EvaluationViewHolder(v);
        return dh;
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationListAdapter.EvaluationViewHolder holder, int position) {
        EvaluationObject object = list.get(position);
        holder.date.setText(object.getDate());
        holder.q2qResult.setText("2Q: " + object.getQ2qRestult());
        holder.q9qReslut.setText("9Q: " + object.getQ9qResult());
        holder.q8qResult.setText("8Q: " + object.getQ9qResult());
        holder.mdqResult.setText("MDQ: " + object.getMdqResult());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EvaluationViewHolder extends RecyclerView.ViewHolder{

        TextView date, q2qResult, q9qReslut, q8qResult, mdqResult;

        public EvaluationViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.evaluation_date);
            q2qResult = itemView.findViewById(R.id.q2q_result);
            q9qReslut = itemView.findViewById(R.id.q9q_result);
            q8qResult = itemView.findViewById(R.id.q8q_result);
            mdqResult = itemView.findViewById(R.id.mdq_result);
        }
    }
}
