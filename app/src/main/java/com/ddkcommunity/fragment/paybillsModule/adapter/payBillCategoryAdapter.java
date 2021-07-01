package com.ddkcommunity.fragment.paybillsModule.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.paybillsModule.models.catModel;
import com.ddkcommunity.fragment.projects.CategoryAllFragment;
import com.ddkcommunity.fragment.projects.BillerAllFragment;
import com.ddkcommunity.fragment.projects.payAfterBillerFragment;

import java.util.List;

public class payBillCategoryAdapter extends RecyclerView.Adapter<payBillCategoryAdapter.MyViewHolder> {

    private List<catModel.DataCategory> createCancellationRequestlist;
    private Activity activity;

    public payBillCategoryAdapter(List<catModel.DataCategory> createCancellationRequestlist, Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
    }

    public void updateData(List<catModel.DataCategory> createCancellationRequestlist) {
        this.createCancellationRequestlist= createCancellationRequestlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paybillscategoryitemnew, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            String ctnam=createCancellationRequestlist.get(position).getCategoryName().toString();
            if(ctnam.equalsIgnoreCase("more"))
            {
                holder.catimg.setVisibility(View.GONE);
                holder.moreiocn.setVisibility(View.VISIBLE);
                holder.catname.setText("See All");
                holder.moreiocn.setImageResource(R.drawable.sellallbg);

            }else {
                holder.catimg.setVisibility(View.GONE);
                holder.moreiocn.setVisibility(View.VISIBLE);
                holder.catname.setText(createCancellationRequestlist.get(position).getCategoryName().toString());
                Glide.with(activity)
                        .asBitmap()
                        .load(createCancellationRequestlist.get(position).getCategoryImage())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                holder.moreiocn.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                holder.moreiocn.setImageResource(R.drawable.default_photo);
                            }
                        });
            }

            holder.btncateLiner.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(createCancellationRequestlist.get(position).getCategoryName().toString().equalsIgnoreCase("more"))
                    {
                        MainActivity.addFragment(new CategoryAllFragment(), true);
                    }else
                    {
                        String iduser=createCancellationRequestlist.get(position).getCategoryName();
                        Fragment fragment = new BillerAllFragment();
                        Bundle arg = new Bundle();
                        arg.putString("billerid", iduser);
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);
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
        return createCancellationRequestlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView catname;
        ImageView catimg,moreiocn;
        LinearLayout btncateLiner;

        public MyViewHolder(View view) {
            super(view);
            btncateLiner=view.findViewById(R.id.btncateLiner);
            catname=view.findViewById(R.id.catname);
            catimg=view.findViewById(R.id.catimg);
            moreiocn=view.findViewById(R.id.moreiocn);
            }
    }

}
