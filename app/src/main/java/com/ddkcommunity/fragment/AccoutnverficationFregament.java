package com.ddkcommunity.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.LoadInterface;
import com.ddkcommunity.MyBounceInterpolator;
import com.ddkcommunity.R;
import com.ddkcommunity.UserModel;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.activities.OTPActivity;
import com.ddkcommunity.fragment.credential.CredentialsFragment;
import com.ddkcommunity.model.SAMPDModel;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.model.verifcationFundSource;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.ddkcommunity.utilies.ScalingUtilities;
import com.ddkcommunity.utilies.Utility;
import com.ddkcommunity.utilies.dataPutMethods;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import static android.app.Activity.RESULT_OK;
import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.ShowSAMPDDialog;
import static com.ddkcommunity.utilies.dataPutMethods.ShowSameWalletDialog;
import static com.ddkcommunity.utilies.dataPutMethods.ShowbasicValiationView;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;
import static com.ddkcommunity.utilies.dataPutMethods.setVerificationdata;
import static com.facebook.FacebookSdk.getApplicationContext;

public class AccoutnverficationFregament extends Fragment implements View.OnClickListener {

    private Context mContext;
    Animation myAnim;
    int i=0;
    TextView thirdview,secondview,firstview;
    String videoPath1;
    File mediaFile1;
    private static final int VIDEO_CAPTURE = 101;
    Bitmap frontimgfirstbitmap,backimgfirstbitmap,frontimgsecondbitmap,backimgsecondbitmap;
    String otp;
    public StringBuffer str_top;
    private View rootView = null;
    private View view;
    private CircleProgressBar circularProgressBar2;
    File videofile;
    private RelativeLayout email_layout,mobile_layout,fund_layout,identity_layout,video_layout,address_layout;
    private ImageView email_icon_img,mobile_icon_img,fund_icon_img,identity_icon_img,video_icon_img,address_icon_img;
    public int progrescount=0;
    TextView hintprogree;
    private UserResponse userData;
    Bitmap changespasswordbitmap;
    String changespasswordimgpath;
    String videostatusvalue,identitystayuvalue,addressstavalue,emailstatusvalue,mobilestatevalue,fundsttusvalue;
    private BottomSheetDialog dialog;
    private static String billingImagePath,imagePathFirstFront = "",imagePathFirstBack = "",imagePathSecondFront = "",imagePathSecondBak = "";
    Bitmap billingBitmap;
    String selectedImagePath;
    LinearLayout recordview_data;
    TextView submitvideo,niteview,Nextvideo,tvMobileNumber,btnsendOtp;
    int SELECT_VIDEO_REQUEST=100;
    String selectedVideoPath;
    TextView fund_text_status,address_text_status,video_text_status,identity_text_status,mobile_text_status,email_text_status;
    ArrayList<verifcationFundSource.Datum> fundslist;
    ArrayList<verifcationFundSource.Datum> govermentidlist;
    TextView addressveirifcationote,videoveirifcationote,identitiyveirifcationote
            ,fundveirifcationote,mobileveirifcationote,emailveirifcationote;
    TextView corporatehint,corporatedailylimit,corporatemonthlylimit,
    basichintlimit,basicdailylimit,basicmonthlylimit,
    fullverifiedhintlimit,fullyvarifieddailylimit,fullyvarifiedmonthlylimit,contactus_view;
    TextView  basicinforlayout,fullyforlayout,basiclevelstaus,fullyveririefstatus;
    private Uri uri;
    int selecetdimgtype=0;
    ImageView img_first_front_pic;
    ImageView img_first_back_pic,img_second_front_pic,img_second_back_pic;
    ImageView billingview;
    TextView submitview;
    TextView submitimgbilling;

