package com.mehrdad.sendmessage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Recipient;

public class RecipientsDetails extends AppCompatActivity {

    private TextView recName, recSurname, recCellNo, recEmail, recMessage;
    private Button delRec;
    private int recId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipients_details);

        recName = (TextView)findViewById(R.id.detNameListId);
        recSurname = (TextView)findViewById(R.id.detSurnameListId);
        recCellNo = (TextView)findViewById(R.id.detCellNoListId);
        recEmail = (TextView)findViewById(R.id.detEmailListId);
        recMessage = (TextView)findViewById(R.id.detMessageId);
//        editRec = (Button)findViewById(R.id.detEditId);
        delRec = (Button)findViewById(R.id.detDelId);

        Recipient recipient = (Recipient) getIntent().getSerializableExtra("usrObj");

        recName.setText(recipient.getRecipientName());
        recSurname.setText(recipient.getRecipientSurname());
        recCellNo.setText(recipient.getRecipientCellNo());
        recEmail.setText(recipient.getRecipientEmail());
        recMessage.setText(recipient.getRecipientMessage());

        recId = recipient.getNameId();

        delRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecipientsDetails.this);
                alert.setTitle("Delete!");
                alert.setMessage("Are you sure you want to delete this Recipient?");
                alert.setNegativeButton("No", null);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                        dba.deleteRecipient(recId);

                        Toast.makeText(getApplicationContext(), "Recipient Deleted!", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(RecipientsDetails.this, Recipients.class));
                        RecipientsDetails.this.finish();
                    }
                });
                alert.show();
            }
        });
    }
}