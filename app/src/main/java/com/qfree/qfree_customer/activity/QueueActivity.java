package com.qfree.qfree_customer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class QueueActivity extends AppCompatActivity {
    private static final String TAG = QueueActivity.class.getSimpleName();

    private QueueApiInterface queueApiInterface;

    private Queue queue;

    private TextView queueNameTextView;
    private TextView currNumbTextView;
    private TextView expectedNumbTextView;
    private TextView expectedDiffTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        queue = (Queue) getIntent().getSerializableExtra("selected_queue");

        queueNameTextView = (TextView) findViewById(R.id.tv_queue_name);
        currNumbTextView = (TextView) findViewById(R.id.tv_curr_numb);
        expectedNumbTextView = (TextView) findViewById(R.id.tv_expected_number);
        expectedDiffTextView = (TextView) findViewById(R.id.tv_expected_diff);

        queueApiInterface = ApiClient.getClient().create(QueueApiInterface.class);

        if (queue.getRunning()) {
            queueNameTextView.setText(queue.getName());
            currNumbTextView.setText(String.format(Locale.ENGLISH, "%d", (queue.getFront() + 1)));
            Long expectedNumber = queue.getRear() + 1;
            expectedNumbTextView.setText(String.format(Locale.ENGLISH, "%d", expectedNumber));
            Long diffNum = expectedNumber - (queue.getFront() + 1);
            expectedDiffTextView.setText(String.format(Locale.ENGLISH, "%d", diffNum));

        } else {
            Toast.makeText(getApplicationContext(), "Queue not running", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBtnEnqueueClick(View view) {
        Customer currCustomer = CustomerService.getInstance().getCustomerInstance();
        Call<Customer> enqueueCustomerCall = queueApiInterface.enqueueCustomer(
                queue.getFacilityId(), queue.getId(), currCustomer.getId());
        enqueueCustomerCall.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                try {
                    if (RestError.ShowIfError(TAG, response, getApplicationContext())) {
                        Customer customer = response.body();
                        CustomerService.getInstance().setCustomerInstance(customer);
//                        Toast.makeText(getApplicationContext(), customer.getQueueNumber() + "", Toast.LENGTH_SHORT).show();
                        enqueueSuccessful();
                    }

                } catch (Exception e) {
                    RestError.ShowError(TAG, e.getMessage(), getApplicationContext());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                RestError.ShowError(TAG, t.getMessage(), getApplicationContext());
            }
        });

    }

    private void enqueueSuccessful() {
        // thanks to: https://stackoverflow.com/a/28869667/4233036
        Intent intent = new Intent(this, CustomerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        /*Intent returnIntent = new Intent();
        returnIntent.putExtra("isSuccessful", true);
        setResult(RESULT_OK, returnIntent);
        finish();*/
    }
}
