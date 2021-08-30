package com.ddkcommunity.fragment.settingModule;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.ContactUsFragemnt;
import com.ddkcommunity.fragment.ProfileFragment;
import com.ddkcommunity.fragment.SFIOShowFragmement;
import com.ddkcommunity.fragment.projects.PayUsingCoinPaymentFragment;
import com.ddkcommunity.fragment.projects.PayUsingCryptoFragment;
import com.ddkcommunity.fragment.projects.PayUsingDDKFragment;
import com.ddkcommunity.interfaces.GegtSettingStatusinterface;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.CountryResponse;
import com.ddkcommunity.model.getSettingModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowFunctionalityAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    public SettingFragment() {

    }

    public static String[] gender;
    public static String[] stateId;
    public static String[] countryCode;
    public static ArrayList<Country> countrygender;
    LinearLayout conatcsupport,changeEmail,account_layout,banklayout,emergencyContactlayout;
    private static List<Country> countryList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.settingfragment, container, false);
        account_layout=view.findViewById(R.id.account_layout);
        banklayout=view.findViewById(R.id.banklayout);
        getCountryCall();
        conatcsupport=view.findViewById(R.id.conatcsupport);
        changeEmail=view.findViewById(R.id.changeEmail);
        emergencyContactlayout=view.findViewById(R.id.emergencyContactlayout);
        account_layout.setOnClickListener(this);
        banklayout.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        conatcsupport.setOnClickListener(this);
        emergencyContactlayout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.conatcsupport:
                MainActivity.addFragment(new ContactUsFragemnt(), true);
                break;

            case R.id.changeEmail:
                getSettingServerOnTab(getActivity(),"sfio");
                break;

            case R.id.account_layout:
                 MainActivity.addFragment(new ProfileFragment(), true);
                break;

            case R.id.banklayout:
                MainActivity.addFragment(new AddBankDetailsFragment(), true);
                break;

            case R.id.emergencyContactlayout:
                MainActivity.addFragment(new emergencyConatcFragment(), true);
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("Setting");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
    }

    public void getSettingServerOnTab(final Activity activity, final String functionname)
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Please wait ....");
        String func=functionname,checkAccountLimit="1";
        {
            func=functionname;
        }
        UserModel.getInstance().getSettignSatusView(activity,func,checkAccountLimit,new GegtSettingStatusinterface()
        {
            @Override
            public void getResponse(Response<getSettingModel> response)
            {
                //   AppConfig.hideLoader();
                try
                {
                    if (response.body().getStatus() == 1)
                    {
                        getChangeEmailStatus(dialog);

                    } else
                    {
                        AppConfig.hideLoading(dialog);
                        ShowFunctionalityAlert(activity, response.body().getMsg());
                    }

                } catch (Exception e)
                {
                    AppConfig.hideLoading(dialog);
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCountryCall() {
        if (AppConfig.isInternetOn()) {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialog, "Getting Countries...");

            Call<ResponseBody> call = AppConfig.getLoadInterface().countryList();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseData = response.body().string();
                            JSONObject object = new JSONObject(responseData);
                            Log.d("countrydata",object.toString());
                            if (object.getInt(Constant.STATUS) == 1) {

                                CountryResponse registerResponse = new Gson().fromJson(responseData, CountryResponse.class);
                                countryList = registerResponse.getData();

                                countrygender=new ArrayList<>();
                                for(int i=0;i<countryList.size();i++)
                                {
                                    countrygender.add(countryList.get(i));
                                }

                                gender = new String[countryList.size()];
                                stateId = new String[countryList.size()];
                                countryCode = new String[countryList.size()];

                                for (int i = 0; i < countryList.size(); i++) {
                                    gender[i] = countryList.get(i).getCountry();
                                    stateId[i] = countryList.get(i).getId();
                                    countryCode[i] = countryList.get(i).getPhoneCode();
                                }
                            } else if (object.getInt(Constant.STATUS) == 3) {
                                AppConfig.showToast(object.getString("msg"));
                                AppConfig.openSplashActivity(getActivity());
                            } else {
                                AppConfig.showToast(object.getString("msg"));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ShowApiError(getActivity(),"server error in country-list");
                    }
                    AppConfig.hideLoading(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppConfig.hideLoading(dialog);
                    t.printStackTrace();
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    private void getChangeEmailStatus(final ProgressDialog pd)
    {
        AppConfig.getLoadInterface().getChangeEmail(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ARPWalletBalanceModel>() {
            @Override
            public void onResponse(Call<ARPWalletBalanceModel> call, Response<ARPWalletBalanceModel> response) {
                try {
                    AppConfig.hideLoading(pd);
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            MainActivity.addFragment(new changeEmailFragment(), true);

                        }else
                        {
                            AskPermissionDialog(getActivity(),response.body().getMsg());
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(getActivity(),"server error userauth/change-mail-status api");
                    }

                } catch (Exception e) {
                    AppConfig.hideLoading(pd);
                    ShowApiError(getActivity(),"server error userauth/change-mail-status api");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ARPWalletBalanceModel> call, Throwable t)
            {
                AppConfig.hideLoading(pd);
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void AskPermissionDialog(Activity activitiy,final String msg)
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
        tvOrderStatus.setText(msg);
        dialog.setCancelable(false);
        no.setVisibility(View.GONE);
        yes.setText("OK");
        /*no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
*/
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

    }

}