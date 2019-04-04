package com.example.thaismoodandroid;

import android.content.Context;

import com.ligl.android.widget.iosdialog.IOSDialog;

public class WaringDialog {

    public static void evaluationNotComplete(Context context){
        new IOSDialog.Builder(context)
                .setMessage(context.getResources().getString(R.string.q_error))
                .setPositiveButton("คกลง", null).show();
    }

}
