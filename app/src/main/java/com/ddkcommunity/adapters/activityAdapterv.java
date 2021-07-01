package com.ddkcommunity.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.model.activityModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class activityAdapterv extends RecyclerView.Adapter<activityAdapterv.MyViewHolder>
{
    private List<activityModel.Datum> data;
    private Activity activity;

    public activityAdapterv(List<activityModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<activityModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activitylayoutv, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try
        {
           holder.mobiletv.setText(data.get(position).getMobileNumber());
           holder.emailid.setText(data.get(position).getEmail());
           holder.tvNametitle.setText(data.get(position).getName());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            String output = null;
            try{
                //Converting the input String to Date
                date= df.parse(data.get(position).getCreatedAt().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                holder.datetv.setText(output);

            }catch(ParseException pe){
                pe.printStackTrace();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView datetv,mobiletv,emailid,tvNametitle;

        public MyViewHolder(View view) {
            super(view);
            datetv=view.findViewById(R.id.datetv);
            mobiletv=view.findViewById(R.id.mobiletv);
            emailid=view.findViewById(R.id.emailid);
            tvNametitle=view.findViewById(R.id.tvNametitle);
        }
    }
}
