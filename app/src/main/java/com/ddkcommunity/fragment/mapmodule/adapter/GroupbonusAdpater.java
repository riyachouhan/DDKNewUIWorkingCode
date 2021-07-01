package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.GroupBonusModel;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GroupbonusAdpater extends RecyclerView.Adapter<GroupbonusAdpater.MyViewHolder>
{
    private List<GroupBonusModel.Datum> data;
    private Activity activity;

    public GroupbonusAdpater(List<GroupBonusModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<GroupBonusModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.groupbonusitem, parent, false);
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
            holder.levelno.setText(data.get(position).getLevel()+"");
            holder.tvpoint.setText("Point : "+data.get(position).getPoints());
            holder.tvRenewal.setText("Renewal : "+data.get(position).getRenewalNo());
            holder.tvLeftRight.setText("Left/Right : "+data.get(position).getBonusDesc());

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

                    holder.tvdatetime.setText(output);

                } catch (ParseException pe) {
                    pe.printStackTrace();
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView levelno,tvpoint,tvdatetime,tvRenewal,tvLeftRight;

        public MyViewHolder(View view) {
            super(view);
            levelno=view.findViewById(R.id.levelno);
            tvpoint=view.findViewById(R.id.tvpoint);
            tvdatetime=view.findViewById(R.id.tvdatetime);
            tvRenewal=view.findViewById(R.id.tvRenewal);
            tvLeftRight=view.findViewById(R.id.tvLeftRight);
        }
    }
}
