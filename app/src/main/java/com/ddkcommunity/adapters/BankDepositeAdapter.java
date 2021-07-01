package com.ddkcommunity.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.bankDepositeFragment;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.bankDepositeModel;
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

public class BankDepositeAdapter extends RecyclerView.Adapter<BankDepositeAdapter.MyViewHolder>
{
    private List<bankDepositeModel.Datum> data;
    private Activity activity;

    public BankDepositeAdapter(List<bankDepositeModel.Datum> data, Activity activity) {
        this.activity = activity;
        this.data = data;
    }

    public void updateData(List<bankDepositeModel.Datum> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bankdepositefragmentlay, parent, false);
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

            bankDepositeFragment.bankidsele="";
            holder.bank_subscriber_view.setText(data.get(position).getHeading()+"");
            holder.bankName.setText(data.get(position).getBankNameLabel()+"");
            holder.bankNameValue.setText(data.get(position).getBankName()+"");
            holder.bankAccountvalue.setText(data.get(position).getAccountNumber()+"");
            holder.bankAccountNo.setText(data.get(position).getAccountNumberLabel()+"");
            holder.bankAccountName.setText(data.get(position).getAccountNameLabel()+"");
            holder.bankAccountNameValue.setText(data.get(position).getAccountName()+"");
            if(data.get(position).getSwiftCodeLabel()== null || data.get(position).getSwiftCodeLabel().toString().equalsIgnoreCase(""))
            {
                holder.swiftcodevie.setVisibility(View.GONE);
            }else
            {
                holder.swiftcodevie.setVisibility(View.VISIBLE);
            }
            holder.swift_code.setText(data.get(position).getSwiftCode()+"");
            holder.swift_code_label.setText(data.get(position).getSwiftCodeLabel()+"");
            Glide.with(activity)
                    .asBitmap()
                    .load(data.get(position).getIcon())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            holder.bankIconImg.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            holder.bankIconImg.setImageResource(R.drawable.default_photo);
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
        public TextView bank_subscriber_view,bankName,bankNameValue,
                bankAccountNo,bankAccountvalue,bankAccountName,bankAccountNameValue,
                swift_code_label,swift_code;
        public ImageView bankIconImg;
        LinearLayout swiftcodevie;

        public MyViewHolder(View view) {
            super(view);
            swiftcodevie=view.findViewById(R.id.swiftcodevie);
            swift_code_label=view.findViewById(R.id.swift_code_label);
            swift_code=view.findViewById(R.id.swift_code);
            bank_subscriber_view=view.findViewById(R.id.bank_subscriber_view);
            bankIconImg=view.findViewById(R.id.bankIconImg);
            bankName=view.findViewById(R.id.bankName);
            bankNameValue=view.findViewById(R.id.bankNameValue);
            bankAccountNo=view.findViewById(R.id.bankAccountNo);
            bankAccountvalue=view.findViewById(R.id.bankAccountvalue);
            bankAccountName=view.findViewById(R.id.bankAccountName);
            bankAccountNameValue=view.findViewById(R.id.bankAccountNameValue);
        }
    }

}
