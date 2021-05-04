package com.ddkcommunity.fragment.send;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;

import static com.ddkcommunity.fragment.wallet.FragmentCreatePassphrase.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendLinkFragment extends Fragment
{
    private View rootView = null;
    private Context mContext;
    public SendLinkFragment() {
        // Required empty public constructor
    }

    private String link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            View view = inflater.inflate(R.layout.fragment_link, container, false);
            rootView = view;
            mContext = getActivity();
            try {
                if (getArguments().getString("link") != null) {
                    link= getArguments().getString("link");
                }
                String linkmain=link;
                WebView webview =view.findViewById(R.id.webView);
                WebSettings settings = webview.getSettings();
                settings.setJavaScriptEnabled(true);
                webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

                final ProgressDialog progressBar = ProgressDialog.show(getActivity(), "", "Loading...");
                webview.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Log.i(TAG, "Processing webview url click...");
                        view.loadUrl(url);
                        return true;
                    }

                    public void onPageFinished(WebView view, String url) {
                        Log.i(TAG, "Finished loading URL: " +url);
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                    }

                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Log.e(TAG, "Error: " + description);
                        Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    }
                });
                webview.loadUrl(link);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.setTitle("");
        MainActivity.enableBackViews(true);
    }
}
