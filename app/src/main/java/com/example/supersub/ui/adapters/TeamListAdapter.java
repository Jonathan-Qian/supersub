package com.example.supersub.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Team;
import com.example.supersub.models.TeamFacade;

import java.util.ArrayList;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {
    private ArrayList<TeamFacade> teamFacades;
    private OnTeamListener onTeamListener;

    public TeamListAdapter(ArrayList<TeamFacade> teams, OnTeamListener onTeamListener) {
        this.teamFacades = teams;
        this.onTeamListener = onTeamListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTeamName, tvSeason, tvDescription;
        private View viewTeamColour;
        private OnTeamListener onTeamListener;

        public ViewHolder(@NonNull View itemView, OnTeamListener onTeamListener) {
            super(itemView);
            tvTeamName = itemView.findViewById(R.id.tv_team_card_name);
            tvSeason = itemView.findViewById(R.id.tv_team_card_season);
            tvDescription = itemView.findViewById(R.id.tv_team_card_description);
            viewTeamColour = itemView.findViewById(R.id.view_team_colour);
            this.onTeamListener = onTeamListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTeamListener.onTeamClick(getAdapterPosition());
        }
    }

    public interface OnTeamListener {
        void onTeamClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onTeamListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTeamName.setText(teamFacades.get(position).getName());
        holder.tvSeason.setText(teamFacades.get(position).getSeason());
        holder.tvDescription.setText(teamFacades.get(position).getDescription());
        holder.viewTeamColour.setBackgroundColor(teamFacades.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return teamFacades.size();
    }

    public void addTeam(TeamFacade team) {
        teamFacades.add(team);
        notifyDataSetChanged();
    }
}
