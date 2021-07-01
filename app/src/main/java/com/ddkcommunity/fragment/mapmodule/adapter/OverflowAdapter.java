package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.overFlowModel;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OverflowAdapter extends RecyclerView.Adapter<OverflowAdapter.MyViewHolder>
{
    private List<overFlowModel.Datum> data;
    private Activity activity;

    public OverflowAdapter(List<overFlowModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<overFlowModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overflowadpateriteam, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            String upperString = data.get(position).getBonusType().substring(0, 1).toUpperCase() + data.get(position).getBonusType().substring(1).toLowerCase();
            holder.tvemail.setText("Type : "+upperString);
            holder.tvpoint.setText("Point | "+data.get(position).getPoints());

            if(data.get(position).getBonusType().toString().equalsIgnoreCase("direct") || data.get(position).getBonusType().toString().equalsIgnoreCase("group"))
            {
                if(data.get(position).getRenewal_no()!=null) {
                    holder.tvrenewal.setText("Renewal : " + data.get(position).getRenewal_no());
                }else
                {
                    holder.tvrenewal.setText("Renewal : 0");
                }
                holder.tvrenewal.setVisibility(View.VISIBLE);
            }else
            {
                holder.tvrenewal.setVisibility(View.INVISIBLE);
            }

            /*if(data.get(position).getCreatedAt()!=null)
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //Desired format: 24 hour format: Change the pattern as per the need
                DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                Date date = null;
                String output = null;
                try {
                    //Converting the input String to Date
                    date = df.parse(data.get(position).getCreatedAt().toString());
                    //Changing the format of date and storing it in String
                    output = outputformat.format(date);
                    //Displaying the date

                    holder.tvdatetime.setText(output);

                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }*/

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            Date date = null;
            String output = null;
            try {
                //Converting the input String to Date
                date = df.parse(data.get(position).getOverflowExpirationDate().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                holder.tvStatus.setText("Expire date :"+output);
            } catch (ParseException pe) {
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
        public TextView tvemail,tvdatetime,tvpoint,tvrenewal,tvStatus;

        public MyViewHolder(View view) {
            super(view);
            tvStatus=view.findViewById(R.id.tvStatus);
            tvemail = view.findViewById(R.id.tvemail);
            tvdatetime= view.findViewById(R.id.tvdatetime);
            tvpoint = view.findViewById(R.id.tvpoint);
            tvrenewal= view.findViewById(R.id.tvrenewal);
        }
    }
}
