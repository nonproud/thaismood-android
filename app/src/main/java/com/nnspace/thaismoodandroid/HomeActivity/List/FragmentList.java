package com.nnspace.thaismoodandroid.HomeActivity.List;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nnspace.thaismoodandroid.Database.EmotionDB;
import com.nnspace.thaismoodandroid.MoodObject;
import com.nnspace.thaismoodandroid.R;
import com.nnspace.thaismoodandroid.SwipeDetector;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private SwipeDetector swipeDetector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getView().findViewById(R.id.list_listview);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        MyDataObject.remove(positionToRemove);
//                        adapter.notifyDataSetChanged();
                    }});
                adb.show();
                System.out.println("clicked!");
            }
        });

        TextView type = getView().findViewById(R.id.list_type);
        EmotionDB db = new EmotionDB(getActivity());
        ArrayList<MoodObject> moodList = db.getAllMood();
        RecordListAdapter adapter = new RecordListAdapter(getActivity(), moodList);
        listView.setAdapter(adapter);
        type.setText("Amount: " + moodList.size());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.listview_mnu, menu);
    }
}
