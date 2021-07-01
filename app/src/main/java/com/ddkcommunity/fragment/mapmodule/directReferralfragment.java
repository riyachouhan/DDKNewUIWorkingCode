package com.ddkcommunity.fragment.mapmodule;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.mapmodule.adapter.DirectReferralAdapter;
import com.ddkcommunity.fragment.mapmodule.adapter.PhaseoneAdapter;
import com.ddkcommunity.fragment.mapmodule.model.directReferralModel;
import com.ddkcommunity.fragment.mapmodule.model.phaseOneBonusModel;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class directReferralfragment extends Fragment
{

     View view;
     Context mContext;
     RecyclerView rvRecycle;
    private ArrayList<directReferralModel.Datum> directReferralDAta;

    public directReferralfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.phaseonefragment, container, false);
        mContext = getActivity();
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        getDirectReferralBonus();
        return view;
    }

    private void getDirectReferralBonus()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<directReferralModel> call = AppConfig.getLoadInterfaceMap().getDirectbonus(maptoken);
        call.enqueue(new retrofit2.Callback<directReferralModel>() {
            @Override
            public void onResponse(Call<directReferralModel> call, Response<directReferralModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {
                                directReferralDAta=new ArrayList<>();
                                directReferralDAta.addAll(response.body().getData());
                                if(directReferralDAta.size()>0) {
                                    DirectReferralAdapter mAdapter = new DirectReferralAdapter(directReferralDAta, getActivity());
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
                            ShowApiError(mContext,"server error direct-bonus");
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
                    ShowApiError(getActivity(),"server error in direct-bonus");
                }
            }

            @Override
            public void onFailure(Call<directReferralModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Direct Referral Bonus");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.prepareListData(getActivity(),"map");
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

}
