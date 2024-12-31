package com.example.ticketsappredesign2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private List<Event> ticketList;
    private Context context;

    public TicketAdapter(Context context, List<Event> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Event event = ticketList.get(position);

        // Parse the date string
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(event.getDate());

            // Format month (3 letters)
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.US);
            String month = monthFormat.format(date);

            // Format day
            SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.US);
            String day = dayFormat.format(date);

            holder.ticketMonth.setText(month);
            holder.ticketDate.setText(day);
        } catch (ParseException e) {
            e.printStackTrace();
            holder.ticketMonth.setText("---");
            holder.ticketDate.setText("--");
        }

        holder.ticketTitle.setText(event.getName());
        holder.ticketVenue.setText(event.getVenue());

        // Make entire item view clickable
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TicketDetailActivity.class);
            intent.putExtra("title", event.getName());
            intent.putExtra("venue", event.getVenue());
            intent.putExtra("date", event.getDate());
            intent.putExtra("image", event.getImageUrl()); // Add this line
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public void setTicketList(List<Event> tickets) {
        this.ticketList = tickets;
        notifyDataSetChanged();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView ticketDate, ticketTitle, ticketVenue, ticketMonth;
        ImageView arrowIcon;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketMonth = itemView.findViewById(R.id.ticketMonth);
            ticketDate = itemView.findViewById(R.id.ticketDate);
            ticketTitle = itemView.findViewById(R.id.ticketTitle);
            ticketVenue = itemView.findViewById(R.id.ticketVenue);
            arrowIcon = itemView.findViewById(R.id.arrowIcon);
        }
    }
}
