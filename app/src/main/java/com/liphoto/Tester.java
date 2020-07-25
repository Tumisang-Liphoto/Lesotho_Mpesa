package com.liphoto;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Tester extends AppCompatActivity {
    TextView TesterActivityTitle;
    TextView TextViewVariable_1_Tester;
    private static final String TAG = "Tester";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);
        //Log.i(TAG, "in tester activity.");

        TesterActivityTitle = findViewById(R.id.TesterActivityTitle);
        TextViewVariable_1_Tester = findViewById(R.id.TextViewVariable_1_Tester);

        Bundle bundle = getIntent().getExtras();//get variables from transaction
        if(bundle.getString("ReturnedMpesaBalance") != null){
            String MpesaBalance = bundle.getString("ReturnedMpesaBalance");
            TextViewVariable_1_Tester.setText(MpesaBalance);
        }


    }
}