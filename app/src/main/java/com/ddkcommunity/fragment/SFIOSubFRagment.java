package com.ddkcommunity.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.SFIOAdapter;
import com.ddkcommunity.adapters.SfioHeaderAdapter;
import com.ddkcommunity.adapters.SfioHeaderAdapterAll;
import com.ddkcommunity.fragment.projects.TermsAndConsitionSubscription;
import com.ddkcommunity.model.sfioHeaderModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.utilies.AppConfig;
import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class SFIOSubFRagment extends Fragment
{
     LinearLayout addicon;
     View view;
     Context mContext;
     RecyclerView rvRecycleher;
    public static SfioHeaderAdapterAll mAdapterher;

    public SFIOSubFRagment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.sfiosublisr, container, false);
        mContext = getActivity();
        rvRecycleher=view.findViewById(R.id.rvRecycleher);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        rvRecycleher.setLayoutManager(mLayoutManager1);
        rvRecycleher.setItemAnimator(new DefaultItemAnimator());
        getCurrentBonusSfio();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle(" SFIO ");
        MainActivity.enableBackViews(true);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.prepareListData(getActivity(),"home");
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    private void getCurrentBonusSfio()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();

        AppConfig.getLoadInterface().getSumSubsciption(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<sfioHeaderModel>() {
            @Override
            public void onResponse(Call<sfioHeaderModel> call, Response<sfioHeaderModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        pd.dismiss();
                        if (response.body().getStatus()==1)
                        {
                            ArrayList<sfioHeaderModel.BankDeposit> funnelDAtasf=new ArrayList<>();
                            funnelDAtasf.addAll(response.body().getData().getBankDeposit());
                            mAdapterher = new SfioHeaderAdapterAll(funnelDAtasf, mContext);
                            rvRecycleher.setAdapter(mAdapterher);

                         }
                    } else {
                        pd.dismiss();
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<sfioHeaderModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
