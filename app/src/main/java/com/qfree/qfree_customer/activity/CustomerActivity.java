package com.qfree.qfree_customer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.qfree.qfree_customer.R;
import com.qfree.qfree_customer.model.Customer;
import com.qfree.qfree_customer.service.CustomerService;

import java.util.Locale;

public class CustomerActivity extends AppCompatActivity {
    private static final String TAG = CustomerActivity.class.getSimpleName();

    private Customer currCustomer;

    private TextView customerNameTextView;
    private TextView queueNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        customerNameTextView = (TextView) findViewById(R.id.tv_customer_name);

        currCustomer =  CustomerService.getInstance().getCustomerInstance();
        customerNameTextView.setText(currCustomer.getName());

        if (currCustomer.getInQueue()) {
            queueNumberTextView.setText(String.format(Locale.ENGLISH, "%d", currCustomer.getQueueNumber()));

        }


    }
}
