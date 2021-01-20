package com.mehrdad.sendmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.Recipient;
import util.Utils;

public class MainActivity extends AppCompatActivity {

    private EditText recipientName, recipientSurname, recipientCellNo, recipientEmail, recipientMessage;
    private Button add, recipients;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(broadcastReceiver, new IntentFilter("PowerConnectionReceiver"));
        dba = new DatabaseHandler(MainActivity.this);

        recipientName = (EditText)findViewById(R.id.editNameId);
        recipientSurname = (EditText)findViewById(R.id.editSurnameId);
        recipientCellNo = (EditText)findViewById(R.id.editCellNoId);
        recipientEmail = (EditText)findViewById(R.id.editEmailId);
        recipientMessage = (EditText)findViewById(R.id.messageId);
        add = (Button)findViewById(R.id.addId);
        recipients = (Button)findViewById(R.id.totalId);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveDataToDB();
            }
        });

        recipients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Recipients.class));
            }
        });
    }

    private void saveDataToDB() {

        Recipient recipient = new Recipient();
        String name = recipientName.getText().toString().trim();
        String surname = recipientSurname.getText().toString().trim();
        String cellNoString = recipientCellNo.getText().toString().trim();
        String email = recipientEmail.getText().toString().trim();
        String message = recipientMessage.getText().toString().trim();

        if(cellNoString.equals("")){

            Toast.makeText(getApplicationContext(), "Please Enter the Cell No.", Toast.LENGTH_LONG).show();
        }else {

            recipient.setRecipientName(name);
            recipient.setRecipientSurname(surname);
            recipient.setRecipientCellNo(cellNoString);
            recipient.setRecipientEmail(email);
            recipient.setRecipientMessage(message);

            dba.addRecipient(recipient);
            dba.close();

            Toast.makeText(getApplicationContext(), "New Recipient Added", Toast.LENGTH_LONG).show();
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            ArrayList<Recipient> recipientsFromDB = dba.getRecipients();
//
//            for(int i = 0; i < recipientsFromDB.size(); i++){
//
//                String name = recipientsFromDB.get(i).getRecipientName();
//                String surname = recipientsFromDB.get(i).getRecipientSurname();
//                String cellNo = recipientsFromDB.get(i).getRecipientCellNo();
////                String email = recipientsFromDB.get(i).getRecipientEmail();
//                String message = recipientsFromDB.get(i).getRecipientMessage();
//
//                String messageToSend = name + " " + surname + "\n" + message;
//
//                SmsManager.getDefault().sendTextMessage(cellNo, null, messageToSend, null,null);
//            }
//
//            if(recipientsFromDB.size() == 0){
//
//                Toast.makeText(getApplicationContext(), "There are not any body to sending message", Toast.LENGTH_LONG).show();
//            }else {
//
//                Toast.makeText(getApplicationContext(), "Message sent to all recipients", Toast.LENGTH_LONG).show();
//            }

//            Bundle extras = intent.getExtras();
//
//            if (extras != null){

//                int data = extras.getInt("power");
//                Toast.makeText(getApplicationContext(), String.valueOf(data), Toast.LENGTH_LONG).show();

//                try {
//                    GMailSender sender = new GMailSender("zareimehrdad72@gmail.com", "mehr.4000@115");
//                    sender.sendMail("test from Android",
//                            "Hey from Android App","zareimehrdad72@gmail.com",
//                            "mehr.zarey@yahoo.com");
//
//                    Toast.makeText(getApplicationContext(), "Mail Send", Toast.LENGTH_SHORT).show();
//
//                } catch (Exception e) {
//                    Log.v("Mail", e.getMessage(), e);
//                }

//                String messageToSend = "this is a message from bot";
//                String number = "536482394";
//
//                SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
//            }
        }
    };
}