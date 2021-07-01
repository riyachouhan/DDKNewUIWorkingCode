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
import com.ddkcommunity.fragment.mapmodule.adapter.FunnelAdapter;
import com.ddkcommunity.fragment.mapmodule.adapter.PhaseoneAdapter;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.fragment.mapmodule.model.phaseOneBonusModel;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class phaseonefragment extends Fragment
{

     View view;
     Context mContext;
     RecyclerView rvRecycle;
    private ArrayList<phaseOneBonusModel.Datum> phaseDAta;

    public phaseonefragment() {
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
        getPhaseOneBonus();
        return view;
    }


    private void getPhaseOneBonus()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<phaseOneBonusModel> call = AppConfig.getLoadInterfaceMap().getPhaseOneBonusData(maptoken);
        call.enqueue(new retrofit2.Callback<phaseOneBonusModel>() {
            @Override
            public void onResponse(Call<phaseOneBonusModel> call, Response<phaseOneBonusModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {
                                phaseDAta=new ArrayList<>();
                                phaseDAta.addAll(response.body().getData());
                                if(phaseDAta.size()>0) {
                                    PhaseoneAdapter mAdapter = new PhaseoneAdapter(phaseDAta, getActivity());
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
                            ShowApiError(mContext,"server error phase-one-bonus");
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
                    ShowApiError(getActivity(),"server error in phase-one-bonus");
                }
            }

            @Override
            public void onFailure(Call<phaseOneBonusModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Phase 1 Bonus");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.prepareListData(getActivity(),"map");
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

}
