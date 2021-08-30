package com.ddkcommunity.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ARPHistoryAdapter extends RecyclerView.Adapter<ARPHistoryAdapter.MyViewHolder>
{
    private ArrayList<ARPHistoryModel.Datum> ARPList;
    private Activity activity;

    public ARPHistoryAdapter(ArrayList<ARPHistoryModel.Datum> ARPList, Activity activity) {
        this.activity = activity;
        this.ARPList = ARPList;
    }

    public void updateData(ArrayList<ARPHistoryModel.Datum> ARPList) {
        this.ARPList = ARPList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arp__history_iteam_layout, parent, false);
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
            holder.name.setText(ARPList.get(position).getName());
            holder.email.setText(ARPList.get(position).getEmail());
            holder.tvPrice.setText(ARPList.get(position).getPoints());
            String upperStringremar = ARPList.get(position).getType().substring(0, 1).toUpperCase() + ARPList.get(position).getType().substring(1).toLowerCase();
            holder.tvremark.setText(upperStringremar);
            String upperString = ARPList.get(position).getStatus().substring(0, 1).toUpperCase() + ARPList.get(position).getStatus().substring(1).toLowerCase();
            if (upperString.equalsIgnoreCase("Processing")) {

                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                holder.tvStatus.setText(upperString);
            } else if (upperString.equalsIgnoreCase("failed")) {
                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_red_back));
                holder.tvStatus.setText(upperString);
            } else if (upperString.equalsIgnoreCase("pending")) {
                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.neworangesfio));
                holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_orange_back));
                holder.tvStatus.setText(upperString);
            } else {
                holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                holder.tvStatus.setText(upperString);
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return ARPList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView  tvStatus,name,email,tvPrice,tvremark;

        public MyViewHolder(View view) {
            super(view);
            tvStatus=view.findViewById(R.id.tvStatus);
            name= view.findViewById(R.id.name);
            tvremark= view.findViewById(R.id.tvremark);
            email= view.findViewById(R.id.email);
            tvPrice= view.findViewById(R.id.tvPrice);
        }
    }
}
