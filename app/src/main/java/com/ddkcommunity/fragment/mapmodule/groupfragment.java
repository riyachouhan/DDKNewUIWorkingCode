package com.ddkcommunity.fragment.mapmodule;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.ddkcommunity.adapters.Marketoplaceadpater;
import com.ddkcommunity.fragment.mapmodule.adapter.FunnelAdapter;
import com.ddkcommunity.fragment.mapmodule.adapter.GroupbonusAdpater;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpater;
import com.ddkcommunity.fragment.mapmodule.adapter.grouptleveleadpaterright;
import com.ddkcommunity.fragment.mapmodule.model.groupModel;
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
public class groupfragment extends Fragment
{

     View view;
     Context mContext;
     int currentgroup=1;
     RecyclerView rvRecycleRight,rvRecycleleft;
     TextView tvlevel,leftbalance,rightbalance,rightuser,leftuesr;
     ImageView  rightside,leftside;

    public groupfragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.groupfragment, container, false);
        mContext = getActivity();
        tvlevel=view.findViewById(R.id.tvlevel);
        rightside=view.findViewById(R.id.rightsiede);
        leftside=view.findViewById(R.id.leftside);
        leftbalance=view.findViewById(R.id.leftbalance);
        rightbalance=view.findViewById(R.id.rightbalance);
        rightuser=view.findViewById(R.id.rightuser);
        leftuesr=view.findViewById(R.id.leftuesr);
        rvRecycleRight=view.findViewById(R.id.rvRecycleRight);
        rvRecycleleft=view.findViewById(R.id.rvRecycleleft);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycleRight.setLayoutManager(mLayoutManager);
        rvRecycleRight.setItemAnimator(new DefaultItemAnimator());
        int numberOfColumns = 1;

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        rvRecycleleft.setLayoutManager(mLayoutManager1);
        rvRecycleleft.setItemAnimator(new DefaultItemAnimator());
        rvRecycleleft.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        tvlevel.setText("Level "+currentgroup);
        getGroup(currentgroup);

        rightside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                currentgroup=currentgroup+1;
                tvlevel.setText("Level "+currentgroup);
                getGroup(currentgroup);
            }
        });

        leftside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(currentgroup>1) {
                    currentgroup = currentgroup - 1;
                    getGroup(currentgroup);
                }else
                {
                    getGroup(currentgroup);
                }
                tvlevel.setText("Level "+currentgroup);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Group");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    private void getGroup(int currentgroup)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<groupModel> call = AppConfig.getLoadInterfaceMap().getGroup(maptoken,currentgroup+"");
        call.enqueue(new retrofit2.Callback<groupModel>() {
            @Override
            public void onResponse(Call<groupModel> call, Response<groupModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            ArrayList<groupModel.LeftGroup> lefgtGroupArraylist=new ArrayList<>();
                            ArrayList<groupModel.RightGroup> rightGroupArraylist=new ArrayList<>();

                            if (response.body().getStatus()==1)
                            {
                                leftbalance.setText("Left Balance:"+response.body().getLeftBalance());
                                rightbalance.setText("Right Balance:"+response.body().getRightBalance());
                                rightuser.setText("Right User:"+response.body().getRightCountUser());
                                leftuesr.setText("Left User:"+response.body().getLeftCountUser());

                                if(response.body().getLeftGroup().size()>0 || response.body().getRightGroup().size()>0)
                                {
                                    lefgtGroupArraylist.addAll(response.body().getLeftGroup());
                                    rightGroupArraylist.addAll(response.body().getRightGroup());

                                        grouptleveleadpater mAdapter = new grouptleveleadpater(lefgtGroupArraylist,getActivity());
                                        rvRecycleleft.setAdapter(mAdapter);

                                        grouptleveleadpaterright mAdapter1 = new grouptleveleadpaterright(rightGroupArraylist,getActivity());
                                        rvRecycleRight.setAdapter(mAdapter1);

                                }else
                                {
                                    grouptleveleadpater mAdapter = new grouptleveleadpater(lefgtGroupArraylist,getActivity());
                                    rvRecycleleft.setAdapter(mAdapter);

                                    grouptleveleadpaterright mAdapter1 = new grouptleveleadpaterright(rightGroupArraylist,getActivity());
                                    rvRecycleRight.setAdapter(mAdapter1);

                                    Toast.makeText(mContext, "Data Not Available", Toast.LENGTH_SHORT).show();
                                }
                            }else
                            {
                                grouptleveleadpater mAdapter = new grouptleveleadpater(lefgtGroupArraylist,getActivity());
                                rvRecycleleft.setAdapter(mAdapter);
                                grouptleveleadpaterright mAdapter1 = new grouptleveleadpaterright(rightGroupArraylist,getActivity());
                                rvRecycleRight.setAdapter(mAdapter1);
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
            public void onFailure(Call<groupModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

}
