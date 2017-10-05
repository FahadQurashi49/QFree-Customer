package com.qfree.qfree_customer.rest;

import com.qfree.qfree_customer.model.Facility;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Fahad Qureshi on 10/5/2017.
 */

public interface FacilityApiInterface {
    @GET("facilities/{id}")
    Call<Facility> getFacility(@Path("id") String id);

}
