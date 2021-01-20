package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import model.Recipient;

public class DatabaseHandler extends SQLiteOpenHelper {

    private ArrayList<Recipient> recipientsList = new ArrayList<>();

    public DatabaseHandler(Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create table
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.RECIPIENT_NAME + " TEXT, " + Constants.RECIPIENT_SURNAME + " TEXT, " + Constants.RECIPIENT_CELL_NO
                + " TEXT, " + Constants.RECIPIENT_EMAIL + " TEXT, " + Constants.RECIPIENT_MESSAGE + " TEXT);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        // create a new one
        onCreate(db);
    }

    // get total items saved
    public int getTotalItems(){

        int totalItems = 0;
        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.rawQuery(query, null);

        totalItems = cursor.getCount();

        cursor.close();
        dba.close();

        return totalItems;
    }

    // delete recipient item
    public void deleteRecipient(int id){

        SQLiteDatabase dba = this.getWritableDatabase();
        dba.delete(Constants.TABLE_NAME, Constants.KEY_ID + " =?",
                new String[]{String.valueOf(id)});

        dba.close();
    }

    // add recipient
    public void addRecipient(Recipient recipient){

        SQLiteDatabase dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.RECIPIENT_NAME, recipient.getRecipientName());
        values.put(Constants.RECIPIENT_SURNAME, recipient.getRecipientSurname());
        values.put(Constants.RECIPIENT_CELL_NO, recipient.getRecipientCellNo());
        values.put(Constants.RECIPIENT_EMAIL, recipient.getRecipientEmail());
        values.put(Constants.RECIPIENT_MESSAGE, recipient.getRecipientMessage());

        dba.insert(Constants.TABLE_NAME, null, values);

        dba.close();
    }

    // get all recipients
    public ArrayList<Recipient> getRecipients(){

        recipientsList.clear();

        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID, Constants.RECIPIENT_NAME,
                Constants.RECIPIENT_SURNAME, Constants.RECIPIENT_CELL_NO, Constants.RECIPIENT_EMAIL, Constants.RECIPIENT_MESSAGE},
                null, null, null, null, Constants.RECIPIENT_MESSAGE);

        // loop through
        if(cursor.moveToFirst()){
            do {

                Recipient recipient = new Recipient();
                recipient.setRecipientName(cursor.getString(cursor.getColumnIndex(Constants.RECIPIENT_NAME)));
                recipient.setRecipientSurname(cursor.getString(cursor.getColumnIndex(Constants.RECIPIENT_SURNAME)));
                recipient.setRecipientCellNo(cursor.getString(cursor.getColumnIndex(Constants.RECIPIENT_CELL_NO)));
                recipient.setRecipientEmail(cursor.getString(cursor.getColumnIndex(Constants.RECIPIENT_EMAIL)));
                recipient.setRecipientMessage(cursor.getString(cursor.getColumnIndex(Constants.RECIPIENT_MESSAGE)));
                recipient.setNameId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                recipientsList.add(recipient);

            }while (cursor.moveToNext());
        }

        cursor.close();
        dba.close();

        return recipientsList;
    }
}
