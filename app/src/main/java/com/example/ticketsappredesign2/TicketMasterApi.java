package com.example.ticketsappredesign2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketMasterApi {
    @GET("discovery/v2/events")  // Updated endpoint path
    Call<EventResponse> getEvents(
            @Query("apikey") String apiKey,
            @Query("locale") String locale,
            @Query("page") int page,
            @Query("countryCode") String countryCode
    );
}
