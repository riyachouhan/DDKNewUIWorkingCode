package com.ddkcommunity.fragment.send;


import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.ddkcommunity.fragment.ScanFragment.clickoption;
import static com.ddkcommunity.fragment.send.SendFragment.ddkscan;

/**
 * A simple {@link Fragment} subclass.
 */
public class BcardFragment extends Fragment {
    private static final String cameraPerm = Manifest.permission.CAMERA;
    boolean hasCameraPermission = false;
    private SurfaceView mySurfaceView;
    private QREader qrEader;
    private String clickAddressname="";
    View view;
    TextView tvWalletAddressCopy;

    public BcardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_bcard, container, false);
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(getActivity(), cameraPerm);
        mySurfaceView = view.findViewById(R.id.camera_view);
        tvWalletAddressCopy=view.findViewById(R.id.tvWalletAddressCopy);
        if (hasCameraPermission) {
            // Setup QREader
            setupQREader();
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA},
                    100);
        }

        tvWalletAddressCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copy", tvWalletAddressCopy.getText().toString().trim());
                clipboard.setPrimaryClip(clip);
                AppConfig.showToast("Copied");

            }
        });
        return view;
    }


    void setupQREader()
    {
        qrEader = new QREader.Builder(getActivity(), mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data)
            {
                    String linkvalue=data;
                if(linkvalue!=null) {
                    //send view
                    Fragment fragment = new SendLinkFragment();
                    Bundle arg = new Bundle();
                    arg.putString("link", linkvalue);
                    fragment.setArguments(arg);
                    MainActivity.addFragment(fragment, true);
                }else
                {
                    Toast.makeText(getActivity(), "Link not available ", Toast.LENGTH_SHORT).show();
                }

            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (hasCameraPermission)
        {
            // Cleanup in onPause()
            // --------------------
            qrEader.releaseAndCleanup();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasCameraPermission) {

            // Init and Start with SurfaceView
            // -------------------------------
            qrEader.initAndStart(mySurfaceView);
        }
        MainActivity.setTitle("Scan via QR Code");
        MainActivity.enableBackViews(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode == 100)
        {
            RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                @Override
                public void onPermissionGranted()
                {
                    if (RuntimePermissionUtil.checkPermissonGranted(getActivity(), cameraPerm))
                    {
                        if(mySurfaceView!=null)
                        {
                            setupQREader();
                            qrEader.initAndStart(mySurfaceView);
                        }else
                        {

                        }
                    }
                }

                @Override
                public void onPermissionDenied() {
                    // do nothing
                }
            });
        }
    }

}
