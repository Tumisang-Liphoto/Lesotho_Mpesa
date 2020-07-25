package com.liphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;

public class SendMoney extends AppCompatActivity {

    TextInputLayout textInputRecipient;
    TextInputLayout textInputAmount;
    Button buttonConfirmRecipient;
    private static final String TAG = "SendMoney";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        Log.i(TAG, "in main send money.");
        Toast.makeText(getBaseContext(), "In send money" , Toast.LENGTH_SHORT ).show();
        Hover.initialize(this); //initialise hover
        Hover.setBranding("App Activity logo", R.drawable.applogo,getApplicationContext());

        textInputRecipient = findViewById(R.id.text_input_MpesaRecipient);
        textInputAmount = findViewById(R.id.text_input_MpesaAmount);
        buttonConfirmRecipient = findViewById(R.id.ButtonConfirmRecipient);

        buttonConfirmRecipient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Confirming details.");
                Toast.makeText(getBaseContext(), "Confirming details.", Toast.LENGTH_SHORT ).show();

                if(!ValidateRecipientNumber() | !ValidateAmount() ){
                    Log.i(TAG, "Validation failed, will not proceed.");
                    Toast.makeText(getBaseContext(), "Validation failed, will not proceed." , Toast.LENGTH_SHORT ).show();
                    return;
                }
                else{
                    Log.i(TAG, "Validation successful to send.");
                    Toast.makeText(getBaseContext(), "Validation successful to send." , Toast.LENGTH_SHORT ).show();
                    //run hover sdk to validate the recipient number
                    String subscriber = textInputRecipient.getEditText().getText().toString(); //receivers number
                    String amount = textInputAmount.getEditText().getText().toString();//amount being sent

                    Log.i(TAG, "The subscriber details are: "+ subscriber );
                    Log.i(TAG, "The amount is: "+ amount );

                    //we will run an action called GetMpesaReceiverDetails
                    Log.i(TAG, "getting details.");
                    Intent intent = new HoverParameters.Builder(getApplicationContext())
                            .setHeader("Getting details")// main message, to tell the user what is happening
                            .initialProcessingMessage(" ")//left blank to keep UI clean
                            .finalMsgDisplayTime(0) // result message will show for 1 second(s), this will be set to zero to limit the number of visible screens
                            .extra("subscriber",subscriber)
                            .extra("amount",amount)
                            .request("34506971")//ID of action to run (Get recipient details)
                            .showUserStepDescriptions(true)//show the steps from USSD, default to false once working as intended
                            .buildIntent();//build intent
                    startActivityForResult(intent, 0); // go to onActivityResult

                    /*
                    String output = "number: "+ textInputRecipient.getEditText().getText().toString();
                    output +="\n";
                    output += "amount: "+ textInputAmount.getEditText().getText().toString();
                    Toast.makeText(getBaseContext(), "The result is  "+ output, Toast.LENGTH_SHORT ).show();
                    */
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(getBaseContext(), "starting activity", Toast.LENGTH_SHORT ).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String[] sessionTextArr = data.getStringArrayExtra("session_messages");
            String uuid = data.getStringExtra("uuid");
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Error: " + data.getStringExtra("error"), Toast.LENGTH_LONG).show();
        }
    }

    private  boolean  ValidateAmount() {
        String InputAmount = textInputAmount.getEditText().getText().toString().trim();
        if (InputAmount.isEmpty()) {
            textInputAmount.setError("Field may not be blank");
            return false;
        }
        /*******************************************
         * ensure that you validate currency format
         * *******************************************/

        double DecimalInputAmount = Double.parseDouble(InputAmount);
        if(DecimalInputAmount < 10.00){
            textInputAmount.setError("Mpesa requires a minimum of 10 Maluti");
            return false;
        }
        //ensure the the amount has only two decimal places
        else{
            textInputAmount.setError(null);
            textInputAmount.setEnabled(true);
            return  true;
        }
    }

    private boolean ValidateRecipientNumber(){
        String RecipientNumber = textInputRecipient.getEditText().getText().toString().trim();

        if(RecipientNumber.isEmpty()){
            textInputRecipient.setError("Field may not be blank.");
            return false;
        }
        if(RecipientNumber.length()!= 8){
            textInputRecipient.setError("Please check the recipients number, must be 8 digits.");
            return false;
        }
        else{
            textInputRecipient.setError(null);
            textInputRecipient.setEnabled(true);
            return true;
        }
    }

}


