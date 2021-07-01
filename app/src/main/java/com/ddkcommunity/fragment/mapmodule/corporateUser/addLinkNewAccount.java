package com.ddkcommunity.fragment.mapmodule.corporateUser;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.ddkcommunity.fragment.CreditCardPaymentSFIOFragment;
import com.ddkcommunity.fragment.send.SuccessFragmentScan;
import com.ddkcommunity.interfaces.GetUSDAndBTCCallback;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class addLinkNewAccount extends Fragment {

    public addLinkNewAccount() {
    }

    View view;
    private Context mContext;
    EditText emailadd,passwordtxt;
    AppCompatButton submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.addlinkfragemnlay, container, false);
        mContext = getActivity();
        //for amount
        emailadd=view.findViewById(R.id.emailadd);
        passwordtxt=view.findViewById(R.id.passwordtxt);
        submit=view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*String amountvlaue=amount.getText().toString();
                if(amountvlaue.equalsIgnoreCase(""))
                {
                    Toast.makeText(mContext, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }else
                {
                    BigDecimal etAmountvalue = new BigDecimal(amountvlaue);
                    if(amount.getText().toString().equalsIgnoreCase("."))
                    {
                    }else {
                        String miniumsfio=App.pref.getString(Constant.sfio_minimumamount, "");
                        String sfiofee=App.pref.getString(Constant.sfiofees, "");
                        BigDecimal threeHundred = new BigDecimal(miniumsfio);
                        if (threeHundred.compareTo(new BigDecimal(amountvlaue.trim())) == 1)
                        {
                            Toast.makeText(mContext, "Minimum amount is "+miniumsfio+" USDT", Toast.LENGTH_SHORT).show();
                           return;
                        }
                        BigDecimal enteramount=new BigDecimal(amountvlaue);
                        BigDecimal currencyTransactionPercent = new BigDecimal(sfiofee);
                        BigDecimal currencyTransactionPercentduivi = currencyTransactionPercent.divide(new BigDecimal(100));
                        BigDecimal finalfees = enteramount.multiply(currencyTransactionPercentduivi);
                        //BigDecimal finalfees = currencyTransactionPercent;
                        String fee=String.format(Locale.ENGLISH, "%.4f", finalfees);
                        Fragment fragment = new CreditCardPaymentSFIOFragment();
                        Bundle arg = new Bundle();
                        arg.putString("input_amount", amountvlaue);
                        arg.putString("fee", fee);
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment,false);
                    }

                }*/
                sendOtp(getActivity());
            }
        });
        return view;
    }

    private void sendOtp(final Activity activity) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", App.pref.getString(Constant.USER_EMAIL, ""));
        hm.put("name", App.pref.getString(Constant.USER_NAME, ""));
        Log.d("param",":"+hm);
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().postOtp(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().status == 1) {
                    Log.d("otpre","::"+response.body().data );
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Log.d("otpre","::"+jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String otp = response.body().data;
                    Log.d("otp",otp);
                    initOtpVerifiaction(otp,activity);
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 3) {
                    AppConfig.showToast(response.body().msg);
                    AppConfig.openSplashActivity(getActivity());
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 0) {
                    AppConfig.showToast(response.body().msg);
                } else {
                    AppConfig.showToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void initOtpVerifiaction(final String otp,final Activity mContext)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View dialogView;
        dialogView = layoutInflater.inflate(R.layout.otpverificationlayout, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        CommonMethodFunction.setupFullHeight(mContext,dialog);
        ImageView logo1=dialogView.findViewById(R.id.logo1);
        final LinearLayout otherview=dialogView.findViewById(R.id.otherview);
        otherview.setVisibility(View.VISIBLE);
        logo1.setVisibility(View.GONE);
        final LinearLayout back_view=dialogView.findViewById(R.id.back_view);
        final TextView btnVerify =dialogView.findViewById(R.id.btnVerify);
        final EditText otp_edt1 =dialogView.findViewById(R.id.otp_edt11);
        final EditText otp_edt2 =dialogView.findViewById(R.id.otp_edt12);
        final EditText otp_edt3 =dialogView.findViewById(R.id.otp_edt13);
        final EditText otp_edt4 =dialogView.findViewById(R.id.otp_edt14);

        btnVerify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Fragment fragment = new SuccessFragmentScan();
                Bundle arg = new Bundle();
                arg.putString("action", "addnewlinka");
                arg.putString("actionamnt", "success");
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, false);

                /*StringBuffer str_top = new StringBuffer();
                str_top.append(otp_edt1.getText().toString()).append(otp_edt2.getText().toString()).append(otp_edt3.getText().toString()).append(otp_edt4.getText().toString());
                if (str_top.length() > 0 && str_top.length() == 4)
                {
                    String otpnew=str_top.toString();
                    if (str_top.toString().equalsIgnoreCase(otp))
                    {
                        ProgressDialog pd=new ProgressDialog(getActivity());
                        pd.setCanceledOnTouchOutside(false);
                        pd.setMessage("Please wait");
                        pd.show();

                        Fragment fragment = new SuccessFragmentScan();
                        Bundle arg = new Bundle();
                        arg.putString("action", "addnewlinka");
                        arg.putString("actionamnt", "success");
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, false);

                        //  sendDataServer(dialog,pd,getActivity());
                    } else {
                        AppConfig.showToast("Otp is expired or incorrect");
                    }
                    //............
                }*/
            }
        });

        otp_edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt1.length() == 0) {
                    otp_edt1.requestFocus();
                }
                if (otp_edt1.length() == 1) {
                    otp_edt1.clearFocus();
                    otp_edt2.requestFocus();
                    otp_edt2.setCursorVisible(true);
                }
            }
        });

        otp_edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt2.length() == 0) {
                    otp_edt2.requestFocus();
                }
                if (otp_edt2.length() == 1) {
                    otp_edt2.clearFocus();
                    otp_edt3.requestFocus();
                    otp_edt3.setCursorVisible(true);
                }
            }
        });


        otp_edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt3.length() == 0) {
                    otp_edt3.requestFocus();
                }
                if (otp_edt3.length() == 1) {
                    otp_edt3.clearFocus();
                    otp_edt4.requestFocus();
                    otp_edt4.setCursorVisible(true);
                }
            }
        });


        otp_edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt4.length() == 0) {
                    otp_edt4.requestFocus();
                }
                if (otp_edt4.length() == 1) {
                    otp_edt4.requestFocus();
                }
            }
        });
        otp_edt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    otp_edt1.requestFocus();
                }
                return false;
            }
        });
        otp_edt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp_edt2.requestFocus();
                }
                return false;
            }
        });
        otp_edt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp_edt3.requestFocus();
                }
                return false;
            }
        });

        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Link New Account");
        MainActivity.enableBackViews(true);
    }

}
