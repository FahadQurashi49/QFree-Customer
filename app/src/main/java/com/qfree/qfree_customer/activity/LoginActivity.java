package com.qfree.qfree_customer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qfree.qfree_customer.R;
import com.qfree.qfree_customer.model.Customer;
import com.qfree.qfree_customer.rest.ApiClient;
import com.qfree.qfree_customer.rest.CustomerApiInterface;
import com.qfree.qfree_customer.rest.RestError;
import com.qfree.qfree_customer.service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private CustomerApiInterface customerApiInterface;

    private EditText customerIdEditText;
    private TextView loginStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customerIdEditText = (EditText) findViewById(R.id.et_customer_id);
        loginStatusTextView = (TextView) findViewById(R.id.tv_login_status);

        customerApiInterface = ApiClient.getClient()
                .create(CustomerApiInterface.class);

    }

    public void onBtnLoginClick(View view) {
        String customerId = customerIdEditText.getText().toString();
        if (!customerId.isEmpty()) {
            Call<Customer> customerCall = customerApiInterface.getCustomer(customerId);
            customerCall.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(Call<Customer> call, Response<Customer> response) {
                    try {
                        if (RestError.ShowIfError(TAG, response, getApplicationContext())) {
                            Customer customer = response.body();
                            if (customer != null) {
                                CustomerService.getInstance().setCustomerInstance(customer);
                                Intent intent = new Intent(LoginActivity.this, CustomerActivity.class);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {
                        loginStatusTextView.setText(e.getMessage());
                        RestError.ShowError(TAG, e.getMessage(), getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<Customer> call, Throwable t) {
                    loginStatusTextView.setText(t.getMessage());
                    RestError.ShowError(TAG, t.getMessage(), getApplicationContext());
                }
            });
        }
    }
}
