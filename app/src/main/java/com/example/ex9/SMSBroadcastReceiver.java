package com.example.ex9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SMSBroadcastReceiver extends BroadcastReceiver {

    Toast toast;
    int duration = Toast.LENGTH_SHORT;

    private static String SMS="android.provider.Telephony.SMS_RECEIVED";

    /**onReceive - in this method the event will be handled*/
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(SMS)) {
            /**Get the SMS data bound to intent*/
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String msgFrom = null;
            String msgBody = null;
            if (bundle != null)
            {
                /** Retrieve the SMS Messages received*/
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i=0;i<msgs.length;i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                    msgFrom = msgs[i].getOriginatingAddress();
                    msgBody = msgs[i].getMessageBody();
                }

                toast = Toast.makeText(context.getApplicationContext(),
                        "Sender: "+msgFrom +"\n"+
                                "Context: "+msgBody, Toast.LENGTH_LONG);

                toast.show();
                /******************************************/

            }
        }
    }
}
