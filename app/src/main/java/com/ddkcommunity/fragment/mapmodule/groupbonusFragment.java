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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.mapmodule.adapter.GroupbonusAdpater;
import com.ddkcommunity.fragment.mapmodule.adapter.OverflowAdapter;
import com.ddkcommunity.fragment.mapmodule.adapter.dailybonusadpater;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpater;
import com.ddkcommunity.fragment.mapmodule.model.GroupBonusModel;
import com.ddkcommunity.fragment.mapmodule.model.overFlowModel;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.model.smpdModelNew;
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
public class groupbonusFragment extends Fragment
{

    View view;
    Context mContext;
    RecyclerView rvRecycle;

    public groupbonusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.funnel_fragment, container, false);
        mContext = getActivity();
        MyTabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.GONE);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        getGroupBonus();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Group Bonus");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        //MainActivity.showHideIteam("map");
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void getGroupBonus()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<GroupBonusModel> call = AppConfig.getLoadInterfaceMap().getGroupBonus(maptoken);
        call.enqueue(new retrofit2.Callback<GroupBonusModel>() {
            @Override
            public void onResponse(Call<GroupBonusModel> call, Response<GroupBonusModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {
                                ArrayList<GroupBonusModel.Datum> funnelDAta=new ArrayList<>();
                                funnelDAta.addAll(response.body().getData());
                                if(funnelDAta.size()>0)
                                {
                                    GroupbonusAdpater mAdapter = new GroupbonusAdpater(funnelDAta, getActivity());
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
            public void onFailure(Call<GroupBonusModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

}
