package com.ddkcommunity.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.CardTypeAdapter;
import com.ddkcommunity.fragment.projects.SelectPaymentPoolingFragment;
import com.ddkcommunity.fragment.send.SuccessFragmentScan;
import com.ddkcommunity.interfaces.GetCryptoSubscriptionResponse;
import com.ddkcommunity.model.CardName;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.checkRefferalModel;
import com.ddkcommunity.model.mapSubscriptionModel;
import com.ddkcommunity.model.sfioAddModelsee;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.ddkcommunity.utilies.CreditCardEditTextNew;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.stripe.android.CardUtils;
import com.stripe.android.Stripe;
import com.stripe.android.StripeTextUtils;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardNumberEditText;
import com.stripe.android.view.ExpiryDateEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreditCardPaymentSFIOFragment extends Fragment {

    public String input_amount,conversion_rate,sam_koin_conversion,fee,total_usdt_subscription;

    public CreditCardPaymentSFIOFragment()
    {

    }
    UserResponse userData;
    TextView paytotal;
    private ExpiryDateEditText etExpiryDate;
    private CardNumberEditText etCardNumber;
    private CreditCardEditTextNew cardCVVEt, cardNameEt;
    private RecyclerView rvCardList;
    private Context mContext;
    String action="",usereferrlacode;
    TextView  subscriptionAmount,FeesAmount,TotalAmount;
    LinearLayout mainbotton;
    String ownershipjoin_status,ownershiptype,name,email,contact,address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay_using_credit_card, container, false);
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

        view.findViewById(R.id.btnGoBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mainbotton=view.findViewById(R.id.mainbotton);
        mainbotton.setVisibility(View.VISIBLE);
        userData = AppConfig.getUserData(getContext());
        subscriptionAmount=view.findViewById(R.id.subscriptionAmount);
        FeesAmount=view.findViewById(R.id.FeesAmount);
        TotalAmount=view.findViewById(R.id.TotalAmount);
        BigDecimal inputa=new BigDecimal(input_amount);
        paytotal=view.findViewById(R.id.paytotal);
        paytotal.setVisibility(View.GONE);
        BigDecimal feea=new BigDecimal(fee);
        BigDecimal total=inputa.add(feea);
        subscriptionAmount.setText("Subscription Amount : "+input_amount);
        FeesAmount.setText("Fee : "+fee);
        TotalAmount.setText("Total : "+total);
         view.findViewById(R.id.submit_BT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog dialog = new ProgressDialog(mContext);
                AppConfig.showLoading(dialog, "Checking Card Details..");

                Card card = getCard();

                if (card != null) {
                    boolean validation = card.validateCard();
                    if (validation) {
                        Stripe stripe = null;
                        String paymentkeydeta= App.pref.getString(Constant.Strip_Payment_Key,"");
                        stripe=new Stripe(mContext,paymentkeydeta);
                       // stripe = new Stripe(mContext,stripekeysubsciption);
                        /*pk_live_MPDfhcqUcSIJ2pe5yI95FRDw*/
                        /*"pk_test_SEzNBwE6G7lELdq63At6lvEV"*/
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        // Send token to your server
                                        AppConfig.hideLoading(dialog);
                                        String tokenId = token.getId();
                                        Log.d("credit card",tokenId);
                                        sendOtp(tokenId);
                                    }

                                    public void onError(Exception error) {
                                        // Show localized error message
                                        AppConfig.hideLoading(dialog);
                                        Toast.makeText(mContext,
                                                error.toString(),
                                                Toast.LENGTH_LONG
                                        ).show();

                                    }
                                }
                        );
                    } else if (!card.validateNumber()) {
                        AppConfig.hideLoading(dialog);
                        AppConfig.showToast("Error : " + "The card number that you entered is invalid.");
                    } else if (!card.validateExpiryDate()) {
                        AppConfig.hideLoading(dialog);
                        AppConfig.showToast("Error : " + "The expiration date that you entered is invalid.");
                    } else if (!card.validateCVC()) {
                        AppConfig.showToast("Error : " + "The CVC code that you entered is invalid.");
                        AppConfig.hideLoading(dialog);
                    } else {
                        AppConfig.showToast("Error : " + "The card details that you entered are invalid.");
                        AppConfig.hideLoading(dialog);
                    }
                } else {
                    AppConfig.showToast("Error : " + "Enter Card Details");
                    AppConfig.hideLoading(dialog);
                }
            }
        });
        initCardView(view);
        paytotal.setText("Pay : "+input_amount);
        return view;
    }

    private void sendOtp(final String tokenid) {
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
                    initOtpVerifiaction(tokenid,otp,getActivity());
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

    private void initOtpVerifiaction(final String tokenid,final String otp,final Activity mContext)
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
                StringBuffer str_top = new StringBuffer();
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
                                sendDataServer(dialog,pd,getActivity(),input_amount,tokenid);
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

    private void sendDataServer(final BottomSheetDialog dialog,final ProgressDialog pd,final Activity activity, final String input_amount, final String transaction_id)
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("iv", App.pref.getString(Constant.IVPARAM, ""));
        hm.put("key", App.pref.getString(Constant.KEYENCYPARAM, ""));
        hm.put("amount", input_amount);
        hm.put("fee", fee);
        hm.put("transaction_id", transaction_id);
        hm.put("ownership",ownershipjoin_status);
        hm.put("ownership_type",ownershiptype);
        hm.put("name",name);
        hm.put("email",email);
        hm.put("contact",contact);
        hm.put("address",address);
        Log.d("paramterHM",hm+"");
        AppConfig.getLoadInterface().addSFIOAmt(AppConfig.getStringPreferences(activity, Constant.JWTToken), hm).enqueue(new Callback<sfioAddModelsee>() {
            @Override
            public void onResponse(Call<sfioAddModelsee> call, Response<sfioAddModelsee> response) {
                try
                {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        pd.dismiss();
                        if (response.body().getStatus()==1)
                        {
                                String text_id=response.body().getData().getTxtId();
                                final String emailid=userData.getUser().getEmail();
                                dialog.dismiss();
                                Fragment fragment = new SuccessFragmentScan();
                                Bundle arg = new Bundle();
                                arg.putString("action", "sfio");
                                arg.putString("actionamnt", "success");
                                arg.putString("message", response.body().getMsg().toString());
                                arg.putString("email", emailid);
                                fragment.setArguments(arg);
                                MainActivity.addFragment(fragment, false);
                        }else
                        {
                          /*  final String emailid=userData.getUser().getEmail();
                            dialog.dismiss();
                            Fragment fragment = new SuccessFragmentScan();
                            Bundle arg = new Bundle();
                            arg.putString("action", "sfio");
                            arg.putString("actionamnt", "failed");
                            arg.putString("email", emailid);
                            fragment.setArguments(arg);
                            MainActivity.addFragment(fragment, true);
                          */  Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pd.dismiss();
                        ShowApiError(getContext(),"server error check-mail-exist");
                    }

                } catch (Exception e)
                {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<sfioAddModelsee> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCardView(View view) {
        cardNameEt = view.findViewById(R.id.etCardHolderName);
        cardCVVEt = view.findViewById(R.id.etCVCNumber);
        rvCardList = view.findViewById(R.id.rvCardList);

        etCardNumber = view.findViewById(R.id.etCardNumber);
        etExpiryDate = view.findViewById(R.id.etExpiryDate);

        etCardNumber.setErrorColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
        etExpiryDate.setErrorColor(ContextCompat.getColor(getActivity(), R.color.colorRed));

        final ArrayList<CardName> cardTypeList = new ArrayList<>();
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.ic_visa1), "Visa"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.ic_mastercard), "MasterCard"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.ic_amex), "American Express"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.ic_discover), "Discover"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.bt_ic_jcb), "JCB"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.bt_ic_unionpay), "UnionPay"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.bt_ic_diners_club), "Diners Club"));
        cardTypeList.add(new CardName(ContextCompat.getDrawable(mContext, R.drawable.bt_ic_unknown), "Unknown"));

        final CardTypeAdapter cardTypeAdapter = new CardTypeAdapter(cardTypeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        rvCardList.setLayoutManager(mLayoutManager);
        rvCardList.setItemAnimator(new DefaultItemAnimator());
        rvCardList.setAdapter(cardTypeAdapter);

        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String source = s.toString();
                int length = source.length();

                if (length >= 4) {
                    String cardType = CardUtils.getPossibleCardType(etCardNumber.getText().toString().trim());
                    cardTypeAdapter.updateData(CardUtils.getPossibleCardType(etCardNumber.getText().toString().trim()));
                    int po = 0;
                    for (CardName cardName : cardTypeList
                    ) {
                        if (cardName.cardName.equalsIgnoreCase(cardType)) {
                            po = cardTypeList.indexOf(cardName);
                            break;
                        }
                    }
                    rvCardList.smoothScrollToPosition(po);
                    if (CardUtils.getPossibleCardType(etCardNumber.getText().toString().trim()).equalsIgnoreCase("American Express")) {
                        mISAmEx = true;
                        setEditTextMaxLength(4);
                    } else {
                        setEditTextMaxLength(3);
                        mISAmEx = false;
                    }
                } else {
                    cardTypeAdapter.updateData("unknown");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setEditTextMaxLength(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        cardCVVEt.setFilters(filterArray);
    }

    private boolean mISAmEx = false;

    public Card getCard() {
        String cardNumber = etCardNumber.getCardNumber();
        int[] cardDate = etExpiryDate.getValidDateFields();
        if (cardNumber == null || cardDate == null || cardDate.length != 2) {
            return null;
        }

        // CVC/CVV is the only field not validated by the entry control itself, so we check here.
        int requiredLength = mISAmEx ? Card.CVC_LENGTH_AMERICAN_EXPRESS : Card.CVC_LENGTH_COMMON;
        String cvcValue = cardCVVEt.getText().toString();
        if (StripeTextUtils.isBlank(cvcValue) || cvcValue.length() != requiredLength) {
            return null;
        }

        return new Card(cardNumber, cardDate[0], cardDate[1], cvcValue);
    }

}
