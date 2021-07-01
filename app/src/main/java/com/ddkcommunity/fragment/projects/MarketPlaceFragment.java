package com.ddkcommunity.fragment.projects;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.HomeBannerPagerAdapter;
import com.ddkcommunity.adapters.Marketoplaceadpater;
import com.ddkcommunity.model.SliderWithType;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketPlaceFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    ViewPager viewPager;
    private ArrayList<SliderWithType.Datum> drawablesListnew;
    private int count = 0;
    LinearLayout btnallcate;
    private ArrayList<smpdModelNew.Banner> BannerList1;
    RecyclerView rvProjectRecycle;

    public MarketPlaceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplaces, container, false);
        mContext = getActivity();
        viewPager=view.findViewById(R.id.viewPager);
        btnallcate=view.findViewById(R.id.btnallcate);
        btnallcate.setOnClickListener(this);
        getBannerKImages();
        BannerList1=new ArrayList<>();
        for(int i = 0; i< 4; i++) {
            smpdModelNew.Banner model1 = new smpdModelNew.Banner();
            model1.setImage("");
            BannerList1.add(model1);
        }
        rvProjectRecycle = view.findViewById(R.id.rvProjectRecycle);
         int numberOfColumns = 2;
        rvProjectRecycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        Marketoplaceadpater mAdapter = new Marketoplaceadpater(BannerList1,getActivity());
        rvProjectRecycle.setAdapter(mAdapter);

        return view;
    }

    //for banner
    private void setBannerImages()
    {
        // The code below assumes that the root container has an id called 'main'
        HomeBannerPagerAdapter homeBannerPager = new HomeBannerPagerAdapter(mContext, drawablesListnew);
        viewPager.setAdapter(homeBannerPager);
        changeViewPagerPage();
    }

    private void changeViewPagerPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count == drawablesListnew.size() - 1) {
                    count = 0;
                } else {
                    count++;
                }
                viewPager.setCurrentItem(count);
                changeViewPagerPage();
            }
        }, 3000);
    }

    private void getBannerKImages()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("banner_type", "market_place");
        AppConfig.getLoadInterface().getSlidersType(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<SliderWithType>() {
            @Override
            public void onResponse(Call<SliderWithType> call, Response<SliderWithType> response) {
                try {

                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            Log.d("chages data",response.toString());
                            if(response.body().getData()!=null)
                            {

                                drawablesListnew = new ArrayList<>();
                                drawablesListnew.addAll(response.body().getData());
                                setBannerImages();
                                //.........
                               pd.dismiss();
                            }
                        }else
                        {
                            pd.dismiss();
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(mContext,"server error sampd-company/company-list");
                    }

                } catch (Exception e) {
                    ShowApiError(mContext,"error in response sampd-company/company-list");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SliderWithType> call, Throwable t)
            {
                AppConfig.hideLoader();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //..
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnallcate:
                MainActivity.addFragment(new BillerAllFragment(), true);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.titleText.setVisibility(View.GONE);
        MainActivity.searchlayout.setVisibility(View.VISIBLE);
        MainActivity.prepareListData(getActivity(),"home");
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);

    }
}