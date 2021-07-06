package com.ddkcommunity.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
            //.............
            ArrayList<bankDepositeModel.Bankfield> bankfiel=new ArrayList<>();
            bankfiel.addAll(data.get(position).getBankfield());

            for(int i=0;i<bankfiel.size();i++)
            {
                String labaleview=bankfiel.get(i).getLabel().toString();
                String valueview=bankfiel.get(i).getValue().toString();
                createdynamic(holder.subaddpaylayout,labaleview,valueview,activity);
            }
            //...............
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void createdynamic(LinearLayout subaddpaylayout,String labaleview,String valueview,Activity activity)
    {
        LinearLayout row = new LinearLayout(activity);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(0, 10, 0, 10);
        row.setLayoutParams(p);
        row.setGravity(Gravity.TOP);

        TextView textView = new TextView(activity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(250, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(11);
        String upperString = labaleview.substring(0, 1).toUpperCase() + labaleview.substring(1).toLowerCase();
        textView.setText(upperString+" ");
        textView.setTextColor(activity.getResources().getColor(R.color.txtblack));
        row.addView(textView);

        TextView textView1 = new TextView(activity);
        textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView1.setGravity(Gravity.CENTER_VERTICAL);
        textView1.setTextSize(11);
        String upperString1 = "-----------";
        textView1.setText(upperString1+" ");
        textView1.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        row.addView(textView1);

        TextView newField = new TextView(activity);
        newField.setGravity(Gravity.LEFT);
        newField.setTextSize(12);
        String upperString23 = valueview.substring(0, 1).toUpperCase() + valueview.substring(1).toLowerCase();
        newField.setText(upperString23);
        newField.setPadding(5,5,5,5);
        newField.setBackgroundColor(activity.getResources().getColor(R.color.white));
        newField.setTextColor(activity.getResources().getColor(R.color.txtblack));
        row.addView(newField);
        subaddpaylayout.addView(row);

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView bank_subscriber_view;
        public ImageView bankIconImg;
        LinearLayout subaddpaylayout;

        public MyViewHolder(View view) {
            super(view);
            bank_subscriber_view=view.findViewById(R.id.bank_subscriber_view);
            bankIconImg=view.findViewById(R.id.bankIconImg);
            subaddpaylayout=view.findViewById(R.id.subaddpaylayout);
        }
    }

}
