package com.ddkcommunity.fragment.projects;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.mapoptionadapter;
import com.ddkcommunity.interfaces.GegtSettingStatusinterface;
import com.ddkcommunity.interfaces.GetBTCUSDTETHPriceCallback;
import com.ddkcommunity.model.getSettingModel;
import com.ddkcommunity.model.mapoptionmodel;
import com.ddkcommunity.utilies.AppConfig;

import java.math.BigDecimal;
import java.util.ArrayList;

import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowFunctionalityAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mappaymentviewfragment extends Fragment implements View.OnClickListener
{
    RecyclerView recyclerviewGridView;
    Activity mContext;
    TextView Portal;
    AppCompatButton submit_BT;
    LinearLayout mainpart,submenuiteam,paymenthours,btnUsingSamKoin,btnUsingbtc,btnUsingeth,btnUsingUsdt,btnUsingcreditcard;
    ArrayList<mapoptionmodel> mapoptionList;
    String amount,selecetdpackage;
    String action="",usereferrlacode;
    String emailp,passwordp,bidposiiton,bidpositionid,bidamount;

    public Mappaymentviewfragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mapoptionallfragment, container, false);
        mContext = getActivity();
        if (getArguments().getString("action") != null) {
            action= getArguments().getString("action");
        }

        if(action.equalsIgnoreCase("bid"))
        {
            if (getArguments().getString("email") != null) {
                emailp= getArguments().getString("email");
            }
            if (getArguments().getString("password") != null) {
                passwordp= getArguments().getString("password");
            }
            if (getArguments().getString("bidposiiton") != null) {
                bidposiiton= getArguments().getString("bidposiiton");
            }
            if (getArguments().getString("bidposiitonID") != null) {
                bidpositionid= getArguments().getString("bidposiitonID");
            }
            if (getArguments().getString("bidamount") != null) {
                bidamount= getArguments().getString("bidamount");
            }
        }else
        {
            if (action.equalsIgnoreCase("mapwithreferral")) {
                if (getArguments().getString("userenterreferrla") != null) {
                    usereferrlacode = getArguments().getString("userenterreferrla");
                }
            }

            if (getArguments().getString("amount") != null) {
                amount = getArguments().getString("amount");
            }
            if (getArguments().getString("selecetdpackage") != null) {
                selecetdpackage = getArguments().getString("selecetdpackage");
            }
        }
        getViewValue();
        mainpart=view.findViewById(R.id.mainpart);
        paymenthours=view.findViewById(R.id.paymenthours);
        submit_BT=view.findViewById(R.id.submit_BT);
        submenuiteam=view.findViewById(R.id.submenuiteam);
        submit_BT.setVisibility(View.GONE);
        paymenthours.setVisibility(View.VISIBLE);
        submenuiteam.setVisibility(View.GONE);
        Portal=view.findViewById(R.id.Portal);
        //............
        btnUsingSamKoin=view.findViewById(R.id.btnUsingSamKoin);
        btnUsingbtc=view.findViewById(R.id.btnUsingbtc);
        btnUsingeth=view.findViewById(R.id.btnUsingeth);
        btnUsingUsdt=view.findViewById(R.id.btnUsingUsdt);
        btnUsingcreditcard=view.findViewById(R.id.btnUsingcreditcard);
        //.............
        SpannableString content = new SpannableString("Portal");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        Portal.setText(content);
        recyclerviewGridView=view.findViewById(R.id.recyclerviewGridView);
        recyclerviewGridView.setVisibility(View.GONE);
        mainpart.setBackground(mContext.getDrawable(R.color.background));
        submit_BT.setOnClickListener(this);
        btnUsingSamKoin.setOnClickListener(this);
        btnUsingbtc.setOnClickListener(this);
        btnUsingeth.setOnClickListener(this);
        btnUsingUsdt.setOnClickListener(this);
        btnUsingcreditcard.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("M.A.P");
        MainActivity.enableBackViews(true);
    }

    public void getViewValue()
    {
        UserModel.getInstance().getUsdtEthBtcPriceCall(new GetBTCUSDTETHPriceCallback() {
            @Override
            public void getValues(BigDecimal btcPrice, BigDecimal eth, BigDecimal usdt, BigDecimal tron)
            {
                BigDecimal ONE_HUNDRED = new BigDecimal(100);
                BigDecimal btcpercent=UserModel.getInstance().btcBuyPercentage;
                if (UserModel.getInstance().btcBuyPercentage != null) {
                    BigDecimal btcBuyPer = btcPrice.multiply(UserModel.getInstance().btcBuyPercentage).divide(ONE_HUNDRED);
                    BigDecimal ethBuyPer = eth.multiply(UserModel.getInstance().ethBuyPercentage).divide(ONE_HUNDRED);
                    BigDecimal usdtBuyPer = usdt.multiply(UserModel.getInstance().usdtBuyPercentage).divide(ONE_HUNDRED);
                    BigDecimal samkoinBuyPer = UserModel.getInstance().samkoinvalueper.multiply(UserModel.getInstance().samkoinBuyPercentage).divide(ONE_HUNDRED);
                    BigDecimal tronBuyPer = tron.multiply(UserModel.getInstance().tronBuyPercentage).divide(ONE_HUNDRED);

                    BigDecimal btcSellPer = btcPrice.multiply(UserModel.getInstance().btcSellPercentage).divide(ONE_HUNDRED);
                    BigDecimal ethSellPer = eth.multiply(UserModel.getInstance().ethSellPercentage).divide(ONE_HUNDRED);
                    BigDecimal usdtSellPer = usdt.multiply(UserModel.getInstance().usdtSellPercentage).divide(ONE_HUNDRED);
                    BigDecimal samkoinSellPer = UserModel.getInstance().samkoinvalueper.multiply(UserModel.getInstance().samkoinSellPercentage).divide(ONE_HUNDRED);
                    BigDecimal tronSellPer = tron.multiply(UserModel.getInstance().tronSellPercentage).divide(ONE_HUNDRED);

                    UserModel.getInstance().btcBuyPrice = btcBuyPer.add(btcPrice);
                    UserModel.getInstance().btcSellPrice = btcPrice.subtract(btcSellPer);

                    UserModel.getInstance().ethBuyPrice = ethBuyPer.add(eth);
                    UserModel.getInstance().ethSellPrice = eth.subtract(ethSellPer);

                    UserModel.getInstance().usdtBuyPrice = usdtBuyPer.add(usdt);
                    UserModel.getInstance().usdtSellPrice = usdt.subtract(usdtSellPer);

                    UserModel.getInstance().samkoinBuyPrice= samkoinBuyPer.add(UserModel.getInstance().samkoinvalueper);
                    UserModel.getInstance().samkoinSellPrice= UserModel.getInstance().samkoinvalueper.subtract(samkoinSellPer);

                    UserModel.getInstance().tronBuyPrice= tronBuyPer.add(tron);
                    UserModel.getInstance().tronSellPrice= tron.subtract(tronSellPer);

                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent("updatePrice"));
                }
            }
        }, getActivity());

    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnUsingSamKoin)
        {
            getSettingServerOnTab(getActivity(),"samkoin");
        }
        if (v.getId() == R.id.btnUsingbtc)
        {
            getSettingServerOnTab(getActivity(),"btc");
        }
        if (v.getId() == R.id.btnUsingeth)
        {
            getSettingServerOnTab(getActivity(),"eth");
        }
        if (v.getId() == R.id.btnUsingUsdt)
        {
            getSettingServerOnTab(getActivity(),"usdt");
        }
        if (v.getId() == R.id.btnUsingcreditcard)
        {
            getSettingServerOnTab(getActivity(),"creditcard");

        }
    }

    public void getSettingServerOnTab(Activity activity, final String functionname)
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Please wait ....");
        String func="",checkAccountLimit="0";
        if(functionname.equalsIgnoreCase("eth"))
        {
            func="map_send_eth";
        }
        if(functionname.equalsIgnoreCase("btc"))
        {
            func="map_send_btc";
        }
        if(functionname.equalsIgnoreCase("usdt"))
        {
            func="map_send_usdt";
        }
        if(functionname.equalsIgnoreCase("creditcard"))
        {
            func="map_send_credit_card";
        }
        if(functionname.equalsIgnoreCase("samkoin"))
        {
            func="map_send_sam_koin";
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
                        clickevent(functionname);
                        AppConfig.hideLoading(dialog);

                    } else
                    {
                        AppConfig.hideLoading(dialog);
                        ShowFunctionalityAlert(getActivity(), response.body().getMsg());
                    }

                } catch (Exception e)
                {
                    AppConfig.hideLoading(dialog);
                    e.printStackTrace();
                }
            }
        });
    }

    public void clickevent(String actionname)
    {
        if(action.equalsIgnoreCase("mapwithreferral"))
        {
            Fragment fragment = new MapPAyemnASkFragment();
            Bundle arg = new Bundle();
            arg.putString("userenterreferrla",usereferrlacode);
            arg.putString("action", action);
            arg.putString("actiontype", actionname);
            arg.putString("packages", selecetdpackage);
            arg.putString("amount", amount);
            fragment.setArguments(arg);
            MainActivity.addFragment(fragment, false);
         }else if(action.equalsIgnoreCase("bid"))
         {
            Fragment fragment = new MapBidPaymentFragment();
            Bundle arg = new Bundle();
            arg.putString("email",emailp);
            arg.putString("action", "bid");
             arg.putString("actiontype", actionname);
             arg.putString("password", passwordp);
            arg.putString("bidposiiton", bidposiiton);
            arg.putString("bidpositionid",bidpositionid);
            arg.putString("bidamount",bidamount);
            fragment.setArguments(arg);
            MainActivity.addFragment(fragment, false);
        }else
        {
            Fragment fragment = new MapPAyemnASkFragment();
            Bundle arg = new Bundle();
            arg.putString("action", action);
            arg.putString("actiontype", actionname);
            arg.putString("packages", selecetdpackage);
            arg.putString("amount", amount);
            fragment.setArguments(arg);
            MainActivity.addFragment(fragment, true);
        }
    }
}