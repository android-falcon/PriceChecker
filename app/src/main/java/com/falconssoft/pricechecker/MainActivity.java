package com.falconssoft.pricechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    EditText editeTextItemCode;
    ImageButton setting;
    PriceCheckerDatabase priceCheckerDatabase;
    String ipAddress ="",cono;
    String   URL_TO_HIT="";
    public  static  TextView itemprice,itemname;
    public  boolean openDial=false;
    RadioGroup CurrencyradioButton;
    String   Currencyvalu="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editeTextItemCode=findViewById(R.id.editeTextItemCode);
        editeTextItemCode.requestFocus();
        setting=findViewById(R.id.setting);
        itemname=findViewById(R.id.itemname);
                itemprice=findViewById(R.id.itemprice);


        priceCheckerDatabase=new PriceCheckerDatabase(MainActivity.this);
        ipAddress =priceCheckerDatabase.getAllMainSetting();

        cono=priceCheckerDatabase.getAllMainSetting_cono();
        getWindow().setSoftInputMode(

                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN

        );
try {
    Currencyvalu= priceCheckerDatabase.getCurrencySetting();
    if(Currencyvalu.equals("1"))
    itemprice.setText("0.00 IQD");
    else    itemprice.setText("0.00 JD");
}catch (Exception exception ){
    Currencyvalu="0";
    itemprice.setText("0.00 JD");
}
//            new SyncGetItem().execute();
//        if(!ipAddress.equals(""))
//        new JSONTask_AccountStatment_Withdate().execute();
        editeTextItemCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_NULL) {

                    Log.e("onEditorAction",""+editeTextItemCode.getText().toString());
                    ccleatext();
//                    dialogForItem("مياه سما 250 مل ","0.25 JD");
                    if(ipAddress.trim().length()!=0)
                    new JSONTask_AccountStatment_Withdate().execute();
                    Toast.makeText(MainActivity.this, "tex", Toast.LENGTH_SHORT).show();
                }



                return false;
            }
        });
//        editeTextItemCode.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editeTextItemCode.getText().toString().trim().length()!=0){
//                    if(ipAddress.trim().length()!=0)
//                    new JSONTask_AccountStatment_Withdate().execute();
//                }
//
//            }
//        });
//        editeTextItemCode.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN)
//                {
//                    Log.e("onKey",""+editeTextItemCode.getText().toString());
//
//                    switch (i)
//                    {
//
//                        case KeyEvent.KEYCODE_DPAD_CENTER:
//                        case KeyEvent.KEYCODE_ENTER:
//
//                            if ((!editeTextItemCode.getText().equals(""))) {
//
////                editeTextItemCode.setError(null);
//                                Log.e("KeyListeneritemqty==", editeTextItemCode.getText().toString().trim());
//
//
//                                final Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        if(!ipAddress.equals(""))
//                                        new JSONTask_AccountStatment_Withdate().execute();
////                            editeTextItemCode.requestFocus();
//
//
//                                    }
//
//                                }, 100);
//
//
////
//                            } else {
//                                Log.e("else==", "else");
////                                editeTextItemCode.setError("");
////                                editeTextItemCode.requestFocus();
//                            }
//
//                            break;
//                    }
//                    return  true;
//                }
//
//
//                return false;
//            }
//        });





        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwordDialog();

                
            }
        });

    }

    private void ccleatext() {
        itemname.setText("");
        itemprice.setText("");
        editeTextItemCode.requestFocus();
    }


    void dialogSetting() {
        final Dialog settingDialog = new Dialog(MainActivity.this, R.style.Theme_Dialog);
        settingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingDialog.setContentView(R.layout.setting_dialog);
        settingDialog.setCancelable(false);
        settingDialog.setCanceledOnTouchOutside(true);
        String ip="";
           Currencyvalu="0";
        String   Currencyvalu2="0";
        final EditText ipAddress=settingDialog.findViewById(R.id.ipAddress);
        final EditText cono=settingDialog.findViewById(R.id.cono);
        CurrencyradioButton=settingDialog.findViewById(R.id. Currency);
       RadioButton jdCurrencyradioButton=settingDialog.findViewById(R.id. jdCurrencyradioButton);
        RadioButton IQDCurrencyradioButton=settingDialog.findViewById(R.id. IQDCurrencyradioButton);
        CurrencyradioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup,
                                         int radioButtonID) {
                switch(radioButtonID) {
                    case R.id.jdCurrencyradioButton:
                        Currencyvalu="0";
                        break;
                    case R.id.IQDCurrencyradioButton:
                        Currencyvalu="1";
                        break;

                }
            }
        });

        Button okButton=settingDialog.findViewById(R.id.OkButton);
        try {
             ip =priceCheckerDatabase.getAllMainSetting();
            Currencyvalu2=  priceCheckerDatabase.getCurrencySetting();
            if(Currencyvalu2.equals("0")){

                jdCurrencyradioButton.setChecked(true);
                IQDCurrencyradioButton.setChecked(false);
            }
            else {
                jdCurrencyradioButton.setChecked(false);
                IQDCurrencyradioButton.setChecked(true);
            }

            cono.setText(priceCheckerDatabase.getAllMainSetting_cono());
//             if(!ip.equals(""))
//             {
//
//             }
        }

        catch (Exception e)
        {

        }


