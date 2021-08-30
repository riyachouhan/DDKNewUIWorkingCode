package com.ddkcommunity.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.model.Country;

import java.util.ArrayList;

public class CountrycodeAdapter extends RecyclerView.Adapter<CountrycodeAdapter.MyViewHolder> {

    public String[] genderlist;
    private Context activity;
    private EditText searchET;
    private SetOnItemClickListener setOnItemClickListener;
    ArrayList<Country> countrygender;

    public CountrycodeAdapter(ArrayList<Country> countrygender, String[] genderlist, Context activity, EditText searchET, SetOnItemClickListener setOnItemClickListener) {
        this.activity = activity;
        this.searchET = searchET;
        this.countrygender=countrygender;
        this.genderlist=genderlist;
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public void updateData( ArrayList<Country> countrygender) {
        this.countrygender= countrygender;
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

        holder.title.setText(countrygender.get(position).getCountry()+"   "+countrygender.get(position).getPhoneCode());
        holder.ddk.setText(countrygender.get(position).getCountry());
        holder.ddk.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return countrygender.size();
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
                public void onClick(View view)
                {
                    setOnItemClickListener.onItemClick(countrygender.get(getAdapterPosition()).getCountry(),countrygender.get(getAdapterPosition()).getId(),countrygender.get(getAdapterPosition()).getPhoneCode());
                }
            });
        }

    }

    public interface SetOnItemClickListener {
        public void onItemClick(String country, String stateid, String phoneid);
    }
}
