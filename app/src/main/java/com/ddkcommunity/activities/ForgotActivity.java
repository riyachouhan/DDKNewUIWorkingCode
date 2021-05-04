package com.ddkcommunity.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ddkcommunity.App;
import com.ddkcommunity.R;
import com.ddkcommunity.Constant;
import com.ddkcommunity.model.CheckOtpResponse;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.ddkcommunity.utilies.ScalingUtilities;
import com.ddkcommunity.utilies.Utility;
import com.ddkcommunity.utilies.dataPutMethods;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;
import static com.ddkcommunity.utilies.dataPutMethods.getSettingServerDataSt;
import static com.ddkcommunity.utilies.dataPutMethods.hideKeyBoard;

public class ForgotActivity extends AppCompatActivity {

    EditText emailET;
    Context context;
    Bitmap changespasswordbitmap;
    String changespasswordimgpath;
    LinearLayout back_view;
    private Uri uri;
    ImageView img_first_front_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        back_view=findViewById(R.id.back_view);
        context = this;
        emailET = findViewById(R.id.email_ET);
        emailET.requestFocus();
        findViewById(R.id.submit_BT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String email = emailET.getText().toString();

                if (!AppConfig.isStringNullOrBlank(email))
                {
                    if (AppConfig.isEmail(email))
                    {
                        sendOtp(email);
                        //  getProfileCall(email);
                    }else
                    {
                        emailET.setError("Please enter valid Email.");
                        AppConfig.showToast("Please enter valid Email.");
                    }
                } else {
                    emailET.setError("Please enter Email.");
                    AppConfig.showToast("Please enter Email.");
                }
            }
        });

        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getProfileCall(String id) {
        if (AppConfig.isInternetOn()) {
            final ProgressDialog dialog = new ProgressDialog(context);

            AppConfig.showLoading(dialog, "Fetching User Details..");
            Call<ResponseBody> call = AppConfig.getLoadInterface().forgotCall(AppConfig.setRequestBody(id));

            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String responseData = response.body().string();
                            JSONObject object = new JSONObject(responseData);
                            if (object.getString(Constant.STATUS).equals("1")) {
                                emailET.setText("");
                                AppConfig.showToast(object.getString("msg"));
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
                        ShowApiError(ForgotActivity.this,"Server error in forgot-password");
                    }
                    AppConfig.hideLoading(dialog);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    AppConfig.hideLoading(dialog);
                    errordurigApiCalling(ForgotActivity.this,t.getMessage());
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    private void sendOtp(final String email) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email",email);
        final ProgressDialog dialog = new ProgressDialog(ForgotActivity.this);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().forgotPAssword(hm).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().status == 1) {
                    try {
                        String passwordchangetoken = response.body().forget_password;
                        initOtpVerifiaction(passwordchangetoken,email,ForgotActivity.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 3) {
                    AppConfig.showToast(response.body().msg);
                    AppConfig.openSplashActivity(ForgotActivity.this);
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 0) {
                    AppConfig.showToast(response.body().msg);
                } else {
                    AppConfig.showToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }


    private void verifiyOtpUser(final BottomSheetDialog dialogNew,final String otpvalue,final String email,final String updatettoke) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email",email);
        hm.put("user_otp",otpvalue);
        hm.put("verify_for","forget_password");
        final ProgressDialog dialog = new ProgressDialog(ForgotActivity.this);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().checkOtp(hm).enqueue(new Callback<CheckOtpResponse>() {
            @Override
            public void onResponse(Call<CheckOtpResponse> call, Response<CheckOtpResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful() && response.body().status == 1) {
                    Log.d("otpre","::"+response.body());
                    try {
                       initEmailVerification(email,updatettoke);
                        dialogNew.dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 3) {
                    AppConfig.showToast(response.body().msg);
                    AppConfig.openSplashActivity(ForgotActivity.this);
                } else if (response.isSuccessful() && response.body() != null && response.body().status == 0) {
                    AppConfig.showToast(response.body().msg);
                } else {
                    AppConfig.showToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<CheckOtpResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void initOtpVerifiaction(final String updatetoken,final String emails,final Activity mContext)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View dialogView;
        dialogView = layoutInflater.inflate(R.layout.otpverificationlayout, null);
        final BottomSheetDialog dialogNew = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialogNew.setContentView(dialogView);
        CommonMethodFunction.setupFullHeight(mContext,dialogNew);
        ImageView logo1=dialogView.findViewById(R.id.logo1);
        final LinearLayout otherview=dialogView.findViewById(R.id.otherview);
        otherview.setVisibility(View.VISIBLE);
        logo1.setVisibility(View.GONE);
        final LinearLayout back_view=dialogView.findViewById(R.id.back_view);
        final TextView btnVerify =dialogView.findViewById(R.id.btnVerify);
        final EditText otp_edt1 =dialogView.findViewById(R.id.otp_edt11);
        final EditText otp_edt2 =dialogView.findViewById(R.id.otp_edt12);
        final EditText otp_edt3 =dialogView.findViewById(R.id.otp_edt13);
        final EditText otp_edt4 =dialogView.findViewById(R.id.otp_edt14);

        btnVerify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StringBuffer str_top = new StringBuffer();
                str_top.append(otp_edt1.getText().toString()).append(otp_edt2.getText().toString()).append(otp_edt3.getText().toString()).append(otp_edt4.getText().toString());
                if (str_top.length() > 0 && str_top.length() == 4)
                {
                    String otpnew=str_top.toString();
                        verifiyOtpUser(dialogNew,otpnew,emails,updatetoken);
                    //............
                }
            }
        });

        otp_edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt1.length() == 0) {
                    otp_edt1.requestFocus();
                }
                if (otp_edt1.length() == 1) {
                    otp_edt1.clearFocus();
                    otp_edt2.requestFocus();
                    otp_edt2.setCursorVisible(true);
                }
            }
        });

        otp_edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt2.length() == 0) {
                    otp_edt2.requestFocus();
                }
                if (otp_edt2.length() == 1) {
                    otp_edt2.clearFocus();
                    otp_edt3.requestFocus();
                    otp_edt3.setCursorVisible(true);
                }
            }
        });


        otp_edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt3.length() == 0) {
                    otp_edt3.requestFocus();
                }
                if (otp_edt3.length() == 1) {
                    otp_edt3.clearFocus();
                    otp_edt4.requestFocus();
                    otp_edt4.setCursorVisible(true);
                }
            }
        });


        otp_edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (otp_edt4.length() == 0) {
                    otp_edt4.requestFocus();
                }
                if (otp_edt4.length() == 1) {
                    otp_edt4.requestFocus();
                }
            }
        });
        otp_edt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    otp_edt1.requestFocus();
                }
                return false;
            }
        });
        otp_edt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp_edt2.requestFocus();
                }
                return false;
            }
        });
        otp_edt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp_edt3.requestFocus();
                }
                return false;
            }
        });

        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNew.dismiss();
            }
        });
        dialogNew.show();
    }

    private void initEmailVerification(final String emailid,final String updatetoken)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(ForgotActivity.this);
        final View dialogViewp;
            dialogViewp = layoutInflater.inflate(R.layout.changepasswordactivity, null);
            final BottomSheetDialog dialogp = new BottomSheetDialog(ForgotActivity.this, R.style.DialogStyle);
            dialogp.setContentView(dialogViewp);
            CommonMethodFunction.setupFullHeight(ForgotActivity.this,dialogp);
            img_first_front_pic=dialogp.findViewById(R.id.img_first_front_pic);
            final EditText confirmpassword=dialogp.findViewById(R.id.confirmpassword);
            final EditText newpassword=dialogp.findViewById(R.id.newpassword);
            LinearLayout back_view=dialogp.findViewById(R.id.back_view);
            TextView btnnext=dialogp.findViewById(R.id.btnnext);
            changespasswordimgpath ="";
            changespasswordbitmap=null;
            img_first_front_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //for pic img
                    if (ContextCompat.checkSelfPermission(ForgotActivity.this, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ForgotActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(ForgotActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        selectImage();

                    } else {
                        ActivityCompat.requestPermissions(ForgotActivity.this,
                                new String[]{Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                    }

                }
            });
            btnnext.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String newpassworvalue=newpassword.getText().toString().trim();
                    String confirmpassordvalu=confirmpassword.getText().toString().trim();
                    if(newpassworvalue.equalsIgnoreCase(""))
                    {
                        Toast.makeText(context, "Please enter new password", Toast.LENGTH_SHORT).show();
                    }else if(confirmpassordvalu.equalsIgnoreCase(""))
                    {
                        Toast.makeText(context, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                    }else if(!newpassworvalue.equalsIgnoreCase(confirmpassordvalu))
                    {
                        Toast.makeText(context, "Confirm password not match", Toast.LENGTH_SHORT).show();
                    }else if(changespasswordimgpath.equalsIgnoreCase(""))
                    {
                        Toast.makeText(context, "Please upload ID proof with image", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        updatePasswordCall(dialogp, emailid, updatetoken, newpassworvalue);
                    }
                }
            });
            back_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogp.dismiss();
                }
            });
            dialogp.show();
    }

    //.............
    public void selectImage()
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(ForgotActivity.this);
            builder.setTitle("Select Image");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ForgotActivity.this, android.R.layout.simple_list_item_1);
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
                                    Toast.makeText(ForgotActivity.this, "Need permission for access external directory", Toast.LENGTH_SHORT).show();
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
            uri = Utility.getExternalFilesDirForVersion24Above(ForgotActivity.this, Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        } else {
            uri = Utility.getExternalFilesDirForVersion24Below(ForgotActivity.this, Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        }
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                setImage();
            }

            if (requestCode == 2) {
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
                    changespasswordimgpath = f.getAbsolutePath();
                    changespasswordbitmap=bitmap;
                    img_first_front_pic.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void setImage() {
        changespasswordimgpath = Utility.getFile().getAbsolutePath();
        Bitmap bitmap = BitmapFactory.decodeFile(changespasswordimgpath);
        FileOutputStream fOut;
        try {
            File f = new File(changespasswordimgpath);
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
            fOut.flush();
            fOut.close();
            changespasswordimgpath = f.getAbsolutePath();
            changespasswordbitmap=bitmap;
            img_first_front_pic.setImageBitmap(bitmap);

        } catch (Exception e){
            e.printStackTrace();
            StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
            e.printStackTrace(new PrintWriter(stackTrace));
        }
    }

    //................

    private void updatePasswordCall(final BottomSheetDialog dialogp,final String email,final String updatetoken,final String newpassword) {

        if (AppConfig.isInternetOn())
        {
            final ProgressDialog dialog = new ProgressDialog(ForgotActivity.this);
            AppConfig.showLoading(dialog, "Please wait..........");
            File file = null;
            try {
                if (changespasswordimgpath != null) {
                    file = new File(changespasswordimgpath);
                }

                try {
                    File userimg=new File("file://" + changespasswordimgpath);
                    FileOutputStream out = new FileOutputStream(userimg);
                    changespasswordbitmap.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.close();
                    file = new File(changespasswordimgpath);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("user_photo_id", file.getName(), requestFile);

                Call<ResponseBody> call = AppConfig.getLoadInterface().resetPassword(
                        AppConfig.setRequestBody(email),
                        AppConfig.setRequestBody(updatetoken),
                        AppConfig.setRequestBody(newpassword),
                        body
                );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful())
                        {
                            dialog.dismiss();
                            try {
                                String responseData = response.body().string();
                                JSONObject object = new JSONObject(responseData);
                                if (object.getInt(Constant.STATUS) == 1)
                                {
                                    PasswordChangesSuccessfully();
                                    dialogp.dismiss();

                                 } else if (object.getInt(Constant.STATUS) == 3) {
                                    AppConfig.showToast(object.getString("msg"));
                                    AppConfig.openSplashActivity(ForgotActivity.this);
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
                            dialog.dismiss();
                            ShowApiError(ForgotActivity.this,"server error in edit-user-profile");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AppConfig.hideLoading(dialog);
                        errordurigApiCalling(ForgotActivity.this,t.getMessage());
                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
                //  Toast.makeText(getContext(),"error", Toast.LENGTH_SHORT).show();
            }
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    private void PasswordChangesSuccessfully()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(ForgotActivity.this);
        final View dialogView;
        dialogView = layoutInflater.inflate(R.layout.passwordchangesuccesslayout, null);
        final BottomSheetDialog dialogs = new BottomSheetDialog(ForgotActivity.this, R.style.DialogStyle);
        dialogs.setContentView(dialogView);
        CommonMethodFunction.setupFullHeight(ForgotActivity.this,dialogs);
        LinearLayout back_view=dialogs.findViewById(R.id.back_view);
        TextView btnnext=dialogs.findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogs.dismiss();
                startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
            }
        });
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.dismiss();
            }
        });
        dialogs.show();
    }

}