//        if(!ip.equals(""))
        ipAddress.setText(ip);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if(!TextUtils.isEmpty(ipAddress.getText().toString())){


                    priceCheckerDatabase.deleteAllSetting();
                    priceCheckerDatabase.addAllMainSetting(ipAddress.getText().toString().trim(),cono.getText().toString().trim(),Currencyvalu);

                    settingDialog.dismiss();
                    try {
                        Currencyvalu= priceCheckerDatabase.getCurrencySetting();
                        ipAddress=ip;
                        if(Currencyvalu.equals("1"))
                            itemprice.setText("0.00 IQD");
                        else    itemprice.setText("0.00 JD");
                    }catch (Exception exception ){
                        Currencyvalu="0";
                        itemprice.setText("0.00 JD");
                    }


                    Toast.makeText(MainActivity.this, "Save", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "IP Address is Empty", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        
        
        
        settingDialog.show();
    }

    void dialogForItem(String itemName,String itemPrice){
        final Dialog dialog = new Dialog(MainActivity.this,R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.name_price_dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        openDial=true;
        TextView itemNameText,itemPriceText,cancel;
        itemNameText=dialog.findViewById(R.id.ItemName);
        itemPriceText=dialog.findViewById(R.id.itemPrice);

        cancel=dialog.findViewById(R.id.cancel);
        itemNameText.setText(itemName);
        itemPriceText.setText(itemPrice);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDial=false;
                dialog.dismiss();
            }
        });

        dialog.show();




    }

    private class SyncGetItem extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
//        SweetAlertDialog pdItem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog = new ProgressDialog(context,R.style.MyTheme);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();

//            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            pdItem.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//            pdItem.setTitleText("Item  ");
//            pdItem.setCancelable(false);
//            pdItem.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                final List<MainSetting> mainSettings=dbHandler.getAllMainSetting();
                String ip="",STORE="295";
//                if(mainSettings.size()!=0) {
//                    ip= mainSettings.get(0).getIP();
////                    STORE=mainSettings.get(0).getStorNo();
//                }
                Log.e("ipAddress",""+ipAddress);
                if(ipAddress.trim().length()==0)
                {  ipAddress =priceCheckerDatabase.getAllMainSetting();}

//                String link = controll.URL + "GetJRDITEMPRICE";
//
//                cono ITEMCODE
                String itemCode=editeTextItemCode.getText().toString().trim();
                itemCode="5D0202011";
                String data = "ITEMCODE=" + URLEncoder.encode(itemCode, "UTF-8")+"&"
                        +"CONO=" + URLEncoder.encode(STORE, "UTF-8") ;

                STORE=priceCheckerDatabase.getAllMainSetting_cono();
 String link = "http://"+ipAddress.trim() + "/GetJRDITEMPRICE?"+"ITEMCODE="+itemCode+"&CONO="+STORE;
                Log.e("TAG_itemPrice", "link -->" +link+"     -->"+itemCode);
//                URL url = new URL(link);

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_CustomerAccount", "JsonResponse\t" + JsonResponse);

                return JsonResponse;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("tag", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            if (JsonResponse != null && JsonResponse.contains("ItemNameA")) {
                Log.e("TAG_itemPrice", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);

                    Log.e("TAG_itemPrice", " "+parentArray.toString());
                    Log.e("TAG_itemPriceR", " "+JsonResponse);
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);


                        String itemName= finalObject.getString("ItemNameA");
                        String qty= finalObject.getString("F_D");

                        dialogForItem(itemName,qty);
                        editeTextItemCode.setText("");
                        Log.e("TAG_itemPrice", "****getSuccess"+qty+"name= "+ itemName);

                    }

//                    if(pdItem !=null) {
//                        pdItem.dismissWithAnimation();
////                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
////                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
////                                .setContentText(context.getResources().getString(R.string.importSuc))
////                                .show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
                Log.e("TAG_itemPrice", "****No Parameter Found.");

//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("*");



            } else {
                Log.e("TAG_itemPrice", "****Failed to export data");

//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("-1");
            }
