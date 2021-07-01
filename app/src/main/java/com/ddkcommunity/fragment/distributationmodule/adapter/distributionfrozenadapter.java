package com.ddkcommunity.fragment.distributationmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.util.List;

public class distributionfrozenadapter extends RecyclerView.Adapter<distributionfrozenadapter.MyViewHolder>
{
    private List<PollingHistoryTransction.PoolingHistoryData> data;
    private Activity activity;

    public distributionfrozenadapter(List<PollingHistoryTransction.PoolingHistoryData> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<PollingHistoryTransction.PoolingHistoryData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.frozanitemdis, parent, false);
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
          //  holder.tvDateTime.setText(data.get(position).transactionDate);

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
       // public TextView tvddkamount,tvConversion,tvFees,tvStatus,tvDateTime, tvOrderNumber, tvAmount, tvOrderType;

        public MyViewHolder(View view) {
            super(view);
          //    tvOrderType = view.findViewById(R.id.tvOrderType);
        }
    }
}
