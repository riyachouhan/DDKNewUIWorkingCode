package com.ddkcommunity.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.SFIOAdapter;
import com.ddkcommunity.adapters.activityAdapterv;
import com.ddkcommunity.adapters.sfiosubfrgamentadapter;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpater;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpaterright;
import com.ddkcommunity.fragment.mapmodule.model.groupModel;
import com.ddkcommunity.fragment.projects.TermsAndConsitionSubscription;
import com.ddkcommunity.model.activityModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.model.sfioSubPackageModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.ScalingUtilities;
import com.ddkcommunity.utilies.Utility;
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
     RecyclerView rvRecycle;
    private ArrayList<sfioModel.Datum> funnelDAta;
    public static SFIOAdapter mAdapter;

    public SFIOShowFragmement() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activityfragment, container, false);
        mContext = getActivity();
        addicon=view.findViewById(R.id.addicon);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        getActivitydata();
        addicon.setOnClickListener(this);
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

    private void getActivitydata()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        AppConfig.getLoadInterface().getSFIOData(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<sfioModel>() {
            @Override
            public void onResponse(Call<sfioModel> call, Response<sfioModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                            if (response.isSuccessful() && response.body() != null)
                            {
                                if (response.body().getStatus()==1)
                                {
                                    pd.dismiss();
                                    funnelDAta=new ArrayList<>();
                                    funnelDAta.addAll(response.body().getData());
                                    mAdapter = new SFIOAdapter(funnelDAta, mContext);
                                    rvRecycle.setAdapter(mAdapter);

                                }else
                                {
                                    pd.dismiss();
                                    Fragment fragment = new TermsAndConsitionSubscription();
                                    Bundle arg = new Bundle();
                                    arg.putString("activityaction", "subscriptionSFIO");
                                    fragment.setArguments(arg);
                                    MainActivity.addFragment(fragment,false);
//                                    Toast.makeText(mContext, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Log.d("context","::");
                                ShowApiError(mContext,"server error direct-bonus");
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
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
