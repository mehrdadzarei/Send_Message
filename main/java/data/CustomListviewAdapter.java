package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mehrdad.sendmessage.R;
import com.mehrdad.sendmessage.RecipientsDetails;

import java.util.ArrayList;

import model.Recipient;

public class CustomListviewAdapter extends ArrayAdapter<Recipient> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Recipient> recipientsList = new ArrayList<>();

    public CustomListviewAdapter(Activity act, int resource, ArrayList<Recipient> data) {
        super(act, resource, data);

        layoutResource = resource;
        activity = act;
        recipientsList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recipientsList.size();
    }

    @Override
    public Recipient getItem(int position) {
        return recipientsList.get(position);
    }

    @Override
    public int getPosition(Recipient item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if(row == null || (row.getTag() == null)){

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.recipientName = (TextView) row.findViewById(R.id.nameListId);
            holder.recipientSurname = (TextView) row.findViewById(R.id.surnameListId);
            holder.recipientCellNo = (TextView) row.findViewById(R.id.cellNoListId);
            holder.recipientEmail =  (TextView) row.findViewById(R.id.emailListId);

            row.setTag(holder);
        }else {

            holder = (ViewHolder) row.getTag();
        }

        holder.recipient = getItem(position);

        holder.recipientName.setText(holder.recipient.getRecipientName());
        holder.recipientSurname.setText(holder.recipient.getRecipientSurname());
        holder.recipientCellNo.setText(holder.recipient.getRecipientCellNo());
        holder.recipientEmail.setText(holder.recipient.getRecipientEmail());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, RecipientsDetails.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("usrObj", finalHolder.recipient);
                i.putExtras(mBundle);

                activity.startActivity(i);
            }
        });

        return row;
    }

    public class ViewHolder {

        Recipient recipient;
        TextView recipientName;
        TextView recipientSurname;
        TextView recipientCellNo;
        TextView recipientEmail;
//        TextView recipientMessage;
    }

}
