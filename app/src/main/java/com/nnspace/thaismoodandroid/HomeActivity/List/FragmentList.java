package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ligl.android.widget.iosdialog.IOSSheetDialog;
import com.nnspace.thaismoodandroid.Database.ThaisMoodDB;
import com.nnspace.thaismoodandroid.R;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private RecyclerView recyclerView;
    private TextView fliter, eye;
    private TextView amount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.record_list_view);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        amount = getView().findViewById(R.id.list_type);
        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        ArrayList<RecordObject> recordList = db.getAllRecordObject();
        RecordListAdapter adapter = new RecordListAdapter(getActivity(), recordList);
        recyclerView.setAdapter(adapter);
        amount.setText("จำนวน: " + recordList.size());

        fliter = getView().findViewById(R.id.fliter);
        eye = getView().findViewById(R.id.view_type);
        fliter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IOSSheetDialog.SheetItem[] items = new IOSSheetDialog.SheetItem[4];
                items[0] = new IOSSheetDialog.SheetItem("รายการสรุป", IOSSheetDialog.SheetItem.BLUE);
                items[1] = new IOSSheetDialog.SheetItem("อารมณ์", IOSSheetDialog.SheetItem.BLUE);
                items[2] = new IOSSheetDialog.SheetItem("การนอน", IOSSheetDialog.SheetItem.BLUE);
                items[3] = new IOSSheetDialog.SheetItem("กิจกรรม", IOSSheetDialog.SheetItem.BLUE);
                IOSSheetDialog dialog2 = new IOSSheetDialog.Builder(getActivity())
                        .setTitle("เลือกมุมมอง")
                        .setData(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        setSummaryView();
                                        break;
                                    case 1:
                                        setMoodView();
                                        break;
                                    case 2:
                                        setSleepView();
                                        break;
                                    case 3:
                                        setActivityView();
                                        break;
                                }
                            }
                        })
                        .setCancelText("ยกเลิก")
                        .show();
            }
        });
    }

    private void setSummaryView(){
        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        ArrayList<RecordObject> recordList = db.getAllRecordObject();
        RecordListAdapter adapter = new RecordListAdapter(getActivity(), recordList);
        recyclerView.setAdapter(adapter);
        amount.setText("จำนวน: " + recordList.size());
    }

    private void setSleepView(){
        ThaisMoodDB db = new ThaisMoodDB(getActivity());
        ArrayList<SleepObject> sleepList = db.getSleep();
        SleepListAdapter adapter = new SleepListAdapter(getActivity(), sleepList);
        recyclerView.setAdapter(adapter);
        amount.setText("จำนวน: " + sleepList.size());
    }

    private void setActivityView(){

    }

    private void setMoodView(){

    }
}
