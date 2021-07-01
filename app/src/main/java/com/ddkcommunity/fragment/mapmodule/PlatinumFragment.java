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
import com.ddkcommunity.fragment.mapmodule.MyTabLayout;
import com.ddkcommunity.fragment.mapmodule.adapter.powerxmainfragmentAdapter;
import com.ddkcommunity.fragment.mapmodule.model.powerOfXModel;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlatinumFragment extends Fragment
{

     View view;
     Context mContext;
     RecyclerView rvRecycle;
    private ArrayList<powerOfXModel.Datum> funnelDAta;
    String fragmenttype;

    public PlatinumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.funnel_fragment, container, false);
        mContext = getActivity();
        if (getArguments().getString("fragmenttype") != null) {
            fragmenttype= getArguments().getString("fragmenttype");
        }
        MyTabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setVisibility(View.GONE);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        if(fragmenttype.equalsIgnoreCase("platinum")) {
            getPlatinumData();
        }else
        {
            getTitanumData();
        }
        return view;
    }

    private void getPlatinumData()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<powerOfXModel> call = AppConfig.getLoadInterfaceMap().getPlatinumBonus(maptoken);
        call.enqueue(new retrofit2.Callback<powerOfXModel>() {
            @Override
            public void onResponse(Call<powerOfXModel> call, Response<powerOfXModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {
                                funnelDAta=new ArrayList<>();
                                funnelDAta.addAll(response.body().getData());
                                if(funnelDAta.size()>0)
                                {
                                    powerxmainfragmentAdapter mAdapter = new powerxmainfragmentAdapter(fragmenttype,funnelDAta, getActivity());
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
            public void onFailure(Call<powerOfXModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }


    private void getTitanumData()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken= App.pref.getString(Constant.MAPToken, "");
        Call<powerOfXModel> call = AppConfig.getLoadInterfaceMap().getTitaniumBonus(maptoken);
        call.enqueue(new retrofit2.Callback<powerOfXModel>() {
            @Override
            public void onResponse(Call<powerOfXModel> call, Response<powerOfXModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus()==1)
                            {
                                funnelDAta=new ArrayList<>();
                                funnelDAta.addAll(response.body().getData());
                                if(funnelDAta.size()>0)
                                {
                                    powerxmainfragmentAdapter mAdapter = new powerxmainfragmentAdapter(fragmenttype,funnelDAta, getActivity());
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
            public void onFailure(Call<powerOfXModel> call, Throwable t) {
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
        if(fragmenttype.equalsIgnoreCase("platinum"))
        {
            MainActivity.setTitle("Platinum");
        }else
        {
            MainActivity.setTitle("Titanium");
        }
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.prepareListData(getActivity(),"map");
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

}
