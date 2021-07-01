package com.ddkcommunity.fragment.SAMPD;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.ComapnyImagesAdapter;
import com.ddkcommunity.model.SMPDCompanyDetailsModel;
import com.ddkcommunity.utilies.AppConfig;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class companhyProfilePicView extends Fragment implements View.OnClickListener
{
    private Context mContext;
    ImageView companyview;
    TextView sharebutton;
    String link;

    public companhyProfilePicView()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.companyprofileviewlayout, container, false);
        mContext = getActivity();
        if (getArguments().getString("link") != null)
        {
                link= getArguments().getString("link");
        }
        companyview=view.findViewById(R.id.companyview);
        sharebutton=view.findViewById(R.id.sharebutton);
        sharebutton.setOnClickListener(this);
        Glide.with(getActivity())
                .asBitmap()
                .load(link)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        companyview.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        companyview.setImageResource(R.drawable.default_photo);
                    }

                });

        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sharebutton:
                try
                {
                    String messagecontetn =link;
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, messagecontetn);
                    startActivity(Intent.createChooser(shareIntent , "Share Company Profile"));

                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("");
        MainActivity.enableBackViews(true);
        MainActivity.bottomNavigation.setVisibility(View.GONE);
    }

}