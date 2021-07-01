package com.ddkcommunity.fragment.mapmodule.adapter;

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

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.fragment.mapmodule.model.powerOfXModel;
import com.ddkcommunity.fragment.mapmodule.model.powerXSubFragment;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.utilies.AppConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

public class powerxmainfragmentAdapter extends RecyclerView.Adapter<powerxmainfragmentAdapter.MyViewHolder>
{
    private List<powerOfXModel.Datum> data;
    private Activity activity;
    public String frgetytpe;
    int setclick=0;

    public powerxmainfragmentAdapter(String fragmenttype,List<powerOfXModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
        this.frgetytpe=fragmenttype;
    }

    public void updateData(List<powerOfXModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.powerxfragmentiteam, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        try
        {
            holder.powerxpoint.setText(data.get(position).getPoints()+"");
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

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            holder.rvRecycle.setLayoutManager(mLayoutManager);
            holder.rvRecycle.setItemAnimator(new DefaultItemAnimator());

            holder.arrow_click.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(holder.rvRecycle.getVisibility()==View.GONE)
                    {
                        holder.ivdropIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.up_arrow));
                        String fromdate=data.get(position).getFromDate();
                        String todate=data.get(position).getToDate();
                        if(frgetytpe.equalsIgnoreCase("platinum"))
                        {
                            getSubPowerxData(activity, holder.rvRecycle, fromdate, todate);

                        }else if(frgetytpe.equalsIgnoreCase("titanium"))
                        {
                            getTitanumumData(activity, holder.rvRecycle, fromdate, todate);
                        }else
                        {
                            getPowerXData(activity, holder.rvRecycle, fromdate, todate);
                        }
                    }else
                    {
                        holder.ivdropIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.drop_down_icon));
                        holder.rvRecycle.setVisibility(View.GONE);
                    }
                }
            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public LinearLayout arrow_click;
        ImageView ivdropIcon;
        RecyclerView rvRecycle;
        TextView powerxpoint,tvdatetime;

        public MyViewHolder(View view) {
            super(view);
            powerxpoint=view.findViewById(R.id.powerxpoint);
            tvdatetime=view.findViewById(R.id.tvdatetime);
            rvRecycle=view.findViewById(R.id.rvRecycle);
            arrow_click = view.findViewById(R.id.arrow_click);
            ivdropIcon=view.findViewById(R.id.ivdropIcon);
        }
    }

    private void getSubPowerxData(final Activity activity,final RecyclerView rvview,final String fromdate,final String todate)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<powerXSubFragment> call = AppConfig.getLoadInterfaceMap().getPlatinumDetails(maptoken,fromdate,todate);
        call.enqueue(new retrofit2.Callback<powerXSubFragment>() {
            @Override
            public void onResponse(Call<powerXSubFragment> call, Response<powerXSubFragment> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {

                                ArrayList<powerXSubFragment.Datum> subfunnelDAta=new ArrayList<>();
                                subfunnelDAta.addAll(response.body().getData());
                                if(subfunnelDAta.size()>0)
                                {

                                    powerxfrgamentadapter mAdapter = new powerxfrgamentadapter(subfunnelDAta,activity);
                                    rvview.setAdapter(mAdapter);
                                    rvview.setVisibility(View.VISIBLE);

                                }else
                                {
                                    Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("context","::");
                            ShowApiError(activity,"server error direct-bonus");
                        }

                    } catch (Exception e)
                    {
                        if(pd.isShowing())
                            pd.dismiss();

                        e.printStackTrace();
                    }
                } else {
                    if(pd.isShowing())
                        pd.dismiss();
                    ShowApiError(activity,"server error in direct-bonus");
                }
            }

            @Override
            public void onFailure(Call<powerXSubFragment> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(activity,t.getMessage());
            }
        });
    }


    private void getTitanumumData(final Activity activity,final RecyclerView rvview,final String fromdate,final String todate)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<powerXSubFragment> call = AppConfig.getLoadInterfaceMap().getTitaniumDetails(maptoken,fromdate,todate);
        call.enqueue(new retrofit2.Callback<powerXSubFragment>() {
            @Override
            public void onResponse(Call<powerXSubFragment> call, Response<powerXSubFragment> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {

                                ArrayList<powerXSubFragment.Datum> subfunnelDAta=new ArrayList<>();
                                subfunnelDAta.addAll(response.body().getData());
                                if(subfunnelDAta.size()>0)
                                {

                                    powerxfrgamentadapter mAdapter = new powerxfrgamentadapter(subfunnelDAta,activity);
                                    rvview.setAdapter(mAdapter);
                                    rvview.setVisibility(View.VISIBLE);

                                }else
                                {
                                    Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("context","::");
                            ShowApiError(activity,"server error direct-bonus");
                        }

                    } catch (Exception e)
                    {
                        if(pd.isShowing())
                            pd.dismiss();

                        e.printStackTrace();
                    }
                } else {
                    if(pd.isShowing())
                        pd.dismiss();
                    ShowApiError(activity,"server error in direct-bonus");
                }
            }

            @Override
            public void onFailure(Call<powerXSubFragment> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(activity,t.getMessage());
            }
        });

    }

    private void getPowerXData(final Activity activity,final RecyclerView rvview,final String fromdate,final String todate)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<powerXSubFragment> call = AppConfig.getLoadInterfaceMap().getPowerBonusDetails(maptoken,fromdate,todate);
        call.enqueue(new retrofit2.Callback<powerXSubFragment>() {
            @Override
            public void onResponse(Call<powerXSubFragment> call, Response<powerXSubFragment> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {

                                ArrayList<powerXSubFragment.Datum> subfunnelDAta=new ArrayList<>();
                                subfunnelDAta.addAll(response.body().getData());
                                if(subfunnelDAta.size()>0)
                                {

                                    powerxfrgamentadapter mAdapter = new powerxfrgamentadapter(subfunnelDAta,activity);
                                    rvview.setAdapter(mAdapter);
                                    rvview.setVisibility(View.VISIBLE);

                                }else
                                {
                                    Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("context","::");
                            ShowApiError(activity,"server error direct-bonus");
                        }

                    } catch (Exception e)
                    {
                        if(pd.isShowing())
                            pd.dismiss();

                        e.printStackTrace();
                    }
                } else {
                    if(pd.isShowing())
                        pd.dismiss();
                    ShowApiError(activity,"server error in direct-bonus");
                }
            }

            @Override
            public void onFailure(Call<powerXSubFragment> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(activity,t.getMessage());
            }
        });

    }

}
