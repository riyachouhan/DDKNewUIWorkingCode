package com.ddkcommunity.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SFIOShowFragmement;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.sfioHeaderModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.model.sfioSubPackageModel;
import com.ddkcommunity.utilies.AppConfig;

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

public class SfioHeaderAdapter extends RecyclerView.Adapter<SfioHeaderAdapter.MyViewHolder>
{
    private List<sfioHeaderModel.BankDeposit> data;
    private Context activity;

    public SfioHeaderAdapter(List<sfioHeaderModel.BankDeposit> data, Context activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<sfioHeaderModel.BankDeposit> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sfioheadertablayout, parent, false);
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
            String udata="Trx Link..";
            SpannableString content = new SpannableString(udata);
            content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
            holder.trxlink.setText(content);
            holder.amountonetv.setText("$"+data.get(position).getAmount()+"");
            holder.idonetv.setText("#"+data.get(position).getTrxNumber()+"");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null,date1=null;
            String output = null,output1=null;
            try
            {
                //Converting the input String to Date
                date= df.parse(data.get(position).getSubscriptionDate().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                holder.dateonetv.setText(output);

            }catch(ParseException pe)
            {
                pe.printStackTrace();
            }

            String uploadreceipt_status=data.get(position).getUploadBtn()+"";
            if(uploadreceipt_status.equalsIgnoreCase("1"))
            {

                String udataupload="Upload Receipt";
                SpannableString content1 = new SpannableString(udataupload);
                content1.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
                holder.uploadbutton.setText(content1);
                holder.uploadbutton.setVisibility(View.VISIBLE);
                holder.statsbank.setVisibility(View.GONE);

            }else
            {
                holder.uploadbutton.setVisibility(View.GONE);
                holder.statsbank.setVisibility(View.VISIBLE);
                if(data.get(position).getBankStatus().equalsIgnoreCase("Approved") || data.get(position).getBankStatus().equalsIgnoreCase("Confirmed"))
                {
                    holder.statsbank.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    holder.statsbank.setText(data.get(position).getBankStatus().toString().toUpperCase()+"");
                    holder.statsbank.setText("Confirmed");
                   // holder.statsbank.setBackground(ContextCompat.getDrawable(activity,R.drawable.status_bg_lightgreen));
                }else
                {
                        holder.statsbank.setTextColor(ContextCompat.getColor(activity, R.color.white));
                        holder.statsbank.setText(data.get(position).getBankStatus().toString().toUpperCase() + "");
                     //   holder.statsbank.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_card_back));
                }

            }

            holder.trxlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                String linkvalue=data.get(position).getTrxLink().toString();
                    if(linkvalue!=null && !linkvalue.equalsIgnoreCase(""))
                    {
                        //send view
                        Fragment fragment = new SendLinkFragment();
                        Bundle arg = new Bundle();
                        arg.putString("link", linkvalue);
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);
                    }else
                    {
                        Toast.makeText(activity, "Please wait while the transaction being created.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.uploadbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String id=data.get(position).getId()+"";
                    String reasone="";
                    MainActivity.showDialogHeaderData(reasone,SFIOShowFragmement.mAdapterher,position,data,id,activity);
                }
            });

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView  trxlink,amountonetv,dateonetv,idonetv,uploadbutton,statsbank;

        public MyViewHolder(View view)
        {
            super(view);
            statsbank=view.findViewById(R.id.statsbank);
            trxlink=view.findViewById(R.id.trxlink);
            amountonetv=view.findViewById(R.id.amountonetv);
            dateonetv=view.findViewById(R.id.dateonetv);
            idonetv=view.findViewById(R.id.idonetv);
            uploadbutton=view.findViewById(R.id.uploadbutton);
        }

    }

    private void getSubPowerxData(final Context activity,final RecyclerView rvview,final String idsfio)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("sfio_id",idsfio);
        Log.d("php user ", hm.toString());
        AppConfig.getLoadInterface().getSFIOSubPAckage(AppConfig.getStringPreferences(activity, Constant.JWTToken),hm).enqueue(new Callback<sfioSubPackageModel>() {
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
                                ArrayList<sfioSubPackageModel.Datum> subfunnelDAta=new ArrayList<>();
                                subfunnelDAta.addAll(response.body().getData());
                                if(subfunnelDAta.size()>0)
                                {

                                    sfiosubfrgamentadapter mAdapter = new sfiosubfrgamentadapter(subfunnelDAta,activity);
                                    rvview.setAdapter(mAdapter);
                                    rvview.setVisibility(View.VISIBLE);

                                }else
                                {
                                    Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }

                        }else
                        {
                            pd.dismiss();
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

}
