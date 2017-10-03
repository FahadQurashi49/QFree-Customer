package com.qfree.qfree_customer.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qfree.qfree_customer.R;
import com.qfree.qfree_customer.model.Customer;
import com.qfree.qfree_customer.model.Queue;
import com.qfree.qfree_customer.rest.ApiClient;
import com.qfree.qfree_customer.rest.QueueApiInterface;
import com.qfree.qfree_customer.rest.RestError;
import com.qfree.qfree_customer.service.CustomerService;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    private static final String TAG = CustomerActivity.class.getSimpleName();

    private Customer currCustomer;
    private QueueApiInterface queueApiInterface;

    private ConstraintLayout inQueueLayout;

    private TextView customerNameTextView;
    private TextView queueNumberTextView;
    private TextView queueNameTextView;
    private TextView diffNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        customerNameTextView = (TextView) findViewById(R.id.tv_customer_name);
        queueNumberTextView = (TextView) findViewById(R.id.tv_queue_number);
        queueNameTextView = (TextView) findViewById(R.id.tv_queue_name);
        diffNumberTextView = (TextView) findViewById(R.id.tv_diff_number);
        inQueueLayout = (ConstraintLayout) findViewById(R.id.cl_in_queue);

        queueApiInterface = ApiClient.getClient().create(QueueApiInterface.class);

//        currCustomer =  CustomerService.getInstance().getCustomerInstance();
        currCustomer = (Customer) getIntent().getSerializableExtra("customer");
        customerNameTextView.setText(currCustomer.getName());

        if (currCustomer.getInQueue()) {
            toggleInQueueFunctionalityViews(true);
            queueNumberTextView.setText(String.format(Locale.ENGLISH, "%d", currCustomer.getQueueNumber()));
            Call<Queue> queueCall = queueApiInterface.getQueueById(currCustomer.getQueueId());
            queueCall.enqueue(new Callback<Queue>() {
                @Override
                public void onResponse(Call<Queue> call, Response<Queue> response) {
                    try {
                        if (RestError.ShowIfError(TAG, response, getApplicationContext())) {
                            Queue respQueue = response.body();
                            if (respQueue != null) {
                                queueNameTextView.setText(respQueue.getName());
                                Long diffNum = respQueue.getRear() - (respQueue.getFront() + 1);
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
        }


    }

    private void toggleInQueueFunctionalityViews(boolean show) {
        int visibility = show? View.VISIBLE: View.INVISIBLE;
        inQueueLayout.setVisibility(visibility);
    }
}
