package com.ddkcommunity.fragment.send;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SFIOShowFragmement;
import com.ddkcommunity.fragment.mapmodule.corporateUser.corporateUserDashboardFragment;
import com.ddkcommunity.fragment.projects.MapOtionAllFragment;
import com.ddkcommunity.fragment.projects.MapreferralFragment;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.checkRefferalModel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.fragment.ScanFragment.clickoption;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessFragmentScan extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View rootView = null;
    private SlideToActView slide_custom_icon;

    public SuccessFragmentScan()
    {
        // Required empty public constructor
    }

    private UserResponse userData;
    String txtid,name,amount;
    String otp,action;
    String emailaddress,currentWalletBalance,message;
    TextView hintformsubvie,upperheading,hintform,etWalletUserName,etDDKCoins,btnVerify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        if (rootView == null) {

            View view = inflater.inflate(R.layout.successtranscationlayout, container, false);
            try {
                rootView = view;
                mContext = getActivity();
                if (getArguments().getString("action") != null) {
                    action= getArguments().getString("action");
                }
                hintformsubvie=view.findViewById(R.id.hintformsubvie);
                upperheading=view.findViewById(R.id.upperheading);
                btnVerify = view.findViewById(R.id.btnVerify);
                etDDKCoins = view.findViewById(R.id.etDDKCoins);
                userData = AppConfig.getUserData(mContext);
                ImageView failedi,succeicon;
                failedi=view.findViewById(R.id.failedi);
                succeicon=view.findViewById(R.id.succeicon);
                etWalletUserName = view.findViewById(R.id.etWalletUserName);

                if(action.equalsIgnoreCase("scan"))
                {
                    succeicon.setVisibility(View.VISIBLE);
                    failedi.setVisibility(View.GONE);
                    if (getArguments().getString("txtid") != null) {
                        txtid = getArguments().getString("txtid");
                    }
                    if (getArguments().getString("name") != null) {
                        name = getArguments().getString("name");
                    }
                    if (getArguments().getString("amount") != null) {
                        amount = getArguments().getString("amount");
                    }
                    String clickoptionv = clickoption;
                    if (clickoptionv.equalsIgnoreCase("usingphp"))
                    {
                        upperheading.setText("PHP");
                        currentWalletBalance = App.pref.getString(Constant.PHP_Balance, "");
                    } else {
                        upperheading.setText("SAMK");
                        currentWalletBalance = App.pref.getString(Constant.SAMKOIN_Balance, "");
                    }
                    etDDKCoins.setText(amount);
                    etWalletUserName.setText(name);
                    etDDKCoins.setVisibility(View.VISIBLE);
                    etWalletUserName.setVisibility(View.VISIBLE);
                    upperheading.setVisibility(View.VISIBLE);
                    btnVerify.setText("Go To Home");
                    btnVerify.setOnClickListener(this);

                }else if(action.equalsIgnoreCase("successcorporate"))
                {
                    btnVerify.setText("Go To Dashboard");
                    if (getArguments().getString("email") != null) {
                        emailaddress = getArguments().getString("email");
                    }

                    String actionamnt="";
                    if (getArguments().getString("actionamnt") != null) {
                        actionamnt = getArguments().getString("actionamnt");
                    }

                    if(getArguments().getString("message")!=null)
                    {
                        message=getArguments().getString("message");
                    }

                    hintform=view.findViewById(R.id.hintform);
                    etDDKCoins.setVisibility(View.GONE);
                    etWalletUserName.setVisibility(View.GONE);
                    upperheading.setVisibility(View.GONE);
                    btnVerify.setOnClickListener(this);
                    if(actionamnt.equalsIgnoreCase("success")) {
                        hintform.setText("Successfully registered your \n" +
                                "account as Corporate User");
                        succeicon.setVisibility(View.VISIBLE);
                        failedi.setVisibility(View.GONE);

                    }else
                    {
                        succeicon.setVisibility(View.GONE);
                        failedi.setVisibility(View.VISIBLE);

                        hintform.setText("Sorry ! \n" +
                                "Payment Failed. Please try again later");
                    }
                }else if(action.equalsIgnoreCase("addnewlinka"))
                {
                    btnVerify.setText("Manage Account");

                    String actionamnt="";
                    if (getArguments().getString("actionamnt") != null) {
                        actionamnt = getArguments().getString("actionamnt");
                    }

                    if(getArguments().getString("message")!=null)
                    {
                        message=getArguments().getString("message");
                    }

                    hintform=view.findViewById(R.id.hintform);
                    etDDKCoins.setVisibility(View.GONE);
                    etWalletUserName.setVisibility(View.GONE);
                    upperheading.setVisibility(View.GONE);
                    btnVerify.setOnClickListener(this);
                    if(actionamnt.equalsIgnoreCase("success")) {
                        hintform.setText("Successfully linked account to \n" +
                                "your Corporate User Account");
                        succeicon.setVisibility(View.VISIBLE);
                        failedi.setVisibility(View.GONE);

                    }else
                    {
                        succeicon.setVisibility(View.GONE);
                        failedi.setVisibility(View.VISIBLE);

                        hintform.setText("Sorry ! \n" +
                                "Payment Failed. Please try again later");
                    }

                }else if(action.equalsIgnoreCase("sfio"))
                {
                    btnVerify.setText("Go To SFIO");
                    if (getArguments().getString("email") != null) {
                        emailaddress = getArguments().getString("email");
                    }

                    String actionamnt="";
                    if (getArguments().getString("actionamnt") != null) {
                        actionamnt = getArguments().getString("actionamnt");
                    }

                    if(getArguments().getString("message")!=null)
                    {
                        message=getArguments().getString("message");
                    }

                    hintform=view.findViewById(R.id.hintform);
                    etDDKCoins.setVisibility(View.GONE);
                    etWalletUserName.setVisibility(View.GONE);
                    upperheading.setVisibility(View.GONE);
                    btnVerify.setOnClickListener(this);
                    if(actionamnt.equalsIgnoreCase("success")) {
                        hintform.setText("Congratulations ! \n" +
                                "Payment Successful");
                        hintformsubvie.setText(message+"");
                        succeicon.setVisibility(View.VISIBLE);
                        failedi.setVisibility(View.GONE);

                    }else
                    {
                        succeicon.setVisibility(View.GONE);
                        failedi.setVisibility(View.VISIBLE);

                        hintform.setText("Sorry ! \n" +
                                "Payment Failed. Please try again later");
                    }

                }else if(action.equalsIgnoreCase("sfioSave"))
                {
                    btnVerify.setText("Go To SFIO");
                    if (getArguments().getString("email") != null) {
                        emailaddress = getArguments().getString("email");
                    }

                    String actionamnt="";
                    if (getArguments().getString("actionamnt") != null) {
                        actionamnt = getArguments().getString("actionamnt");
                    }

                    if(getArguments().getString("message")!=null)
                    {
                        message=getArguments().getString("message");
                    }

                    hintform=view.findViewById(R.id.hintform);
                    etDDKCoins.setVisibility(View.GONE);
                    etWalletUserName.setVisibility(View.GONE);
                    upperheading.setVisibility(View.GONE);
                    btnVerify.setOnClickListener(this);
                    if(actionamnt.equalsIgnoreCase("success")) {
                        hintform.setText("Congratulations ! \n" +
                                "");
                        hintformsubvie.setText(message+"");
                        succeicon.setVisibility(View.VISIBLE);
                        failedi.setVisibility(View.GONE);

                    }else
                    {
                        succeicon.setVisibility(View.GONE);
                        failedi.setVisibility(View.VISIBLE);

                        hintform.setText("Sorry ! \n" +
                                "Payment Failed. Please try again later");
                    }

                }else
                {
                    succeicon.setVisibility(View.VISIBLE);
                    failedi.setVisibility(View.GONE);
                    if (getArguments().getString("email") != null) {
                        emailaddress = getArguments().getString("email");
                    }
                    hintform=view.findViewById(R.id.hintform);
                    etDDKCoins.setVisibility(View.GONE);
                    etWalletUserName.setVisibility(View.GONE);
                    upperheading.setVisibility(View.GONE);
                    btnVerify.setText("Go To Home");
                    btnVerify.setOnClickListener(this);
                    hintform.setText("Successfully registered your \n" +
                            "account on M.A.P");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnVerify)
        {
            if(action.equalsIgnoreCase("scan")) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(i);
                getActivity().finishAffinity();
            }else if(action.equalsIgnoreCase("sfio") || action.equalsIgnoreCase("sfioSave"))
            {
                Fragment fragment = new SFIOShowFragmement();
                Bundle arg = new Bundle();
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, false);

            }else if(action.equalsIgnoreCase("successcorporate"))
            {

                Fragment fragment = new corporateUserDashboardFragment();
                Bundle arg = new Bundle();
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, false);

            }else
            {
                Intent i=new Intent(getActivity(),MainActivity.class);
                getActivity().startActivity(i);
               // CheckUserActiveStaus(emailaddress);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(action.equalsIgnoreCase("scan")) {
            MainActivity.setTitle("Success");
        }else if(action.equalsIgnoreCase("sfio"))
        {
            MainActivity.setTitle("SFIO Success");

        }else
        {
            MainActivity.setTitle("Referral Code");
        }
        MainActivity.scanview.setVisibility(View.GONE);
    }

    private void CheckUserActiveStaus(final String userrefferal)
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Please wait ....");
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", userrefferal);
        AppConfig.getLoadInterfaceMap().CheckUserActive(hm).enqueue(new Callback<checkRefferalModel>() {
            @Override
            public void onResponse(Call<checkRefferalModel> call, Response<checkRefferalModel> response) {
                try
                {
                    AppConfig.hideLoading(dialog);
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getSubscription_status().equalsIgnoreCase("true"))
                        {
                            Fragment fragment = new MapOtionAllFragment();
                            Bundle arg = new Bundle();
                            arg.putString("action", "map");
                            arg.putString("activeStatus", "1");
                            fragment.setArguments(arg);
                            MainActivity.addFragment(fragment,true);

                        }else
                        {
                            if (response.body().getRegister_status().equalsIgnoreCase("true"))
                            {
                                Fragment fragment = new MapOtionAllFragment();
                                Bundle arg = new Bundle();
                                arg.putString("action", "map");
                                arg.putString("activeStatus", "0");
                                fragment.setArguments(arg);
                                MainActivity.addFragment(fragment,true);
                            }else
                            {
                                MainActivity.addFragment(new MapreferralFragment(), false);
                            }
                        }

                    } else {
                        ShowApiError(getContext(),"server error check-mail-exist");
                    }

                } catch (Exception e)
                {
                    AppConfig.hideLoading(dialog);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<checkRefferalModel> call, Throwable t)
            {
                AppConfig.hideLoading(dialog);
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
