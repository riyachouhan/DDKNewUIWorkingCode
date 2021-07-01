package com.ddkcommunity.fragment.mapmodule.corporateUser;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.mapmodule.model.finnelModel;
import com.ddkcommunity.fragment.send.SuccessFragmentScan;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class paymentCorporateFragment extends Fragment implements View.OnClickListener
{

     View view;
     Context mContext;
     LinearLayout btnPayUsingSamKoin,btnPayUsingBTC,btnPayUsingETH
             ,btnPayUsingUSdt,btnPayUsingCreditCard,btnPayUsingKpay;
     TextView next;
    UserResponse userData;

    public paymentCorporateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmentpaymentmap, container, false);
        mContext = getActivity();
        userData = AppConfig.getUserData(getContext());
        next=view.findViewById(R.id.next);
        btnPayUsingSamKoin=view.findViewById(R.id.btnPayUsingSamKoin);
        btnPayUsingBTC=view.findViewById(R.id.btnPayUsingBTC);
        btnPayUsingETH=view.findViewById(R.id.btnPayUsingETH);
        btnPayUsingUSdt=view.findViewById(R.id.btnPayUsingUSdt);
        btnPayUsingCreditCard=view.findViewById(R.id.btnPayUsingCreditCard);
        btnPayUsingKpay=view.findViewById(R.id.btnPayUsingKpay);
        next.setOnClickListener(this);
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
            case R.id.next:
                final String emailid=userData.getUser().getEmail();
                Fragment fragment = new SuccessFragmentScan();
                Bundle arg = new Bundle();
                arg.putString("action", "successcorporate");
                arg.putString("actionamnt", "success");
                arg.putString("email", emailid);
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, false);
                break;
        }
    }
}
