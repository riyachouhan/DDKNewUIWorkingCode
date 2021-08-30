package com.ddkcommunity.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.interfaces.GetUSDAndBTCCallback;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class sfioSelectionFragment extends Fragment {

    public sfioSelectionFragment() {
    }

    View view;
    private Context mContext;
    TextView amount;
    AppCompatButton submit;
    LinearLayout selection_type_layout,form_layout,creditcard_layout,bankdepositelayout;
    String input_amount,fee;
    String ownershipjoin_status,ownershiptype,name,email,contact,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.addsfiolayout, container, false);
        mContext = getActivity();
        if (getArguments().getString("input_amount") != null)
        {
            input_amount= getArguments().getString("input_amount");
        }

        if (getArguments().getString("fee") != null)
        {
            fee= getArguments().getString("fee");
        }

        if (getArguments().getString("ownershipjoin_status") != null)
        {
            ownershipjoin_status= getArguments().getString("ownershipjoin_status");
                if (getArguments().getString("ownershiptype") != null)
                {
                    ownershiptype= getArguments().getString("ownershiptype");
                }
                if (getArguments().getString("name") != null)
                {
                    name= getArguments().getString("name");
                }
                if (getArguments().getString("email") != null)
                {
                    email= getArguments().getString("email");
                }
                if (getArguments().getString("contact") != null)
                {
                    contact= getArguments().getString("contact");
                }
                if (getArguments().getString("address") != null)
                {
                    address= getArguments().getString("address");
                }
        }
        //for amount
        selection_type_layout=view.findViewById(R.id.selection_type_layout);
        form_layout=view.findViewById(R.id.form_layout);
        creditcard_layout=view.findViewById(R.id.creditcard_layout);
        bankdepositelayout=view.findViewById(R.id.bankdepositelayout);
        form_layout.setVisibility(View.GONE);
        selection_type_layout.setVisibility(View.VISIBLE);

        creditcard_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CreditCardPaymentSFIOFragment();
                Bundle arg = new Bundle();
                arg.putString("input_amount", input_amount);
                arg.putString("ownershipjoin_status", ownershipjoin_status);
                arg.putString("ownershiptype", ownershiptype);
                arg.putString("name", name);
                arg.putString("email", email);
                arg.putString("contact", contact);
                arg.putString("address", address);
                arg.putString("fee", fee);
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment,false);
            }
        });

        bankdepositelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new bankDepositeFragment();
                Bundle arg = new Bundle();
                arg.putString("input_amount", input_amount);
                arg.putString("ownershipjoin_status",ownershipjoin_status);
                arg.putString("ownershiptype", ownershiptype);
                arg.putString("name", name);
                arg.putString("email", email);
                arg.putString("contact", contact);
                arg.putString("address", address);
                arg.putString("fee", fee);
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment,false);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Selection Type");
        MainActivity.enableBackViews(true);
    }

}
