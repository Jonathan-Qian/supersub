package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.models.Team;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.adapters.PositionChipAdapter;

public class PlayerProfileFragment extends Fragment {
    private int playerIndex;
    private Player player;
    private TextView tvJerseyNumber, tvName, tvGoals, tvAssists, tvYellowCards, tvRedCards;
    private RecyclerView rvPositions;
    private Button buttonBack, buttonEdit, buttonRemove;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Enable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_player_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views and other variables.
        playerIndex = PlayerProfileFragmentArgs.fromBundle(getArguments()).getPlayerIndex(); // This uses safe args to get the playerIndex. The playerIndex is necessary to determine which player's information to display.
        player = Team.getCurrentTeam().getPlayers().get(playerIndex);
        tvJerseyNumber = view.findViewById(R.id.tv_player_profile_player_number);
        tvName = view.findViewById(R.id.tv_player_profile_player_name);
        tvGoals= view.findViewById(R.id.tv_player_profile_goals);
        tvAssists = view.findViewById(R.id.tv_player_profile_assists);
        tvYellowCards = view.findViewById(R.id.tv_player_profile_yellow_cards);
        tvRedCards = view.findViewById(R.id.tv_player_profile_red_cards);
        tvJerseyNumber = view.findViewById(R.id.tv_player_profile_player_number);
        rvPositions = view.findViewById(R.id.rv_player_profile_positions);
        buttonBack = view.findViewById(R.id.button_player_profile_back);
        buttonEdit = view.findViewById(R.id.button_player_profile_edit_player);
        buttonRemove = view.findViewById(R.id.button_player_profile_remove_player);

        // Set the text in the TextViews to display the player's information.
        tvJerseyNumber.setText(Integer.toString(player.getJerseyNumber()));
        tvName.setText(player.getFirstName() +
                (player.getFirstName().equals("") || player.getLastName().equals("") ? "" : "\n")
                + player.getLastName()); // Only if both the player's first and last name are not empty, make a new line between the first and last name.
        tvGoals.setText(Integer.toString(player.getGoals()));
        tvAssists.setText(Integer.toString(player.getAssists()));
        tvYellowCards.setText(Integer.toString(player.getYellowCards()));
        tvRedCards.setText(Integer.toString(player.getRedCards()));

        // Set up the position chip RecyclerView with the player's position.
        PositionChipAdapter adapter = new PositionChipAdapter(player.getPositions());
        rvPositions.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 6); // Use a grid layout to display the chips in 6 columns.
        rvPositions.setLayoutManager(layoutManager);

        // Go back to the team page.
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        PlayerProfileFragmentDirections.actionPlayerProfileFragmentToTeamMainFragment()
                );
            }
        });

        // Go to the edit player page (see comments in EditPlayerFragment).
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        PlayerProfileFragmentDirections.actionPlayerProfileFragmentToEditPlayerFragment(playerIndex)
                );
            }
        });

        // Remove the player from the current team and go back to the team page.
        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team.getCurrentTeam().getPlayers().remove(player);
                Navigation.findNavController(view).navigate(
                        PlayerProfileFragmentDirections.actionPlayerProfileFragmentToTeamMainFragment()
                );
            }
        });
    }
}
