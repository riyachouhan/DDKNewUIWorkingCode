package com.ddkcommunity.navigationdra;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddkcommunity.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public static int ITEM1 = 0;
    public static int ITEM2 = 1;
    public static int ITEM3 = 2;
    public static int ITEM4 = 3;
    public static int ITEM5 = 4;
    public static int ITEM6 = 5;
    public static int ITEM7 = 6;
    public static int ITEM8 = 7;


    public static int SUBITEM1_1 = 0;
    public static int SUBITEM1_2 = 1;
    public static int SUBITEM1_3 = 2;
    public static int SUBITEM1_4 = 3;
    public static int SUBITEM1_5 = 4;
    public static int SUBITEM1_6 = 5;
    public static int SUBITEM1_7 = 6;
    public static int SUBITEM2_1 = 0;
    public static int SUBITEM2_2 = 1;
    public static int SUBITEM2_3 = 2;
    public static int SUBITEM2_4 = 3;
    public  String acitivyttype;

    public ExpandableListAdapter(String acitivyttype,Context context, List<String> expandableListTitle,
                                 HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.acitivyttype=acitivyttype;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);

        ImageView listTitleTextArrowView =(ImageView) convertView.findViewById(R.id.listTitleArrow);

        // set icons for menu items
        ImageView listTitleTextIconView = convertView.findViewById(R.id.listTitleIcon);
        if (acitivyttype.equalsIgnoreCase("map")) {
            if (listPosition == ITEM1)
                listTitleTextIconView.setImageResource(R.drawable.funnel);
            else if (listPosition == ITEM2)
                listTitleTextIconView.setImageResource(R.drawable.groupm);
            else if (listPosition == ITEM3)
                listTitleTextIconView.setImageResource(R.drawable.bonourse);
            else if (listPosition == ITEM4)
                listTitleTextIconView.setImageResource(R.drawable.overflow);
        }else
        {
            if (listPosition == ITEM1)
                listTitleTextIconView.setImageResource(R.drawable.ic_profile);
            else if (listPosition == ITEM2)
                listTitleTextIconView.setImageResource(R.drawable.ic_credentials);
            else if (listPosition == ITEM3)
            listTitleTextIconView.setImageResource(R.drawable.browser);
            else if (listPosition == ITEM4)
                listTitleTextIconView.setImageResource(R.drawable.diary);
        else if (listPosition == ITEM5)
            listTitleTextIconView.setImageResource(R.drawable.ic_mail);
        else if (listPosition == ITEM6)
                listTitleTextIconView.setImageResource(R.drawable.ic_logout);
        }

        // set arrow icons for relevant items
        if (acitivyttype.equalsIgnoreCase("map"))
        {
            if (listPosition == ITEM3)
            {
                listTitleTextArrowView.setImageResource(R.drawable.right_arrow_fill);
            } else {

            }
        }
        if (isExpanded)
        {
            if (listPosition == ITEM3)
            {
                listTitleTextArrowView.setImageResource(R.drawable.arrow_fill_down);
            }
         } else
        {
            if (acitivyttype.equalsIgnoreCase("map"))
            {
                if (listPosition == ITEM3) {
                    listTitleTextArrowView.setImageResource(R.drawable.right_arrow_fill);
                }
            }
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
