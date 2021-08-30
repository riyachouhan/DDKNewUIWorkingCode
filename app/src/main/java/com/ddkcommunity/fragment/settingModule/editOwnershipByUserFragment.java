package com.ddkcommunity.fragment.settingModule;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.directreferralbidAdpater;
import com.ddkcommunity.fragment.sfioSelectionFragment;
import com.ddkcommunity.model.arpstausModel;
import com.ddkcommunity.model.owneeshipstausModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class editOwnershipByUserFragment extends Fragment implements View.OnClickListener
{
    Activity mContext;
    TextView packageamount,titel_main;
    ArrayList<ARPHistoryModel.Datum> Listtab1;
    EditText name_ET,contact_ET,email_ET,address_ET;
    AppCompatButton submit_BT;
    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout nolayput;
    String input_amount,fee;
    int olduserstatus=0;
    LinearLayout radioview,ownerhsiview;

    public editOwnershipByUserFragment()
    {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.editownershipfragment, container, false);
        mContext = getActivity();
        titel_main=view.findViewById(R.id.titel_main);
        radioview=view.findViewById(R.id.radioview);
        nolayput=view.findViewById(R.id.nolayput);
        packageamount=view.findViewById(R.id.packageamount);
        radioGroup=view.findViewById(R.id.radioGroup);
        submit_BT=view.findViewById(R.id.submit_BT);
        name_ET=view.findViewById(R.id.name_ET);
        titel_main.setVisibility(View.GONE);
        packageamount.setVisibility(View.VISIBLE);
        radioview.setVisibility(View.GONE);
        nolayput.setVisibility(View.VISIBLE);
        getOwnershipStatus();
        contact_ET=view.findViewById(R.id.contact_ET);
        email_ET=view.findViewById(R.id.email_ET);
        address_ET=view.findViewById(R.id.address_ET);
        submit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                    String namev=name_ET.getText().toString();
                    String packageam=packageamount.getText().toString();
                    String contactv=contact_ET.getText().toString();
                    String emailv=email_ET.getText().toString();
                    String addressv=address_ET.getText().toString();
                            if(namev.equalsIgnoreCase(""))
                            {
                                Toast.makeText(getActivity(), "Please enter Name", Toast.LENGTH_SHORT).show();
                            }else if(contactv.equalsIgnoreCase(""))
                            {
                                Toast.makeText(getActivity(), "Please enter Contact No", Toast.LENGTH_SHORT).show();
                            }else if(emailv.equalsIgnoreCase(""))
                            {
                                Toast.makeText(getActivity(), "Please enter Email", Toast.LENGTH_SHORT).show();
                            }else if(addressv.equalsIgnoreCase(""))
                            {
                                Toast.makeText(getActivity(), "Please enter Address", Toast.LENGTH_SHORT).show();
                            }else {
                                getOwnerShipData(packageam,namev, emailv, contactv, addressv);
                            }
            }
        });

        packageamount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Listtab1=new ArrayList<>();
                ARPHistoryModel.Datum m1=new ARPHistoryModel.Datum();
                m1.setName("Join");
                Listtab1.add(m1);

                ARPHistoryModel.Datum m2=new ARPHistoryModel.Datum();
                m2.setName("Entity");
                Listtab1.add(m2);
                showDialogView(packageamount, mContext, Listtab1);
               // setCredentialPopup(inflater,packageamount);

            }
        });
        return view;
    }

    public void setCredentialPopup(LayoutInflater inflater,final TextView packageamount) {
        View view1 = inflater.inflate(R.layout.popup_diglog, null, false);
        final PopupWindow pw = new PopupWindow(view1, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        RecyclerView recyclerView = view1.findViewById(R.id.recycler_view);
        final EditText searchEt = view1.findViewById(R.id.search_ET);
        searchEt.setVisibility(View.GONE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final directreferralbidAdpater adapterCredential = new directreferralbidAdpater(Listtab1, mContext,new directreferralbidAdpater.SetOnItemClickListener()
        {
            @Override
            public void onItemClick(final String wallet_code)
            {
                //...........
                packageamount.setText(wallet_code+"");
                pw.dismiss();
            }
        });
        recyclerView.setAdapter(adapterCredential);
        pw.showAtLocation(packageamount, Gravity.TOP | Gravity.LEFT, dataPutMethods.locateView(packageamount).left, dataPutMethods.locateView(packageamount).bottom);
    }

    public void showDialogView(final TextView packageamount, final Context mContext, final ArrayList<ARPHistoryModel.Datum> Listtab1)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogView = layoutInflater.inflate(R.layout.popup_wallet_pooling, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        final EditText searchEt = dialogView.findViewById(R.id.search_ET);
        final TextView titel_mainhin=dialogView.findViewById(R.id.titel_mainhin);
        searchEt.setVisibility(View.GONE);
        titel_mainhin.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final directreferralbidAdpater adapterCredential = new directreferralbidAdpater(Listtab1, mContext,new directreferralbidAdpater.SetOnItemClickListener()
        {
            @Override
            public void onItemClick(final String wallet_code)
            {
                //...........
                packageamount.setText(wallet_code+"");
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapterCredential);
        dialog.show();
    }

    private void getOwnershipStatus()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        AppConfig.getLoadInterface().getSFIOOwnershipData(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ownershipStatusModel>() {
            @Override
            public void onResponse(Call<ownershipStatusModel> call, Response<ownershipStatusModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                           name_ET.setText(response.body().getData().getName()+"");
                           contact_ET.setText(response.body().getData().getContact());
                           email_ET.setText(response.body().getData().getEmail());
                           address_ET.setText(response.body().getData().getAddress());
                           String ownertype= response.body().getData().getOwnership_type();
                           packageamount.setText(response.body().getData().getOwnership_type());
                        }else
                        {

                        }

                    } else {
                        ShowApiError(getActivity(),"server error in sfio status");
                        olduserstatus=0;
                    }

                } catch (Exception e)
                {
                    if(pd.isShowing())
                        pd.dismiss();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ownershipStatusModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
    }

    private void getOwnerShipData(final String packageam,final String name, final String email, final String contact, final String address)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", name);
        hm.put("email",email);
        hm.put("contact", contact);
        hm.put("address", address);
        hm.put("ownership_type", packageam);
        AppConfig.getLoadInterface().updateSFIOData(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<owneeshipstausModel>() {
            @Override
            public void onResponse(Call<owneeshipstausModel> call, Response<owneeshipstausModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            Fragment fragment = new OwnerShipFragment();
                            Bundle arg = new Bundle();
                            fragment.setArguments(arg);
                            MainActivity.addFragment(fragment, false);
                        }else
                        {
                            Toast.makeText(mContext, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        ShowApiError(getActivity(),"server error in ARP");
                    }

                } catch (Exception e)
                {
                    if(pd.isShowing())
                        pd.dismiss();

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<owneeshipstausModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("Update Ownership");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.mDrawerToggle.setDrawerIndicatorEnabled(true);
        MainActivity.mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v)
    {

    }

}