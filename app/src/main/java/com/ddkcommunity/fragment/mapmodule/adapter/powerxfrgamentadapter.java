package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.powerXSubFragment;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class powerxfrgamentadapter extends RecyclerView.Adapter<powerxfrgamentadapter.MyViewHolder>
{
    private List<powerXSubFragment.Datum> data;
    private Activity activity;

    public  powerxfrgamentadapter(List<powerXSubFragment.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<powerXSubFragment.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.powerxfragmentsublayout, parent, false);
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
           holder.tvemailsub.setText(data.get(position).getEmail());
            //holder.tvpointsub.setText("Point : apime ");
            holder.tvpointsub.setVisibility(View.GONE);
            holder.tvrenewalsub.setText("Renewal: x"+data.get(position).getUpRenewalNo());
            String amount=data.get(position).getPackAmt();
            holder.tvPackk_amt.setText(data.get(position).getPackAmt()+"");

            if(data.get(position).getCreatedAt()!=null)
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

                    holder.tvdatetimesub.setText(output);

                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }

            if(data.get(position).getPackageStatus()!=null)
            {
                String upperString = data.get(position).getPackageStatus().substring(0, 1).toUpperCase() + data.get(position).getPackageStatus().substring(1).toLowerCase();
                if (data.get(position).getPackageStatus().equalsIgnoreCase("pool")) {
                    holder.tvStatussub.setText(upperString);
                    holder.tvStatussub.setTextColor(ContextCompat.getColor(activity, R.color.orange));
                    holder.tvStatussub.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_orange_back));

                } else if (data.get(position).getPackageStatus().equalsIgnoreCase("active")) {

                    holder.tvStatussub.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.tvStatussub.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                    holder.tvStatussub.setText(upperString);

                } else if (data.get(position).getPackageStatus().equalsIgnoreCase("failed")) {
                    holder.tvStatussub.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                    holder.tvStatussub.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_red_back));
                    holder.tvStatussub.setText(upperString);

                } else if (data.get(position).getPackageStatus().equalsIgnoreCase("pending")) {
                    holder.tvStatussub.setTextColor(ContextCompat.getColor(activity, R.color.colorpedn));
                    holder.tvStatussub.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_peding_back));
                    holder.tvStatussub.setText(upperString);

                } else {
                    holder.tvStatussub.setTextColor(ContextCompat.getColor(activity, R.color.darkblue));
                    holder.tvStatussub.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_card_back));
                    holder.tvStatussub.setText(upperString);

                }
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

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvemailsub,tvdatetimesub,tvpointsub,tvrenewalsub,tvStatussub,tvPackk_amt;

        public MyViewHolder(View view)
        {
            super(view);
            tvemailsub= view.findViewById(R.id.tvemailsub);
            tvdatetimesub= view.findViewById(R.id.tvdatetimesub);
            tvpointsub= view.findViewById(R.id.tvpointsub);
            tvrenewalsub= view.findViewById(R.id.tvrenewalsub);
            tvStatussub= view.findViewById(R.id.tvStatussub);
            tvPackk_amt= view.findViewById(R.id.tvPackk_amt);
        }
    }
}
