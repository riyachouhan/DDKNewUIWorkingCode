package com.ddkcommunity.fragment.settingModule;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.activities.OTPActivity;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.emergencyModel;
import com.ddkcommunity.model.referalRequestsendModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class changeReferalRequest extends Fragment
{
    private Context mContext;
    View view;
    AppCompatButton submit_BT;
    EditText alt_referralcode;
    TextView validate_view;

    public changeReferalRequest()
    {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.changereferralqerequest, container, false);
        mContext = getActivity();
        validate_view=view.findViewById(R.id.validate_view);
        alt_referralcode=view.findViewById(R.id.alt_referralcode);
        String udata=" Validate ";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        validate_view.setText(content);
        alt_referralcode.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            submit_BT.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() != 0)
            {
                submit_BT.setVisibility(View.GONE);
            }else
            {
                submit_BT.setVisibility(View.GONE);
            }
        }
    });

        submit_BT=view.findViewById(R.id.submit_BT);
        submit_BT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String alt_referralcodev=alt_referralcode.getText().toString();
                    if(alt_referralcodev.equalsIgnoreCase(""))
                    {
                        Toast.makeText(mContext, "Please enter Referral code", Toast.LENGTH_SHORT).show();
                    }else {
                        sendOtpEmail(getActivity(),alt_referralcodev);
                    }
            }
        });

        validate_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alt_referralcodev=alt_referralcode.getText().toString();
                if(alt_referralcodev.equalsIgnoreCase(""))
                {
                    Toast.makeText(mContext, "Please enter Referral code", Toast.LENGTH_SHORT).show();
                }else {
                    checkReferalCodeValida(getActivity(),alt_referralcodev);
                }
            }
        });
        return view;
    }

    public void checkReferalCodeValida(final Activity mContext,final String referaalcode)
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("referal_code", referaalcode);
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Please wait....");

        AppConfig.getLoadInterface().validateReferral(AppConfig.getStringPreferences(mContext, Constant.JWTToken), hm).enqueue(new Callback<referalRequestsendModel>() {
            @Override
            public void onResponse(Call<referalRequestsendModel> call, Response<referalRequestsendModel> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().getStatus() == 1) {
                    dataPutMethods.userResponseMsg(getActivity(),response.body().getMsg());
                    submit_BT.setVisibility(View.VISIBLE);
                } else if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 3) {
                    dataPutMethods.userResponseMsg(getActivity(),response.body().getMsg());
                    submit_BT.setVisibility(View.GONE);
                } else if (response.isSuccessful() && response.body() != null && response.body().getStatus() == 0) {
                    dataPutMethods.userResponseMsg(getActivity(),response.body().getMsg());
                    submit_BT.setVisibility(View.GONE);

                } else {
                    dataPutMethods.userResponseMsg(getActivity(),response.body().getMsg());
                    submit_BT.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<referalRequestsendModel> call, Throwable t) {
                dialog.dismiss();
                errordurigApiCalling(mContext,t.getMessage());
                submit_BT.setVisibility(View.GONE);
            }
        });
    }

    public void sendOtpEmail(final Activity mContext,final String referaalcode)
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email",  App.pref.getString(Constant.USER_EMAIL, ""));
        hm.put("name", App.pref.getString(Constant.USER_NAME, ""));

        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().postOtp(AppConfig.getStringPreferences(mContext, Constant.JWTToken), hm).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().status == 1) {
                    String otp = response.body().data;
                    Log.d("otp",otp);
                    initOtpVerifiaction(otp,mContext,referaalcode);
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 3) {
                    AppConfig.showToast(response.body().msg);
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 0) {
                    AppConfig.showToast(response.body().msg);
                } else {
                    AppConfig.showToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                dialog.dismiss();
                errordurigApiCalling(mContext,t.getMessage());
            }
        });
    }

    private void initOtpVerifiaction(final String otp, final Activity mContext, final String referaalcode)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View dialogView;
        dialogView = layoutInflater.inflate(R.layout.otpverificationlayout, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        CommonMethodFunction.setupFullHeight(mContext,dialog);
        ImageView logo1=dialogView.findViewById(R.id.logo1);
        Toolbar totolher=dialogView.findViewById(R.id.totolher);
        TextView full_name_txt=dialogView.findViewById(R.id.full_name_txt);
        full_name_txt.setText("OTP has been sent successfully to your New Email. Please verify your account by entering it below.");
        totolher.setVisibility(View.GONE);
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
                StringBuffer str_top = new StringBuffer();
                str_top.append(otp_edt1.getText().toString()).append(otp_edt2.getText().toString()).append(otp_edt3.getText().toString()).append(otp_edt4.getText().toString());
                if (str_top.length() > 0 && str_top.length() == 4)
                {
                    String otpnew=str_top.toString();
                    if (str_top.toString().equalsIgnoreCase(otp))
                    {
                        submitBillingView(dialog,referaalcode);
                    } else {
                        AppConfig.showToast("Otp is expired or incorrect");
                    }
                    //............
                }
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
        MainActivity.setTitle("Enter Referral Code");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    private void submitBillingView(final BottomSheetDialog dialog,String referaalcode)
    {
        final ProgressDialog dialognew = new ProgressDialog(getActivity());
        AppConfig.showLoading(dialognew, "Please wait ....");
        HashMap<String, String> hm = new HashMap<>();
        hm.put("referal_code", referaalcode);
        Log.d("hmparam",hm.toString());
        AppConfig.getLoadInterface().updateReferalCode(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<referalRequestsendModel>() {
            @Override
            public void onResponse(Call<referalRequestsendModel> call, Response<referalRequestsendModel> response) {
                             dialognew.dismiss();
                        try {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                String responseData = response.body().toString();
                                Log.d("status", responseData);
                                if(response.body().getStatus()==1)
                                {
                                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    getActivity().onBackPressed();
                                }else
                                {
                                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                AppConfig.showToast("Server error");
                                Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            AppConfig.showToast("Server json error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<referalRequestsendModel> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

}
