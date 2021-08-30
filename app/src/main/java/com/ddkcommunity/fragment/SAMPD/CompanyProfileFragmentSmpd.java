package com.ddkcommunity.fragment.SAMPD;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.ComapnyImagesAdapter;
import com.ddkcommunity.adapters.SAMPDAdapter;
import com.ddkcommunity.adapters.addsampdfrgae;
import com.ddkcommunity.model.BankList;
import com.ddkcommunity.model.SAMPDModel;
import com.ddkcommunity.model.SMPDCompanyDetailsModel;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.utilies.AppConfig;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyProfileFragmentSmpd extends Fragment implements View.OnClickListener
{
    private Context mContext;
    RecyclerView rvProjectRecycle;
    TextView  companyname,countryname;
    WebView contactview,profilelay,productview;
    String company_id;
    ImageView ivCompanyImg;
    String thirdviewstatus="";
    ArrayList<SMPDCompanyDetailsModel.SampdCompanyService> SAMPDList;

    public CompanyProfileFragmentSmpd()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.comapnysmpdprofile, container, false);
        mContext = getActivity();
        productview=view.findViewById(R.id.productview);
        ivCompanyImg=view.findViewById(R.id.ivCompanyImg);
        companyname=view.findViewById(R.id.companyname);
        countryname=view.findViewById(R.id.countryname);
        contactview=view.findViewById(R.id.contactview);
        profilelay=view.findViewById(R.id.profilelay);
        if (getArguments().getString("id") != null) {
            company_id= getArguments().getString("id");
        }
        //..........
        getCompanyDetails(company_id);
        rvProjectRecycle = view.findViewById(R.id.rvProjectRecycle);
        int numberOfColumns = 3;
        rvProjectRecycle.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        profilelay.setVisibility(View.VISIBLE);
        contactview.setVisibility(View.GONE);
        rvProjectRecycle.setVisibility(View.GONE);
        //..............
        TabLayout tabLayout = view.findViewById(R.id.profiletabs);
        tabLayout.addTab(tabLayout.newTab().setText(" Profile "));
        tabLayout.addTab(tabLayout.newTab().setText(" Engagements "));
        tabLayout.addTab(tabLayout.newTab().setText(" Products/ Services "));
        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 15, 0);
            tab.requestLayout();
        }
        //..........

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (tab.getPosition() == 0)
                {
                    profilelay.setVisibility(View.VISIBLE);
                    contactview.setVisibility(View.GONE);
                    productview.setVisibility(View.GONE);
                    rvProjectRecycle.setVisibility(View.GONE);

                } else if (tab.getPosition() == 1)
                {
                    profilelay.setVisibility(View.GONE);
                    productview.setVisibility(View.GONE);
                    contactview.setVisibility(View.VISIBLE);
                    rvProjectRecycle.setVisibility(View.GONE);
                }else
                {
                    profilelay.setVisibility(View.GONE);
                    contactview.setVisibility(View.GONE);
                    rvProjectRecycle.setVisibility(View.VISIBLE);
                    productview.setVisibility(View.GONE);
                    if(thirdviewstatus.equalsIgnoreCase("text"))
                    {
                        productview.setVisibility(View.VISIBLE);
                        rvProjectRecycle.setVisibility(View.GONE);
                    }else
                    {
                        productview.setVisibility(View.GONE);
                        rvProjectRecycle.setVisibility(View.VISIBLE);
                    }
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
        MainActivity.setTitle("");
        MainActivity.enableBackViews(true);
        MainActivity.bottomNavigation.setVisibility(View.VISIBLE);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void getCompanyDetails(String companyId)
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("sampd_company_id",companyId);
        AppConfig.showLoading("Loading...", mContext);
        Log.d("param",hm.toString());
        AppConfig.getLoadInterface().getCompanydetails(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<SMPDCompanyDetailsModel>() {
            @Override
            public void onResponse(Call<SMPDCompanyDetailsModel> call, Response<SMPDCompanyDetailsModel> response) {
                AppConfig.hideLoader();
                try {

                if (response.isSuccessful() && response.body() != null)
                {
                    if (response.body().getStatus() == 1)
                    {
                        Log.d("chages data",response.toString());
                        String namecom=response.body().getData().getName();
                        String companylink=response.body().getData().getIconImage();
                        String country_name=response.body().getData().getCountryId().toString();
                        String description=response.body().getData().getDescription();
                        String companyprofile=response.body().getData().getSampdCompanyProfile().getProfileDescription();
                        String contactprofile=response.body().getData().getSampdCompanyProfile().getContactEngagementDescription();
                        //thirdviewstatus
                        //...............................
                        final String mimeType = "text/html";
                        final String encoding = "UTF-8";
                        String html = companyprofile;
                        profilelay.loadDataWithBaseURL("", html, mimeType, encoding, "");
                        //...........................
                        String html1 = contactprofile;
                        contactview.loadDataWithBaseURL("", html1, mimeType, encoding, "");

                        thirdviewstatus=response.body().getData().getSampdCompanyProfile().getView_type();
                        if(thirdviewstatus.equalsIgnoreCase("text"))
                        {
                            if(response.body().getData().getSampdCompanyProfile().getService_description()!=null)
                            {
                                String html11 = response.body().getData().getSampdCompanyProfile().getService_description().toString();
                                productview.loadDataWithBaseURL("", html11, mimeType, encoding, "");
                            }

                            productview.setVisibility(View.VISIBLE);
                            rvProjectRecycle.setVisibility(View.GONE);

                        }else
                        {
                            productview.setVisibility(View.GONE);
                            rvProjectRecycle.setVisibility(View.GONE);
                        }
                        companyname.setText(namecom);
                        countryname.setText(country_name);
                        Glide.with(getActivity())
                                .asBitmap()
                                .load(companylink)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        ivCompanyImg.setImageBitmap(resource);
                                    }

                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                        super.onLoadFailed(errorDrawable);
                                        ivCompanyImg.setImageResource(R.drawable.default_photo);
                                    }

                                });

                        SAMPDList = new ArrayList<>();
                        SAMPDList.addAll(response.body().getData().getSampdCompanyServices());
                        if(SAMPDList.size()!=0)
                        {
                            ComapnyImagesAdapter mAdapter = new ComapnyImagesAdapter(SAMPDList,getActivity());
                            rvProjectRecycle.setAdapter(mAdapter);
                        }

                    }else
                    {
                        Toast.makeText(mContext, ""+response.message().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else
                {
                    ShowApiError(getActivity(),"server error in sampd-company/company-details");
                }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SMPDCompanyDetailsModel> call, Throwable t) {
                AppConfig.hideLoader();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
    }

  /*  http://128.199.182.16/migration/new_api/api/v2.4/v2.4.20/sampd-company/add-remove-fav-sampd-company?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjguMTk5LjE4Mi4xNlwvbWlncmF0aW9uXC9uZXdfYXBpXC9hcGlcL3YyLjRcL3YyLjQuMjBcL3VzZXJhdXRoXC91c2VyLWxvZ2luIiwiaWF0IjoxNjIwODMwMjM5LCJleHAiOjM2MTYyMDgzMDIzOSwibmJmIjoxNjIwODMwMjM5LCJqdGkiOiJpTFZKU0V6YmpScm5mR0wwIiwic3ViIjo5ODk5LCJwcnYiOiI4N2UwYWYxZWY5ZmQxNTgxMmZkZWM5NzE1M2ExNGUwYjA0NzU0NmFhIn0.LEvlXlsdQ2Brb1q6IWYkGcuPgmCDHQGb2A-turF0zy0
    Method : POST
    sampd_company_id:8
*/
}