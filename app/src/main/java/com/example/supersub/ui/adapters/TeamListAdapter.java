package com.example.supersub.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Team;

import java.util.ArrayList;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {
    private ArrayList<Team> teams;

    public TeamListAdapter(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTeamName, tvSeason, tvDescription;
        private View viewTeamColour;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.tv_team_card_name);
            tvSeason = itemView.findViewById(R.id.tv_team_card_season);
            tvDescription = itemView.findViewById(R.id.tv_team_card_description);
            viewTeamColour = itemView.findViewById(R.id.view_team_colour);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTeamName.setText(teams.get(position).getName());
        holder.tvSeason.setText(teams.get(position).getSeason());
        holder.tvDescription.setText(teams.get(position).getDescription());
        holder.viewTeamColour.setBackgroundColor(teams.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void addTeam(Team team) {
        teams.add(team);
        notifyDataSetChanged();
    }
}