//            progressDialog.dismiss();
        }
    }
    private class JSONTask_AccountStatment_Withdate extends AsyncTask<String, String, String> {

        private String custId = "";
        private int type = 0;
        public String from_Date, to_Date;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pdValidation = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            pdValidation.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pdValidation.setTitleText(context.getResources().getString(R.string.process));
//            pdValidation.setCancelable(false);
//            pdValidation.show();
            String do_ = "my";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String ip="",STORE="295";
                Log.e("ipAddress",""+ipAddress);
                if(ipAddress.trim().length()==0)
                {  ipAddress =priceCheckerDatabase.getAllMainSetting();
                    cono=priceCheckerDatabase.getAllMainSetting_cono();
                }
                cono=priceCheckerDatabase.getAllMainSetting_cono();
                if (!ipAddress.equals("")) {
                    //  URL_TO_HIT = "http://" + ipAddress +"/Falcons/VAN.dll/GetACCOUNTSTATMENT?ACCNO=402001100";
//                    if (ipAddress.contains(":")) {
//                        int ind = ipAddress.indexOf(":");
//                        ipAddress = ipAddress.substring(0, ind);
//                    }

                    String itemCode=editeTextItemCode.getText().toString().trim();
//                    itemCode="5D0202011";
                    URL_TO_HIT = "http://" + ipAddress.trim() + "/GetJRDITEMPRICE?ITEMCODE=" + itemCode +
                            "&CONO=" + cono ;


                }





                Log.e("urlAccount", "" + URL_TO_HIT.toString());
            } catch (Exception e) {
            }

            try {

                String JsonResponse = null;
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(URL_TO_HIT));

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                nameValuePairs.add(new BasicNameValuePair("FLAG", "1"));
//                nameValuePairs.add(new BasicNameValuePair("customerNo", custId));


                //  request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("tag_CustomerAccount", "JsonResponse\t" + JsonResponse);

                return JsonResponse;


            }//org.apache.http.conn.HttpHostConnectException: Connection to http://10.0.0.115 refused
            catch (HttpHostConnectException ex) {
                ex.printStackTrace();
//                progressDialog.dismiss();

                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {

                        Toast.makeText(MainActivity.this, "Ip Connection Failed AccountStatment", Toast.LENGTH_LONG).show();
                    }
                });


                return null;
            } catch (Exception e) {
                e.printStackTrace();
//                progressDialog.dismiss();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String JsonResponse) {
            super.onPostExecute(JsonResponse);

            JSONObject result = null;
            String impo = "";

            if (JsonResponse != null && JsonResponse.contains("ITEMNAMEA")) {
                Log.e("TAG_itemPrice", "****Success");

                try {

                    JSONArray parentArray = new JSONArray(JsonResponse);

                    Log.e("TAG_itemPrice", " "+parentArray.toString());
                    Log.e("TAG_itemPriceR", " "+JsonResponse);
                    String itemName="",qty="";
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);


                         itemName= finalObject.getString("ITEMNAMEA");
                         qty= finalObject.getString("F_D");


                        Log.e("TAG_itemPrice", "****getSuccess"+qty+"name= "+ itemName);

                    }
                    if(Currencyvalu.equals("1"))
                    itemprice.setText(qty+"  IQD ");
                    else
                        itemprice.setText(qty+"  JD ");

                    itemname.setText(itemName);
//                    if(openDial==false)
//                    dialogForItem(itemName,qty);
                    editeTextItemCode.setText("");
                    editeTextItemCode.requestFocus();
//                    if(pdItem !=null) {
//                        pdItem.dismissWithAnimation();
////                        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
////                                .setTitleText(context.getResources().getString(R.string.save_SUCCESS))
////                                .setContentText(context.getResources().getString(R.string.importSuc))
////                                .show();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if(JsonResponse != null && JsonResponse.contains("No Parameter Found.")){
                Log.e("TAG_itemPrice", "****No Parameter Found.");
//                Toast.makeText(MainActivity.this, "Not Exist", Toast.LENGTH_SHORT).show();
                editeTextItemCode.setText("");

                ccleatext();
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("*");



            } else {
                Log.e("TAG_itemPrice", "****Failed to export data");
                editeTextItemCode.setText("");
                ccleatext();
//                if(pdItem !=null) {
//                    pdItem.dismissWithAnimation();
//                }
//
//                textViewUpdate.setText("-1");
            }
//            progressDialog.dismiss();
        }

//                progressDialog.dismiss();
            }
    private void passwordDialog() {
        final EditText editText = new EditText(MainActivity.this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        SweetAlertDialog sweetMessage= new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE);

        sweetMessage.setTitleText(getResources().getString(R.string.enter_password));
        sweetMessage .setConfirmText("Ok");
        sweetMessage.setCanceledOnTouchOutside(true);
        sweetMessage.setCustomView(editText);
        sweetMessage.setConfirmButton(getResources().getString(R.string.app_ok), new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if(editText.getText().toString().trim().equals("2022000"))
                {
                    sweetMessage.dismiss();
                    dialogSetting();
                }
                else {
                    editText.setError("Incorrect");
                }
            }
        })

                .show();
    }
        }







