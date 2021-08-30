package com.ddkcommunity.fragment.settingModule;


import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.CountryListAdapter;
import com.ddkcommunity.adapters.CountrycodeAdapter;
import com.ddkcommunity.fragment.ProfileFragment;
import com.ddkcommunity.fragment.projects.CreditCardPaymentFragment;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.conatcModel;
import com.ddkcommunity.model.emergencyModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.fragment.HomeFragment.userselctionopt;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class changeEmailFragment extends Fragment
{

    private Context mContext;
    View view;
    AppCompatButton submit_BT;
    EditText passwordv,alt_email_ET,alt_email_old;
    TextView emailstatus,otpstatus,send_otp,alt_contactnumber_ET;
    String otpid;
    int otpsucces=0;
    EditText otp_edt1m,otp_edt2m,otp_edt3m,otp_edt4m;
    String countrycode,mobilenumber;

    public changeEmailFragment()
    {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.changeemaillayout, container, false);
        mContext = getActivity();
        emailstatus=view.findViewById(R.id.emailstatus);
        otpstatus=view.findViewById(R.id.otpstatus);
        passwordv=view.findViewById(R.id.passwordv);
        submit_BT=view.findViewById(R.id.submit_BT);
        alt_email_old=view.findViewById(R.id.alt_email_old);
        alt_email_ET=view.findViewById(R.id.alt_email_ET);
        countrycode=MainActivity.userData.getUser().getPhoneCode();
        mobilenumber=countrycode+""+App.pref.getString(Constant.USER_MOBILE, "");
        alt_contactnumber_ET=view.findViewById(R.id.alt_contactnumber_ET);
        alt_contactnumber_ET.setText(mobilenumber);
        send_otp=view.findViewById(R.id.send_otp);
        otp_edt1m =view.findViewById(R.id.otp_edt11);
        otp_edt2m =view.findViewById(R.id.otp_edt12);
        otp_edt3m =view.findViewById(R.id.otp_edt13);
        otp_edt4m =view.findViewById(R.id.otp_edt14);
        alt_contactnumber_ET.setText(mobilenumber+"");
        submit_BT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String oldmainuser=App.pref.getString(Constant.USER_EMAIL, "");
                String emailv=alt_email_ET.getText().toString();
                String alt_email_oldv=alt_email_old.getText().toString();
                String moilev=alt_contactnumber_ET.getText().toString();
                String password=passwordv.getText().toString();
                String finalmobileno= moilev;

                if(!oldmainuser.equalsIgnoreCase(alt_email_oldv))
                {
                    Toast.makeText(mContext, "Please enter correct old email address", Toast.LENGTH_SHORT).show();

                }else
                {
                    StringBuffer str_top = new StringBuffer();
                    str_top.append(otp_edt1m.getText().toString()).append(otp_edt2m.getText().toString()).append(otp_edt3m.getText().toString()).append(otp_edt4m.getText().toString());
                    if(emailv.equalsIgnoreCase(""))
                    {
                        Toast.makeText(mContext, "Please enter new email", Toast.LENGTH_SHORT).show();
                    }else if (str_top.length() == 0 || str_top.length() != 4)
                    {
                        Toast.makeText(mContext, "Please enter otp", Toast.LENGTH_SHORT).show();
                    }
                    else if(password.equalsIgnoreCase(""))
                    {
                        Toast.makeText(mContext, "Please enter password", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        if (str_top.length() > 0 && str_top.length() == 4)
                        {
                            String otpnew = str_top.toString();
                            String alt_contactnumber_ETv=alt_contactnumber_ET.getText().toString();
                            String finalcode=alt_contactnumber_ETv;
                            otpVerified(finalcode,otpnew,emailv,getActivity(),oldmainuser,password,oldmainuser,finalmobileno);
                           // AskPermissionDialog(emailv,getActivity(),oldmainuser,password,oldmainuser,finalmobileno);
                        }
                    }

                }
            }
        });
        //for new
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(send_otp.getText().toString().equalsIgnoreCase("Resend mobile OTP") || send_otp.getText().toString().equalsIgnoreCase("Get mobile OTP"))
                {
                    String alt_contactnumber_ETv = alt_contactnumber_ET.getText().toString();
                    String oldmainuser=App.pref.getString(Constant.USER_EMAIL, "");
                    String emailv=alt_email_ET.getText().toString();
                    String alt_email_oldv=alt_email_old.getText().toString();
                    String moilev=alt_contactnumber_ET.getText().toString();
                    String password=passwordv.getText().toString();
                    String finalmobileno= moilev;

                    if(alt_email_oldv.equalsIgnoreCase(""))
                    {
                        Toast.makeText(mContext, "Please enter old email address", Toast.LENGTH_SHORT).show();

                    }else if(emailv.equalsIgnoreCase(""))
                    {
                        Toast.makeText(mContext, "Please enter new email address", Toast.LENGTH_SHORT).show();

                    }else
                    if(!oldmainuser.equalsIgnoreCase(alt_email_oldv))
                    {
                        Toast.makeText(mContext, "Please enter correct old email address", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        emailstatus.setVisibility(View.GONE);
                        String finalcode = App.pref.getString(Constant.USER_MOBILE, "");
                        sendOtp(finalcode,countrycode, alt_email_ET.getText().toString());
                    }

                    /*    if (alt_contactnumber_ETv.equalsIgnoreCase("")) {
                        Toast.makeText(mContext, "Please enter mobile no", Toast.LENGTH_SHORT).show();
                    } else {
                        String finalcode = App.pref.getString(Constant.USER_MOBILE, "");
                        sendOtp(finalcode,countrycode, alt_email_ET.getText().toString());
                    }*/
                }
            }
        });

        alt_contactnumber_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                    otp_edt1m.setText("");
                    otp_edt2m.setText("");
                    otp_edt3m.setText("");
                    otp_edt4m.setText("");
                    otp_edt1m.clearFocus();
                    otp_edt2m.clearFocus();
                    otp_edt3m.clearFocus();
                    otp_edt4m.clearFocus();
                    alt_contactnumber_ET.requestFocus();
                    send_otp.setText("Get mobile OTP");
                    otpstatus.setText("");
                    otpstatus.setVisibility(View.INVISIBLE);
                    send_otp.setVisibility(View.VISIBLE);
            }
        });

        otp_edt1m.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt1m.length() == 0) {
                    otp_edt1m.requestFocus();
                }
                if (otp_edt1m.length() == 1) {
                    otp_edt1m.clearFocus();
                    otp_edt2m.requestFocus();
                    otp_edt2m.setCursorVisible(true);
                    send_otp.setVisibility(View.VISIBLE);
                    otpstatus.setVisibility(View.INVISIBLE);

                }
            }
        });

        otp_edt2m.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt2m.length() == 0) {
                    otp_edt2m.requestFocus();
                }
                if (otp_edt2m.length() == 1) {
                    otp_edt2m.clearFocus();
                    otp_edt3m.requestFocus();
                    otp_edt3m.setCursorVisible(true);
                    send_otp.setVisibility(View.VISIBLE);
                    otpstatus.setVisibility(View.INVISIBLE);
                }
            }
        });


        otp_edt3m.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt3m.length() == 0) {
                    otp_edt3m.requestFocus();
                }
                if (otp_edt3m.length() == 1) {
                    otp_edt3m.clearFocus();
                    otp_edt4m.requestFocus();
                    otp_edt4m.setCursorVisible(true);
                    send_otp.setVisibility(View.VISIBLE);
                    otpstatus.setVisibility(View.INVISIBLE);

                }
            }
        });


        otp_edt4m.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt4m.length() == 0) {
                    otp_edt4m.requestFocus();
                }
                if (otp_edt4m.length() == 1) {
                    otp_edt4m.requestFocus();
                }
            }
        });
        otp_edt2m.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    otp_edt1m.requestFocus();
                }
                return false;
            }
        });
        otp_edt3m.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp_edt2m.requestFocus();
                }
                return false;
            }
        });
        otp_edt4m.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp_edt3m.requestFocus();
                }
               return false;
            }
        });
        return view;
    }

    public void sendOtpEmail(final String newEmail,final Activity mContext,final String password,final String oldmainuser,final String finalmobileno)
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", newEmail);
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
                    initOtpVerifiaction(otp,mContext,newEmail,password,oldmainuser,finalmobileno);
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

    private void initOtpVerifiaction(final String otp,final Activity mContext,final String emailv,final String password,final String oldmainuser,final String finalmobileno)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View dialogView;
        dialogView = layoutInflater.inflate(R.layout.otpverificationlayout, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
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
                        submitBillingView(dialog,emailv,password,oldmainuser,finalmobileno);
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
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    private void sendOtp(String mobile,String countrycode,String email)
    {

        HashMap<String, String> hm = new HashMap<>();
        hm.put("mobile",mobile);
        hm.put("phone_code",countrycode);
        hm.put("new_email",email);
        Log.d("param",":"+hm);
        otpsucces=0;
        send_otp.setVisibility(View.VISIBLE);
        //send_otp.setText("Get mobile OTP");
        otpstatus.setVisibility(View.INVISIBLE);
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().sendtOtp(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().status == 1) {
                    Log.d("otpre","::"+response.body().data );
                    try {
                        emailstatus.setVisibility(View.GONE);
                        otpid = response.body().data;
                        Log.d("otpid",otpid);
                        String msge=response.body().msg;
                        Toast.makeText(mContext, ""+response.body().msg.toString(), Toast.LENGTH_SHORT).show();
                        alt_contactnumber_ET.clearFocus();
                        send_otp.setText("Resend mobile OTP");
                       /* emailstatus.setTextColor(getActivity().getResources().getColor(R.color.green_color));
                        emailstatus.setText(response.body().msg+"");*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 4) {
                    emailstatus.setVisibility(View.VISIBLE);
                     emailstatus.setTextColor(getActivity().getResources().getColor(R.color.red_color));
                     emailstatus.setText(response.body().msg+"");
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 0) {
                    AppConfig.showToast(response.body().msg.toString());
                } else {
                    AppConfig.showToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                dialog.dismiss();
                AppConfig.showToast(t.getMessage());


            }
        });
    }

    private void otpVerified(String mobile, String otp, final String emailv, final Activity activitiy, final String Oldemail, final String password, final String oldmainuser, final String finalmobileno) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("id", otpid);
        hm.put("otp", otp);
        hm.put("password",password);
        Log.d("param",":"+hm);
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().OtpVerified(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful() && response.body().status == 1)
                {
                    dialog.dismiss();
                    Log.d("otpre","::"+response.body().data );
                    try {
                        String otp = response.body().data;
                        Log.d("otp",otp);
                        otpsucces=1;
                        Toast.makeText(mContext, ""+response.body().msg, Toast.LENGTH_SHORT).show();
                        AskPermissionDialog(emailv,activitiy,Oldemail,password,oldmainuser,finalmobileno);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 3) {
                    dialog.dismiss();
                    Toast.makeText(mContext, ""+response.body().msg, Toast.LENGTH_SHORT).show();
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 0) {
                    dialog.dismiss();
                    Toast.makeText(mContext, ""+response.body().msg, Toast.LENGTH_SHORT).show();
                } else {
                    AppConfig.showToast("Server Error");
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(mContext, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void submitBillingView(final BottomSheetDialog dialog, String newemail, String passwordv, String currentemail, String mobilenumber)
    {

        final ProgressDialog dialognew = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialognew, "Please wait ....");

        HashMap<String, String> hm = new HashMap<>();
        hm.put("new_email", newemail);
        hm.put("password",passwordv);
        hm.put("id",otpid);
        hm.put("current_email",currentemail);
        hm.put("mobile_number",mobilenumber);
        Log.d("hmparam",hm.toString());
       AppConfig.getLoadInterface().updateEmailAddress(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<emergencyModel>() {
            @Override
            public void onResponse(Call<emergencyModel> call, Response<emergencyModel> response) {
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
                    public void onFailure(Call<emergencyModel> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    public void AskPermissionDialog(final String emailv,Activity activitiy,final String Oldemail,final String password,final String oldmainuser,final String finalmobileno)
    {
        //api_error
        LayoutInflater layoutInflater = (LayoutInflater) activitiy.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.beforepaymentdialog, null);
        TextView no =customView.findViewById(R.id.no);
        TextView yes=customView.findViewById(R.id.yes);
        TextView tvOrderStatus=customView.findViewById(R.id.tvOrderStatus);
        AlertDialog.Builder alert = new AlertDialog.Builder(activitiy);
        alert.setView(customView);
        final AlertDialog dialog = alert.create();
        dialog.show();
        tvOrderStatus.setText("Are you sure you want to \n Change Email  ?");
        dialog.setCancelable(false);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                sendOtpEmail(emailv,getActivity(),password,oldmainuser,finalmobileno);
            }
        });

    }

}
