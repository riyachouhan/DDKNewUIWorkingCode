package com.ddkcommunity.fragment.projects;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ddkcommunity.Constant;
import com.ddkcommunity.R;
import com.ddkcommunity.activities.MainActivity;
import com.ddkcommunity.activities.SignupActivity;
import com.ddkcommunity.adapters.WalletAddressAdapter;
import com.ddkcommunity.adapters.billeralladapteralpha;
import com.ddkcommunity.fragment.paybillsModule.PayBillsFragment;
import com.ddkcommunity.fragment.paybillsModule.adapter.valueAdapterSpinner;
import com.ddkcommunity.fragment.paybillsModule.models.addformModelBiller;
import com.ddkcommunity.fragment.paybillsModule.models.billerAllModel;
import com.ddkcommunity.fragment.paybillsModule.models.dynamicFormModel;
import com.ddkcommunity.fragment.paybillsModule.models.payModelbiller;
import com.ddkcommunity.model.Wallet_Response;
import com.ddkcommunity.model.user.UserResponse;
import com.ddkcommunity.utilies.AppConfig;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ddkcommunity.utilies.dataPutMethods.ShowApiError;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class payAfterBillerFragment extends Fragment
{
    private Context mContext;
    private UserResponse userData;
    String  billernamestr="",billerid;
    View view;
    LinearLayout addpaylayout;
    TextView billername,billerfirstname;
    int year;
    int month;
    int day;
    String userage="";
    TextView dateformat,submit_BT,dateformat2;
    //.......
    int newday, newmonth, newyear, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute,mySecond=00;
    String selecetdate="";

    public payAfterBillerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.afterbilleraddlayout, container, false);
        mContext = getActivity();
        if (getArguments().getString("billerid") != null) {
            billerid= getArguments().getString("billerid");
        }

        if (getArguments().getString("billername") != null) {
            billernamestr= getArguments().getString("billername");
        }

        userData = AppConfig.getUserData(getActivity());
        addpaylayout=view.findViewById(R.id.addpaylayout);
        billername=view.findViewById(R.id.billername);
        String upperString = billernamestr.substring(0, 1).toUpperCase();
        billerfirstname=view.findViewById(R.id.billerfirstname);
        submit_BT=view.findViewById(R.id.submit_BT);
        billerfirstname.setText(upperString);
        billername.setText(billernamestr);

        for(int i = 0; i< PayBillsFragment.arraylist.size(); i++)
        {
            createEditText(PayBillsFragment.arraylist.get(i),i);
        }

        // getCategortyData(billerid);
        submit_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sizenn=PayBillsFragment.viewList.size();
                boolean vaildvlue=true;
                for(int i=0;i<PayBillsFragment.viewList.size();i++)
                {

                    if(PayBillsFragment.arraylist.get(i).getType().equalsIgnoreCase("datetime") || PayBillsFragment.arraylist.get(i).getType().equalsIgnoreCase("date"))
                    {
                        String editva = PayBillsFragment.viewList.get(i).getTxv1().getText().toString();
                        if (editva.equalsIgnoreCase(""))
                        {
                            Toast.makeText(mContext, "Please Enter "+PayBillsFragment.arraylist.get(i).getLabel(), Toast.LENGTH_SHORT).show();
                            vaildvlue=false;
                            break;
                        } else {
                            vaildvlue = isValidUsername(PayBillsFragment.arraylist.get(i).getValidation(), editva);
                            if (vaildvlue == false) {
                                Toast.makeText(mContext, "" + PayBillsFragment.arraylist.get(i).getValidation_msg(), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }else if(PayBillsFragment.arraylist.get(i).getType().equalsIgnoreCase("enum"))
                    {
                        String editva = PayBillsFragment.viewList.get(i).getSpine1().getSelectedItem().toString();
                        String upperString = PayBillsFragment.arraylist.get(i).getLabel().substring(0, 1).toUpperCase() + PayBillsFragment.arraylist.get(i).getLabel().substring(1).toLowerCase();
                        if (editva.equalsIgnoreCase("Select "+upperString))
                        {
                            Toast.makeText(mContext, "Please Select "+PayBillsFragment.arraylist.get(i).getLabel(), Toast.LENGTH_SHORT).show();
                            vaildvlue=false;
                            break;
                        } else {
                            vaildvlue = isValidUsername(PayBillsFragment.arraylist.get(i).getValidation(), editva);
                            if (vaildvlue == false) {
                                Toast.makeText(mContext, "" + PayBillsFragment.arraylist.get(i).getValidation_msg(), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }else
                    {
                        String editva = PayBillsFragment.viewList.get(i).getEd1().getText().toString();
                        if (editva.equalsIgnoreCase(""))
                        {
                            Toast.makeText(mContext, "Please Enter "+PayBillsFragment.arraylist.get(i).getLabel(), Toast.LENGTH_SHORT).show();
                            vaildvlue=false;
                            break;
                        } else {
                            vaildvlue = isValidUsername(PayBillsFragment.arraylist.get(i).getValidation(), editva);
                            if (vaildvlue == false) {
                                Toast.makeText(mContext, "" + PayBillsFragment.arraylist.get(i).getValidation_msg(), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                }

                if(vaildvlue==true)
                {

                    JSONObject object = new JSONObject();

                    for(int i=0;i<PayBillsFragment.viewList.size();i++)
                    {
                        if(PayBillsFragment.arraylist.get(i).getType().equalsIgnoreCase("datetime") || PayBillsFragment.arraylist.get(i).getType().equalsIgnoreCase("date"))
                        {
                            String editva = PayBillsFragment.viewList.get(i).getTxv1().getText().toString();
                            String keyval = PayBillsFragment.arraylist.get(i).getJsonType();
                            try {
                                object.put(keyval,editva);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else if(PayBillsFragment.arraylist.get(i).getType().equalsIgnoreCase("enum"))
                        {
                            String editva= String.valueOf(((TextView)(PayBillsFragment.viewList.get(i).getSpine1().getSelectedView().findViewById(R.id.title_TV))).getText());
                            String editvakey= String.valueOf(((TextView)(PayBillsFragment.viewList.get(i).getSpine1().getSelectedView().findViewById(R.id.title_key))).getText());
                            String keyval = PayBillsFragment.arraylist.get(i).getJsonType();
                            try {
                                JSONObject objectnew = new JSONObject();
                                objectnew.put("key",editvakey);
                                objectnew.put("value",editva);
                                object.put(keyval,objectnew);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else
                        {
                            String editva = PayBillsFragment.viewList.get(i).getEd1().getText().toString();
                            String keyval = PayBillsFragment.arraylist.get(i).getJsonType();
                            try {
                                object.put(keyval,editva);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Log.d("param",object.toString());
                }
            }
        });
        return view;
    }

    // Function to validate the username
    public static boolean isValidUsername(String name,String regex)
    {

        // Regex to check valid username.
        //String regex = "^[A-Za-z]\\w{5,29}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the username is empty
        // return false
        if (name == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given username
        // and regular expression.
        Matcher m = p.matcher(name);

        // Return if the username
        // matched the ReGex
        return m.matches();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        MainActivity.setTitle("Pay Bills");
        MainActivity.enableBackViews(true);
        MainActivity.titleText.setVisibility(View.VISIBLE);
        MainActivity.searchlayout.setVisibility(View.GONE);
    }

    private void SelectDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dateDialog = new DatePickerDialog(getActivity(),pickerListener, year, month, day);
        dateDialog.getDatePicker().setMaxDate(new Date().getTime());
        dateDialog.show();

    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            userage=getAge(year,month,day);
            //  Toast.makeText(context, "your age "+userage, Toast.LENGTH_SHORT).show();
            // Show selected date
            dateformat.setText(new StringBuilder().append(day)
                    .append("/").append(month+1).append("/").append(year)
                    .append(" "));

        }
    };

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public void createEditText(addformModelBiller.Datum modelBiller,int position)
    {

        LinearLayout row = new LinearLayout(getActivity());
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        row.setBackgroundResource(R.drawable.edit_text_bla_back);
        row.setGravity(Gravity.CENTER_VERTICAL);

        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(11);
        String upperString = modelBiller.getLabel().substring(0, 1).toUpperCase() + modelBiller.getLabel().substring(1).toLowerCase();
        textView.setText(upperString+" ");
        textView.setTextColor(getActivity().getResources().getColor(R.color.txtblack));
        row.addView(textView);


        if (modelBiller.getType().toString().equalsIgnoreCase("datetime"))
        {

            final TextView dateformat12 = new TextView(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            dateformat12.setLayoutParams(p);
            dateformat12.setGravity(Gravity.RIGHT);
            dateformat12.setTextSize(12);
            dateformat12.setPadding(5,5,5,5);
            dateformat12.setBackgroundColor(getResources().getColor(R.color.white));
            dateformat12.setText("Select "+upperString+"");
            dateformat12.setId(position);
            row.addView(dateformat12);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setTxv1(dateformat12);
            PayBillsFragment.viewList.add(vielismo);

            dateformat12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateformat2=dateformat12;
                    Calendar calendar = Calendar.getInstance();
                    newyear = calendar.get(Calendar.YEAR);
                    newmonth = calendar.get(Calendar.MONTH);
                    newday = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), pickerListenertime,newyear, newmonth,newday);
                    datePickerDialog.show();
                }
            });

        }else
        if (modelBiller.getType().toString().equalsIgnoreCase("date"))
        {

            final TextView dateformat1 = new TextView(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            dateformat1.setLayoutParams(p);
            dateformat1.setGravity(Gravity.RIGHT);
            dateformat1.setTextSize(12);
            dateformat1.setPadding(5,5,5,5);
            dateformat1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            dateformat1.setBackgroundColor(getResources().getColor(R.color.white));
            dateformat1.setText("Select "+upperString+"");
            dateformat1.setId(position);
            row.addView(dateformat1);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setTxv1(dateformat1);
            PayBillsFragment.viewList.add(vielismo);

            dateformat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateformat=dateformat1;
                    SelectDate();
                }
            });

        }else
        if (modelBiller.getType().toString().equalsIgnoreCase("password"))
       {

        EditText newField = new EditText(getActivity());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.setMargins(1, 1, 1, 1);
        newField.setLayoutParams(p);
        newField.setGravity(Gravity.RIGHT);
        newField.setTextSize(12);
        newField.setPadding(5,5,5,5);
        newField.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newField.setBackgroundColor(getResources().getColor(R.color.white));
        newField.setHint("Enter "+upperString+"");
        newField.setId(position);
        row.addView(newField);
        addpaylayout.addView(row);

           dynamicFormModel vielismo=new dynamicFormModel();
           vielismo.setType(modelBiller.getJsonType().toString());
           vielismo.setEd1(newField);
           PayBillsFragment.viewList.add(vielismo);

        }else
        if (modelBiller.getType().toString().equalsIgnoreCase("point") ||modelBiller.getType().toString().equalsIgnoreCase("uri") ||modelBiller.getType().toString().equalsIgnoreCase("base64") ||modelBiller.getType().toString().equalsIgnoreCase("hex") || modelBiller.getType().toString().equalsIgnoreCase("email") ||modelBiller.getType().toString().equalsIgnoreCase("text") || modelBiller.getType().toString().equalsIgnoreCase("word")|| modelBiller.getType().toString().equalsIgnoreCase("phrase"))
        {

            EditText newField = new EditText(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            newField.setLayoutParams(p);
            newField.setGravity(Gravity.RIGHT);
            newField.setTextSize(12);
            newField.setPadding(5,5,5,5);
            newField.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            newField.setBackgroundColor(getResources().getColor(R.color.white));
            newField.setHint("Enter "+upperString+" "+modelBiller.getSample().toString());
            newField.setId(position);
            row.addView(newField);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setEd1(newField);
            PayBillsFragment.viewList.add(vielismo);

        }else if (modelBiller.getType().toString().equalsIgnoreCase("address") || modelBiller.getType().toString().equalsIgnoreCase("utf-8") || modelBiller.getType().toString().equalsIgnoreCase("sentence"))
        {

            EditText newField = new EditText(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            newField.setLayoutParams(p);
            newField.setGravity(Gravity.RIGHT);
            newField.setTextSize(12);
            newField.setPadding(5,5,5,5);
            newField.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            newField.setBackgroundColor(getResources().getColor(R.color.white));
            newField.setHint("Enter "+upperString+"");
            newField.setId(position);
            row.addView(newField);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setEd1(newField);
            PayBillsFragment.viewList.add(vielismo);

        } else if (modelBiller.getType().toString().equalsIgnoreCase("number") || modelBiller.getType().toString().equalsIgnoreCase("mobile") || modelBiller.getType().toString().equalsIgnoreCase("integer"))
        {

            EditText newField = new EditText(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            newField.setLayoutParams(p);
            newField.setGravity(Gravity.RIGHT);
            newField.setTextSize(12);
            newField.setPadding(5,5,5,5);
            newField.setInputType(InputType.TYPE_CLASS_NUMBER);
            newField.setBackgroundColor(getResources().getColor(R.color.white));
            newField.setHint("Enter "+upperString+"");
            newField.setId(position);
            row.addView(newField);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setEd1(newField);
            PayBillsFragment.viewList.add(vielismo);

        }else if (modelBiller.getType().toString().equalsIgnoreCase("enum"))
        {
            try {
                String valuearray=modelBiller.getValue();
                JSONArray jsonvalue=new JSONArray(valuearray);
                if(jsonvalue!=null)
                {
                    ArrayList<addformModelBiller.Value> arraylistval = new ArrayList<>();
                    addformModelBiller.Value model11=new addformModelBiller.Value();
                    model11.setKey("1110");
                    model11.setValue("Select "+upperString);
                    arraylistval.add(model11);

                    for(int i=0;i<jsonvalue.length();i++)
                    {
                        JSONObject jsonObject = jsonvalue.getJSONObject(i);
                        // add interviewee name to arraylist
                        addformModelBiller.Value model1=new addformModelBiller.Value();
                        model1.setKey(jsonObject.getString("key"));
                        model1.setValue(jsonObject.getString("value"));
                        arraylistval.add(model1);
                    }

                    final Spinner spinner = new Spinner(getActivity());
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(1, 1, 1, 1);
                    spinner.setLayoutParams(p);
                    spinner.setGravity(Gravity.RIGHT);
                    // we pass our item list and context to our Adapter.
                    valueAdapterSpinner adapter1 = new valueAdapterSpinner(getActivity(), arraylistval);
                    spinner.setAdapter(adapter1);
                    row.addView(spinner);

                    dynamicFormModel vielismo=new dynamicFormModel();
                    vielismo.setType(modelBiller.getJsonType().toString());
                    vielismo.setSpine1(spinner);
                    PayBillsFragment.viewList.add(vielismo);

                    spinner.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent,
                                                           View view, int position, long id)
                                {
                                    // It returns the clicked item.
                                    addformModelBiller.Value clickedItem = (addformModelBiller.Value)
                                            parent.getItemAtPosition(position);
                                    String name = clickedItem.getValue();
                                    String keyva=clickedItem.getKey();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent)
                                {
                                }
                            });
                }

                addpaylayout.addView(row);

            }catch (Exception e)
            {
                e.printStackTrace();
            }

        } else if (modelBiller.getType().toString().equalsIgnoreCase("money"))
        {

            EditText newField = new EditText(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            newField.setLayoutParams(p);
            newField.setGravity(Gravity.RIGHT);
            newField.setTextSize(12);
            newField.setPadding(5,5,5,5);
            newField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            newField.setBackgroundColor(getResources().getColor(R.color.white));
            newField.setHint("Enter "+upperString+"");
            newField.setId(position);
            row.addView(newField);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setEd1(newField);
            PayBillsFragment.viewList.add(vielismo);

        } else if (modelBiller.getType().toString().equalsIgnoreCase("formula"))
        {

            EditText newField = new EditText(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            newField.setLayoutParams(p);
            newField.setGravity(Gravity.RIGHT);
            newField.setTextSize(12);
            newField.setPadding(5,5,5,5);
            newField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            newField.setBackgroundColor(getResources().getColor(R.color.white));
            newField.setHint("Enter "+upperString+"");
            newField.setId(position);
            row.addView(newField);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setEd1(newField);
            PayBillsFragment.viewList.add(vielismo);

        }else
        {

            EditText newField = new EditText(getActivity());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.setMargins(1, 1, 1, 1);
            newField.setLayoutParams(p);
            newField.setGravity(Gravity.RIGHT);
            newField.setTextSize(12);
            newField.setPadding(5,5,5,5);
            newField.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            newField.setBackgroundColor(getResources().getColor(R.color.white));
            newField.setHint("Enter "+upperString+" "+modelBiller.getSample().toString());
            newField.setId(position);
            row.addView(newField);
            addpaylayout.addView(row);

            dynamicFormModel vielismo=new dynamicFormModel();
            vielismo.setType(modelBiller.getJsonType().toString());
            vielismo.setEd1(newField);
            PayBillsFragment.viewList.add(vielismo);
        }

        TextView textView1 = new TextView(getActivity());
        LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        p1.setMargins(0, 25, 0, 25);
        textView1.setLayoutParams(p1);
       // textView1.setBackgroundResource(R.drawable.edit_text_bla);
        addpaylayout.addView(textView1);

    }

    class DecimalDigitsInputFilter implements InputFilter {
        private Pattern mPattern;
        DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener pickerListenertime = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            myYear = selectedYear;
            myday = selectedDay;
            myMonth = selectedMonth;
            selecetdate= myday+"/"+(myMonth+1)+"/"+myYear;
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR);
            minute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),pickerListenertimenew, hour, minute, DateFormat.is24HourFormat(getActivity()));
            timePickerDialog.show();

        }
    };

    private TimePickerDialog.OnTimeSetListener pickerListenertimenew = new TimePickerDialog.OnTimeSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            myHour = hourOfDay;
            myMinute = minute;
            dateformat2.setText(selecetdate+" "+ myHour + ":" +myMinute+":"+mySecond);

        }
    };

}