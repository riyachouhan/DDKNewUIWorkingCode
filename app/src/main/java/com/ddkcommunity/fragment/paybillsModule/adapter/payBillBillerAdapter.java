package com.ddkcommunity.fragment.paybillsModule.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.paybillsModule.PayBillsFragment;
import com.ddkcommunity.fragment.paybillsModule.models.addformModelBiller;
import com.ddkcommunity.fragment.paybillsModule.models.catModel;
import com.ddkcommunity.fragment.projects.CategoryAllFragment;
import com.ddkcommunity.fragment.projects.BillerAllFragment;
import com.ddkcommunity.fragment.projects.payAfterBillerFragment;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class payBillBillerAdapter extends RecyclerView.Adapter<payBillBillerAdapter.MyViewHolder> {

    private List<catModel.DataBiller> createCancellationRequestlist;
    private Activity activity;
    private UserResponse userData;

    public payBillBillerAdapter(List<catModel.DataBiller> createCancellationRequestlist, Activity activity) {
        this.createCancellationRequestlist=createCancellationRequestlist;
        this.activity = activity;
    }

    public void updateData(List<catModel.DataBiller> createCancellationRequestlist) {
        this.createCancellationRequestlist= createCancellationRequestlist;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paybillsbillersitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            userData = AppConfig.getUserData(activity);
            String ctnam=createCancellationRequestlist.get(position).getBillerName().toString();
            if(ctnam.equalsIgnoreCase("more"))
            {
                holder.catimg.setVisibility(View.GONE);
                holder.moreiocn.setVisibility(View.VISIBLE);
                holder.catname.setText("See All");
                holder.moreiocn.setImageResource(R.drawable.sellallbg);

            }else
            {
                holder.catimg.setVisibility(View.VISIBLE);
                holder.moreiocn.setVisibility(View.GONE);
                holder.catname.setText(createCancellationRequestlist.get(position).getBillerName().toString());
                Glide.with(activity)
                        .asBitmap()
                        .load(createCancellationRequestlist.get(position).getBillerImage())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                holder.catimg.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                super.onLoadFailed(errorDrawable);
                                holder.catimg.setImageResource(R.drawable.default_photo);
                            }

                        });
            }


            holder.btnBiller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(createCancellationRequestlist.get(position).getBillerName().toString().equalsIgnoreCase("more"))
                    {
                        Fragment fragment = new BillerAllFragment();
                        Bundle arg = new Bundle();
                        arg.putString("billerid", "All");
                        fragment.setArguments(arg);
                        MainActivity.addFragment(fragment, true);

                    }else {
                        String iduser = createCancellationRequestlist.get(position).getBillerId().toString();
                        String billername = createCancellationRequestlist.get(position).getBillerName().toString();
                        getCategortyData(billername,iduser,activity);
                    }
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return createCancellationRequestlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView catname;
        ImageView catimg,moreiocn;
        LinearLayout btnBiller;

        public MyViewHolder(View view) {
            super(view);
            btnBiller=view.findViewById(R.id.btnBiller);
            moreiocn=view.findViewById(R.id.moreiocn);
            catname=view.findViewById(R.id.catname);
            catimg=view.findViewById(R.id.catimg);
        }
    }


    private void getCategortyData(final String billername, final String billerid, final Activity activity)
    {
        final ProgressDialog pd = new ProgressDialog(activity);
        pd.setMessage("Please wait..........");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("country_id", userData.getUser().country.get(0).id);
        hm.put("biller_id", "61");
       // hm.put("biller_id", billerid);
        Log.d("hm",hm.toString());
        AppConfig.getLoadInterface().getBillerParamterList(AppConfig.getStringPreferences(activity, Constant.JWTToken),hm).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        try {
                            pd.dismiss();
                            String resStr = response.body().string();
                            JSONObject object = new JSONObject(resStr);
                            if (object.getInt("status") == 1)
                            {
                               //for data
                                PayBillsFragment.viewList=new ArrayList<>();
                                PayBillsFragment.arraylist=new ArrayList<>();
                                JSONArray dataarray=object.getJSONArray("data");
                                for(int i=0;i<dataarray.length();i++)
                                {
                                    JSONObject jsonObject = dataarray.getJSONObject(i);
                                    // add interviewee name to arraylist
                                    addformModelBiller.Datum model1=new addformModelBiller.Datum();
                                    model1.setValidation(jsonObject.getString("validation"));
                                    model1.setDescription(jsonObject.getString("description"));
                                    model1.setSample(jsonObject.getString("sample"));
                                    model1.setLabel(jsonObject.getString("label"));
                                    String typevalu=jsonObject.getString("type");
                                    model1.setType(jsonObject.getString("type"));
                                    if(typevalu.equalsIgnoreCase("enum"))
                                    {
                                        String valuev= String.valueOf(jsonObject.getJSONArray("value"));
                                        model1.setValue(valuev);
                                    }else  if(typevalu.equalsIgnoreCase("formula"))
                                    {
                                        String valuev= String.valueOf(jsonObject.getString("value"));
                                        model1.setValue(valuev);
                                    }else
                                    {
                                        model1.setValue(jsonObject.getString("value"));
                                    }
                                    model1.setDefault(jsonObject.getString("default"));
                                    model1.setJsonType(jsonObject.getString("json_type"));
                                    model1.setValidation_msg(jsonObject.getString("validation_msg"));
                                    PayBillsFragment.arraylist.add(model1);
                                }
                                //.............

                                Fragment fragment = new payAfterBillerFragment();
                                Bundle arg = new Bundle();
                                arg.putString("billerid", billerid);
                                arg.putString("billername", billername);
                                fragment.setArguments(arg);
                                MainActivity.addFragment(fragment, true);

                            } else if (object.getInt(Constant.STATUS) == 3) {
                                AppConfig.showToast(object.getString("msg"));
                                AppConfig.openSplashActivity(activity);
                            } else {
                                AppConfig.showToast(object.getString("msg"));
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        pd.dismiss();
                        AppConfig.showToast(response.message());
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
