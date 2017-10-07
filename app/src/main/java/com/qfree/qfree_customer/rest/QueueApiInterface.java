package com.qfree.qfree_customer.rest;

import com.qfree.qfree_customer.model.Customer;
import com.qfree.qfree_customer.model.PageResponse;
import com.qfree.qfree_customer.model.Queue;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Fahad Qureshi on 10/2/2017.
 */

public interface QueueApiInterface {
    @GET("queues/{id}")
    Call<Queue> getQueueById(@Path("id") String id);

    @GET("facilities/{id}/queues")
    Call<PageResponse<Queue>> getAllQueues(@Path("id") String facilityId);

    @GET("facilities/{id}/queues/{queue_id}/customers/{customer_id}/enqueue")
    Call<Customer> enqueueCustomer(@Path("id") String facilityId, @Path("queue_id") String queueId, @Path("customer_id") String customerId);
}
