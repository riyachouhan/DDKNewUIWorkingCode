package com.ddkcommunity.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.settingModule.ARPDirectModel;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ARPAdapter extends RecyclerView.Adapter<ARPAdapter.MyViewHolder>
{
    private ArrayList<ARPDirectModel.Datum> ARPList;
    private Activity activity;

    public ARPAdapter(ArrayList<ARPDirectModel.Datum> ARPList, Activity activity) {
        this.activity = activity;
        this.ARPList = ARPList;
    }

    public void updateData(ArrayList<ARPDirectModel.Datum> ARPList) {
        this.ARPList = ARPList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arp_iteam_layout, parent, false);
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
          holder.nametv.setText(ARPList.get(position).getName());
          holder.emailtv.setText(ARPList.get(position).getEmail());
          holder.subscriptionamt.setText("$ "+ARPList.get(position).getAmount());
          holder.tvPrice.setText(ARPList.get(position).getPoints());
          if(ARPList.get(position).getIs_transferred().equalsIgnoreCase("0"))
          {
              holder.redeemlayout.setVisibility(View.VISIBLE);
          }else
          {
              holder.redeemlayout.setVisibility(View.GONE);
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
        public TextView nametv,emailtv,subscriptionamt,tvPrice;
        LinearLayout redeemlayout;

        public MyViewHolder(View view) {
            super(view);
            nametv= view.findViewById(R.id.nametv);
            redeemlayout= view.findViewById(R.id.redeemlayout);
            emailtv= view.findViewById(R.id.emailtv);
            subscriptionamt= view.findViewById(R.id.subscriptionamt);
            tvPrice= view.findViewById(R.id.tvPrice);
        }
    }
}
