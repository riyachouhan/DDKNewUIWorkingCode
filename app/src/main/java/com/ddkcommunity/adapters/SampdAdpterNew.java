package com.ddkcommunity.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SAMPD.AddIconSmpdFragment;
import com.ddkcommunity.fragment.SAMPD.CompanyProfileFragmentSmpd;
import com.ddkcommunity.model.smpdModelNew;

import java.util.ArrayList;
import java.util.List;

public class SampdAdpterNew extends RecyclerView.Adapter<SampdAdpterNew.MyViewHolder> {

    private List<smpdModelNew.Favourite> createCancellationRequestlist;
     private Activity activity;

    public void updateData(ArrayList<smpdModelNew.Favourite> createCancellationRequestlist)
    {
        this.createCancellationRequestlist = createCancellationRequestlist;
        notifyDataSetChanged();
    }

    public SampdAdpterNew(ArrayList<smpdModelNew.Favourite> createCancellationRequestlist, Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
     }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_smpd_add, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        try
        {
            if (createCancellationRequestlist.get(position).getSampdCompany().getName().equals("Addoption")){
                holder.ic_addicon.setVisibility(View.VISIBLE);
                holder.favicon.setVisibility(View.GONE);
             }else
             {
                holder.ic_addicon.setVisibility(View.GONE);
                holder.favicon.setVisibility(View.VISIBLE);
                 Glide.with(activity)
                         .asBitmap()
                         .load(createCancellationRequestlist.get(position).getSampdCompany().getIconImage())
                         .into(new SimpleTarget<Bitmap>() {
                             @Override
                             public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                 RoundedBitmapDrawable circularBitmapDrawable =
                                         RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                                 circularBitmapDrawable.setCircular(true);
                                 holder.favicon.setImageDrawable(circularBitmapDrawable);
                             }

                             @Override
                             public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                 super.onLoadFailed(errorDrawable);
                                 holder.favicon.setImageResource(R.drawable.default_photo);
                             }

                         });

             }

         holder.ic_addicon.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View v)
             {
                 if(createCancellationRequestlist.get(position).getSampdCompany().getName().equals("Addoption")) {
                     MainActivity.addFragment(new AddIconSmpdFragment(), true);
                 }else
                 {
                     String iduser=createCancellationRequestlist.get(position).getId().toString();
                     Fragment fragment = new CompanyProfileFragmentSmpd();
                     Bundle arg = new Bundle();
                     arg.putString("id" , iduser);
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

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView favicon,ic_addicon;
        LinearLayout phlayout;

        public MyViewHolder(View view)
        {
            super(view);
            favicon=view.findViewById(R.id.favicon);
            phlayout=view.findViewById(R.id.phlayout);
            ic_addicon=view.findViewById(R.id.ic_addicon);
         }
    }

}
