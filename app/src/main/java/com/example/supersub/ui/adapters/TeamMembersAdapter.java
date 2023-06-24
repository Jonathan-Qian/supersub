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

// RecyclerView adapter that displays player cards for the team members fragment.
public class TeamMembersAdapter extends RecyclerView.Adapter<TeamMembersAdapter.ViewHolder> {
    // This list contains all the items that will be displayed in the RecyclerView this adapter is used in.
    private ArrayList<Player> players;
    // A custom listener that is intended to be passed in from whatever fragment that uses this adapter and will be relayed to all the ViewHolder(s) in this adapter (See comments at the OnPlayerListener interface below).
    private OnPlayerListener onPlayerListener;

    public TeamMembersAdapter(ArrayList<Player> players, OnPlayerListener onPlayerListener) {
        this.players = players;
        this.onPlayerListener = onPlayerListener;
    }

    /*
     * The ViewHolder is the container for an item in the RecyclerView similar to how fragments are containers for a page.
     * It implements View.OnClickListener so that it can set its onClickListener to itself.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvNumber, tvName;
        private OnPlayerListener onPlayerListener;

        // Initialize views and OnPlayerListener.
        public ViewHolder(@NonNull View itemView, OnPlayerListener onPlayerListener) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_player_card_number);
            tvName = itemView.findViewById(R.id.tv_player_card_name);
            this.onPlayerListener = onPlayerListener;

            itemView.setOnClickListener(this);
        }

        // When this ViewHolder is clicked, call the implementation of onPlayerClick() from the OnPlayerListener that is passed into this ViewHolder.
        @Override
        public void onClick(View view) {
            onPlayerListener.onPlayerClick(getAdapterPosition());
        }
    }

    // This interface along with its set up in this class and the ViewHolder class allows a fragment to define custom behaviour for when an item in the RecyclerView attached to this adapter is clicked.
    public interface OnPlayerListener {
        void onPlayerClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate match event card and pass it into a new ViewHolder along with onPlayerListener (see comments at the declaration of onPlayerListener above).
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_team_members_player, parent, false);
        TeamMembersAdapter.ViewHolder viewHolder = new TeamMembersAdapter.ViewHolder(view, onPlayerListener);
        return viewHolder;
    }

    // This is where the content of each view in each ViewHolder in the RecyclerView is defined.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set the player card views to display the ViewHolder's player's information.
        holder.tvNumber.setText(Integer.toString(players.get(position).getJerseyNumber()));
        holder.tvName.setText(players.get(position).getFirstName() + " " + players.get(position). getLastName());
    }

    // Return the length of players.
    @Override
    public int getItemCount() {
        return players.size();
    }
}
