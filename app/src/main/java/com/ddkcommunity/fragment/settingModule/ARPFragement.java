package com.ddkcommunity.fragment.settingModule;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.ddkcommunity.adapters.ARPAdapter;
import com.ddkcommunity.adapters.ARPHistoryAdapter;
import com.ddkcommunity.adapters.ARPRedeemAdapter;
import com.ddkcommunity.model.arpstausModel;
import com.ddkcommunity.utilies.AppConfig;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class ARPFragement extends Fragment
{
    private Context mContext;
    public static RecyclerView rvProjectRecycle;
    ArrayList<ARPDirectModel.Datum> ARPListtab1;
    ArrayList<ARPHistoryModel.Datum> ARPListtab2;
    ArrayList<ARPHistoryModel.Datum> ARPListtab3;
    LinearLayout layoutdirecet,layouthistory;
    CircleImageView imgprofile;
    TextView name_TV,email_TV,appliedarp;
    CircleProgressBar custom_progress_outer;

    public ARPFragement()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.arpfragment, container, false);
        mContext = getActivity();
        layoutdirecet=view.findViewById(R.id.layoutdirecet);
        layouthistory=view.findViewById(R.id.layouthistory);
        imgprofile=view.findViewById(R.id.imgprofile);
        appliedarp=view.findViewById(R.id.appliedarp);
        name_TV=view.findViewById(R.id.name_TV);
        email_TV=view.findViewById(R.id.email_TV);
        name_TV.setText(MainActivity.userData.getUser().getName());
        email_TV.setText(MainActivity.userData.getUser().getEmail());
        //..........
        rvProjectRecycle = view.findViewById(R.id.rvProjectRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvProjectRecycle.setLayoutManager(mLayoutManager);
        rvProjectRecycle.setItemAnimator(new DefaultItemAnimator());
        //..............
        TabLayout tabLayout = view.findViewById(R.id.profiletabs);
        tabLayout.addTab(tabLayout.newTab().setText(" DIRECT REFERRAL "));
        tabLayout.addTab(tabLayout.newTab().setText(" REDEEM "));
        tabLayout.addTab(tabLayout.newTab().setText(" HISTORY "));
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }
        //..........
        getArpBalance();
        ARPListtab1=new ArrayList<>();
        ARPAdapter mAdapter = new ARPAdapter(ARPListtab1, getActivity());
        rvProjectRecycle.setAdapter(mAdapter);
        layoutdirecet.setVisibility(View.VISIBLE);
        layouthistory.setVisibility(View.GONE);
        getARPTABDirectReferral();
        custom_progress_outer=view.findViewById(R.id.custom_progress_outer);
        custom_progress_outer.setProgress(100);
        if(MainActivity.userData.getUser().getUserImage()!=null && !MainActivity.userData.getUser().getUserImage().equalsIgnoreCase(null) && !MainActivity.userData.getUser().getUserImage().equalsIgnoreCase(""))
        {
            Glide.with(getActivity())
                    .asBitmap()
                    .load(MainActivity.userData.getUser().getUserImage())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            imgprofile.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            imgprofile.setImageResource(R.drawable.default_photo);
                        }

                    });
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (tab.getPosition() == 0)
                {
                    layoutdirecet.setVisibility(View.VISIBLE);
                    layouthistory.setVisibility(View.GONE);
                    ARPListtab1=new ArrayList<>();
                    ARPAdapter mAdapter = new ARPAdapter(ARPListtab1, getActivity());
                    rvProjectRecycle.setAdapter(mAdapter);
                    getARPTABDirectReferral();

                } else if (tab.getPosition() == 1)
                {
                    layoutdirecet.setVisibility(View.GONE);
                    layouthistory.setVisibility(View.VISIBLE);
                    ARPListtab3=new ArrayList<>();
                    ARPRedeemAdapter mAdapter = new ARPRedeemAdapter(ARPListtab3, getActivity());
                    rvProjectRecycle.setAdapter(mAdapter);
                    getARPTABRedeem(getActivity());
                }else
                {
                    layoutdirecet.setVisibility(View.GONE);
                    layouthistory.setVisibility(View.VISIBLE);
                    ARPListtab2=new ArrayList<>();
                    ARPHistoryAdapter mAdapter = new ARPHistoryAdapter(ARPListtab2, getActivity());
                    rvProjectRecycle.setAdapter(mAdapter);
                    getARPTABHisotry();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("Selected", "");
                if (tab.getPosition() == 0)
                {
                }
            }
        });
        return view;
    }

    private void getArpBalance()
    {
        AppConfig.getLoadInterface().getArpWalletBalance(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ARPWalletBalanceModel>() {
            @Override
            public void onResponse(Call<ARPWalletBalanceModel> call, Response<ARPWalletBalanceModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            appliedarp.setText("ARP Balance "+response.body().getBalance());

                        }else if(response.body().getStatus()==2)
                        {
                            appliedarp.setText("Applied for ARP");
                        }else
                        {
                            appliedarp.setText("Apply for ARP");
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ARPWalletBalanceModel> call, Throwable t)
            {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        appliedarp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String arpdata=appliedarp.getText().toString();
                if(arpdata.equalsIgnoreCase("Apply for ARP"))
                {
                    getArpStatus();
                }else if(arpdata.equalsIgnoreCase("Applied for ARP"))
                {
                    getArpStatus();

                }else
                {
                    Fragment fragment = new ARPFragement();
                    Bundle arg = new Bundle();
                    fragment.setArguments(arg);
                    MainActivity.addFragment(fragment, true);
                }
            }
        });

    }

    private void getArpStatus()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        AppConfig.getLoadInterface().getArpStatus(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<arpstausModel>() {
            @Override
            public void onResponse(Call<arpstausModel> call, Response<arpstausModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        pd.dismiss();
                        if (response.body().getStatus()==1)
                        {
                            appliedarp.setText("Applied for ARP");
                            ShowSuccessDialog(getActivity(),response.body().getMsg(),"Success","OK");

                        }else if (response.body().getStatus()==2)
                        {
                            appliedarp.setText("Applied for ARP");
                            ShowSuccessDialog(getActivity(),response.body().getMsg(),"Alert","OK");

                        }else
                        {
                            appliedarp.setText("Apply for ARP");
                        }

                    } else {
                        pd.dismiss();
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<arpstausModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void ShowSuccessDialog(Context activity,String succesmsg,String titleher,String buttonstatus)
    {
        //api_error
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.alertshowsuccess, null);
        TextView btnGoHome =customView.findViewById(R.id.btnGoHome);
        TextView titile=customView.findViewById(R.id.titile);
        TextView tvOrderStatus=customView.findViewById(R.id.tvOrderStatus);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(customView);
        titile.setText(titleher);
        btnGoHome.setText(buttonstatus);
        tvOrderStatus.setText(succesmsg);
        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.setCancelable(false);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("ARP Wallet");
        MainActivity.enableBackViews(true);
        MainActivity.bottomNavigation.setVisibility(View.VISIBLE);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    private void getARPTABHisotry()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        AppConfig.getLoadInterface().getARPHistory(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ARPHistoryModel>() {
            @Override
            public void onResponse(Call<ARPHistoryModel> call, Response<ARPHistoryModel> response) {
                pd.dismiss();
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                ARPListtab2=new ArrayList<>();
                                ARPListtab2.addAll(response.body().getData());
                                if(ARPListtab2.size()>0)
                                {
                                    ARPHistoryAdapter mAdapter = new ARPHistoryAdapter(ARPListtab2, getActivity());
                                    rvProjectRecycle.setAdapter(mAdapter);
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
                            ShowApiError(getActivity(),"server error in ARP");
                        }

                    } catch (Exception e)
                    {
                        if(pd.isShowing())
                            pd.dismiss();

                        e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(Call<ARPHistoryModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
    }

    private void getARPTABDirectReferral()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        AppConfig.getLoadInterface().getARPDirect(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ARPDirectModel>() {
            @Override
            public void onResponse(Call<ARPDirectModel> call, Response<ARPDirectModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            ARPListtab1=new ArrayList<>();
                            ARPListtab1.addAll(response.body().getData());
                            if(ARPListtab1.size()>0)
                            {
                                ARPAdapter mAdapter = new ARPAdapter(ARPListtab1, getActivity());
                                rvProjectRecycle.setAdapter(mAdapter);
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
                        ShowApiError(getActivity(),"server error in ARP");
                    }

                } catch (Exception e)
                {
                    if(pd.isShowing())
                        pd.dismiss();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ARPDirectModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
    }

    public void getARPTABRedeem(final Activity activity)
    {
        final ProgressDialog pd=new ProgressDialog(activity);
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        AppConfig.getLoadInterface().getARPRedeem(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ARPHistoryModel>() {
            @Override
            public void onResponse(Call<ARPHistoryModel> call, Response<ARPHistoryModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            ARPListtab3=new ArrayList<>();
                            ARPListtab3.addAll(response.body().getData());
                            if(ARPListtab3.size()>0)
                            {
                                ARPRedeemAdapter mAdapter = new ARPRedeemAdapter(ARPListtab3, activity);
                                rvProjectRecycle.setAdapter(mAdapter);
                            }else
                            {
                                Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            Toast.makeText(activity, "Data Not Available", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(activity,"server error in ARP");
                    }

                } catch (Exception e)
                {
                    if(pd.isShowing())
                        pd.dismiss();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ARPHistoryModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(activity,t.getMessage());
            }
        });

    }

}