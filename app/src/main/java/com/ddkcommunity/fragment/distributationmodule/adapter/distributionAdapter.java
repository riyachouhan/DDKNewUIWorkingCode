package com.ddkcommunity.fragment.distributationmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.util.List;

public class distributionAdapter extends RecyclerView.Adapter<distributionAdapter.MyViewHolder>
{
    private List<PollingHistoryTransction.PoolingHistoryData> data;
    private Activity activity;

    public distributionAdapter(List<PollingHistoryTransction.PoolingHistoryData> data, Activity activity) {
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
                .inflate(R.layout.distributioniteam, parent, false);
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
            holder.titel_main.setText(data.get(position).getType());
            holder.tvmelimun.setText(data.get(position).getPayment_status());
            holder.percentvalue.setText(data.get(position).getSender_email());

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

        TextView titel_main,tvmelimun,percentvalue;

        public MyViewHolder(View view) {
            super(view);
            titel_main=view.findViewById(R.id.titel_main);
            tvmelimun=view.findViewById(R.id.tvmelimun);
            percentvalue=view.findViewById(R.id.percentvalue);
        }
    }
}
