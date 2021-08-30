package com.ddkcommunity.fragment.mapmodule.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.model.PollingHistoryTransction;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ddkcommunity.utilies.dataPutMethods.ShowTransationHistory;

public class FunnelAdapter extends RecyclerView.Adapter<FunnelAdapter.MyViewHolder>
{
    private ArrayList<finnelModel.Datum> funnelDAta;
    private Activity activity;

    public FunnelAdapter(ArrayList<finnelModel.Datum> funnelDAta, Activity activity) {
        this.activity = activity;
        this.funnelDAta = funnelDAta;
    }

    public void updateData(ArrayList<finnelModel.Datum> funnelDAta) {
        this.funnelDAta = funnelDAta;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.funnel_adapter_layout, parent, false);
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
            holder.tvPrice.setText(funnelDAta.get(position).getPackAmt()+"");

            if(funnelDAta.get(position).getName().toLowerCase().equalsIgnoreCase("bronze"))
            {
               holder.tvTitlepackage.setText("Bronze Package");
               holder.ivIcon.setImageResource(R.drawable.bronzecoin);

            }else if(funnelDAta.get(position).getName().toLowerCase().equalsIgnoreCase("silver"))
            {
                holder.tvTitlepackage.setText("Silver Package");
                holder.ivIcon.setImageResource(R.drawable.slivercoin);

            }else if(funnelDAta.get(position).getName().toLowerCase().equalsIgnoreCase("gold"))
            {
                holder.tvTitlepackage.setText("Gold Package");
                holder.ivIcon.setImageResource(R.drawable.samgoldcoin);

            }else if(funnelDAta.get(position).getName().toLowerCase().equalsIgnoreCase("diamond"))
            {
                holder.tvTitlepackage.setText("Diamond Package");
                holder.ivIcon.setImageResource(R.drawable.diamondcoin);

            }else if(funnelDAta.get(position).getName().toLowerCase().equalsIgnoreCase("platinum"))
            {
                holder.tvTitlepackage.setText("Platinum Package");
                holder.ivIcon.setImageResource(R.drawable.platinumcoin);

            }else if(funnelDAta.get(position).getName().toLowerCase().equalsIgnoreCase("titanium"))
            {
                holder.tvTitlepackage.setText("Titanium Package");
                holder.ivIcon.setImageResource(R.drawable.titaniumcoin);
            }

            //..............
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
            Date date = null;
            String output = null;
            try{
                //Converting the input String to Date
                date= df.parse(funnelDAta.get(position).getCreatedAt().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date

                if(funnelDAta.get(position).getPaymentMode()!=null)
                {
                    String pay=funnelDAta.get(position).getPaymentMode().toString();
                    if (funnelDAta.get(position).getPaymentMode().toString().equalsIgnoreCase("sam_koin")) {
                        holder.tvpaidby.setText("paid by SAMKoin on " + output);
                    } else
                    if (funnelDAta.get(position).getPaymentMode().toString().equalsIgnoreCase("credit card")) {
                        holder.tvpaidby.setText("paid by CREDIT CARD on " + output);
                    }else {
                        holder.tvpaidby.setText("paid by SAMKoin on " + output);
                    }
                }else
                {
                    holder.tvpaidby.setText("paid by SAMKoin on " + output);
                }

            }catch(ParseException pe){
                pe.printStackTrace();
            }


            if(funnelDAta.get(position).getPackageStatus()!=null)
            {
                String upperString = funnelDAta.get(position).getPackageStatus().substring(0, 1).toUpperCase() + funnelDAta.get(position).getPackageStatus().substring(1).toLowerCase();
                if (funnelDAta.get(position).getPackageStatus().equalsIgnoreCase("pool")) {
                    holder.tvStatus.setText(upperString);
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.orange));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_orange_back));

                } else if (funnelDAta.get(position).getPackageStatus().equalsIgnoreCase("active")) {

                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_green_back));
                    holder.tvStatus.setText(upperString);

                } else if (funnelDAta.get(position).getPackageStatus().equalsIgnoreCase("failed")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_red_back));
                    holder.tvStatus.setText(upperString);

                } else if (funnelDAta.get(position).getPackageStatus().equalsIgnoreCase("pending")) {
                    holder.tvStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorpedn));
                    holder.tvStatus.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_peding_back));
                    holder.tvStatus.setText(upperString);

                } else if (funnelDAta.get(position).getPackageStatus().equalsIgnoreCase("fulfilled"))  {
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
        return funnelDAta.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitlepackage,tvpaidby,tvStatus,tvPrice;
        public ImageView ivIcon;

        public MyViewHolder(View view) {
            super(view);
            ivIcon= view.findViewById(R.id.ivIcon);
            tvTitlepackage= view.findViewById(R.id.tvTitlepackage);
            tvpaidby= view.findViewById(R.id.tvpaidby);
            tvStatus= view.findViewById(R.id.tvStatus);
            tvPrice= view.findViewById(R.id.tvPrice);
        }
    }
}
