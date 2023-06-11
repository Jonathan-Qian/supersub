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
    private OnPlayerListener onPlayerListener;

    public TeamMembersAdapter(ArrayList<Player> players, OnPlayerListener onPlayerListener) {
        this.players = players;
        this.onPlayerListener = onPlayerListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvNumber, tvName;
        private OnPlayerListener onPlayerListener;

        public ViewHolder(@NonNull View itemView, OnPlayerListener onPlayerListener) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_player_card_number);
            tvName = itemView.findViewById(R.id.tv_player_card_name);
            this.onPlayerListener = onPlayerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPlayerListener.onPlayerClick(getAdapterPosition());
        }
    }

    public interface OnPlayerListener {
        void onPlayerClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_members_player, parent, false);
        TeamMembersAdapter.ViewHolder viewHolder = new TeamMembersAdapter.ViewHolder(view, onPlayerListener);
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
