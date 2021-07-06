package com.ddkcommunity.demopack;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.SplashActivity;
import com.ddkcommunity.model.CheckOtpResponse;
import com.ddkcommunity.model.OtpResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.ddkcommunity.utilies.CommonMethodFunction;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.ddkcommunity.utilies.dataPutMethods.errordurigApiCalling;

public class cretaeDemoActivity extends AppCompatActivity {

    Context context;
    Button btnRead , btnSave;
    EditText txtInput;
    TextView txtContent;
    //..........
    final static String fileName = "data.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/" ;
    final static String TAG = cretaeDemoActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "All permisison is allow", Toast.LENGTH_SHORT).show();
                // proceed();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createdemolayout);
        context = this;
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtInput = (EditText) findViewById(R.id.txtInput);
        //.............
            //**************************** Location ( Latitude & longitude ) *********************************
            if (ActivityCompat.checkSelfPermission(cretaeDemoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(cretaeDemoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {

                ActivityCompat.requestPermissions(cretaeDemoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            } else {
                //if we have use the debug mode and release mode for the testing purpose then we have use the check login.
                //And upload on the play store then we have use the check for update app.
            }
        //.................
        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtContent.setText(ReadFile(cretaeDemoActivity.this));
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ActivityCompat.checkSelfPermission(cretaeDemoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(cretaeDemoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {

                    ActivityCompat.requestPermissions(cretaeDemoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_REQUEST_CODE);
                } else {
                    //if we have use the debug mode and release mode for the testing purpose then we have use the check login.
                    //And upload on the play store then we have use the check for update app.
                    String pathv=getFileName(txtInput.getText().toString());
                    if (pathv!=null){
                        Toast.makeText(cretaeDemoActivity.this,"Saved to file",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(cretaeDemoActivity.this,"Error save file!!!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public static  String ReadFile( Context context){
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    private String getFileName(String emilcombo) {
        String fileName = "";
        try {
            File directory = null;
            if (Build.VERSION.SDK_INT >= 29) {
                directory = new File(getExternalFilesDir(null).getAbsolutePath() + "/" + "MyReflect/");
            } else {
                directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "MyReflect/");
            }

            if (!directory.exists()) {
                directory.mkdirs();
                if (!directory.exists()) {
                    Log.d("appfile", "directory Name =" + directory);
                }
            }

            fileName = directory.toString() + "/" + emilcombo + ".txt";
            Log.d("appfile", "File Name =" + fileName);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return fileName;
    }

    public String saveToFile( String data) {
        String pathej = "";
        try {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/MyReflect");
           // have the object build the directory structure, if needed.
           ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }
           File f = new File(wallpaperDirectory, data + ".txt");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            pathej = f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return pathej;
    }
            /*String rootSdDirectory = Environment.getExternalStorageDirectory()
                    + File.separator + "yateneshimg" + File.separator;

            File casted_image;

            casted_image = new File(rootSdDirectory, "qr_code.txt");
            if (casted_image.exists()) {
                casted_image.delete();
            }
            casted_image.createNewFile();

            FileOutputStream fos = new FileOutputStream(casted_image);
            URL url;
*/
           /* new File(path).mkdir();
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
*/
  /*         FileOutputStream fileOutputStream = new FileOutputStream(fos,true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
*/

}