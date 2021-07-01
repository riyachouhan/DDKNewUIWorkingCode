package com.ddkcommunity.fragment.distributationmodule;


import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.ComapnyImagesAdapter;
import com.ddkcommunity.fragment.distributationmodule.adapter.distributionAdapter;
import com.ddkcommunity.fragment.distributationmodule.adapter.distributionfrozenadapter;
import com.ddkcommunity.fragment.mapmodule.adapter.PhaseoneAdapter;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.model.smpdModelNew;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ditributionfragment extends Fragment implements View.OnClickListener
{
    private Context mContext;
    LinearLayout distributionlayout,frozenlayout;
    RecyclerView rvdistributionRecycle,rvProjectRecycle;
    private ArrayList<PollingHistoryTransction.PoolingHistoryData> distributionlist;

    public ditributionfragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.distributationlayout, container, false);
        mContext = getActivity();
        //..........
        distributionlayout=view.findViewById(R.id.distributionlayout);
        frozenlayout=view.findViewById(R.id.frozenlayout);
        rvdistributionRecycle= view.findViewById(R.id.rvdistributionRecycle);
        rvProjectRecycle= view.findViewById(R.id.rvProjectRecycle);
        //for recycle view
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvProjectRecycle.setLayoutManager(mLayoutManager);
        rvProjectRecycle.setItemAnimator(new DefaultItemAnimator());
        //..............
        TabLayout tabLayout = view.findViewById(R.id.profiletabs);
        tabLayout.addTab(tabLayout.newTab().setText("Distribution"));
        tabLayout.addTab(tabLayout.newTab().setText("Frozen"));
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }
        //..........
        distributionlayout.setVisibility(View.VISIBLE);
        frozenlayout.setVisibility(View.GONE);
        distributionlist=new ArrayList<>();
        for(int i=0;i<9;i++)
        {
            PollingHistoryTransction.PoolingHistoryData p1=new PollingHistoryTransction.PoolingHistoryData();
            if(i==0)
            {
                p1.setType("Rewards Incentives");
                p1.setPayment_status("150M");
                p1.setSender_email("43%");
            }else if(i==1)
            {
                p1.setType("For Sales");
                p1.setPayment_status("20M");
                p1.setSender_email("6%");
            }else if(i==2)
            {
                p1.setType("Referral Rewards Airdrop");
                p1.setPayment_status("8M");
                p1.setSender_email("2%");
            }else if(i==3)
            {
                p1.setType("Management Allocation");
                p1.setPayment_status("15M");
                p1.setSender_email("8%");
            }else if(i==4)
            {
                p1.setType("SAM Foundation");
                p1.setPayment_status("38M");
                p1.setSender_email("11%");
            }else if(i==5)
            {
                p1.setType("R&D");
                p1.setPayment_status("25M");
                p1.setSender_email("7%");
            }else if(i==6)
            {
                p1.setType("Airdrop Reload");
                p1.setPayment_status("11M");
                p1.setSender_email("3%");
            }else if(i==7)
            {
                p1.setType("Expansion");
                p1.setPayment_status("83M");
                p1.setSender_email("24%");
            }else
            {
                p1.setType("Total");
                p1.setPayment_status("350M");
                p1.setSender_email("100%");
            }
            distributionlist.add(p1);
        }
        distributionAdapter mAdapter = new distributionAdapter(distributionlist, getActivity());
        rvdistributionRecycle.setAdapter(mAdapter);
        //..............
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (tab.getPosition() == 0)
                {
                    distributionlayout.setVisibility(View.VISIBLE);
                    frozenlayout.setVisibility(View.GONE);
                    distributionlist=new ArrayList<>();
                    for(int i=0;i<9;i++)
                    {
                        PollingHistoryTransction.PoolingHistoryData p1=new PollingHistoryTransction.PoolingHistoryData();
                        if(i==0)
                        {
                            p1.setType("Rewards Incentives");
                            p1.setPayment_status("150M");
                            p1.setSender_email("43%");
                        }else if(i==1)
                        {
                            p1.setType("For Sales");
                            p1.setPayment_status("20M");
                            p1.setSender_email("6%");
                        }else if(i==2)
                        {
                            p1.setType("Referral Rewards Airdrop");
                            p1.setPayment_status("8M");
                            p1.setSender_email("2%");
                        }else if(i==3)
                        {
                            p1.setType("Management Allocation");
                            p1.setPayment_status("15M");
                            p1.setSender_email("8%");
                        }else if(i==4)
                        {
                            p1.setType("SAM Foundation");
                            p1.setPayment_status("38M");
                            p1.setSender_email("11%");
                        }else if(i==5)
                        {
                            p1.setType("R&D");
                            p1.setPayment_status("25M");
                            p1.setSender_email("7%");
                        }else if(i==6)
                        {
                            p1.setType("Airdrop Reload");
                            p1.setPayment_status("11M");
                            p1.setSender_email("3%");
                        }else if(i==7)
                        {
                            p1.setType("Expansion");
                            p1.setPayment_status("83M");
                            p1.setSender_email("24%");
                        }else
                        {
                            p1.setType("Total");
                            p1.setPayment_status("350M");
                            p1.setSender_email("100%");
                        }
                        distributionlist.add(p1);
                    }
                    distributionAdapter mAdapter = new distributionAdapter(distributionlist, getActivity());
                    rvdistributionRecycle.setAdapter(mAdapter);

                } else if (tab.getPosition() == 1)
                {
                    distributionlayout.setVisibility(View.GONE);
                    frozenlayout.setVisibility(View.VISIBLE);
                    rvProjectRecycle.setVisibility(View.VISIBLE);
                    //......
                    distributionlist=new ArrayList<>();
                    for(int i=0;i<6;i++)
                    {
                        PollingHistoryTransction.PoolingHistoryData p1=new PollingHistoryTransction.PoolingHistoryData();
                        p1.setType(i+"");
                        distributionlist.add(p1);
                    }
                    distributionfrozenadapter mAdapter = new distributionfrozenadapter(distributionlist, getActivity());
                    rvProjectRecycle.setAdapter(mAdapter);
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Distribution");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.searchlayout.setVisibility(View.GONE);
        MainActivity.prepareListData(getActivity(),"home");

    }
}