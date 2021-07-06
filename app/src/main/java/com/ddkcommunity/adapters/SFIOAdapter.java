package com.ddkcommunity.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SFIOShowFragmement;
import com.ddkcommunity.fragment.activityFragmement;
import com.ddkcommunity.fragment.mapmodule.adapter.powerxfrgamentadapter;
import com.ddkcommunity.fragment.mapmodule.model.powerXSubFragment;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.activityModel;
import com.ddkcommunity.model.addActivityModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.model.sfioSubPackageModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.ScalingUtilities;
import com.ddkcommunity.utilies.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowSameWalletDialog;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SFIOAdapter extends RecyclerView.Adapter<SFIOAdapter.MyViewHolder>
{
    private List<sfioModel.Datum> data;
    private Context activity;

    public SFIOAdapter(List<sfioModel.Datum> data, Context activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<sfioModel.Datum> data) {
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
                .inflate(R.layout.sfiofragmentlayout, parent, false);
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
            holder.tvtrxlink.setText(content);
            holder.tvAount.setText("$"+data.get(position).getAmount()+"");
            holder.tvtrxnumber.setText("#"+data.get(position).getTrxNumber()+"");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //Desired format: 24 hour format: Change the pattern as per the need
            DateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null,date1=null;
            String output = null,output1=null;
            try{
                //Converting the input String to Date
                date= df.parse(data.get(position).getSubscriptionDate().toString());
                //Changing the format of date and storing it in String
                output = outputformat.format(date);
                //Displaying the date
                holder.tvDate.setText(output);

                String remindaya=data.get(position).getRemaining_days().toString();
                holder.tvRemainingdays.setText("Remaining Days : "+remindaya);

            }catch(ParseException pe){
                pe.printStackTrace();
            }

            String remindaya=data.get(position).getRemaining_days().toString();
            String cancellation_status=data.get(position).getCancellation_status();
            String uploadreceipt_status=data.get(position).getUpload_btn().toString();
            if(uploadreceipt_status.equalsIgnoreCase("1"))
            {
                String udataupload="Upload Receipt";
                SpannableString content1 = new SpannableString(udataupload);
                content1.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
                holder.uploadRequestview.setText(content1);

                holder.uploadRequestview.setVisibility(View.VISIBLE);
                holder.statusliner.setVisibility(View.GONE);
                holder.cancelRequestview.setVisibility(View.GONE);

                if(data.get(position).getBank_status().equalsIgnoreCase("Rejected"))
                {
                    holder.rejectvie.setTextColor(ContextCompat.getColor(activity, R.color.red_color));
                    holder.rejectvie.setText(data.get(position).getBank_status().toString().toUpperCase()+"");
                    holder.rejectvie.setBackground(ContextCompat.getDrawable(activity,R.drawable.status_bg_lightred));
                    holder.Noteview.setText("Note : "+data.get(position).getReason_for_rejection());
                    holder.Noteview.setVisibility(View.VISIBLE);
                }else
                {
                    holder.rejectvie.setVisibility(View.INVISIBLE);
                    holder.Noteview.setVisibility(View.GONE);
                }

            }else if(remindaya.equalsIgnoreCase("0") && cancellation_status.equalsIgnoreCase("pending"))
            {
                String udataupload="Confirm Cancellation";
                SpannableString content1 = new SpannableString(udataupload);
                content1.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
                holder.cancelRequestview.setText(content1);

                holder.uploadRequestview.setVisibility(View.GONE);
                holder.statusliner.setVisibility(View.GONE);
                holder.cancelRequestview.setVisibility(View.VISIBLE);

            }else
            {

                holder.uploadRequestview.setVisibility(View.GONE);
                holder.statusliner.setVisibility(View.VISIBLE);
                holder.cancelRequestview.setVisibility(View.GONE);

                if(data.get(position).getBank_status().equalsIgnoreCase("Approved") || data.get(position).getBank_status().equalsIgnoreCase("Confirmed"))
                {
                    holder.arrow_click.setVisibility(View.VISIBLE);
                    holder.Statusview.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen));
                    //holder.Statusview.setText(data.get(position).getBank_status().toString().toUpperCase()+"");
                    holder.Statusview.setText("Confirmed");
                    holder.Statusview.setBackground(ContextCompat.getDrawable(activity,R.drawable.status_bg_lightgreen));
                    holder.Noteview.setVisibility(View.GONE);
                    holder.rejectvie.setVisibility(View.INVISIBLE);

                }else
                {
                    holder.arrow_click.setVisibility(View.INVISIBLE);
                    if(data.get(position).getBank_status().equalsIgnoreCase("Rejected"))
                    {
                        holder.Statusview.setTextColor(ContextCompat.getColor(activity, R.color.red_color));
                        holder.Statusview.setText(data.get(position).getBank_status().toString().toUpperCase()+"");
                        holder.Statusview.setBackground(ContextCompat.getDrawable(activity,R.drawable.status_bg_lightred));
                        holder.Noteview.setText("Note : "+data.get(position).getReason_for_rejection());
                        holder.Noteview.setVisibility(View.VISIBLE);
                        holder.rejectvie.setVisibility(View.VISIBLE);
                    }else
                    {
                        holder.Noteview.setVisibility(View.GONE);
                        holder.rejectvie.setVisibility(View.INVISIBLE);
                        holder.Statusview.setTextColor(ContextCompat.getColor(activity, R.color.darkblue));
                        holder.Statusview.setText(data.get(position).getBank_status().toString().toUpperCase() + "");
                        holder.Statusview.setBackground(ContextCompat.getDrawable(activity, R.drawable.light_card_back));

                    }
                }

            }

            if(data.get(position).getBank_status().equalsIgnoreCase("Approved") || data.get(position).getBank_status().equalsIgnoreCase("Confirmed"))
            {

                holder.arrow_click.setVisibility(View.VISIBLE);
            }else
            {
                holder.arrow_click.setVisibility(View.INVISIBLE);
            }

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
            holder.rvRecycle.setLayoutManager(mLayoutManager);
            holder.rvRecycle.setItemAnimator(new DefaultItemAnimator());

            holder.tvtrxlink.setOnClickListener(new View.OnClickListener() {
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

            holder.arrow_click.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(holder.rvRecycle.getVisibility()==View.GONE)
                    {
                        holder.ivdropIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.up_arrow));
                        String idsfio=data.get(position).getId()+"";
                        getSubPowerxData(activity,holder.rvRecycle,idsfio);
                        holder.remaingdayaly.setVisibility(View.VISIBLE);
                    }else
                    {
                        holder.ivdropIcon.setImageDrawable(activity.getResources().getDrawable(R.drawable.drop_down_icon));
                        holder.rvRecycle.setVisibility(View.GONE);
                        holder.remaingdayaly.setVisibility(View.GONE);
                    }
                }
            });

            holder.cancelRequestview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String id=data.get(position).getId()+"";
                    getRedeemData(data,SFIOShowFragmement.mAdapter,position,holder.cancelRequestview,holder.statusliner,holder.Statusview,activity,id);
                }
            });

            holder.uploadRequestview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String id=data.get(position).getId()+"";
                    String reasone=data.get(position).getReason_for_rejection();
                    MainActivity.showDialogCryptoData(reasone,SFIOShowFragmement.mAdapter,position,data,id,activity);
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
        public LinearLayout arrow_click;
        ImageView ivdropIcon;
        RecyclerView rvRecycle;
        LinearLayout remaingdayaly,statusliner;
        TextView   rejectvie,Noteview,uploadRequestview,Statusview,cancelRequestview
                ,tvRemainingdays,tvtrxnumber,tvAount,tvDate,tvtrxlink;

        public MyViewHolder(View view)
        {
            super(view);
            rejectvie=view.findViewById(R.id.rejectvie);
            Noteview=view.findViewById(R.id.Noteview);
            statusliner=view.findViewById(R.id.statusliner);
            uploadRequestview=view.findViewById(R.id.uploadRequestview);
            Statusview=view.findViewById(R.id.Statusview);
            cancelRequestview=view.findViewById(R.id.cancelRequestview);
            remaingdayaly=view.findViewById(R.id.remaingdayaly);
            tvRemainingdays=view.findViewById(R.id.tvRemainingdays);
            tvtrxnumber=view.findViewById(R.id.tvtrxnumber);
            tvAount=view.findViewById(R.id.tvAount);
            tvDate=view.findViewById(R.id.tvDate);
            tvtrxlink=view.findViewById(R.id.tvtrxlink);
            rvRecycle=view.findViewById(R.id.rvRecycle);
            arrow_click = view.findViewById(R.id.arrow_click);
            ivdropIcon=view.findViewById(R.id.ivdropIcon);
      }

    }

    private void getRedeemData(final List<sfioModel.Datum> data, final SFIOAdapter mAdapter, final int position, final TextView cancelconfirm, final LinearLayout statusliner, final TextView Statusview, final Context activity, final String idsfio)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("sfio_id",idsfio);
        Log.d("php user ", hm.toString());
        AppConfig.getLoadInterface().getCancellationstatus(AppConfig.getStringPreferences(activity, Constant.JWTToken),hm).enqueue(new Callback<sfioSubPackageModel>() {
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
                            data.get(position).setBank_status("Confirmed");
                            mAdapter.updateData(data);

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
