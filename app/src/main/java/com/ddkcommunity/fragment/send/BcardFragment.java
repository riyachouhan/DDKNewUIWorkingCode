package com.ddkcommunity.fragment.send;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ddkcommunity.App;
import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.HomeFragment;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.RPResultListener;
import com.ddkcommunity.utilies.RuntimePermissionUtil;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.ddkcommunity.fragment.ScanFragment.clickoption;
import static com.ddkcommunity.fragment.send.SendFragment.ddkscan;

/**
 * A simple {@link Fragment} subclass.
 */
public class BcardFragment extends Fragment {

    public final static int QRcodeWidth = 400;
    private Bitmap bitmap;
    private CallbackManager callbackManager;
    private ImageView ivQrCode;
    private Context mContext;
    private static final String IMAGE_DIRECTORY = "/ddkpay";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private TextView header_view,tvWalletAddressCopy,tvText1;
    private ClipboardManager clipboard;
    private ClipData clip;
    //    private ShareDialog shareDialog;
    private ProgressDialog pDialog;
    ShareDialog shareDialog;
    String clickAddressname;
    private UserResponse userData;
     View view;
    public BcardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_bcard, container, false);
        mContext = getActivity();
        userData = AppConfig.getUserData(mContext);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        pDialog = new ProgressDialog(mContext);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        header_view=view.findViewById(R.id.header_view);
        tvText1=view.findViewById(R.id.tvText1);
        ivQrCode = view.findViewById(R.id.ivQrCode);
        tvWalletAddressCopy = view.findViewById(R.id.tvWalletAddressCopy);

       view.findViewById(R.id.tvWalletAddressCopy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clipboard = (ClipboardManager) mContext.getSystemService(CLIPBOARD_SERVICE);
                clip = ClipData.newPlainText("Copy", tvWalletAddressCopy.getText().toString().trim());
                clipboard.setPrimaryClip(clip);
                AppConfig.showToast("Copied");
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            if (!checkPermission()) {
                requestPermission(); // Code for permission
            }
        }

        try {
            header_view.setText(getResources().getString(R.string.toclickfirst)+" "+getResources().getString(R.string.toclicksecond));
            String userEmail =App.pref.getString(Constant.UserWebViewProfile, "");
            tvWalletAddressCopy.setText(userEmail);
            bitmap = TextToImageEncode(userEmail);
            ivQrCode.setImageBitmap(bitmap);
            if(bitmap!=null) {
                String path = saveImage(bitmap);  //give read write permission
            }//Toast.makeText(context, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
        } catch (WriterException e)
        {
            e.printStackTrace();
        }
        view.findViewById(R.id.btnShareQrCode).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view)
            {
                try
                {
                    String messagecontetn ="I am shareing my profile link which is "+tvWalletAddressCopy.getText().toString();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, messagecontetn);
                    startActivity(Intent.createChooser(shareIntent , "Share You Profile"));

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }

        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("Scan via QR Code");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, "qr_code" + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(mContext,
                    new String[]{f.getPath()},
                    new String[]{"image/jpg"}, null);
            fo.close();
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {
            Illegalargumentexception.printStackTrace();
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 400, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                break;
        }
    }

}
