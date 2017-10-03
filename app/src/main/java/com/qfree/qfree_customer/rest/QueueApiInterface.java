package com.qfree.qfree_customer.rest;

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
}
