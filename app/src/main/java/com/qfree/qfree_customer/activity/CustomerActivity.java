package com.qfree.qfree_customer.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qfree.qfree_customer.R;
import com.qfree.qfree_customer.model.Customer;
import com.qfree.qfree_customer.model.Facility;
import com.qfree.qfree_customer.model.Queue;
import com.qfree.qfree_customer.rest.ApiClient;
import com.qfree.qfree_customer.rest.FacilityApiInterface;
import com.qfree.qfree_customer.rest.QueueApiInterface;
import com.qfree.qfree_customer.rest.RestError;
import com.qfree.qfree_customer.service.CustomerService;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    private static final String TAG = "test";

    private Customer currCustomer;
    private QueueApiInterface queueApiInterface;

    private ConstraintLayout inQueueLayout;

    private TextView customerNameTextView;
    private TextView queueNumberTextView;
    private TextView currNumberTextView;
    private TextView diffNumberTextView;

    private FacilityApiInterface facilityApiInterface;

    private ConstraintLayout notInQueueLayout;

    private EditText facilityIdEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        customerNameTextView = (TextView) findViewById(R.id.tv_customer_name);
        queueNumberTextView = (TextView) findViewById(R.id.tv_queue_number);
        currNumberTextView = (TextView) findViewById(R.id.tv_current_number);
        diffNumberTextView = (TextView) findViewById(R.id.tv_diff_number);
        inQueueLayout = (ConstraintLayout) findViewById(R.id.cl_in_queue);

        facilityIdEditText = (EditText) findViewById(R.id.tv_facility_id);
        notInQueueLayout = (ConstraintLayout) findViewById(R.id.cl_not_in_queue);


        queueApiInterface = ApiClient.getClient().create(QueueApiInterface.class);
        facilityApiInterface = ApiClient.getClient().create(FacilityApiInterface.class);

        currCustomer =  CustomerService.getInstance().getCustomerInstance();
//        currCustomer = (Customer) getIntent().getSerializableExtra("customer");
        customerNameTextView.setText(currCustomer.getName());

        if (currCustomer.getInQueue()) {
            notInQueueLayout.setVisibility(View.INVISIBLE);
            inQueueLayout.setVisibility(View.VISIBLE);

            queueNumberTextView.setText(String.format(Locale.ENGLISH, "%d", currCustomer.getQueueNumber()));
            Call<Queue> queueCall = queueApiInterface.getQueueById(currCustomer.getQueueId());
            queueCall.enqueue(new Callback<Queue>() {
                @Override
                public void onResponse(Call<Queue> call, Response<Queue> response) {
                    try {
                        if (RestError.ShowIfError(TAG, response, getApplicationContext())) {
                            Queue respQueue = response.body();
                            if (respQueue != null) {
                                currNumberTextView.setText(String.format(Locale.ENGLISH, "%d", respQueue.getFront() + 1) );
                                Long diffNum = (currCustomer.getQueueNumber() -1) - respQueue.getFront();
                                diffNumberTextView.setText(String.format(Locale.ENGLISH, "%d", diffNum));
                            }
                        }
                    } catch (Exception e) {
                        RestError.ShowError(TAG, e.getMessage(), getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<Queue> call, Throwable t) {
                    RestError.ShowError(TAG, t.getMessage(), getApplicationContext());
                }
            });
        } else {
            notInQueueLayout.setVisibility(View.VISIBLE);
            inQueueLayout.setVisibility(View.INVISIBLE);
        }


    }

    public void onBtnFacilitySearchClick(View view) {
        String facilityId = facilityIdEditText.getText().toString();
        if (!facilityId.isEmpty()) {
            Call<Facility> facilityCall = facilityApiInterface.getFacility(facilityId);
            facilityCall.enqueue(new Callback<Facility>() {
                @Override
                public void onResponse(Call<Facility> call, Response<Facility> response) {
                    try {
                        if (RestError.ShowIfError(TAG, response, getApplicationContext())) {
                            Facility facility = response.body();
                            if (facility != null) {
                                Intent intent = new Intent(CustomerActivity.this, FacilityActivity.class);
                                intent.putExtra("facility", facility);
                                startActivity(intent);
                            }

                        }
                    } catch (Exception e) {
                        RestError.ShowError(TAG, e.getMessage(), getApplicationContext());
                    }
                }

                @Override
                public void onFailure(Call<Facility> call, Throwable t) {
                    RestError.ShowError(TAG, t.getMessage(), getApplicationContext());
                }
            });

        }
    }
}
