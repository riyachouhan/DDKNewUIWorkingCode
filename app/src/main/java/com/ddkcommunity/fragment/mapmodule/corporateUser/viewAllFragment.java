package com.ddkcommunity.fragment.mapmodule.corporateUser;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.ddkcommunity.fragment.mapmodule.adapter.FunnelAdapter;
import com.ddkcommunity.fragment.mapmodule.adapter.corporateUserDashboarAdapter;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.model.activityModel;
import com.ddkcommunity.utilies.AppConfig;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class viewAllFragment extends Fragment implements View.OnClickListener
{

     View view;
     Context mContext;
     RecyclerView rvRecycle;
    LinearLayout addicon;
    private ArrayList<activityModel.Datum> funnelDAta;

    public viewAllFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.funnel_fragment, container, false);
        mContext = getActivity();
        addicon=view.findViewById(R.id.addicon);
        String[] str = {"  All  ", "Active", "Inactive", "Expiring"};
        List<String> titles = Arrays.asList(str);
        MyTabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setTitle(titles);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                //........
                funnelDAta=new ArrayList<>();
                corporateUserDashboarAdapter mAdapter = new corporateUserDashboarAdapter(funnelDAta, getActivity());
                rvRecycle.setAdapter(mAdapter);

                if (tab.getPosition() == 0)
                {
                    getCorporateUser("all");
                }else if (tab.getPosition() == 1)
                {
                    getCorporateUser("active");
                }else if (tab.getPosition() == 2)
                {
                    getCorporateUser("inactive");
                }else if (tab.getPosition() == 3)
                {
                    getCorporateUser("expiring");
                }
                //............
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                Log.d("Selected", "");
            }
        });

        getCorporateUser("all");
        addicon.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addicon:
                MainActivity.addFragment(new addLinkNewAccount(), true);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Manage Your Accounts");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void getCorporateUser(String tabname)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        AppConfig.getLoadInterface().mapActivityList(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<activityModel>() {
            @Override
            public void onResponse(Call<activityModel> call, Response<activityModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            funnelDAta=new ArrayList<>();
                            funnelDAta.addAll(response.body().getData());
                            corporateUserDashboarAdapter mAdapter = new corporateUserDashboarAdapter(funnelDAta, getActivity());
                            rvRecycle.setAdapter(mAdapter);

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(getContext(),"server error check-mail-exist");
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
            public void onFailure(Call<activityModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
