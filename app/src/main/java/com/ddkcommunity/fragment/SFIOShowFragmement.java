package com.ddkcommunity.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.SFIOAdapter;
import com.ddkcommunity.adapters.SfioHeaderAdapter;
import com.ddkcommunity.adapters.activityAdapterv;
import com.ddkcommunity.adapters.sfiosubfrgamentadapter;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpater;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpaterright;
import com.ddkcommunity.fragment.mapmodule.model.groupModel;
import com.ddkcommunity.fragment.projects.TermsAndConsitionSubscription;
import com.ddkcommunity.fragment.settingModule.ARPFragement;
import com.ddkcommunity.fragment.settingModule.ARPWalletBalanceModel;
import com.ddkcommunity.fragment.settingModule.OwnerShipFragment;
import com.ddkcommunity.fragment.settingModule.editOwnershipFragment;
import com.ddkcommunity.model.activityModel;
import com.ddkcommunity.model.arpstausModel;
import com.ddkcommunity.model.referalRequestsendModel;
import com.ddkcommunity.model.sfioHeaderModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.model.sfioSubPackageModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.ScalingUtilities;
import com.ddkcommunity.utilies.Utility;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class SFIOShowFragmement extends Fragment implements View.OnClickListener
{
     LinearLayout addicon;
     View view;
     Context mContext;
     RecyclerView rvRecycle,rvRecycleher;
    private ArrayList<sfioModel.Datum> funnelDAta;
    public static SFIOAdapter mAdapter;
    CircleProgressBar custom_progress_outer;
    CircleImageView imgprofile;
    int bankonesttaus=0;
    TextView name_TV,email_TV,totalpoint;
    public static SfioHeaderAdapter mAdapterher;
    LinearLayout applyforarp,upperheader;
    TextView seelall,appliedarp;
    SwipeRefreshLayout swiperefresh_items;
    LinearLayout ownershiplay,mainlayout;
    ProgressDialog pd;

    public SFIOShowFragmement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.sfioactivityfragment, container, false);
        mContext = getActivity();
        mainlayout=view.findViewById(R.id.mainlayout);
        ownershiplay=view.findViewById(R.id.ownershiplay);
        applyforarp=view.findViewById(R.id.applyforarp);
        appliedarp=view.findViewById(R.id.appliedarp);
        swiperefresh_items=view.findViewById(R.id.swiperefresh_items);
        seelall=view.findViewById(R.id.seelall);
        String udata="See All..";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        seelall.setText(content);
        upperheader=view.findViewById(R.id.upperheader);
        totalpoint=view.findViewById(R.id.totalpoint);
        name_TV=view.findViewById(R.id.name_TV);
        email_TV=view.findViewById(R.id.email_TV);
        imgprofile=view.findViewById(R.id.imgprofile);
        addicon=view.findViewById(R.id.addicon);
        rvRecycleher=view.findViewById(R.id.rvRecycleher);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        rvRecycleher.setLayoutManager(mLayoutManager1);
        rvRecycleher.setItemAnimator(new DefaultItemAnimator());
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        getArpBalance();
        pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        getCurrentBonusSfio();
        addicon.setOnClickListener(this);
        custom_progress_outer=view.findViewById(R.id.custom_progress_outer);
        custom_progress_outer.setProgress(100);
        //.........
        name_TV.setText(MainActivity.userData.getUser().getName());
        email_TV.setText(MainActivity.userData.getUser().getEmail());

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

        seelall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SFIOSubFRagment();
                Bundle arg = new Bundle();
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, true);
            }
        });

        swiperefresh_items.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to make your refresh action
                // CallYourRefreshingMethod();
                getArpBalance();
                getCurrentBonusSfio();
               // getActivitydata("no");

            }
        });

        applyforarp.setOnClickListener(new View.OnClickListener()
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

        ownershiplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Fragment fragment = new OwnerShipFragment();
                Bundle arg = new Bundle();
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, true);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle(" SFIO ");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.prepareListData(getActivity(),"home");
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addicon:
                //MainActivity.addFragment(new AddActivityFragemnt(), false);
                Fragment fragment = new TermsAndConsitionSubscription();
                Bundle arg = new Bundle();
                arg.putString("activityaction", "subscriptionSFIO");
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment,true);
                break;
        }
    }

    private void getActivitydata(final ProgressDialog pd)
    {
        AppConfig.getLoadInterface().getSFIOData(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<sfioModel>() {
            @Override
            public void onResponse(Call<sfioModel> call, Response<sfioModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                            if (response.isSuccessful() && response.body() != null)
                            {
                                if(pd.isShowing())
                                    pd.dismiss();
                                if (response.body().getStatus()==1)
                                {
                                    mainlayout.setVisibility(View.VISIBLE);

                                    funnelDAta=new ArrayList<>();
                                    funnelDAta.addAll(response.body().getData());
                                    mAdapter = new SFIOAdapter(funnelDAta, mContext);
                                    rvRecycle.setAdapter(mAdapter);
                                }else
                                {
                                    if(bankonesttaus!=1) {
                                        Fragment fragment = new TermsAndConsitionSubscription();
                                        Bundle arg = new Bundle();
                                        arg.putString("activityaction", "subscriptionSFIO");
                                        fragment.setArguments(arg);
                                        MainActivity.addFragment(fragment, false);
                                        mainlayout.setVisibility(View.GONE);

                                    }else
                                    {
                                        mainlayout.setVisibility(View.VISIBLE);
                                    }
                                }
                                if(swiperefresh_items.isRefreshing()) {
                                    swiperefresh_items.setRefreshing(false);
                                }
                            } else {
                                Log.d("context","::");
                                ShowApiError(mContext,"server error direct-bonus");
                                if(swiperefresh_items.isRefreshing()) {
                                    swiperefresh_items.setRefreshing(false);
                                }
                            }

                } catch (Exception e) {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<sfioModel> call, Throwable t)
            {
                if(pd.isShowing())
                {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCurrentBonusSfio()
    {
        AppConfig.getLoadInterface().getSumSubsciption(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<sfioHeaderModel>() {
            @Override
            public void onResponse(Call<sfioHeaderModel> call, Response<sfioHeaderModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            String pointsvaleu=response.body().getData().getPoints();
                            totalpoint.setText(pointsvaleu+"");
                            ArrayList<sfioHeaderModel.BankDeposit> funnelDAtasf=new ArrayList<>();
                            funnelDAtasf.addAll(response.body().getData().getBankDeposit());

                            if(funnelDAtasf.size()>2)
                            {
                                seelall.setVisibility(View.VISIBLE);
                                ArrayList<sfioHeaderModel.BankDeposit> funnelDAtasfnew=new ArrayList<>();
                                for(int i=0;i<2;i++)
                                {
                                    funnelDAtasfnew.add(funnelDAtasf.get(i));
                                }
                                mAdapterher = new SfioHeaderAdapter(funnelDAtasfnew, mContext);
                                rvRecycleher.setAdapter(mAdapterher);
                                bankonesttaus=1;
                            }else
                            {
                                seelall.setVisibility(View.GONE);
                                mAdapterher = new SfioHeaderAdapter(funnelDAtasf, mContext);
                                rvRecycleher.setAdapter(mAdapterher);
                                if(funnelDAtasf.size()!=0)
                                {
                                    bankonesttaus=1;
                                }
                            }
                            upperheader.setVisibility(View.VISIBLE);
                            getActivitydata(pd);

                        }else
                        {
                            upperheader.setVisibility(View.VISIBLE);
                            seelall.setVisibility(View.GONE);
                            pd.dismiss();
                        }

                    } else {
                        Log.d("context","::");
                        upperheader.setVisibility(View.VISIBLE);
                        ShowApiError(mContext,"server error direct-bonus");
                        pd.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<sfioHeaderModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
}
