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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SAMPD.CompanyProfileFragmentSmpd;
import com.ddkcommunity.fragment.SAMPD.companhyProfilePicView;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.SMPDCompanyDetailsModel;
import com.ddkcommunity.model.smpdModelNew;

import java.util.List;

public class ComapnyImagesAdapter extends RecyclerView.Adapter<ComapnyImagesAdapter.MyViewHolder> {

    private List<SMPDCompanyDetailsModel.SampdCompanyService> createCancellationRequestlist;
    private Activity activity;

    public ComapnyImagesAdapter(List<SMPDCompanyDetailsModel.SampdCompanyService> createCancellationRequestlist, Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
    }

    public void updateData(List<SMPDCompanyDetailsModel.SampdCompanyService> createCancellationRequestlist) {
        this.createCancellationRequestlist= createCancellationRequestlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.samp_itemprofilecompany, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {

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

                    });

            holder.ll_GeneralInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String linkvalue=createCancellationRequestlist.get(position).getImage();
                    if(linkvalue!=null) {
                        //send view
                        Fragment fragment = new companhyProfilePicView();
                        Bundle arg = new Bundle();
                        arg.putString("link", linkvalue);
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
        public TextView pdname;
        public ImageView smpdicon;
        LinearLayout ll_GeneralInformation;

        public MyViewHolder(View view) {
            super(view);
            ll_GeneralInformation=view.findViewById(R.id.ll_GeneralInformation);
            pdname=view.findViewById(R.id.pdname);
            smpdicon=view.findViewById(R.id.smpdicon);
            }
    }

}
