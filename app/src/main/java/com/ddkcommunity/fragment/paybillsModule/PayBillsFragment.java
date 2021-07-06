package com.ddkcommunity.fragment.paybillsModule;


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
import com.ddkcommunity.fragment.paybillsModule.adapter.payBillBillerAdapter;
import com.ddkcommunity.fragment.paybillsModule.adapter.payBillCategoryAdapter;
import com.ddkcommunity.fragment.paybillsModule.models.addformModelBiller;
import com.ddkcommunity.fragment.paybillsModule.models.catModel;
import com.ddkcommunity.fragment.paybillsModule.models.dynamicFormModel;
import com.ddkcommunity.fragment.projects.BillerAllFragment;
import com.ddkcommunity.model.SliderWithType;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowServerPost;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayBillsFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    ViewPager viewPager;
    private HomeBannerPagerAdapter homeBannerPager;
    public ArrayList<SliderWithType.Datum> drawablesListpay;
    private int count = 0;
    LinearLayout btnallcate;
    //for dynamic
    private RecyclerView rvProjectRecyclecategory,rvProjectRecycleBiller;
    ArrayList<catModel.DataCategory> catlist;
    ArrayList<catModel.DataBiller> billerlist;
    View view=null;
    //for subitem
    public static ArrayList<addformModelBiller.Datum> arraylist;
    public static ArrayList<dynamicFormModel> viewList;

    public PayBillsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
        {
            view = inflater.inflate(R.layout.fragment_pay_bills, container, false);
            mContext = getActivity();
            viewPager = view.findViewById(R.id.viewPager);
            btnallcate = view.findViewById(R.id.btnallcate);
            btnallcate.setOnClickListener(this);
            rvProjectRecyclecategory = view.findViewById(R.id.rvProjectRecyclecategory);
            rvProjectRecycleBiller = view.findViewById(R.id.rvProjectRecycleBiller);
            //for cat
            int numberOfColumns = 4;
            rvProjectRecyclecategory.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
            rvProjectRecycleBiller.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
            getPaybillsCatData();
            getPayBills();
        }
        return view;
    }


    private void getPaybillsCatData()
    {

        AppConfig.getLoadInterface().getPayBillCat(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<catModel>() {
            @Override
            public void onResponse(Call<catModel> call, Response<catModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            catlist=new ArrayList<>();
                            billerlist=new ArrayList<>();
                            Log.d("chages data",response.toString());
                            billerlist.addAll(response.body().getDataBiller());
                            catlist.addAll(response.body().getDataCategory());
                            if(catlist.size()!=0)
                            {
                                catModel.DataCategory m1=new catModel.DataCategory();
                                m1.setCategoryName("more");
                                catlist.add(m1);
                                payBillCategoryAdapter mAdapter = new payBillCategoryAdapter(catlist,getActivity());
                                rvProjectRecyclecategory.setAdapter(mAdapter);
                            }

                            if(billerlist.size()!=0)
                            {
                                catModel.DataBiller m1=new catModel.DataBiller();
                                m1.setBillerName("more");
                                billerlist.add(m1);

                                payBillBillerAdapter mAdapter = new payBillBillerAdapter(billerlist,getActivity());
                                rvProjectRecycleBiller.setAdapter(mAdapter);
                            }
                            AppConfig.hideLoader();
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
            public void onFailure(Call<catModel> call, Throwable t)
            {
                AppConfig.hideLoader();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //for banner
    private void setBannerImages()
    {
        // The code below assumes that the root container has an id called 'main'
        homeBannerPager = new HomeBannerPagerAdapter(mContext, drawablesListpay);
        viewPager.setAdapter(homeBannerPager);
        changeViewPagerPage();
    }

    private void changeViewPagerPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count == drawablesListpay.size() - 1) {
                    count = 0;
                } else {
                    count++;
                }
                viewPager.setCurrentItem(count);
                changeViewPagerPage();
            }
        }, 5000);
    }


    private void getPayBills()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("banner_type", "paybills");
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
                                drawablesListpay = new ArrayList<>();
                                drawablesListpay.addAll(response.body().getData());
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
                pd.dismiss();
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
        MainActivity.setTitle("Pay Bill");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }
}