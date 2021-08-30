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

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

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
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class editOwnershipFragment extends Fragment implements View.OnClickListener
{
    Activity mContext;
    TextView packageamount;
    ArrayList<ARPHistoryModel.Datum> Listtab1;
    EditText name_ET,contact_ET,email_ET,address_ET;
    AppCompatButton submit_BT;
    RadioGroup radioGroup;
    RadioButton radioButton;
    LinearLayout nolayput;
    String input_amount,fee;
    int olduserstatus=0;

    public editOwnershipFragment()
    {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.editownershipfragment, container, false);
        mContext = getActivity();
        if (getArguments().getString("input_amount") != null)
        {
            input_amount= getArguments().getString("input_amount");
        }

        if (getArguments().getString("fee") != null)
        {
            fee= getArguments().getString("fee");
        }
        nolayput=view.findViewById(R.id.nolayput);
        radioGroup=view.findViewById(R.id.radioGroup);
        submit_BT=view.findViewById(R.id.submit_BT);
        name_ET=view.findViewById(R.id.name_ET);
        getOwnershipStatus();
        contact_ET=view.findViewById(R.id.contact_ET);
        email_ET=view.findViewById(R.id.email_ET);
        address_ET=view.findViewById(R.id.address_ET);
        packageamount=view.findViewById(R.id.packageamount);
        //packageamount.setText("Entity");
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
              //  setCredentialPopup(inflater,packageamount);

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // checkedId is the RadioButton selected
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    String checktextv=checkedRadioButton.getText().toString();
                    if(olduserstatus==1)
                    {
                        if(checktextv.equalsIgnoreCase("No"))
                        {
                            nolayput.setVisibility(View.VISIBLE);
                            packageamount.setText("Entity");

                        }else
                        {
                            nolayput.setVisibility(View.GONE);
                            packageamount.setText("Join");
                        }
                    }else
                    {
                        nolayput.setVisibility(View.VISIBLE);
                        if(checktextv.equalsIgnoreCase("No"))
                        {
                            packageamount.setText("Entity");

                        }else
                        {
                             packageamount.setText("Join");
                        }
                    }
                }
            }
        });

        submit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton= (RadioButton)radioGroup.findViewById(selectedID);
                String ownershipjoin_strtaus=radioButton.getText().toString().toLowerCase();
                if(olduserstatus==1)
                {
                    String namev=name_ET.getText().toString();
                    String contactv=contact_ET.getText().toString();
                    String emailv=email_ET.getText().toString();
                    String addressv=address_ET.getText().toString();
                    String ownershipv=packageamount.getText().toString();
                    if(ownershipjoin_strtaus.equalsIgnoreCase("no"))
                    {
                        if(ownershipv.equalsIgnoreCase(""))
                        {
                            Toast.makeText(getActivity(), "Please select Ownership Type ", Toast.LENGTH_SHORT).show();
                        }else
                        {
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
                                Toast.makeText(getActivity(), "Please enter Complete Address", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                    Fragment fragment = new sfioSelectionFragment();
                                    Bundle arg = new Bundle();
                                    arg.putString("input_amount", input_amount);
                                    arg.putString("ownershipjoin_status", ownershipjoin_strtaus);
                                    arg.putString("ownershiptype", ownershipv);
                                    arg.putString("name", namev);
                                    arg.putString("email", emailv);
                                    arg.putString("contact", contactv);
                                    arg.putString("address", addressv);
                                    arg.putString("fee", fee);
                                    fragment.setArguments(arg);
                                    MainActivity.addFragment(fragment, false);

                            }
                        }
                    }else
                    {
                            Fragment fragment = new sfioSelectionFragment();
                            Bundle arg = new Bundle();
                            arg.putString("input_amount", input_amount);
                            arg.putString("ownershipjoin_status",ownershipjoin_strtaus);
                            arg.putString("ownershiptype","Join");
                            arg.putString("name", namev);
                            arg.putString("email", emailv);
                            arg.putString("contact", contactv);
                            arg.putString("address", addressv);
                            arg.putString("fee", fee);
                            fragment.setArguments(arg);
                            MainActivity.addFragment(fragment,false);
                    }
                }else
                {
                    String namev=name_ET.getText().toString();
                    String contactv=contact_ET.getText().toString();
                    String emailv=email_ET.getText().toString();
                    String addressv=address_ET.getText().toString();
                    String ownershipv=packageamount.getText().toString();
                    if(ownershipv.equalsIgnoreCase(""))
                    {
                        Toast.makeText(getActivity(), "Please select Ownership Type ", Toast.LENGTH_SHORT).show();
                    }else
                    {
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
                        }else
                        {
                                getOwnerShipData(ownershipjoin_strtaus,ownershipv,namev,emailv,contactv,addressv);
                            }
                    }
                }
            }
        });
        return view;
    }

    public void setCredentialPopup(LayoutInflater inflater,final TextView packageamount) {
        View view1 = inflater.inflate(R.layout.popup_diglog, null, false);
        final PopupWindow pw = new PopupWindow(view1, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        RecyclerView recyclerView = view1.findViewById(R.id.recycler_view);
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

    private void getOwnershipStatus()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        AppConfig.getLoadInterface().checkSfioOwnership(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ownershipStatusModel>() {
            @Override
            public void onResponse(Call<ownershipStatusModel> call, Response<ownershipStatusModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            olduserstatus=1;
                            packageamount.setText("Join");
                            nolayput.setVisibility(View.GONE);
                        }else
                        {
                            olduserstatus=0;
                            packageamount.setText("Join");
                            nolayput.setVisibility(View.VISIBLE);
                        }

                    } else {
                        ShowApiError(getActivity(),"server error in sfio status");
                        olduserstatus=0;
                        packageamount.setText("Join");
                        nolayput.setVisibility(View.VISIBLE);
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

    private void getOwnerShipData(final String ownership,final String ownershiptype,final String name, final String email, final String contact, final String address)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ......");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("ownership_type", ownershiptype);
        hm.put("name", name);
        hm.put("email",email);
        hm.put("contact", contact);
        hm.put("address", address);
        AppConfig.getLoadInterface().addSfioOwnership(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<arpstausModel>() {
            @Override
            public void onResponse(Call<arpstausModel> call, Response<arpstausModel> response) {
                pd.dismiss();
                try
                {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                                Fragment fragment = new sfioSelectionFragment();
                                Bundle arg = new Bundle();
                                arg.putString("input_amount", input_amount);
                                arg.putString("ownershipjoin_status", ownership);
                                arg.putString("ownershiptype", ownershiptype);
                                arg.putString("name", name);
                                arg.putString("email", email);
                                arg.putString("contact", contact);
                                arg.putString("address", address);
                                arg.putString("fee", fee);
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
            public void onFailure(Call<arpstausModel> call, Throwable t) {
                if(pd.isShowing())
                    pd.dismiss();

                errordurigApiCalling(getActivity(),t.getMessage());
            }
        });
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

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("Ownership");
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