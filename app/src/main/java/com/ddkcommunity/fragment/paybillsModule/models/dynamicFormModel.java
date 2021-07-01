package com.ddkcommunity.fragment.paybillsModule.models;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

public class dynamicFormModel
{
    EditText ed1;
    TextView txv1;
    Spinner spine1;
    String type;

    public EditText getEd1() {
        return ed1;
    }

    public void setEd1(EditText ed1) {
        this.ed1 = ed1;
    }

    public TextView getTxv1() {
        return txv1;
    }

    public void setTxv1(TextView txv1) {
        this.txv1 = txv1;
    }

    public Spinner getSpine1() {
        return spine1;
    }

    public void setSpine1(Spinner spine1) {
        this.spine1 = spine1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
