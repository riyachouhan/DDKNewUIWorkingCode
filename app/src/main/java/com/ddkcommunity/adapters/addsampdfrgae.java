package com.ddkcommunity.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.model.BankList;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.model.smpdfavmodel;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

public class addsampdfrgae extends RecyclerView.Adapter<addsampdfrgae.ViewHolder>
        implements SectionIndexer {

    private List<smpdModelNew.Datum> mDataArray;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private HashMap<Integer, Integer> sectionsTranslator = new HashMap<>();
    private ArrayList<Integer> mSectionPositions;
    Activity activity;

    public addsampdfrgae(Activity activity,List<smpdModelNew.Datum> dataset) {
        mDataArray = dataset;
        this.activity=activity;
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public addsampdfrgae.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billeriteamlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        holder.tvBankName.setText(mDataArray.get(position).getName());
        holder.likeicon.setVisibility(View.VISIBLE);

        if(mDataArray.get(position).getIsFavourite()==1)
        {
            holder.likeicon.setImageResource(R.drawable.heart);
        }else
        {
            holder.likeicon.setImageResource(R.drawable.heart_line);
        }
        Glide.with(activity)
                .asBitmap()
                .load(mDataArray.get(position).getIconImage())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        holder.ivBankIcon.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        holder.ivBankIcon.setImageResource(R.drawable.default_photo);
                    }

                });

        holder.likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(mDataArray.get(position).getIsFavourite()==1)
                {
                    holder.likeicon.setImageResource(R.drawable.heart_line);
                }else
                {
                    holder.likeicon.setImageResource(R.drawable.heart);

                }
                getMakeFv(mDataArray.get(position).getId().toString());
            }
        });
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public Object[] getSections() {
       /* List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = mDataArray.size(); i < size; i++) {
            String section = String.valueOf(mDataArray.get(i).charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        } */

        List<String> sections = new ArrayList<>(27);
        ArrayList<String> alphabetFull = new ArrayList<>();

        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = mDataArray.size(); i < size; i++) {
            String section = String.valueOf(mDataArray.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.toString().toUpperCase().contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        for (int i = 0; i < mSections.length(); i++) {
            alphabetFull.add(String.valueOf(mSections.charAt(i)));
        }


        return alphabetFull.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionsTranslator.get(sectionIndex));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName;
        ImageView ivBankIcon,likeicon;

        ViewHolder(View itemView) {
            super(itemView);
            ivBankIcon=itemView.findViewById(R.id.ivBankIcon);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            likeicon=itemView.findViewById(R.id.likeicon);
        }
    }

    private void getMakeFv(String searchkeyword)
    {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("sampd_company_id", searchkeyword);

        AppConfig.getLoadInterface().AddFavItem(AppConfig.getStringPreferences(activity, Constant.JWTToken),hm).enqueue(new Callback<smpdfavmodel>() {
            @Override
            public void onResponse(Call<smpdfavmodel> call, Response<smpdfavmodel> response) {
                try {
                    if(pd.isShowing()) {
                        pd.dismiss();
                    }

                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            AppConfig.showToast(response.body().getMsg());
                        }else
                        {
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        ShowApiError(activity,"server error sampd-company/company-list");
                    }

                } catch (Exception e) {
                    if(pd.isShowing()) {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<smpdfavmodel> call, Throwable t)
            {
                if(pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}