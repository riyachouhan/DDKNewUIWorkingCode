package com.ddkcommunity.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ddkcommunity.App;
import com.ddkcommunity.AppSignatureHelper;
import com.ddkcommunity.BuildConfig;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.interfaces.GetUSDAndBTCCallback;
import com.ddkcommunity.model.Country;
import com.ddkcommunity.model.CountryResponse;
import com.ddkcommunity.model.baseUrlModel;
import com.ddkcommunity.model.buyCryptoModel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.model.versionModel;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CamomileSpinner;
import com.github.omadahealth.lollipin.lib.managers.AppLock;
import com.github.omadahealth.lollipin.lib.managers.LockManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.Constant.BASE_URL_versionName;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowVersionError;
import static com.ddkcommunity.utilies.dataPutMethods.generateEncyTab;
import static com.ddkcommunity.utilies.dataPutMethods.putGoogleAuthStatus;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;


public class SplashActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, InstallStateUpdatedListener {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static boolean isAppRunning;
    public static Activity activity;
    String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    private int MY_REQUEST_CODE = 1010;
    private AppUpdateManager appUpdateManager;
    private boolean isComeFromUpdate = false;
    private Context mContext;
    public static List<Country> countryList;
    public static String finalcall,baseurl;
    AppSignatureHelper appSignatureHelper;
    public static int customadds=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appUpdateManager = AppUpdateManagerFactory.create(this);
        activity = this;
        mContext = this;
        CamomileSpinner spinner1 = findViewById(R.id.spinner1);
        //for new
       appSignatureHelper = new AppSignatureHelper(SplashActivity.this);
        //for value
        String IVparam=App.pref.getString(Constant.IVPARAM, "");
        if(IVparam ==null || IVparam.equalsIgnoreCase("null") || IVparam.equalsIgnoreCase("")) {
            //for code
            generateEncyTab();
        }
        //...........
        spinner1.start();
        //proceed();
        getUserAppApi();
        appUpdateManager.registerListener(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new com.google.android.gms.tasks.OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                try {
                    App.RegEditor.putString(Constant.FIREBASE_TOKEN, newToken);
                    App.RegEditor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAppRunning = false;
        appUpdateManager.unregisterListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserAppApi();
               // proceed();
            }
        }
    }

    private void proceed()
    {
        //**************************** Location ( Latitude & longitude ) *********************************
        if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
        } else {
            //if we have use the debug mode and release mode for the testing purpose then we have use the check login.
            //And upload on the play store then we have use the check for update app.
//            checkForUpdateApp();
            checkLogin();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void getCountryCall() {
        if (AppConfig.isInternetOn())
        {
            Call<ResponseBody> call = AppConfig.getLoadInterface().countryList();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("splash countrycall",response.body().toString());
                    if (response.isSuccessful()) {
                        try {
                            String responseData = response.body().string();
                            if(responseData!=null && !responseData.equalsIgnoreCase(null) && !responseData.equalsIgnoreCase(""))
                            {
                            JSONObject object = new JSONObject(responseData);
                            if (object.getInt(Constant.STATUS) == 1) {

                               countryList=new ArrayList();
                                CountryResponse registerResponse = new Gson().fromJson(responseData, CountryResponse.class);
                                countryList = registerResponse.getData();

                            } else if (object.getInt(Constant.STATUS) == 3) {
                                AppConfig.showToast(object.getString("msg"));
                            } else {
                                AppConfig.showToast(object.getString("msg"));
                            }
                            }else
                            {
                                AppConfig.showToast("No Data Found");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ShowApiError(SplashActivity.this,"server error in country-list");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    private void getFacebookShare()
    {
        if (AppConfig.isInternetOn())
        {
        Call<ResponseBody> call = AppConfig.getLoadInterface().getFacebookShare();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("splash facebook",response.body().toString());
                if (response.isSuccessful())
                {
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        String status=jsonObject.getString("status");
                        if(status.equalsIgnoreCase("1"))
                        {
                            JSONObject jObject = jsonObject.getJSONObject("data");
                            String imgview = jObject.getString("image");
                            String combineurl=Constant.SLIDERIMG+imgview;
                            AppConfig.setStringPreferences(App.getInstance(), Constant.FACEBOOKURL,""+combineurl);

                        }else
                        {
                            String msg=jsonObject.getString("msg");
                        }

                    } catch (Exception e)
                    {
                        ShowApiError(activity,"exception in ninethface/social-media-share");
                        e.printStackTrace();
                    }
                } else {
                    ShowApiError(activity,"server error in ninethface/social-media-share");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    private void getVersionApp()
    {
        if (AppConfig.isInternetOn())
        {
            Call<versionModel> call = AppConfig.getLoadInterface().getVersioning();
            call.enqueue(new Callback<versionModel>() {
                @Override
                public void onResponse(Call<versionModel> call, Response<versionModel> response) {
                    if (response.isSuccessful())
                    {
                        Log.d("splash facebook",response.body().toString());
                        try {
                            if(response.body().getStatus()==1)
                            {
                                String imgview = response.body().getData().getAndroidVersion();
                                String versionName = BuildConfig.VERSION_NAME;
                                int versionvalue=versionCompare(versionName,imgview);
                                if(versionvalue < 0)
                                {
                                    ShowVersionError(SplashActivity.this);
                                }else
                                {
                                    proceed();
                                }
                            }
                        } catch (Exception e)
                        {
                            ShowApiError(activity,"exception in commondetails/get-app-version");
                            e.printStackTrace();
                        }
                    } else {
                        ShowApiError(activity,"server error in commondetails/get-app-version");
                    }
                }

                @Override
                public void onFailure(Call<versionModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            AppConfig.showToast("No internet.");
        }
    }

    static int versionCompare(String v1, String v2)
    {
        // vnum stores each numeric part of version
        int vnum1 = 0, vnum2 = 0;

        // loop until both String are processed
        for (int i = 0, j = 0; (i < v1.length()
                || j < v2.length());) {
            // Storing numeric part of
            // version 1 in vnum1
            while (i < v1.length()
                    && v1.charAt(i) != '.') {
                vnum1 = vnum1 * 10
                        + (v1.charAt(i) - '0');
                i++;
            }

            // storing numeric part
            // of version 2 in vnum2
            while (j < v2.length()
                    && v2.charAt(j) != '.') {
                vnum2 = vnum2 * 10
                        + (v2.charAt(j) - '0');
                j++;
            }

            if (vnum1 > vnum2)
                return 1;
            if (vnum2 > vnum1)
                return -1;

            // if equal, reset variables and
            // go for next numeric part
            vnum1 = vnum2 = 0;
            i++;
            j++;
        }
        return 0;
    }

    //for sererchangeurl
    private void getUserAppApi()
    {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("appkey", appSignatureHelper.getAppSignatures().get(0));
        AppConfig.getSingleLoadInterface().getConstantUrl(AppConfig.getStringPreferences(SplashActivity.this, Constant.JWTToken),hm).enqueue(new Callback<baseUrlModel>() {
            @Override
            public void onResponse(Call<baseUrlModel> call, Response<baseUrlModel> response) {
                try {
                    Log.d("sam erro par currency",response.body().toString());
                    if (response.isSuccessful() && response.body() != null)
                    {
                        if (response.body().getStatus() == 1)
                        {
                            try {
                                baseurl=response.body().getData().getUrl();
                                finalcall=baseurl+BASE_URL_versionName;
                                App.editor.putString(Constant.AllURllive,finalcall);
                                App.editor.putString( Constant.apiserver,baseurl);
                                App.editor.commit();
                                getVersionApp();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else {
                            AppConfig.showToast(response.body().getMsg());
                        }
                    } else {
                        ShowApiError(mContext,"server error commondetails/get-constant-url");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<baseUrlModel> call, Throwable t) {
//                dialog.dismiss();
            }
        });
    }
    //.................

    private void checkLogin()
    {
        getFacebookShare();
        getCountryCall();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (App.pref.getBoolean(Constant.IS_LOGIN, false)) {
                        if (AppConfig.isInternetOn()) {
                            getProfileCall(AppConfig.getStringPreferences(mContext, Constant.JWTToken));
                        } else {
                            AppConfig.ShowAlertDialog(activity, "Please Connect to internet");
                        }
                    } else {
                        if (AppConfig.isInternetOn())
                        {
                            SharedPreferences sharedPrefs = mContext.getSharedPreferences(
                                    AppConfig.PREF_UNIQUE_ID, Context.MODE_PRIVATE);
                            String uniqueID = sharedPrefs.getString(AppConfig.PREF_UNIQUE_ID, null);

                            App.editor.clear();
                            App.editor.commit();

                            if (uniqueID != null && !uniqueID.isEmpty()) {
                                SharedPreferences.Editor editor = sharedPrefs.edit();
                                editor.putString(AppConfig.PREF_UNIQUE_ID, uniqueID);
                                editor.commit();
                            }

                            LockManager lockManager = LockManager.getInstance();
                            lockManager.getAppLock().setPasscode(null);

                            startActivity(new Intent(activity, LoginActivity.class));
                            finish();
                        } else {
                            AppConfig.ShowAlertDialog(activity, "Please Connect to internet");
                        }
                    }
                }
            }, 1500);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
    }

    private void latestDDKPrice()
    {
        UserModel.getInstance().getUSDCall(new GetUSDAndBTCCallback() {
            @Override
            public void getValues(BigDecimal btc, BigDecimal usd) {
                if (usd != null) {
                    BigDecimal ONE_HUNDRED = new BigDecimal(100);
                    BigDecimal buy = usd.multiply(UserModel.getInstance().ddkBuyPercentage).divide(ONE_HUNDRED);
                    BigDecimal sell = usd.multiply(UserModel.getInstance().ddkSellPercentage).divide(ONE_HUNDRED);

                    if (UserModel.getInstance().ddkBuyPrice == null) {
                        UserModel.getInstance().ddkBuyPrice = buy.add(usd);
                        UserModel.getInstance().ddkSellPrice = usd.subtract(sell);
                        //  LocalBroadcastManager.getInstance(CustomPinActivity.this).sendBroadcast(new Intent("updatePrice"));
                    }
                }
            }
        },SplashActivity.this);
    }

    private void getProfileCall(String token) {
        if (AppConfig.isInternetOn()) {
            final ProgressDialog dialog = new ProgressDialog(SplashActivity.this);
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
                            if (object.getInt(Constant.STATUS) == 1)
                            {
                                latestDDKPrice();
                                UserResponse data = new Gson().fromJson(responseData, UserResponse.class);
                                AppConfig.setUserData(mContext, data);
                                String user_idvalue=data.getUser().getId().toString();
                                String referalcode = data.getUser().unique_code;
                                App.editor.putString(Constant.USER_REFERAL_CODE,referalcode);
                                App.editor.putString(Constant.USER_ID,user_idvalue);
                                App.editor.putString(Constant.USER_EMAIL, data.getUser().getEmail());
                                App.editor.putString(Constant.USER_NAME, data.getUser().getName());
                                App.editor.putString(Constant.USER_MOBILE, data.getUser().getMobile());
                                App.editor.putBoolean(Constant.IS_LOGIN, true);
                                App.editor.commit();
                                putGoogleAuthStatus(data.getUser().getGauth_status(),data.getUser().getGoogle_authentication(),data.getUser().getGoogle_auth_secret());
                                if (App.pref.getBoolean(AppConfig.isPin, false)) {
                                    Intent intent = new Intent(SplashActivity.this, CustomPinActivity.class);
                                    intent.putExtra(AppLock.EXTRA_TYPE, AppLock.UNLOCK_PIN);
                                    startActivityForResult(intent, MainActivity.REQUEST_CODE_DISABLE);
                                } else {
                                    if(data.getEth_details()!=null)
                                    openHomeActivity();
                                }
                            } else if (object.getInt(Constant.STATUS) == 3) {
                                AppConfig.showToast(object.getString("msg"));
                                AppConfig.openSplashActivity((Activity)mContext);
                            } else {
                                AppConfig.showToast(object.getString("msg"));
                            }
                        } catch (Exception e) {
                            ShowApiError(SplashActivity.this,"exception in eightface/user-profile");
                            e.printStackTrace();
                        }
                    } else {
                        ShowApiError(SplashActivity.this,"server error in eightface/user-profile");
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

    private void checkForUpdateApp() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        new OnSuccessListener<AppUpdateInfo>() {
                            @Override
                            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                                if (appUpdateInfo.updateAvailability()
                                        == UpdateAvailability.UPDATE_AVAILABLE) {
                                    // If an in-app update is already running, resume the update.
                                    try {
                                        appUpdateManager.startUpdateFlowForResult(
                                                appUpdateInfo,
                                                IMMEDIATE,
                                                SplashActivity.this,
                                                MY_REQUEST_CODE);
                                    } catch (IntentSender.SendIntentException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    if (ActivityCompat.checkSelfPermission(SplashActivity.this,
                                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                                            ActivityCompat.checkSelfPermission(SplashActivity.this,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                                        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                                                PERMISSION_REQUEST_CODE);
                                        isComeFromUpdate = true;
                                    } else {
                                        checkLogin();
                                    }
                                }
                            }
                        });
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.tvText),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
                checkLogin();
            }
        });
        snackbar.setActionTextColor(
                getResources().getColor(R.color.white));
        snackbar.show();
    }

    private void openHomeActivity()
    {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.REQUEST_CODE_DISABLE)
        {
            SplashActivity.customadds=1;
            openHomeActivity();
        }
        if (requestCode == MY_REQUEST_CODE)
        {
            if (resultCode != RESULT_OK)
            {
                checkForUpdateApp();
            }
        }
    }

    @Override
    public void onStateUpdate(InstallState state) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate();
        }
    }
}
