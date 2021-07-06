package com.ddkcommunity.fragment.SAMPD;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.LoadInterface;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.CashoutFragmenAdapter;
import com.ddkcommunity.adapters.CredentialAdapter;
import com.ddkcommunity.adapters.HomeBannerPagerAdapter;
import com.ddkcommunity.adapters.SAMPDAdapter;
import com.ddkcommunity.adapters.SMPDBannerPagerAdapter;
import com.ddkcommunity.adapters.SampdAdpterNew;
import com.ddkcommunity.adapters.mapoptionadapter;
import com.ddkcommunity.adapters.subscriptonAdapter;
import com.ddkcommunity.fragment.CashOutFragmentNew;
import com.ddkcommunity.fragment.HomeFragment;
import com.ddkcommunity.fragment.SAMPDShowProject;
import com.ddkcommunity.fragment.projects.TermsAndConsitionSubscription;
import com.ddkcommunity.model.SAMPDModel;
import com.ddkcommunity.model.SliderImg;
import com.ddkcommunity.model.SliderImgResponse;
import com.ddkcommunity.model.UserBankList;
import com.ddkcommunity.model.UserBankListDetails;
import com.ddkcommunity.model.mapoptionmodel;
import com.ddkcommunity.model.mazigneModel;
import com.ddkcommunity.model.samModel;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.utilies.AppConfig;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowSAMPDDialog;
import static com.ddkcommunity.utilies.dataPutMethods.ShowServerPost;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class SAMPDNewFragment extends Fragment implements View.OnClickListener
{
    private ArrayList<smpdModelNew.Datum> SAMPDList;
    private ArrayList<smpdModelNew.Favourite> FavoritesList;
    private ArrayList<smpdModelNew.Banner> BannerList;
    private RecyclerView rvProjectRecycle,recyclerview1,recyclerviewsubc;
    private Context mContext;
    ViewPager viewPager;
    private SMPDBannerPagerAdapter homeBannerPager;
    private int count = 0;
    TextView myfav;
    View view1 = null;

    public SAMPDNewFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        if (view1 == null)
        {
            view1 = inflater.inflate(R.layout.fragment_smpd_new, container, false);
            mContext = getActivity();
            myfav=view1.findViewById(R.id.myfav);
            viewPager=view1.findViewById(R.id.viewPager);
            recyclerview1= view1.findViewById(R.id.recyclerview1);
            rvProjectRecycle = view1.findViewById(R.id.rvProjectRecycle);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerview1.setLayoutManager(layoutManager);
            recyclerview1.setItemAnimator(new DefaultItemAnimator());
            int numberOfColumns = 3;
            rvProjectRecycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
            //for subscrition
            recyclerviewsubc=view1.findViewById(R.id.recyclerviewsubc);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerviewsubc.setLayoutManager(layoutManager1);
            recyclerviewsubc.setItemAnimator(new DefaultItemAnimator());
            ArrayList<mapoptionmodel> mapoptionList=new ArrayList<>();
            mapoptionList.add(new mapoptionmodel("SFIO",R.drawable.sfiologo));
            mapoptionList.add(new mapoptionmodel("Kinerja PAy",R.drawable.kinerjapay));
            mapoptionList.add(new mapoptionmodel("iiRide",R.drawable.iiride));
            subscriptonAdapter allTypeCashoutFragmentAdapter = new subscriptonAdapter("","main",mapoptionList, getActivity());
            recyclerviewsubc.setAdapter(allTypeCashoutFragmentAdapter);
            getActiveSubscriptionStatus();
        }
        return view1;
    }

    //for banner
    private void setBannerImages()
    {
        // The code below assumes that the root container has an id called 'main'
        homeBannerPager = new SMPDBannerPagerAdapter(mContext, BannerList);
        viewPager.setAdapter(homeBannerPager);
        changeViewPagerPage();
    }

    private void changeViewPagerPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count == BannerList.size() - 1) {
                    count = 0;
                } else {
                    count++;
                }
                viewPager.setCurrentItem(count);
                changeViewPagerPage();
            }
        }, 3000);
    }

    private void getSMPDListData()
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("search", "");
        hm.put("home_screen", "1");
        hm.put("order_by", "sorting");
        AppConfig.getLoadInterface().getSMPDLIST(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<smpdModelNew>() {
            @Override
            public void onResponse(Call<smpdModelNew> call, Response<smpdModelNew> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getStatus() == 1)
                        {
                            Log.d("chages data",response.toString());
                            if(response.body().getData()!=null)
                            {
                                myfav.setText("My Favourites");
                                SAMPDList = new ArrayList<>();
                                SAMPDList.addAll(response.body().getData());
                                if(SAMPDList.size()!=0)
                                {

                                    SAMPDAdapter mAdapter = new SAMPDAdapter(SAMPDList,getActivity());
                                    rvProjectRecycle.setAdapter(mAdapter);
                                }
                                //for faveroties
                                FavoritesList=new ArrayList<>();
                                FavoritesList.addAll(response.body().getFavourites());
                                smpdModelNew.Favourite userBankList = new smpdModelNew.Favourite();
                                smpdModelNew.SampdCompany sm=new smpdModelNew.SampdCompany();
                                sm.setName("Addoption");
                                userBankList.setSampdCompany(sm);
                                FavoritesList.add(userBankList);

                                if(FavoritesList.size()>0)
                                {
                                    SampdAdpterNew mAdapter = new SampdAdpterNew(FavoritesList, getActivity());
                                    recyclerview1.setAdapter(mAdapter);
                                }
                                //.....
                                BannerList=new ArrayList<>();
                                BannerList.addAll(response.body().getBanners());
                                setBannerImages();
                                //.........
                                AppConfig.hideLoader();

                            }
                        }else if (response.body().getStatus() == 4)
                        {
                            AppConfig.hideLoader();
                            ShowServerPost(getActivity(),"server error sampd-company/company-list");
                        }else
                        {
                            AppConfig.hideLoader();
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        AppConfig.hideLoader();
                        ShowApiError(mContext,"server error sampd-company/company-list");
                    }

                } catch (Exception e) {
                    AppConfig.hideLoader();
                    ShowApiError(mContext,"error in response sampd-company/company-list");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<smpdModelNew> call, Throwable t)
            {
                AppConfig.hideLoader();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getActiveSubscriptionStatus()
    {
        AppConfig.showLoading("Loading...", mContext);
        AppConfig.getLoadInterface().getActivteSubscriptionStatus(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<SAMPDModel>() {
            @Override
            public void onResponse(Call<SAMPDModel> call, Response<SAMPDModel> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    if (response.body().getStatus() == 1)
                    {
                        getSMPDListData();

                    }else
                    {
                        AppConfig.hideLoader();
                        ShowSAMPDDialog(getActivity(),response.body().getMsg());
                    }
                } else
                {
                    AppConfig.hideLoader();
                    ShowApiError(getActivity(),"server error in all_transactions/check-active-subscription-user");
                }
            }

            @Override
            public void onFailure(Call<SAMPDModel> call, Throwable t) {
                AppConfig.hideLoader();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
    }

    public static void switchActivty()
    {
        Fragment fragment = new TermsAndConsitionSubscription();
        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        MainActivity.addFragment(fragment, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Project Development");
        MainActivity.enableBackViews(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
        }

    }
}
