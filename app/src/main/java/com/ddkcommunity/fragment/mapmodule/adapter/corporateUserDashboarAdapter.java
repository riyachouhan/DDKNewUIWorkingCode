package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.model.activityModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class corporateUserDashboarAdapter extends RecyclerView.Adapter<corporateUserDashboarAdapter.MyViewHolder>
{
    private List<activityModel.Datum> data;
    private Activity activity;

    public corporateUserDashboarAdapter(List<activityModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<activityModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.corporateuserdashap, parent, false);
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
           holder.emailid.setText(data.get(position).getEmail());
           holder.tvNametitle.setText(data.get(position).getName());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            String output = null;
            try{
                //Converting the input String to Date
                date= df.parse(data.get(position).getCreatedAt().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                holder.datetv.setText(output);

            }catch(ParseException pe){
                pe.printStackTrace();
            }

            //............
            String fstatusface="active";
            if(position==2)
            {
                fstatusface="Inactive";
            }else if(position==1)
            {
                fstatusface="Expiring";
            }

            if(fstatusface!=null)
            {
                String upperString = fstatusface.substring(0, 1).toUpperCase() + fstatusface.substring(1).toLowerCase();
                if (fstatusface.equalsIgnoreCase("expiring")) {
                    holder.tvStatus.setText(upperString);
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_orange_back));

                } else if (fstatusface.equalsIgnoreCase("active")) {

                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                    holder.tvStatus.setText(upperString);

                } else if (fstatusface.equalsIgnoreCase("inactive")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_red_back));
                    holder.tvStatus.setText(upperString);

                } else if (fstatusface.equalsIgnoreCase("pending")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorpedn));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_peding_back));
                    holder.tvStatus.setText(upperString);

                } else if (fstatusface.equalsIgnoreCase("fulfilled"))  {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.darkblue));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_card_back));
                    holder.tvStatus.setText(upperString);

                }else
                {
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
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView datetv,emailid,tvNametitle,tvStatus;

        public MyViewHolder(View view) {
            super(view);
            datetv=view.findViewById(R.id.datetv);
            tvStatus=view.findViewById(R.id.tvStatus);
            emailid=view.findViewById(R.id.emailid);
            tvNametitle=view.findViewById(R.id.tvNametitle);
        }
    }
}
