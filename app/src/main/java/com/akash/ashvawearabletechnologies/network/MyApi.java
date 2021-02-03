package com.akash.ashvawearabletechnologies.network;

import com.akash.ashvawearabletechnologies.model.TimeZoneModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {

    @GET("get-time-zone?")
    Call<TimeZoneModel> getTime(@Query("key")String key, @Query("format") String format,
                                @Query("by") String by, @Query("lat") String lat,
                                @Query("lng") String lng);
}
