package com.ddkcommunity.fragment.projects;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.AllTypeCashoutFragmentAdapter;
import com.ddkcommunity.adapters.CashoutFragmenAdapter;
import com.ddkcommunity.adapters.mapoptionadapter;
import com.ddkcommunity.fragment.CashOutFragmentNew;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.fragment.send.SuccessFragmentScan;
import com.ddkcommunity.model.EthModelBalance;
import com.ddkcommunity.model.UserBankList;
import com.ddkcommunity.model.UserBankListDetails;
import com.ddkcommunity.model.UserBankListResponse;
import com.ddkcommunity.model.checkRefferalModel;
import com.ddkcommunity.model.mapLoginModel;
import com.ddkcommunity.model.mapoptionmodel;
import com.ddkcommunity.model.navigationModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.ddkcommunity.utilies.dataPutMethods;
import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowServerPost;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;
import static com.ddkcommunity.utilies.dataPutMethods.showDialogCryptoData;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapOtionAllFragment extends Fragment implements View.OnClickListener
{
    RecyclerView recyclerviewGridView;
    Activity mContext;
    TextView Portal,mapoptiontext;
    LinearLayout submenuiteam,headerinactiviteview;
    ArrayList<mapoptionmodel> mapoptionList;
    public static String activeStatus;
    ImageView copyview,browseview;
    RelativeLayout mapheader;
    TextView expandedListItem;
    CircleProgressBar circularProgressBar2,custom_progress_inner;
    TextView tvAddressCode;

    public MapOtionAllFragment()
    {
    }

    public void ShowUserOnly(Context activity, String apierronamev)
    {
        //api_error
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.alertmapsublayotu, null);
        TextView btnGoHome =customView.findViewById(R.id.btnGoHome);
        TextView apierrorname=customView.findViewById(R.id.apierrorname);
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(customView);
        apierrorname.setText(apierronamev);
        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.setCancelable(false);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Fragment fragment = new Mapsubfragmentclick();
                Bundle arg = new Bundle();
                arg.putString("action", "map");
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment,true);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mapoptionallfragment, container, false);
        mContext = getActivity();
        expandedListItem=view.findViewById(R.id.expandedListItem);
        browseview=view.findViewById(R.id.browseview);
        mapheader=view.findViewById(R.id.mapheader);
        mapoptiontext=view.findViewById(R.id.mapoptiontext);
        if (getArguments().getString("activeStatus") != null) {
            activeStatus= getArguments().getString("activeStatus");
        }
        circularProgressBar2 =view.findViewById(R.id.custom_progress_outer);
        custom_progress_inner =view.findViewById(R.id.custom_progress_inner);
        tvAddressCode=view.findViewById(R.id.tvAddressCode);
        submenuiteam=view.findViewById(R.id.submenuiteam);
        String userReferalCode = App.pref.getString(Constant.USER_REFERAL_CODE, "");
        submenuiteam.setVisibility(View.GONE);
        Portal=view.findViewById(R.id.Portal);
        if(activeStatus.equalsIgnoreCase("1")) {
            mapNavigaton();
        }else
        {
            App.editor.putInt(Constant.Navigationcount, 0);
            App.editor.putString(Constant.Packagename, "");
            App.editor.apply();
            //....
            String Packagename=App.pref.getString(Constant.Packagename, "");
            if(activeStatus.equalsIgnoreCase("1"))
            {
                expandedListItem.setText(Packagename+" | Active");
            }else
            {
                expandedListItem.setText(" Inactive");
            }
            tvAddressCode.setText(userReferalCode);
            int maptoken=App.pref.getInt(Constant.Navigationcount, 0);
            circularProgressBar2.setProgress(maptoken);
            custom_progress_inner.setProgress(maptoken);

            ShowUserOnly(getActivity(),"You registerd successfully . Now please subscribe for access the MAP first.");
        }
        //............
        tvAddressCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.copyPass(tvAddressCode.getText().toString().trim(), "Copy Address", getActivity());
            }
        });

        circularProgressBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.GetSummaryDialog(getActivity());
            }
        });
        //.............
        Portal.setText(userReferalCode);
        headerinactiviteview=view.findViewById(R.id.headerinactiviteview);
        headerinactiviteview.setVisibility(View.GONE);
        mapheader.setVisibility(View.VISIBLE);
        mapoptionList=new ArrayList<>();
        mapoptionList.add(new mapoptionmodel("Funnel",R.drawable.funnel_ico));
        mapoptionList.add(new mapoptionmodel("Group",R.drawable.gorpwkqk));
        mapoptionList.add(new mapoptionmodel("Phase 1 Bonus",R.drawable.phaseoneboun));
        mapoptionList.add(new mapoptionmodel("Direct Refferal Bonus",R.drawable.directreferral));
        mapoptionList.add(new mapoptionmodel("Group Bonus",R.drawable.group_c));
        mapoptionList.add(new mapoptionmodel("Power of 10 Bonus",R.drawable.powerxlogo));
        mapoptionList.add(new mapoptionmodel("Platinum Bonus",R.drawable.platinum_new));
        mapoptionList.add(new mapoptionmodel("Titanium Bonus",R.drawable.titaniumic));
        mapoptionList.add(new mapoptionmodel("Daily Rewards Bonus",R.drawable.daily_rearws));
        mapoptionList.add(new mapoptionmodel("Overflow",R.drawable.overflow_b));
        mapoptionList.add(new mapoptionmodel("M.A.P. Activity",R.drawable.mapactivtiy));
        recyclerviewGridView=view.findViewById(R.id.recyclerviewGridView);
        mapoptionadapter allTypeCashoutFragmentAdapter = new mapoptionadapter("","main",mapoptionList, getActivity());
        recyclerviewGridView.setAdapter(allTypeCashoutFragmentAdapter);
        copyview=view.findViewById(R.id.copyview);
        copyview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConfig.copyPass(Portal.getText().toString().trim(), "Copy Address", getActivity());
            }
        });
        browseview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mapurl= App.pref.getString(Constant.MApLoginURl,"");
                String linkvalue=mapurl;
                if(linkvalue!=null)
                {
                    //send view
                    Fragment fragment = new SendLinkFragment();
                    Bundle arg = new Bundle();
                    arg.putString("link", linkvalue);
                    fragment.setArguments(arg);
                    MainActivity.addFragment(fragment, true);
                }else
                {
                    Toast.makeText(mContext, "Link not available ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("M.A.P");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.searchlayout.setVisibility(View.GONE);

        if(activeStatus.equalsIgnoreCase("1"))
        {
            MainActivity.prepareListData(getActivity(),"map");
        }

    }

    @Override
    public void onClick(View v)
    {
       /* if (v.getId() == R.id.submit_BT)
        {
            Fragment fragment = new SuccessFragmentScan();
            Bundle arg = new Bundle();
            arg.putString("action", "map");
            fragment.setArguments(arg);
            MainActivity.addFragment(fragment,true);
        }*/
    }

    private void mapNavigaton()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken=App.pref.getString(Constant.MAPToken, "");
        Call<navigationModel> call = AppConfig.getLoadInterfaceMap().mapNavigationData(maptoken);
        call.enqueue(new retrofit2.Callback<navigationModel>() {
            @Override
            public void onResponse(Call<navigationModel> call, Response<navigationModel> response) {

                pd.dismiss();
                if (response.isSuccessful())
                {
                    try
                    {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            if (response.body().getStatus() == 1)
                            {
                                int packagepercent=response.body().getData();
                                String packagename=response.body().getPackage().getPackageName();
                                App.editor.putInt(Constant.Navigationcount, packagepercent);
                                App.editor.putString(Constant.Packagename, packagename);
                                App.editor.apply();

                            }else
                            {
                                App.editor.putInt(Constant.Navigationcount, 0);
                                App.editor.putString(Constant.Packagename, "");
                                App.editor.apply();
                            }

                            //.......
                            String userReferalCode = App.pref.getString(Constant.USER_REFERAL_CODE, "");
                            String Packagename=App.pref.getString(Constant.Packagename, "");
                            if(activeStatus.equalsIgnoreCase("1"))
                            {
                                expandedListItem.setText(Packagename+" | Active");
                            }else
                            {
                                expandedListItem.setText(Packagename+" | Inactive");
                            }
                            tvAddressCode.setText(userReferalCode);
                            int maptoken=App.pref.getInt(Constant.Navigationcount, 0);
                            circularProgressBar2.setProgress(maptoken);
                            custom_progress_inner.setProgress(maptoken);
                            //.........
                            MainActivity.prepareListData(getActivity(),"map");

                        } else {
                            Log.d("context","::");
                            ShowApiError(mContext,"server error progress");
                        }

                    } catch (Exception e)
                    {
                       if(pd.isShowing())
                           pd.dismiss();

                        e.printStackTrace();
                    }
                } else {
                    if(pd.isShowing())
                        pd.dismiss();
                    ShowApiError(getActivity(),"server error in progress");
                }
            }

            @Override
            public void onFailure(Call<navigationModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });

    }

}