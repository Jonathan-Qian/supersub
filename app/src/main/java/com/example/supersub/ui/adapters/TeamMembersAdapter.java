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

public class TeamMembersAdapter extends RecyclerView.Adapter<TeamMembersAdapter.ViewHolder> {
    private ArrayList<Player> players;

    public TeamMembersAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber, tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_player_card_number);
            tvName = itemView.findViewById(R.id.tv_player_card_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_members_player_card, parent, false);
        TeamMembersAdapter.ViewHolder viewHolder = new TeamMembersAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNumber.setText(Integer.toString(players.get(position).getJerseyNumber()));
        holder.tvName.setText(players.get(position).getFirstName() + " " + players.get(position). getLastName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
