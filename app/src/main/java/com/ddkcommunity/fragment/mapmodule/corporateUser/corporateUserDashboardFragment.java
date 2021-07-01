package com.ddkcommunity.fragment.mapmodule.corporateUser;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.adapters.activityAdapterv;
import com.ddkcommunity.fragment.AddActivityFragemnt;
import com.ddkcommunity.fragment.mapmodule.adapter.corporateUserDashboarAdapter;
import com.ddkcommunity.model.activityModel;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class corporateUserDashboardFragment extends Fragment implements View.OnClickListener
{

    LinearLayout addicon;
    View view;
    Context mContext;
    RecyclerView rvRecycle;
    private ArrayList<activityModel.Datum> funnelDAta;
    TextView viewall;

    public corporateUserDashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.corporateuserdashboarlayout, container, false);
        mContext = getActivity();
        addicon=view.findViewById(R.id.addicon);
        viewall=view.findViewById(R.id.viewall);
        rvRecycle=view.findViewById(R.id.rvRecycle);
        String udata="View all";
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        viewall.setText(content);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvRecycle.setLayoutManager(mLayoutManager);
        rvRecycle.setItemAnimator(new DefaultItemAnimator());
        getActivitydata();
        addicon.setOnClickListener(this);
        viewall.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Corporate User");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.viewall:
                MainActivity.addFragment(new viewAllFragment(), true);
                break;

            case R.id.addicon:
                MainActivity.addFragment(new addLinkNewAccount(), true);
                break;
        }
    }

    private void getActivitydata()
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        AppConfig.getLoadInterface().mapActivityList(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<activityModel>() {
            @Override
            public void onResponse(Call<activityModel> call, Response<activityModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            funnelDAta=new ArrayList<>();
                            funnelDAta.addAll(response.body().getData());
                            corporateUserDashboarAdapter mAdapter = new corporateUserDashboarAdapter(funnelDAta, getActivity());
                            rvRecycle.setAdapter(mAdapter);

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(getContext(),"server error check-mail-exist");
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
            public void onFailure(Call<activityModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
