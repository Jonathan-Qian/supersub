package com.example.supersub.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.TeamFacade;

import java.util.ArrayList;

// RecyclerView adapter that displays team cards.
public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder> {
    // This list contains all the items that will be displayed in the RecyclerView this adapter is used in.
    private ArrayList<TeamFacade> teamFacades;
    // A custom listener that is intended to be passed in from whatever fragment that uses this adapter and will be relayed to all the ViewHolder(s) in this adapter (See comments at the OnTeamListener interface below).
    private OnTeamListener onTeamListener;

    public TeamListAdapter(ArrayList<TeamFacade> teams, OnTeamListener onTeamListener) {
        this.teamFacades = teams;
        this.onTeamListener = onTeamListener;
    }

    /*
     * The ViewHolder is the container for an item in the RecyclerView similar to how fragments are containers for a page.
     * It implements View.OnClickListener so that it can set its onClickListener to itself.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTeamName, tvSeason, tvDescription;
        private View viewTeamColour;
        private OnTeamListener onTeamListener;

        public ViewHolder(@NonNull View itemView, OnTeamListener onTeamListener) {
            super(itemView);

            // Initialize views and OnTeamListener.
            tvTeamName = itemView.findViewById(R.id.tv_team_card_name);
            tvSeason = itemView.findViewById(R.id.tv_team_card_season);
            tvDescription = itemView.findViewById(R.id.tv_team_card_description);
            viewTeamColour = itemView.findViewById(R.id.view_team_colour);
            this.onTeamListener = onTeamListener;

            itemView.setOnClickListener(this);
        }

        // When this ViewHolder is clicked, call the implementation of onTeamClick() from the OnTeamListener that is passed into this ViewHolder.
        @Override
        public void onClick(View view) {
            onTeamListener.onTeamClick(getAdapterPosition());
        }
    }

    // This interface along with its set up in this class and the ViewHolder class allows a fragment to define custom behaviour for when an item in the RecyclerView attached to this adapter is clicked.
    public interface OnTeamListener {
        void onTeamClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate match event card and pass it into a new ViewHolder along with onTeamListener (see comments at the declaration of onTeamListener above).
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_list_team, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onTeamListener);
        return viewHolder;
    }

    // This is where the content of each view in each ViewHolder in the RecyclerView is defined.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the team card views to display the ViewHolder's team's information.
        holder.tvTeamName.setText(teamFacades.get(position).getName());
        holder.tvSeason.setText(teamFacades.get(position).getSeason());
        holder.tvDescription.setText(teamFacades.get(position).getDescription());
        holder.viewTeamColour.setBackgroundColor(teamFacades.get(position).getColor());
    }

    // Return the length of teamFacades.
    @Override
    public int getItemCount() {
        return teamFacades.size();
    }
}
