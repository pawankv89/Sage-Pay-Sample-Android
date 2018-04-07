package com.pk.sagepaysample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pawan on 07/04/18.
 */


public class SagePaySuccessOrFailActivity extends AppCompatActivity {

    TextView text_bookingstatusmainheading, text_bookingstatussubheading, text_bookingstatuspnrnumber;
    ImageView image_statusimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sagepaysuccessorfail);

        // Get TextView id

        image_statusimageview = (ImageView) findViewById(R.id.statusimageview);
        text_bookingstatusmainheading = (TextView) findViewById(R.id.bookingstatusmainheading);
        text_bookingstatussubheading = (TextView) findViewById(R.id.bookingstatussubheading);
        text_bookingstatuspnrnumber = (TextView) findViewById(R.id.bookingstatuspnrnumber);

        text_bookingstatusmainheading.setText("");
        text_bookingstatussubheading.setText("");
        text_bookingstatuspnrnumber.setText("");

        retriveSAGEPAYMENT();

    }

    void retriveSAGEPAYMENT(){

        //JSONObject
        SharedPreferences shared = getSharedPreferences("SAGEPAYMENT", MODE_PRIVATE);
        String jsonString = (shared.getString("JSONObject", ""));
        System.out.println("JSONObject-->" + jsonString);

        try {

            String Status = "";
            String Amount = "";
            String PostCodeResult = "";
            String DeclineCode = "";
            String VPSTxId = "";
            String GiftAid = "";
            String AVSCV = "";
            String VendorTxCode = "";
            String TxAuthNo = "";
            String BankAuthCode = "";
            String AddressResult = "";
            String DSecureStatus = "";
            String ExpiryDate = "";
            String CardType = "";
            String Last4Digits = "";
            String StatusDetail = "";
            String CV2Result = "";
            String SecureStatus = "";

            JSONObject billingJSON = new JSONObject(jsonString);

            if (billingJSON.optString("Status") != null){

                Status = billingJSON.optString("Status");
            }
            if (billingJSON.optString("Amount") != null){

                Amount = billingJSON.optString("Amount");
            }
            if (billingJSON.optString("PostCodeResult") != null){

                PostCodeResult = billingJSON.optString("PostCodeResult");
            }
            if (billingJSON.optString("DeclineCode") != null){

                DeclineCode = billingJSON.optString("DeclineCode");
            }
            if (billingJSON.optString("VPSTxId") != null){

                VPSTxId = billingJSON.optString("VPSTxId");
            }
            if (billingJSON.optString("GiftAid") != null){

                GiftAid = billingJSON.optString("GiftAid");
            }
            if (billingJSON.optString("VendorTxCode") != null){

                VendorTxCode = billingJSON.optString("VendorTxCode");
            }
            if (billingJSON.optString("TxAuthNo") != null){

                TxAuthNo = billingJSON.optString("TxAuthNo");
            }
            if (billingJSON.optString("BankAuthCode") != null){

                BankAuthCode = billingJSON.optString("BankAuthCode");
            }
            if (billingJSON.optString("AddressResult") != null){

                AddressResult = billingJSON.optString("AddressResult");
            }
            if (billingJSON.optString("AVSCV2") != null){

                AVSCV = billingJSON.optString("AVSCV2");
            }
            if (billingJSON.optString("3DSecureStatus") != null){

                SecureStatus = billingJSON.optString("3DSecureStatus");
            }
            if (billingJSON.optString("ExpiryDate") != null){

                ExpiryDate = billingJSON.optString("ExpiryDate");
            }
            if (billingJSON.optString("CardType") != null){

                CardType = billingJSON.optString("CardType");
            }
            if (billingJSON.optString("Last4Digits") != null){

                Last4Digits = billingJSON.optString("Last4Digits");
            }
            if (billingJSON.optString("StatusDetail") != null){

                StatusDetail = billingJSON.optString("StatusDetail");
            }
            if (billingJSON.optString("CV2Result") != null){

                CV2Result = billingJSON.optString("CV2Result");
            }

            if (Status.equals("OK")){

                text_bookingstatusmainheading.setText("PAYMENT CONFIRMED");
                text_bookingstatussubheading.setText("Thank you for choosing SagePay, your payment is confirmed and reference number is -" + VendorTxCode);
                text_bookingstatuspnrnumber.setText(VendorTxCode);

                image_statusimageview.setImageResource(R.drawable.paymentsuccess);
                text_bookingstatusmainheading.setBackgroundColor(Color.parseColor("#5DB64C"));


            }else {

                text_bookingstatusmainheading.setText("PAYMENT NOT CONFIRMED");
                text_bookingstatussubheading.setText("Thank you for choosing SagePay, your payment is not confirmed and reference number is -" + VendorTxCode);
                text_bookingstatuspnrnumber.setText(VendorTxCode);

                image_statusimageview.setImageResource(R.drawable.paymenterror);
                text_bookingstatusmainheading.setBackgroundColor(Color.parseColor("#F05A5E"));
            }


        }catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("JSONException-->" + e.getLocalizedMessage().toString());
            e.printStackTrace();
        }
    }
}
