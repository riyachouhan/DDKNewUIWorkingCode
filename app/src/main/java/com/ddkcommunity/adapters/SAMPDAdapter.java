package com.ddkcommunity.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SAMPD.AddIconSmpdFragment;
import com.ddkcommunity.fragment.SAMPD.CompanyProfileFragmentSmpd;
import com.ddkcommunity.fragment.SFIOShowFragmement;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.SAMPDModel;
import com.ddkcommunity.model.ShowRequestApiModel;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.utilies.AppConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ddkcommunity.utilies.dataPutMethods.ShowSameWalletDialog;

public class SAMPDAdapter extends RecyclerView.Adapter<SAMPDAdapter.MyViewHolder> {

    private List<smpdModelNew.Datum> createCancellationRequestlist;
    private Activity activity;

    public SAMPDAdapter(List<smpdModelNew.Datum> createCancellationRequestlist,Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
    }

    public void updateData(List<smpdModelNew.Datum> createCancellationRequestlist) {
        this.createCancellationRequestlist= createCancellationRequestlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.samp_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
           holder.pdname.setText(createCancellationRequestlist.get(position).getName());
            Glide.with(activity)
                    .asBitmap()
                    .load(createCancellationRequestlist.get(position).getIconImage())
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
                    //if(createCancellationRequestlist.get(position).getComapany_profile_view_type().equalsIgnoreCase("ck_editor"))
                    //{
                        String iduser = createCancellationRequestlist.get(position).getId().toString();
                        Fragment fragment = new CompanyProfileFragmentSmpd();
                        Bundle arg = new Bundle();
                        arg.putString("id", iduser);
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);

                 /*   }else {
                        Fragment fragment = new SFIOShowFragmement();
                        Bundle arg = new Bundle();
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);
                    }*/
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
