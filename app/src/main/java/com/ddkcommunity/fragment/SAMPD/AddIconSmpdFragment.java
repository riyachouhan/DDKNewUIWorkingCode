package com.ddkcommunity.fragment.SAMPD;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.ddkcommunity.adapters.addsampdfrgae;
import com.ddkcommunity.model.smpdModelNew;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddIconSmpdFragment extends Fragment implements View.OnClickListener
{
    private Context mContext;
    IndexFastScrollRecyclerView fast_scroller_recycler;
     private ArrayList<smpdModelNew.Datum> SAMPDList;
    EditText search_ET;
    ImageView ivIcon;

    public AddIconSmpdFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmentelectricitybiller, container, false);
        mContext = getActivity();
        search_ET=view.findViewById(R.id.search_ET);
        ivIcon=view.findViewById(R.id.ivIcon);
        search_ET.setHint("Search ");
        fast_scroller_recycler=view.findViewById(R.id.fast_scroller_recycler);
        ivIcon.setOnClickListener(this);
        initialiseUI();
        return view;
    }

    protected void initialiseUI()
    {
        fast_scroller_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        getCompanyList("");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivIcon:
                if(!search_ET.getText().toString().equalsIgnoreCase(""))
                {
                    getCompanyList(search_ET.getText().toString());
                }else
                {
                    Toast.makeText(mContext, "Please enter search keyword", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("SAM PD");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void getCompanyList(String searchkeyword)
    {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("search", searchkeyword);
        hm.put("home_screen", "0");
        hm.put("order_by", "sorting");
        AppConfig.getLoadInterface().getSMPDLIST(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<smpdModelNew>() {
            @Override
            public void onResponse(Call<smpdModelNew> call, Response<smpdModelNew> response) {
                try {
                    if(pd.isShowing()) {
                        pd.dismiss();
                    }

                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            Log.d("chages data",response.toString());
                            if(response.body().getData()!=null)
                            {
                                SAMPDList = new ArrayList<>();
                                SAMPDList.addAll(response.body().getData());
                                if(SAMPDList.size()!=0)
                                {
                                    fast_scroller_recycler.setAdapter(new addsampdfrgae(getActivity(),SAMPDList));
                                }
                                //.........
                            }else
                            {
                                SAMPDList = new ArrayList<>();
                                fast_scroller_recycler.setAdapter(new addsampdfrgae(getActivity(),SAMPDList));
                            }
                        }else
                        {
                            SAMPDList = new ArrayList<>();
                            fast_scroller_recycler.setAdapter(new addsampdfrgae(getActivity(),SAMPDList));
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        ShowApiError(mContext,"server error sampd-company/company-list");
                    }

                } catch (Exception e) {
                    AppConfig.hideLoader();
                    if(pd.isShowing()) {
                        pd.dismiss();
                    }
                    ShowApiError(mContext,"error in response sampd-company/company-list");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<smpdModelNew> call, Throwable t)
            {
                AppConfig.hideLoader();
                if(pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}