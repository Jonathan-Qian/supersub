package com.example.supersub.ui.adapters;

import android.animation.LayoutTransition;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.supersub.R;
import com.example.supersub.models.Player;

import java.util.ArrayList;

// RecyclerView adapter for that displays match event player cards.
public class MatchEventAdapter extends RecyclerView.Adapter<MatchEventAdapter.ViewHolder> {
    private ArrayList<Player> players;

    public MatchEventAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    // Getters.
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // The ViewHolder is the container for an item in the RecyclerView similar to how fragments are containers for a page.
    public class ViewHolder extends RecyclerView.ViewHolder {
        // expander and expanded are needed for the drop down functionality of a match event player card.
        private LinearLayout expander; // The expander is what contains the expanded layout.
        private GridLayout expanded; // Expanded is the layout that appears when the expander layout expands.
        private ImageView ivDropdown;
        private TextView tvNumber, tvName, tvGoals, tvAssists, tvYellowCards, tvRedCards;
        private View incrementGoals, incrementAssists, incrementYellowCards, incrementRedCards;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views.
            expander = itemView.findViewById(R.id.match_event_card_expander);
            tvNumber = itemView.findViewById(R.id.tv_match_event_player_number);
            tvName = itemView.findViewById(R.id.tv_match_event_player_name);
            ivDropdown = itemView.findViewById(R.id.iv_drop_down);
            expanded = itemView.findViewById(R.id.match_event_card_expanded);
            incrementGoals = itemView.findViewById(R.id.increment_match_event_goals);
            incrementAssists = itemView.findViewById(R.id.increment_match_event_assists);
            incrementYellowCards = itemView.findViewById(R.id.increment_match_event_yellow_cards);
            incrementRedCards = itemView.findViewById(R.id.increment_match_event_red_cards);
            tvGoals = incrementGoals.findViewById(R.id.tv_increment_number);
            tvAssists = incrementAssists.findViewById(R.id.tv_increment_number);
            tvYellowCards = incrementYellowCards.findViewById(R.id.tv_increment_number);
            tvRedCards = incrementRedCards.findViewById(R.id.tv_increment_number);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate match event card and pass it into a new ViewHolder.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_match_event_player, parent, false);
        MatchEventAdapter.ViewHolder viewHolder = new MatchEventAdapter.ViewHolder(view);
        return viewHolder;
    }

    // This is where the behaviour of each ViewHolder in the RecyclerView will be defined.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set text of the TextView(s) in the ViewHolder.
        holder.tvNumber.setText(Integer.toString(players.get(position).getJerseyNumber()));
        holder.tvName.setText(players.get(position).getFirstName() + " " + players.get(position).getLastName());
        // Add a transition to the did the expander layout.
        holder.expander.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        // Expand the card when the dropdown button is clicked.
        holder.ivDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(holder);
            }
        });

        Player currentPlayer = players.get(position);

        holder.incrementGoals.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int goals = Integer.parseInt(holder.tvGoals.getText().toString()) - 1;

                if (goals >= 0) {
                    holder.tvGoals.setText(Integer.toString(goals));
                    currentPlayer.setGoals(currentPlayer.getGoals() - 1);
                }
                else {
                    Toast.makeText(holder.expander.getContext(), R.string.goals_less_than_0, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.incrementGoals.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int goals = Integer.parseInt(holder.tvGoals.getText().toString()) + 1;
                holder.tvGoals.setText(Integer.toString(goals));
                currentPlayer.setGoals(currentPlayer.getGoals() + 1);
                //TODO: When a goal is added, show a pop up prompting the user to identify who assisted the goal
            }
        });

        holder.incrementAssists.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int assists = Integer.parseInt(holder.tvAssists.getText().toString()) - 1;

                if (assists >= 0) {
                    holder.tvAssists.setText(Integer.toString(assists));
                    currentPlayer.setAssists(currentPlayer.getAssists() - 1);
                }
                else {
                    Toast.makeText(holder.expander.getContext(), R.string.assists_less_than_0, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.incrementAssists.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int assists = Integer.parseInt(holder.tvAssists.getText().toString()) + 1;
                holder.tvAssists.setText(Integer.toString(assists));
                currentPlayer.setAssists(currentPlayer.getAssists() + 1);
                //TODO: Make sure the total number of assists isn't more than the total number of goals
            }
        });

        holder.incrementYellowCards.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yellowCards = Integer.parseInt(holder.tvYellowCards.getText().toString()) - 1;

                if (yellowCards >= 0) {
                    holder.tvYellowCards.setText(Integer.toString(yellowCards));
                    currentPlayer.setYellowCards(currentPlayer.getYellowCards() - 1);
                }
                else {
                    Toast.makeText(holder.expander.getContext(), R.string.yellow_cards_less_than_0, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.incrementYellowCards.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int yellowCards = Integer.parseInt(holder.tvYellowCards.getText().toString()) + 1;

                if (yellowCards > 1) {
                    holder.tvYellowCards.setText("0");
                    holder.tvRedCards.setText("1");
                    currentPlayer.setYellowCards(currentPlayer.getYellowCards() - 1);
                    currentPlayer.setRedCards(currentPlayer.getRedCards() + 1);
                    Toast.makeText(holder.expander.getContext(), R.string.double_yellow_card, Toast.LENGTH_SHORT).show();
                }
                else if (holder.tvRedCards.getText().equals("1")) {
                    Toast.makeText(holder.expander.getContext(), R.string.yellow_and_red_card, Toast.LENGTH_SHORT).show();
                }
                else  {
                    holder.tvYellowCards.setText(Integer.toString(yellowCards));
                    currentPlayer.setYellowCards(currentPlayer.getYellowCards() + 1);
                }
            }
        });

        holder.incrementRedCards.findViewById(R.id.decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int redCards = Integer.parseInt(holder.tvRedCards.getText().toString()) - 1;

                if (redCards >= 0) {
                    holder.tvRedCards.setText(Integer.toString(redCards));
                    currentPlayer.setRedCards(currentPlayer.getRedCards() - 1);
                }
                else {
                    Toast.makeText(holder.expander.getContext(), R.string.red_cards_less_than_0, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.incrementRedCards.findViewById(R.id.increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int redCards = Integer.parseInt(holder.tvRedCards.getText().toString()) + 1;

                if (redCards <= 1) {
                    holder.tvRedCards.setText(Integer.toString(redCards));
                    currentPlayer.setRedCards(currentPlayer.getRedCards() + 1);
                }
                else {
                    Toast.makeText(holder.expander.getContext(), R.string.red_cards_more_than_1, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    // Expand the expander within the ViewHolder.
    public void expand(ViewHolder holder) {
        // Toggle visibility of the expanded layout and do a smooth animation when it happens.
        int visibility = (holder.expanded.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(holder.expander, new AutoTransition());
        holder.expanded.setVisibility(visibility);
    }
}
