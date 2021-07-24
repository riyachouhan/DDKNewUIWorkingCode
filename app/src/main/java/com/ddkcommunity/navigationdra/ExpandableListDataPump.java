package com.ddkcommunity.navigationdra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump
{
    public static HashMap<String, List<String>> getData(String acitivyttype) {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> menu3 = new ArrayList<String>();
        List<String> menu4 = new ArrayList<String>();

        List<String> list1 = new ArrayList<String>();
        list1.add("Phase 1");
        list1.add("Direct Referral");
        list1.add("Group");
        list1.add("Power of 10");
        list1.add("Platinum");
        list1.add("Titanium");
        list1.add("Daily Rewards");

        List<String> list2 = new ArrayList<String>();
        if (acitivyttype.equalsIgnoreCase("map")) {
            expandableListDetail.put("Funnel", menu3);
            expandableListDetail.put("Group", menu4);
            expandableListDetail.put("Bonuses", list1);
            expandableListDetail.put("Overflow", list2);
        }else {
            //expandableListDetail.put("Profile", list2);
            expandableListDetail.put("Setting   ", list2);
            expandableListDetail.put("Credentials", list2);
            expandableListDetail.put("B-Card", list2);
            expandableListDetail.put("Activity", list2);
            expandableListDetail.put("Contact Us", list2);
            expandableListDetail.put("Logout", list2);
        }
        return expandableListDetail;
    }

}
