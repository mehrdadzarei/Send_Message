package com.mehrdad.sendmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.Recipient;

public class PowerConnectionReceiver extends BroadcastReceiver {

    private DatabaseHandler dba;

    @Override
    public void onReceive(Context context, Intent intent) {
        int status =  intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        // back to main activity if it is alive
        Intent intent1 = new Intent("PowerConnectionReceiver");
//        intent1.putExtra("power", isCharging);
        context.sendBroadcast(intent1);

        // sending message
        dba = new DatabaseHandler(context);

        ArrayList<Recipient> recipientsFromDB = dba.getRecipients();

        for(int i = 0; i < recipientsFromDB.size(); i++){

            String name = recipientsFromDB.get(i).getRecipientName();
            String surname = recipientsFromDB.get(i).getRecipientSurname();
            String cellNo = recipientsFromDB.get(i).getRecipientCellNo();
//                String email = recipientsFromDB.get(i).getRecipientEmail();
            String message = recipientsFromDB.get(i).getRecipientMessage();

            String messageToSend = name + " " + surname + "\n" + message;

            SmsManager.getDefault().sendTextMessage(cellNo, null, messageToSend, null,null);
        }

        try {
            GMailSender sender = new GMailSender("zareimehrdad72@gmail.com", "mehr.4000@115");
            sender.sendMail("test from Android",
                    "Hey from Android App","zareimehrdad72@gmail.com",
                    "mehr.zarey@yahoo.com");

            Toast.makeText(context, "Mail Send", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.v("Mail", e.getMessage(), e);
        }

        if(recipientsFromDB.size() == 0){

            Toast.makeText(context, "There are not any body to sending message", Toast.LENGTH_LONG).show();
        }else {

            Toast.makeText(context, "Message sent to all recipients", Toast.LENGTH_LONG).show();
        }
        dba.close();
    }
}
