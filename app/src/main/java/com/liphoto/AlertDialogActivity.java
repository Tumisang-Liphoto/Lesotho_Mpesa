package com.liphoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AlertDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();//get variables from transaction

        assert bundle != null;
        if(bundle.getString("ReturnedMpesaBalance") != null){
            String MpesaBalance = bundle.getString("ReturnedMpesaBalance");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    //.setTitle("Mpesa Balance")
                    .setMessage("Your Mpesa balance is "+ "M "+MpesaBalance)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class); //intent to move to <MainActivity> activity
                            startActivity(intent); //run above intent
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}