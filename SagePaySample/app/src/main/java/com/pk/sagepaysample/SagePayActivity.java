package com.pk.sagepaysample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.sagepay.sdk.api.util.FormApiEncryptionHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by pawan on 07/04/18.
 */


public class SagePayActivity extends AppCompatActivity {

    private static final String TAG = SagePayActivity.class.getSimpleName();
    WebView web;
    String link;
    public static final String QUERY_STRING_BEGIN = "?";
    private static final String DELIMITER = "&";
    public  FormApiEncryptionHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sagepay);

        String registration = generateRegistrationString();

        final FormApiEncryptionHelper helper = new FormApiEncryptionHelper();
        String cryptString = helper.encrypt("UTF-8", SagePayConfig.getInstance().sagePay_Password, registration);
        link = createRegistrationLink(cryptString, SagePayConfig.getInstance().sagePay_Vendor);

        web = (WebView) findViewById(R.id.webv);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(link);
        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String success = SagePayConfig.getInstance().sagePay_SuccessURL + "?crypt=";
                String fail = SagePayConfig.getInstance().sagePay_FailureURL + "?crypt=";

                Log.d(TAG, "onPageStarted: " + url);
                String url1 = url;
                if (url1.contains(fail)) {
                    url1 = url1.replace(fail, "");
                    if (url1.startsWith("@")) {
                        try {
                            String succesOrfail = helper.decrypt("UTF-8", SagePayConfig.getInstance().sagePay_Password, url1.toUpperCase());
                            Log.d(TAG, "onPageFinished:fail " + succesOrfail);

                            JSONObject JSONObject = convertToDictionaryManually(succesOrfail);
                            String jsonString = String.valueOf(JSONObject);

                            SharedPreferences.Editor editor = getSharedPreferences("SAGEPAYMENT", MODE_PRIVATE).edit();
                            editor.putString("JSONObject", jsonString);
                            editor.commit();

                            //Goto Fail Screen
                            gotoPaymentFailVC();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (url1.contains(success)) {
                    url1 = url1.replace(success, "");
                    if (url1.startsWith("@")) {
                        try {
                            String succesOrfail = helper.decrypt("UTF-8", SagePayConfig.getInstance().sagePay_Password, url1.toUpperCase());
                            Log.d(TAG, "onPageFinished:succes " + succesOrfail);

                           JSONObject JSONObject = convertToDictionaryManually(succesOrfail);
                           String jsonString = String.valueOf(JSONObject);

                            SharedPreferences.Editor editor = getSharedPreferences("SAGEPAYMENT", MODE_PRIVATE).edit();
                            editor.putString("JSONObject", jsonString);
                            editor.commit();

                            //Goto Success Screen
                            gotoPaymentSuccessVC();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


            }


        });

    }

    public String generateRegistrationString() {

        String postparams = "";

        String vtcRandomString = randomString(16);
        System.out.println(vtcRandomString);

        String success = SagePayConfig.getInstance().sagePay_SuccessURL;
        String fail = SagePayConfig.getInstance().sagePay_FailureURL;
        String VendorEMail = SagePayConfig.getInstance().sagePay_VendorEmail;

        //JSONObject
        SharedPreferences shared = getSharedPreferences("BillingAddress", MODE_PRIVATE);
        String jsonString = (shared.getString("JSONObject", ""));
        System.out.println("JSONObject-->" + jsonString);

        try {

            JSONObject billingJSON = new JSONObject(jsonString);
            String amount = billingJSON.getString("amount");
            String name = billingJSON.getString("name");
            String currency = billingJSON.getString("currency");
            String description = billingJSON.getString("description");
            String surname = billingJSON.getString("surname");
            String customerEMail = billingJSON.getString("customerEMail");
            String billingFirstnames = billingJSON.getString("billingFirstnames");
            String billingSurname = billingJSON.getString("billingSurname");
            String billingAddress1 = billingJSON.getString("billingAddress1");
            String billingCity = billingJSON.getString("billingCity");
            String billingPostCode = billingJSON.getString("billingPostCode");
            String billingCountry = billingJSON.getString("billingCountry"); //Only For Display in UI not send in below Request only send Country Code IN or GB etc depend on your country
            String billingPhone = billingJSON.getString("billingPhone");
            String deliveryFirstnames = billingJSON.getString("deliveryFirstnames");
            String deliverySurname = billingJSON.getString("deliverySurname");
            String deliveryAddress1 = billingJSON.getString("deliveryAddress1");
            String deliveryCity = billingJSON.getString("deliveryCity");
            String deliveryPostCode = billingJSON.getString("deliveryPostCode");
            String deliveryCountry = billingJSON.getString("deliveryCountry");
            String deliveryPhone = billingJSON.getString("deliveryPhone");
            String countryCode = billingJSON.getString("countryCode");


            postparams = "VendorTxCode=" + vtcRandomString + "&Amount="+ amount +"&Currency="+ currency +"&Description="+ description +"&CustomerName="+ name +"&Surname "+ surname +"&CustomerEMail="+ customerEMail +"&VendorEMail=" + VendorEMail+ "&BillingSurname=" + billingSurname + "&BillingFirstnames=" + billingFirstnames + "&BillingAddress1=" + billingAddress1 + "&BillingCity=" + billingCity + "&BillingPostCode=" + billingPostCode + "&BillingCountry=" +countryCode+ "&BillingPhone=" + billingPhone + "&DeliveryFirstnames=" + deliveryFirstnames + "&DeliverySurname=" + deliverySurname + "&DeliveryAddress1=" + deliveryAddress1 + "&DeliveryCity=" + deliveryCity + "&DeliveryPostCode=" + deliveryPostCode + "&DeliveryCountry=" + countryCode + "&DeliveryPhone=" + deliveryPhone + "&SuccessURL=" + success + "&FailureURL=" + fail;
            System.out.println(postparams);

        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return postparams;
    }

    public static String createRegistrationLink(String cryptString, String vendor) {
        StringBuilder linkBuilder = new StringBuilder();
        linkBuilder.append(SagePayConfig.getInstance().sagePay_TESTURL);
        linkBuilder.append(QUERY_STRING_BEGIN);
        linkBuilder.append("VPSProtocol="+ SagePayConfig.getInstance().sagePay_VPSProtocol + DELIMITER);
        linkBuilder.append("TxType=" + SagePayConfig.getInstance().sagePay_TxType + DELIMITER);
        linkBuilder.append("Vendor=" + vendor + DELIMITER);
        linkBuilder.append("Crypt=" + cryptString);

        return linkBuilder.toString();
    }

    String randomString(final int length) {

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        StringBuilder sb = new StringBuilder(20);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output;
    }

    JSONObject convertToDictionaryManually (String text){

        //put your intent code here
        JSONObject billingAddress = new JSONObject();
        try {

            String[] splitByFirstKey = text.split("&");

            for (int index = 0 ; index < splitByFirstKey.length ; index++){

                String keyValuePair =  splitByFirstKey[index];
                String[] splitBySecondKey = keyValuePair.split("=");

                String key1 = splitBySecondKey[0];
                String value1 = splitBySecondKey[1];
                value1 = value1.replace("{", "");
                value1 = value1.replace("}", "");

                billingAddress.put(key1, value1);

            }

            String jsonString = String.valueOf(billingAddress);

            Log.d("ConvertToDictionary-->" , jsonString);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return billingAddress;
    }

    void gotoPaymentSuccessVC(){

        //Goto SagePaySuccessOrFailVC Screen

        Intent intent = new Intent(SagePayActivity.this, SagePaySuccessOrFailActivity.class);
        startActivity(intent);

    }

    void gotoPaymentFailVC(){

        //Goto SagePaySuccessOrFailVC Screen

        Intent intent = new Intent(SagePayActivity.this, SagePaySuccessOrFailActivity.class);
        startActivity(intent);
    }

}
