package com.ddkcommunity.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.adapters.CountryListAdapter;
import com.ddkcommunity.fragment.GogoleAuthFragment;
import com.ddkcommunity.fragment.GogolePasswordFragment;
import com.ddkcommunity.fragment.ProfileFragment;
import com.ddkcommunity.fragment.credential.CredentialsFragment;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.CountryResponse;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.ScalingUtilities;
import com.ddkcommunity.utilies.Utility;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;
import static com.ddkcommunity.utilies.dataPutMethods.putGoogleAuthStatus;

public class SignupActivity extends AppCompatActivity {

    EditText nameET, emailET, passET, confirmPassET, mobileET, referral_code_ET;
    TextView countryET, phoneCodeET;
    Bitmap  idproofbitmap;
    String country, mCountryId, imagePath,idpreefpath;
    ImageView back_IV;
    CircleImageView profile_img;
    Context context;
    int position = -1;
    CheckBox check_wallet;
    private String deviceToken="1234abcdsa";
    private List<Country> countryList;
    int year;
    int month;
    int day;
    TextView birthdate_edt;
    String userage="";
    ImageView img_first_front_pic;
    private Uri uri;
    int imgseletcoption=0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = this;
        App.editor.putString("checked", "0");
        App.editor.commit();
        img_first_front_pic=findViewById(R.id.img_first_front_pic);
        birthdate_edt=findViewById(R.id.birthdate_edt);
        nameET = findViewById(R.id.name_ET);
        referral_code_ET = findViewById(R.id.referral_code_ET);
        referral_code_ET.requestFocus();
        emailET = findViewById(R.id.email_ET);
        back_IV = findViewById(R.id.back_IV);
        passET = findViewById(R.id.password_ET);
        confirmPassET = findViewById(R.id.confirm_password_ET);
        mobileET = findViewById(R.id.mobile_ET);
        countryET = findViewById(R.id.country_ET);
        phoneCodeET = findViewById(R.id.phone_code_ET);
        profile_img = findViewById(R.id.profile_img);

