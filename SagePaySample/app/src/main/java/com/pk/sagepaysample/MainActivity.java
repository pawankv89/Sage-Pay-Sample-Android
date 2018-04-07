package com.pk.sagepaysample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Move to Payment BillingAddress Screen
        Intent intent = new Intent(MainActivity.this, PaymentDetailsActivity.class);
        startActivity(intent);
    }
}
