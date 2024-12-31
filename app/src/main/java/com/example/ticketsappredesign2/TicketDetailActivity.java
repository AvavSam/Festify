package com.example.ticketsappredesign2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.load.DataSource;

import androidx.annotation.Nullable;
import android.graphics.drawable.Drawable;

public class TicketDetailActivity extends AppCompatActivity {

    private static final String TAG = "TicketDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        // Back button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Ambil data dari intent
        TextView ticketTitle = findViewById(R.id.ticketTitle);
        TextView ticketVenue = findViewById(R.id.ticketVenue);
        ImageView ticketImage = findViewById(R.id.ticketImage);

        // Get image data from intent with the correct key
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Log.d(TAG, "Image URL received: " + imageUrl);

        // Load image using Glide with error handling
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Log.d(TAG, "Attempting to load image from: " + imageUrl);
            Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.event) // Fallback image if loading fails
                .placeholder(R.drawable.event) // Placeholder while loading
                .into(ticketImage);
        } else {
            Log.e(TAG, "No image URL provided");
            ticketImage.setImageResource(R.drawable.event);
        }

        // Contoh data statis dari intent
        String title = getIntent().getStringExtra("title");
        String venue = getIntent().getStringExtra("venue");

        ticketTitle.setText(title != null ? title : "One Direction: This is Us");
        ticketVenue.setText(venue != null ? venue : "Jakarta International Stadium");
    }
}
