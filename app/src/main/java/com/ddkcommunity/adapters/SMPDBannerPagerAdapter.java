package com.ddkcommunity.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.send.SendLinkFragment;
import com.ddkcommunity.model.SliderImg;
import com.ddkcommunity.model.smpdModelNew;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SMPDBannerPagerAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<smpdModelNew.Banner> mResources;

    public SMPDBannerPagerAdapter(Context context, ArrayList<smpdModelNew.Banner> mResources) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CardView) object);
    }
    /*@Override
    public float getPageWidth(final int position) {
        return 0.8f;
    }*/
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        CardView slidermain=itemView.findViewById(R.id.slidermain);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext).load(Constant.SLIDERIMG+mResources.get(position).getImage()).into(imageView);
        container.addView(itemView);
        slidermain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String linkvalue=mResources.get(position).getImage();
                if(linkvalue!=null) {
                    //send view
                    Fragment fragment = new SendLinkFragment();
                    Bundle arg = new Bundle();
                    arg.putString("link", linkvalue);
                    fragment.setArguments(arg);
                    MainActivity.addFragment(fragment, true);
                }else
                {
                    Toast.makeText(mContext, "Link not available ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }
}