package com.ddkcommunity.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.powerXSubFragment;
import com.ddkcommunity.model.sfioSubPackageModel;
import com.ddkcommunity.utilies.AppConfig;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

public class sfiosubfrgamentadapter extends RecyclerView.Adapter<sfiosubfrgamentadapter.MyViewHolder>
{
    private List<sfioSubPackageModel.Datum> data;
    private Context activity;

    public sfiosubfrgamentadapter(List<sfioSubPackageModel.Datum> data,Context activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<sfioSubPackageModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sfiosubchildlayout, parent, false);
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

            holder.tvtrxnumber.setText("#"+data.get(position).getTrxNo()+"");
            holder.tvamountsb.setText("Pts. "+data.get(position).getAmount()+"");

            if(data.get(position).getRedeem_btn_status().equalsIgnoreCase("0"))
            {
                holder.redeemview.setVisibility(View.GONE);
                holder.submit.setVisibility(View.GONE);
            }else
            {
                holder.redeemview.setVisibility(View.VISIBLE);
                holder.submit.setVisibility(View.VISIBLE);
            }

            if(data.get(position).getCreatedAt()!=null)
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                //Desired format: 24 hour format: Change the pattern as per the need
                DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                String output = null;
                try {
                    //Converting the input String to Date
                    date = df.parse(data.get(position).getDate().toString());
                    //Changing the format of date and storing it in String
                    output = outputformat.format(date);
                    //Displaying the date

                    holder.tvDate.setText(output);

                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            }

            if(data.get(position).getStatus()!=null)
            {
                String upperString = data.get(position).getStatus().substring(0, 1).toUpperCase() + data.get(position).getStatus().substring(1).toLowerCase();
                if (data.get(position).getStatus().equalsIgnoreCase("pending")) {
                    holder.tvstatus.setText(upperString);
                    holder.tvstatus.setTextColor(ContextCompat.getColor(activity, R.color.neworangesfio));

                } else if (data.get(position).getStatus().equalsIgnoreCase("send") || data.get(position).getStatus().equalsIgnoreCase("completed")) {

                    holder.tvstatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.tvstatus.setText(upperString);

                } else if (data.get(position).getStatus().equalsIgnoreCase("failed")) {
                    holder.tvstatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed));
                    holder.tvstatus.setText(upperString);

                }  else {
                    holder.tvstatus.setTextColor(ContextCompat.getColor(activity, R.color.darkblue));
                    holder.tvstatus.setText(upperString);
                }
            }

            holder.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id=data.get(position).getId()+"";
                    getRedeemData(activity,holder.submit,id,holder.tvstatus);
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getRedeemData(final Context activity, final AppCompatButton rvview, final String idsfio, final TextView tvstats)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("monthly_txn_id",idsfio);
        Log.d("php user ", hm.toString());
        AppConfig.getLoadInterface().getRedeemStatus(AppConfig.getStringPreferences(activity, Constant.JWTToken),hm).enqueue(new Callback<sfioSubPackageModel>() {
            @Override
            public void onResponse(Call<sfioSubPackageModel> call, Response<sfioSubPackageModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            //...........
                            rvview.setVisibility(View.GONE);
                            tvstats.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                            tvstats.setText("completed");

                        }else
                        {
                            pd.dismiss();
                            rvview.setVisibility(View.VISIBLE);
                            Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(activity,"server error add activity");
                    }

                } catch (Exception e) {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<sfioSubPackageModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvtrxnumber,tvamountsb,tvDate,tvstatus;
        AppCompatButton submit;
        LinearLayout redeemview;
        AppCompatButton cancel;

        public MyViewHolder(View view)
        {
            super(view);
            cancel=view.findViewById(R.id.cancel);
            redeemview=view.findViewById(R.id.redeemview);
            submit=view.findViewById(R.id.submit);
            tvtrxnumber= view.findViewById(R.id.tvtrxnumber);
            tvamountsb= view.findViewById(R.id.tvamountsb);
            tvDate= view.findViewById(R.id.tvDate);
            tvstatus= view.findViewById(R.id.tvstatus);
        }
    }
}
