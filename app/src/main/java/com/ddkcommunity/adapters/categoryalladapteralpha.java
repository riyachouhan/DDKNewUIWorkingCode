package com.ddkcommunity.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.fragment.paybillsModule.models.billerAllModel;
import com.ddkcommunity.fragment.paybillsModule.models.categoryAllModel;
import com.ddkcommunity.fragment.projects.CategoryAllFragment;
import com.ddkcommunity.fragment.projects.selectedCatFragment;
import com.ddkcommunity.fragment.send.SendLinkFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class categoryalladapteralpha extends RecyclerView.Adapter<categoryalladapteralpha.ViewHolder>
        implements SectionIndexer {

    private List<categoryAllModel.Datum> mDataArray;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private HashMap<Integer, Integer> sectionsTranslator = new HashMap<>();
    private ArrayList<Integer> mSectionPositions;

    public categoryalladapteralpha(List<categoryAllModel.Datum> dataset) {
        mDataArray = dataset;
    }

    public void updateData( ArrayList<categoryAllModel.Datum> countrygender) {
        this.mDataArray= countrygender;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDataArray == null)
            return 0;
        return mDataArray.size();
    }

    @Override
    public categoryalladapteralpha.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.billeriteamlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
            holder.ivBankIcon.setImageResource(R.drawable.bpiimg);
            holder.tvBankName.setText(mDataArray.get(position).getCategoryName().toString());
            holder.ll_selectedBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String catseledt=mDataArray.get(position).getCategoryName();
                Fragment fragment = new selectedCatFragment();
                Bundle arg = new Bundle();
                arg.putString("catid", catseledt);
                arg.putString("type", "cat");
                fragment.setArguments(arg);
                MainActivity.addFragment(fragment, true);

            }
        });
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(27);
        ArrayList<String> alphabetFull = new ArrayList<>();

        mSectionPositions = new ArrayList<>();
        for (int i = 0, size = mDataArray.size(); i < size; i++) {
            String section = String.valueOf(mDataArray.get(i).getCategoryName().charAt(0)).toUpperCase();
            if (!sections.toString().toUpperCase().contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        for (int i = 0; i < mSections.length(); i++) {
            alphabetFull.add(String.valueOf(mSections.charAt(i)));
        }


        return alphabetFull.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionsTranslator.get(sectionIndex));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName;
        ImageView ivBankIcon;
        LinearLayout ll_selectedBank;

        ViewHolder(View itemView) {
            super(itemView);
            ll_selectedBank=itemView.findViewById(R.id.ll_selectedBank);
            ivBankIcon=itemView.findViewById(R.id.ivBankIcon);
            tvBankName = itemView.findViewById(R.id.tvBankName);
        }
    }

}