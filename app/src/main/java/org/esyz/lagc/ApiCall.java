package org.esyz.lagc;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Sonu on 28-02-2017.
 */

public interface ApiCall {
    @GET("lagc/latest_event.php")
    Call<JsonObject> latest_event();

    @GET("lagc/getEvent.php")
    Call<JsonObject> latest_event_list();

    @FormUrlEncoded
    @POST("/lagc/subscribe.php")
    Call<JsonObject> subscribe(@Field("name") String name, @Field("email") String email, @Field("phone") String phone);

    //http://35.154.142.214/lagc/getCommitte.php
    @GET("lagc/getCommitte.php")
    Call<JsonObject> getCommitte();

    @GET("/lagc/getGallary.php")
    Call<JsonObject> getGallary();

}
