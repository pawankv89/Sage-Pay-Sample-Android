package com.pk.sagepaysample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by pawan on 07/04/18.
 */

public class PaymentDetailsActivity extends AppCompatActivity {


    TextView text_amount, text_firstname, text_surname, text_address1, text_address2, text_city, text_state, text_country, text_pincode, text_phone, text_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdetails);

        // Get TextView id

        text_amount = (TextView) findViewById(R.id.amount);
        text_firstname = (TextView) findViewById(R.id.firstname);
        text_surname = (TextView) findViewById(R.id.surname);
        text_address1 = (TextView) findViewById(R.id.address1);
        text_address2 = (TextView) findViewById(R.id.address2);
        text_city = (TextView) findViewById(R.id.city);
        text_state = (TextView) findViewById(R.id.state);
        text_country = (TextView) findViewById(R.id.country);
        text_pincode = (TextView) findViewById(R.id.pincode);
        text_phone = (TextView) findViewById(R.id.phone);
        text_email = (TextView) findViewById(R.id.email);

        text_amount.setText("50.55");
        text_firstname.setText("Pawan");
        text_surname.setText("Sharma");
        text_address1.setText("Sector 15");
        text_address2.setText("Near PNB Bank");
        text_city.setText("Noida");
        text_state.setText("Uttar Pradesh");
        text_country.setText("India");
        text_pincode.setText("201301");
        text_phone.setText("9910914896");
        text_email.setText("pawankv89@gmail.com");

        //SagePay Button Tap Action
        Button btn = (Button) findViewById(R.id.sagepaybutton);
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //put your intent code here
                JSONObject billingAddress = new JSONObject();
                try {

                    billingAddress.put("amount", text_amount.getText().toString());
                    billingAddress.put("name", text_firstname.getText().toString());
                    billingAddress.put("currency", "GBP");
                    billingAddress.put("description", "Booking from iOS Product Name App");
                    billingAddress.put("surname", text_surname.getText().toString());
                    billingAddress.put("customerEMail", text_email.getText().toString());
                    billingAddress.put("billingFirstnames", text_firstname.getText().toString());
                    billingAddress.put("billingSurname", text_surname.getText().toString());
                    billingAddress.put("billingAddress1", text_address1.getText().toString() + text_address2.getText().toString());
                    billingAddress.put("billingCity", text_city.getText().toString());
                    billingAddress.put("billingPostCode", text_pincode.getText().toString());
                    billingAddress.put("billingCountry", text_country.getText().toString());
                    billingAddress.put("billingPhone", text_phone.getText().toString());
                    billingAddress.put("deliveryFirstnames", text_firstname.getText().toString());
                    billingAddress.put("deliverySurname", text_surname.getText().toString());
                    billingAddress.put("deliveryAddress1", text_address1.getText().toString() + text_address2.getText().toString());
                    billingAddress.put("deliveryCity", text_city.getText().toString());
                    billingAddress.put("deliveryPostCode", text_pincode.getText().toString());
                    billingAddress.put("deliveryCountry", text_country.getText().toString());
                    billingAddress.put("deliveryPhone", text_phone.getText().toString());
                    billingAddress.put("countryCode", "IN");

                    String jsonString = String.valueOf(billingAddress);

                    Log.d("BillingAddress" , jsonString);


                    SharedPreferences.Editor editor = getSharedPreferences("BillingAddress", MODE_PRIVATE).edit();
                    editor.putString("JSONObject", jsonString);
                    editor.commit();

                    Intent intent = new Intent(PaymentDetailsActivity.this, SagePayActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

}
