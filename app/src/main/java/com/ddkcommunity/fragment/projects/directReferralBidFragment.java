package com.ddkcommunity.fragment.projects;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
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

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.WalletPopupAdapter;
import com.ddkcommunity.adapters.directreferralbidAdpater;
import com.ddkcommunity.fragment.mapmodule.adapter.direcetReferalAdpaterbid;
import com.ddkcommunity.fragment.mapmodule.model.direcetBidModel;
import com.ddkcommunity.fragment.settingModule.ARPHistoryModel;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.CountryResponse;
import com.ddkcommunity.model.credential.Credential;
import com.ddkcommunity.model.mapLoginModel;
import com.ddkcommunity.model.navigationModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

/**
 * A simple {@link Fragment} subclass.
 */
public class directReferralBidFragment extends Fragment implements View.OnClickListener
{
    Activity mContext;
    EditText positon_inputAmt;
    EditText phone_code_ET,packageamount;
    LinearLayout coutrylayout,subdetails,dateorbirthview,positionview,gendermainview;
    TextView positionsetting,posiotion_name;
    public static ArrayList<direcetBidModel.ExtraDatum> Listtab1;
    EditText country_ET,dateofbirth_ET,gender_value_ET,password_ET,email_ET;
    AppCompatButton submit_BT;
    String gendervalue,selectedposiiton="",selecetdposionid="",posiitonAmount="";
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    View view;
    int year;
    int month;
    int day;
    String userage="";
    private List<Country> countryList = new ArrayList<>();
    String[] gender;
    String[] stateId;
    String[] countryCode;
    ArrayList<Country> countrygender;

