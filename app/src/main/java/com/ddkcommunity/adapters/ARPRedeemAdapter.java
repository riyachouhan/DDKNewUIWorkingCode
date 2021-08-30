package com.ddkcommunity.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.activityFragmement;
import com.ddkcommunity.fragment.settingModule.ARPFragement;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;
import com.ddkcommunity.model.addActivityModel;
import com.ddkcommunity.utilies.AppConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.DataNotFound;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

public class ARPRedeemAdapter extends RecyclerView.Adapter<ARPRedeemAdapter.MyViewHolder>
{
    private ArrayList<ARPHistoryModel.Datum> ARPList;
    private Activity activity;

    public ARPRedeemAdapter(ArrayList<ARPHistoryModel.Datum> ARPList, Activity activity) {
        this.activity = activity;
        this.ARPList = ARPList;
    }

    public void updateData(ArrayList<ARPHistoryModel.Datum> ARPList) {
        this.ARPList = ARPList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.arp_redeem_layout, parent, false);
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
            holder.name.setText(ARPList.get(position).getName());
            holder.email.setText(ARPList.get(position).getEmail());
            holder.tvPrice.setText(ARPList.get(position).getPoints());
            String upperString = ARPList.get(position).getType().substring(0, 1).toUpperCase() + ARPList.get(position).getType().substring(1).toLowerCase();
            holder.tvremark.setText(upperString);
            /*if(ARPList.get(position).getIs_transferred().equalsIgnoreCase("0"))
            {
                holder.redeemlayout.setVisibility(View.VISIBLE);
            }else
            {
                holder.redeemlayout.setVisibility(View.GONE);
            }*/

            if(ARPList.get(position).getIs_transferred()==null)
            {
                holder.redeemlayout.setVisibility(View.VISIBLE);
            }else
            {
                holder.redeemlayout.setVisibility(View.GONE);
            }

            holder.redeemlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    String redrremid=ARPList.get(position).getId();
                    getRedeemRequest(activity,redrremid);
                }
            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return ARPList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView  name,email,tvPrice,tvremark;
        LinearLayout redeemlayout;

        public MyViewHolder(View view) {
            super(view);
            redeemlayout=view.findViewById(R.id.redeemlayout);
            name= view.findViewById(R.id.name);
            tvremark= view.findViewById(R.id.tvremark);
            email= view.findViewById(R.id.email);
            tvPrice= view.findViewById(R.id.tvPrice);
        }
    }

    private void getRedeemRequest(final Activity activity, String redeermid)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("redeem_id", redeermid);
        Log.d("php user ", hm.toString());
        AppConfig.getLoadInterface().transaferarpBalance(AppConfig.getStringPreferences(activity, Constant.JWTToken),hm).enqueue(new Callback<addActivityModel>() {
            @Override
            public void onResponse(Call<addActivityModel> call, Response<addActivityModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            getRedeem(activity,pd);
                            Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        }else
                        {
                            pd.dismiss();
                            DataNotFound(activity,response.body().getMsg()+"");
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
            public void onFailure(Call<addActivityModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getRedeem(final Activity activity,final ProgressDialog pd)
    {
        AppConfig.getLoadInterface().getARPRedeem(AppConfig.getStringPreferences(activity, Constant.JWTToken)).enqueue(new Callback<ARPHistoryModel>() {
            @Override
            public void onResponse(Call<ARPHistoryModel> call, Response<ARPHistoryModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        ArrayList<ARPHistoryModel.Datum> ARPListtab3=new ArrayList<>();
                        if (response.body().getStatus() == 1)
                        {
                            ARPListtab3.addAll(response.body().getData());
                            if(ARPListtab3.size()>0)
                            {
                                ARPRedeemAdapter mAdapter = new ARPRedeemAdapter(ARPListtab3, activity);
                                ARPFragement.rvProjectRecycle.setAdapter(mAdapter);
                            }else
                            {
                                ARPRedeemAdapter mAdapter = new ARPRedeemAdapter(ARPListtab3, activity);
                                ARPFragement.rvProjectRecycle.setAdapter(mAdapter);
                                Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            ARPRedeemAdapter mAdapter = new ARPRedeemAdapter(ARPListtab3, activity);
                            ARPFragement.rvProjectRecycle.setAdapter(mAdapter);
                            Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(activity,"server error in ARP");
                    }

                } catch (Exception e)
                {
                    if(pd.isShowing())
                        pd.dismiss();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ARPHistoryModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(activity,t.getMessage());
            }
        });

    }

}
