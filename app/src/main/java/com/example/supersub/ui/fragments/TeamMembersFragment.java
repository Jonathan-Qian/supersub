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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.models.Team;
import com.example.supersub.ui.VerticalSpaceItemDecoration;
import com.example.supersub.ui.adapters.TeamMembersAdapter;

import java.util.ArrayList;

public class TeamMembersFragment extends Fragment implements TeamMembersAdapter.OnPlayerListener {
    private Button buttonAddPlayer, buttonRemovePlayers;
    private TextView tvPlayerCount;
    private RecyclerView rvPlayers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // The drawer has already been enabled in TeamMainFragment.
        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_team_members, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views.
        buttonAddPlayer = view.findViewById(R.id.button_add_player);
        buttonRemovePlayers = view.findViewById(R.id.button_remove_players);
        tvPlayerCount = view.findViewById(R.id.tv_team_members_player_count);
        rvPlayers = view.findViewById(R.id.rv_team_players_list);

        // Go to the add player page when the add player button is tapped.
        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        TeamMainFragmentDirections.actionTeamMainFragmentToAddPlayerFragment()
                );
            }
        });

        // Set up the adapter with the current team's players.
        ArrayList<Player> players = Team.getCurrentTeam().getPlayers();
        TeamMembersAdapter adapter = new TeamMembersAdapter(players, this);

        // Set the player count TextView to show how many players there are in the team by getting the adapter's item count.
        tvPlayerCount.setText(Integer.toString(adapter.getItemCount()));

        rvPlayers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvPlayers.setLayoutManager(layoutManager);
        rvPlayers.addItemDecoration(new VerticalSpaceItemDecoration(32)); // Add spacing between the items in the RecyclerView.
    }

    // When a player card is clicked, go to that player's player profile.
    @Override
    public void onPlayerClick(int position) {
        Navigation.findNavController(getView()).navigate(
                TeamMainFragmentDirections.actionTeamMainFragmentToPlayerProfileFragment(position)
        );
    }
}
