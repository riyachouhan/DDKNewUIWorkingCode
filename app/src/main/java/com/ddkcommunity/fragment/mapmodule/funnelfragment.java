package com.ddkcommunity.fragment.mapmodule;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.ddkcommunity.adapters.HistoryAdapter;
import com.ddkcommunity.fragment.mapmodule.adapter.FunnelAdapter;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.model.navigationModel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class funnelfragment extends Fragment
{

     View view;
     Context mContext;
     RecyclerView rvRecycle;
    private ArrayList<finnelModel.Datum> funnelDAta;

    public funnelfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.funnel_fragment, container, false);
        mContext = getActivity();
        String[] str = {"  All  ", "Active", "Pending", "Pool", "Fulfilled", "Failed"};
        List<String> titles = Arrays.asList(str);
        MyTabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setTitle(titles);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                //........
                funnelDAta=new ArrayList<>();
                FunnelAdapter mAdapter = new FunnelAdapter(funnelDAta, getActivity());
                rvRecycle.setAdapter(mAdapter);
                if (tab.getPosition() == 0)
                {
                    getFunneldata("all");
                }else if (tab.getPosition() == 1)
                {
                    getFunneldata("active");
                }else if (tab.getPosition() == 2)
                {
                    getFunneldata("pending");
                }else if (tab.getPosition() == 3)
                {
                    getFunneldata("pool");
                }else if (tab.getPosition() == 4)
                {
                    getFunneldata("fulfilled");
                }else if (tab.getPosition() == 5)
                {
                    getFunneldata("failed");
                }
                //............
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                Log.d("Selected", "");
            }
        });

        getFunneldata("all");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Funnel");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void getFunneldata(String tabname)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken=App.pref.getString(Constant.MAPToken, "");
        Call<finnelModel> call = AppConfig.getLoadInterfaceMap().getFunnelData(maptoken,tabname);
        call.enqueue(new retrofit2.Callback<finnelModel>() {
            @Override
            public void onResponse(Call<finnelModel> call, Response<finnelModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                funnelDAta=new ArrayList<>();
                                funnelDAta.addAll(response.body().getData());
                                if(funnelDAta.size()>0)
                                {
                                    FunnelAdapter mAdapter = new FunnelAdapter(funnelDAta, getActivity());
                                    rvRecycle.setAdapter(mAdapter);
                                }else
                                {
                                    Toast.makeText(mContext, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                Toast.makeText(mContext, "Data Not Available", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("context","::");
                            ShowApiError(mContext,"server error funnel");
                        }

                    } catch (Exception e)
                    {
                        if(pd.isShowing())
                            pd.dismiss();

                        e.printStackTrace();
                    }
                } else {
                    if(pd.isShowing())
                        pd.dismiss();
                    ShowApiError(getActivity(),"server error in funnel");
                }
            }

            @Override
            public void onFailure(Call<finnelModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

}
