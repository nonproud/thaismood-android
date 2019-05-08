package com.nnspace.thaismoodandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RecordListAdapter extends BaseAdapter {

    Context mContext;
    String[] strName;
    int[] resID;

    public RecordListAdapter(Context context, String[] strName, int[] resID){
        this.mContext = context;
        this.strName = strName;
        this.resID = resID;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
