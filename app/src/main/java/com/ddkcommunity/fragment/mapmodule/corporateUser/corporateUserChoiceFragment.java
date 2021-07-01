package com.ddkcommunity.fragment.mapmodule.corporateUser;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.mapmodule.MyTabLayout;
import com.ddkcommunity.fragment.mapmodule.adapter.FunnelAdapter;
import com.ddkcommunity.fragment.mapmodule.funnelfragment;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.utilies.AppConfig;
import com.google.android.material.tabs.TabLayout;

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
public class corporateUserChoiceFragment extends Fragment implements View.OnClickListener
{

     View view;
     Context mContext;
     TextView yes,no;

    public corporateUserChoiceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.corporateuserdialog, container, false);
        mContext = getActivity();
        yes=view.findViewById(R.id.yes);
        no=view.findViewById(R.id.no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Corporate User");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.no:
                getActivity().getSupportFragmentManager().popBackStack();
                break;

            case R.id.yes:
                MainActivity.addFragment(new paymentCorporateFragment(), true);
                break;
        }
    }
}
