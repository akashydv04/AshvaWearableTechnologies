package com.akash.ashvawearabletechnologies.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static RetrofitClient instance = null;

    public Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                            .baseUrl("http://api.timezonedb.com/v2.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

}
