package com.example.supersub.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supersub.R;
import com.example.supersub.models.Player;
import com.example.supersub.models.Team;
import com.example.supersub.ui.adapters.TeamMembersAdapter;

import java.util.ArrayList;

public class TeamMembersFragment extends Fragment {
    private Button buttonAddPlayer, buttonEditPlayer, buttonRemovePlayer;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team_members, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonAddPlayer = view.findViewById(R.id.button_add_player);
        buttonEditPlayer = view.findViewById(R.id.button_edit_player);
        buttonRemovePlayer = view.findViewById(R.id.button_remove_player);
        recyclerView = view.findViewById(R.id.rv_team_players_list);

        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        TeamMainFragmentDirections.actionTeamMainFragmentToAddPlayerFragment()
                );
            }
        });

        ArrayList<Player> players = Team.getCurrentTeam().getPlayers();
        TeamMembersAdapter adapter = new TeamMembersAdapter(players);

        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }
}
