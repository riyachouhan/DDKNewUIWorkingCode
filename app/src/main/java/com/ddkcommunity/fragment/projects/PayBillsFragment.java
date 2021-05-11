package com.ddkcommunity.fragment.projects;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ddkcommunity.LoadInterface;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.HomeBannerPagerAdapter;
import com.ddkcommunity.model.SliderImg;
import com.ddkcommunity.model.SliderImgResponse;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayBillsFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    ViewPager viewPager;
    private HomeBannerPagerAdapter homeBannerPager;
    private ArrayList<SliderImg> drawablesList = new ArrayList<>();
    private int count = 0;

    public PayBillsFragment() {

    }

    private LinearLayout lytCreditCard, lytUtilities, lytSubscription, btnPayBills;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_bills, container, false);
        mContext = getActivity();
        viewPager=view.findViewById(R.id.viewPager);
        getBannerKImages();
        return view;
    }

    //for banner
    private void setBannerImages()
    {
        // The code below assumes that the root container has an id called 'main'
        homeBannerPager = new HomeBannerPagerAdapter(mContext, drawablesList);
        viewPager.setAdapter(homeBannerPager);
        changeViewPagerPage();
    }

    private void changeViewPagerPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count == drawablesList.size() - 1) {
                    count = 0;
                } else {
                    count++;
                }
                viewPager.setCurrentItem(count);
                changeViewPagerPage();
            }
        }, 3000);
    }

    private void getBannerKImages() {
        try {
            LoadInterface apiservice = AppConfig.getClient().create(LoadInterface.class);
            //we havd to  correct
            Call<SliderImgResponse> call = apiservice.getBannerLayout();
            //showProgressDiaog();
            call.enqueue(new Callback<SliderImgResponse>() {
                @Override
                public void onResponse(Call<SliderImgResponse> call, Response<SliderImgResponse> response) {
                    //  hideProgress();
                    try {
                        int code = response.code();
                        String retrofitMesage = "";
                        retrofitMesage = response.body().getMsg();
                        if (code == 200) {
                            retrofitMesage = response.body().getMsg();
                            int status = response.body().getStatus();
                            if (status == 1) {
                                drawablesList = new ArrayList<>();
                                drawablesList.addAll(response.body().lendData);
                                if (drawablesList.size() > 0) {
                                    setBannerImages();
                                }
                            } else if (status == 400) {
                                ShowApiError(getActivity(),"error in api ninethface/sliders");
                            } else {
                                Toast.makeText(getActivity(), retrofitMesage, Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                        if (code == 404) {
                            Toast.makeText(getActivity(), "" + "Page not found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (code == 500) {
                            ShowApiError(getActivity(),"error in api ninethface/sliders");
                        } else {
                            Toast.makeText(getActivity(), "" + retrofitMesage, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception s) {
                        s.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<SliderImgResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Response getting failed", Toast.LENGTH_SHORT).show();
                    // hideProgress();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //..
    @Override
    public void onClick(View v) {
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Pay Bill");
        MainActivity.enableBackViews(true);
    }
}