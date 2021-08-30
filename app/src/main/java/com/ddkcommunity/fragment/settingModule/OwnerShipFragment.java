package com.ddkcommunity.fragment.settingModule;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.ARPAdapter;
import com.ddkcommunity.adapters.ARPHistoryAdapter;
import com.ddkcommunity.adapters.ARPRedeemAdapter;
import com.ddkcommunity.adapters.ownershipAdapter;
import com.ddkcommunity.fragment.distributationmodule.adapter.distributionAdapter;
import com.ddkcommunity.model.PollingHistoryTransction;
import com.ddkcommunity.utilies.AppConfig;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerShipFragment extends Fragment
{
    private Context mContext;
    RecyclerView rvProjectRecycle;
    ArrayList<ownershipListShowModel.Ownership> Ownerlist;
    TextView setdefault,edit, herdername,tvaddress;

    public OwnerShipFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ownershipfragmentlayout, container, false);
        mContext = getActivity();
        herdername=view.findViewById(R.id.herdername);
        tvaddress=view.findViewById(R.id.tvaddress);
        setdefault=view.findViewById(R.id.setdefault);
        edit=view.findViewById(R.id.edit);
         //..........
        String udata="Set as default";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        setdefault.setText(content);
        setdefault.setVisibility(View.GONE);

        String udata1="Edit";
        SpannableString content1 = new SpannableString(udata1);
        content1.setSpan(new UnderlineSpan(), 0, udata1.length(), 0);
        edit.setText(content1);

        rvProjectRecycle = view.findViewById(R.id.rvProjectRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvProjectRecycle.setLayoutManager(mLayoutManager);
        rvProjectRecycle.setItemAnimator(new DefaultItemAnimator());
        getOwnerList();
        setdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new editOwnershipByUserFragment();
                Bundle arg = new Bundle();
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, false);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Ownership");
        MainActivity.enableBackViews(true);
        MainActivity.bottomNavigation.setVisibility(View.VISIBLE);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    private void getOwnerList()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        AppConfig.getLoadInterface().getOwnershipList(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ownershipListShowModel>() {
            @Override
            public void onResponse(Call<ownershipListShowModel> call, Response<ownershipListShowModel> response) {
                pd.dismiss();
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                String ownershipname=response.body().getData().getOwnershipData().getName();
                                String ownershipconatct=response.body().getData().getOwnershipData().getContact();
                                String ownershipaddress=response.body().getData().getOwnershipData().getAddress();

                                herdername.setText(ownershipname+" | "+ownershipconatct);
                                tvaddress.setText(ownershipaddress+"");

                                Ownerlist=new ArrayList<>();
                                Ownerlist.addAll(response.body().getData().getOwnershipList());
                                if(Ownerlist.size()>0)
                                {
                                    ownershipAdapter mAdapter = new ownershipAdapter(Ownerlist, getActivity());
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
            public void onFailure(Call<ownershipListShowModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

}