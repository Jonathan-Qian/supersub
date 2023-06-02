package com.example.supersub.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;

import java.util.ArrayList;

public class TeamCardAdapter extends RecyclerView.Adapter<TeamCardAdapter.Viewholder> {
    private ArrayList<Player> players;

    public TeamCardAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvNumber, tvName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_dashboard_team_card_element_number);
            tvName = itemView.findViewById(R.id.tv_dashboard_team_card_element_name);
        }
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_team_card_element, parent, false);
        TeamCardAdapter.Viewholder viewHolder = new TeamCardAdapter.Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Player player = players.get(position);
        holder.tvNumber.setText(Integer.toString(player.getJerseyNumber()));
        holder.tvName.setText(player.getFirstName() + " " + player.getLastName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
