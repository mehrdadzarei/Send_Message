package com.mehrdad.sendmessage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import data.CustomListviewAdapter;
import data.DatabaseHandler;
import model.Recipient;
import util.Utils;

public class Recipients extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<Recipient> dbRecipients = new ArrayList<>();
    private CustomListviewAdapter recipientAdapter;
    private ListView listView;
    private Recipient myRecipient;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients);

        listView = (ListView)findViewById(R.id.list);
        total = (TextView)findViewById(R.id.totalId);

        refreshData();
    }

    private void refreshData() {

        dbRecipients.clear();

        dba = new DatabaseHandler(getApplicationContext());
        ArrayList<Recipient> recipientsFromDB = dba.getRecipients();

        int totalItems = dba.getTotalItems();
        String formattedValue = Utils.formatNumber(totalItems);

        total.setText("Total: " + formattedValue);

        for(int i = 0; i < recipientsFromDB.size(); i++){

            String name = recipientsFromDB.get(i).getRecipientName();
            String surname = recipientsFromDB.get(i).getRecipientSurname();
            String cellNo = recipientsFromDB.get(i).getRecipientCellNo();
            String email = recipientsFromDB.get(i).getRecipientEmail();
            String message = recipientsFromDB.get(i).getRecipientMessage();
            int recipientId = recipientsFromDB.get(i).getNameId();

            myRecipient = new Recipient();
            myRecipient.setRecipientName(name);
            myRecipient.setRecipientSurname(surname);
            myRecipient.setRecipientCellNo(cellNo);
            myRecipient.setRecipientEmail(email);
            myRecipient.setRecipientMessage(message);
            myRecipient.setNameId(recipientId);

            dbRecipients.add(myRecipient);
        }

        dba.close();

        // setup Adapter
        recipientAdapter = new CustomListviewAdapter(Recipients.this, R.layout.list_row, dbRecipients);
        listView.setAdapter(recipientAdapter);
        recipientAdapter.notifyDataSetChanged();
    }
}