        check_wallet = findViewById(R.id.check_wallet);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignupActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceToken = instanceIdResult.getToken();
                AppConfig.setStringPreferences(context, "DeviceToken", instanceIdResult.getToken());
            }
        });

        findViewById(R.id.profile_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                imgseletcoption=2;
                selectImage();
             /*   final PickImageDialog dialog = PickImageDialog.build(new PickSetup());
                dialog.setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        dialog.dismiss();
                    }
                }).setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        //TODO: do what you have to...
                        imagePath = r.getPath();
                        profile_img.setImageBitmap(r.getBitmap());
                    }
                }).show(getSupportFragmentManager());
*/
            }
        });

        birthdate_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });

        img_first_front_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for pic img
                imgseletcoption=3;
                selectImage();
            }
        });

        findViewById(R.id.submit_BT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String pass = passET.getText().toString();
                String confirm = confirmPassET.getText().toString();
                String mobile = mobileET.getText().toString();
                String countryId = mCountryId;
                String dateofbirthvalue=birthdate_edt.getText().toString();
                String referalcode = referral_code_ET.getText().toString();
                App.editor.commit();
                if (referalcode.isEmpty()) {
                    referalcode="JOIN SAM NOW";
                }
                if (!AppConfig.isStringNullOrBlank(name))
                {

                    if (!AppConfig.isStringNullOrBlank(dateofbirthvalue))
                    {
                        int useragevalue= Integer.parseInt(userage);
                        if (useragevalue>=18)
                        {
                        if (!AppConfig.isStringNullOrBlank(email) && AppConfig.isEmail(email)) {

                        if (!AppConfig.isStringNullOrBlank(pass) && pass.length() >= 6) {

                            if (!AppConfig.isStringNullOrBlank(confirm) && confirm.length() >= 6) {

                                if (confirm.equals(pass)) {

//                                    if (!AppConfig.isStringNullOrBlank(mobile) && mobile.length() >= 9 && mobile.length() <= 15) {

                                        if (!AppConfig.isStringNullOrBlank(countryId))
                                        {
                                            createAccountCall(dateofbirthvalue,name, email, pass, mobile, countryId, "", Constant.ANDROID, referalcode);
                                        } else {
                                            countryET.setError("Select Country");
                                            AppConfig.showToast("Select Country.");
                                        }
//                                    } else {
//                                        mobileET.setError("Please enter valid Mobile Number i.e less than 9 and more than 15.");
//                                        AppConfig.showToast("Please enter valid Mobile Number i.e less than 9 and more than 15.");
//                                    }
                                } else {
                                    AppConfig.showToast("Password not matched");
                                }
                            } else {
                                confirmPassET.setError("Please enter valid Confirm Password.");
                                AppConfig.showToast("Please enter valid Confirm Password.");
                            }
                        } else {
                            passET.setError("Please enter valid Password.");
                            AppConfig.showToast("Please enter valid Password.");
                        }
                    } else {
                        emailET.setError("Please enter valid Email.");
                        AppConfig.showToast("Please enter valid Email.");
                    }
                        } else {
                            birthdate_edt.setError("User must be 18 years old and up .");
                            AppConfig.showToast("User must be 18 years old and up .");
                        }
                    } else {
                        birthdate_edt.setError("Please select Date of Birth.");
                        AppConfig.showToast("Please select date of birth");
                    }
                 } else {
                    nameET.setError("Please enter Name.");
                    AppConfig.showToast("Please enter Name.");
                }
            }
        });

        phoneCodeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCountryCall();
            }
        });

        back_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    //..........for
    //for password
    public void selectImage()
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
            builder.setTitle("Select Image");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(SignupActivity.this, android.R.layout.simple_list_item_1);
            adapter.add("Take Picture");
            adapter.add("Choose from gallery");
            adapter.add("Cancel");

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            try{
                                if (Utility.isExternalStorageAvailable()) {
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileURI());
                                    if (intent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent, 1);
                                    }
                                } else {
                                    Toast.makeText(SignupActivity.this, "Need permission for access external directory", Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            break;

                        case 1:
                            try {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,
                                        "Select Picture"), 2);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                //  Log.e(e.getClass().getName(), e.getMessage(), e);
                            }
                            break;

                        case 2:
                            dialog.cancel();
                            break;
                    }
                }
            });
            builder.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Uri getPhotoFileURI() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssZ", Locale.ENGLISH);
        Date currentDate = new Date();
        String photoFileName = "photo.jpg";
        String fileName = simpleDateFormat.format(currentDate) + "_" + photoFileName;

        String APP_TAG = "ImageFolder";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            uri = Utility.getExternalFilesDirForVersion24Above(SignupActivity.this, Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        } else {
            uri = Utility.getExternalFilesDirForVersion24Below(SignupActivity.this, Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        }
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            switch (requestCode) {

                case 1:
                    setImage();
                    break;

                case 2:
                    Uri select = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), select);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream fOut;
                   try {
                        String photoname="profilepic.png";
                        File f = new File(getFilesDir(), photoname);
                        fOut = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        if(imgseletcoption==3)
                        {
                            idpreefpath = f.getAbsolutePath();
                            idproofbitmap = bitmap;
                            img_first_front_pic.setImageBitmap(bitmap);
                        }

                        if(imgseletcoption==2)
                        {
                            imagePath= f.getAbsolutePath();
                            profile_img.setImageBitmap(bitmap);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void setImage()
    {
        if(imgseletcoption==3)
        {
            idpreefpath = Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(idpreefpath);
            FileOutputStream fOut;
            try {
                File f = new File(idpreefpath);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();
                idpreefpath= f.getAbsolutePath();
                idproofbitmap=bitmap;
                img_first_front_pic.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }

        if(imgseletcoption==2)
        {
            imagePath = Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            FileOutputStream fOut;
            try {
                File f = new File(imagePath);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();
                imagePath= f.getAbsolutePath();
                profile_img.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }
      }
    //.............

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!

    }

    private void createAccountCall(String dateofbirthv,String name, String email, String pass,
                                   String mobile, String countryId, String fcmToken,
                                   String deviceType, String referal) {
        if (AppConfig.isInternetOn()) {
            final ProgressDialog dialog = new ProgressDialog(context);
            AppConfig.showLoading(dialog, "Creating Account..");
            String image;
            RequestBody fbody;
            MultipartBody.Part body;
            if (AppConfig.isStringNullOrBlank(imagePath))
            {
                image = "";
                fbody = RequestBody.create(MediaType.parse("image/*"), image);
                body = MultipartBody.Part.createFormData("user_image", image, fbody);
            } else {
                image = imagePath;
                fbody = RequestBody.create(MediaType.parse("image/*"), image);
                body = MultipartBody.Part.createFormData("user_image", image, fbody);
            }

            File filefirstfinal = null;
                if (idpreefpath != null)
                {
                    filefirstfinal = new File(idpreefpath);
                }
                try
                {
                    //for first
                    File userimg=new File(idpreefpath);
                    FileOutputStream out = new FileOutputStream(userimg);
                    idproofbitmap.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.close();
                    filefirstfinal = new File(idpreefpath);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            RequestBody requestFile1 = null;
            MultipartBody.Part body1;
            String imagenew;
            if (AppConfig.isStringNullOrBlank(idpreefpath))
            {
                imagenew = "";
                fbody = RequestBody.create(MediaType.parse("image/*"), imagenew);
                body1 = MultipartBody.Part.createFormData("user_photo_id", imagenew, fbody);
            } else {
                requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), filefirstfinal);
                body1 = MultipartBody.Part.createFormData("user_photo_id", filefirstfinal.getName(), requestFile1);
            }

                Call<ResponseBody> call = AppConfig.getLoadInterface().signUpCall(
                    AppConfig.setRequestBody(dateofbirthv),
                    AppConfig.setRequestBody(name),
                    AppConfig.setRequestBody(email),
                    AppConfig.setRequestBody(mobile),
                    AppConfig.setRequestBody(pass),
                    AppConfig.setRequestBody(countryId),
                    AppConfig.setRequestBody(deviceToken),
                    AppConfig.setRequestBody(countryET.getText().toString().trim()),
                    AppConfig.setRequestBody(deviceType),
                    AppConfig.setRequestBody(referal), body,
                        body1);

            Log.d("parameter",call.toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseData = response.body().string();
                            Log.d("parameter",responseData);
                            //                            Log.e("Data_response", responseData);
                            JSONObject object = new JSONObject(responseData);
                            if (object.getString(Constant.STATUS).equals("1") || object.getString(Constant.STATUS).equals("2"))
                            {
                 //               AppConfig.showToast("Registered Successfully.");
//                              ResisterResponse data = new Gson().fromJson(responseData, ResisterResponse.class);
                                nameET.setText("");
                                emailET.setText("");
                                passET.setText("");
                                confirmPassET.setText("");
                                mobileET.setText("");
                                countryET.setText("");
                                phoneCodeET.setText("");
                                referral_code_ET.setText("");
                                AppConfig.setStringPreferences(context, Constant.JWTToken, object.getString("token"));
                                getProfileCall(object.getString("token"));
                                //MainActivity.addFragment(ChildProfileFragment.newInstance(data.getChildData()), true);
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
                        ShowApiError(SignupActivity.this,"server error in eightface/create_user");
                    }
                    AppConfig.hideLoading(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppConfig.hideLoading(dialog);
                    errordurigApiCalling(SignupActivity.this,t.getMessage());
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    private void getCountryCall()
    {
        if (AppConfig.isInternetOn())
        {
            final ProgressDialog dialog = new ProgressDialog(context);
            AppConfig.showLoading(dialog, "Getting Countries...");
            Call<ResponseBody> call = AppConfig.getLoadInterface().countryList();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful())
                    {
                        try {
                            String responseData = response.body().string();
                            JSONObject object = new JSONObject(responseData);
                            if (object.getString(Constant.STATUS).equals("1"))
                            {
                                CountryResponse registerResponse = new Gson().fromJson(responseData, CountryResponse.class);
                                final ArrayList<Country> countrygender=new ArrayList<>();
                                countryList=new ArrayList<>();
                                countryList = registerResponse.getData();
                                for(int i=0;i<countryList.size();i++)
                                {
                                    countrygender.add(countryList.get(i));
                                }
                                final String[] gender = new String[registerResponse.getData().size()];
                                final String[] stateId = new String[registerResponse.getData().size()];
                                final String[] countryCode = new String[registerResponse.getData().size()];
                                for (int i = 0; i < registerResponse.getData().size(); i++)
                                {
                                    gender[i] = registerResponse.getData().get(i).getCountry();
                                    countryCode[i] = registerResponse.getData().get(i).getPhoneCode();
                                    stateId[i] = registerResponse.getData().get(i).getId();
                                }

                               /* final MaterialAlertDialogBuilder alert = new MaterialAlertDialogBuilder(context);
                                alert.setTitle("Select Country")
                                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                alert.setSingleChoiceItems(gender, position, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        country = gender[which];
                                        mCountryId = stateId[which];
                                        countryET.setText(countryCode[which]);
                                        phoneCodeET.setText(country);
                                        position = which;
                                    }
                                });
                                alert.show();*/
                                showDialogForSearchCountrynew(SignupActivity.this,countrygender,gender,stateId,countryCode,countryET,phoneCodeET);

                            } else
                                {
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
                        ShowApiError(SignupActivity.this,"server error in country-list");
                    }
                    AppConfig.hideLoading(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppConfig.hideLoading(dialog);
                    t.printStackTrace();
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    public void showDialogForSearchCountrynew(final Context mContext, final ArrayList<Country> countrygender, final String[] genderlist, final String [] stateId, final String [] countryCode, final TextView countryET, final TextView phoneCodeET)
    {
        // country, mCountryId
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogView = layoutInflater.inflate(R.layout.popup_wallet_pooling, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        TextView countrynamehint=dialogView.findViewById(R.id.countrynamehint);
        countrynamehint.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_view);
        final EditText searchEt = dialogView.findViewById(R.id.search_ET);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager1);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final CountryListAdapter adapterCredential = new CountryListAdapter(countrygender,genderlist, mContext, searchEt, new CountryListAdapter.SetOnItemClickListener() {

            @Override
            public void onItemClick(String country, String stateid,String phoneid)
            {
                country = country;
                mCountryId = stateid;
                countryET.setText(phoneid);
                phoneCodeET.setText(country);
                //position = which;
                dialog.dismiss();
            }
        });

        recyclerView.setAdapter(adapterCredential);
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (searchEt.getText().toString().length() > 0)
                {
                    dataPutMethods.filterCountry(searchEt.getText().toString(),countrygender,adapterCredential);
                } else {
                    adapterCredential.updateData(countrygender);
                }
            }
        });

        dialog.show();
    }

    private void getProfileCall(String token) {
        if (AppConfig.isInternetOn()) {
            final ProgressDialog dialog = new ProgressDialog(context);
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
                            Log.d("user profile",responseData);
                            JSONObject object = new JSONObject(responseData);
                            if (object.getInt(Constant.STATUS) == 1) {
                                UserResponse data = new Gson().fromJson(responseData, UserResponse.class);
                                try {
                                    String referalcode = data.getUser().unique_code;
                                    App.editor.putString(Constant.USER_REFERAL_CODE,referalcode);
                                    App.editor.apply();
                                    //.........
                                    putGoogleAuthStatus(data.getUser().getGauth_status(),data.getUser().getGoogle_authentication(),data.getUser().getGoogle_auth_secret());
                                    JSONObject jsonob = object.getJSONObject(Constant.eth_details);
                                    if(jsonob.length()!=0)
                                    {
                                        dataPutMethods.putOtherWallet(data.getEth_details().getPublic_key(),"",data.getEth_details().getWallet_address(), data.getEth_details().getWallet_id(),data.getEth_details().getSecret());
                                    }

                                    JSONObject jsonobusdt = object.getJSONObject(Constant.usdt_details);
                                    if(jsonobusdt.length()!=0)
                                    {
                                        dataPutMethods.putUsdtWallet(data.getUsdt_details().getPublic_key(),data.getUsdt_details().getWallet_address(),data.getUsdt_details().getWallet_id(),data.getUsdt_details().getSecret());
                                    }

                                    JSONObject jsonobtron = object.getJSONObject(Constant.usdt_details);
                                    if(jsonobtron.length()!=0)
                                    {
                                        dataPutMethods.puttronWallet(data.getUsdt_details().getPublic_key(),data.getUsdt_details().getWallet_address().toString(),data.getUsdt_details().getWallet_id().toString(),data.getUsdt_details().getSecret().toString());
                                    }

                                    JSONObject jsonobbtc = object.getJSONObject(Constant.btc_details);
                                    if(jsonobbtc.length()!=0)
                                    {
                                        dataPutMethods.putBTCWallet(data.getBtc_details().getPublic_key(),data.getBtc_details().getWallet_address(),data.getBtc_details().getWallet_id(),data.getBtc_details().getSecret());
                                    }

                                    JSONObject jsonsamkoin = object.getJSONObject(Constant.samkoin_details);
                                    if(jsonsamkoin.length()!=0)
                                    {
                                        dataPutMethods.putSamKoinWallet(data.getSam_koin_details().getPublic_key(),data.getSam_koin_details().getWallet_address(),data.getSam_koin_details().getWallet_id(),data.getSam_koin_details().getSecret());
                                    }

                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                openHomeActivity(data);
                            } else if (object.getInt(Constant.STATUS) == 3) {
                                AppConfig.showToast(object.getString("msg"));
                                AppConfig.openSplashActivity((Activity)context);
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
                        ShowApiError(SignupActivity.this,"server error in eightface/user-profile");
                    }
                    AppConfig.hideLoading(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppConfig.hideLoading(dialog);
                    errordurigApiCalling(SignupActivity.this,t.getMessage());
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }


    private void openHomeActivity(UserResponse user) {
        Intent intent = new Intent(SignupActivity.this, OTPActivity.class);
        App.editor.putString("email", user.getUser().getEmail());
        App.editor.putString("signUp", "yes");
        App.editor.putString("switchUser", "no");
        App.editor.putString(Constant.USER_EMAIL, user.getUser().getEmail());
        App.editor.putString(Constant.USER_NAME, user.getUser().getName());
        App.editor.putString(Constant.USER_MOBILE, user.getUser().getMobile());
        App.editor.commit();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //for date of birth
    private void SelectDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dateDialog = new DatePickerDialog(SignupActivity.this,pickerListener, year, month, day);
        dateDialog.getDatePicker().setMaxDate(new Date().getTime());
        dateDialog.show();

    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            userage=getAge(year,month,day);
          //  Toast.makeText(context, "your age "+userage, Toast.LENGTH_SHORT).show();
            // Show selected date
            birthdate_edt.setText(new StringBuilder().append(day)
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
    //................
    @Override
    public void onBackPressed() {
        back_IV.performClick();
    }
}