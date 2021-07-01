package com.ddkcommunity.fragment.projects;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.CountryListAdapter;
import com.ddkcommunity.adapters.billeralladapteralpha;
import com.ddkcommunity.adapters.categoryalladapteralpha;
import com.ddkcommunity.fragment.paybillsModule.models.billerAllModel;
import com.ddkcommunity.fragment.paybillsModule.models.categoryAllModel;
import com.ddkcommunity.model.BankList;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.dataPutMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillerAllFragment extends Fragment implements View.OnClickListener
{
    private Context mContext;
    IndexFastScrollRecyclerView fast_scroller_recycler;
    private ArrayList<billerAllModel.Datum> mbillerList;
    private UserResponse userData;
    ImageView ivIcon;
    EditText search_ET;
    billeralladapteralpha adfpater;
    String billerid;

    public BillerAllFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmentelectricitybiller, container, false);
        mContext = getActivity();
        if (getArguments().getString("billerid") != null) {
            billerid= getArguments().getString("billerid");
        }
        fast_scroller_recycler=view.findViewById(R.id.fast_scroller_recycler);
        userData = AppConfig.getUserData(getActivity());
        ivIcon=view.findViewById(R.id.ivIcon);
        search_ET=view.findViewById(R.id.search_ET);
        getCategortyData();

        search_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (search_ET.getText().toString().length() > 0)
                {
                    filterCountry(search_ET.getText().toString(),mbillerList);
                } else {
                   adfpater.updateData(mbillerList);
                }
            }
        });

        return view;
    }

    public void filterCountry(String newText, ArrayList<billerAllModel.Datum> countrygender)
    {
        //new array list that will hold the filtered data
        ArrayList<billerAllModel.Datum> temp = new ArrayList();
        for(billerAllModel.Datum d: countrygender){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getBillerName().toLowerCase().contains(newText.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        adfpater.updateData(temp);
    }

    private void getCategortyData()
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String countrydata=userData.getUser().country.get(0).country;
        HashMap<String, String> hm = new HashMap<>();
        hm.put("country_id", userData.getUser().country.get(0).id);
        hm.put("category", billerid);
        AppConfig.getLoadInterface().getBillerlist(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<billerAllModel>() {
            @Override
            public void onResponse(Call<billerAllModel> call, Response<billerAllModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            Log.d("chages data",response.toString());
                            if(response.body().getData()!=null)
                            {

                                mbillerList = new ArrayList<>();
                                mbillerList.addAll(response.body().getData());
                                initialiseUI();
                                //.........
                                pd.dismiss();

                            }
                        }else
                        {
                            pd.dismiss();
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(mContext,"server error sampd-company/company-list");
                    }

                } catch (Exception e) {
                    ShowApiError(mContext,"error in response sampd-company/company-list");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<billerAllModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void getSearchData(String search)
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String countrydata=userData.getUser().country.get(0).country;
        HashMap<String, String> hm = new HashMap<>();
        hm.put("type", "name");
        hm.put("name", search);
        AppConfig.getLoadInterface().getSearchList(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<billerAllModel>() {
            @Override
            public void onResponse(Call<billerAllModel> call, Response<billerAllModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            Log.d("chages data",response.toString());
                            if(response.body().getData()!=null)
                            {

                                mbillerList = new ArrayList<>();
                                mbillerList.addAll(response.body().getData());
                                initialiseUI();
                                //.........
                                pd.dismiss();

                            }
                        }else
                        {
                            pd.dismiss();
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(mContext,"server error sampd-company/company-list");
                    }

                } catch (Exception e) {
                    ShowApiError(mContext,"error in response sampd-company/company-list");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<billerAllModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    protected void initialiseUI()
    {
        fast_scroller_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adfpater=new billeralladapteralpha(getActivity(),mbillerList);
        fast_scroller_recycler.setAdapter(adfpater);
        fast_scroller_recycler.setIndexTextSize(16);
        fast_scroller_recycler.setIndexBarColor("#FFFFFF");
        fast_scroller_recycler.setIndexBarCornerRadius(0);
        fast_scroller_recycler.setIndexBarTransparentValue((float) 0.4);
        fast_scroller_recycler.setIndexbarMargin(0);
        fast_scroller_recycler.setIndexbarWidth(60);
        fast_scroller_recycler.setPreviewPadding(0);
        fast_scroller_recycler.setIndexBarTextColor("#000000");

        fast_scroller_recycler.setPreviewTextSize(60);
        fast_scroller_recycler.setPreviewColor("#FFFFFF");
        fast_scroller_recycler.setPreviewTextColor("#000000");
        fast_scroller_recycler.setPreviewTransparentValue(0.6f);

        fast_scroller_recycler.setIndexBarVisibility(false);
        fast_scroller_recycler.setIndexBarStrokeVisibility(false);
        fast_scroller_recycler.setIndexBarStrokeWidth(1);
        fast_scroller_recycler.setIndexBarStrokeColor("#000000");

        fast_scroller_recycler.setIndexbarHighLightTextColor("#FFFFFF");
        fast_scroller_recycler.setIndexBarHighLightTextVisibility(true);

        Objects.requireNonNull(fast_scroller_recycler.getLayoutManager()).scrollToPosition(0);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("Biller");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }
}