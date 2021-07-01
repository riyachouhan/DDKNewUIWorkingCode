package com.ddkcommunity.fragment;


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
import com.ddkcommunity.model.addActivityModel;
import com.ddkcommunity.model.conatcModel;
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

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddActivityFragemnt extends Fragment
{
    private Context mContext;
    View view;
    AppCompatButton submit;
    EditText name,mobile,email,notes;

    public AddActivityFragemnt() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.addactivtyfragment, container, false);
        mContext = getActivity();
        submit=view.findViewById(R.id.submit);
        name=view.findViewById(R.id.name);
        mobile=view.findViewById(R.id.mobile);
        email=view.findViewById(R.id.email);
        notes=view.findViewById(R.id.notes);

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               String namevalue=name.getText().toString();
                String emailval=email.getText().toString();
                String mobileval=mobile.getText().toString();
                String notesval=notes.getText().toString();
               if(namevalue.equalsIgnoreCase(""))
               {
                   Toast.makeText(mContext, "Please enter Name", Toast.LENGTH_SHORT).show();
               }else if(emailval.equalsIgnoreCase(""))
               {
                   Toast.makeText(mContext, "Please enter Email", Toast.LENGTH_SHORT).show();
               }else
               {
               addActivityData(namevalue,emailval,mobileval,notesval);
               }
            }
        });
        //for new
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Add Activity");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    private void addActivityData(String namevalue,String emailval,String mobileval,String notesval)
    {
        final ProgressDialog pd=new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Please wait");
        pd.show();
        HashMap<String, String> hm = new HashMap<>();
        hm.put("name", namevalue);
        hm.put("email",emailval);
        hm.put("mobile_number", mobileval);
        hm.put("notes",notesval);
        Log.d("php user ", hm.toString());
        AppConfig.getLoadInterface().addActivity(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<addActivityModel>() {
            @Override
            public void onResponse(Call<addActivityModel> call, Response<addActivityModel> response) {
                try {
                    Log.d("sam erro par invi",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus()==1)
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            MainActivity.addFragment(new activityFragmement(), false);

                        }else
                        {
                            pd.dismiss();
                            Toast.makeText(getContext(), ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        ShowApiError(getContext(),"server error add activity");
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
            public void onFailure(Call<addActivityModel> call, Throwable t)
            {
                pd.dismiss();
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
