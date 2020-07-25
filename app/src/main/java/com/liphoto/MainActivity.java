package com.liphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;

public class MainActivity extends AppCompatActivity {
    Button ButtonSendMoney; //used to send money to another subscriber
    Button ButtonGetBalance; // used to get balance
    // --Commented out by Inspection (7/25/2020 7:21 AM):private static final String TAG = "MainActivity";

    /*********************************************************************
     * Any and all unnecessary logs and toast messages are to be removes,
     * only those for debugging are to be maintained.
     ******************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hover.initialize(this); //initialise hover
        Hover.setBranding("App Activity logo", R.drawable.applogo,getApplicationContext());
        //Log.i(TAG, "in main activity.");


        ButtonGetBalance = findViewById(R.id.ButtonBalance);//press this button to get the balance
        //press this button to send money, the balance will be obtained in the background first to validate sending of money
        ButtonSendMoney = findViewById(R.id.ButtonSendMoney);

        ButtonSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(TAG, "Button send money clicked, moving to send money.");
                Intent intent = new Intent(getApplicationContext(), SendMoney.class); //move to send money activity
                startActivity(intent); //run intent
            }
        });

        ButtonGetBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.i(TAG, "Button get Mpesa balance clicked.");
                //Toast.makeText(getBaseContext(), "Button get Mpesa balance clicked.", Toast.LENGTH_SHORT ).show();

                Intent intent = new HoverParameters.Builder(MainActivity.this)
                        .setHeader("Getting Mpesa Balance")// main message, to tell the user what is happening
                        .initialProcessingMessage(" ")//left blank to keep UI clean
                        .finalMsgDisplayTime(0) // result message will show for 1 second(s), this will be set to zero to limit the number of visible screens
                        .request("62ab9c09")//ID of action to run (Get Mpesa Balance)
                        .showUserStepDescriptions(true)//show the steps from USSD, default to false once working as intended
                        .buildIntent();//build intent
                startActivityForResult(intent, 0); // go to onActivityResult
                 }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Log.i(TAG, "In onActivityResult.");
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String[] sessionTextArr = data.getStringArrayExtra("session_messages");
            String uuid = data.getStringExtra("uuid");
        }else if(requestCode == 0 && resultCode == Activity.RESULT_CANCELED){
            //an error occurred
            Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
        }
    }
}