package com.ddkcommunity.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SAMPD.CompanyProfileFragmentSmpd;
import com.ddkcommunity.model.smpdModelNew;

import java.util.List;

public class Marketoplaceadpater extends RecyclerView.Adapter<Marketoplaceadpater.MyViewHolder> {

    private List<smpdModelNew.Banner> createCancellationRequestlist;
    private Activity activity;

    public Marketoplaceadpater(List<smpdModelNew.Banner> createCancellationRequestlist, Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
    }

    public void updateData(List<smpdModelNew.Banner> createCancellationRequestlist) {
        this.createCancellationRequestlist= createCancellationRequestlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marketplacemod, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
                if(position==0)
                {
                    holder.smpdicon.setImageResource(R.drawable.marketplace_banner);

                }else if(position==1)
                {
                    holder.smpdicon.setImageResource(R.drawable.marketplace_banner_two);

                }else if(position==2)
                {
                    holder.smpdicon.setImageResource(R.drawable.marketplace_banner_three);

                }else
                {
                    holder.smpdicon.setImageResource(R.drawable.marketplace_banner_four);

                }
           /*String imgj=createCancellationRequestlist.get(position).getImage();
            Glide.with(activity)
                    .asBitmap()
                    .load(createCancellationRequestlist.get(position).getImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            holder.smpdicon.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            holder.smpdicon.setImageResource(R.drawable.default_photo);
                        }

                    });*/
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return createCancellationRequestlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
         public ImageView smpdicon;
        LinearLayout ll_GeneralInformation;

        public MyViewHolder(View view) {
            super(view);
           // ll_GeneralInformation=view.findViewById(R.id.ll_GeneralInformation);
            smpdicon=view.findViewById(R.id.smpdicon);
            }
    }

}
