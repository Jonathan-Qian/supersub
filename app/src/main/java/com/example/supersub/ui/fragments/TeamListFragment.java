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
import com.example.supersub.models.Team;
import com.example.supersub.models.TeamFacade;
import com.example.supersub.ui.DrawerHeaderSetter;
import com.example.supersub.ui.DrawerLocker;
import com.example.supersub.ui.VerticalSpaceItemDecoration;
import com.example.supersub.ui.adapters.TeamListAdapter;

import java.util.ArrayList;

public class TeamListFragment extends Fragment implements TeamListAdapter.OnTeamListener {
    private RecyclerView rvTeams;
    private Button buttonNewTeam;
    private ArrayList<TeamFacade> teamFacades;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Disable the drawer.
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        // Inflate the layout that corresponds to this fragment.
        return inflater.inflate(R.layout.fragment_team_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views.
        rvTeams = view.findViewById(R.id.rv_team_list);
        buttonNewTeam = view.findViewById(R.id.button_new_team);

        // Read all TeamFacade(s) and add it to the RecyclerView's adapter.
        //TODO: if teamFacades.size() == 0, display a layout prompting the user to create their first team
        teamFacades = TeamFacade.readAll(view.getContext());
        TeamListAdapter adapter = new TeamListAdapter(teamFacades, this);

        // Set up the team card RecyclerView with the TeamFacade(s) that have just been read above.
        rvTeams.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rvTeams.setLayoutManager(layoutManager);
        rvTeams.addItemDecoration(new VerticalSpaceItemDecoration(32)); // Add spacing between the items in the RecyclerView.

        // When the new team button is tapped, go to the create team page.
        buttonNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(
                        TeamListFragmentDirections.actionTeamListFragmentToCreateTeamFragment()
                );
            }
        });
    }

    // When a team card is clicked, set the current team to the team that was clicked, update the header to display its information and go to the dashboard page.
    @Override
    public void onTeamClick(int position) {
        Team.setCurrentTeam(getContext(), teamFacades.get(position));
        ((DrawerHeaderSetter) getActivity()).updateDrawerHeader(Team.getCurrentTeam());
        Navigation.findNavController(getView()).navigate(
                TeamListFragmentDirections.actionTeamListFragmentToDashboardFragment()
        );
    }
}