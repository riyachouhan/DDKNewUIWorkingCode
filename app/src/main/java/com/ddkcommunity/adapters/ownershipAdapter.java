package com.ddkcommunity.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.settingModule.ownershipListShowModel;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.util.List;

public class ownershipAdapter extends RecyclerView.Adapter<ownershipAdapter.MyViewHolder>
{
    private List<ownershipListShowModel.Ownership> data;
    private Activity activity;

    public ownershipAdapter(List<ownershipListShowModel.Ownership> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<ownershipListShowModel.Ownership> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ownershiplayoutiteam, parent, false);
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
            holder.titel_main.setText("#"+data.get(position).getTrxNumber());
            holder.tvmelimun.setText(data.get(position).getAmount()+"");
            holder.percentvalue.setText(data.get(position).getName());
            holder.tvStatus.setText(data.get(position).getOwnershipType());

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

        TextView titel_main,tvmelimun,percentvalue,tvStatus;

        public MyViewHolder(View view) {
            super(view);
            tvStatus=view.findViewById(R.id.tvStatus);
            titel_main=view.findViewById(R.id.titel_main);
            tvmelimun=view.findViewById(R.id.tvmelimun);
            percentvalue=view.findViewById(R.id.percentvalue);
        }
    }
}
