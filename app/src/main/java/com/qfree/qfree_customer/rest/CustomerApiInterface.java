package com.qfree.qfree_customer.rest;

import com.qfree.qfree_customer.model.Customer;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Fahad Qureshi on 10/1/2017.
 */

public interface CustomerApiInterface {
    @GET("customers/{id}")
    Call<Customer> getCustomer(@Path("id") String id);
}
