package com.ddkcommunity.fragment;


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
import android.view.inputmethod.InputMethodManager;
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
import com.ddkcommunity.activities.OTPActivity;
import com.ddkcommunity.fragment.projects.CreditCardPaymentFragment;
import com.ddkcommunity.fragment.projects.SelectPaymentPoolingFragment;
import com.ddkcommunity.fragment.send.SuccessFragmentScan;
import com.ddkcommunity.fragment.settingModule.editOwnershipFragment;
import com.ddkcommunity.interfaces.GetCryptoSubscriptionResponse;
import com.ddkcommunity.interfaces.GetUSDAndBTCCallback;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.addActivityModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ReplacecommaValue;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSFIOfrgament extends Fragment {

    public AddSFIOfrgament() {
    }

    View view;
    private Context mContext;
    TextView amount;
    AppCompatButton submit;
    LinearLayout selection_type_layout,form_layout,creditcard_layout,bankdepositelayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.addsfiolayout, container, false);
        mContext = getActivity();
        latestDDKPrice();
        //for amount
        selection_type_layout=view.findViewById(R.id.selection_type_layout);
        form_layout=view.findViewById(R.id.form_layout);
        amount=view.findViewById(R.id.amount);
        creditcard_layout=view.findViewById(R.id.creditcard_layout);
        bankdepositelayout=view.findViewById(R.id.bankdepositelayout);
        submit=view.findViewById(R.id.submit);
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

                        //..........

                        Fragment fragment = new editOwnershipFragment();
                        Bundle arg = new Bundle();
                        arg.putString("input_amount", amountvlaue);
                        arg.putString("fee", fee);
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);

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
        MainActivity.setTitle("Add SFIO");
        MainActivity.enableBackViews(true);
    }

}
