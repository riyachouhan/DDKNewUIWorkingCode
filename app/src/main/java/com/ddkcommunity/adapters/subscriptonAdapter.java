package com.ddkcommunity.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.SFIOShowFragmement;
import com.ddkcommunity.fragment.mapmodule.PlatinumFragment;
import com.ddkcommunity.fragment.mapmodule.dailybonousFragment;
import com.ddkcommunity.fragment.mapmodule.directReferralfragment;
import com.ddkcommunity.fragment.mapmodule.funnelfragment;
import com.ddkcommunity.fragment.mapmodule.groupbonusFragment;
import com.ddkcommunity.fragment.mapmodule.groupfragment;
import com.ddkcommunity.fragment.mapmodule.overflowFragment;
import com.ddkcommunity.fragment.mapmodule.phaseonefragment;
import com.ddkcommunity.fragment.mapmodule.powerofxfragmetn;
import com.ddkcommunity.fragment.projects.Mapsubfragmentclick;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.interfaces.GegtSettingStatusinterface;
import com.ddkcommunity.model.arpstausModel;
import com.ddkcommunity.model.checkRefferalModel;
import com.ddkcommunity.model.getSettingModel;
import com.ddkcommunity.model.mapoptionmodel;
import com.ddkcommunity.model.sfioHeaderModel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowFunctionalityAlert;

public class subscriptonAdapter extends RecyclerView.Adapter<subscriptonAdapter.MyViewHolder> {

    int count=0;
    ArrayList<mapoptionmodel> mapoptionList;
    private Activity activity;
    String actionname;
    UserResponse userData;
    String selectedview;

    public subscriptonAdapter(String selectedview, String actionname, ArrayList<mapoptionmodel> mapoptionList, Activity activity) {
        this.selectedview=selectedview;
        this.mapoptionList=mapoptionList;
        this.activity = activity;
        this.actionname=actionname;
     }

    public void updateData(ArrayList<mapoptionmodel> mapoptionList,String actionname,String selectedview)
    {
        this.selectedview=selectedview;
        this.actionname=actionname;
        this.mapoptionList=  mapoptionList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.smpdsubscriotnitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        try {
               //for php
        userData = AppConfig.getUserData(activity);
        holder.myfav.setText(mapoptionList.get(position).getName());
        holder.favicon.setImageResource(mapoptionList.get(position).getImageUrl());
        holder.phlayout.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                            if(position==0)
                            {
                                getSettingServerOnTab(activity,"sfio");

                            }else if(position==1)
                            {

                            }else if(position==2)
                            {

                            }
                    }
                });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {
        count = mapoptionList.size();
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView favicon;
         LinearLayout phlayout;
         TextView myfav;

        public MyViewHolder(View view) {
            super(view);
            myfav=view.findViewById(R.id.myfav);
            favicon=view.findViewById(R.id.favicon);
            phlayout=view.findViewById(R.id.phlayout);
        }
    }

    public void getSettingServerOnTab(final Activity activity, final String functionname)
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Please wait ....");
        String func=functionname,checkAccountLimit="1";
        {
            func=functionname;
        }
        UserModel.getInstance().getSettignSatusView(activity,func,checkAccountLimit,new GegtSettingStatusinterface()
        {
            @Override
            public void getResponse(Response<getSettingModel> response)
            {
                try
                {
                    AppConfig.hideLoading(dialog);
                    if (response.body().getStatus() == 1)
                    {
                        Fragment fragment = new SFIOShowFragmement();
                        Bundle arg = new Bundle();
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);

                    } else
                    {
                        ShowFunctionalityAlert(activity, response.body().getMsg());
                    }

                } catch (Exception e)
                {
                    AppConfig.hideLoading(dialog);
                    e.printStackTrace();
                }
            }
        });
    }

}