    public directReferralBidFragment()
    {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.directreferalbidfragment, container, false);
        mContext = getActivity();
        country_ET=view.findViewById(R.id.country_ET);
        coutrylayout=view.findViewById(R.id.coutrylayout);
        subdetails=view.findViewById(R.id.subdetails);
        posiotion_name=view.findViewById(R.id.posiotion_name);
        radioGroup=view.findViewById(R.id.radio);
        phone_code_ET=view.findViewById(R.id.phone_code_ET);
        positionview=view.findViewById(R.id.positionview);
        submit_BT=view.findViewById(R.id.submit_BT);
        positionsetting=view.findViewById(R.id.positionsetting);
        packageamount=view.findViewById(R.id.packageamount);
        password_ET=view.findViewById(R.id.password_ET);
        email_ET=view.findViewById(R.id.email_ET);
        dateofbirth_ET=view.findViewById(R.id.dateofbirth_ET);
        dateorbirthview=view.findViewById(R.id.dateorbirthview);
        gender_value_ET=view.findViewById(R.id.gender_value_ET);
        gendermainview=view.findViewById(R.id.gendermainview);
        positon_inputAmt=view.findViewById(R.id.positon_inputAmt);
        getCountryCall();
        mapBidPosition();
        positionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDialogView(packageamount, mContext, Listtab1);
               // setCredentialPopup(inflater,packageamount);
            }
        });

        dateorbirthview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });

        //for radio button
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // checkedId is the RadioButton selected
                int selectedIdradio = group.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) view.findViewById(selectedIdradio);
                final String useroption=radioButton.getText().toString();
                if(useroption.equalsIgnoreCase("Female"))
                {
                    gendervalue="female";
                    gendermainview.setVisibility(View.GONE);
                    gender_value_ET.setText("");
                }else if(useroption.equalsIgnoreCase("Male"))
                {
                    gendervalue="male";
                    gendermainview.setVisibility(View.GONE);
                    gender_value_ET.setText("");
                }else
                {
                    gender_value_ET.setText("");
                    gendervalue="other";
                    gendermainview.setVisibility(View.VISIBLE);
                }
                //.......
            }
        });

        coutrylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dataPutMethods.showDialogForSearchCountry(view,getContext(),countrygender,gender,stateId,countryCode,country_ET,phone_code_ET);
            }
        });
        //................
        submit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String positionbid=packageamount.getText().toString();
                String emailv=email_ET.getText().toString();
                String passwordv=password_ET.getText().toString();

                if(positionbid.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please select position", Toast.LENGTH_SHORT).show();
                }else if(emailv.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
                }else if(emailv.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter email id", Toast.LENGTH_SHORT).show();
                }else if(passwordv.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                }else if(passwordv.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please select date of birth", Toast.LENGTH_SHORT).show();
                }else if(passwordv.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please select country", Toast.LENGTH_SHORT).show();
                }else
                {
                 /*   for (Country country : countryList) {
                        if (country.getPhoneCode().equals(country_ET.getText().toString()) && country.getCountry().equalsIgnoreCase(countryname))
                        {
                            mCountryId = country.getId();
                        }
                    }
                 */
                    Fragment fragment = new Mappaymentviewfragment();
                    Bundle arg = new Bundle();
                    arg.putString("action","bid");
                    arg.putString("email", emailv);
                    arg.putString("password", passwordv);
                    arg.putString("bidposiiton",selectedposiiton);
                    arg.putString("bidposiitonID", selecetdposionid);
                    arg.putString("bidamount", posiitonAmount);
                    fragment.setArguments(arg);
                    MainActivity.addFragment(fragment,false);
                }
            }
        });
        return view;
    }

    private void getCountryCall() {
        if (AppConfig.isInternetOn()) {
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
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    private void SelectDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dateDialog = new DatePickerDialog(getContext(), datePickerListener, year, month, day);
        dateDialog.getDatePicker().setMaxDate(new Date().getTime());
        dateDialog.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            userage=getAge(year,month,day);
            // Toast.makeText(getActivity(), "your age "+userage, Toast.LENGTH_SHORT).show();
            // Show selected date
            dateofbirth_ET.setText(new StringBuilder().append(day)
                    .append("-").append(month+1).append("-").append(year)
                    .append(" "));

        }
    };

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void setCredentialPopup(LayoutInflater inflater,final TextView packageamount) {
        View view1 = inflater.inflate(R.layout.popup_diglog, null, false);
        final PopupWindow pw = new PopupWindow(view1, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        RecyclerView recyclerView = view1.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final direcetReferalAdpaterbid adapterCredential = new direcetReferalAdpaterbid(Listtab1, mContext,new direcetReferalAdpaterbid.SetOnItemClickListener()
        {
            @Override
            public void onItemClick(final String posiitonid,final String posiiton,final String amount)
            {
                //...........
                packageamount.setText(posiiton+"");
                pw.dismiss();
            }
        });
        recyclerView.setAdapter(adapterCredential);
        pw.showAtLocation(packageamount, Gravity.TOP | Gravity.LEFT, dataPutMethods.locateView(packageamount).left, dataPutMethods.locateView(packageamount).bottom);
    }

    private void mapBidPosition()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setMessage("Please wait ...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String maptoken=App.pref.getString(Constant.MAPToken, "");
        AppConfig.getLoadInterfaceMap().getDirectPurches(maptoken).enqueue(new Callback<direcetBidModel>() {
            @Override
            public void onResponse(Call<direcetBidModel> call, Response<direcetBidModel> response)
            {
                try
                {
                    AppConfig.hideLoading(pd);
                    Log.d("map response",response.body().toString());
                    if(response.isSuccessful() && response.body() != null)
                    {
                        Listtab1=new ArrayList<>();
                        Listtab1.addAll(response.body().getExtraData());
                    } else
                    {
                        ShowApiError(getActivity(),"server error check-mail-exist");
                    }

                } catch (Exception e)
                {
                    AppConfig.hideLoading(pd);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<direcetBidModel> call, Throwable t)
            {
                AppConfig.hideLoading(pd);
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

}

    public void showDialogView(final TextView packageamount, final Context mContext, final ArrayList<direcetBidModel.ExtraDatum> Listtab1)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogView = layoutInflater.inflate(R.layout.popup_wallet_pooling, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        final TextView countrynamehint=dialogView.findViewById(R.id.countrynamehint);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        final EditText searchEt = dialogView.findViewById(R.id.search_ET);
        searchEt.setVisibility(View.GONE);
        countrynamehint.setText("Please select Bid Purches");
        countrynamehint.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final direcetReferalAdpaterbid adapterCredential = new direcetReferalAdpaterbid(Listtab1, mContext,new direcetReferalAdpaterbid.SetOnItemClickListener()
        {
            @Override
            public void onItemClick(final String posiitonid,final String posiiton,final String amount)
            {
                //...........
                if(posiiton!=null && posiiton.equalsIgnoreCase("1")) {
                    positionsetting.setText("Under "+posiitonid+" at Left Position");
                    posiotion_name.setText("At Left Position");
                }else
                {
                    positionsetting.setText("Under "+posiitonid+" at Right Position");
                    posiotion_name.setText("At Right Position");
                }
                positionsetting.setVisibility(View.VISIBLE);
                positon_inputAmt.setText(amount+"");
                packageamount.setText(posiitonid);
                selectedposiiton=posiiton;
                selecetdposionid=posiitonid;
                posiitonAmount=amount;
                subdetails.setVisibility(View.VISIBLE);
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
        MainActivity.setTitle("Direct Position Purches");
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