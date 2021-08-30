package com.ddkcommunity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;

import java.util.ArrayList;

public class directreferralbidAdpater extends RecyclerView.Adapter<directreferralbidAdpater.MyViewHolder> {

    private ArrayList<ARPHistoryModel.Datum> data;
    private Context activity;
    private SetOnItemClickListener setOnItemClickListener;

    public directreferralbidAdpater(ArrayList<ARPHistoryModel.Datum> data, Context activity, SetOnItemClickListener setOnItemClickListener) {
        this.activity = activity;
        this.data = data;
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public void updateData(ArrayList<ARPHistoryModel.Datum> data) {
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

        String sss=data.get(position).getName();
        holder.title.setText(data.get(position).getName().toString());
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
                    setOnItemClickListener.onItemClick(data.get(getAdapterPosition()).getName());
                }
            });
        }
    }

    public interface SetOnItemClickListener {
        public void onItemClick(String wallet_code);
    }
}
