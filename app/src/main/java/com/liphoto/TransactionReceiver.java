package com.liphoto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class TransactionReceiver extends BroadcastReceiver {
    private static final String TAG = "TransactionReceiver";

    public TransactionReceiver() {
        //Log.i(TAG, "In TransactionReceiver.");
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "In onReceive.");
        String uuid = intent.getStringExtra("uuid");
        /*

        if (intent.hasExtra("parsed_variables")) {
            //something has been returned from hover
            HashMap<String, String> parsed_variables = (HashMap<String, String>) intent.getSerializableExtra("parsed_variables");

            //MpesaBalance is one of the variables returned
            if (parsed_variables.containsKey("MpesaBalance")){
                //this variable will be used to store the returned Mpesa SMS
                //set ReturnedMpesaBalance to value in SMS
                String ReturnedMpesaBalance = parsed_variables.get("MpesaBalance");
                //Log.d(TAG, "The returned MpesaBalance is :" + ReturnedMpesaBalance);

                intent = new Intent(context, AlertDialogActivity .class); //intent to move to <Tester> activity
                //intent = new Intent(context, MpesaBalanceDialog.class); //intent to move to <MpesaBalanceDialog> activity
                intent.putExtra("ReturnedMpesaBalance", ReturnedMpesaBalance); //variable to pass to next activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent); //run above intent
            }
        }
        else{
            Toast.makeText(context, "Error state.", Toast.LENGTH_SHORT ).show();
        }

         */
        if (intent.hasExtra("parsed_variables")) {
            //something has been returned from hover
            HashMap<String, String> parsed_variables = (HashMap<String, String>) intent.getSerializableExtra("parsed_variables");

            //MpesaRecipientPhone is one of the variables returned
            if (parsed_variables.containsKey("MpesaRecipientPhone")){
                //this variable will be used to store the recipients number
                //set MpesaRecipientPhone to value in USSD
                String MpesaRecipientPhone = parsed_variables.get("MpesaRecipientPhone");
                Log.i(TAG, "The subscriber number is :" + MpesaRecipientPhone);

                /****************************
                 *  this currently will run
                 *  only for registered users
                 *****************************/

                //this variable will be used to store the recipients name
                //set MpesaRecipientName to value in USSD
                String MpesaRecipientName = parsed_variables.get("recipientName");
                Log.i(TAG, "The subscriber name is :" + MpesaRecipientName);

                //this variable will be used to store the recipients surname
                //set MpesaRecipientSurname to value in USSD
                String MpesaRecipientSurname = parsed_variables.get("recipientSurname");
                Log.i(TAG, "The subscriber surname is :" + MpesaRecipientSurname);

                //this variable will be used to store the amount to send
                //set MpesaSendingAmount to value in USSD
                String MpesaSendingAmount = parsed_variables.get("amount");
                Log.i(TAG, "The amount is :" + MpesaSendingAmount);


                    String output = "Number: "+ MpesaRecipientPhone;
                    output +="\n";
                    output += "Name: "+ MpesaRecipientName;
                    output +="\n";
                    output += "surname: "+ MpesaRecipientSurname;
                    output +="\n";
                    output += "amount: "+ MpesaSendingAmount;
                    output +="\n";

                    Toast.makeText(context, "The result is  "+ output, Toast.LENGTH_LONG ).show();
            }
        }
        else{
            Toast.makeText(context, "Error state.", Toast.LENGTH_SHORT ).show();
        }
    }
}
