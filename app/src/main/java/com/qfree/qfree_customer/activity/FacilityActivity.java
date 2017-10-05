package com.qfree.qfree_customer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.qfree.qfree_customer.R;
import com.qfree.qfree_customer.adapter.QueueAdapter;
import com.qfree.qfree_customer.adapter.QueueAdapterListener;
import com.qfree.qfree_customer.model.Facility;
import com.qfree.qfree_customer.model.PageResponse;
import com.qfree.qfree_customer.model.Queue;
import com.qfree.qfree_customer.rest.ApiClient;
import com.qfree.qfree_customer.rest.QueueApiInterface;
import com.qfree.qfree_customer.rest.RestError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityActivity extends AppCompatActivity {
    private static final String TAG = FacilityActivity.class.getSimpleName();

    private Facility facility;

    private QueueApiInterface queueApiService;

    private TextView facilityNameTextView;
    private RecyclerView queuesRecyclerView;
    private QueueAdapter queueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);

        facility = (Facility) getIntent().getSerializableExtra("facility");
        queueApiService = ApiClient.getClient().create(QueueApiInterface.class);

        facilityNameTextView = (TextView) findViewById(R.id.tv_facility_name);
        queuesRecyclerView = (RecyclerView) findViewById(R.id.rv_queues);

        facilityNameTextView.setText(facility.getName());
        queuesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String facilityId = facility.getId();
        Call<PageResponse<Queue>> facilityQueuesCall = queueApiService.getAllQueues(facilityId);
        facilityQueuesCall.enqueue(new Callback<PageResponse<Queue>>() {
            @Override
            public void onResponse(Call<PageResponse<Queue>> call, Response<PageResponse<Queue>> response) {
                if (RestError.ShowIfError(TAG, response, getApplicationContext())) {
                    PageResponse<Queue> queuesPageResp = response.body();
                    if (queuesPageResp != null) {
                        List<Queue> queues = queuesPageResp.getDocs();
                        queueAdapter = new QueueAdapter(queues,
                                R.layout.list_item_queue,
                                getApplicationContext(),
                                new QueueAdapterListener() {
                                    @Override
                                    public void onQueueSelected(int position, Queue queue) {
                                        if (queue != null) {
                                            Toast.makeText(getApplicationContext(), queue.getName() + "clicked", Toast.LENGTH_SHORT).show();
//                                            selectedQueuePosition = position;
                                            /*Intent intent = new Intent(FacilityActivity.this, QueueActivity.class);
                                            intent.putExtra("selected_queue", queue);
                                            startActivity(intent);*/
                                        }
                                    }
                                });
                        queuesRecyclerView.setAdapter(queueAdapter);
                    }
                }
            }

            @Override
            public void onFailure (Call < PageResponse < Queue >> call, Throwable t){
                RestError.ShowError(TAG, t.toString(), getApplicationContext());
            }
        });
    }
}
