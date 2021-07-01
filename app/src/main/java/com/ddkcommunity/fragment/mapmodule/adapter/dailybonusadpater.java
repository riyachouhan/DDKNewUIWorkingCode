package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.dailyBonusModel;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class dailybonusadpater extends RecyclerView.Adapter<dailybonusadpater.MyViewHolder>
{
    private List<dailyBonusModel.Datum> data;
    private Activity activity;

    public dailybonusadpater(List<dailyBonusModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<dailyBonusModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dailybonusadapteiteam, parent, false);
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
            holder.tvAmt.setText(data.get(position).getPoints()+" points");

            if(data.get(position).getPackageName().toLowerCase().equalsIgnoreCase("bronze"))
            {
                holder.tvpackagename.setText("Bronze Package");
                holder.ivIcon.setImageResource(R.drawable.bronzecoin);

            }else if(data.get(position).getPackageName().toLowerCase().equalsIgnoreCase("silver"))
            {
                holder.tvpackagename.setText("Silver Package");
                holder.ivIcon.setImageResource(R.drawable.slivercoin);

            }else if(data.get(position).getPackageName().toLowerCase().equalsIgnoreCase("gold"))
            {
                holder.tvpackagename.setText("Gold Package");
                holder.ivIcon.setImageResource(R.drawable.samgoldcoin);

            }else if(data.get(position).getPackageName().toLowerCase().equalsIgnoreCase("diamond"))
            {
                holder.tvpackagename.setText("Diamond Package");
                holder.ivIcon.setImageResource(R.drawable.diamondcoin);

            }else if(data.get(position).getPackageName().toLowerCase().equalsIgnoreCase("platinum"))
            {
                holder.tvpackagename.setText("Platinum Package");
                holder.ivIcon.setImageResource(R.drawable.platinumcoin);

            }else if(data.get(position).getPackageName().toLowerCase().equalsIgnoreCase("titanium"))
            {
                holder.tvpackagename.setText("Titanium Package");
                holder.ivIcon.setImageResource(R.drawable.titaniumcoin);
            }


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            Date date = null;
            String output = null;
            try{
                //Converting the input String to Date
                date= df.parse(data.get(position).getCreatedAt().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                holder.tvpaiddate.setText("paid by SAMKoin on "+output);

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
        public TextView tvpackagename,tvpaiddate,tvAmt;
        public ImageView ivIcon;

        public MyViewHolder(View view) {
            super(view);
            ivIcon= view.findViewById(R.id.ivIcon);
            tvpackagename= view.findViewById(R.id.tvpackagename);
            tvpaiddate= view.findViewById(R.id.tvpaiddate);
            tvAmt= view.findViewById(R.id.tvAmt);
        }
    }
}
