package com.ddkcommunity.fragment.settingModule;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.ddkcommunity.fragment.activityFragmement;
import com.ddkcommunity.fragment.sfioSelectionFragment;
import com.ddkcommunity.interfaces.GetUSDAndBTCCallback;
import com.ddkcommunity.model.addActivityModel;
import com.ddkcommunity.model.bankDetailsModel;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.utilies.AppConfig;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBankDetailsFragment extends Fragment {

    public AddBankDetailsFragment() {
    }

    View view;
    private Context mContext;
    EditText bsbcode,bankname,accountname,accountno,swiftcode;
    AppCompatButton save;
    int updateapi=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.addbankfragmentlayout, container, false);
        mContext = getActivity();
        //for amount
        bsbcode=view.findViewById(R.id.bsbcode);
        bankname=view.findViewById(R.id.bankname);
        accountname=view.findViewById(R.id.accountname);
        accountno=view.findViewById(R.id.accountno);
        swiftcode=view.findViewById(R.id.swiftcode);
        save=view.findViewById(R.id.save);
        getBabkDetails();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String banknamevl=bankname.getText().toString();
                String accountnam=accountname.getText().toString();
                String accountnova=accountno.getText().toString();
                String swiftcodeva=swiftcode.getText().toString();
                String bsbcodeva=swiftcode.getText().toString();
                addBankDetail(banknamevl,accountnam,accountnova,bsbcodeva,swiftcodeva);
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("Add Bank ");
        MainActivity.enableBackViews(true);
    }

    private void addBankDetail(String bank_name,String account_name,String account_number,String bsb_number,String swift_code)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("bank_name", bank_name);
        hm.put("account_name",account_name);
        hm.put("account_number", account_number);
        hm.put("bsb_number",bsb_number);
        hm.put("swift_code",swift_code);
        Log.d("bank add ", hm.toString());
        AppConfig.getLoadInterface().addBankPRofileDetail(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<sfioModel>() {
            @Override
            public void onResponse(Call<sfioModel> call, Response<sfioModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(getContext(),"server error add activity");
                    }

                } catch (Exception e) {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<sfioModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBabkDetails()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        AppConfig.getLoadInterface().getBankPRofileDetail(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<bankDetailsModel>() {
            @Override
            public void onResponse(Call<bankDetailsModel> call, Response<bankDetailsModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            if(response.body().getData()!=null)
                            {
                                String banknamev=response.body().getData().getBankName();
                                String accName=response.body().getData().getAccountName();
                                String accNo=response.body().getData().getAccountNumber();
                                String swift=response.body().getData().getSwiftCode();
                                String bsd=response.body().getData().getBsbNumber();
                                bsbcode.setText(bsd);
                                bankname.setText(banknamev);
                                accountname.setText(accName);
                                accountno.setText(accNo);
                                swiftcode.setText(swift);
                            }

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(getContext(),"server error add activity");
                    }

                } catch (Exception e) {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<bankDetailsModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
