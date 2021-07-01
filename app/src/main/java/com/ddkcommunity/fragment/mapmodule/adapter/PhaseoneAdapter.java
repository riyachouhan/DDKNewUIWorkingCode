package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.phaseOneBonusModel;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhaseoneAdapter extends RecyclerView.Adapter<PhaseoneAdapter.MyViewHolder>
{
    private List<phaseOneBonusModel.Datum> data;
    private Activity activity;

    public PhaseoneAdapter(List<phaseOneBonusModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<phaseOneBonusModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phaseone_adapter_layout, parent, false);
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
            holder.tvTitle.setText(data.get(position).getEmail());
            holder.pointvalue.setText("Point : "+data.get(position).getPoints());
            holder.renewaltv.setText("Renewal : "+data.get(position).getUpRenewalNo());
            holder.tvAMT.setText(data.get(position).getPackAmt()+"");

            //..............
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

                    holder.datetime.setText(output);

                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }

            if(data.get(position).getPackageStatus()!=null)
            {
                String upperString = data.get(position).getPackageStatus().substring(0, 1).toUpperCase() + data.get(position).getPackageStatus().substring(1).toLowerCase();
                if (data.get(position).getPackageStatus().equalsIgnoreCase("pool")) {
                    holder.tvStatus.setText(upperString);
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_orange_back));

                } else if (data.get(position).getPackageStatus().equalsIgnoreCase("active")) {

                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                    holder.tvStatus.setText(upperString);

                } else if (data.get(position).getPackageStatus().equalsIgnoreCase("failed")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_red_back));
                    holder.tvStatus.setText(upperString);

                }else if (data.get(position).getPackageStatus().equalsIgnoreCase("pending")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorpedn));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_peding_back));
                    holder.tvStatus.setText(upperString);

                }  else {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.darkblue));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_card_back));
                    holder.tvStatus.setText(upperString);

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
        public TextView  tvTitle,datetime,pointvalue,renewaltv,tvStatus,tvAMT;

        public MyViewHolder(View view) {
            super(view);
            tvTitle= view.findViewById(R.id.tvTitle);
            datetime= view.findViewById(R.id.datetime);
            pointvalue= view.findViewById(R.id.pointvalue);
            renewaltv= view.findViewById(R.id.renewaltv);
            tvStatus= view.findViewById(R.id.tvStatus);
            tvAMT= view.findViewById(R.id.tvAMT);
        }
    }
}
