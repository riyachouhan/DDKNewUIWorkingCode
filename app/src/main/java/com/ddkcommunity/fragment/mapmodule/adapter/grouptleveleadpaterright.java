package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.groupModel;
import com.ddkcommunity.model.smpdModelNew;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class grouptleveleadpaterright extends RecyclerView.Adapter<grouptleveleadpaterright.MyViewHolder> {

    private List<groupModel.RightGroup> createCancellationRequestlist;
    private Activity activity;

    public grouptleveleadpaterright(List<groupModel.RightGroup> createCancellationRequestlist, Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
    }

    public void updateData(List<groupModel.RightGroup> createCancellationRequestlist) {
        this.createCancellationRequestlist= createCancellationRequestlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grouplevelitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final grouptleveleadpaterright.MyViewHolder holder, final int position) {

        try {
            holder.emailview.setText(createCancellationRequestlist.get(position).getEmail());
            holder.renewaltv.setText("Renewal : "+createCancellationRequestlist.get(position).getRenewalNo());
            holder.tvpoint.setText(createCancellationRequestlist.get(position).getPackAmt()+"");

            //..............
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            Date date = null;
            String output = null;
            try{
                //Converting the input String to Date
                date= df.parse(createCancellationRequestlist.get(position).getCreatedAt().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date

                holder.datetimetv.setText(""+output);

            }catch(ParseException pe){
                pe.printStackTrace();
            }


            if(createCancellationRequestlist.get(position).getPackageStatus()!=null)
            {
                String upperString =createCancellationRequestlist.get(position).getPackageStatus().substring(0, 1).toUpperCase() + createCancellationRequestlist.get(position).getPackageStatus().substring(1).toLowerCase();
                if (createCancellationRequestlist.get(position).getPackageStatus().equalsIgnoreCase("pool")) {
                    holder.tvStatus.setText(upperString);
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_orange_back));

                } else if (createCancellationRequestlist.get(position).getPackageStatus().equalsIgnoreCase("active")) {

                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                    holder.tvStatus.setText(upperString);

                } else if (createCancellationRequestlist.get(position).getPackageStatus().equalsIgnoreCase("failed")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_red_back));
                    holder.tvStatus.setText(upperString);

                } else if (createCancellationRequestlist.get(position).getPackageStatus().equalsIgnoreCase("pending")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorpedn));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_peding_back));
                    holder.tvStatus.setText(upperString);

                } else {
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
        return createCancellationRequestlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvStatus,emailview,datetimetv,renewaltv,tvpoint;

        public MyViewHolder(View view) {
            super(view);
            tvStatus=view.findViewById(R.id.tvStatus);
            emailview=view.findViewById(R.id.emailview);
            datetimetv=view.findViewById(R.id.datetimetv);
            renewaltv=view.findViewById(R.id.renewaltv);
            tvpoint=view.findViewById(R.id.tvpoint);
        }
    }

}
