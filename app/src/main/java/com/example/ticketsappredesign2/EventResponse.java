package com.example.ticketsappredesign2;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EventResponse {
    @SerializedName("_embedded")
    private Embedded embedded;

    public List<Event> getEvents() {
        if (embedded != null && embedded.events != null) {
            return embedded.events;
        }
        return null;
    }

    public static class Embedded {
        @SerializedName("events")
        List<Event> events;
    }

    // Make Event class static
    public static class Event {
        @SerializedName("name")
        private String name;

        @SerializedName("dates")
        private Dates dates;

        @SerializedName("images")
        private List<Image> images;

        @SerializedName("_embedded")
        private EmbeddedVenue embeddedVenue;

        public String getName() {
            return name;
        }

        public String getDate() {
            if (dates != null && dates.start != null) {
                return dates.start.localDate;
            }
            return "";
        }

        public String getImageUrl() {
            if (images != null) {
                for (Image image : images) {
                    if ("3_2".equals(image.ratio)) {
                        return image.url;
                    }
                }
            }
            return "";
        }

        public String getVenue() {
            if (embeddedVenue != null && embeddedVenue.venues != null && !embeddedVenue.venues.isEmpty()) {
                return embeddedVenue.venues.get(0).name;
            }
            return "";
        }
    }

    // Make all nested classes static
    public static class Dates {
        @SerializedName("start")
        private Start start;

        public static class Start {
            @SerializedName("localDate")
            String localDate;
        }
    }

    public static class Image {
        @SerializedName("ratio")
        String ratio;

        @SerializedName("url")
        String url;
    }

    public static class EmbeddedVenue {
        @SerializedName("venues")
        List<Venue> venues;

        public static class Venue {
            @SerializedName("name")
            String name;
        }
    }
}
