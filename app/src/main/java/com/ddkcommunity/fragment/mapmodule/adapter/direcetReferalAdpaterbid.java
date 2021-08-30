package com.ddkcommunity.fragment.mapmodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.direcetBidModel;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;

import java.util.ArrayList;

public class direcetReferalAdpaterbid extends RecyclerView.Adapter<direcetReferalAdpaterbid.MyViewHolder> {

    private ArrayList<direcetBidModel.ExtraDatum> data;
    private Context activity;
    private SetOnItemClickListener setOnItemClickListener;

    public direcetReferalAdpaterbid(ArrayList<direcetBidModel.ExtraDatum> data, Context activity, SetOnItemClickListener setOnItemClickListener) {
        this.activity = activity;
        this.data = data;
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public void updateData(ArrayList<direcetBidModel.ExtraDatum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wallet, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String posiId=data.get(position).getPosId()+"";
        String positionVal=data.get(position).getPosition()+"";
        String latamountVal=data.get(position).getPosition()+"";
        if(positionVal!=null && positionVal.equalsIgnoreCase("1")) {
            holder.title.setText("Under " + posiId + " at Left Position");
        }else
        {
            holder.title.setText("Under " + posiId + " at Right Position");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, ddk;
        ImageView online;

        public MyViewHolder(View view) {
            super(view);

            online = view.findViewById(R.id.online);
            title = view.findViewById(R.id.title_TV);
            ddk = view.findViewById(R.id.ddk_code_TV);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOnItemClickListener.onItemClick(data.get(getAdapterPosition()).getPosId(),data.get(getAdapterPosition()).getPosition(),data.get(getAdapterPosition()).getLast_amount());
                }
            });
        }
    }

    public interface SetOnItemClickListener {
        public void onItemClick(String positionid,String position,String amount);
    }
}
