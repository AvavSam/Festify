package com.example.ticketsappredesign2;

public class Event {
    private String name;
    private String date;
    private String imageUrl;
    private String venue;

    public Event(String name, String date, String imageUrl, String venue) {
        this.name = name;
        this.date = date;
        this.imageUrl = imageUrl;
        this.venue = venue;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVenue() {
        return venue;
    }
}
