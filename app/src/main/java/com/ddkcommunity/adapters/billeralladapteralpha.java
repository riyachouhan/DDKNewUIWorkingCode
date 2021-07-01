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
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.paybillsModule.PayBillsFragment;
import com.ddkcommunity.fragment.paybillsModule.models.addformModelBiller;
import com.ddkcommunity.fragment.paybillsModule.models.billerAllModel;
import com.ddkcommunity.fragment.paybillsModule.models.categoryAllModel;
import com.ddkcommunity.fragment.projects.payAfterBillerFragment;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.Country;
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

public class billeralladapteralpha extends RecyclerView.Adapter<billeralladapteralpha.ViewHolder>
        implements SectionIndexer {

    private List<billerAllModel.Datum> mDataArray;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private HashMap<Integer, Integer> sectionsTranslator = new HashMap<>();
    private ArrayList<Integer> mSectionPositions;
    private UserResponse userData;
    Activity activity;

    public billeralladapteralpha(Activity activity,List<billerAllModel.Datum> dataset) {
        mDataArray = dataset;
        this.activity=activity;
    }

    public void updateData( ArrayList<billerAllModel.Datum> countrygender) {
        this.mDataArray= countrygender;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public billeralladapteralpha.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billeriteamlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        userData = AppConfig.getUserData(activity);

        holder.ivBankIcon.setImageResource(R.drawable.bpiimg);
            holder.tvBankName.setText(mDataArray.get(position).getBillerName().toString());

            holder.ll_selectedBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String billerid=mDataArray.get(position).getBillerId().toString();
                String billername=mDataArray.get(position).getBillerName().toString();
                getCategortyData(billername,billerid,activity);
            }
        });
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(27);
        ArrayList<String> alphabetFull = new ArrayList<>();

        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = mDataArray.size(); i < size; i++) {
            String section = String.valueOf(mDataArray.get(i).getBillerName().charAt(0)).toUpperCase();
            if (!sections.toString().toUpperCase().contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        for (int i = 0; i < mSections.length(); i++) {
            alphabetFull.add(String.valueOf(mSections.charAt(i)));
        }


        return alphabetFull.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionsTranslator.get(sectionIndex));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName;
        ImageView ivBankIcon;
        LinearLayout ll_selectedBank;

        ViewHolder(View itemView) {
            super(itemView);
            ll_selectedBank=itemView.findViewById(R.id.ll_selectedBank);
            ivBankIcon=itemView.findViewById(R.id.ivBankIcon);
            tvBankName = itemView.findViewById(R.id.tvBankName);
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
                            if (object.getInt("status") == 1) {

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