package com.ddkcommunity.fragment.paybillsModule.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ddkcommunity.R;
import com.ddkcommunity.fragment.paybillsModule.models.addformModelBiller;

import java.util.ArrayList;

public class valueAdapterSpinner extends ArrayAdapter<addformModelBiller.Value> {

    Activity context;
    ArrayList<addformModelBiller.Value> algorithmList;

    public valueAdapterSpinner(Activity context,
                            ArrayList<addformModelBiller.Value> algorithmList)
    {
        super(context, 0, algorithmList);
        this.context=context;
        this.algorithmList=algorithmList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.iteam_value, parent, false);
        }

        TextView title_key=convertView.findViewById(R.id.title_key);
        TextView textViewName = convertView.findViewById(R.id.title_TV);
        addformModelBiller.Value currentItem = getItem(position);
        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null)
        {
            if(position==0)
            {
                textViewName.setTextColor(context.getResources().getColor(R.color.texthint));

            }else
            {
                textViewName.setTextColor(context.getResources().getColor(R.color.txtblack));
            }

            title_key.setText(currentItem.getKey());
            textViewName.setText(currentItem.getValue());
        }
        return convertView;
    }
}