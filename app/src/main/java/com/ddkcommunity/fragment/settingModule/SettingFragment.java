package com.ddkcommunity.fragment.settingModule;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
import com.ddkcommunity.model.getSettingModel;
import com.ddkcommunity.utilies.AppConfig;

import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowFunctionalityAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    public SettingFragment() {

    }

    LinearLayout conatcsupport,changeEmail,account_layout,banklayout,emergencyContactlayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.settingfragment, container, false);
        account_layout=view.findViewById(R.id.account_layout);
        banklayout=view.findViewById(R.id.banklayout);
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
                        AppConfig.hideLoading(dialog);
                        MainActivity.addFragment(new changeEmailFragment(), true);

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

}