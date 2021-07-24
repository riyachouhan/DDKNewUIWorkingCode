package com.ddkcommunity.fragment.settingModule;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.model.conatcModel;
import com.ddkcommunity.model.smpdfavmodel;
import com.ddkcommunity.utilies.AppConfig;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class emergencyConatcFragment extends Fragment
{
    private Context mContext;
    View view;
    AppCompatButton submit_BT;
    EditText alt_name_ET,alt_email_ET,alt_contactnumber_ET;

    public emergencyConatcFragment()
    {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.emergencyfragmentlayout, container, false);
        mContext = getActivity();
        submit_BT=view.findViewById(R.id.submit_BT);
        alt_name_ET=view.findViewById(R.id.alt_name_ET);
        alt_email_ET=view.findViewById(R.id.alt_email_ET);
        alt_contactnumber_ET=view.findViewById(R.id.alt_contactnumber_ET);

        submit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String namev=alt_name_ET.getText().toString();
                String emailv=alt_email_ET.getText().toString();
                String moilev=alt_contactnumber_ET.getText().toString();
               if(namev.equalsIgnoreCase(""))
               {
                   Toast.makeText(mContext, "Please enter person name", Toast.LENGTH_SHORT).show();
               }else if(emailv.equalsIgnoreCase(""))
               {
                   Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();
               }else if(moilev.equalsIgnoreCase(""))
               {
                   Toast.makeText(mContext, "Please enter mobile no", Toast.LENGTH_SHORT).show();
               }else {
                   submitBillingView(namev,emailv,moilev);
               }
            }
        });
        //for new
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void submitBillingView(String namev,String emailv,String mobilev)
    {

        final ProgressDialog dialognew = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialognew, "Please wait ....");

        HashMap<String, String> hm = new HashMap<>();
        hm.put("personname", namev);
        hm.put("email", emailv);
        hm.put("mobileno", mobilev);

        AppConfig.getLoadInterface().AddEmergencyContact(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<conatcModel>() {
            @Override
            public void onResponse(Call<conatcModel> call, Response<conatcModel> response) {
                             dialognew.dismiss();
                        try {
                            if (response.isSuccessful() && response.body() != null)
                            {
                                String responseData = response.body().toString();
                                Log.d("status", responseData);
                                if(response.body().getStatus()==1)
                                {
                                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }else
                                {
                                    Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                AppConfig.showToast("Server error");
                                Toast.makeText(getActivity(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            AppConfig.showToast("Server json error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<conatcModel> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

}