    public AccoutnverficationFregament() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            view = inflater.inflate(R.layout.fragment_accountverificationlayout, container, false);
            rootView = view;
            mContext = getActivity();
            userData = AppConfig.getUserData(mContext);
            getAllViewIds();
        }
        return rootView;
    }

    public void showNoteSection(String viewvalue,String notestring,TextView notetextview)
    {
        if(viewvalue.equalsIgnoreCase("1"))
        {
            notetextview.setText(notestring);
            notetextview.setVisibility(View.VISIBLE);
        }else
        {
            notetextview.setVisibility(View.GONE);
        }
    }

    public void getAllViewIds()
    {
        basiclevelstaus=rootView.findViewById(R.id.basiclevelstaus);
        fullyveririefstatus=rootView.findViewById(R.id.fullyveririefstatus);
        basicinforlayout=rootView.findViewById(R.id.basicinforlayout);
        fullyforlayout=rootView.findViewById(R.id.fullyforlayout);
        contactus_view=rootView.findViewById(R.id.contactus_view);
        corporatedailylimit=rootView.findViewById(R.id.corporatedailylimit);
        corporatemonthlylimit=rootView.findViewById(R.id.corporatemonthlylimit);
        basichintlimit=rootView.findViewById(R.id.basichintlimit);
        basicdailylimit=rootView.findViewById(R.id.basicdailylimit);
        basicmonthlylimit=rootView.findViewById(R.id.basicmonthlylimit);
        fullverifiedhintlimit=rootView.findViewById(R.id.fullverifiedhintlimit);
        fullyvarifieddailylimit=rootView.findViewById(R.id.fullyvarifieddailylimit);
        fullyvarifiedmonthlylimit=rootView.findViewById(R.id.fullyvarifiedmonthlylimit);
        addressveirifcationote=rootView.findViewById(R.id.addressveirifcationote);
        videoveirifcationote=rootView.findViewById(R.id.videoveirifcationote);
        identitiyveirifcationote=rootView.findViewById(R.id.identitiyveirifcationote);
        fundveirifcationote=rootView.findViewById(R.id.fundveirifcationote);
        mobileveirifcationote=rootView.findViewById(R.id.mobileveirifcationote);
        emailveirifcationote=rootView.findViewById(R.id.emailveirifcationote);
        fund_text_status=rootView.findViewById(R.id.fund_text_status);
        address_text_status=rootView.findViewById(R.id.address_text_status);
        video_text_status=rootView.findViewById(R.id.video_text_status);
        identity_text_status=rootView.findViewById(R.id.identity_text_status);
        mobile_text_status=rootView.findViewById(R.id.mobile_text_status);
        email_text_status=rootView.findViewById(R.id.email_text_status);
        hintprogree=rootView.findViewById(R.id.hintprogree);
        email_icon_img=rootView.findViewById(R.id.email_icon_img);
        mobile_icon_img=rootView.findViewById(R.id.mobile_icon_img);
        fund_icon_img=rootView.findViewById(R.id.fund_icon_img);
        identity_icon_img=rootView.findViewById(R.id.identity_icon_img);
        video_icon_img=rootView.findViewById(R.id.video_icon_img);
        address_icon_img=rootView.findViewById(R.id.address_icon_img);
        email_layout=rootView.findViewById(R.id.email_layout);
        mobile_layout=rootView.findViewById(R.id.mobile_layout);
        fund_layout=rootView.findViewById(R.id.fund_layout);
        identity_layout=rootView.findViewById(R.id.identity_layout);
        video_layout=rootView.findViewById(R.id.video_layout);
        address_layout=rootView.findViewById(R.id.address_layout);
        circularProgressBar2 =rootView.findViewById(R.id.custom_progress);
        email_layout.setOnClickListener(this);
        mobile_layout.setOnClickListener(this);
        fund_layout.setOnClickListener(this);
        identity_layout.setOnClickListener(this);
        video_layout.setOnClickListener(this);
        address_layout.setOnClickListener(this);
        contactus_view.setOnClickListener(this);
        getFundSources();
        getGovermentList();
        getVerificationStatus();
        getKycRules();
        basicinforlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowbasicValiationView(getActivity(),"basic");
            }
        });

        fullyforlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowbasicValiationView(getActivity(),"fully");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Account Verification");
        MainActivity.enableBackViews(true);
        setProgressBar();
        circularProgressBar2.setProgress(progrescount);
        myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.button_click);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
    }

    public void setProgressBar()
    {
        int emailfirst=0,fundfirst=0,addressfirst=0,ideneitiy=0,videofirst=0;
        progrescount=0;
        emailstatusvalue=App.pref.getString(Constant.EMAIL_VERIFIED_STATUS,"");
        mobilestatevalue=App.pref.getString(Constant.MOBILE_VERIFIED_STATUS,"");
        fundsttusvalue=App.pref.getString(Constant.FUND_VERIFIED_STATUS,"");
        addressstavalue=App.pref.getString(Constant.ADDRESS_VERIFIED_STATUS,"");
        identitystayuvalue=App.pref.getString(Constant.IDENTITY_VERIFIED_STATUS,"");
        videostatusvalue=App.pref.getString(Constant.VIDEO_VERIFIED_STATUS,"");
        if(emailstatusvalue.equalsIgnoreCase("yes") || emailstatusvalue.equalsIgnoreCase("verified"))
        {
            emailfirst=1;
            email_icon_img.setImageResource(R.drawable.complete_icon);
            email_text_status.setText("Verified");
            progrescount=progrescount+10;
        }else if(emailstatusvalue.equalsIgnoreCase("pending") || emailstatusvalue.equalsIgnoreCase("no") || emailstatusvalue.equalsIgnoreCase("not"))
        {
            email_icon_img.setImageResource(R.drawable.pending_icon);
            email_text_status.setText("Pending");
        }else if(emailstatusvalue.equalsIgnoreCase("reject"))
        {
            email_icon_img.setImageResource(R.drawable.reject);
            email_text_status.setText(emailstatusvalue);
        }else if(emailstatusvalue.equalsIgnoreCase("in_review"))
        {
            email_icon_img.setImageResource(R.drawable.review);
            email_text_status.setText("In Review");
        }

        if(mobilestatevalue.equalsIgnoreCase("yes")  || mobilestatevalue.equalsIgnoreCase("verified"))
        {
            mobile_text_status.setText("Verified");
            mobile_icon_img.setImageResource(R.drawable.complete_icon);
            progrescount=progrescount+10;
        }else if(mobilestatevalue.equalsIgnoreCase("pending") || mobilestatevalue.equalsIgnoreCase("not") || mobilestatevalue.equalsIgnoreCase("no"))
        {
            mobile_icon_img.setImageResource(R.drawable.pending_icon);
            mobile_text_status.setText("Pending");
        }else if(mobilestatevalue.equalsIgnoreCase("rejected"))
        {
          mobile_icon_img.setImageResource(R.drawable.reject);
          mobile_text_status.setText(emailstatusvalue);
        }else if(mobilestatevalue.equalsIgnoreCase("in_review"))
        {
            mobile_icon_img.setImageResource(R.drawable.review);
            mobile_text_status.setText("In Review");
        }

        if(fundsttusvalue.equalsIgnoreCase("yes") || fundsttusvalue.equalsIgnoreCase("verified"))
        {
            fundfirst=1;
            fund_text_status.setText("Verified");
            fund_icon_img.setImageResource(R.drawable.complete_icon);
            progrescount=progrescount+10;
        }else if(fundsttusvalue.equalsIgnoreCase("pending") || fundsttusvalue.equalsIgnoreCase("not") || fundsttusvalue.equalsIgnoreCase("no"))
        {
            fund_icon_img.setImageResource(R.drawable.pending_icon);
            fund_text_status.setText("Pending");
        }else if(fundsttusvalue.equalsIgnoreCase("rejected"))
        {
            fund_icon_img.setImageResource(R.drawable.reject);
            fund_text_status.setText(fundsttusvalue);
        }else if(fundsttusvalue.equalsIgnoreCase("in_review"))
        {
            fund_icon_img.setImageResource(R.drawable.review);
            fund_text_status.setText("In Review");
        }

        if(addressstavalue.equalsIgnoreCase("yes") || addressstavalue.equalsIgnoreCase("verified"))
        {
            addressfirst=1;
            address_text_status.setText("Verified");
            address_icon_img.setImageResource(R.drawable.complete_icon);
            progrescount=progrescount+30;
        }else if(addressstavalue.equalsIgnoreCase("pending") || addressstavalue.equalsIgnoreCase("not") || addressstavalue.equalsIgnoreCase("no"))
        {
            address_icon_img.setImageResource(R.drawable.pending_icon);
            address_text_status.setText("Pending");
        }else if(addressstavalue.equalsIgnoreCase("rejected"))
        {
            address_icon_img.setImageResource(R.drawable.reject);
            address_text_status.setText(addressstavalue);
        }else if(addressstavalue.equalsIgnoreCase("in_review"))
        {
            address_icon_img.setImageResource(R.drawable.review);
            address_text_status.setText("In Review");
        }

        if(identitystayuvalue.equalsIgnoreCase("yes") || identitystayuvalue.equalsIgnoreCase("verified"))
        {
            ideneitiy=1;
            identity_text_status.setText("Verified");
            identity_icon_img.setImageResource(R.drawable.complete_icon);
            progrescount=progrescount+30;
        }else if(identitystayuvalue.equalsIgnoreCase("pending") ||identitystayuvalue.equalsIgnoreCase("not") || identitystayuvalue.equalsIgnoreCase("no"))
        {
            identity_icon_img.setImageResource(R.drawable.pending_icon);
            identity_text_status.setText("Pending");
        }else if(identitystayuvalue.equalsIgnoreCase("rejected"))
        {
            identity_icon_img.setImageResource(R.drawable.reject);
            identity_text_status.setText(identitystayuvalue);
        }else if(identitystayuvalue.equalsIgnoreCase("in_review"))
        {
            identity_icon_img.setImageResource(R.drawable.review);
            identity_text_status.setText("In Review");
        }

        if(videostatusvalue.equalsIgnoreCase("yes") || videostatusvalue.equalsIgnoreCase("verified"))
        {
            videofirst=1;
            video_text_status.setText("Verified");
            video_icon_img.setImageResource(R.drawable.complete_icon);
            progrescount=progrescount+10;
        }else if(videostatusvalue.equalsIgnoreCase("pending") ||videostatusvalue.equalsIgnoreCase("not") || videostatusvalue.equalsIgnoreCase("no"))
        {
            video_icon_img.setImageResource(R.drawable.pending_icon);
            video_text_status.setText("Pending");
        }else if(videostatusvalue.equalsIgnoreCase("rejected") || videostatusvalue.equalsIgnoreCase("rejected"))
        {
            video_icon_img.setImageResource(R.drawable.reject);
            video_text_status.setText(videostatusvalue);
        }else if(videostatusvalue.equalsIgnoreCase("in_review"))
        {
            video_icon_img.setImageResource(R.drawable.review);
            video_text_status.setText("In Review");
        }
        hintprogree.setText("Your account is "+progrescount+"% Verified");
        circularProgressBar2.setProgress(progrescount);

        if(emailfirst==1 && ideneitiy==1 && fundfirst==1)
        {
            basiclevelstaus.setVisibility(View.VISIBLE);
        }else
        {
            basiclevelstaus.setVisibility(View.GONE);
        }

        if(emailfirst==1 && ideneitiy==1 && fundfirst==1 && addressfirst==1 && videofirst==1)
        {
            fullyveririefstatus.setVisibility(View.VISIBLE);
        }else
        {
            fullyveririefstatus.setVisibility(View.GONE);
        }
    }

    private void getFundSources() {
        LoadInterface apiservice = AppConfig.getClient().create(LoadInterface.class);
        //we havd to  correct
        Call<verifcationFundSource> call = apiservice.getFundSource();
        //showProgressDiaog();
        call.enqueue(new Callback<verifcationFundSource>() {
            @Override
            public void onResponse(Call<verifcationFundSource> call, Response<verifcationFundSource> response) {
                //  hideProgress();
                try {
                    int code = response.code();
                    String retrofitMesage="";
                    retrofitMesage=response.body().getMsg();
                    if (code==200)
                    {
                        retrofitMesage=response.body().getMsg();
                        int status = response.body().getStatus();
                        if (status==1)
                        {
                            fundslist=new ArrayList<>();
                            fundslist.addAll(response.body().getData());
                        }
                        else
                        if (status==400)
                        {
                            Toast.makeText(getActivity(), "Bad request", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), retrofitMesage, Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }else
                    {
                     ShowApiError(getContext(),"server error in get-source-fund");
                    }

                }
                catch (Exception s)
                {
                    s.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<verifcationFundSource> call, Throwable t) {
                Toast.makeText(getActivity(), "Response getting failed", Toast.LENGTH_SHORT).show();
                // hideProgress();
            }
        });
    }

    private void getGovermentList() {
        LoadInterface apiservice = AppConfig.getClient().create(LoadInterface.class);
        //we havd to  correct
        Call<verifcationFundSource> call = apiservice.getVerificationsoucer();
        //showProgressDiaog();
        call.enqueue(new Callback<verifcationFundSource>() {
            @Override
            public void onResponse(Call<verifcationFundSource> call, Response<verifcationFundSource> response) {
                //  hideProgress();
                try {
                    int code = response.code();
                    String retrofitMesage="";
                    retrofitMesage=response.body().getMsg();
                    if (code==200)
                    {
                        retrofitMesage=response.body().getMsg();
                        int status = response.body().getStatus();
                        if (status==1)
                        {
                            govermentidlist=new ArrayList<>();
                            govermentidlist.addAll(response.body().getData());
                        }
                        else
                        if (status==400)
                        {
                            Toast.makeText(getActivity(), "Bad request", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), retrofitMesage, Toast.LENGTH_SHORT).show();
                        }
                        return;
                    } else
                    {
                        ShowApiError(getContext(),"server error in get-government-id");
                    }

                }
                catch (Exception s)
                {
                    s.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<verifcationFundSource> call, Throwable t) {
                Toast.makeText(getActivity(), "Response getting failed", Toast.LENGTH_SHORT).show();
                // hideProgress();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.contactus_view:
                MainActivity.addFragment(new ContactUsFragemnt(), true);
                break;
            case R.id.email_layout:
                email_layout.startAnimation(myAnim);
                emailstatusvalue=App.pref.getString(Constant.EMAIL_VERIFIED_STATUS,"");
                if(emailstatusvalue.equalsIgnoreCase("yes") || emailstatusvalue.equalsIgnoreCase("verified") || emailstatusvalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Email verification is done", Toast.LENGTH_SHORT).show();
                }else if(emailstatusvalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Email verification is in review", Toast.LENGTH_SHORT).show();
                }else
                {
                    viewdelay(1);
                }

                break;

            case R.id.mobile_layout:
                mobile_layout.startAnimation(myAnim);
                mobilestatevalue=App.pref.getString(Constant.MOBILE_VERIFIED_STATUS,"");
                if(mobilestatevalue.equalsIgnoreCase("in_review") || mobilestatevalue.equalsIgnoreCase("yes")  || mobilestatevalue.equalsIgnoreCase("verified"))
                {
                    Toast.makeText(mContext, "Mobile verification is done", Toast.LENGTH_SHORT).show();
                }else if(mobilestatevalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Mobile verification is in review", Toast.LENGTH_SHORT).show();
                }else
                {
                    viewdelay(2);
                }
                break;

            case R.id.fund_layout:
                fund_layout.startAnimation(myAnim);
                fundsttusvalue=App.pref.getString(Constant.FUND_VERIFIED_STATUS,"");
                if(fundsttusvalue.equalsIgnoreCase("yes") || fundsttusvalue.equalsIgnoreCase("verified"))
                {
                    Toast.makeText(mContext, "Fund Source verification is done", Toast.LENGTH_SHORT).show();
                }else if(fundsttusvalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Fund Source verification is in review", Toast.LENGTH_SHORT).show();
                }else
                {
                    viewdelay(3);
                }
                getFundSources();
                break;

            case R.id.identity_layout:
                identity_layout.startAnimation(myAnim);
                identitystayuvalue=App.pref.getString(Constant.IDENTITY_VERIFIED_STATUS,"");
                if(identitystayuvalue.equalsIgnoreCase("yes") || identitystayuvalue.equalsIgnoreCase("verified"))
                {
                    Toast.makeText(mContext, "Identity Source verification is done", Toast.LENGTH_SHORT).show();
                }else if(identitystayuvalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Identity Source verification is in review", Toast.LENGTH_SHORT).show();
                }else
                {
                    viewdelay(4);
                }
                getGovermentList();
                break;

            case R.id.video_layout:
                video_layout.startAnimation(myAnim);
                videostatusvalue=App.pref.getString(Constant.VIDEO_VERIFIED_STATUS,"");
                if(videostatusvalue.equalsIgnoreCase("yes") || videostatusvalue.equalsIgnoreCase("verified"))
                {
                    Toast.makeText(mContext, "Video verification is done", Toast.LENGTH_SHORT).show();
                }else if(videostatusvalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Video verification is in review", Toast.LENGTH_SHORT).show();
                }else
                {
                    i=0;
                    viewdelay(5);
                }

                break;

            case R.id.address_layout:
                address_layout.startAnimation(myAnim);
                addressstavalue=App.pref.getString(Constant.ADDRESS_VERIFIED_STATUS,"");
                if(addressstavalue.equalsIgnoreCase("yes") || addressstavalue.equalsIgnoreCase("verified"))
                {
                    Toast.makeText(mContext, "Address verification is done", Toast.LENGTH_SHORT).show();
                }else if(addressstavalue.equalsIgnoreCase("in_review"))
                {
                    Toast.makeText(mContext, "Address verification is in review", Toast.LENGTH_SHORT).show();
                }else
                {
                    viewdelay(6);
                }
                break;

        }
    }

    public void viewdelay(final int buttonclick)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(buttonclick==1)
                {
                    sendEmailVerification();
                }else if(buttonclick==2)
                {
                    sendMobileVerfication(getActivity());

                }else{
                    if(buttonclick==4)
                    {
                        String emailvalue=App.pref.getString(Constant.USER_EMAIL, "");
                        initEmailVerificationSingleimg(emailvalue);
                    }else {
                        initEmailVerification(buttonclick);
                    }
                }
            }
        }, 300);
    }

    //for new
    private void  initEmailVerificationSingleimg(final String emailid)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View dialogViewp;
        dialogViewp = layoutInflater.inflate(R.layout.loginchangepasswordlayout, null);
        final BottomSheetDialog dialogp = new BottomSheetDialog(getActivity(), R.style.DialogStyle);
        dialogp.setContentView(dialogViewp);
        img_first_front_pic=dialogp.findViewById(R.id.img_first_front_pic);
        TextView btnnext=dialogp.findViewById(R.id.btnnext);
        TextView infoicon=dialogp.findViewById(R.id.infoicon);
        infoicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPutMethods.ShowInfoAlert(getActivity());
            }
        });
        changespasswordimgpath ="";
        changespasswordbitmap=null;
        img_first_front_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for pic img
                selecetdimgtype=3;
                selectImg();
                /*final PickImageDialog dialognew = PickImageDialog.build(new PickSetup());
                dialognew.setOnPickCancel(new IPickCancel()
                {
                    @Override
                    public void onCancelClick() {
                        dialognew.dismiss();
                    }
                }).setOnPickResult(new IPickResult()
                {
                    @Override
                    public void onPickResult(PickResult r)
                    {
                        //TODO: do what you have to...
                        changespasswordimgpath = r.getPath();
                        changespasswordbitmap=r.getBitmap();
                        img_first_front_pic.setImageBitmap(r.getBitmap());
                    }
                }).show(getActivity().getSupportFragmentManager());*/

            }
        });
        btnnext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(changespasswordimgpath.equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(), "Please upload ID proof with image", Toast.LENGTH_SHORT).show();
                }else
                {
                    updateIdProof(dialogp,emailid);
                }
            }
        });
        dialogp.show();
    }
    //...
    private void updateIdProof(final BottomSheetDialog dialogp,final String email) {

        if (AppConfig.isInternetOn())
        {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialog, "Please wait..........");
            File file = null;
            try {
                if (changespasswordimgpath != null) {
                    file = new File(changespasswordimgpath);
                }

                try {
                    File userimg=new File(changespasswordimgpath);
                    FileOutputStream out = new FileOutputStream(userimg);
                    changespasswordbitmap.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.close();
                    file = new File(changespasswordimgpath);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("user_photo_id", file.getName(), requestFile);

                Call<ResponseBody> call = AppConfig.getLoadInterface().loginUserIdUpdate(
                        AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),
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
                                    getVerificationStatus();
                                    PasswordChangesSuccessfully();
                                    dialogp.dismiss();

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
                            dialog.dismiss();
                            ShowApiError(getActivity(),"server error in edit-user-profile");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AppConfig.hideLoading(dialog);
                        errordurigApiCalling(getActivity(),t.getMessage());
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
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        final View dialogView;
        dialogView = layoutInflater.inflate(R.layout.passwordchangesuccesslayout, null);
        final BottomSheetDialog dialogs = new BottomSheetDialog(getActivity(), R.style.DialogStyle);
        dialogs.setContentView(dialogView);
        Toolbar container=dialogs.findViewById(R.id.tootlbarheadr);
        container.setVisibility(View.INVISIBLE);
        LinearLayout back_view=dialogs.findViewById(R.id.back_view);
        TextView btnnext=dialogs.findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialogs.dismiss();
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
    //...........

    public void sendMobileVerfication(Context mContext) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View dialogView = layoutInflater.inflate(R.layout.popup_mobile_verfication, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        dialog.show();
        btnsendOtp = dialog.findViewById(R.id.btnsendOtp);
        tvMobileNumber = dialog.findViewById(R.id.tvMobileNumber);

        tvMobileNumber.setText("+"+userData.getUser().getPhoneCode()+" "+App.pref.getString(Constant.USER_MOBILE, ""));

        btnsendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMobileVerification();
                dialog.dismiss();
            }
        });

    }

    private void initEmailVerification(final int dialogclick)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View dialogView;
        if(dialogclick==2 || dialogclick==1)
        {
            dialogView = layoutInflater.inflate(R.layout.otp_verify_dialog, null);
            dialog = new BottomSheetDialog(getActivity(), R.style.DialogStyle);
            dialog.setContentView(dialogView);
            ImageView logo1=dialogView.findViewById(R.id.logo1);
            final LinearLayout otherview=dialogView.findViewById(R.id.otherview);
            ImageView logoicon=dialogView.findViewById(R.id.logoicon);
            TextView headinglog=dialogView.findViewById(R.id.headinglog);
            TextView mobile_txt=dialogView.findViewById(R.id.mobile_txt);
            otherview.setVisibility(View.VISIBLE);
            logo1.setVisibility(View.GONE);
            if(dialogclick==1)
            {
                logoicon.setImageResource(R.drawable.emailverficaytion);
                headinglog.setText("Email Verification");
            }else
            {
                headinglog.setText("Mobile Verification");
                logoicon.setImageResource(R.drawable.mbile_iconver);
                mobile_txt.setText("+"+userData.getUser().getPhoneCode()+"-"+App.pref.getString(Constant.USER_MOBILE, ""));
            }
            final LinearLayout back_view=dialogView.findViewById(R.id.back_view);
            final TextView btnVerify =dialogView.findViewById(R.id.btnVerify);
            final EditText otp_edt1 =dialogView.findViewById(R.id.otp_edt11);
            final EditText otp_edt2 =dialogView.findViewById(R.id.otp_edt12);
            final EditText otp_edt3 =dialogView.findViewById(R.id.otp_edt13);
            final EditText otp_edt4 =dialogView.findViewById(R.id.otp_edt14);

            btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    str_top = new StringBuffer();
                    str_top.append(otp_edt1.getText().toString()).append(otp_edt2.getText().toString()).append(otp_edt3.getText().toString()).append(otp_edt4.getText().toString());
                    if (str_top.length() > 0 && str_top.length() == 4)
                    {
                        String otpnew=str_top.toString();
                        //for value
                        if(dialogclick==1)
                        {
                            submitEmailVerificationData(otpnew);
                        }else
                        {
                            submitMobileVerificationData(otpnew);
                        }
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
                    dialog.dismiss();
                }
            });

        }else {
            dialogView = layoutInflater.inflate(R.layout.emailverifcaitionlayout, null);
            dialog = new BottomSheetDialog(getActivity(), R.style.DialogStyle);
            dialog.setContentView(dialogView);
            //for other
            niteview=dialog.findViewById(R.id.niteview);
            Nextvideo=dialog.findViewById(R.id.Nextvideo);
            submitvideo=dialog.findViewById(R.id.submitvideo);
            secondview=dialog.findViewById(R.id.secondview);
            firstview=dialog.findViewById(R.id.firstview);
            submitimgbilling=dialog.findViewById(R.id.submitimgbilling);
            billingview=dialog.findViewById(R.id.billingview);
            submitview=dialog.findViewById(R.id.submitview);
            final TextView govermentid=dialog.findViewById(R.id.govermentid);
            final TextView govermentid2=dialog.findViewById(R.id.govermentid2);
            LinearLayout fund_layout=dialog.findViewById(R.id.fund_layout);
            LinearLayout video_layout=dialog.findViewById(R.id.video_layout);
            LinearLayout identity_layout=dialog.findViewById(R.id.identity_layout);
            LinearLayout addressbill_layout=dialog.findViewById(R.id.addressbill_layout);
            TextView btnGoHome=dialog.findViewById(R.id.btnGoHome);
            TextView upload=dialog.findViewById(R.id.upload);
            final ImageView img_first_front_pic=dialog.findViewById(R.id.img_first_front_pic);
            img_first_back_pic=dialog.findViewById(R.id.img_first_back_pic);
            img_second_front_pic=dialog.findViewById(R.id.img_second_front_pic);
            img_second_back_pic=dialog.findViewById(R.id.img_second_back_pic);
            final TextView etFundValue=dialog.findViewById(R.id.etFundValue);

            etFundValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    dataPutMethods.showDialogVerificationView(view,getContext(),fundslist,etFundValue);
                }
            });
            if(dialogclick==3)
            {
                fund_layout.setVisibility(View.VISIBLE);
                video_layout.setVisibility(View.GONE);
                identity_layout.setVisibility(View.GONE);
                addressbill_layout.setVisibility(View.GONE);
            }else if(dialogclick==4)
            {
                video_layout.setVisibility(View.GONE);
                fund_layout.setVisibility(View.GONE);
                identity_layout.setVisibility(View.VISIBLE);
                addressbill_layout.setVisibility(View.GONE);
            }else if(dialogclick==5)
            {
                List<String> permissionList =checkAndRequestPermissions(getActivity());
                if (permissions(permissionList))
                {

                }
                video_layout.setVisibility(View.VISIBLE);
                fund_layout.setVisibility(View.GONE);
                identity_layout.setVisibility(View.GONE);
                addressbill_layout.setVisibility(View.GONE);
            }else if(dialogclick==6)
            {
                video_layout.setVisibility(View.GONE);
                fund_layout.setVisibility(View.GONE);
                identity_layout.setVisibility(View.GONE);
                addressbill_layout.setVisibility(View.VISIBLE);
            }

            btnGoHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitFundSource(etFundValue.getText().toString());
                }
            });


            govermentid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataPutMethods.showDialogVerificationView(view,getContext(),govermentidlist,govermentid);
                }
            });

            govermentid2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataPutMethods.showDialogVerificationView(view,getContext(),govermentidlist,govermentid2);
                }
            });

            img_first_front_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //for pic img
                    selecetdimgtype=4;
                    selectImg();
                    /*
                    final PickImageDialog dialognew = PickImageDialog.build(new PickSetup());
                    dialognew.setOnPickCancel(new IPickCancel()
                    {
                        @Override
                        public void onCancelClick() {
                            dialognew.dismiss();
                        }
                    }).setOnPickResult(new IPickResult()
                    {
                        @Override
                        public void onPickResult(PickResult r)
                        {
                            //TODO: do what you have to...
                            imagePathFirstFront = r.getPath();
                            frontimgfirstbitmap=r.getBitmap();
                            img_first_front_pic.setImageBitmap(r.getBitmap());
                            //changeImageCall(DataHolder.getUser().getId(),imagePath);
                        }
                    }).show(getActivity().getSupportFragmentManager());
                    */
                    //........
                }
            });

            img_first_back_pic.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    selecetdimgtype=5;
                    selectImg();
                    //for pic img
                  /*  final PickImageDialog dialognew = PickImageDialog.build(new PickSetup());
                    dialognew.setOnPickCancel(new IPickCancel()
                    {
                        @Override
                        public void onCancelClick() {
                            dialognew.dismiss();
                        }
                    }).setOnPickResult(new IPickResult()
                    {
                        @Override
                        public void onPickResult(PickResult r)
                        {
                            //TODO: do what you have to...
                            imagePathFirstBack = r.getPath();
                            backimgfirstbitmap=r.getBitmap();
                            img_first_back_pic.setImageBitmap(r.getBitmap());
                            //changeImageCall(DataHolder.getUser().getId(),imagePath);
                        }
                    }).show(getActivity().getSupportFragmentManager());
                  */  //........
                }
            });

            img_second_front_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //for pic img
                    selecetdimgtype=6;
                    selectImg();
                   /* final PickImageDialog dialognew = PickImageDialog.build(new PickSetup());
                    dialognew.setOnPickCancel(new IPickCancel()
                    {
                        @Override
                        public void onCancelClick() {
                            dialognew.dismiss();
                        }
                    }).setOnPickResult(new IPickResult()
                    {
                        @Override
                        public void onPickResult(PickResult r)
                        {
                            //TODO: do what you have to...
                            imagePathSecondFront = r.getPath();
                            frontimgsecondbitmap=r.getBitmap();
                            img_second_front_pic.setImageBitmap(r.getBitmap());
                            //changeImageCall(DataHolder.getUser().getId(),imagePath);
                        }
                    }).show(getActivity().getSupportFragmentManager());*/
                    //........
                }
            });

            img_second_back_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    selecetdimgtype=7;
                    selectImg();

                    //for pic img
                   /* final PickImageDialog dialognew = PickImageDialog.build(new PickSetup());
                    dialognew.setOnPickCancel(new IPickCancel()
                    {
                        @Override
                        public void onCancelClick() {
                            dialognew.dismiss();
                        }
                    }).setOnPickResult(new IPickResult()
                    {
                        @Override
                        public void onPickResult(PickResult r)
                        {
                            //TODO: do what you have to...
                            imagePathSecondBak = r.getPath();
                            backimgsecondbitmap=r.getBitmap();
                            img_second_back_pic.setImageBitmap(r.getBitmap());
                            //changeImageCall(DataHolder.getUser().getId(),imagePath);
                        }
                    }).show(getActivity().getSupportFragmentManager());*/
                    //........
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String govermentidvalue=govermentid.getText().toString();
                    String govermentid22=govermentid2.getText().toString();
                    submitIdentityVerification(govermentidvalue,govermentid22);
                }
            });

            submitview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    selecetdimgtype=8;
                    selectImg();
                    //for pic img
                    /*final PickImageDialog dialognew = PickImageDialog.build(new PickSetup());
                    dialognew.setOnPickCancel(new IPickCancel()
                    {
                        @Override
                        public void onCancelClick() {
                            dialognew.dismiss();
                        }
                    }).setOnPickResult(new IPickResult()
                    {
                        @Override
                        public void onPickResult(PickResult r)
                        {
                            //TODO: do what you have to...
                            billingImagePath = r.getPath();
                            billingBitmap=r.getBitmap();
                            billingview.setImageBitmap(r.getBitmap());
                            submitview.setVisibility(View.GONE);
                            submitimgbilling.setVisibility(View.VISIBLE);
                            //changeImageCall(DataHolder.getUser().getId(),imagePath);
                        }
                    }).show(getActivity().getSupportFragmentManager());
*/
                }
            });

            submitimgbilling.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitBillingView();
                }
            });


            Nextvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // In my case I've put the 'checkAndRequestPermissions' method in a separate class named 'PermissionUtils'
                        Intent captureVideoIntent = new Intent();
                        captureVideoIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
                        captureVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getVideoFileURI());
                        if (captureVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(captureVideoIntent, 2);
                        }
                        //startActivityForResult(captureVideoIntent,2);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        dialog.show();
    }

    public static List<String> checkAndRequestPermissions(Context context) {

        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int readStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStorage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int fineLoc = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (readStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (fineLoc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }

        return listPermissionsNeeded;
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    private boolean permissions(List<String> listPermissionsNeeded) {

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public File getFile()
    {
        File filename=new File("sdcard/myfolder");
        if(!filename.exists())
        {
            filename.mkdirs();
        }
        File video_file=new File(filename,"myvideo.mp4");
        return video_file;
    }

    public void selectVideoFromGallery()
    {
        Intent intent;
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        }
        else
        {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.INTERNAL_CONTENT_URI);
        }
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,SELECT_VIDEO_REQUEST);
    }

    private void sendEmailVerification() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", App.pref.getString(Constant.USER_EMAIL, ""));
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().emailVerification(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseData = response.body().string();
                        Log.d("get verification status", responseData);
                        JSONObject object = new JSONObject(responseData);
                        if (object.getInt(Constant.STATUS) == 1) {
                            initEmailVerification(1);
                        } else {
                            AppConfig.showToast(object.getString("msg"));
                        }
                    } else {
                      ShowApiError(getContext(),"server error in kyc-email-verification");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    //for new api
    private void submitFundSource(String fundsource) {
        HashMap<String, String> hm = new HashMap<>();
        // hm.put("fund_source", App.pref.getString(Constant.USER_EMAIL, ""));
        hm.put("fund_source",fundsource);
        final ProgressDialog dialogvi = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialogvi, "Please wait verifying....");

        AppConfig.getLoadInterface().kycFundSource(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialogvi.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseData = response.body().string();
                        Log.d("get verification status", responseData);
                        JSONObject object = new JSONObject(responseData);
                        if (object.getInt(Constant.STATUS) == 1)
                        {
                            getVerificationStatus();
                            ShowSameWalletDialog(getActivity(),object.getString("msg"),"2");
                            dialog.dismiss();

                        } else {
                            ShowSameWalletDialog(getActivity(),object.getString("msg"),"1");
                            dialog.dismiss();

                        }
                    } else {
                        ShowApiError(getContext(),"server error in kyc-fund-source-verification");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogvi.dismiss();
            }
        });
    }

    private void submitEmailVerificationData(String enterotp) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("email", App.pref.getString(Constant.USER_EMAIL, ""));
        hm.put("otp",enterotp);
        final ProgressDialog dialogvi = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialogvi, "Please wait verifying....");

        AppConfig.getLoadInterface().emailVerifiedData(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialogvi.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseData = response.body().string();
                        Log.d("get verification status", responseData);
                        JSONObject object = new JSONObject(responseData);
                        if (object.getInt(Constant.STATUS) == 1)
                        {
                            getVerificationStatus();
                            ShowSameWalletDialog(getActivity(),object.getString("msg"),"2");
                            dialog.dismiss();
                        } else {
                            ShowSameWalletDialog(getActivity(),object.getString("msg"),"1");
                            dialog.dismiss();
                        }
                    } else {
                        ShowApiError(getContext(),"server error in kyc-otp-verification");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogvi.dismiss();
            }
        });
    }

    private void sendMobileVerification() {
        HashMap<String, String> hm = new HashMap<>();
        String countrycode=userData.getUser().getPhoneCode();
        final String mobilenumber="+"+countrycode+""+App.pref.getString(Constant.USER_MOBILE, "");
        //Toast.makeText(mContext, "mobile "+mobilenumber, Toast.LENGTH_SHORT).show();
        hm.put("mobile",mobilenumber);
        hm.put("phone_code",countrycode);
        final ProgressDialog dialog = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialog, "Sending otp....");

        AppConfig.getLoadInterface().mobileVerification(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseData = response.body().string();
                        //Toast.makeText(mContext, "response "+responseData+" mobilbe"+mobilenumber, Toast.LENGTH_SHORT).show();
                        Log.d("get verification status", responseData);
                        JSONObject object = new JSONObject(responseData);
                        if (object.getInt(Constant.STATUS) == 1)
                        {
                            getVerificationStatus();
                            initEmailVerification(2);
                        } else {
                            AppConfig.showToast(object.getString("msg"));
                        }
                    } else {
                        ShowApiError(getContext(),"server error in kyc-mobile-verification");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void submitMobileVerificationData(String enterotp) {
        HashMap<String, String> hm = new HashMap<>();
        String countrycode=userData.getUser().getPhoneCode();
        final String mobilenumber="+"+countrycode+""+App.pref.getString(Constant.USER_MOBILE, "");
        hm.put("mobile", mobilenumber);
        hm.put("otp",enterotp);
        final ProgressDialog dialogvi = new ProgressDialog(MainActivity.activity);
        AppConfig.showLoading(dialogvi, "Please wait verifying....");

        AppConfig.getLoadInterface().mobileVerifiedData(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken), hm).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialogvi.dismiss();
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseData = response.body().string();
                        Log.d("get verification status", responseData);
                        JSONObject object = new JSONObject(responseData);
                        if (object.getInt(Constant.STATUS) == 1)
                        {
                            getVerificationStatus();
                            ShowSameWalletDialog(getActivity(),object.getString("msg"),"2");
                            dialog.dismiss();
                        } else {
                            ShowSameWalletDialog(getActivity(),object.getString("msg"),"1");
                            dialog.dismiss();
                        }
                    } else {
                       ShowApiError(getContext(),"server error in kyc-mobile-otp-verification");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialogvi.dismiss();
            }
        });
    }

    //for identity verification
    //for new api
    private void submitIdentityVerification(String govermentidvalue,String govermentid2) {

        if (AppConfig.isInternetOn())
        {
            final ProgressDialog dialognew = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialognew, "Please wait verifying....");
            File filefirstfinal = null,filefirstbackfinal=null,filesecondfrontfinal=null,filesecondbackfinal=null;
            try {
                if (imagePathFirstFront != null) {
                    filefirstfinal = new File(imagePathFirstFront);
                }
                try {
                    //for first
                    File userimg=new File(imagePathFirstFront);
                    FileOutputStream out = new FileOutputStream(userimg);
                    frontimgfirstbitmap.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.close();
                    filefirstfinal = new File(imagePathFirstFront);
                    //for firstback
                    File filefirstbackfinauser=new File(imagePathFirstBack);
                    FileOutputStream out1 = new FileOutputStream(filefirstbackfinauser);
                    backimgfirstbitmap.compress(Bitmap.CompressFormat.PNG, 100, out1); //100-best quality
                    out.close();
                    filefirstbackfinal= new File(imagePathFirstBack);
                    //for secondfront
                    File filefirstbackfinauser1=new File(imagePathSecondFront);
                    FileOutputStream out11 = new FileOutputStream(filefirstbackfinauser1);
                    frontimgsecondbitmap.compress(Bitmap.CompressFormat.PNG, 100, out11); //100-best quality
                    out.close();
                    filesecondfrontfinal= new File(imagePathSecondFront);
                    //for seconback
                    File filefirstbackfinauser12=new File(imagePathSecondBak);
                    FileOutputStream out12 = new FileOutputStream(filefirstbackfinauser12);
                    backimgsecondbitmap.compress(Bitmap.CompressFormat.PNG, 100, out12); //100-best quality
                    out.close();
                    filesecondbackfinal= new File(imagePathSecondBak);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), filefirstfinal);
                MultipartBody.Part body1 = MultipartBody.Part.createFormData("id_proof_1_front", filefirstfinal.getName(), requestFile1);

                RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), filefirstfinal);
                MultipartBody.Part body2 = MultipartBody.Part.createFormData("id_proof_1_back", filefirstbackfinal.getName(), requestFile2);

                RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), filefirstfinal);
                MultipartBody.Part body3 = MultipartBody.Part.createFormData("id_proof_2_front", filesecondfrontfinal.getName(), requestFile3);

                RequestBody requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), filefirstfinal);
                MultipartBody.Part body4 = MultipartBody.Part.createFormData("id_proof_2_back", filesecondbackfinal.getName(), requestFile4);

                Call<ResponseBody> call = AppConfig.getLoadInterface().submitIdentityVerification(
                        AppConfig.getStringPreferences(mContext, Constant.JWTToken),
                        AppConfig.setRequestBody(govermentidvalue),
                        AppConfig.setRequestBody(govermentid2),
                        body1,
                        body2,
                        body3,
                        body4
                );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialognew.dismiss();
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                String responseData = response.body().string();
                                Log.d("get verification status", responseData);
                                JSONObject object = new JSONObject(responseData);
                                if (object.getInt(Constant.STATUS) == 1)
                                {
                                    getVerificationStatus();
                                    ShowSameWalletDialog(getActivity(),object.getString("msg"),"2");
                                    dialog.dismiss();

                                } else {
                                    ShowSameWalletDialog(getActivity(),object.getString("msg"),"1");
                                    dialog.dismiss();
                                }
                            } else {
                            ShowApiError(getContext(),"server error in kyc-identity-submission");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        t.printStackTrace();
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

    private void getKycRules()
    {
        try {
            HashMap<String, String> hm = new HashMap<>();
            String countrycode=userData.getUser().getCountryId();
            hm.put("country_id", countrycode);
            final ProgressDialog dialogvi = new ProgressDialog(MainActivity.activity);
            AppConfig.showLoading(dialogvi, "Please wait ....");

            AppConfig.getLoadInterface().getKYCRules(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken),hm).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        dialogvi.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            String responseData = response.body().string();
                            Log.d("get verification status", responseData);
                            JSONObject object = new JSONObject(responseData);
                            if (object.getInt(Constant.STATUS) == 1) {
                                try {
                                    JSONObject dataobj=object.getJSONObject("data");
                                    {
                                        String emailverifcation = dataobj.getString("email_verification");
                                        String moibleverifcation = dataobj.getString("mobile_verification");
                                        String id_proof_verification = dataobj.getString("identity_verification");
                                        String fund_source_verification = dataobj.getString("fund_source_verification");
                                        String address_verification = dataobj.getString("address_verification");
                                        String video_verification = dataobj.getString("video_verification");
                                        setVerificationdata(email_layout,mobile_layout,identity_layout,fund_layout,address_layout,video_layout, emailverifcation,moibleverifcation,id_proof_verification,fund_source_verification,address_verification,video_verification);
                                    }
                                    JSONArray account_limitarray=object.getJSONArray("account_limit");
                                    if(account_limitarray.length()>0)
                                    {
                                        String basichint=null,dailylimit1=null,monthlylimit1=null;
                                        String fulylverifiedhint=null,dailylimit2=null,monthlylimit2=null;
                                        String cororpratehint = null,dailylimit3=null,monthlylimit3=null;
                                        for(int i=0;i<account_limitarray.length();i++)
                                        {
                                            JSONObject accoutnobj=account_limitarray.getJSONObject(i);
                                            if(i==0)
                                            {
                                                basichint=accoutnobj.getString("name");
                                                dailylimit1=accoutnobj.getString("daily_limit");
                                                monthlylimit1=accoutnobj.getString("monthly_limit");
                                            }

                                            if(i==1)
                                            {
                                                fulylverifiedhint=accoutnobj.getString("name");
                                                dailylimit2=accoutnobj.getString("daily_limit");
                                                monthlylimit2=accoutnobj.getString("monthly_limit");
                                            }

                                            if(i==2)
                                            {
                                                cororpratehint=accoutnobj.getString("name");
                                                dailylimit3=accoutnobj.getString("daily_limit");
                                                monthlylimit3=accoutnobj.getString("monthly_limit");
                                            }
                                        }
                                        //corporatehint.setText(cororpratehint);
                                        if(dailylimit3.equalsIgnoreCase(""))
                                        {
                                            corporatedailylimit.setText(" ---- \n Fiat and Crypto");
                                        }else {
                                            corporatedailylimit.setText(dailylimit3 + " USD \n Fiat and Crypto");
                                        }
                                        if(monthlylimit3.equalsIgnoreCase(""))
                                        {
                                            corporatemonthlylimit.setText(" ---- \n Fiat and Crypto");
                                        }else {
                                            corporatemonthlylimit.setText(monthlylimit3 + " USD \n Fiat and Crypto");
                                        }

                                        basichintlimit.setText(basichint);
                                        if(dailylimit1.equalsIgnoreCase(""))
                                        {
                                            basicdailylimit.setText(" ---- \n Fiat and Crypto");
                                        }else {
                                            basicdailylimit.setText(dailylimit1 + " USD \n Fiat and Crypto");
                                        }

                                        if(monthlylimit1.equalsIgnoreCase(""))
                                        {
                                            basicmonthlylimit.setText(" ---- \n Fiat and Crypto");
                                        }else {
                                            basicmonthlylimit.setText(monthlylimit1+ " USD \n Fiat and Crypto");
                                        }

                                        fullverifiedhintlimit.setText(fulylverifiedhint);
                                        if(dailylimit2.equalsIgnoreCase(""))
                                        {
                                            fullyvarifieddailylimit.setText(" ---- \n Fiat and Crypto");
                                        }else {
                                            fullyvarifieddailylimit.setText(dailylimit2+ " USD \n Fiat and Crypto");
                                        }

                                        if(monthlylimit2.equalsIgnoreCase(""))
                                        {
                                            fullyvarifiedmonthlylimit.setText(" ---- \n Fiat and Crypto");
                                        }else {
                                            fullyvarifiedmonthlylimit.setText(monthlylimit2+ " USD \n Fiat and Crypto");
                                        }
                                    }
                                   } catch (JSONException e) {
                                    AppConfig.showToast("Server error");
                                    e.printStackTrace();
                                }
                            } else {
                                AppConfig.showToast(object.getString("msg"));
                            }
                        } else {
                            AppConfig.showToast("Server error");
                        }
                    } catch (IOException e) {
                        AppConfig.showToast("Server error");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        AppConfig.showToast("Server error");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dialogvi.dismiss();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void getVerificationStatus()
    {
        String userId =App.pref.getString(Constant.USER_ID, "");
        try {
            AppConfig.getLoadInterface().getVerificationStatus(AppConfig.getStringPreferences(getActivity(), Constant.JWTToken)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null)
                        {
                            String responseData = response.body().string();
                            Log.d("get verification status", responseData);
                            JSONObject object = new JSONObject(responseData);
                            if (object.getInt(Constant.STATUS) == 1) {
                                try {
                                    JSONObject dataobj=object.getJSONObject("data");
                                    String emailverifcation = dataobj.getString("email_verification_status");
                                    String moibleverifcation = dataobj.getString("mobile_verification_status");
                                    String id_proof_1_verification_status = dataobj.getString("id_proof_1_verification_status");
                                    String id_proof_2_verification_status = dataobj.getString("id_proof_2_verification_status");
                                    String fund_source_verification_status = dataobj.getString("fund_source_verification_status");
                                    String address_verification_status= dataobj.getString("address_verification_status");
                                    String single_video_verification_status=dataobj.getString("single_video_verification_status");
                                    dataPutMethods.putUserVerification(emailverifcation,moibleverifcation,id_proof_1_verification_status,id_proof_2_verification_status,fund_source_verification_status,address_verification_status,single_video_verification_status);
                                    setProgressBar();

                                    String emailnote=dataobj.getString("email_note");
                                    if(emailnote!=null && !emailnote.equalsIgnoreCase(""))
                                    {
                                        showNoteSection("1","Note : "+emailnote,emailveirifcationote);
                                    }else
                                    {
                                        showNoteSection("0","",emailveirifcationote);
                                    }
                                    String mobile_note=dataobj.getString("mobile_note");
                                    if(mobile_note!=null && !mobile_note.equalsIgnoreCase(""))
                                    {
                                        showNoteSection("1","Note : "+mobile_note,mobileveirifcationote);
                                    }else
                                    {
                                        showNoteSection("0","",mobileveirifcationote);
                                    }
                                    String address_note=dataobj.getString("address_note");
                                    if(address_note!=null && !address_note.equalsIgnoreCase(""))
                                    {
                                        showNoteSection("1","Note : "+address_note,addressveirifcationote);
                                    }else
                                    {
                                        showNoteSection("0","",addressveirifcationote);
                                    }
                                    String fund_note=dataobj.getString("fund_note");
                                    if(fund_note!=null && !fund_note.equalsIgnoreCase(""))
                                    {
                                        showNoteSection("1","Note : "+fund_note,fundveirifcationote);
                                    }else
                                    {
                                        showNoteSection("0","",fundveirifcationote);
                                    }
                                    String identitiy_note=dataobj.getString("identitiy_note");
                                    if(identitiy_note!=null && !identitiy_note.equalsIgnoreCase(""))
                                    {
                                        showNoteSection("1","Note : "+identitiy_note,identitiyveirifcationote);
                                    }else
                                    {
                                        showNoteSection("0","",identitiyveirifcationote);
                                    }
                                    String video_note=dataobj.getString("video_note");
                                    if(video_note!=null && !video_note.equalsIgnoreCase(""))
                                    {
                                        showNoteSection("1","Note : "+video_note,videoveirifcationote);
                                    }else
                                    {
                                        showNoteSection("0","",videoveirifcationote);
                                    }


                                } catch (JSONException e) {
                                    AppConfig.showToast("Server error");
                                    e.printStackTrace();
                                }
                            } else {
                                AppConfig.showToast(object.getString("msg"));
                            }
                        } else {
                            ShowApiError(mContext,"server error in kyc-verification-status");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                dialog.dismiss();
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void submitVideoApi(File videopath) {

        if (AppConfig.isInternetOn())
        {
            final ProgressDialog dialognew = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialognew, "Please wait verifying....");
            try {
                try {
                    //for first
                    if(videopath.length()==0)
                    {
                      //  Toast.makeText(mContext, "first vide blank", Toast.LENGTH_SHORT).show();
                    }else
                    {
                    //    Toast.makeText(mContext, "sze view "+filesize_in_megaBytes(videopath)+" MB", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), videopath);
                MultipartBody.Part body1 = MultipartBody.Part.createFormData("single_video", videopath.getName(), requestFile1);

                Call<ResponseBody> call = AppConfig.getLoadInterface().submitVideoData(
                        AppConfig.getStringPreferences(mContext, Constant.JWTToken),
                        body1
                );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialognew.dismiss();
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                String responseData = response.body().string();
                                Log.d("get verification status", responseData);
                                JSONObject object = new JSONObject(responseData);
                                if (object.getInt(Constant.STATUS) == 1)
                                {
                                    getVerificationStatus();
                                    ShowSameWalletDialog(getActivity(),object.getString("msg"),"2");
                                    dialog.dismiss();
                                } else {
                                    ShowSameWalletDialog(getActivity(),object.getString("msg"),"1");
                                    dialog.dismiss();
                                }
                            } else {
                                AppConfig.showToast("Server error");
                            }
                        } catch (IOException e) {
                            AppConfig.showToast("Server error");
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppConfig.showToast("Server json error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        t.printStackTrace();
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

    private void submitBillingView()
    {
        if (AppConfig.isInternetOn())
        {
            final ProgressDialog dialognew = new ProgressDialog(getActivity());
            AppConfig.showLoading(dialognew, "Please wait verifying....");
            File filefirstfinal = null;
            try
            {
                if (billingImagePath != null)
                {
                    filefirstfinal = new File(billingImagePath);
                }
                try
                {
                    //for first
                    File userimg=new File(billingImagePath);
                    FileOutputStream out = new FileOutputStream(userimg);
                    billingBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); //100-best quality
                    out.close();
                    filefirstfinal = new File(billingImagePath);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), filefirstfinal);
                MultipartBody.Part body1 = MultipartBody.Part.createFormData("proof_of_billing", filefirstfinal.getName(), requestFile1);

                Call<ResponseBody> call = AppConfig.getLoadInterface().submitaddresssubmission(
                        AppConfig.getStringPreferences(mContext, Constant.JWTToken),
                        body1
                );

                call.enqueue(new Callback<ResponseBody>()
                {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        dialognew.dismiss();
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                String responseData = response.body().string();
                                Log.d("get verification status", responseData);
                                JSONObject object = new JSONObject(responseData);
                                if (object.getInt(Constant.STATUS) == 1)
                                {
                                    getVerificationStatus();
                                    ShowSameWalletDialog(getActivity(),object.getString("msg"),"2");
                                    dialog.dismiss();
                                } else {
                                    ShowSameWalletDialog(getActivity(),object.getString("msg"),"1");
                                    dialog.dismiss();
                                }
                            } else {
                                AppConfig.showToast("Server error");
                            }
                        } catch (IOException e) {
                            AppConfig.showToast("Server error");
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppConfig.showToast("Server json error");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AppConfig.hideLoading(dialognew);
                        t.printStackTrace();
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

    //for new
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK) {

            try {
                if (requestCode == 10) {
                    setImage();
                }else
                if (requestCode ==11)
                {
                    Uri select = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), select);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    FileOutputStream fOut;
                    try {
                        String photoname="profilepic.png";
                        File f = new File(getActivity().getFilesDir(), photoname);
                        fOut = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        if(selecetdimgtype==3)
                        {
                            changespasswordimgpath = f.getAbsolutePath();
                            changespasswordbitmap = bitmap;
                            img_first_front_pic.setImageBitmap(bitmap);
                        }

                        if(selecetdimgtype==4)
                        {
                            imagePathFirstFront = f.getAbsolutePath();
                            frontimgfirstbitmap=bitmap;
                            img_first_front_pic.setImageBitmap(bitmap);
                        }

                        if(selecetdimgtype==5)
                        {
                            imagePathFirstBack = f.getAbsolutePath();
                            backimgfirstbitmap=bitmap;
                            img_first_back_pic.setImageBitmap(bitmap);
                        }

                        if(selecetdimgtype==6)
                        {
                            imagePathSecondFront = f.getAbsolutePath();
                            frontimgsecondbitmap=bitmap;
                            img_second_front_pic.setImageBitmap(bitmap);
                        }

                        if(selecetdimgtype==7)
                        {
                            imagePathSecondBak = f.getAbsolutePath();
                            backimgsecondbitmap=bitmap;
                            img_second_back_pic.setImageBitmap(bitmap);
                        }

                        if(selecetdimgtype==8)
                        {
                            billingImagePath = f.getAbsolutePath();
                            billingBitmap=bitmap;
                            billingview.setImageBitmap(bitmap);
                            submitview.setVisibility(View.GONE);
                            submitimgbilling.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else
                if(requestCode == SELECT_VIDEO_REQUEST && resultCode == RESULT_OK)
                {
                    if(data.getData()!=null)
                    {
                        String selectedVideoPath;
                        Uri selectedImageUri = data.getData();
                        //MEDIA GALLERY
                        selectedVideoPath= getImageFilePath(selectedImageUri);
                        Log.i("Image File Path", ""+selectedVideoPath);
                        File neewds=new File(selectedVideoPath);
                        if(neewds.length()==0)
                        {
                            Toast.makeText(mContext, "video lenght 0", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Double videofile= Double.valueOf(filesize_in_megaBytes(neewds));
                            if(videofile<=8.0000000000)
                            {

                                if(i==0)
                                {
                                    i=1;
                                }else if(i==1)
                                {
                                    i=2;
                                }else if(i==2)
                                {
                                    i=3;
                                }else
                                {
                                    i=0;
                                }
                                niteview.setVisibility(View.GONE);
                                try {
                                    ExifInterface exif = new ExifInterface(selectedVideoPath);
                                    videoPath1 = selectedImagePath;
                                    Nextvideo.setVisibility(View.GONE);
                                    submitvideo.setVisibility(View.VISIBLE);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } catch (OutOfMemoryError e) {
                                    e.printStackTrace();
                                }

                            }else {
                                niteview.setText("Please make the video 8 MB or below 8 MB because you video size is " + filesize_in_megaBytes(neewds)+" MB");
                                niteview.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Failed to select video" , Toast.LENGTH_LONG).show();
                    }

                }else {
                    File file = null;
                    final String mediaFilePath;

                    //Uri vid = data.getData();
                    //mediaFilePath = getRealPathFromURI(vid);
                    selectedImagePath= Utility.getFile().getAbsolutePath();

                    //selectedImagePath = mediaFilePath;
                    final File neewds=new File(selectedImagePath);

                    if(neewds.length()==0)
                    {
                        Toast.makeText(mContext, "video lenght 0", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        final Double videofile= Double.valueOf(filesize_in_megaBytes(neewds));
                        if(videofile<=15.0000000000)
                        {
                            mediaFile1 = neewds;
                            videoPath1 = selectedImagePath;
                            submitVideoApi(neewds);
                            /*VideoCompress.compressVideoLow(mediaFilePath, mediaFilePath, new VideoCompress.CompressListener() {
                                @Override
                                public void onStart()
                                {
                                    Toast.makeText(mContext, "Start in MB "+filesize_in_megaBytes(neewds)+" MB", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess()
                                {
                                    selectedImagePath = mediaFilePath;
                                    final File neewds=new File(selectedImagePath);
                                    //mediaFile1 = neewdsnew;
                                    //videoPath1 = selectedImagePath;
                                    submitVideoApi(neewds);
                                 }

                                @Override
                                public void onFail() {
                                    Toast.makeText(mContext, "Compress faild", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onProgress(float percent) {
                                   // tv_progress.setText(String.valueOf(percent) + "%");
                                }
                            });*/

                        }else {
                            niteview.setText("Please make the video 8 MB or below 8 MB because you video size is " + filesize_in_megaBytes(neewds)+" MB");
                            niteview.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
        }

    }

    /*private void startMediaCompression() {
        progress_bar.setVisibility(View.VISIBLE);
        mVideoCompressor.startCompressing(mInputPath, new VideoCompressor.CompressionListener() {
            @Override
            public void compressionFinished(int status, boolean isVideo, String fileOutputPath) {

                if (mVideoCompressor.isDone()) {
                    File outputFile = new File(fileOutputPath);
                    long outputCompressVideosize = outputFile.length();
                    long fileSizeInKB = outputCompressVideosize / 1024;
                    long fileSizeInMB = fileSizeInKB / 1024;

                    String s = "Output video path : " + fileOutputPath + "\n" +
                            "Output video size : " + fileSizeInMB + "mb";
                    tv_output_path.setText(s);
                }
                progress_bar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(String message) {
                progress_bar.setVisibility(View.INVISIBLE);
                tv_output_path.setText("Some error find. please try again : " + message);
            }

            @Override
            public void onProgress(final int progress) {
                progress_bar.setProgress(progress);
                tv_progress.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_progress.setText(progress + "%");
                    }
                });
            }
        });
    }
    */
    private static Double filesize_in_megaBytes(File file) {
        return (double) file.length()/(1024*1024);
    }

    private static String filesize_in_kiloBytes(File file) {
        return (double) file.length()/1024+"  kb";
    }

    private static String filesize_in_Bytes(File file) {
        return file.length()+" bytes";
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    //for password
    public void selectImage()
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Image");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
            adapter.add("Take Picture");
            adapter.add("Choose from gallery");
            adapter.add("Cancel");

            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case 0:
                            try{
                                if (Utility.isExternalStorageAvailable())
                                {
                                    Intent intent = new Intent();
                                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileURI());
                                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                        startActivityForResult(intent, 10);
                                    }
                                } else
                                {
                                    Toast.makeText(getActivity(), "Need permission for access external directory", Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            break;

                        case 1:
                            try
                            {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,
                                        "Select Picture"), 11);
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
            uri = Utility.getExternalFilesDirForVersion24Above(getActivity(), Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        } else {
            uri = Utility.getExternalFilesDirForVersion24Below(getActivity(), Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        }
        return uri;
    }

    private Uri getVideoFileURI() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssZ", Locale.ENGLISH);
        Date currentDate = new Date();
        String photoFileName = "photovideo.mp4";
        String fileName = simpleDateFormat.format(currentDate) + "_" + photoFileName;

        String APP_TAG = "VideoFolder";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            uri = Utility.getExternalFilesDirForVersion24Above(getActivity(), Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        } else {
            uri = Utility.getExternalFilesDirForVersion24Below(getActivity(), Environment.DIRECTORY_PICTURES, APP_TAG, fileName);
        }
        return uri;
    }

    private void setImage() {
        if(selecetdimgtype==3)
        {
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
        if(selecetdimgtype==4)
        {
            imagePathFirstFront= Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePathFirstFront);
            FileOutputStream fOut;
            try {
                File f = new File(imagePathFirstFront);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();
                imagePathFirstFront = f.getAbsolutePath();
                frontimgfirstbitmap=bitmap;
                img_first_front_pic.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }
        if(selecetdimgtype==5)
        {
            imagePathFirstBack= Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePathFirstBack);
            FileOutputStream fOut;
            try {
                File f = new File( imagePathFirstBack);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();

                imagePathFirstBack = f.getAbsolutePath();
                backimgfirstbitmap=bitmap;
                img_first_back_pic.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }

        if(selecetdimgtype==6)
        {
            imagePathSecondFront= Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePathSecondFront);
            FileOutputStream fOut;
            try {
                File f = new File(imagePathSecondFront);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();

                imagePathSecondFront = f.getAbsolutePath();
                frontimgsecondbitmap=bitmap;
                img_second_front_pic.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }

        if(selecetdimgtype==7)
        {
            imagePathSecondBak= Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePathSecondBak);
            FileOutputStream fOut;
            try {
                File f = new File(imagePathSecondBak);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();

                imagePathSecondBak = f.getAbsolutePath();
                backimgsecondbitmap=bitmap;
                img_second_back_pic.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }

        if(selecetdimgtype==8)
        {
            billingImagePath= Utility.getFile().getAbsolutePath();
            Bitmap bitmap = BitmapFactory.decodeFile(billingImagePath);
            FileOutputStream fOut;
            try {
                File f = new File(billingImagePath);
                fOut = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                bitmap = ScalingUtilities.scaleDown(bitmap, 500, true);
                fOut.flush();
                fOut.close();

                billingImagePath = f.getAbsolutePath();
                billingBitmap=bitmap;
                billingview.setImageBitmap(bitmap);
                submitview.setVisibility(View.GONE);
                submitimgbilling.setVisibility(View.VISIBLE);

            } catch (Exception e){
                e.printStackTrace();
                StringWriter stackTrace = new StringWriter(); // not all Android versions will print the stack trace automatically
                e.printStackTrace(new PrintWriter(stackTrace));
            }
        }
      }

    public String getImageFilePath(Uri uri) {
        String path = null, image_id = null;

        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            image_id = cursor.getString(0);
            image_id = image_id.substring(image_id.lastIndexOf(":") + 1);
            cursor.close();
        }

        cursor = getActivity().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Video.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            cursor.close();
        }
        return path;
    }

    public void selectImg()
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            selectImage();

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        }
    }
}
