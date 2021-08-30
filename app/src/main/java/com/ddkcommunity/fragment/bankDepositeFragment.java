package com.ddkcommunity.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.LoadInterface;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.BankDepositeAdapter;
import com.ddkcommunity.adapters.CountryListAdapter;
import com.ddkcommunity.adapters.SFIOAdapter;
import com.ddkcommunity.adapters.bankDepositieAdaptermd;
import com.ddkcommunity.adapters.cryptoListAdapter;
import com.ddkcommunity.fragment.projects.TermsAndConsitionSubscription;
import com.ddkcommunity.fragment.send.SuccessFragmentScan;
import com.ddkcommunity.interfaces.GetUSDAndBTCCallback;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.TransactionFeeData;
import com.ddkcommunity.model.TransactionFeesResponse;
import com.ddkcommunity.model.bankDepositeModel;
import com.ddkcommunity.model.bankLstModel;
import com.ddkcommunity.model.currencyModelClick;
import com.ddkcommunity.model.depositeModelSaveData;
import com.ddkcommunity.model.sfioModel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowServerPost;

/**
 * A simple {@link Fragment} subclass.
 */
public class bankDepositeFragment extends Fragment {

    public bankDepositeFragment() {
    }

    View view;
    private Context mContext;
    AppCompatButton submit;
    RecyclerView rvRecycle;
    private UserResponse userData;
    ArrayList<bankDepositeModel.Datum> bankData;
    ArrayList<bankLstModel.Datum> currencyData;
    TextView convertto,finalamount;
    EditText amount;
    LinearLayout convertolayout;
    String  input_amount;
    String ratevalue="",currenccode="";
    public static String bankidsele="";
    String ownershipjoin_status,ownershiptype,name,email,contact,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.bankdepositielayout, container, false);
        mContext = getActivity();
        if (getArguments().getString("input_amount") != null)
        {
            input_amount= getArguments().getString("input_amount");
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

        convertto=view.findViewById(R.id.convertto);
        finalamount=view.findViewById(R.id.finalamount);
        amount=view.findViewById(R.id.amount);
        amount.setText(input_amount+"");
        userData = AppConfig.getUserData(mContext);
        submit=view.findViewById(R.id.submit);
        latestDDKPrice();
        convertolayout=view.findViewById(R.id.convertolayout);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        getPriceConvert();
        getActivitydata();
        convertolayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialogCryptoData(convertto,getActivity());
            }
        });
        //......
        //for amount
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amountvlaue=amount.getText().toString();
                if(amountvlaue.equalsIgnoreCase(""))
                {
                    Toast.makeText(mContext, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }else
                {
                    BigDecimal etAmountvalue = new BigDecimal(amountvlaue);
                    if(amount.getText().toString().equalsIgnoreCase("."))
                    {

                    }else
                    {
                        String bankid=bankidsele;
                        String amountval=amount.getText().toString();
                        String converted_amountval=finalamount.getText().toString();
                        String conversionrateval=ratevalue;
                        String currencyval=currenccode;
                        sfioBankDepositeSave(bankid,amountval,converted_amountval,conversionrateval,currencyval);
                        //..........

                    }

                }

            }
        });

        return view;
    }

    private void latestDDKPrice()
    {
        UserModel.getInstance().getUSDCall(new GetUSDAndBTCCallback() {
            @Override
            public void getValues(BigDecimal btc, BigDecimal usd) {
                if (usd != null) {
                    BigDecimal ONE_HUNDRED = new BigDecimal(100);
                    BigDecimal buy = usd.multiply(UserModel.getInstance().ddkBuyPercentage).divide(ONE_HUNDRED);
                    BigDecimal sell = usd.multiply(UserModel.getInstance().ddkSellPercentage).divide(ONE_HUNDRED);

                }
            }
        }, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Bank Deposit");
        MainActivity.enableBackViews(true);
    }

    private void sfioBankDepositeSave(String bankid,String amountval,String converted_amountval,String conversionrateval,String currencyval)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("bank_id", bankid);
        hm.put("amount", amountval);
        hm.put("converted_amount", converted_amountval);
        hm.put("conversion_rate", conversionrateval);
        hm.put("currency",currencyval);
        hm.put("ownership",ownershipjoin_status);
        hm.put("ownership_type",ownershiptype);
        hm.put("name",name);
        hm.put("email",email);
        hm.put("contact",contact);
        hm.put("address",address);
        Log.d("hm",hm.toString());
        AppConfig.getLoadInterface().getSFIOBankDepositeSave(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<depositeModelSaveData>() {
            @Override
            public void onResponse(Call<depositeModelSaveData> call, Response<depositeModelSaveData> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            final String emailid=userData.getUser().getEmail();
                            Fragment fragment = new SuccessFragmentScan();
                            Bundle arg = new Bundle();
                            arg.putString("action", "sfioSave");
                            arg.putString("actionamnt", "success");
                            arg.putString("message", response.body().getMsg().toString());
                            arg.putString("email", emailid);
                            fragment.setArguments(arg);
                            MainActivity.addFragment(fragment, false);

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(mContext, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pd.dismiss();
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
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
            public void onFailure(Call<depositeModelSaveData> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void convertAmountToOther(String name,String amount)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("to", name);
        hm.put("amount", amount);
        AppConfig.getLoadInterface().getUSDTToConvert(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<currencyModelClick>() {
            @Override
            public void onResponse(Call<currencyModelClick> call, Response<currencyModelClick> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getSuccess().equalsIgnoreCase("true"))
                        {
                            pd.dismiss();
                            BigDecimal finalamt=new BigDecimal(response.body().getResult());
                            BigDecimal roundhaldv = finalamt.setScale(6, BigDecimal.ROUND_FLOOR);
                            finalamount.setText(roundhaldv.toPlainString()+"");
                            ratevalue=response.body().getInfo().getRate()+"";
                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(mContext, ""+response.body().getInfo(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
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
            public void onFailure(Call<currencyModelClick> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getActivitydata()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("country_id", userData.getUser().country.get(0).id);
        AppConfig.getLoadInterface().getBankDetails(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<bankDepositeModel>() {
            @Override
            public void onResponse(Call<bankDepositeModel> call, Response<bankDepositeModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            bankData=new ArrayList<>();
                            bankData.addAll(response.body().getData());
                            BankDepositeAdapter mAdapter = new BankDepositeAdapter(bankData, getActivity());
                            rvRecycle.setAdapter(mAdapter);

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(mContext, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            bankData=new ArrayList<>();
                            BankDepositeAdapter mAdapter = new BankDepositeAdapter(bankData, getActivity());
                            rvRecycle.setAdapter(mAdapter);
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
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
            public void onFailure(Call<bankDepositeModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPriceConvert()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();

        LoadInterface apiservice = AppConfig.getClient().create(LoadInterface.class);
        Call<bankLstModel> call = apiservice.getCurrenecySfioList();
        call.enqueue(new Callback<bankLstModel>() {
            @Override
            public void onResponse(Call<bankLstModel> call, Response<bankLstModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            currencyData=new ArrayList<>();
                            currencyData.addAll(response.body().getData());

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(mContext, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            currencyData=new ArrayList<>();
                            BankDepositeAdapter mAdapter = new BankDepositeAdapter(bankData, getActivity());
                            rvRecycle.setAdapter(mAdapter);
                        }

                    } else {
                        Log.d("context","::");
                        ShowApiError(mContext,"server error direct-bonus");
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
            public void onFailure(Call<bankLstModel> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Response getting failed", Toast.LENGTH_SHORT).show();
                // hideProgress();
            }
        });

    }

    public void showDialogCryptoData(final TextView txt_ET,final Activity useravituv)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogView = layoutInflater.inflate(R.layout.select_popup_country, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final EditText searchEt = dialogView.findViewById(R.id.search_ET);

        final bankDepositieAdaptermd adapterCredential = new bankDepositieAdaptermd(currencyData, mContext,new bankDepositieAdaptermd.SetOnItemClickListener()
        {
            @Override
            public void onItemClick(final String name,final String id,final String code) {
                txt_ET.setText(code.toUpperCase()+" " +name);
                currenccode=code;
                //...........
                dialog.dismiss();
                convertAmountToOther(code,amount.getText().toString());
            }
        });
        recyclerView.setAdapter(adapterCredential);

        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (searchEt.getText().toString().length() > 0)
                {
                    filterCountry(searchEt.getText().toString(),currencyData,adapterCredential);
                } else {
                    adapterCredential.updateData(currencyData);
                }
            }
        });

        dialog.show();
    }

    public void filterCountry(String newText, ArrayList<bankLstModel.Datum> countrygender,bankDepositieAdaptermd adapterCredential)
    {
        //new array list that will hold the filtered data
        ArrayList<bankLstModel.Datum> temp = new ArrayList();
        for(bankLstModel.Datum d: countrygender){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getCode().toLowerCase().contains(newText.toLowerCase()) || d.getName().toLowerCase().contains(newText.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        adapterCredential.updateData(temp);
    }

}
