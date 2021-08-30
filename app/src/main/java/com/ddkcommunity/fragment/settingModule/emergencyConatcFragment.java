package com.ddkcommunity.fragment.settingModule;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.conatcModel;
import com.ddkcommunity.model.emergencyModel;
import com.ddkcommunity.model.smpdfavmodel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

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
        getProfileCall(AppConfig.getStringPreferences(mContext, Constant.JWTToken));
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
        hm.put("alt_name", namev);
        hm.put("alt_email", emailv);
        hm.put("alt_contact_number", mobilev);
        Log.d("emerge",hm.toString());
        AppConfig.getLoadInterface().AddEmergencyContact(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<emergencyModel>() {
            @Override
            public void onResponse(Call<emergencyModel> call, Response<emergencyModel> response) {
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
                    public void onFailure(Call<emergencyModel> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    public void getProfileCall(String token) {
        if (AppConfig.isInternetOn()) {
            final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
            AppConfig.showLoading(dialog, "Fetching User Details..");
            HashMap<String, String> hm = new HashMap<>();
            hm.put("iv", App.pref.getString(Constant.IVPARAM, ""));
            hm.put("key", App.pref.getString(Constant.KEYENCYPARAM, ""));
            Call<ResponseBody> call = AppConfig.getLoadInterface().getProfileCall(token,hm);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseData = response.body().string();
                            JSONObject object = new JSONObject(responseData);
                            if (object.getInt(Constant.STATUS) == 1) {
                                UserResponse userResponse = new Gson().fromJson(responseData, UserResponse.class);
                                AppConfig.setUserData(mContext, userResponse);
                                MainActivity.userData=userResponse;
                                MainActivity.setUserDetail(userResponse.getUser());
                                String referalcode = userResponse.getUser().unique_code;
                                App.editor.putString(Constant.USER_REFERAL_CODE,referalcode);
                                App.editor.putString(Constant.USER_NAME, userResponse.getUser().getName());
                                App.editor.putString(Constant.USER_EMAIL, userResponse.getUser().getEmail());
                                if (userResponse.getUser().getMobile() != null) {
                                    App.editor.putString(Constant.USER_MOBILE, userResponse.getUser().getMobile());
                                }
                                App.editor.apply();
                                if(userResponse.getUser().getUserAlternateInfo()!=null)
                                {
                                    String altname=userResponse.getUser().getUserAlternateInfo().getAltName();
                                    String altemail=userResponse.getUser().getUserAlternateInfo().getAltEmail();
                                    String altcontact=userResponse.getUser().getUserAlternateInfo().getAltContactNumber();
                                    alt_contactnumber_ET.setText(altcontact);
                                    alt_email_ET.setText(altemail);
                                    alt_name_ET.setText(altname);
                                }

                            } else if (object.getInt(Constant.STATUS) == 3) {
                                AppConfig.showToast(object.getString("msg"));
                                AppConfig.openSplashActivity(getActivity());
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
                        ShowApiError(getActivity(),"server error in eightface/user-profile");
                    }
                    AppConfig.hideLoading(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppConfig.hideLoading(dialog);
                    errordurigApiCalling(getActivity(),t.getMessage());
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

}
