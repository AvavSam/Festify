package com.example.ticketsappredesign2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private TicketMasterApi apiService;
    private TextView profileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize profile name TextView
        profileName = view.findViewById(R.id.profileName);

        // Get username from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("USER_NAME");
            profileName.setText(name != null ? name : "Guest");
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.container_card42);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter with empty list
        eventAdapter = new EventAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(eventAdapter);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.ticketmaster.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(TicketMasterApi.class);
        fetchEvents();

        return view;
    }

    private void fetchEvents() {
        String apiKey = "cbKXERTqZBS64Q3tNTQIlYLibdxORVNs";
        apiService.getEvents(apiKey, "*", 10, "GB").enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EventResponse.Event> apiEvents = response.body().getEvents();
                    if (apiEvents != null) {
                        List<Event> events = new ArrayList<>();
                        for (EventResponse.Event apiEvent : apiEvents) {
                            events.add(new Event(
                                    apiEvent.getName(),
                                    apiEvent.getDate(),
                                    apiEvent.getImageUrl(),
                                    apiEvent.getVenue()
                            ));
                        }
                        Log.d("API Data", "Events: " + events.toString());
                        eventAdapter.updateEvents(events);
                    }
                } else {
                    Log.e("API Error", "Response not successful: " + response.code());
                    Toast.makeText(getActivity(), "Error fetching events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("API Error", "Network error: " + t.getMessage());
                Toast.makeText(getActivity(), "Error fetching events: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
