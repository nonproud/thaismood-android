package com.example.thaismoodandroid.MainUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.thaismoodandroid.R;
import com.example.thaismoodandroid.PartUI.register_form;
import com.ligl.android.widget.iosdialog.IOSDialog;
import com.ligl.android.widget.iosdialog.IOSSheetDialog;

public class SignInOn extends AppCompatActivity {

    private Button loginBtn, registBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_sign_in_on);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registBtn = (Button) findViewById(R.id.registBtn);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IOSSheetDialog.SheetItem[] items = new IOSSheetDialog.SheetItem[2];
                items[0] = new IOSSheetDialog.SheetItem("ผู้ใช้ทั่วไป", IOSSheetDialog.SheetItem.BLUE);
                items[1] = new IOSSheetDialog.SheetItem("ผู้ป่วย", IOSSheetDialog.SheetItem.BLUE);


                IOSSheetDialog dialog2 = new IOSSheetDialog.Builder(SignInOn.this)
                        .setTitle("เลือกประเภทสมาชิก").setData(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){
                                   startActivity(new Intent(SignInOn.this, register_form.class));
                                }else if(which == 1){
                                    /* Patient Form */
                                    new IOSDialog.Builder(SignInOn.this)
                                            .setTitle("คุณเลือก")
                                            .setMessage("ผู้ป่วย")
                                            .setPositiveButton("OK", null)
                                            .setNegativeButton("Cancel", null).show();
                                }
                            }
                        }).setCancelText("ยกเลิก").show();
            }
        });
    }

    public void adjustFontScale(Configuration configuration){
        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
}
